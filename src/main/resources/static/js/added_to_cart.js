$(document).ready(function () {
    $("#addToCartBtn").on("click", function (e){
        addToCart();
    })
});
function addToCart(){
    quantity = $("#quantity" + productId).val();

    url = contextPath + "basket/add/" + productId + "/" + quantity;

    $.ajax({
        type: "POST",
        url: url,
        beforeSend: function (xhr){
            xhr.setRequestHeader(csrfHeaderName, csrfValue);
        }
    }).done(function (response){
        $("#modalTitle").text("Shopping basket");
        $("#modalBody").text(response);
        $("#myModal").modal();
    }).fail(function (){
        $("#modalTitle").text("Shopping basket");
        $("#modalBody").text("Error while adding product to shopping basket.");
        $("#myModal").modal();
    });
}