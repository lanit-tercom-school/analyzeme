import {Component} from "@angular/core";
import {ROUTER_DIRECTIVES, Router} from "@angular/router";
import {NavigationComponent} from "./navigation.component";
import {FooterComponent} from "./footer.component";
import {ProjectService} from "./project.service";

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

@Component({
    selector: 'my-app',
    template: '<h1>My First Angular 2 App</h1>',
    directives: [ROUTER_DIRECTIVES, NavigationComponent, FooterComponent],
    providers: [ProjectService]
})
export class AppComponent {
    constructor(public projectService: ProjectService, public router: Router) {
    }
}

