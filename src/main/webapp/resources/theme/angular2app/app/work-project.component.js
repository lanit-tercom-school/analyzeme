'use strict';
(function(app) {
  /* global ng */
  var l = app.AppUtils.logger("work-project.component");

  app.WorkProjectComponent =
    ng.core.Component({
      "selector" : 'work-project',
      "templateUrl" : app.AppUtils.templateUrl("work-project"),
      "directives" : [ng.router.ROUTER_DIRECTIVES, app.FilesListComponent, app.WorkAreaComponent],
      "providers" : [app.FileService]
      /*"templateUrl" : "app/templates/work-project.component.html",
      "styleUrls" : ["app/css/work-project.component.css"]*/
    })
    .Class({
        constructor : [
            app.ProjectService,
            app.FileService,
            ng.router.RouteParams,
            ng.router.Router,
            function WorkProjectComponent(projectService, fileService, routeParams, router) {
                this._projectService = projectService;
                this._fileService = fileService;
                this._routeParams = routeParams;
                this._router = router;
                this.project = null;
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
                    reject => this._router.navigate(['OupsPage'])
                        /*this.project = {
                            projectName : "Failed to load\n"
                            + "Can not load project \""
                            + id + "\"(" + reject + ")."
                            + "All files will be uploaded in default project"
                          }*/
                );
            l.log("loadContent ends");
        }
    });

    ng.router.RouteConfig([
    {
      path : '/...',
      name : 'WorkArea',
      component : app.WorkAreaComponent,
      useAsDefault : true
    }
  ])(app.WorkProjectComponent);
})(window.app || (window.app = {}));
