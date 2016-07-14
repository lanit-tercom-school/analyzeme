import {Component, EventEmitter, OnInit, AfterViewInit, Output} from "@angular/core"
import {RouterConfig, provideRouter, ROUTER_DIRECTIVES} from "@angular/router";
import {ROUTER_PROVIDERS} from "@angular/router-deprecated";
import {AppUtils} from "./app-utils";
import {FileService} from "./file.service";

@Component({
    selector: 'dropbox-upload',
    templateUrl: AppUtils.templateUrl("dropbox-upload"),
    outputs: ["colapsed"]
})
export class DropboxUploadComponent implements OnInit {

    selectedFile:any = null;
    Data:any = null;
    cancelClicked:boolean = false;

    @Output() collapsed = new EventEmitter();

    constructor(public fileService:FileService) {
    }

    l = AppUtils.logger("dropbox-upload.component");

    ngOnInit() {
        this.fileService.getSelectedFile()
            .then(file => this.selectedFile = file);
    }

    ngAfterViewInit() {
        AppUtils.MDL.upgradeClasses(["mdl-js-button"]);
    }

    onSubmit(event) {
        this.l.log(this.cancelClicked ? "CANCEL" : "SUBMIT");
        var input:any = (document.forms as any).upload.elements.myfile;
        if (!this.cancelClicked && input.files) {
            for (var i = 0; i < input.files.length; i++) {
                if (input.files[i]) {
                    this._uploadFile(input.files[i]);
                }
                this.l.dir(input.files[i]);
            }
        }
        this.collapsed.next(false);
        return false;
    }

    updateFileNameField() {
        var input:any = (document.forms as any).upload.elements.myfile;
        var fileNameField = document.getElementById("fileNameField");
        fileNameField.innerText = "";
        if (input.files) {
            for (var i = 0; i < input.files.length; i++) {
                if (input.files[i]) {
                    fileNameField.innerText += "\n" + input.files[i].name;
                }
            }
        }
    }

    noon(event) {
        event.stopPropagation();
        event.preventDefault();
    }

    onDragEnter(event) {
        this.l.log("DRAG ENTER");
        var dropArea = document.getElementsByName("dropbox-upload-area")[0];
        dropArea.className = "mdl-color--primary-dark"
        //this.noon(event);
        return false;
    }

    onDragLeave(event) {
        this.l.log("DRAG LEAVE");
        var dropArea = document.getElementsByName("dropbox-upload-area")[0];
        dropArea.className = "mdl-color--primary";
        //this.noon(event);
        return false;
    }

    onDrop(event) {
        this.l.log("DROP");
        this.onDragLeave(event);
        this.l.dir(event.dataTransfer.files);
        var input = (document.forms as any).upload.elements.myfile;
        input.files = event.dataTransfer.files;
        this.noon(event);
        return false;
    }

    private _uploadFile(file) {
        var sp:any = this.fileService.projectService.selectedProject;
        if (sp.login === undefined) {
            sp = {
                login: "demo",
                projectId: ""
            };
        }
        AppUtils.API.uploadFile(this, file, sp, this.l);
    }
}
