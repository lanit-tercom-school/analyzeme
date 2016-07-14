import {Component, EventEmitter, OnInit, AfterViewInit, Output, Pipe} from "@angular/core"
import {RouterConfig, provideRouter, ROUTER_DIRECTIVES, Router} from "@angular/router";
import {ROUTER_PROVIDERS} from "@angular/router-deprecated";
import {AppUtils} from "./app-utils";
import {ProjectService} from "./project.service";

@Pipe({
    name: "checkProperty",
    pure: true
})
class CheckPropertyPipe {
    transform(array) {
        return AppUtils.findByKey('isActive', array, true);
    }
}


@Component({
    selector: "projects-list",
    templateUrl: AppUtils.templateUrl("projects-list"),
    styleUrls: [AppUtils.cssUrls("projects-list")],
    pipes: [CheckPropertyPipe]
})
export class ProjectsListComponent implements OnInit, AfterViewInit {
    l = AppUtils.logger("project-list.component");

    selectedProject = null;
    projects = null;

    constructor(public router:Router, public projectService: ProjectService) {
        document.title = "AnalyzeMe | Projects";
    }

    ngOnInit() {
        this.getProjects();
        this.getSelectedProject();
        this.l.log("(ngOnInit)");
        this.l.dir(this.selectedProject);
    }

    ngAfterViewInit() {
        AppUtils.MDL.upgradeClasses(["mdl-js-button"]);
    }

    getProjects() {
        this.projectService.getProjects()
            .then(projects => this.projects = projects);
    }

    getSelectedProject() {
        this.projectService.getSelectedProject()
            .then(project => this.selectedProject = project);
    }

    isSelectedProject(project) {
        return this.projectService.equals(project, this.selectedProject);
    }

    onSelect(project) {
        this.projectService.setSelectedProject(project);
        this.l.log("(onSelect)");
        this.projectService.getSelectedProject()
            .then(data => this.l.dir(data));
    }

    addProject() {
        var name = prompt("Enter name of new project:", "new project");
        if (name == null) return;
        var self = this;
        //var name = "newProject" + new Date().getSeconds();
        this.projectService.addItem(name)
            .then(xhr => self.getProjects());
    }

    deleteProject(project) {
        var self = this;
        this.projectService.deleteProject(project.projectId)
            .then(xhr => self.getProjects());
    }
}
