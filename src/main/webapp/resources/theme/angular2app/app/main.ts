import {bootstrap}    from '@angular/platform-browser-dynamic';
import {AppComponent} from './app.component';
import {RouterConfig, provideRouter} from "@angular/router";
import {ProjectPageComponent} from "./project-page.component";
import {WorkProjectComponent} from "./work-project.component";
import {OupsComponent} from "./oups.component";


const routes:RouterConfig = [
    {
        path: '/',
        redirectTo: '/projectsList'
    },
    {
        path: '/projectsList',
        component: ProjectPageComponent,
    },
    {
        path: '/:id/...',
        component: WorkProjectComponent
    },
    {
        path: '/oups',
        component: OupsComponent
    }
];


const appRouterProviders = [
    provideRouter(routes)
];

bootstrap(AppComponent /*[appRouterProviders]*/);
