'use strict';
(function(app) {
  /* global ng */

  app.HeroDetailComponent =
    ng.core.Component({
      "selector" : 'my-hero-detail',
      "templateUrl" : app.AppUtils.templateUrl("hero-detail"),
      "styleUrls" : [app.AppUtils.cssUrls("hero-detail")]
    })
    .Class({
        constructor : [
          app.HeroService,
          ng.router.RouteParams,
          function(heroService, routeParams) {
            this._heroService = heroService;
            this._routeParams = routeParams;
            this.hero = null;
          }
        ],
        ngOnInit : function() {
          let id = this._routeParams.get('id');
          this._heroService.getHero(id)
            .then(hero => this.hero = hero);
        },
        goBack : function() {
          window.history.back();
        }
    });
})(window.app || (window.app = {}));
