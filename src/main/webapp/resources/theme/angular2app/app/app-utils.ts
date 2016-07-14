import {HighchartsUtils} from "./highcharts-utils";


export class AppUtils {
    static domens = [
        "http://localhost:8080/",
        "http://analyzeme-dev.herokuapp.com/",
        "http://192.168.1.126:8080/",
        "/"
    ];

    static resolveUrl(relativePath) {
        return AppUtils.domens[3] + relativePath;
    };

    static pathToApp:string = AppUtils.resolveUrl("resources/angular2app/");

    static templateUrl(componentName) {
        return AppUtils.pathToApp + "app/templates/" +
            componentName + ".component.html";
    };

    // BUG: Angular 2 don't allow absolute paths in cssUrls
    static cssUrls(componentName) {
        return "../" + AppUtils.pathToApp + "app/css/" +
            componentName + ".component.css";
    };

    static logger(name) {
        return {
            _name: name,
            _level: 0,
            log: function (msg) {
                if (this._level > 0) return;
                window.console.log(new Date().toLocaleTimeString() +
                    "] " + this._name + ">> " + msg);
            },
            dir: function (obj) {
                if (this._level > 1) return;
                this.log("dir-begin--------\\");
                window.console.dir(obj);
                this.log("dir--end---------/");
            },
            error: function (msg, e) {
                if (this._level > 3) return;
                window.console.error(new Date().toLocaleTimeString() +
                    "] " + this._name + ">> " + msg);
                window.console.error(e);
            },
            setLevel: function (level) {
                this._level = level;
            }
        };
    };

    static copyObj(src, dest) {
        for (var property in src) {
            dest[property] = src[property];
        }
    };

    static findByKey(keyName, arr, key) {
        var res = [];
        if (arr) {
            for (var elem of arr) {
                if (elem[keyName] == key) {
                    res.push(elem);
                }
            }
        }
        return res;
    };

    static extractFileFromXHR(xhr) {
        var temp_ResponseFileName = xhr.getResponseHeader("fileName");
        var temp_Date = xhr.getResponseHeader("Date");
        var temp_ResponseData = xhr.getResponseHeader("Data");
        return {
            "name": temp_ResponseFileName,
            "serverName": temp_ResponseFileName,
            "date": temp_Date,
            "content": temp_ResponseData ?
                temp_ResponseData : null
        };
    };

    static makeRequest(method, path, body, headers, successChecker, callbackSuccess, callbackError) {
        return new Promise((resolve, reject) => {
            var xhr = new XMLHttpRequest();
            xhr.onload = xhr.onerror = function (event) {
                if (successChecker(xhr)) {
                    callbackSuccess(xhr);
                    resolve(xhr);
                } else {
                    callbackError(xhr);
                    reject("error " + xhr.status);
                }
            };
            xhr.open(method, AppUtils.resolveUrl(path), true);
            if (headers) {
                for (var header of headers) {
                    xhr.setRequestHeader(header.name, header.data);
                }
            }
            xhr.send(body);
        });
    };

    static testRequest(method, path) {
        AppUtils.makeRequest(method, path, null, [],
            (xhr) => xhr.status == 200,
            (xhr) => window.console.dir(xhr),
            (xhr) => window.console.dir(xhr)
        );
    }

}


export module AppUtils {
    export class MDL {
        static upgradeClasses(classes) {
            var els = [];
            classes.forEach(
                (c) => [].forEach.call(document.getElementsByClassName(c), (e) => els.push(e))
            );
            (componentHandler as any).upgradeElements(els);
        }
    }
    export class API {
        static logger = AppUtils.logger("AppUtils::API");

        static makeRequest(method, path, body, headers, successChecker, callbackSuccess, callbackError) {
            return new Promise((resolve, reject) => {
                var xhr = new XMLHttpRequest();
                xhr.onload = xhr.onerror = function (event) {
                    if (successChecker(xhr)) {
                        callbackSuccess(xhr);
                        resolve(xhr);
                    } else {
                        callbackError(xhr);
                        reject("error " + xhr.status);
                    }
                };
                xhr.open(method, AppUtils.resolveUrl(path), true);
                if (headers) {
                    for (var header of headers) {
                        xhr.setRequestHeader(header.name, header.data);
                    }
                }
                xhr.send(body);
            });
        }

        static createProject(userId, projectName) {
            return AppUtils.makeRequest(
                "PUT",
                userId + "/project/new/create",
                null,
                [
                    {
                        name: "project_name",
                        data: projectName
                    }
                ],
                (xhr) => xhr.status == 200,
                (xhr) => AppUtils.API.logger.log(userId + " created project " + projectName),
                (xhr) => AppUtils.API.logger.log(
                    userId + " failed create project " + projectName
                    + ": error " + xhr.status
                )
            );
        }

        static deleteProject(userId, projectId) {
            return AppUtils.makeRequest(
                "DELETE",
                userId + "/project/" + projectId + "/delete",
                null,
                [],
                (xhr) => xhr.status == 200,
                (xhr) => AppUtils.API.logger.log(userId + " deleted project " + projectId),
                (xhr) => AppUtils.API.logger.log(
                    userId + " failed delete project " + projectId
                    + ": error " + xhr.status
                )
            );
        }

        // returns { "Files" : [{"uniqueName":"0_10.json","nameForUser":"0_10.json"},{"uniqueName":"check_1.json","nameForUser":"check.json"},{"uniqueName":"lineal.json","nameForUser":"lineal.json"}]}
        // extract: JSON.parse(xhr.responseText)
        static getProjectFiles(userId, projectId) {
            return AppUtils.makeRequest(
                "GET",
                userId + "/project/" + projectId + "/filesForList",
                null,
                [],
                (xhr) => xhr.status == 200,
                (xhr) => AppUtils.API.logger.log(userId + " got files of project " + projectId),
                (xhr) => AppUtils.API.logger.log(
                    userId + " failed to get files of project " + projectId
                    + ": error " + xhr.status
                )
            );
        }

        // returns {"uniqueName":"0_10.json","nameForUser":"0_10.json","isActive":"true","fields":[{"fieldName":"x","fieldId":"x"},{"fieldName":"y","fieldId":"y"}],"uploadingDate":"Wed Apr 20 18:25:28 MSK 2016"}
        // extract: JSON.parse(xhr.responseText)
        //file/{user_id}/{project_id}/{nameForUser}/getFullInfo
        static getFullFileInfo(userId, projectId, filenameForUser) {
            return AppUtils.makeRequest(
                "GET",
                "file/" + userId + "/" + projectId + '/' + filenameForUser + "/getFullInfo/",
                null,
                [],
                (xhr) => xhr.status == 200,
                (xhr) => AppUtils.API.logger.log(userId + " got files of project " + projectId),
                (xhr) => AppUtils.API.logger.log(
                    userId + " failed to get FileInfo for file " + filenameForUser + " in project " + projectId
                    + ": error " + xhr.status
                )
            );
        }

        // returns file data
        // extract: JSON.parse(xhr.responseText).Data
        static getFileData(fileName) {
            return AppUtils.makeRequest(
                "GET",
                "file/" + fileName + "/data",
                null,
                [],
                (xhr) => xhr.status == 200,
                (xhr) => AppUtils.API.logger.log("got file's data: " + fileName),
                (xhr) => AppUtils.API.logger.log(
                    "failed to get file's data: " + fileName
                    + ": error " + xhr.status
                )
            );
        }

        ///GET: file/{user_id}/{project_id}/{reference_name}/data
        // returns file data
        // extract: JSON.parse(xhr.responseText).Data
        static getFileDataFromDataSet(userId, projectId, referenceName) {
            return AppUtils.makeRequest(
                "GET",
                "file/" + userId + "/" + projectId + "/" + referenceName + "/data",
                null,
                [],
                (xhr) => xhr.status == 200,
                (xhr) => AppUtils.API.logger.log("got file's data: " + referenceName),
                (xhr) => AppUtils.API.logger.log(
                    "failed to get file's data: " + referenceName
                    + ": error " + xhr.status
                )
            );
        }

        static deleteFile(fileName) {
            return AppUtils.makeRequest(
                "DELETE",
                "file/" + fileName + "/delete",
                null,
                [],
                (xhr) => xhr.status == 200,
                (xhr) => AppUtils.API.logger.log("deleted file: " + fileName),
                (xhr) => AppUtils.API.logger.log(
                    "failed to delete file: " + fileName
                    + ": error " + xhr.status
                )
            );
        }

        ///file/{user_id}/{project_id}/{reference_name}/{function_Type}
        static analyzeFile(userId, projectId, referenceName, functionType) {
            return AppUtils.makeRequest(
                "GET",
                "file/" + userId + "/" + projectId + "/" + referenceName + "/" + functionType,
                null,
                [],
                (xhr) => xhr.status == 200,
                (xhr) => {
                    AppUtils.API.logger.dir(xhr);
                    AppUtils.API.logger.log(
                        functionType + "(" + referenceName + ") = " +
                        xhr.responseText
                    );
                },
                (xhr) => AppUtils.API.logger.log("Error: " + xhr.status)
            );
        }

        static runScript(userId, projectId, typeOfResult, name, script) {
            return AppUtils.makeRequest(
                "POST",
                userId + "/" + projectId + "/run/script",
                script,
                [
                    {
                        name: "type_of_call",
                        data: "RUN"
                    },
                    {
                        name: "type_of_result",
                        data: typeOfResult
                    },
                    {
                        name: "name",
                        data: name
                    }
                ],
                (xhr) => xhr.status == 202,
                (xhr) => AppUtils.API.logger.log("Success: " + xhr.responseText),
                (xhr) => AppUtils.API.logger.log("Error: " + xhr.status)
            );
        };

        static uploadFile(self, file, sp, l) {
            var formData = new FormData();
            formData.append("file", file);

            return AppUtils.makeRequest(
                "POST",
                "upload/" + (sp.login == "guest" ? 1 : sp.login) + "/" + sp.projectId,
                formData,
                [],
                (xhr) => xhr.status == 200,
                (xhr) => {
                    var extractedFile = AppUtils.extractFileFromXHR(xhr);
                    if (extractedFile.name != null) {
                        extractedFile.name = file.name
                        self._fileService.setSelectedFile(
                            extractedFile
                        );
                        self._fileService.getSelectedFile()
                            .then(sFile => self.selectedFile = sFile);

                        self._fileService.updateFileInfo();
                        if (self.selectedFile.content) {
                            self.Data = JSON.parse(self.selectedFile.content).Data;
                        }
                        l.dir(self.selectedFile);

                        l.log("success");
                        self._fileService.addFile(self.selectedFile);
                        try {
                            HighchartsUtils.DrawGraph(self.Data);
                        } catch (e) {
                            l.error("can't draw graphic [possibly, wrong data]", e);
                        } finally {
                        }
                        
                    }

                },
                (xhr) => l.log("error " + xhr.status)
            );
        }
    }
}

