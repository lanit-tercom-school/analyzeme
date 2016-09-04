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
                    this.cancelClicked = false;
                }
            ],
            ngOnInit: function() {
                this._fileService.getSelectedFile()
                    .then(file => this.selectedFile = file);
            },
            ngAfterViewInit: function() {
                app.AppUtils.MDL.upgradeClasses(["mdl-js-button"]);
            },
            onSubmit: function(event) {
                l.log(this.cancelClicked ? "CANCEL" : "SUBMIT");
                var input = document.forms.upload.elements.myfile;
                if (!this.cancelClicked && input.files) {
                  for (var i = 0; i < input.files.length; i++) {
                    if (input.files[i]) {
                      this._uploadFile(input.files[i]);
                    }
                    l.dir(input.files[i]);
                  }
                }
                this.collapsed.next(false);
                return false;
            },
            updateFileNameField: function() {
                var input = document.forms.upload.elements.myfile;
                var fileNameField = document.getElementById("fileNameField");
                fileNameField.innerText = "";
                if (input.files) {
                  for (var i = 0; i < input.files.length; i++) {
                    if (input.files[i]) {
                      fileNameField.innerText += "\n" + input.files[i].name;
                    }
                  }
                }
            },
            noon: function(event) {
                event.stopPropagation();
                event.preventDefault();
            },
            onDragEnter: function(event) {
                l.log("DRAG ENTER");
                var dropArea = document.getElementsByName("dropbox-upload-area")[0];
                dropArea.className = "mdl-color--primary-dark"
                //this.noon(event);
                return false;
            },
            onDragLeave: function(event) {
                l.log("DRAG LEAVE");
                var dropArea = document.getElementsByName("dropbox-upload-area")[0];
                dropArea.className = "mdl-color--primary"
                //this.noon(event);
                return false;
            },
            onDrop: function(event) {
                l.log("DROP");
                this.onDragLeave(event);
                l.dir(event.dataTransfer.files);
                var input = document.forms.upload.elements.myfile;
                input.files = event.dataTransfer.files;
                this.noon(event);
                return false;
            },
            _uploadFile: function(file) {
                var sp = this._fileService._projectService.selectedProject;
                if (sp.login === undefined)
                {
                  sp = {
                      login: "demo",
                      projectId: ""
                  };
                }
                app.AppUtils.API.uploadFile(this, file, sp, l);
            }
        });
})(window.app || (window.app = {}));
