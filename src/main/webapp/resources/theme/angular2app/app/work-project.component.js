'use strict';
(function(app) {
  /* global ng */
  var l = app.AppUtils.logger("work-project.component");

  app.WorkProjectComponent =
    ng.core.Component({
      "selector" : 'work-project',
      "templateUrl" : app.AppUtils.templateUrl("work-project"),
      "directives" : [app.FilesListComponent],
      "providers" : [app.FileService]
      /*"templateUrl" : "app/templates/work-project.component.html",
      "styleUrls" : ["app/css/work-project.component.css"]*/
    })
    .Class({
        constructor : [
            app.ProjectService,
            app.FileService,
            ng.router.RouteParams,
            function WorkProjectComponent(projectService, fileService, routeParams) {
                this._projectService = projectService;
                this._fileService = fileService;
                this._routeParams = routeParams;
                this.project = null;
                this.svgSize = 500;
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
            }
        ],
        ngOnInit: function() {
            this.loadContent();
        },
        loadContent : function() {
            l.log("loadContent begins");
            let id = this._routeParams.get("id");
            this._projectService.getProject(id)
                .then(
                    project => {
                      this.project = project;
                      this._projectService.setSelectedProject(project);
                    },
                    reject =>
                        this.project = {
                            projectName : "Failed to load\n"
                            + "Can not load project \""
                            + id + "\"(" + reject + "). Check name plz"
                            }
                );
            l.log("loadContent ends");
        },
        applyFunction: function() {
            var fileName = this._fileService.getServerName();
            var functionType = document.getElementById("functionSelect").value;
            var resultOutput = document.getElementById("functionResult");
            resultOutput.value = "Analyzing...";
            var xhr = app.AppUtils.API.analyzeFile(fileName, functionType);
            xhr.then(
              (xhr) => {
                resultOutput.value =
                    xhr.getResponseHeader("value");
              },
              (err) => {
                resultOutput.value = "Can't evaluate: " + err;
              }
            );
        }
    });
        /*
    ng.router.RouteConfig([
    {
      path : '/list',
      name : 'ProjectPage',
      component : app.ProjectPage,
      useAsDefault : false
    }
  ])(app.WorkProjectComponent);*/
})(window.app || (window.app = {}));
