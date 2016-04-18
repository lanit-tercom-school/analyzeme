'use strict';
(function(app) {
  /* global ng */
  var l = app.AppUtils.logger("project-list.component");

  app.CheckPropertyPipe =
    ng.core.Pipe({
        name: "checkProperty",
        pure: true// | checkProperty : 'isActive' : true
    })
    .Class({
        constructor : function() {},
        transform: function(array) {
            return app.AppUtils.findByKey('isActive', array, true);
        }
    });

  app.ProjectsListComponent =
    ng.core.Component({
      "selector" : 'projects-list',
      "templateUrl" : app.AppUtils.templateUrl("projects-list"),
      "styleUrls" : [app.AppUtils.cssUrls("projects-list")],
      "pipes" : [app.CheckPropertyPipe]
    })
    .Class({
      constructor : [ng.router.Router, app.ProjectService, function ProjectsListComponent(router, projectService) {
        this._router = router;
      	this._projectService = projectService;
      	this.selectedProject = null;
      	this.projects = null;
      }],
      ngOnInit : function() {
        this.getProjects();
        this.getSelectedProject();
        l.log("(ngOnInit)");
        l.dir(this.selectedProject);
      },
      getProjects : function() {
        this._projectService.getProjects()
          .then(projects => this.projects = projects);
      },
      getSelectedProject: function() {
          this._projectService.getSelectedProject()
            .then(project => this.selectedProject = project);
      },
      isSelectedProject: function(project) {
          return this._projectService.equals(project,this.selectedProject);
      },
      onSelect : function(project) {
        this._projectService.setSelectedProject(project);
        l.log("(onSelect)");
        this._projectService.getSelectedProject()
            .then(data => l.dir(data));
      },
      addProject: function() {
          var name = prompt("Enter name of new project:", "new project");
          var self = this;
          //var name = "newProject" + new Date().getSeconds();
          this._projectService.addItem(name)
          .then(xhr => self.getProjects());
      },
      deleteProject: function(project) {
          var self = this;
          this._projectService.deleteProject(project.projectId)
          .then(xhr => self.getProjects());
      }
    });
})(window.app || (window.app = {}));
