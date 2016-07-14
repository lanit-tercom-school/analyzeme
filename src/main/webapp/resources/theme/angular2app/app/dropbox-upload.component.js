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
var file_service_1 = require("./file.service");
var DropboxUploadComponent = (function () {
    function DropboxUploadComponent(fileService) {
        this.fileService = fileService;
        this.selectedFile = null;
        this.Data = null;
        this.cancelClicked = false;
        this.collapsed = new core_1.EventEmitter();
        this.l = app_utils_1.AppUtils.logger("dropbox-upload.component");
    }
    DropboxUploadComponent.prototype.ngOnInit = function () {
        var _this = this;
        this.fileService.getSelectedFile()
            .then(function (file) { return _this.selectedFile = file; });
    };
    DropboxUploadComponent.prototype.ngAfterViewInit = function () {
        app_utils_1.AppUtils.MDL.upgradeClasses(["mdl-js-button"]);
    };
    DropboxUploadComponent.prototype.onSubmit = function (event) {
        this.l.log(this.cancelClicked ? "CANCEL" : "SUBMIT");
        var input = document.forms.upload.elements.myfile;
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
    };
    DropboxUploadComponent.prototype.updateFileNameField = function () {
        var input = document.forms.upload.elements.myfile;
        var fileNameField = document.getElementById("fileNameField");
        fileNameField.innerText = "";
        if (input.files) {
            for (var i = 0; i < input.files.length; i++) {
                if (input.files[i]) {
                    fileNameField.innerText += "\n" + input.files[i].name;
                }
            }
        }
    };
    DropboxUploadComponent.prototype.noon = function (event) {
        event.stopPropagation();
        event.preventDefault();
    };
    DropboxUploadComponent.prototype.onDragEnter = function (event) {
        this.l.log("DRAG ENTER");
        var dropArea = document.getElementsByName("dropbox-upload-area")[0];
        dropArea.className = "mdl-color--primary-dark";
        //this.noon(event);
        return false;
    };
    DropboxUploadComponent.prototype.onDragLeave = function (event) {
        this.l.log("DRAG LEAVE");
        var dropArea = document.getElementsByName("dropbox-upload-area")[0];
        dropArea.className = "mdl-color--primary";
        //this.noon(event);
        return false;
    };
    DropboxUploadComponent.prototype.onDrop = function (event) {
        this.l.log("DROP");
        this.onDragLeave(event);
        this.l.dir(event.dataTransfer.files);
        var input = document.forms.upload.elements.myfile;
        input.files = event.dataTransfer.files;
        this.noon(event);
        return false;
    };
    DropboxUploadComponent.prototype._uploadFile = function (file) {
        var sp = this.fileService.projectService.selectedProject;
        if (sp.login === undefined) {
            sp = {
                login: "demo",
                projectId: ""
            };
        }
        app_utils_1.AppUtils.API.uploadFile(this, file, sp, this.l);
    };
    __decorate([
        core_1.Output(), 
        __metadata('design:type', Object)
    ], DropboxUploadComponent.prototype, "collapsed", void 0);
    DropboxUploadComponent = __decorate([
        core_1.Component({
            selector: 'dropbox-upload',
            templateUrl: app_utils_1.AppUtils.templateUrl("dropbox-upload"),
            outputs: ["colapsed"]
        }), 
        __metadata('design:paramtypes', [file_service_1.FileService])
    ], DropboxUploadComponent);
    return DropboxUploadComponent;
}());
exports.DropboxUploadComponent = DropboxUploadComponent;
//# sourceMappingURL=dropbox-upload.component.js.map