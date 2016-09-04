'use strict';
(function(app) {
  /* global ng */

  app.NavigationComponent =
    ng.core.Component({
      "selector" : 'navigation',
      "templateUrl" : app.AppUtils.templateUrl("navigation"),
      //"styleUrls" : [app.AppUtils.cssUrls("app")],
      "directives" : [ng.router.ROUTER_DIRECTIVES]
    })
    .Class({
        constructor : function NavigationComponent() {
        }
    });
})(window.app || (window.app = {}));
