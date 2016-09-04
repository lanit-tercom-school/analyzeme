(function(app) {
  /* global ng */

  app.FooterComponent =
    ng.core.Component({
      "selector" : 'app-footer',
      "templateUrl" : app.AppUtils.templateUrl("footer"),
      "directives" : [ng.router.ROUTER_DIRECTIVES]
    })
    .Class({
        constructor : function FooterComponent() {}
    });
})(window.app || (window.app = {}));
