//namespace
(function () {
    window.App = ({
        Models: {},
        Views: {},
        Collections: {},
        Router: {}
    });

    window.App.URL = "http://localhost:8080/rest/users";
    
    window.vent = _.extend({}, Backbone.Events);
    
    window.App.template = function (id) {
        return _.template($('#' + id).html());
    };
}());
