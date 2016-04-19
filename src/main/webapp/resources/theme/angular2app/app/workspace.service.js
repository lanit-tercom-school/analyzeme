'use strict';
(function(app) {
    /* global ng */
    var l = app.AppUtils.logger("workspace.service");

    app.WorkspaceService = ng.core
        .Class({
            constructor: [app.ProjectService, app.FileService, function WorkspaceService(projectService, fileService) {
                this.session = {
                  script: "//empty script"
                };

                this.availableFunctions = [
                   {
                      func: "GlobalMinimum",
                      name: "Global minimum"
                   },
                   {
                       func: "GlobalMaximum",
                       name: "Global maximum"
                   }
                ];
                this.availableFunctions.push({
                    func: "UserScript",
                    name: "Your script"
                });

                this._projectService = projectService;
                this._fileService = fileService;
            }]
        });

})(window.app || (window.app = {}));
