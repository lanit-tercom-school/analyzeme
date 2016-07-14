import {Component, EventEmitter, OnInit, AfterViewInit, Output} from "@angular/core"
import {RouterConfig, provideRouter, ROUTER_DIRECTIVES, Router} from "@angular/router";
import {ROUTER_PROVIDERS} from "@angular/router-deprecated";
import {AppUtils} from "./app-utils";
import {WorkspaceService} from "./workspace.service";
import {HighchartsUtils} from "./highcharts-utils";

@Component({
    selector: 'plot',
    templateUrl: AppUtils.templateUrl("plot")
})
export class PlotComponent implements OnInit {
    l = AppUtils.logger("plot.component");

    selectedProject:any = null;
    svgSize:number = 500;
    isSelectExpanded:boolean = false;
    functionType:string = "UserScript";
    file:any = {};

    constructor(public workspaceService:WorkspaceService) {
        document.title = "AnalyzeMe | Plot";
    }

    ngOnInit() {
        this.l.log("ngOnInit");
        var self = this;
        this.workspaceService.fileService.getSelectedFile()
            .then(f => {
                this.l.log("fileReceived");
                AppUtils.copyObj(f, this.file)
                this.l.dir(this.file);
                if (this.file.content != null) {
                    this.l.log("drawStart");
                    this.l.dir(this.file);
                    HighchartsUtils.DrawGraph(JSON.parse(this.file.content).Data);
                    this.l.log("drawEnd");
                }
                if (this.workspaceService.session.autorun) {
                    this.l.log("autorun script");
                    this.applyFunction();
                    this.workspaceService.session.autorun = false;
                }
            });
    }

    ngAfterViewInit() {
        AppUtils.MDL.upgradeClasses(["mdl-js-button"]);
    }

    selectFunction(funcType) {
        let a = this.workspaceService.availableFunctions;
        a.splice(0, 0, a.splice(1, 1)[0]);
        this.isSelectExpanded = !this.isSelectExpanded;
        this.functionType = funcType;
    }

    applyFunction() {
        var wss = this.workspaceService;
        var fileName = wss.fileService.getServerName();
        //var functionType = document.getElementById("functionSelect").value;
        var resultOutput:any = document.getElementById("functionResult");
        resultOutput.value = "Analyzing...";
        if (this.functionType == "UserScript") {
            AppUtils.API.runScript(
                1,
                wss.projectService.selectedProject.projectId,
                wss.session.returnType,
                1 + "_" + wss.projectService.selectedProject.projectId + "_script",
                wss.session.script
            )
                .then(
                    (xhr: any) => {
                        resultOutput.value =
                            xhr.responseText;
                    },
                    (err) => {
                        resultOutput.value = "Can't evaluate: " + err;
                    }
                );
            return;
        }

        var xhr = AppUtils.API.analyzeFile(1, wss.projectService.selectedProject.projectId, fileName, this.functionType);
        xhr.then(
            (xhr: any) => {
                resultOutput.value =
                    xhr.responseText;
            },
            (err) => {
                resultOutput.value = "Can't evaluate: " + err;
            }
        );
    }

}
