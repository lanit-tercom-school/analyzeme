"use strict";
var platform_browser_dynamic_1 = require('@angular/platform-browser-dynamic');
var app_component_1 = require('./app.component');
var router_1 = require("@angular/router");
var project_page_component_1 = require("./project-page.component");
var work_project_component_1 = require("./work-project.component");
var oups_component_1 = require("./oups.component");
var routes = [
    {
        path: '/',
        redirectTo: '/projectsList'
    },
    {
        path: '/projectsList',
        component: project_page_component_1.ProjectPageComponent,
    },
    {
        path: '/:id/...',
        component: work_project_component_1.WorkProjectComponent
    },
    {
        path: '/oups',
        component: oups_component_1.OupsComponent
    }
];
var appRouterProviders = [
    router_1.provideRouter(routes)
];
platform_browser_dynamic_1.bootstrap(app_component_1.AppComponent, [appRouterProviders]);
//# sourceMappingURL=main.js.map