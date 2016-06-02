// set XHRRules
(function(MOCKS) {
    console.info('MOCKS rules begin');
    MOCKS.XHRRules.get(
        function(url) { 
            return url == "/projects/info"; 
        }, 
        function(xhr){
            return {
                status : 200,
                responseText : MOCKS.server.getAllProjects()
            };
        }
    ).get(
        function(url) {
            //"/1/project/demo/filesForList"
            var parts = url.split('/');
            return parts[2] == 'project' && parts[4] == 'filesForList';
        },
        function(xhr) {
            var parts = xhr.url.split('/');
            return {
                status : 200,
                responseText : MOCKS.server.getFilesForList(parts[1], parts[3])
            };
        }
    ).get( 
        function(url) {
            //"/file/1/demo/0_10.json/data"
            var parts = url.split('/');
            return parts[1] == 'file' && parts[5] == 'data';
        },
        function(xhr) {
            var parts = xhr.url.split('/');
            return {
                status : 200,
                responseText : MOCKS.server.getFileData(parts[2], parts[3], parts[4])
            };
        }
    ).get(
        function(url) {
            //"/file/1/demo/lineal.json/getFullInfo/"
            var parts = url.split('/');
            return parts[1] == 'file' && parts[5] == 'getFullInfo';
        },
        function(xhr) {
            var parts = xhr.url.split('/');
            return {
                status : 200,
                responseText : MOCKS.server.getFileFullInfo(parts[2], parts[3], parts[4])
            };
        }
    ).get(
        function(url) {
            //"/file/lineal.json/GlobalMaximum"
            var parts = url.split('/');
            return parts[1] == 'file' && parts.length == 4;
        },
        function(xhr) {
            var parts = xhr.url.split('/');
            return {
                status : 200,
                responseText : MOCKS.server.analyzeFile(parts[2], parts[3])
            };
        }
    ).post(
        function(url) {
            //"/1/demo/run/script"
            var parts = url.split('/');
            return parts[3] == 'run' && parts[4] == 'script';
        },
        function(xhr) {
            var parts = xhr.url.split('/');
            return {
                status : 202,
                responseText : "notImplemented"
            };
        }
    ).delete(
        function(url) {
            //"/file/lineal.json/delete"
            var parts = url.split('/');
            return parts[1] == 'file' && parts[3] == 'delete';
        },
        function(xhr) {
            var parts = xhr.url.split('/');
            return {
                status : 200,
                responseText : MOCKS.server.deleteFile(parts[2])
            };
        }
    ).delete(
        function(url) {
            //"/1/project/demo/delete"
            var parts = url.split('/');
            return parts[2] == 'project' && parts[4] == 'delete';
        },
        function(xhr) {
            var parts = xhr.url.split('/');
            return {
                status : 200,
                responseText : MOCKS.server.deleteProject(parts[1], parts[3])
            };
        }
    ).put(
        function(url) {
            //"/1/project/new/create"
            var parts = url.split('/');
            return parts[2] == 'project' && parts[3] == 'new' && parts[4] == 'create';
        },
        function(xhr) {
            var parts = xhr.url.split('/');
            return {
                status : 200,
                responseText : MOCKS.server.createProject(parts[1], xhr.requestHeaders.project_name)
            };
        }
    ).post(//TODO
        function(url) {
            //"/upload/1/project2"
            var parts = url.split('/');
            return parts[1] == 'upload';
        },
        function(xhr) {
            var parts = xhr.url.split('/');
            var response = {
                status : 200
            };
            var fr = new FileReader();
            fr.onloadend = function(f) {
                console.dir(this.result);
                response.responseText = MOCKS.server.createFile(parts[2], parts[3], f.name.toString(), this.result);
            };
            fr.readAsText(xhr.data.get('file'))
            function sleep(milliseconds) {
                var start = new Date().getTime();
                for (var i = 0; i < 1e7; i++) {
                    if ((new Date().getTime() - start) > milliseconds){
                        break;
                    }
                }
            }
            sleep(500);
            return response;
        }
    );
    
    console.info('MOCKS rules end');
})(window.MOCKS || (window.MOCKS = {}));