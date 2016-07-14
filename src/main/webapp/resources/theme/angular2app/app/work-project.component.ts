import {Component, EventEmitter, OnDestroy, AfterViewInit, Output, OnInit} from "@angular/core"
import {RouterConfig, provideRouter, ROUTER_DIRECTIVES, Router} from "@angular/router";
import {ROUTER_PROVIDERS, RouteParams} from "@angular/router-deprecated";
import {AppUtils} from "./app-utils";
import {FileService} from "./file.service";
import {ProjectService} from "./project.service";

@Component({
    selector: 'r-editor',
    templateUrl: AppUtils.templateUrl("r-editor"),

})
export class WorkProjectComponent implements OnInit {
    l = AppUtils.logger("work-project.component");

    project:any = null;

    constructor(public projectService:ProjectService,
                public fileService:FileService,
                public routeParams:RouteParams, public router:Router) {
    }
    
    ngOnInit() {
        this.loadContent();
    }

    loadContent() {
        this.l.log("loadContent begins");
        let id = this.routeParams.get("id");
        this.projectService.getProject(id)
            .then(
                project => {
                    this.project = project;
                    this.projectService.setSelectedProject(project);
                },
                reject => this.router.navigate(['OupsPage'])
                /*this.project = {
                 projectName : "Failed to load\n"
                 + "Can not load project \""
                 + id + "\"(" + reject + ")."
                 + "All files will be uploaded in default project"
                 }*/
            );
        this.l.log("loadContent ends");
    }
}

