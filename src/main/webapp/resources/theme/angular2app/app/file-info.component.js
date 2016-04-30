'use strict';
(function (app) {
    /* global ng */
    var l = app.AppUtils.logger("file-info.component");

    app.FileInfoComponent =
        ng.core.Component({
                "selector": 'file-info',
                "templateUrl": app.AppUtils.templateUrl("file-info"),
                "styleUrls": [app.AppUtils.cssUrls("new-project")],
                "inputs": ['fileInfo']
            })
            .Class({
                constructor: [
                    function FilesInfoComponent() {
                    }],
                ngOnInit: function () {
                },
                isEmpty: function () {
                    l.dir(this.fileInfo);
                    return this.fileInfo == null;
                }
            });
})(window.app || (window.app = {}));
