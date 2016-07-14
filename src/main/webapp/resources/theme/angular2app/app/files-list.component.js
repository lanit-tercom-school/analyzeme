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
var file_info_component_1 = require("./file-info.component");
var dropbox_upload_component_1 = require("./dropbox-upload.component");
var file_service_1 = require("./file.service");
var project_service_1 = require("./project.service");
var highcharts_utils_1 = require("./highcharts-utils");
var FilesListComponent = (function () {
    function FilesListComponent(router, projectServicepublic, fileService) {
        this.router = router;
        this.projectServicepublic = projectServicepublic;
        this.fileService = fileService;
        this.l = app_utils_1.AppUtils.logger("files-list.component");
        this.isDropboxExpanded = false;
        this.selectedFile = null;
        this.selectedProject = null;
        this.files = null;
        this.fileInfo = {};
    }
    FilesListComponent.prototype.ngOnInit = function () {
        this.getFiles();
        this.getSelectedFile();
        this.l.dir(this.selectedProject);
        /**/
    };
    FilesListComponent.prototype.ngAfterViewInit = function () {
        app_utils_1.AppUtils.MDL.upgradeClasses(["mdl-js-button"]);
    };
    FilesListComponent.prototype.invertDropboxFlag = function () {
        this.isDropboxExpanded = !this.isDropboxExpanded;
    };
    FilesListComponent.prototype.getFiles = function () {
        var _this = this;
        this.fileService.getFiles()
            .then(function (files) { return _this.files = files; });
    };
    FilesListComponent.prototype.getFileInfo = function () {
        var _this = this;
        return this.fileService.getFileInfo()
            .then(function (fileInfo) { return _this.fileInfo = fileInfo; });
    };
    FilesListComponent.prototype.getSelectedFile = function () {
        var _this = this;
        this.fileService.getSelectedFile()
            .then(function (file) { return _this.selectedFile = file; });
    };
    FilesListComponent.prototype.isSelectedFile = function (file) {
        return this.fileService.equals(file, this.selectedFile);
    };
    FilesListComponent.prototype.onSelect = function (file) {
        var _this = this;
        this.fileService.setSelectedFile(file);
        this.getFileInfo();
        //this.l.log("Got file info");
        highcharts_utils_1.HighchartsUtils.DrawGraph(JSON.parse(file.content).Data);
        this.fileService.getSelectedFile()
            .then(function (data) { return _this.l.dir(data); });
    };
    FilesListComponent.prototype.previewFile = function (file) {
        this.l.log("previewFile");
        this.l.dir(file);
    };
    //TODO: delete file from list without refresh
    FilesListComponent.prototype.deleteFile = function (file) {
        this.l.log("deleteFile");
        this.fileService.deleteFile(file);
    };
    FilesListComponent.prototype.isEmpty = function () {
        return this.fileService.equals(this.fileInfo, {});
    };
    FilesListComponent = __decorate([
        core_1.Component({
            selector: 'files-list',
            templateUrl: app_utils_1.AppUtils.templateUrl("files-list"),
            styleUrls: [app_utils_1.AppUtils.cssUrls("projects-list")],
            directives: [dropbox_upload_component_1.DropboxUploadComponent, file_info_component_1.FileInfoComponent]
        }), 
        __metadata('design:paramtypes', [router_1.Router, project_service_1.ProjectService, file_service_1.FileService])
    ], FilesListComponent);
    return FilesListComponent;
}());
exports.FilesListComponent = FilesListComponent;
//# sourceMappingURL=files-list.component.js.map