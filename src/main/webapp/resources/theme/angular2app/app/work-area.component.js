'use strict';
(function(app) {
    /* global ng */
    var l = app.AppUtils.logger("work-area.component");

    app.WorkAreaComponent =
        ng.core.Component({
            "selector": 'work-area',
            "templateUrl": app.AppUtils.templateUrl("work-area"),
            "directives": [ng.router.ROUTER_DIRECTIVES],
            "providers": [app.WorkspaceService]
        })
        .Class({
            constructor: [app.WorkspaceService, function WorkAreaComponent(wss) {
              this._workspaceService = wss;
            }],
            ngAfterViewInit: function() {
                app.AppUtils.MDL.upgradeClasses(["mdl-js-button"]);
            }
        });

    ng.router.RouteConfig([{
        path: '/plot',
        name: 'Plot',
        component: app.PlotComponent,
        useAsDefault: true
    }, {
        path: '/REdit',
        name: 'REdit',
        component: app.REditorComponent,
        useAsDefault: false
    }])(app.WorkAreaComponent);
})(window.app || (window.app = {}));
