(function(MOCKS) {
    console.info("MOCKS server data mock begin");
    MOCKS.server = {
        analyzerGlobalMaximum : function(data) {
            var max = parseInt(data[0].y);
            for (var point of data) {
                if (point.y > max) {
                    max = parseInt(point.y);
                }
            }
            return max;
        },
        analyzerGlobalMinimum : function(data) {
            var min = parseInt(data[0].y);
            for (var point of data) {
                if (point.y < min) {
                    min = parseInt(point.y);
                }
            }
            return min;
        },
        users : [
                    {
                        "id" : 1,
                        "login" : "guest"
                    }
                ],
        projects : [
                    {
                        "lastChangeDate" : "Wed Jun 01 21:50:16 GMT+03:00 2016",
                        "projectName" : "demo",
                        "login" : "guest",
                        "creationDate" : "Wed Jun 01 21:50:16 GMT+03:00 2016",
                        "isActive" : true,
                        "projectId" : "demo"
                    }
                ],
        files : [
                {
                    "uniqueName" : "0_10.json",
                    "nameForUser" : "0_10.json",
                    "isActive" : true,
                    "owner" : "guest",
                    "projectId" : "demo",
                    "uploadingDate" : "Thu Jun 02 04:44:23 GMT+03:00 2016",
                    "data" : [{ "x": "0","y": "0" },{ "x": "1","y": "1" },{"x": "2","y": "2"},{ "x": "3","y": "3" },{ "x": "4","y": "4" },{ "x": "5","y": "5" },{ "x": "6","y": "6" },{ "x": "7","y": "7" },{ "x": "8","y": "8" },{ "x": "9","y": "9" },{ "x": "10","y": "10" }]
                },
                {
                    "uniqueName" : "lineal.json",
                    "nameForUser" : "lineal.json",
                    "isActive" : true,
                    "owner" : "guest",
                    "projectId" : "demo",
                    "uploadingDate" : "Thu Jun 02 04:44:23 GMT+03:00 2016",
                    "data" : [{ "x": "1","y": "1"},{"x": "20","y": "20"},{"x": "40", "y": "40"},{"x": "60","y": "60"}, {"x": "80","y": "80"},{"x": "100","y": "100"}]
                }
            ],
        getAllProjects : function() {
            return JSON.stringify({ Projects : this.projects });
        },
        getFilesForList : function(userId, projectId) {
            for (var user of this.users) {
                if (user.id == userId) {
                    userId = user.login;
                    break;
                }
            }
            var files = [];
            for (var i = 0; i < this.files.length; i++) {
                var file = this.files[i];
                if (file.isActive && file.owner == userId && file.projectId == projectId) {
                    files.push({
                        uniqueName : file.uniqueName,
                        nameForUser : file.nameForUser
                    });
                }
            }
            return JSON.stringify({ Files : files });
        },
        getFileData : function(userId, projectId, filename) {
            for (var user of this.users) {
                if (user.id == userId) {
                    userId = user.login;
                    break;
                }
            }
            for (var i = 0; i < this.files.length; i++) {
                var file = this.files[i];
                if (file.owner == userId && file.projectId == projectId && file.uniqueName == filename) {
                    return JSON.stringify({ Data : file.data });
                }
            }
        },
        getFileFullInfo : function(userId, projectId, filename) {
            for (var user of this.users) {
                if (user.id == userId) {
                    userId = user.login;
                    break;
                }
            }
            for (var i = 0; i < this.files.length; i++) {
                var file = this.files[i];
                if (file.owner == userId && file.projectId == projectId && file.uniqueName == filename) {
                    return JSON.stringify({
                        uniqueName : file.uniqueName,
                        nameForUser : file.nameForUser,
                        isActive : file.isActive,
                        uploadingDate : file.uploadingDate,
                        "fields":[{"fieldName":"x","fieldId":"x"},{"fieldName":"y","fieldId":"y"}]
                    });
                }
            }
        },
        analyzeFile : function(filename, funcName) {
            for (var i = 0; i < this.files.length; i++) {
                var file = this.files[i];
                if (file.uniqueName == filename) {
                    return this['analyzer' + funcName](file.data);
                }
            }
        },
        createFile : function(userId, projectId, filename, data) {//TODO
            for (var user of this.users) {
                if (user.id == userId) {
                    userId = user.login;
                    break;
                }
            }
            this.files.push(
                {
                    "uniqueName" : 'unique' + filename,
                    "nameForUser" : filename,
                    "isActive" : true,
                    "owner" : userId,
                    "projectId" : projectId,
                    "uploadingDate" : "Thu Jun 02 04:44:23 GMT+03:00 2016",
                    "data" : data
                }
            );
            return 'unique' + filename;
        },
        deleteFile : function(filename) {
            for (var file of this.files) {
                if (file.uniqueName == filename) {
                    file.isActive = false;
                    return true;
                }
            }
            return false;
        },
        deleteProject : function(userId, projectId) {
            for (var user of this.users) {
                if (user.id == userId) {
                    userId = user.login;
                    break;
                }
            }
            for (var project of this.projects) {
                if (project.login == userId && project.projectId == projectId) {
                    project.isActive = false;
                    return "OK";
                }
            }
            return "NO SUCH PROJECT";
        },
        createProject : function(userId, projectName) {
            for (var user of this.users) {
                if (user.id == userId) {
                    userId = user.login;
                    break;
                }
            }
            this.projects.push({
                        "lastChangeDate" : "Wed Jun 01 21:50:16 GMT+03:00 2016",
                        "projectName" : projectName,
                        "login" : userId,
                        "creationDate" : "Wed Jun 01 21:50:16 GMT+03:00 2016",
                        "isActive" : true,
                        "projectId" : "project" + this.projects.length
                    });
        }
    };
    console.info("MOCKS server data mock end");
})(window.MOCKS || (window.MOCKS = {}));