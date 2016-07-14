import {AppUtils} from "./app-utils";
import {ProjectService} from "./project.service";
import {Injectable} from "@angular/core";

@Injectable()
export class FileService {
    selectedFile:any = {};
    data:Array<any> = [];
    fileInfo:any = {};
    l = AppUtils.logger("file.service");

    constructor(public projectService:ProjectService) {
    }

    getSelectedFile() {
        return Promise.resolve(this.selectedFile);
    }

    getServerName() {
        return this.selectedFile.serverName;
    }

    getNameForUser() {
        return this.selectedFile.name;
    }

    setSelectedFile(file) {
        AppUtils.copyObj(file, this.selectedFile);
    }

    equals(o1, o2) {
        if (o1 == o2) return true;
        if (!o1 || !o2) return false;//!!!
        if (o1.length != o2.length) return false;
        for (let property in o1) {
            if (o1[property] != o2[property]) {
                return false;
            }
        }
        return true;
    }

    getFiles(): any {
        this.updateFiles();
        return Promise.resolve(this.data);
    }

    getFileInfo() {
        this.updateFileInfo();
        return Promise.resolve(this.fileInfo);
    }

    getFile(name) {
        return new Promise((resolve, reject) => {
            this.getFiles()
                .then(files => {
                        this.l.log("files");
                        this.l.log(files);
                        //TODO: what is fid here?
                        var filtered = AppUtils.findByKey("name", files, undefined); //projects.filter(project => project.id === id);
                        this.l.log("files filtered by name:" + name);
                        this.l.log(filtered);
                        if (filtered.length == 0) {
                            reject("no files with this name");
                        } else {
                            resolve(filtered[0]);
                        }
                    },
                    Promise.reject("no data")
                );
        });
    }

    updateFiles() {
        this.l.log("updateFiles");
        var sp:any = this.projectService.selectedProject;
        var filesList = null;
        AppUtils.API.getProjectFiles(
            1,
            sp.projectId === undefined ? "project" : sp.projectId
        )
            .then(
                (xhr: any) => {
                    return xhr.responseText ? JSON.parse(xhr.responseText) : [];
                },
                err => {
                    this.l.log("Failed load files list: " + err);
                }
            )
            .then(
                (filesList: any) => {
                    this.l.log("filesList");
                    this.l.dir(filesList);
                    if (filesList && filesList.Files) {
                        //{"uniqueName": ..., "nameForUser": ..., "isActive": ...}
                        for (let file of filesList.Files) {
                            if (file) {
                                this.l.log("file in filelist");
                                AppUtils.API.getFileDataFromDataSet(1, this.projectService.selectedProject.projectId, file.uniqueName)
                                    .then(
                                        (xhr: any) => {
                                            var downloadedFile: any = {
                                                "content": xhr.responseText
                                            };
                                            downloadedFile.serverName = file.uniqueName;
                                            downloadedFile.name = file.nameForUser;
                                            // downloadedFile.isActive = file.isActive;
                                            this.data.push(downloadedFile);
                                        },
                                        err =>this.l.log("Failed to load " + file.nameForUser + " file: " + err)
                                    )
                                /*
                                 AppUtils.API.getFileData(file.uniqueName)
                                 .then(
                                 (xhr: any) => {
                                 var downloadedFile = {
                                 "content": xhr.responseText
                                 };
                                 downloadedFile.serverName = file.uniqueName;
                                 downloadedFile.name = file.nameForUser;
                                 // downloadedFile.isActive = file.isActive;
                                 this.data.push(downloadedFile);
                                 },
                                 err =>this.l.log("Failed to load " + file.nameForUser + " file: " + err)
                                 )    */
                            }
                        }
                    }
                });
    }

    updateFileInfo() {
        this.l.log("getFileInfo for " + this.getServerName());
        var sp = this.projectService.selectedProject;
        var fileI = null;
        AppUtils.API.getFullFileInfo(
            1,
            sp.projectId === undefined ? "project" : sp.projectId,
            this.getServerName()
        )
            .then(
                (xhr: any) => {
                    return xhr.responseText ? JSON.parse(xhr.responseText) : {};
                },
                err => {
                    this.l.log("Failed load FileInfo: " + err);
                }
            )
            .then(
                fileI => {
                    this.l.log("fileInfo");
                    this.l.dir(fileI);
                    if (fileI) {
                        AppUtils.copyObj(fileI, this.fileInfo);
                    }
                    this.l.dir(this.fileInfo);
                })
    }

    addFile(file) {
        let fileCopy = {};
        AppUtils.copyObj(file, fileCopy);
        this.data.push(fileCopy);
    }

    deleteFile(file) {
        AppUtils.API.deleteFile(file.serverName);
        for (var i = 0; i < this.data.length; i++) {
            if (this.equals(this.data[i], file)) {
                this.data.splice(i, 1);
                this.l.log("File deleted");
                return true;
            }
        }
        return false;
    }
}
