'use strict';
(function (app) {
    /* global ng */
    var l = app.AppUtils.logger("workspace.service");

    app.WorkspaceService = ng.core
        .Class({
            constructor: [app.ProjectService, app.FileService, function WorkspaceService(projectService, fileService) {
                this.session = {
                    autorun: false,
                    returnType: "DOUBLE",
                    script: `# Define a variable.
x <- rnorm(10)

# calculate the mean of x
mean(x)`
                };

                this.returnTypes = [
                    "DOUBLE",
                    "SCALAR",
                    "JSON_STRING",
                    "VECTOR",
                    "FILE"
                ];

                this.availableFunctions = [];
                this.getFunctions();

                this.availableFunctions.push({
                    func: "UserScript"
                });

                this._projectService = projectService;
                this._fileService = fileService;
            }],
            getFunctions: function () {
                l.log("getFunctions()");
                var functions = null;
                app.AppUtils.API.getFunctions()
                    .then(
                        (xhr) => {
                            return xhr.responseText ? JSON.parse(xhr.responseText) : [];
                        },
                        (err) => {
                            l.log("Failed load functions: " + err);
                        }
                    )
                    .then(
                        (functions) => {
                            l.dir(functions);
                            if (functions) {
                                for (let func of functions) {
                                    if (func) {
                                        l.log("func in functions");
                                        this.availableFunctions.push({"func": func});
                                    }
                                }
                            }
                        });
            }
        })

})(window.app || (window.app = {}));
