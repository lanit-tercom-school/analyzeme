import {Component, EventEmitter, OnInit, AfterViewInit, Output} from "@angular/core"
import {RouterConfig, provideRouter, ROUTER_DIRECTIVES, Router} from "@angular/router";
import {ROUTER_PROVIDERS} from "@angular/router-deprecated";
import {AppUtils} from "./app-utils";
import {ProjectsListComponent} from "./projects-list.component";
import {NewProjectComponent} from "./new-project.component";

@Component({
    selector: 'project-page',
    templateUrl: AppUtils.templateUrl("project-page"),
    styleUrls: [AppUtils.cssUrls("new-project")],
    directives: [ROUTER_DIRECTIVES, ProjectsListComponent, NewProjectComponent]

})
export class ProjectPageComponent {
    constructor() {
        document.title = "AnalyzeMe | Projects";
    }
}
