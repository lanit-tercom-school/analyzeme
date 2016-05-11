'use strict';
(function (app) {
    /* global ng */
    var l = app.AppUtils.logger("files-list.component");

    app.FilesListComponent =
        ng.core.Component({
                "selector": 'files-list',
                "templateUrl": app.AppUtils.templateUrl("files-list"),
                "styleUrls": [app.AppUtils.cssUrls("projects-list")],
                "directives": [app.DropboxUploadComponent, app.FileInfoComponent]
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
                        this.fileInfo = {};
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
                    return this._fileService.getFileInfo()
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
                    //l.log("Got file info");
                    app.d3Utils.DrawGraph(JSON.parse(file.content).Data);
                    this._fileService.getSelectedFile()
                        .then(data => l.dir(data));
                },
                previewFile: function (file) {
                    l.log("previewFile");
                    l.dir(file);
                },
                //TODO: delete file from list without refresh
                deleteFile: function (file) {
                    l.log("deleteFile");
                    this._fileService.deleteFile(file);
                },
                isEmpty() {
                    return this._fileService.equals(this.fileInfo, {});
                }
            });
})(window.app || (window.app = {}));
