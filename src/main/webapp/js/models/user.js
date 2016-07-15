App.Models.User = Backbone.Model.extend({

    defaults: {
        login: "lll",
        password: "ppp",
        email: "eeee",
        firstName: "",
        lastName: "",
        birthday: "",
        role: {
            name: ""
        }
    },

    idAttribute: "id",

    initialize: function () {
    },

    validate: function (attrs) {
        if (attrs.login.length < 5 || attrs.login.length > 50) {
            return "Too short login"
        }
        if (attrs.password.length < 5 || attrs.password.length > 50) {
            return "Too short password"
        }
        if (attrs.login.length < 3) {
            return "Incorrect email"
        }
    },

    urlRoot: App.URL
});