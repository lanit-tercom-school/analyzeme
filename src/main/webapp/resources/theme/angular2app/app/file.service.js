'use strict';
(function (app) {
    /* global ng */
    var l = app.AppUtils.logger("file.service");

    app.FileService = ng.core
        .Class({
            constructor: [app.ProjectService, function FileService(pS) {
                this.selectedFile = {};

                this.data = [];
                this._projectService = pS;
                this.fileInfo = {};

            }],
            getSelectedFile: function () {
                return Promise.resolve(this.selectedFile);
            },
            getServerName: function () {
                return this.selectedFile.serverName;
            },
            getNameForUser: function () {
                return this.selectedFile.name;
            },
            setSelectedFile: function (file) {
                app.AppUtils.copyObj(file, this.selectedFile);
            },
            equals: function (o1, o2) {
                if (o1 == o2) return true;
                if (o1.length != o2.length) return false;
                for (let property in o1) {
                    if (o1[property] != o2[property]) {
                        return false;
                    }
                }
                return true;
            },
            getFiles: function () {
                this.updateFiles();
                return Promise.resolve(this.data);
            },
            getFileInfo: function () {
                this.updateFileInfo();
                return Promise.resolve(this.fileInfo);
            },
            getFile: function (name) {
                return new Promise((resolve, reject) => {
                    this.getFiles()
                        .then(files => {
                                l.log("files");
                                l.log(files);
                                //TODO: what is fid here?
                                var filtered = app.AppUtils.findByKey("name", files, fid); //projects.filter(project => project.id === id);
                                l.log("files filtered by name:" + name);
                                l.log(filtered);
                                if (filtered.length == 0) {
                                    reject("no files with this name");
                                } else {
                                    resolve(filtered[0]);
                                }
                            },
                            Promise.reject("no data")
                        );
                });
            },
            updateFiles: function () {
                l.log("updateFiles");
                var sp = this._projectService.selectedProject;
                var filesList = null;
                app.AppUtils.API.getProjectFiles(
                    1,
                    sp.projectId === undefined ? "project" : sp.projectId
                    )
                    .then(
                        xhr => {
                            return JSON.parse(xhr.responseText);
                        },
                        err => {
                            l.log("Failed load files list: " + err);
                        }
                    )
                    .then(
                        filesList => {
                            l.log("filesList");
                            l.dir(filesList);
                            if (filesList && filesList.Files) {
                                //{"uniqueName": ..., "nameForUser": ..., "isActive": ...}
                                for (let file of filesList.Files) {
                                    if (file) {
                                        l.log("file in filelist");
                                        app.AppUtils.API.getFileData(file.uniqueName)
                                            .then(
                                                xhr => {
                                                    var downloadedFile = {
                                                        "content": xhr.responseText
                                                    };
                                                    downloadedFile.serverName = file.uniqueName;
                                                    downloadedFile.name = file.nameForUser;
                                                   // downloadedFile.isActive = file.isActive;
                                                    this.data.push(downloadedFile);
                                                },
                                                err => l.log("Failed to load " + file.nameForUser + " file: " + err)
                                            )
                                    }
                                }
                            }
                        });
            },
            updateFileInfo: function () {
                l.log("getFileInfo for" + this.getServerName());
                var sp = this._projectService.selectedProject;
                var fileI = null;
                app.AppUtils.API.getFullFileInfo(
                    1,
                    sp.projectId === undefined ? "project" : sp.projectId,
                    this.getNameForUser()
                    )
                    .then(
                        xhr => {
                            return JSON.parse(xhr.responseText);
                        },
                        err => {
                            l.log("Failed load FileInfo: " + err);
                        }
                    )
                    .then(
                        fileI => {
                            l.log("fileInfo");
                            l.dir(fileI);
                            if (fileI) {
                                this.fileInfo = fileI;
                            }  
                            l.dir(this.fileInfo);
                        })
            },
            addFile: function (file) {
                let fileCopy = {};
                app.AppUtils.copyObj(file, fileCopy);
                this.data.push(fileCopy);
            },
            deleteFile: function(file) {
                for(var i = 0; i < data.length; i++) {
                    if (this.equals(data[i], file)) {
                        delete data[i];
                        l.log("File deleted");
                        return true;
                    }
                }
                return false;
            }
        });

})
(window.app || (window.app = {}));
