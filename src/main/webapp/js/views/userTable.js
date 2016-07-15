App.Views.Users = Backbone.View.extend({

    initialize: function () {
        this.collection.on("add", this.addOne, this)
    },

    tagName: "tbody",

    render: function () {
        this.collection.each(this.addOne, this);
        return this;
    },

    addOne: function (user) {
        var singleUser = new App.Views.User({ model: user});
        console.log(singleUser.render().el);
        this.$el.append(singleUser.render().el);
        return this;
    }
});