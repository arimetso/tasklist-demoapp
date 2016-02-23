(function(app) {
    app.AppComponent =
        ng.core.Component({
                selector: 'todo-app',
                template: '<h1>Demo Todo App</h1>'
            })
            .Class({
                constructor: function() {}
            });
})(window.app || (window.app = {}));
