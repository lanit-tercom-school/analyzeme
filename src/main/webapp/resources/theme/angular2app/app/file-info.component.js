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
var FileInfoComponent = (function () {
    function FileInfoComponent(fileService) {
        this.fileService = fileService;
        this.fileInfo = {};
        this.l = app_utils_1.AppUtils.logger("file-info.component");
    }
    FileInfoComponent.prototype.ngOnInit = function () {
        this.getFileInfo();
    };
    FileInfoComponent.prototype.getFileInfo = function () {
        var _this = this;
        return this.fileService.getFileInfo()
            .then(function (fileInfo) { return _this.fileInfo = fileInfo; });
    };
    FileInfoComponent.prototype.isEmpty = function () {
        return this.fileService.equals(this.fileInfo, {});
    };
    FileInfoComponent = __decorate([
        core_1.Component({
            selector: 'file-info',
            templateUrl: app_utils_1.AppUtils.templateUrl("file-info")
        }), 
        __metadata('design:paramtypes', [file_service_1.FileService])
    ], FileInfoComponent);
    return FileInfoComponent;
}());
exports.FileInfoComponent = FileInfoComponent;
//# sourceMappingURL=file-info.component.js.map