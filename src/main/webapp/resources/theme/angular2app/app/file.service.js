'use strict';
(function(app) {
    /* global ng */
    var l = app.AppUtils.logger("file.service");

    app.FileService = ng.core
        .Class({
            constructor: [app.ProjectService, function FileService(pS) {
                this.selectedFile = {};

                this.data = [];
                this._projectService = pS;

            }],
            getSelectedFile: function() {
                return Promise.resolve(this.selectedFile);
            },
            getServerName: function() {
                return this.selectedFile.serverName;
            },
            setSelectedFile: function(file) {
                app.AppUtils.copyObj(file, this.selectedFile);
            },
            equals: function(o1, o2) {
                if (o1 == o2) return true;
                if (o1.length != o2.length) return false;
                for (let property in o1) {
                    if (o1[property] != o2[property]) {
                        return false;
                    }
                }
                return true;
            },
            getFiles: function() {
                this.updateFiles();
                return Promise.resolve(this.data);
            },
            getFile: function(name) {
                return new Promise((resolve, reject) => {
                    this.getFiles()
                        .then(files => {
                                l.log("files");
                                l.log(files);
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
            updateFiles: function() {
                l.log("updateFiles");
                var sp = this._projectService.selectedProject;
                var filesList = null;
                app.AppUtils.API.getProjectFiles(sp.projectName)
                .then(
                    xhr => {
                      return JSON.parse(xhr.responseText);
                    },
                    err => { l.log("Failed load files list: " + err); }
                )
                .then(
                    filesList => {
                      l.log("filesList");
                      l.dir(filesList);
                      if (filesList)
                      {
                        for (let fileName of filesList)
                        {
                          app.AppUtils.API.getFileData(fileName)
                            .then(
                                xhr => {
                                  var downloadedFile = app.AppUtils.extractFileFromXHR(xhr);
                                  downloadedFile.serverName = fileName;
                                  downloadedFile.name = fileName;
                                  this.data.push(downloadedFile);
                                },
                                err => l.log("Failed to load " + fileName + " file: " + err)
                            );
                        };
                      };
                    }
                );
            },
            addFile: function(file) {
                let fileCopy = {};
                app.AppUtils.copyObj(file, fileCopy);
                this.data.push(fileCopy);
            }
        });

})(window.app || (window.app = {}));
