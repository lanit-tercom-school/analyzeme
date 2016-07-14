import {Component, EventEmitter, OnDestroy, AfterViewInit, Output} from "@angular/core"
import {RouterConfig, provideRouter, ROUTER_DIRECTIVES, Router} from "@angular/router";
import {ROUTER_PROVIDERS} from "@angular/router-deprecated";
import {AppUtils} from "./app-utils";
import {WorkspaceService} from "./workspace.service";

@Component({
    selector: 'r-editor',
    templateUrl: AppUtils.templateUrl("r-editor"),

})
export class WorkAreaComponent implements AfterViewInit {

    constructor(public workspaceService: WorkspaceService) {
        this.workspaceService.session.autorun = false;
        document.title = "AnalyzeMe | r-editor";
    }

    ngAfterViewInit() {
        AppUtils.MDL.upgradeClasses(["mdl-js-button"]);
    }
}

