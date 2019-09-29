function requestResend() {
    let tar = $("#resend-prompt");
    let email = $("#email").val();
    $.ajax({
        type:"POST",
        url:'/verifyResend',
        data:{
            email: email
        },
        success : function(data){
            console.log(data);
            let res = data['message'];
            if(res == 'notExist') {
                tar.html("<h6>User not exist, please <a href='/signup'>signup</a> first.</h6>");
            } else if(res == 'active') {
                tar.html("<h6>User is active, please <a href='/login'>login</a>.</h6>");
            } else {
                tar.html("<h6>A new email has been sent to {$email}.</h6>");
            }
        }
    });
}

$("#resend").click(function () {
    requestResend();
});


