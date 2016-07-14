"use strict";
var app_utils_1 = require("./app-utils");
var WorkspaceService = (function () {
    function WorkspaceService(projectService, fileService) {
        this.projectService = projectService;
        this.fileService = fileService;
        this.l = app_utils_1.AppUtils.logger("workspace.service");
        this.session = {
            autorun: false,
            returnType: "DOUBLE",
            script: "# Define a variable.\nx <- rnorm(10)\n\n# calculate the mean of x\nmean(x)"
        };
        this.returnTypes = [
            "DOUBLE",
            "SCALAR",
            "JSON_STRING",
            "VECTOR",
            "FILE"
        ];
        this.availableFunctions = [
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
    }
    return WorkspaceService;
}());
exports.WorkspaceService = WorkspaceService;
//# sourceMappingURL=workspace.service.js.map