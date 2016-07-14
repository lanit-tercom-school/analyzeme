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
var router_deprecated_1 = require("@angular/router-deprecated");
var app_utils_1 = require("./app-utils");
var file_service_1 = require("./file.service");
var project_service_1 = require("./project.service");
var WorkProjectComponent = (function () {
    function WorkProjectComponent(projectService, fileService, routeParams, router) {
        this.projectService = projectService;
        this.fileService = fileService;
        this.routeParams = routeParams;
        this.router = router;
        this.l = app_utils_1.AppUtils.logger("work-project.component");
        this.project = null;
    }
    WorkProjectComponent.prototype.ngOnInit = function () {
        this.loadContent();
    };
    WorkProjectComponent.prototype.loadContent = function () {
        var _this = this;
        this.l.log("loadContent begins");
        var id = this.routeParams.get("id");
        this.projectService.getProject(id)
            .then(function (project) {
            _this.project = project;
            _this.projectService.setSelectedProject(project);
        }, function (reject) { return _this.router.navigate(['OupsPage']); });
        this.l.log("loadContent ends");
    };
    WorkProjectComponent = __decorate([
        core_1.Component({
            selector: 'r-editor',
            templateUrl: app_utils_1.AppUtils.templateUrl("r-editor"),
        }), 
        __metadata('design:paramtypes', [project_service_1.ProjectService, file_service_1.FileService, router_deprecated_1.RouteParams, router_1.Router])
    ], WorkProjectComponent);
    return WorkProjectComponent;
}());
exports.WorkProjectComponent = WorkProjectComponent;
//# sourceMappingURL=work-project.component.js.map