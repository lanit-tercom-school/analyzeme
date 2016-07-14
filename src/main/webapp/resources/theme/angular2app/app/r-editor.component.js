"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};
var core_1 = require("@angular/core");
var router_1 = require("@angular/router");
var app_utils_1 = require("./app-utils");
var workspace_service_1 = require("./workspace.service");
var REditorComponent = (function () {
    function REditorComponent(workspaceService, router) {
        this.workspaceService = workspaceService;
        this.router = router;
        this.editor = null;
        this.isSelectExpanded = false;
        this.workspaceService.session.autorun = false;
        document.title = "AnalyzeMe | r-editor";
    }
    REditorComponent.prototype.ngAfterViewInit = function () {
        app_utils_1.AppUtils.MDL.upgradeClasses(["mdl-js-button", "mdl-js-radio"]);
        this.editor = ace.edit("editor");
        this.editor.getSession().setMode("ace/mode/r");
        this.editor.setValue(this.workspaceService.session.script, -1);
        this.editor.focus();
    };
    REditorComponent.prototype.ngOnDestroy = function () {
        this.saveScript();
    };
    REditorComponent.prototype.saveScript = function () {
        this.workspaceService.session.script = this.editor.getValue();
    };
    REditorComponent.prototype.runScript = function () {
        this.saveScript();
        this.workspaceService.session.autorun = true;
        this.router.navigate(['Plot']);
    };
    REditorComponent.prototype.setReturnType = function (returnType) {
        var a = this.workspaceService.availableFunctions;
        a.splice(0, 0, a.splice(1, 1)[0]);
        this.isSelectExpanded = !this.isSelectExpanded;
        this.workspaceService.session.returnType = returnType;
    };
    REditorComponent = __decorate([
        core_1.Component({
            selector: 'r-editor',
            templateUrl: app_utils_1.AppUtils.templateUrl("r-editor"),
        }), 
        __metadata('design:paramtypes', [workspace_service_1.WorkspaceService, router_1.Router])
    ], REditorComponent);
    return REditorComponent;
}());
exports.REditorComponent = REditorComponent;
//# sourceMappingURL=r-editor.component.js.map