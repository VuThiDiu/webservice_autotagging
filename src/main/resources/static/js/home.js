var prototype = HomeController.prototype;
var dataType = "JSON";
function HomeController(){
    prototype.hideLoading();
    $("[name=radiosDataType]").on("change", function(){
        if($(this).val() == "JSON"){
            dataType = "JSON";
        }else{
            dataType = "XML";
        }

    });
    $(function loadData(){
            if(window.localStorage.getItem('autoTaggingSystemToken')!=null){
                var loginResponse = JSON.parse(localStorage.getItem("autoTaggingSystemToken"));
                let fileInput = document.getElementById("file-input");
                $("#file-input").on('change', function(){
                    if(fileInput.files.length <= 10){
                                for (i of fileInput.files){
                                    let formData = new FormData();
                                    formData.append("file", i);
                                    $("#result")[0].style.height = "450px";
                                    // xd kiểu dữ liệu trả về
                                    if (dataType == "JSON"){
                                        prototype.AutoTaggingAPIForJSON(formData, loginResponse);
                                    }else{
                                        prototype.AutoTaggingAPIForXML(formData, loginResponse);
                                    }

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
prototype.AutoTaggingAPIForJSON = function(fileInput, loginResponse){
    $.ajax({
            method:"post",
                url: `autoTaggingImage`,
            headers:{
                "Authorization": `Bearer ${loginResponse.accessToken}`,
                "Accept" : "application/json"
            },
            beforeSend: function(xhr){
                  prototype.showLoading();
              },
            data:fileInput,
            processData: false,
            contentType: false,
            success : function(response){
                    prototype.hideLoading();
                    $("#result").val(JSON.stringify(response, null, 4)) ;
                    $("#preview").attr("src", response.imageURL);
            },
            error: function(e){
            prototype.hideLoading();
                        alert("fail to upload image");
            }
        })
}
prototype.AutoTaggingAPIForXML = function(fileInput, loginResponse){
    $.ajax({
            method:"post",
                url: `autoTaggingImage`,
            headers:{
                "Authorization": `Bearer ${loginResponse.accessToken}`,
                "Accept" : "application/xml"
            },
            beforeSend: function(xhr){
                  prototype.showLoading();
              },
            data:fileInput,
            processData: false,
            contentType: false,
            dataType: "text",
            success : function(response){
                    prototype.hideLoading();
                    response = prototype.parseXML(response);
                    $("#result").val(response) ;
                     $("#preview").attr("src", response.split("<imageURL>")[1].split("</imageURL>")[0]);
            },
            error: function(e){
            prototype.hideLoading();
                        alert("fail to upload image");
            }
        })
}
prototype.parseXML = function(sourceXml)
{
    var xmlDoc = new DOMParser().parseFromString(sourceXml, 'application/xml');
    var xsltDoc = new DOMParser().parseFromString([
        // describes how we want to modify the XML - indent everything
        '<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform">',
        '  <xsl:strip-space elements="*"/>',
        '  <xsl:template match="para[content-style][not(text())]">', // change to just text() to strip space in text nodes
        '    <xsl:value-of select="normalize-space(.)"/>',
        '  </xsl:template>',
        '  <xsl:template match="node()|@*">',
        '    <xsl:copy><xsl:apply-templates select="node()|@*"/></xsl:copy>',
        '  </xsl:template>',
        '  <xsl:output indent="yes"/>',
        '</xsl:stylesheet>',
    ].join('\n'), 'application/xml');

    var xsltProcessor = new XSLTProcessor();
    xsltProcessor.importStylesheet(xsltDoc);
    var resultDoc = xsltProcessor.transformToDocument(xmlDoc);
    var resultXml = new XMLSerializer().serializeToString(resultDoc);
    return resultXml;
};

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




