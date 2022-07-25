function accept(userId, mapId) {
    $.ajax({
        type:"POST",
        url:"/map/give_auth/"+mapId+"/"+userId+"",
        contentType:"application/json; charset=utf-8",
    }).done(function(){
        alert("지도 사용 승인");
        location.reload();
    }).fail(function(response){
        console.log(response.status + " " + response.responseText)
    });
}

function updateSmallSubject(smallSubjectId){
    const inputValue = document.getElementById(smallSubjectId).value;
    $.ajax({
        type:"PATCH",
        url:"/subject/" + smallSubjectId,
        contentType:"application/json; charset=utf-8",
        data: inputValue
    }).done(function(response){
        alert("수정 완료");
        inputValue.value = response
    }).fail(function(response){
        console.log(response.status + " " + response.responseText)
    });
}

function addSubject(){
    const innerDiv = document.createElement('div');
    const outerDiv = document.createElement('div');
    const input = document.createElement('input');
    const button = document.createElement('button');
    const addButton = document.getElementById('addSmallSubjectButton');
    const cancelButton = document.getElementById('cancel');

    outerDiv.id = 'addDiv';
    outerDiv.className = 'row mb-3';

    innerDiv.className = 'col-sm-10';
    input.id = "newSubject";
    input.className = "form-control form-control-sm";
    input.style.marginRight = '10px';
    input.placeholder = "소 주제 이름 입력";

    button.className = "btn btn-primary col-sm-2";
    button.textContent = '추가';
    button.type = 'button';
    button.onclick = function add(){
        const data = {
            mapId: $('#mapId').val(),
            subjectName: $('#newSubject').val()
        }

        $.ajax({
            type:"POST",
            url:"/subject/add",
            contentType:"application/json; charset=utf-8",
            data: JSON.stringify(data)
        }).done(function(response){
            $("#smallSubjectWrap").empty();

            for (const smallSubject of response) {
                const id = smallSubject.subjectId;
                const name = smallSubject.subjectName;

                $("#smallSubjectWrap").append(
                    `<div class="row mb-3">
                            <div class="col-sm-10">
                                <input type="text" class="form-control form-control-sm"
                                       id="${id}" value="${name}"
                                       style="margin-right: 10px">
                            </div>
                            <button type="button" class="btn btn-primary col-sm-2"
                                    onclick="updateSmallSubject(${id});">추가
                            </button>
                        </div>
                        <br>`
                )

            }
            alert("추가완료");
        }).fail(function(response){
            console.log(response.status + " " + response.responseText)
        });

        addButton.style.visibility = 'visible';
        cancelButton.style.visibility = 'hidden';
        $('div').remove('#addDiv');
    };

    innerDiv.appendChild(input);

    outerDiv.appendChild(innerDiv);
    outerDiv.appendChild(button);
    outerDiv.appendChild(document.createElement('br'));

    document.getElementById('smallSubjectWrap').appendChild(outerDiv);

    addButton.style.visibility = 'hidden';
    cancelButton.style.visibility = 'visible';
}

function cancelAddSubject(){
    const addButton = document.getElementById('addSmallSubjectButton');
    const cancelButton = document.getElementById('cancel');

    addButton.style.visibility = 'visible';
    cancelButton.style.visibility = 'hidden';

    $('div').remove('#addDiv');
}