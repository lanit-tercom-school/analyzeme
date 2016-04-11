(function(app) {
  /* global ng */
  
  app.OupsComponent =
    ng.core.Component({
      "selector" : 'my-oups',
      "template" : `
         <h2>Something goes wrong</h2><br />
         We suggest you to go <button (click)="goBack()">BACK</button>
      `,
      "styleUrls" : ["app/css/hero-detail.component.css"]
    })
    .Class({
      constructor : function() {},
      goBack : function() {
          window.history.back();
          window.history.back();
       }
    });
})(window.app || (window.app = {}));
