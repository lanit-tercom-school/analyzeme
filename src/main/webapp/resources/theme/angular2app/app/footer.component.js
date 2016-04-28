(function(app) {
  /* global ng */

  app.FooterComponent =
    ng.core.Component({
      "selector" : 'footer',
      "templateUrl" : app.AppUtils.templateUrl("footer")
    })
    .Class({
        constructor : function() {}
    });
})(window.app || (window.app = {}));
