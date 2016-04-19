'use strict';
(function(app) {
    /* global ng */
    // mock component
    app.REditorComponent =
        ng.core.Component({
            "selector": 'r-editor',
            "template": `
                <div class="greybox">REditorComponent</div>
                `
        })
        .Class({
            constructor: function REditorComponent() {}
        });
})(window.app || (window.app = {}));
