(function(app) {
  /* global ng */

  app.AppComponent =
    ng.core.Component({
      "selector" : 'my-app',
      "templateUrl" : app.AppUtils.templateUrl("app"),
      "styleUrls" : [app.AppUtils.cssUrls("app")],
      "directives" : [ng.router.ROUTER_DIRECTIVES, app.NavigationComponent, app.FooterComponent],
      "providers" : [ng.router.ROUTER_PROVIDERS, app.ProjectService]
    })
    .Class({
        constructor : [app.ProjectService, ng.router.Router,  function AppComponent(projectService, router) {
            this._router = router;
            this._projectService = projectService;
        }]
    });

    ng.router.RouteConfig([
    {
        path : '/',
        redirectTo : ['ProjectsList'],
        useAsDefault : false
    },
    {
      path : '/projectsList',
      name : 'ProjectsList',
      component : app.ProjectPageComponent,
      useAsDefault : true
    },
    {
      path : '/:id',
      name : 'WorkProject',
      component : app.WorkProjectComponent,
      useAsDefault : false
    }/*,
    {
      path : '/*wildcard',
      name : 'OupsPage',
      component : app.OupsComponent,
      useAsDefault : false
    }*/
  ])(app.AppComponent);
})(window.app || (window.app = {}));
