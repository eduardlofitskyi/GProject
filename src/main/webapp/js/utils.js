function validatePassword() {
    console.log('inside');
    var btn_submit = document.getElementById("sbm_btn");
    var password = document.getElementById("password");
    var confirm_password = document.getElementById("confirm_password");

    if (password.value != confirm_password.value) {
        btn_submit.disabled = true;
    } else {
        btn_submit.disabled = false;
    }
}