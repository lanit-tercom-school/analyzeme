"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};
var core_1 = require("@angular/core");
var app_utils_1 = require("./app-utils");
var OupsComponent = (function () {
    function OupsComponent() {
        document.title = "AnalyzeMe | Oups :c";
    }
    OupsComponent.prototype.ngAfterViewInit = function () {
        app_utils_1.AppUtils.MDL.upgradeClasses(["mdl-js-button"]);
    };
    OupsComponent.prototype.goBack = function () {
        window.history.go(-3);
    };
    OupsComponent = __decorate([
        core_1.Component({
            selector: 'my-oups',
            template: "\n        <div align=\"center\">\n            <h2>Something goes wrong</h2>\n            <br/>\n            <h4>\n                We suggest you to go\n                <br/>\n                <br/>\n                <button (click)=\"goBack()\"\n                    class=\"mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect\">\n                    BACK\n                </button>\n            </h4>\n        </div>\n      "
        }), 
        __metadata('design:paramtypes', [])
    ], OupsComponent);
    return OupsComponent;
}());
exports.OupsComponent = OupsComponent;
//# sourceMappingURL=oups.component.js.map