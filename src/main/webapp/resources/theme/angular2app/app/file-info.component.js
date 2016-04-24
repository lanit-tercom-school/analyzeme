'use strict';
(function (app) {
    /* global ng */
    var l = app.AppUtils.logger("file-info.component");

    app.FileInfoComponent =
        ng.core.Component({
                "selector": 'file-info',
                "templateUrl": app.AppUtils.templateUrl("file-info"),
                "styleUrls": [app.AppUtils.cssUrls("new-project")]
            })
            .Class({
                constructor: [
                    ng.router.Router,
                    app.FileService,
                    function FilesInfoComponent(router, fileService) {
                        this._router = router;
                        this._fileService = fileService;
                        this.fileInfo = null;
                    }],
                ngOnInit: function () {
                    if(this._fileService.fileInfo) {
                        getFileInfo();
                    }
                },
                getFileInfo: function () {
                    this._fileService.getFileInfo()
                        .then(fileInfo => this.fileInfo = fileInfo);
                },
                isEmpty: function() {
                    return this.fileInfo == null; 
                }
            });
})(window.app || (window.app = {}));
