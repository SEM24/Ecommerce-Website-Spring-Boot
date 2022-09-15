function emailCheck() {
    if ($("#email").val() == "") {
        $("#email").addClass('is-invalid');
        return false;
    } else {
        var regMail = /^([_a-zA-Z0-9-]+)(\.[_a-zA-Z0-9-]+)*@([a-zA-Z0-9-]+\.)+([a-zA-Z]{2,3})$/;
        if (regMail.test($("#email").val()) == false) {
            $("#email").addClass('is-invalid');
            return false;
        } else {
            $("#email").removeClass('is-invalid');
            $('#next-form').collapse('show');
        }

    }
}
let password = document.getElementById('password');
let icon1 = document.getElementById('icon1');
icon1.addEventListener('click', () => {
    password.type =
        password.type == 'password' ? 'text' : 'password';
    icon1.className =
        icon1.className == 'fas fa-eye' ? 'fas fa-eye-slash' : 'fas fa-eye';
});