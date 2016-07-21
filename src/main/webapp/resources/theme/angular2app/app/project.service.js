'use strict';
(function(app) {
    /* global ng */
    var l = app.AppUtils.logger("project.service");

    app.ProjectService = ng.core
        .Class({
            constructor: function ProjectService() {
                this._idGen = (function*() {
                    let id = 1111;
                    while (true) {
                        yield id;
                        id += 1;
                    }
                })();
                this.selectedProject = {};

                this.data = null;
            },
            getSelectedProject: function() {
                return Promise.resolve(this.selectedProject);
            },
            setSelectedProject: function(project) {
                app.AppUtils.copyObj(project, this.selectedProject);
            },
            updateProjects: function() {
                l.log("updateProjects");
                return new Promise((resolve, reject) => {
                    var request = new XMLHttpRequest();
                    request.onload = request.onerror = function(event) {
                      if (request.status == 200) {
                          var response = request.responseText ? JSON.parse(request.responseText) : [];
                          this.data = response.Projects;
                          l.dir(this.data);
                          resolve(this.data);
                      } else {
                          l.log("error " + request.status);
                          reject("error " + request.status);
                      }
                    };
                    request.open("GET",
                        app.AppUtils.resolveUrl("projects/info"),
                        true
                    );
                    request.send();
                });
            },
            equals: function(o1, o2) {
                if (o1 == o2) return true;
                if (o1.length != o2.length) return false;
                for (let property in o1) {
                    if (o1[property] != o2[property]) {
                        return false;
                    }
                }
                return true;
            },
            getProjects: function() {
                return this.updateProjects();
            },
            getProject: function(id) {
                return new Promise((resolve, reject) => {
                    this.getProjects()
                        .then((projects) => {
                                l.log("projects");
                                l.dir(projects);
                                var filtered = app.AppUtils.findByKey("projectId", projects, id);
                                // var filtered = this._findById(projects, id);
                                l.log("projects filtered by projectId:" + id);
                                l.dir(filtered);
                                if (filtered.length == 0) {
                                    reject("no project with this id");
                                } else {
                                    resolve(filtered[0]);
                                }
                            },
                            Promise.reject("no data")
                        );
                });
            },
            addItem: function(projectName) {
                return app.AppUtils.API.createProject(1, projectName);
            },
            deleteProject: function(projectId) {
                return app.AppUtils.API.deleteProject(1, projectId);
            }
        });

})(window.app || (window.app = {}));
