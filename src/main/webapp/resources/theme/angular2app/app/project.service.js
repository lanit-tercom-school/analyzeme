"use strict";
var app_utils_1 = require("./app-utils");
var ProjectService = (function () {
    function ProjectService() {
        // this._idGen = (function*() {
        //     let id = 1111;
        //     while (true) {
        //         yield id;
        //         id += 1;
        //     }
        // })();
        this.l = app_utils_1.AppUtils.logger("project.service");
        this.selectedProject = {};
        this.data = null;
        this._idGen = null;
    }
    ProjectService.prototype.getSelectedProject = function () {
        return Promise.resolve(this.selectedProject);
    };
    ProjectService.prototype.setSelectedProject = function (project) {
        app_utils_1.AppUtils.copyObj(project, this.selectedProject);
    };
    ProjectService.prototype.updateProjects = function () {
        this.l.log("updateProjects");
        return new Promise(function (resolve, reject) {
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
                }
                else {
                    this.l.log("error " + request.status);
                    reject("error " + request.status);
                }
            };
            request.open("GET", app_utils_1.AppUtils.resolveUrl("projects/info"), true);
            request.send();
        });
    };
    ProjectService.prototype.equals = function (o1, o2) {
        if (o1 == o2)
            return true;
        if (o1.length != o2.length)
            return false;
        for (var property in o1) {
            if (o1[property] != o2[property]) {
                return false;
            }
        }
        return true;
    };
    ProjectService.prototype.getProjects = function () {
        return this.updateProjects();
    };
    ProjectService.prototype.getProject = function (id) {
        var _this = this;
        return new Promise(function (resolve, reject) {
            _this.getProjects()
                .then(function (projects) {
                _this.l.log("projects");
                _this.l.dir(projects);
                var filtered = app_utils_1.AppUtils.findByKey("projectId", projects, id);
                // var filtered = this._findById(projects, id);
                _this.l.log("projects filtered by projectId:" + id);
                _this.l.dir(filtered);
                if (filtered.length == 0) {
                    reject("no project with this id");
                }
                else {
                    resolve(filtered[0]);
                }
            }, Promise.reject("no data"));
        });
    };
    ProjectService.prototype.addItem = function (projectName) {
        return app_utils_1.AppUtils.API.createProject(1, projectName);
    };
    ProjectService.prototype.deleteProject = function (projectId) {
        return app_utils_1.AppUtils.API.deleteProject(1, projectId);
    };
    return ProjectService;
}());
exports.ProjectService = ProjectService;
//# sourceMappingURL=project.service.js.map