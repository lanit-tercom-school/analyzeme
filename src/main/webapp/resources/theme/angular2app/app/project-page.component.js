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
var projects_list_component_1 = require("./projects-list.component");
var new_project_component_1 = require("./new-project.component");
var ProjectPageComponent = (function () {
    function ProjectPageComponent() {
        document.title = "AnalyzeMe | Projects";
    }
    ProjectPageComponent = __decorate([
        core_1.Component({
            selector: 'project-page',
            templateUrl: app_utils_1.AppUtils.templateUrl("project-page"),
            styleUrls: [app_utils_1.AppUtils.cssUrls("new-project")],
            directives: [router_1.ROUTER_DIRECTIVES, projects_list_component_1.ProjectsListComponent, new_project_component_1.NewProjectComponent]
        }), 
        __metadata('design:paramtypes', [])
    ], ProjectPageComponent);
    return ProjectPageComponent;
}());
exports.ProjectPageComponent = ProjectPageComponent;
//# sourceMappingURL=project-page.component.js.map