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
var router_1 = require("@angular/router");
var router_deprecated_1 = require("@angular/router-deprecated");
var app_utils_1 = require("./app-utils");
var navigation_component_1 = require("./navigation.component");
var footer_component_1 = require("./footer.component");
var project_service_1 = require("./project.service");
// RouteConfig([{
//     path: '/plot',
//     name: 'Plot',
//     component: PlotComponent,
//     useAsDefault: true
// }, {
//     path: '/REdit',
//     name: 'REdit',
//     component: REditorComponent,
//     useAsDefault: false
// }])(WorkAreaComponent);
//
// RouteConfig([
//     {
//         path: '/...',
//         name: 'WorkArea',
//         component: WorkAreaComponent,
//         useAsDefault: true
//     }
// ])(WorkProjectComponent);
var AppComponent = (function () {
    function AppComponent(projectService, router) {
        this.projectService = projectService;
        this.router = router;
    }
    AppComponent = __decorate([
        core_1.Component({
            selector: 'my-app',
            templateUrl: app_utils_1.AppUtils.templateUrl("app"),
            directives: [router_1.ROUTER_DIRECTIVES, navigation_component_1.NavigationComponent, footer_component_1.FooterComponent],
            providers: [router_deprecated_1.ROUTER_PROVIDERS, project_service_1.ProjectService]
        }), 
        __metadata('design:paramtypes', [project_service_1.ProjectService, Object])
    ], AppComponent);
    return AppComponent;
}());
exports.AppComponent = AppComponent;
//# sourceMappingURL=app.component.js.map