(function(app) {
    app.AppUtils = {};

    app.AppUtils.domens = [
        "http://localhost:8080/",
        "http://analyzeme-dev.herokuapp.com/",
        "http://192.168.1.126:8080/",
        "/"
    ];
    app.AppUtils.resolveUrl = function(relativePath) {
        return app.AppUtils.domens[3] + relativePath;
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

    app.AppUtils.MDL = {};

    app.AppUtils.MDL.upgradeClasses = function(classes) {
      var els = [];
      classes.forEach(
        (c) => [].forEach.call(document.getElementsByClassName(c), (e) => els.push(e))
      );
      componentHandler.upgradeElements(els);
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

    app.AppUtils.makeRequest = function(method, path, body, headers, successChecker, callbackSuccess, callbackError) {
      return new Promise((resolve, reject) => {
          var xhr = new XMLHttpRequest();
          xhr.onload = xhr.onerror = function(event) {
            if (successChecker(xhr)) {
                callbackSuccess(xhr);
                resolve(xhr);
            } else {
                callbackError(xhr);
                reject("error " + xhr.status);
            }
          };
          xhr.open(method, app.AppUtils.resolveUrl(path), true);
          if (headers) {
            for (header of headers) {
                xhr.setRequestHeader(header.name, header.data);
            }
          }
          xhr.send(body);
      });
    };

    app.AppUtils.testRequest = (method, path) =>
        app.AppUtils.makeRequest(method, path, null, [],
          (xhr) => xhr.status == 200,
          (xhr) => window.console.dir(xhr),
          (xhr) => window.console.dir(xhr)
        );

    app.AppUtils.API.createProject = function(userId, projectName) {
        return app.AppUtils.makeRequest(
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
          (xhr) => app.AppUtils.API.logger.log(userId + " created project " + projectName),
          (xhr) => app.AppUtils.API.logger.log(
              userId + " failed create project " + projectName
              + ": error " + xhr.status
          )
        );
    };

    app.AppUtils.API.deleteProject = function(userId, projectId) {
        return app.AppUtils.makeRequest(
          "DELETE",
          userId + "/project/" + projectId + "/delete",
          null,
          [],
          (xhr) => xhr.status == 200,
          (xhr) => app.AppUtils.API.logger.log(userId + " deleted project " + projectId),
          (xhr) => app.AppUtils.API.logger.log(
              userId + " failed delete project " + projectId
              + ": error " + xhr.status
          )
        );
    };

    // returns array of fileames
    // extract: JSON.parse(xhr.responseText)
    app.AppUtils.API.getProjectFiles = function(userId, projectId) {
        return app.AppUtils.makeRequest(
          "GET",
          userId + "/project/" + projectId + "/files",
          null,
          [],
          (xhr) => xhr.status == 200,
          (xhr) => app.AppUtils.API.logger.log(userId + " got files of project " + projectId),
          (xhr) => app.AppUtils.API.logger.log(
              userId + " failed to get files of project " + projectId
              + ": error " + xhr.status
          )
        );
    };

    // returns file data
    // extract: JSON.parse(xhr.responseText).Data
    app.AppUtils.API.getFileData = function(fileName) {
        return app.AppUtils.makeRequest(
          "GET",
          "file/" + fileName + "/data",
          null,
          [],
          (xhr) => xhr.status == 200,
          (xhr) => app.AppUtils.API.logger.log("got file's data: " + fileName),
          (xhr) => app.AppUtils.API.logger.log(
              "failed to get file's data: " + fileName
              + ": error " + xhr.status
          )
        );
    };

    app.AppUtils.API.deleteFile = function(fileName) {
        return app.AppUtils.makeRequest(
          "DELETE",
          "file/" + fileName + "/delete",
          null,
          [],
          (xhr) => xhr.status == 200,
          (xhr) => app.AppUtils.API.logger.log("deleted file: " + fileName),
          (xhr) => app.AppUtils.API.logger.log(
              "failed to delete file: " + fileName
              + ": error " + xhr.status
          )
        );
    };

    app.AppUtils.API.analyzeFile = function(fileName, functionType) {
        return app.AppUtils.makeRequest(
          "GET",
          "file/" + fileName + "/" + functionType,
          null,
          [],
          (xhr) => xhr.status == 200,
          (xhr) => {
            app.AppUtils.API.logger.dir(xhr);
            app.AppUtils.API.logger.log(
                functionType + "(" + fileName + ") = " +
                xhr.responseText
            );
          },
          (xhr) => app.AppUtils.API.logger.log("Error: " + xhr.status)
        );
    };

    app.AppUtils.API.runScript = function(userId, projectId, typeOfResult, name, script) {
      return app.AppUtils.makeRequest(
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
        (xhr) => app.AppUtils.API.logger.log("Success: " + xhr.responseText),
        (xhr) => app.AppUtils.API.logger.log("Error: " + xhr.status)
      );
    };

    app.AppUtils.API.uploadFile = function(self, file, sp, l) {
      var formData = new FormData();
      formData.append("file", file);

      return app.AppUtils.makeRequest(
        "POST",
        "upload/" + (sp.login == "guest" ? 1 : sp.login) + "/" + sp.projectId,
        formData,
        [],
        (xhr) => xhr.status == 200,
        (xhr) => {
          self._fileService.setSelectedFile(
              app.AppUtils.extractFileFromXHR(xhr)
          );
          self._fileService.getSelectedFile()
              .then(sFile => self.selectedFile = sFile);
          if (self.selectedFile.content) {
              self.Data = JSON.parse(self.selectedFile.content).Data;
          }
          l.dir(self.selectedFile);
          //
          l.log("success");
          self._fileService.addFile(self.selectedFile);
          try {
              app.d3Utils.DrawGraph(self.Data);
          } catch (e) {
              l.error("can't draw graphic [possibly, wrong data]", e);
          } finally {};
        },
        (xhr) => l.log("error " + xhr.status)
      );
    };
})(window.app || (window.app = {}));
