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
                this.isSelectExpanded = false;
                this._workspaceService.session.autorun = false;
                document.title = "AnalyzeMe | R-editor";
              }
            ],
            ngAfterViewInit: function() {
              app.AppUtils.MDL.upgradeClasses(["mdl-js-button", "mdl-js-radio"]);
              this.editor = ace.edit("editor");
              this.editor.getSession().setMode("ace/mode/r");
              this.editor.setValue(this._workspaceService.session.script, -1);
              this.editor.focus();
            },
            ngOnDestroy: function() {
              this.saveScript();
            },
            saveScript: function() {
              this._workspaceService.session.script = this.editor.getValue();
            },
            runScript: function() {
              this.saveScript();
              this._workspaceService.session.autorun = true;
              this._router.navigate(['Plot']);
            },
            setReturnType: function(returnType) {
              let a = this._workspaceService.availableFunctions;
              a.splice(0, 0, a.splice(1,1)[0]);
              this.isSelectExpanded = !this.isSelectExpanded;
              this._workspaceService.session.returnType = returnType;
            }
        });
})(window.app || (window.app = {}));
