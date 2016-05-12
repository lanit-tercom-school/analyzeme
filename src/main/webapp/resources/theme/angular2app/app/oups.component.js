(function(app) {
  /* global ng */

  app.OupsComponent =
    ng.core.Component({
      "selector" : 'my-oups',
      "template" : `
        <div align="center">
            <h2>Something goes wrong</h2>
            <br/>
            <h4>
                We suggest you to go
                <br/>
                <br/>
                <button (click)="goBack()"
                    class="mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect">
                    BACK
                </button>
            </h4>
        </div>
      `
    })
    .Class({
      constructor : function() {
          document.title = "AnalyzeMe | Oups :c";
      },
      ngAfterViewInit: function() {
          app.AppUtils.MDL.upgradeClasses(["mdl-js-button"]);
      },
      goBack : function() {
          window.history.go(-3);
      }
    });
})(window.app || (window.app = {}));
