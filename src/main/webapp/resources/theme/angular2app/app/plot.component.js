'use strict';
(function(app) {
    /* global ng */
    var l = app.AppUtils.logger("plot.component");
    // mock component
    app.PlotComponent =
        ng.core.Component({
            "selector": 'plot',
            "templateUrl": app.AppUtils.templateUrl("plot")
        })
        .Class({
            constructor: [app.WorkspaceService, function PlotComponent(wss) {
                this._workspaceService = wss;
                this.svgSize = 500;
                this.isSelectExpanded = false;
                this.functionType = "UserScript";
                this.file = {};
            }],
            ngOnInit: function() {
              l.log("ngOnInit");
              var self = this;
              this._workspaceService._fileService.getSelectedFile()
              .then(f => {
                l.log("fileReceived");
                app.AppUtils.copyObj(f, this.file)
                l.dir(this.file);
                if (this.file.content != null) {
                  l.log("drawStart");
                  l.dir(this.file);
                  app.d3Utils.DrawGraph(JSON.parse(this.file.content).Data);
                  l.log("drawEnd");
                }
                if (this._workspaceService.session.autorun) {
                  l.log("autorun script");
                  this.applyFunction();
                  this._workspaceService.session.autorun = false;
                }
              });
            },
            ngAfterViewInit: function() {
                app.AppUtils.MDL.upgradeClasses(["mdl-js-button"]);
            },
            selectFunction: function(funcType) {
              let a = this._workspaceService.availableFunctions;
              a.splice(0, 0, a.splice(1,1)[0]);
              this.isSelectExpanded = !this.isSelectExpanded;
              this.functionType = funcType;
            },
            applyFunction: function() {
                var wss = this._workspaceService;
                var fileName = wss._fileService.getServerName();
                //var functionType = document.getElementById("functionSelect").value;
                var resultOutput = document.getElementById("functionResult");
                resultOutput.value = "Analyzing...";
                if (this.functionType == "UserScript") {
                  app.AppUtils.API.runScript(
                    1,
                    wss._projectService.selectedProject.projectId,
                    wss.session.returnType,
                    1 + "_" + wss._projectService.selectedProject.projectId +"_script",
                    wss.session.script
                  )
                  .then(
                    (xhr) => {
                      resultOutput.value =
                          xhr.responseText;
                    },
                    (err) => {
                      resultOutput.value = "Can't evaluate: " + err;
                    }
                  );
                  return;
                }

                var xhr = app.AppUtils.API.analyzeFile(fileName, this.functionType);
                xhr.then(
                  (xhr) => {
                    resultOutput.value =
                        xhr.responseText;
                  },
                  (err) => {
                    resultOutput.value = "Can't evaluate: " + err;
                  }
                );
            }
        });
})(window.app || (window.app = {}));
