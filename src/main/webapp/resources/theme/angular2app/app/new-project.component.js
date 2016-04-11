'use strict';
(function(app) {
  /* global ng */

  app.NewProjectComponent =
    ng.core.Component({
      "selector" : 'new-project',
      "templateUrl" : app.AppUtils.templateUrl("new-project"),
      "styleUrls" : [app.AppUtils.cssUrls("new-project")],
      "directives" : [ng.router.ROUTER_DIRECTIVES]
    })
    .Class({
        constructor : [ng.router.Router, app.ProjectService, function NewProjectComponent(router, projectService) {
            this._router = router;
            this._projectService = projectService;
            this.selectedProject = null;
        }],
        ngOnInit : function() {
            this.selectedProject = this._projectService.selectedProject;
        },
        getProps: function(src) {
            let res = [];
              for (let property in src)
              {
                  res[res.length] = property;
              }
              return res;
          },
        gotoProject : function(project) {
            let link = ['WorkProject', { "id" : this.selectedProject.projectId }];
            this._router.parent.navigate(link);
        },
        isEmpty: function() {
            return this._projectService.equals(this.selectedProject, {});
        }
    });
})(window.app || (window.app = {}));
