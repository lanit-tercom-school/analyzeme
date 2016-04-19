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
                var fileName = this._workspaceService._fileService.getServerName();
                //var functionType = document.getElementById("functionSelect").value;
                var resultOutput = document.getElementById("functionResult");
                if (this.functionType == "UserScript") {
                  resultOutput.value = "User's script";
                  alert(this._workspaceService.session.script);
                  return;
                }
                resultOutput.value = "Analyzing...";
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
