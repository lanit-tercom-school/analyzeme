import {AppUtils} from "./app-utils";
import {Injectable} from "@angular/core";

@Injectable()
export class ProjectService {
    l = AppUtils.logger("project.service");

    selectedProject:any = {};
    data:any = null;
    _idGen:any = null;

    constructor() {
        // this._idGen = (function*() {
        //     let id = 1111;
        //     while (true) {
        //         yield id;
        //         id += 1;
        //     }
        // })();

    }

    getSelectedProject() {
        return Promise.resolve(this.selectedProject);
    }

    setSelectedProject(project) {
        AppUtils.copyObj(project, this.selectedProject);
    }

    updateProjects() {
        this.l.log("updateProjects");
        return new Promise((resolve, reject) => {
            var request = new XMLHttpRequest();
            request.onload = request.onerror = function (event) {
                if (request.status == 200) {
                    var response = request.responseText ? JSON.parse(request.responseText) : [];
                    // if (!this.data) this.data = [];
                    // for (var i = 0; i < this.data.length; i++) {
                    //   delete this.data[i];
                    // };
                    //AppUtils.copyObj(response.Projects, this.data);//
                    this.data = response.Projects;
                    this.l.dir(this.data);
                    resolve(this.data);
                } else {
                    this.l.log("error " + request.status);
                    reject("error " + request.status);
                }
            };
            request.open("GET",
                AppUtils.resolveUrl("projects/info"),
                true
            );
            request.send();
        });
    }

    equals(o1, o2) {
        if (o1 == o2) return true;
        if (o1.length != o2.length) return false;
        for (let property in o1) {
            if (o1[property] != o2[property]) {
                return false;
            }
        }
        return true;
    }

    getProjects():any {
        return this.updateProjects();
    }

    getProject(id) {
        return new Promise((resolve, reject) => {
            this.getProjects()
                .then(projects => {
                        this.l.log("projects");
                        this.l.dir(projects);
                        var filtered = AppUtils.findByKey("projectId", projects, id);
                        // var filtered = this._findById(projects, id);
                        this.l.log("projects filtered by projectId:" + id);
                        this.l.dir(filtered);
                        if (filtered.length == 0) {
                            reject("no project with this id");
                        } else {
                            resolve(filtered[0]);
                        }
                    },
                    Promise.reject("no data")
                );
        });
    }

    addItem(projectName) {
        return AppUtils.API.createProject(1, projectName);
    }

    deleteProject(projectId) {
        return AppUtils.API.deleteProject(1, projectId);
    }
}
