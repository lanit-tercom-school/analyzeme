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
var CheckPropertyPipe = (function () {
    function CheckPropertyPipe() {
    }
    CheckPropertyPipe.prototype.transform = function (array) {
        return app_utils_1.AppUtils.findByKey('isActive', array, true);
    };
    CheckPropertyPipe = __decorate([
        core_1.Pipe({
            name: "checkProperty",
            pure: true
        }), 
        __metadata('design:paramtypes', [])
    ], CheckPropertyPipe);
    return CheckPropertyPipe;
}());
var ProjectsListComponent = (function () {
    function ProjectsListComponent(router, projectService) {
        this.router = router;
        this.projectService = projectService;
        this.l = app_utils_1.AppUtils.logger("project-list.component");
        this.selectedProject = null;
        this.projects = null;
        document.title = "AnalyzeMe | Projects";
    }
    ProjectsListComponent.prototype.ngOnInit = function () {
        this.getProjects();
        this.getSelectedProject();
        this.l.log("(ngOnInit)");
        this.l.dir(this.selectedProject);
    };
    ProjectsListComponent.prototype.ngAfterViewInit = function () {
        app_utils_1.AppUtils.MDL.upgradeClasses(["mdl-js-button"]);
    };
    ProjectsListComponent.prototype.getProjects = function () {
        var _this = this;
        this.projectService.getProjects()
            .then(function (projects) { return _this.projects = projects; });
    };
    ProjectsListComponent.prototype.getSelectedProject = function () {
        var _this = this;
        this.projectService.getSelectedProject()
            .then(function (project) { return _this.selectedProject = project; });
    };
    ProjectsListComponent.prototype.isSelectedProject = function (project) {
        return this.projectService.equals(project, this.selectedProject);
    };
    ProjectsListComponent.prototype.onSelect = function (project) {
        var _this = this;
        this.projectService.setSelectedProject(project);
        this.l.log("(onSelect)");
        this.projectService.getSelectedProject()
            .then(function (data) { return _this.l.dir(data); });
    };
    ProjectsListComponent.prototype.addProject = function () {
        var name = prompt("Enter name of new project:", "new project");
        if (name == null)
            return;
        var self = this;
        //var name = "newProject" + new Date().getSeconds();
        this.projectService.addItem(name)
            .then(function (xhr) { return self.getProjects(); });
    };
    ProjectsListComponent.prototype.deleteProject = function (project) {
        var self = this;
        this.projectService.deleteProject(project.projectId)
            .then(function (xhr) { return self.getProjects(); });
    };
    ProjectsListComponent = __decorate([
        core_1.Component({
            selector: "projects-list",
            templateUrl: app_utils_1.AppUtils.templateUrl("projects-list"),
            styleUrls: [app_utils_1.AppUtils.cssUrls("projects-list")],
            pipes: [CheckPropertyPipe]
        }), 
        __metadata('design:paramtypes', [router_1.Router, project_service_1.ProjectService])
    ], ProjectsListComponent);
    return ProjectsListComponent;
}());
exports.ProjectsListComponent = ProjectsListComponent;
//# sourceMappingURL=projects-list.component.js.map