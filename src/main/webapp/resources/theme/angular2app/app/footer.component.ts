import {Component} from "@angular/core"
import {AppUtils} from "./app-utils";
import {ROUTER_DIRECTIVES} from "@angular/router";

@Component({
    selector: 'app-footer',
    templateUrl: AppUtils.templateUrl("footer"),
    directives : [ROUTER_DIRECTIVES]
})
export class FooterComponent {}

