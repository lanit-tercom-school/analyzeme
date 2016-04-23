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
            ngAfterViewInit: function() {
                app.AppUtils.MDL.upgradeClasses(["mdl-js-button"]);
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
            updateFileNameField: function() {
                  document.getElementById("fileNameField").innerText = document.forms.upload.elements.myfile.files[0].name;
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
