(function(app) {
  /* global ng */

  app.ProjectPageComponent =
    ng.core.Component({
      "selector" : 'project-page',
      "templateUrl" : app.AppUtils.templateUrl("project-page")/*,
      "styleUrls" : ["app/css/project-page.component.css"]*/,
      "directives" : [ng.router.ROUTER_DIRECTIVES,  app.ProjectsListComponent, app.NewProjectComponent]
    })
    .Class({
      constructor : function ProjectPageComponent(){
          document.title = "AnalyzeMe | Projects";
      }
    });

    /*ng.router.RouteConfig([
    {
      path : '/projectsList',
      name : 'ProjectsList',
      component : app.NewProjectComponent,
      useAsDefault : true
    },
    {
      path : '/:id',
      name : 'WorkProject',
      component : app.WorkProjectComponent,
      useAsDefault : false
    }
  ])(app.ProjectPageComponent);*/
})(window.app || (window.app = {}));
