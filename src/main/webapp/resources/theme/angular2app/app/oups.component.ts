import {Component, EventEmitter, OnDestroy, AfterViewInit, Output, OnInit} from "@angular/core"
import {RouterConfig, provideRouter, ROUTER_DIRECTIVES, Router} from "@angular/router";
import {ROUTER_PROVIDERS} from "@angular/router-deprecated";
import {AppUtils} from "./app-utils";

@Component({
    selector: 'my-oups',
    template: `
        <div align="center">
            <h2>Something goes wrong</h2>
            <br/>
            <h4>
                We suggest you to go
                <br/>
                <br/>
                <button (click)="goBack()"
                    class="mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect">
                    BACK
                </button>
            </h4>
        </div>
      `
})
export class OupsComponent {
    constructor() {
        document.title = "AnalyzeMe | Oups :c";
    }

    ngAfterViewInit() {
        AppUtils.MDL.upgradeClasses(["mdl-js-button"]);
    }

    goBack() {
        window.history.go(-3);
    }
}


