document.getElementById("id").addEventListener("keypress", function(event) {
    if (event.key === "Enter") {
        event.preventDefault();
        document.getElementById("submitButton").click();
    }
});

function add(){
    let param = {
        id:$("#id").val()
    };

    $.ajax({
        type:"POST",
        url:"/friend/api/add",
        data: JSON.stringify(param),
        contentType:"application/json; charset=utf-8",
    }).done(function(){
        alert("친구추가 성공");
        location.reload();
    }).fail(function(response){
        console.log(response.status + " " + response.responseText)
        makingErrorField(response);
    });
}

const makingErrorField = function(response){
    const errorFields = response.responseJSON.error;
    $( 'p.error-message' ).remove();
    $('#inputId').append('<p class="field-error">'+errorFields+'</p>')
};