'use strict';
(function(app) {
    /* global ng */
    var l = app.AppUtils.logger("workspace.service");

    app.WorkspaceService = ng.core
        .Class({
            constructor: [app.ProjectService, app.FileService, function WorkspaceService(projectService, fileService) {
                this.session = {
                  autorun: false,
                  script: `# template R-script(just for test)
install.packages("caTools")  # install external package
library(caTools)         # external package providing write.gif function
jet.colors <- colorRampPalette(c("green", "blue", "red", "cyan", "#7FFF7F",
                                 "yellow", "#FF7F00", "red", "#7F0000"))
m <- 1000             # define size
C <- complex( real=rep(seq(-1.8,0.6, length.out=m), each=m ),
              imag=rep(seq(-1.2,1.2, length.out=m), m ) )
C <- matrix(C,m,m)       # reshape as square matrix of complex numbers
Z <- 0                   # initialize Z to zero
X <- array(0, c(m,m,20)) # initialize output 3D array
for (k in 1:20) {        # loop with 20 iterations
  Z <- Z^2+C             # the central difference equation
  X[,,k] <- exp(-abs(Z)) # capture results
}
write.gif(X, "Mandelbrot.gif", col=jet.colors, delay=900)
`
                };

                this.availableFunctions = [
                   {
                      func: "GlobalMinimum",
                      name: "Global minimum"
                   },
                   {
                       func: "GlobalMaximum",
                       name: "Global maximum"
                   }
                ];
                this.availableFunctions.push({
                    func: "UserScript",
                    name: "Your script"
                });

                this._projectService = projectService;
                this._fileService = fileService;
            }]
        });

})(window.app || (window.app = {}));
