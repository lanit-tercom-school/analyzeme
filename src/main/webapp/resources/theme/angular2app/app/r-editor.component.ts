import {Component, EventEmitter, OnDestroy, AfterViewInit, Output} from "@angular/core"
import {RouterConfig, provideRouter, ROUTER_DIRECTIVES, Router} from "@angular/router";
import {ROUTER_PROVIDERS} from "@angular/router-deprecated";
import {AppUtils} from "./app-utils";
import {WorkspaceService} from "./workspace.service";

@Component({
    selector: 'r-editor',
    templateUrl: AppUtils.templateUrl("r-editor"),

})
export class REditorComponent implements OnDestroy, AfterViewInit{
    editor: any = null;
    isSelectExpanded: boolean = false;

    constructor(public workspaceService: WorkspaceService, public router:Router) {
        this.workspaceService.session.autorun = false;
        document.title = "AnalyzeMe | r-editor";
    }

    ngAfterViewInit() {
        AppUtils.MDL.upgradeClasses(["mdl-js-button", "mdl-js-radio"]);
        this.editor = ace.edit("editor");
        this.editor.getSession().setMode("ace/mode/r");
        this.editor.setValue(this.workspaceService.session.script, -1);
        this.editor.focus();
    }

    ngOnDestroy() {
        this.saveScript();
    }

    saveScript() {
        this.workspaceService.session.script = this.editor.getValue();
    }

    runScript() {
        this.saveScript();
        this.workspaceService.session.autorun = true;
        this.router.navigate(['Plot']);
    }

    setReturnType(returnType) {
        let a = this.workspaceService.availableFunctions;
        a.splice(0, 0, a.splice(1, 1)[0]);
        this.isSelectExpanded = !this.isSelectExpanded;
        this.workspaceService.session.returnType = returnType;
    }
}
