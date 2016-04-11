(function(app) {
    app.AppUtils = {};

    app.AppUtils.domens = [
        "http://localhost:8080/",
        "http://analyzeme-dev.herokuapp.com/"
    ];
    app.AppUtils.resolveUrl = function(relativePath) {
        return app.AppUtils.domens[1] + relativePath;
    };

    app.AppUtils.pathToApp = app.AppUtils.resolveUrl("resources/angular2app/");

    app.AppUtils.templateUrl = function(componentName) {
      return app.AppUtils.pathToApp + "app/templates/" +
             componentName + ".component.html";
    };
    // BUG: Angular 2 don't allow absolute paths in cssUrls
    // hotfix needs to be redone
    app.AppUtils.cssUrls = function(componentName) {
      return "../" + "resources/angular2app/" + "app/css/" +
             componentName + ".component.css";
    };

    app.AppUtils.logger = function(name) {
        return {
            _name: name,
            _level: 0,
            log: function(msg) {
                if (this._level > 0) return;
                window.console.log(new Date().toLocaleTimeString() +
                    "] " + this._name + ">> " + msg);
            },
            dir: function(obj) {
                if (this._level > 1) return;
                this.log("dir-begin--------\\");
                window.console.dir(obj);
                this.log("dir--end---------/");
            },
            error: function(msg, e) {
                if (this._level > 3) return;
                window.console.error(new Date().toLocaleTimeString() +
                    "] " + this._name + ">> " + msg);
                window.console.error(e);
            },
            setLevel: function(level) {
                this._level = level;
            }
        };
    };

    app.AppUtils.copyObj = function(src, dest) {
        for (property in src) {
            dest[property] = src[property];
        }
    };
    app.AppUtils.findByKey = function(keyName, arr, key) {
        var res = [];
        if (arr) {
          for (elem of arr) {
              if (elem[keyName] == key) {
                  res.push(elem);
              }
          }
        }
        return res;
    };

    app.AppUtils.extractFileFromXHR = function(xhr) {
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

    app.AppUtils.API = {};
    app.AppUtils.API.logger = app.AppUtils.logger("AppUtils::API");

    app.AppUtils.API.createProject = function(projectName) {
        return new Promise((resolve, reject) => {
            var xhr = new XMLHttpRequest();
            xhr.onload = xhr.onerror = function(event) {
              if (xhr.status == 200) {
                  app.AppUtils.API.logger.log("created project " + projectName);
                  resolve(xhr);
              } else {
                  app.AppUtils.API.logger.log(
                      "failed create project " + projectName
                      + ": error " + xhr.status
                  );
                  reject("error " + xhr.status);
              }
            };
            xhr.open("PUT", app.AppUtils.resolveUrl("/project/new/create"), true);
            xhr.setRequestHeader("project_name", projectName);
            xhr.send();
        })
    };

    app.AppUtils.API.deleteProject = function(projectId) {
        return new Promise((resolve, reject) => {
            var xhr = new XMLHttpRequest();
            xhr.onload = xhr.onerror = function(event) {
              if (xhr.status == 200) {
                  app.AppUtils.API.logger.log("deleted project " + projectId);
                  resolve(xhr);
              } else {
                  app.AppUtils.API.logger.log(
                      "failed delete project " + projectId
                      + ": error " + xhr.status
                  );
                  reject("error " + xhr.status);
              }
            };
            xhr.open("DELETE",
                app.AppUtils.resolveUrl("/project/" + projectId + "/delete"),
                true
            );
            xhr.send();
        });
    };

    // returns array of fileames
    // extract: JSON.parse(xhr.responseText)
    app.AppUtils.API.getProjectFiles = function(projectName) {
        return new Promise((resolve, reject) => {
            var xhr = new XMLHttpRequest();
            xhr.onload = xhr.onerror = function(event) {
              if (xhr.status == 200) {
                  app.AppUtils.API
                    .logger.log("got files of project " + projectName);
                  resolve(xhr);
              } else {
                  app.AppUtils.API.logger.log(
                      "failed to get files of project " + projectName
                      + ": error " + xhr.status
                  );
                  reject("error " + xhr.status);
              }
            };
            xhr.open("GET",
                app.AppUtils.resolveUrl("project/" + projectName + "/files"),
                true
            );
            xhr.send();
        });
    };

    // returns file data
    // extract: JSON.parse(xhr.responseText).Data
    app.AppUtils.API.getFileData = function(fileName) {
        return new Promise((resolve, reject) => {
            var xhr = new XMLHttpRequest();
            xhr.onload = xhr.onerror = function(event) {
              if (xhr.status == 200) {
                  app.AppUtils.API
                    .logger.log("got file's data: " + fileName);
                  resolve(xhr);
              } else {
                  app.AppUtils.API.logger.log(
                      "failed to get file's data: " + fileName
                      + ": error " + xhr.status
                  );
                  reject("error " + xhr.status);
              }
            };
            xhr.open("GET",
                app.AppUtils.resolveUrl("file/" + fileName + "/data"),
                true
            );
            xhr.send();
        });
    };

    app.AppUtils.API.analyzeFile = function(fileName, functionType) {
        return new Promise((resolve, reject) => {
            var xhr = new XMLHttpRequest();
            xhr.open("GET",
                app.AppUtils.resolveUrl(
                    "file/" + fileName + "/" + functionType
                ),
                true);
            xhr.onload = xhr.onerror = function(event) {
                if (this.status == 200) {
                    app.AppUtils.API.logger.dir(this);
                    app.AppUtils.API.logger.log(
                        functionType + "(" + fileName + ") = " +
                        xhr.getResponseHeader("value")
                    );
                    resolve(xhr);
                } else {
                    app.AppUtils.API.logger.log("error " + xhr.status);
                    reject("error " + xhr.status);
                }
            };
            xhr.send();
        });
    };
    /*
    //TODO
    app.AppUtils.API.uploadFile = function(file) {
        return new Promise((resolve, reject) => {
            var xhr = new XMLHttpRequest();

            xhr.send();
        });
    };
    */
})(window.app || (window.app = {}));
