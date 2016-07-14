App.Views.App = Backbone.View.extend({
   initialize: function () {
       vent.on("user:edit", this.editUser, this);
       vent.on("user:add", this.addUser, this);

       var addUser = new App.Views.AddUser({ collection: App.users});
       var editUser= new App.Views.EditUser({ collection: App.users});
       var allUsers = new App.Views.Users({ collection: App.users}).render();

       $("#user-table").append(allUsers.el);
   },

    editUser: function (user) {
        var editUserView = new App.Views.EditUser({ model : user}).render();
        $(".form-content").html(editUserView.el);
    },

    addUser: function () {
        var createUserView = new App.Views.AddUser({ collection: App.users}).render();
        $(".form-content").html(createUserView.el);
    }
});