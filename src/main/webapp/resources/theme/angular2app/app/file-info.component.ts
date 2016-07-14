import {Component,  OnInit} from "@angular/core"
import {AppUtils} from "./app-utils";
import {FileService} from "./file.service";

@Component({
    selector: 'file-info',
    templateUrl: AppUtils.templateUrl("file-info")
})
export class FileInfoComponent implements OnInit {
    fileInfo: any = {};

    l = AppUtils.logger("file-info.component");

    constructor(public fileService:FileService) {
    }

    ngOnInit() {
        this.getFileInfo();
    }

    getFileInfo() {
        return this.fileService.getFileInfo()
            .then(fileInfo => this.fileInfo = fileInfo);
    }

    isEmpty() {
        return this.fileService.equals(this.fileInfo, {});
    }
}

 