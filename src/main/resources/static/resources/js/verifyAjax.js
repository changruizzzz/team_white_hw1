function verifyNeedSignUp(){
    $.ajax({
        type:"POST",
        url:'/verifyNeedSignUpEmail/',
        success:function(){
            $('#login-error').text("Please sign up at the first.");
            var link = "<a href=\"/signup\" th:href=\"@{/signup}\" class=\"txt3\">Sign Up</a>";
            $('#login-error').appendChild(link);
        },
        error:function(){
            $('#login-error').text("Please enter an email at first.");
        }
    });
}

function verifyNeedLogIn() {
    $.ajax({
        type:"POST",
        url:'/verifyNeedLogInEmail/',
        success:function(){
            $('#login-error').text("You already register");
            var link = "<a href=\"/signup\" th:href=\"@{/signup}\" class=\"txt3\">Log In</a>";
            $('#login-error').appendChild(link);
        },
        error:function(){
            $('#login-error').text("Please enter an email at first.");
        }
    });
}

$('#signin-btn').click(function() {
    verifyNeedSignUp();
});