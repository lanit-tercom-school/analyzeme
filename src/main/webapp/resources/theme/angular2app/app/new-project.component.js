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
var project_service_1 = require("./project.service");
var NewProjectComponent = (function () {
    function NewProjectComponent(router, projectService) {
        this.router = router;
        this.projectService = projectService;
        this.selectedProject = null;
    }
    NewProjectComponent.prototype.ngOnInit = function () {
        this.selectedProject = this.projectService.selectedProject;
    };
    NewProjectComponent.prototype.getProps = function (src) {
        var res = [];
        for (var property in src) {
            res[res.length] = property;
        }
        return res;
    };
    NewProjectComponent.prototype.gotoProject = function (project) {
        var link = ['WorkProject', { "id": this.selectedProject.projectId }];
        this.router.navigate(link);
    };
    NewProjectComponent.prototype.isEmpty = function () {
        return this.projectService.equals(this.selectedProject, {});
    };
    NewProjectComponent = __decorate([
        core_1.Component({
            selector: 'new-project',
            templateUrl: app_utils_1.AppUtils.templateUrl("new-project"),
            styleUrls: [app_utils_1.AppUtils.cssUrls("new-project")],
            directives: [router_1.ROUTER_DIRECTIVES]
        }), 
        __metadata('design:paramtypes', [router_1.Router, project_service_1.ProjectService])
    ], NewProjectComponent);
    return NewProjectComponent;
}());
exports.NewProjectComponent = NewProjectComponent;
//# sourceMappingURL=new-project.component.js.map