import {Component, EventEmitter, OnInit, AfterViewInit, Output} from "@angular/core"
import {RouterConfig, provideRouter, ROUTER_DIRECTIVES, Router} from "@angular/router";
import {ROUTER_PROVIDERS} from "@angular/router-deprecated";
import {AppUtils} from "./app-utils";
import {FileInfoComponent} from "./file-info.component";
import {DropboxUploadComponent} from "./dropbox-upload.component";
import {FileService} from "./file.service";
import {ProjectService} from "./project.service";
import {HighchartsUtils} from "./highcharts-utils";

@Component({
    selector: 'files-list',
    templateUrl: AppUtils.templateUrl("files-list"),
    styleUrls: [AppUtils.cssUrls("projects-list")],
    directives: [DropboxUploadComponent, FileInfoComponent]
})
export class FilesListComponent implements OnInit, AfterViewInit {
    l = AppUtils.logger("files-list.component");

    isDropboxExpanded: any = false;
    selectedFile: any  = null;
    selectedProject: any  = null;
    files: any  = null;
    fileInfo: any  = {};

    constructor(public router:Router, public projectServicepublic:ProjectService, public fileService:FileService) {
    }

    ngOnInit() {
        this.getFiles();
        this.getSelectedFile();
        this.l.dir(this.selectedProject);
        /**/
    }

    ngAfterViewInit() {
        AppUtils.MDL.upgradeClasses(["mdl-js-button"]);
    }

    invertDropboxFlag() {
        this.isDropboxExpanded = !this.isDropboxExpanded;
    }

    getFiles() {
        this.fileService.getFiles()
            .then(files => this.files = files);
    }

    getFileInfo() {
        return this.fileService.getFileInfo()
            .then(fileInfo => this.fileInfo = fileInfo);
    }

    getSelectedFile() {
        this.fileService.getSelectedFile()
            .then(file => this.selectedFile = file);
    }

    isSelectedFile(file) {
        return this.fileService.equals(file, this.selectedFile);
    }

    onSelect(file) {
        this.fileService.setSelectedFile(file);
        this.getFileInfo();
        //this.l.log("Got file info");
        HighchartsUtils.DrawGraph(JSON.parse(file.content).Data);
        this.fileService.getSelectedFile()
            .then(data => this.l.dir(data));
    }

    previewFile(file) {
        this.l.log("previewFile");
        this.l.dir(file);
    }

    //TODO: delete file from list without refresh
    deleteFile(file) {
        this.l.log("deleteFile");
        this.fileService.deleteFile(file);
    }

    isEmpty() {
        return this.fileService.equals(this.fileInfo, {});
    }
}
