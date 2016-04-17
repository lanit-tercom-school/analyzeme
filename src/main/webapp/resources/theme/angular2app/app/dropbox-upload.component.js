(function(app) {
    /* global ng */
    var l = app.AppUtils.logger("dropbox-upload.component");

    app.DropboxUploadComponent =
        ng.core.Component({
            "selector": 'dropbox-upload',
            "templateUrl": app.AppUtils.templateUrl("dropbox-upload"),
            "outputs": ["collapsed"]
        })
        .Class({
            constructor: [
                app.FileService,
                function DropboxUploadComponent(fileService) {
                    this._fileService = fileService;
                    this.selectedFile = null;
                    this.Data = null;
                    this.collapsed = new ng.core.EventEmitter();
                }
            ],
            ngOnInit: function() {
                this._fileService.getSelectedFile()
                    .then(file => this.selectedFile = file);
            },
            onSubmit: function(event) {
                l.log("SUBMIT");
                var input = document.forms.upload.elements.myfile;
                var file = input.files[0];
                if (file) {
                    this._uploadFile(file);
                }
                l.dir(file);
                return false;
            },
            noon: function(event) {
                event.stopPropagation();
                event.preventDefault();
            },
            onDrop: function(event) {
                l.log("DROP");
                l.dir(event.dataTransfer.files);
                var input = document.forms.upload.elements.myfile;
                input.files = event.dataTransfer.files;
                this.noon(event);
                return false;
            },
            _uploadFile: function(file) {
                //-------------
                var self = this;
                var formData = new FormData();
                formData.append("file", file);
                var xhr = new XMLHttpRequest();
                // for progress bar //
                // xhr.upload.onprogress = function(event) {
                //     l.log(event.loaded + ' / ' + event.total);
                // };

                xhr.onload = xhr.onerror = function(event) {

                    l.log("onload");
                    //var temp_fileName = document.forms.upload.elements.myfile.files[0].name;

                    if (xhr.status == 200) {
                        self._fileService.setSelectedFile(
                            app.AppUtils.extractFileFromXHR(xhr)
                        );
                        self._fileService.getSelectedFile()
                            .then(sFile => self.selectedFile = sFile);
                        if (self.selectedFile.content) {
                            self.Data = JSON.parse(self.selectedFile.content).Data;
                        }
                        l.dir(self.selectedFile);
                        //
                        l.log("success");
                        self._fileService.addFile(self.selectedFile);
                        try {
                            app.d3Utils.DrawGraph(self.Data);
                        } catch (e) {
                            l.error("can't draw graphic [possibly, wrong data]", e);
                        } finally {};
                    } else {
                        l.log("error " + this.status);
                    }
                };
                //xhr.open("POST", app.AppUtils.resolveUrl("upload/demo"), true);
                var sp = this._fileService._projectService.selectedProject;
                if (sp.login === undefined)
                {
                  sp = {
                      login: "demo",
                      projectId: ""
                  };
                }
                xhr.open("POST",
                  app.AppUtils.resolveUrl(
                    "upload/" + (sp.login == "guest" ? 1 : sp.login) + "/" + sp.projectId
                  ), true);
                xhr.send(formData);
            }
        });

    /*ng.router.RouteConfig([
    {
      path : '/projectsList',
      name : 'ProjectsList',
      component : app.NewProjectComponent,
      useAsDefault : true
    },
    {
      path : '/:id',
      name : 'WorkProject',
      component : app.WorkProjectComponent,
      useAsDefault : false
    }
  ])(app.ProjectPageComponent);*/
})(window.app || (window.app = {}));
