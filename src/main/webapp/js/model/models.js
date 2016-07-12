define(function(require) {
    var Backbone = require('Backbone');

    return Backbone.Model.extend({
        urlRoot: "rest/user",

        url: function() {
            return this.urlRoot + '/' + this.id;
        }
    });
});