import {Component, EventEmitter, OnInit, AfterViewInit, Output} from "@angular/core"
import {ROUTER_DIRECTIVES, Router} from "@angular/router";
import {AppUtils} from "./app-utils";
import {ProjectService} from "./project.service";

@Component({
    selector: 'new-project',
    templateUrl: AppUtils.templateUrl("new-project"),
    styleUrls: [AppUtils.cssUrls("new-project")],

    directives: [ROUTER_DIRECTIVES]

})
export class NewProjectComponent implements OnInit {
    selectedProject: any = null;

    constructor(public router:Router, public projectService:ProjectService) {
    }

    ngOnInit() {
        this.selectedProject = this.projectService.selectedProject;
    }

    getProps(src) {
        let res = [];
        for (let property in src) {
            res[res.length] = property;
        }
        return res;
    }

    gotoProject(project) {
        let link = ['WorkProject', {"id": this.selectedProject.projectId}];
        this.router.navigate(link);
    }

    isEmpty() {
        return this.projectService.equals(this.selectedProject, {});
    }
}
