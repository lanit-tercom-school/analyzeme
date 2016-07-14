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
var app_utils_1 = require("./app-utils");
var workspace_service_1 = require("./workspace.service");
var WorkAreaComponent = (function () {
    function WorkAreaComponent(workspaceService) {
        this.workspaceService = workspaceService;
        this.workspaceService.session.autorun = false;
        document.title = "AnalyzeMe | r-editor";
    }
    WorkAreaComponent.prototype.ngAfterViewInit = function () {
        app_utils_1.AppUtils.MDL.upgradeClasses(["mdl-js-button"]);
    };
    WorkAreaComponent = __decorate([
        core_1.Component({
            selector: 'r-editor',
            templateUrl: app_utils_1.AppUtils.templateUrl("r-editor"),
        }), 
        __metadata('design:paramtypes', [workspace_service_1.WorkspaceService])
    ], WorkAreaComponent);
    return WorkAreaComponent;
}());
exports.WorkAreaComponent = WorkAreaComponent;
//# sourceMappingURL=work-area.component.js.map