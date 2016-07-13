var url = "http://localhost:8080/rest/users";

var User = Backbone.Model.extend({

    defaults: {
        id: null,
        login: "lll",
        password: "ppp",
        email: "eeee",
        firstName: "",
        lastName: "",
        birthday: "",
        role: {
            id: null,
            name: ""
        }
    },

    idAttribute: "id",

    initialize: function () {
        console.log('User has been initialized');
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

    urlRoot: url
});

var UserCollection = Backbone.Collection.extend({

    model: this.User,

    url: url

});

var UserView = Backbone.View.extend({
    tagName: "li",
    model: User,

    template: "#userTemplate",

    render: function (){
        var template = _.template( $(this.template).html() );
        this.$el.html(template(this.model.toJSON()));
        return this;
    }

});

var UserListView = Backbone.View.extend({
    model: UserCollection,

    render: function() {
        this.$el.html(); // lets render this view

        var self = this;

        for(var i = 0; i < this.model.length; ++i) {
            // lets create a book view to render
            var m_bookView = new UserView({model: this.model.at(i)});

            // lets add this book view to this list view
            this.$el.append(m_bookView.$el);
            m_bookView.render(); // lets render the book
        }

        return this;
    }
});


// $(document).ready(function () {
//     var collection = new UserCollection().fetch();
//     console.log(collection);
//     var view = new userListView({ el: $("#list"), model: collection });
//     view.render();
//     console.log("gonna render");
// });