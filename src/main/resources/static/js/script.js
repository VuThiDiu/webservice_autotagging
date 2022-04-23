var prototype = Controller.prototype;
//var token = $("meta[name='_csrf']").attr("content");
//var header = $("meta[name='_csrf_header']").attr("content");
//var baseController = BaseController.prototype;
function Controller(){
    document.querySelector('.img__btn').addEventListener('click', function() {
      document.querySelector('.cont').classList.toggle('s--signup');
    });

    $("button.signIn").click(function(){
        var username = $("#usernameLogin").val();
        var password = $("#passwordLogin").val();
        const json = {
            "username" : username,
            "password" : password
        }
        prototype.callAPILogin(json);
    });

    $("button.signUp").click(function(){
        var username = $("#username").val();
        var password = $("#password").val();
        var confirmPass = $("#confirm").val();
        var json = {
            'username' : username,
            'password' : password
        }
        if(prototype.checkPassword(password, confirmPass)){
            prototype.register(json);
        }
    })
}


prototype.callAPILogin = function(json){
        $.ajax({
                type:'POST',
                url: `login`,
                data:JSON.stringify(json),
                contentType: "application/json",
                beforeSend: function(xhr){
                },
                dataType: 'json',
                cache: false,
                processData: false,
                success : function(data, status){
                    if(status=='success'){
                        console.log(data);
                        localStorage.setItem("loginResponse",JSON.stringify(data) );
                        window.location.replace('/auto_tagging/home');
                    }else{
                        alert("account is not existed! Plx check  infomation login");
                        window.location.reload();
                    }
                },
                error: function (e){
                    alert("Error! Plz check infomation");
                    window.location.reload();
                }
            });
}


prototype.register = function( json){
        $.ajax({
                type:"POST",
                url: `register`,
                data:JSON.stringify(json),
                contentType: "application/json",
                beforeSend: function(xhr){
                },
                dataType: 'json',
                cache: false,
                processData: false,
                success : function(data, status){
                        if(status=='success'){
                            alert("Account is created successfully!");
                            window.location.replace("/login");
                        }else{
                            alert("Error!");
//                            window.location.reload();
                        }
                    },
                    error: function (e){
                        alert("Error! Plz check infomation");
//                        window.location.reload();
                    }
                });
}


//prototype.showAlertFail = function (body_text) {
//  $("#showDialogFailAlert #alertFail_body").text(body_text);
//  $("#showDialogFailAlert").modal('show');
//};


prototype.checkPassword = function(password, confirmPass){
       if(prototype.validatePassword(password)){
           if(password != confirmPass){
           alert("Password not match!");
           return false;
           }
       }else{
            alert("Password must have at least 1 uppercase letter, 1 lowercase letter, 1 number, 1 special character and must be at least 8 characters")
            return false;
       }
       return true;
}
prototype.validatePassword = function (value) {
 return /^(?=.*?[A-Z]).{8,}$/.test(value);
};

var controller ;
$(document).ready(function(){
     controller = new Controller();
});