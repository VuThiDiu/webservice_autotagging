var prototype = HomeController.prototype;

function HomeController(){
    prototype.hideLoading();
    $(function loadData(){
            if(window.localStorage.getItem('autoTaggingSystemToken')!=null){
                var loginResponse = JSON.parse(localStorage.getItem("autoTaggingSystemToken"));
                let fileInput = document.getElementById("file-input");
                $("#file-input").on('change', function(){
                    if(fileInput.files.length <= 10){
                                for (i of fileInput.files){
                                    let formData = new FormData();
                                    formData.append("file", i);
                                    prototype.AutoTaggingAPI(formData, loginResponse);
                            }
                    }else{
                        alert("At most 10 files at times");
                    }
                });
                        $("button.save_change").on("click", function (){
                                var tagCategoryChange = $("#edit_tagCategory :selected").val();
                                var tagColorChange = "";
                                var id = $(".modal-body#id").val();
                                let spanDot = document.querySelectorAll("span.dot");
                                if(spanDot.length > 0){
                                    var tagColorChange = spanDot[0].parentNode.getElementsByTagName("label")[0].style.color;
                                    prototype.saveChangeTag1(id, tagCategoryChange, tagColorChange);
                                }else{
                                    prototype.saveChangeTag2(id, tagCategoryChange);
                                }
                                prototype.prototype.tagController();
                            }
                            );

                        $("button#saveButton").on("click", function(){
                            if(prototype.validateForm() == true){
                               let description = $("#description").val();
                               let quantityInStock = parseInt($("#quantityInStock").val());
                               let cost = parseInt($("#cost").val());
                               let address = $("#address").val();
                               let discount = parseInt($("#discount").val());
                               let userId = loginResponse.id;
                               let title = $("#title").val();
                               var uploadProduct ={
                                   "userId" : userId,
                                   "description" : description,
                                   "quantityInStock" : quantityInStock,
                                   "price" : cost,
                                   "dateTime" : new Date(),
                                   "discount" : discount,
                                   "address" : "Ha Noi",
                                   "title": title
                               }
                               prototype.uploadProduct(loginResponse, uploadProduct);
                            }
                        });

                        $('#btn_clipboard').on('click',  function()
                             {
                                 var copyText = document.getElementById("url");
                                   copyText.select();
                                   copyText.setSelectionRange(0, 99999); /* For mobile devices */
                                   navigator.clipboard.writeText(copyText.value);
                                   alert("Copied the text: " + copyText.value);

                             });
            }else{
                alert ("Login plz");
                window.location.replace("/login");
            }});


}

prototype.AutoTaggingAPI = function(fileInput, loginResponse){
    $.ajax({
            method:"post",
                url: `autoTaggingImage`,
            headers:{
                "Authorization": `Bearer ${loginResponse.accessToken}`,
            },
            beforeSend: function(xhr){
                  prototype.showLoading();
              },
            data:fileInput,
            processData: false,
            contentType: false
        })
        .done(function(response){
                prototype.hideLoading();
                $("#result").val(JSON.stringify(response, null, 4)) ;
                $("#preview").attr("src", response.imageURL);
                $("#result")[0].style.height = "450px";

        })
        .fail(function(response){
            prototype.hideLoading();
            alert("fail to upload image");
        });
}

prototype.showLoading = function() {
            $(".loading").show()
        }

prototype.hideLoading= function () {
            $(".loading").hide()
        }
var homeController;
$(document).ready(function(){
    homeController = new HomeController();

})




