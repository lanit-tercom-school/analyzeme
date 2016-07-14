import {Component, EventEmitter, OnInit, AfterViewInit, Output} from "@angular/core"
import {RouterConfig, provideRouter, ROUTER_DIRECTIVES} from "@angular/router";
import {ROUTER_PROVIDERS} from "@angular/router-deprecated";
import {AppUtils} from "./app-utils";

@Component({
    selector: 'navigation',
    templateUrl: AppUtils.templateUrl("footer"),
    directives: [ROUTER_DIRECTIVES]
})
export class NavigationComponent {
}
