App.Views.EditUser = Backbone.View.extend({

    events: {
        "submit #edit-form": "submit",
        "click button.cancel": "cancel"
    },

    submit: function (e) {
        e.preventDefault();

        this.password = this.$("#edit_password");
        this.email = this.$("#edit_email");
        this.firstName = this.$("#edit_firstName");
        this.lastName = this.$("#edit_lastName");
        this.birthday = this.$("#edit_birthday");
        this.role = this.$("#edit_role");

        this.model.save({
            password: this.password.val(),
            email: this.email.val(),
            firstName: this.firstName.val(),
            lastName: this.lastName.val(),
            birthday: this.birthday.val(),
            role: {
                id: null,
                name: this.role.val()
            }
        });

        this.remove();
    },
    
    cancel: function () {
        this.remove();
    },

    template: App.template("edit-template"),

    render: function () {
        var html = this.template(this.model.toJSON());
        this.$el.html(html);
        return this;
    }
});