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
var app_utils_1 = require("./app-utils");
var workspace_service_1 = require("./workspace.service");
var highcharts_utils_1 = require("./highcharts-utils");
var PlotComponent = (function () {
    function PlotComponent(workspaceService) {
        this.workspaceService = workspaceService;
        this.l = app_utils_1.AppUtils.logger("plot.component");
        this.selectedProject = null;
        this.svgSize = 500;
        this.isSelectExpanded = false;
        this.functionType = "UserScript";
        this.file = {};
        document.title = "AnalyzeMe | Plot";
    }
    PlotComponent.prototype.ngOnInit = function () {
        var _this = this;
        this.l.log("ngOnInit");
        var self = this;
        this.workspaceService.fileService.getSelectedFile()
            .then(function (f) {
            _this.l.log("fileReceived");
            app_utils_1.AppUtils.copyObj(f, _this.file);
            _this.l.dir(_this.file);
            if (_this.file.content != null) {
                _this.l.log("drawStart");
                _this.l.dir(_this.file);
                highcharts_utils_1.HighchartsUtils.DrawGraph(JSON.parse(_this.file.content).Data);
                _this.l.log("drawEnd");
            }
            if (_this.workspaceService.session.autorun) {
                _this.l.log("autorun script");
                _this.applyFunction();
                _this.workspaceService.session.autorun = false;
            }
        });
    };
    PlotComponent.prototype.ngAfterViewInit = function () {
        app_utils_1.AppUtils.MDL.upgradeClasses(["mdl-js-button"]);
    };
    PlotComponent.prototype.selectFunction = function (funcType) {
        var a = this.workspaceService.availableFunctions;
        a.splice(0, 0, a.splice(1, 1)[0]);
        this.isSelectExpanded = !this.isSelectExpanded;
        this.functionType = funcType;
    };
    PlotComponent.prototype.applyFunction = function () {
        var wss = this.workspaceService;
        var fileName = wss.fileService.getServerName();
        //var functionType = document.getElementById("functionSelect").value;
        var resultOutput = document.getElementById("functionResult");
        resultOutput.value = "Analyzing...";
        if (this.functionType == "UserScript") {
            app_utils_1.AppUtils.API.runScript(1, wss.projectService.selectedProject.projectId, wss.session.returnType, 1 + "_" + wss.projectService.selectedProject.projectId + "_script", wss.session.script)
                .then(function (xhr) {
                resultOutput.value =
                    xhr.responseText;
            }, function (err) {
                resultOutput.value = "Can't evaluate: " + err;
            });
            return;
        }
        var xhr = app_utils_1.AppUtils.API.analyzeFile(1, wss.projectService.selectedProject.projectId, fileName, this.functionType);
        xhr.then(function (xhr) {
            resultOutput.value =
                xhr.responseText;
        }, function (err) {
            resultOutput.value = "Can't evaluate: " + err;
        });
    };
    PlotComponent = __decorate([
        core_1.Component({
            selector: 'plot',
            templateUrl: app_utils_1.AppUtils.templateUrl("plot")
        }), 
        __metadata('design:paramtypes', [workspace_service_1.WorkspaceService])
    ], PlotComponent);
    return PlotComponent;
}());
exports.PlotComponent = PlotComponent;
//# sourceMappingURL=plot.component.js.map