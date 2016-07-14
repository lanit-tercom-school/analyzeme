import {AppUtils} from "./app-utils";
import {ProjectService} from "./project.service";
import {FileService} from "./file.service";

import {Injectable} from "@angular/core";

@Injectable()
export class WorkspaceService {
    l:any = AppUtils.logger("workspace.service");
    session:any = {
        autorun: false,
        returnType: "DOUBLE",
        script: `# Define a variable.
x <- rnorm(10)

# calculate the mean of x
mean(x)`
    };
    returnTypes:Array<string> = [
        "DOUBLE",
        "SCALAR",
        "JSON_STRING",
        "VECTOR",
        "FILE"
    ];

    availableFunctions:Array<any> = [
        {
            func: "GlobalMinimum",
            name: "Global minimum"
        },
        {
            func: "GlobalMaximum",
            name: "Global maximum"
        },
        {
            func: "LinearCorrelation",
            name: "Linear Correlation"
        },
        {
            func: "LinearRegression",
            name: "Linear Regression"
        },
        {
            func: "KolmogorovSmirnovTest",
            name: "Kolmogorov-Smirnov Test"
        },
        {
            func: "UserScript",
            name: "Your script"
        }
    ];

    constructor(public projectService:ProjectService, public  fileService:FileService) {

    }

}