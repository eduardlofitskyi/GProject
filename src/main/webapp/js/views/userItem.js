App.Views.User = Backbone.View.extend({

    initialize: function () {
        this.model.on("destroy", this.un_render, this)
        this.model.on("change", this.render, this)
    },

    tagName: "tr",

    events: {
        "click a.delete": "removeUser",
        "click a.edit": "editUser"
    },

    removeUser: function() {
        this.model.destroy();
    },

    editUser: function () {
        vent.trigger("user:edit", this.model);
    },

    un_render: function () {
        this.remove();
    },

    template: App.template("userItem"),

    render: function () {
        this.$el.html(this.template( this.model.toJSON()));
        return this;
    }
});