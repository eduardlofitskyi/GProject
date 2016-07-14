App.Views.AddUser = Backbone.View.extend({
    
    events: {
        "submit #add-form" : "addUser",
        "click button.cancel" : "cancel"
    },

    template: App.template("add-template"),
    
    addUser: function (e) {
        e.preventDefault();

        this.collection.create({
            login: this.$("#add_login").val(),
            password: this.$("#add_password").val(),
            email: this.$("#add_email").val(),
            firstName: this.$("#add_firstName").val(),
            lastName: this.$("#add_lastName").val(),
            birthday: this.$("#add_birthday").val(),
            role: {
                name: this.$("#add_role").val()
            }
        }, { wait: true });
        
        this.remove();
    },

    cancel: function () {
        this.remove();
    },

    render: function () {
        var html = this.template();
        this.$el.html(html);
        return this;
    }
});
