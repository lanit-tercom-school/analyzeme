"use strict";
var highcharts_utils_1 = require("./highcharts-utils");
var AppUtils = (function () {
    function AppUtils() {
    }
    AppUtils.resolveUrl = function (relativePath) {
        return AppUtils.domens[3] + relativePath;
    };
    ;
    AppUtils.templateUrl = function (componentName) {
        return AppUtils.pathToApp + "app/templates/" +
            componentName + ".component.html";
    };
    ;
    // BUG: Angular 2 don't allow absolute paths in cssUrls
    AppUtils.cssUrls = function (componentName) {
        return "../" + AppUtils.pathToApp + "app/css/" +
            componentName + ".component.css";
    };
    ;
    AppUtils.logger = function (name) {
        return {
            _name: name,
            _level: 0,
            log: function (msg) {
                if (this._level > 0)
                    return;
                window.console.log(new Date().toLocaleTimeString() +
                    "] " + this._name + ">> " + msg);
            },
            dir: function (obj) {
                if (this._level > 1)
                    return;
                this.log("dir-begin--------\\");
                window.console.dir(obj);
                this.log("dir--end---------/");
            },
            error: function (msg, e) {
                if (this._level > 3)
                    return;
                window.console.error(new Date().toLocaleTimeString() +
                    "] " + this._name + ">> " + msg);
                window.console.error(e);
            },
            setLevel: function (level) {
                this._level = level;
            }
        };
    };
    ;
    AppUtils.copyObj = function (src, dest) {
        for (var property in src) {
            dest[property] = src[property];
        }
    };
    ;
    AppUtils.findByKey = function (keyName, arr, key) {
        var res = [];
        if (arr) {
            for (var _i = 0, arr_1 = arr; _i < arr_1.length; _i++) {
                var elem = arr_1[_i];
                if (elem[keyName] == key) {
                    res.push(elem);
                }
            }
        }
        return res;
    };
    ;
    AppUtils.extractFileFromXHR = function (xhr) {
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
    ;
    AppUtils.makeRequest = function (method, path, body, headers, successChecker, callbackSuccess, callbackError) {
        return new Promise(function (resolve, reject) {
            var xhr = new XMLHttpRequest();
            xhr.onload = xhr.onerror = function (event) {
                if (successChecker(xhr)) {
                    callbackSuccess(xhr);
                    resolve(xhr);
                }
                else {
                    callbackError(xhr);
                    reject("error " + xhr.status);
                }
            };
            xhr.open(method, AppUtils.resolveUrl(path), true);
            if (headers) {
                for (var _i = 0, headers_1 = headers; _i < headers_1.length; _i++) {
                    var header = headers_1[_i];
                    xhr.setRequestHeader(header.name, header.data);
                }
            }
            xhr.send(body);
        });
    };
    ;
    AppUtils.testRequest = function (method, path) {
        AppUtils.makeRequest(method, path, null, [], function (xhr) { return xhr.status == 200; }, function (xhr) { return window.console.dir(xhr); }, function (xhr) { return window.console.dir(xhr); });
    };
    AppUtils.domens = [
        "http://localhost:8080/",
        "http://analyzeme-dev.herokuapp.com/",
        "http://192.168.1.126:8080/",
        "/"
    ];
    AppUtils.pathToApp = AppUtils.resolveUrl("resources/angular2app/");
    return AppUtils;
}());
exports.AppUtils = AppUtils;
var AppUtils;
(function (AppUtils) {
    var MDL = (function () {
        function MDL() {
        }
        MDL.upgradeClasses = function (classes) {
            var els = [];
            classes.forEach(function (c) { return [].forEach.call(document.getElementsByClassName(c), function (e) { return els.push(e); }); });
            componentHandler.upgradeElements(els);
        };
        return MDL;
    }());
    AppUtils.MDL = MDL;
    var API = (function () {
        function API() {
        }
        API.makeRequest = function (method, path, body, headers, successChecker, callbackSuccess, callbackError) {
            return new Promise(function (resolve, reject) {
                var xhr = new XMLHttpRequest();
                xhr.onload = xhr.onerror = function (event) {
                    if (successChecker(xhr)) {
                        callbackSuccess(xhr);
                        resolve(xhr);
                    }
                    else {
                        callbackError(xhr);
                        reject("error " + xhr.status);
                    }
                };
                xhr.open(method, AppUtils.resolveUrl(path), true);
                if (headers) {
                    for (var _i = 0, headers_2 = headers; _i < headers_2.length; _i++) {
                        var header = headers_2[_i];
                        xhr.setRequestHeader(header.name, header.data);
                    }
                }
                xhr.send(body);
            });
        };
        API.createProject = function (userId, projectName) {
            return AppUtils.makeRequest("PUT", userId + "/project/new/create", null, [
                {
                    name: "project_name",
                    data: projectName
                }
            ], function (xhr) { return xhr.status == 200; }, function (xhr) { return AppUtils.API.logger.log(userId + " created project " + projectName); }, function (xhr) { return AppUtils.API.logger.log(userId + " failed create project " + projectName
                + ": error " + xhr.status); });
        };
        API.deleteProject = function (userId, projectId) {
            return AppUtils.makeRequest("DELETE", userId + "/project/" + projectId + "/delete", null, [], function (xhr) { return xhr.status == 200; }, function (xhr) { return AppUtils.API.logger.log(userId + " deleted project " + projectId); }, function (xhr) { return AppUtils.API.logger.log(userId + " failed delete project " + projectId
                + ": error " + xhr.status); });
        };
        // returns { "Files" : [{"uniqueName":"0_10.json","nameForUser":"0_10.json"},{"uniqueName":"check_1.json","nameForUser":"check.json"},{"uniqueName":"lineal.json","nameForUser":"lineal.json"}]}
        // extract: JSON.parse(xhr.responseText)
        API.getProjectFiles = function (userId, projectId) {
            return AppUtils.makeRequest("GET", userId + "/project/" + projectId + "/filesForList", null, [], function (xhr) { return xhr.status == 200; }, function (xhr) { return AppUtils.API.logger.log(userId + " got files of project " + projectId); }, function (xhr) { return AppUtils.API.logger.log(userId + " failed to get files of project " + projectId
                + ": error " + xhr.status); });
        };
        // returns {"uniqueName":"0_10.json","nameForUser":"0_10.json","isActive":"true","fields":[{"fieldName":"x","fieldId":"x"},{"fieldName":"y","fieldId":"y"}],"uploadingDate":"Wed Apr 20 18:25:28 MSK 2016"}
        // extract: JSON.parse(xhr.responseText)
        //file/{user_id}/{project_id}/{nameForUser}/getFullInfo
        API.getFullFileInfo = function (userId, projectId, filenameForUser) {
            return AppUtils.makeRequest("GET", "file/" + userId + "/" + projectId + '/' + filenameForUser + "/getFullInfo/", null, [], function (xhr) { return xhr.status == 200; }, function (xhr) { return AppUtils.API.logger.log(userId + " got files of project " + projectId); }, function (xhr) { return AppUtils.API.logger.log(userId + " failed to get FileInfo for file " + filenameForUser + " in project " + projectId
                + ": error " + xhr.status); });
        };
        // returns file data
        // extract: JSON.parse(xhr.responseText).Data
        API.getFileData = function (fileName) {
            return AppUtils.makeRequest("GET", "file/" + fileName + "/data", null, [], function (xhr) { return xhr.status == 200; }, function (xhr) { return AppUtils.API.logger.log("got file's data: " + fileName); }, function (xhr) { return AppUtils.API.logger.log("failed to get file's data: " + fileName
                + ": error " + xhr.status); });
        };
        ///GET: file/{user_id}/{project_id}/{reference_name}/data
        // returns file data
        // extract: JSON.parse(xhr.responseText).Data
        API.getFileDataFromDataSet = function (userId, projectId, referenceName) {
            return AppUtils.makeRequest("GET", "file/" + userId + "/" + projectId + "/" + referenceName + "/data", null, [], function (xhr) { return xhr.status == 200; }, function (xhr) { return AppUtils.API.logger.log("got file's data: " + referenceName); }, function (xhr) { return AppUtils.API.logger.log("failed to get file's data: " + referenceName
                + ": error " + xhr.status); });
        };
        API.deleteFile = function (fileName) {
            return AppUtils.makeRequest("DELETE", "file/" + fileName + "/delete", null, [], function (xhr) { return xhr.status == 200; }, function (xhr) { return AppUtils.API.logger.log("deleted file: " + fileName); }, function (xhr) { return AppUtils.API.logger.log("failed to delete file: " + fileName
                + ": error " + xhr.status); });
        };
        ///file/{user_id}/{project_id}/{reference_name}/{function_Type}
        API.analyzeFile = function (userId, projectId, referenceName, functionType) {
            return AppUtils.makeRequest("GET", "file/" + userId + "/" + projectId + "/" + referenceName + "/" + functionType, null, [], function (xhr) { return xhr.status == 200; }, function (xhr) {
                AppUtils.API.logger.dir(xhr);
                AppUtils.API.logger.log(functionType + "(" + referenceName + ") = " +
                    xhr.responseText);
            }, function (xhr) { return AppUtils.API.logger.log("Error: " + xhr.status); });
        };
        API.runScript = function (userId, projectId, typeOfResult, name, script) {
            return AppUtils.makeRequest("POST", userId + "/" + projectId + "/run/script", script, [
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
            ], function (xhr) { return xhr.status == 202; }, function (xhr) { return AppUtils.API.logger.log("Success: " + xhr.responseText); }, function (xhr) { return AppUtils.API.logger.log("Error: " + xhr.status); });
        };
        ;
        API.uploadFile = function (self, file, sp, l) {
            var formData = new FormData();
            formData.append("file", file);
            return AppUtils.makeRequest("POST", "upload/" + (sp.login == "guest" ? 1 : sp.login) + "/" + sp.projectId, formData, [], function (xhr) { return xhr.status == 200; }, function (xhr) {
                var extractedFile = AppUtils.extractFileFromXHR(xhr);
                if (extractedFile.name != null) {
                    extractedFile.name = file.name;
                    self._fileService.setSelectedFile(extractedFile);
                    self._fileService.getSelectedFile()
                        .then(function (sFile) { return self.selectedFile = sFile; });
                    self._fileService.updateFileInfo();
                    if (self.selectedFile.content) {
                        self.Data = JSON.parse(self.selectedFile.content).Data;
                    }
                    l.dir(self.selectedFile);
                    l.log("success");
                    self._fileService.addFile(self.selectedFile);
                    try {
                        highcharts_utils_1.HighchartsUtils.DrawGraph(self.Data);
                    }
                    catch (e) {
                        l.error("can't draw graphic [possibly, wrong data]", e);
                    }
                    finally {
                    }
                }
            }, function (xhr) { return l.log("error " + xhr.status); });
        };
        API.logger = AppUtils.logger("AppUtils::API");
        return API;
    }());
    AppUtils.API = API;
})(AppUtils = exports.AppUtils || (exports.AppUtils = {}));
//# sourceMappingURL=app-utils.js.map