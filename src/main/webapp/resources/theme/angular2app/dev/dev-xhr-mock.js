/* //Example of usage in app
    app.AppUtils.makeRequest = function (method, path, body, headers, successChecker, callbackSuccess, callbackError) {
        return new Promise((resolve, reject) => {
            var xhr = new XMLHttpRequest();
            xhr.onload = xhr.onerror = function (event) {
                if (successChecker(xhr)) {
                    callbackSuccess(xhr);
                    resolve(xhr);
                } else {
                    callbackError(xhr);
                    reject("error " + xhr.status);
                }
            };
            xhr.open(method, app.AppUtils.resolveUrl(path), true);
            if (headers) {
                for (header of headers) {
                    xhr.setRequestHeader(header.name, header.data);
                }
            }
            xhr.send(body);
        });
    };
*/
// mock XMLHttpRequest
(function(MOCKS) {
    console.info("MOCKS begin");
    // rules manager
    MOCKS.XHRRules = {
        rules : [],
        
        add : function(method, urlChecker, callback) {
            this.rules.push({
                "method"  : function() { return method; },
                "urlChecker" :         function(url) { return urlChecker(url); },
                "callback" : function() { return callback(this); }
            });
            return this;
        },
        
        get: function(urlChecker, callback) { return this.add('GET', urlChecker, callback); },

        post: function(urlChecker, callback) { return this.add('POST', urlChecker, callback); },

        put: function(urlChecker, callback) { return this.add('PUT', urlChecker, callback); },

        patch: function(urlChecker, callback) { return this.add('PATCH', urlChecker, callback); },

        delete: function(urlChecker, callback) { return this.add('DELETE', urlChecker, callback); },
        
        action : function(method, url) {
            for (var rule of this.rules) {
                if (rule.method() == method && rule.urlChecker(url)) {
                    return rule.callback;
                }
            }
            return false;
        }
    };
    console.info("MOCKS XHRRules done");
    
    UNSENT                    = 0;
    OPENED                    = 1;
    HEADERS_RECEIVED = 2;
    LOADING                  = 3;
    DONE                       = 4;

    MOCKS.fire = function(eventName) {
        //alert(eventName);
        if (this.onreadystatechange) {
            this.onreadystatechange();
        }

        if (this['on'+eventName]) {
            this['on'+eventName]();
        }
    };
    
    MOCKS.old_open = XMLHttpRequest.prototype.open;
    XMLHttpRequest.prototype.open = function(method, url, async) {
        this.action = MOCKS.XHRRules.action(method, url);
        if (this.action) { 
            this.method = method;
            this.url = url;
            this.async = async;
            this.requestHeaders = {};
            this.data = null;
            Object.defineProperty(this, 'readyState', {
                writable: true
            });
            this.readyState = OPENED;
            MOCKS.fire.call(self, 'open');
        } else {
            MOCKS.old_open.apply(this, arguments); 
        }
    };
    
    MOCKS.old_send = XMLHttpRequest.prototype.send;
    XMLHttpRequest.prototype.send = function(data) {
        if (this.action) { 
            var self = this;
            this.data = data;
            this.readyState = LOADING;
            setTimeout(function() {
                var response = self.action();
                for (var key in response) {
                    Object.defineProperty(self, key, {
                        writable: true
                    });
                    self[key] = response[key];
                };
                self.readyState = DONE;
                MOCKS.fire.call(self, 'load');
            }, 0);
        } else {
            MOCKS.old_send.apply(this, arguments); 
        }
    };

    MOCKS.old_setRequestHeader = XMLHttpRequest.prototype.setRequestHeader;
    XMLHttpRequest.prototype.setRequestHeader = function(name, value) {
        if (this.action) { 
            this.requestHeaders[name] = value;
        } else {
            MOCKS.old_setRequestHeader.apply(this, arguments); 
        }
    };
    console.info("MOCKS end");
})(window.MOCKS || (window.MOCKS = {}));