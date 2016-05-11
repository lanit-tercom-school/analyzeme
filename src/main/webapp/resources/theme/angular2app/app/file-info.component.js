'use strict';
(function (app) {
    /* global ng */
    var l = app.AppUtils.logger("file-info.component");

    app.FileInfoComponent =
        ng.core.Component({
                "selector": 'file-info',
                "templateUrl": app.AppUtils.templateUrl("file-info")
            })
            .Class({
                constructor: [
                    app.FileService,
                    function FileInfoComponent(fileService) {
                        this._fileService = fileService;
                        this.fileInfo = {};
                    }
                ],
                ngOnInit: function () {
                    this.getFileInfo();
                },
                getFileInfo: function () {
                    return this._fileService.getFileInfo()
                        .then(fileInfo => this.fileInfo = fileInfo);
                },
                isEmpty() {
                    return this._fileService.equals(this.fileInfo, {});
                }
            });
})(window.app || (window.app = {}));
