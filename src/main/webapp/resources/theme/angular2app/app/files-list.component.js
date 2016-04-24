'use strict';
(function (app) {
    /* global ng */
    var l = app.AppUtils.logger("files-list.component");

    app.FilesListComponent =
        ng.core.Component({
                "selector": 'files-list',
                "templateUrl": app.AppUtils.templateUrl("files-list"),
                "styleUrls": [app.AppUtils.cssUrls("projects-list")],
                "directives": [app.DropboxUploadComponent]
            })
            .Class({
                constructor: [
                    ng.router.Router,
                    app.ProjectService,
                    app.FileService,
                    function FilesListComponent(router, projectService, fileService) {
                        this._router = router;
                        this._projectService = projectService;
                        this._fileService = fileService;
                        this.isDropboxExpanded = false;
                        this.selectedFile = null;
                        this.files = null;
                        this.fileInfo = null;
                    }],
                ngOnInit: function () {
                    this.getFiles();
                    this.getSelectedFile();
                    l.dir(this.selectedProject);
                    /**/
                },
                ngAfterViewInit: function () {
                    app.AppUtils.MDL.upgradeClasses(["mdl-js-button"]);
                },
                invertDropboxFlag: function () {
                    this.isDropboxExpanded = !this.isDropboxExpanded;
                },
                getFiles: function () {
                    this._fileService.getFiles()
                        .then(files => this.files = files);
                },
                getFileInfo: function () {
                    this._fileService.getFileInfo()
                        .then(fileInfo => this.fileInfo = fileInfo);
                },
                getSelectedFile: function () {
                    this._fileService.getSelectedFile()
                        .then(file => this.selectedFile = file);
                },
                isSelectedFile: function (file) {
                    return this._fileService.equals(file, this.selectedFile);
                },
                onSelect: function (file) {
                    this._fileService.setSelectedFile(file);
                    this.getFileInfo();
                    app.d3Utils.DrawGraph(JSON.parse(file.content).Data);
                    this._fileService.getSelectedFile()
                        .then(data => l.dir(data));
                },
                deleteFile: function (file) {
                    l.log("deleteFile");
                    app.AppUtils.API.deleteFile(file.serverName);
                }
            });
})(window.app || (window.app = {}));
