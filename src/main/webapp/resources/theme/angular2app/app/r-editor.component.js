'use strict';
(function(app) {
    /* global ng */

    app.REditorComponent =
        ng.core.Component({
            "selector": 'r-editor',
            "templateUrl": app.AppUtils.templateUrl("r-editor")
        })
        .Class({
            constructor: [
              app.WorkspaceService,
              ng.router.Router,
              function REditorComponent(wss, router) {
                this._workspaceService = wss;
                this._router = router;
                this.editor = null;
              }
            ],
            ngAfterViewInit: function() {
              app.AppUtils.MDL.upgradeClasses(["mdl-js-button"]);
              this.editor = ace.edit("editor");
              this.editor.getSession().setMode("ace/mode/r");
              this.editor.setValue(this._workspaceService.session.script, -1);
              this.editor.focus();
            },
            saveScript: function() {
              this._workspaceService.session.script = this.editor.getValue();
              this._workspaceService.session.autorun = false;
            },
            runScript: function() {
              this.saveScript();
              this._workspaceService.session.autorun = true;
              this._router.navigate(['Plot']);
            }
        });
})(window.app || (window.app = {}));
