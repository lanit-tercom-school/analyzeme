"use strict";
var app_utils_1 = require("./app-utils");
var FileService = (function () {
    function FileService(projectService) {
        this.projectService = projectService;
        this.selectedFile = {};
        this.data = [];
        this.fileInfo = {};
        this.l = app_utils_1.AppUtils.logger("file.service");
    }
    FileService.prototype.getSelectedFile = function () {
        return Promise.resolve(this.selectedFile);
    };
    FileService.prototype.getServerName = function () {
        return this.selectedFile.serverName;
    };
    FileService.prototype.getNameForUser = function () {
        return this.selectedFile.name;
    };
    FileService.prototype.setSelectedFile = function (file) {
        app_utils_1.AppUtils.copyObj(file, this.selectedFile);
    };
    FileService.prototype.equals = function (o1, o2) {
        if (o1 == o2)
            return true;
        if (!o1 || !o2)
            return false; //!!!
        if (o1.length != o2.length)
            return false;
        for (var property in o1) {
            if (o1[property] != o2[property]) {
                return false;
            }
        }
        return true;
    };
    FileService.prototype.getFiles = function () {
        this.updateFiles();
        return Promise.resolve(this.data);
    };
    FileService.prototype.getFileInfo = function () {
        this.updateFileInfo();
        return Promise.resolve(this.fileInfo);
    };
    FileService.prototype.getFile = function (name) {
        var _this = this;
        return new Promise(function (resolve, reject) {
            _this.getFiles()
                .then(function (files) {
                _this.l.log("files");
                _this.l.log(files);
                //TODO: what is fid here?
                var filtered = app_utils_1.AppUtils.findByKey("name", files, undefined); //projects.filter(project => project.id === id);
                _this.l.log("files filtered by name:" + name);
                _this.l.log(filtered);
                if (filtered.length == 0) {
                    reject("no files with this name");
                }
                else {
                    resolve(filtered[0]);
                }
            }, Promise.reject("no data"));
        });
    };
    FileService.prototype.updateFiles = function () {
        var _this = this;
        this.l.log("updateFiles");
        var sp = this.projectService.selectedProject;
        var filesList = null;
        app_utils_1.AppUtils.API.getProjectFiles(1, sp.projectId === undefined ? "project" : sp.projectId)
            .then(function (xhr) {
            return xhr.responseText ? JSON.parse(xhr.responseText) : [];
        }, function (err) {
            _this.l.log("Failed load files list: " + err);
        })
            .then(function (filesList) {
            _this.l.log("filesList");
            _this.l.dir(filesList);
            if (filesList && filesList.Files) {
                //{"uniqueName": ..., "nameForUser": ..., "isActive": ...}
                var _loop_1 = function(file) {
                    if (file) {
                        _this.l.log("file in filelist");
                        app_utils_1.AppUtils.API.getFileDataFromDataSet(1, _this.projectService.selectedProject.projectId, file.uniqueName)
                            .then(function (xhr) {
                            var downloadedFile = {
                                "content": xhr.responseText
                            };
                            downloadedFile.serverName = file.uniqueName;
                            downloadedFile.name = file.nameForUser;
                            // downloadedFile.isActive = file.isActive;
                            _this.data.push(downloadedFile);
                        }, function (err) { return _this.l.log("Failed to load " + file.nameForUser + " file: " + err); });
                    }
                };
                for (var _i = 0, _a = filesList.Files; _i < _a.length; _i++) {
                    var file = _a[_i];
                    _loop_1(file);
                }
            }
        });
    };
    FileService.prototype.updateFileInfo = function () {
        var _this = this;
        this.l.log("getFileInfo for " + this.getServerName());
        var sp = this.projectService.selectedProject;
        var fileI = null;
        app_utils_1.AppUtils.API.getFullFileInfo(1, sp.projectId === undefined ? "project" : sp.projectId, this.getServerName())
            .then(function (xhr) {
            return xhr.responseText ? JSON.parse(xhr.responseText) : {};
        }, function (err) {
            _this.l.log("Failed load FileInfo: " + err);
        })
            .then(function (fileI) {
            _this.l.log("fileInfo");
            _this.l.dir(fileI);
            if (fileI) {
                app_utils_1.AppUtils.copyObj(fileI, _this.fileInfo);
            }
            _this.l.dir(_this.fileInfo);
        });
    };
    FileService.prototype.addFile = function (file) {
        var fileCopy = {};
        app_utils_1.AppUtils.copyObj(file, fileCopy);
        this.data.push(fileCopy);
    };
    FileService.prototype.deleteFile = function (file) {
        app_utils_1.AppUtils.API.deleteFile(file.serverName);
        for (var i = 0; i < this.data.length; i++) {
            if (this.equals(this.data[i], file)) {
                this.data.splice(i, 1);
                this.l.log("File deleted");
                return true;
            }
        }
        return false;
    };
    return FileService;
}());
exports.FileService = FileService;
//# sourceMappingURL=file.service.js.map