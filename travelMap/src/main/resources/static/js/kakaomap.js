let mapContainer = document.getElementById('map'),
    mapOption = {
        center: new kakao.maps.LatLng(37.566826, 126.9786567),
        level: 3
    };
let map = new kakao.maps.Map(mapContainer, mapOption);
let ps = new kakao.maps.services.Places();
let savedMarker = [];
let findMarker = [];
/**
 * 기본 페이지 열때 저장 위치 마커 표시
 */
showMarker();

/**
 * 기존에 저장되어 있던 위치들 찾아오기
 */
function showMarker() {
    let mapId = window.location.pathname.split('/')[2];
    $.ajax({
        method: 'get',
        url: "/locations",
        data: {"mapId": mapId},
        success: function (data) {
            for (let i = 0; i < data.length; i++) {
                let locationId = data[i].locationId;
                let lat = data[i].latitude;
                let lng = data[i].longitude;
                let name = data[i].name;

                let marker = new kakao.maps.Marker({
                    map: map,
                    position: new kakao.maps.LatLng(lat, lng),
                    title: name
                });

                savedMarker.push(marker);

                let ref = "/location/" + locationId + "/update?mapId=" + mapId
                let iwContent =
                    `<div class="customoverlay" style="padding:5px; width: max-content;">
                        제목 :  ${name} 
                        <br>
                        <a href=${ref} style="color:blue">
                            상세보기
                        </a>
                    </div>`;
                kakao.maps.event.addListener(marker, 'click', function () {
                    new kakao.maps.InfoWindow({
                        content: iwContent,
                        position: new kakao.maps.LatLng(lat, lng),
                        removable: true,
                        zIndex: 1
                    }).open(map, marker);
                });

            }
        },
        error: function (request, status, error) {
            alert("code: " + status + "\n error: " + error);
        }
    })
}

/**
 * 검색 값 가져오기
 * @returns {boolean}
 */
function searchPlaces() {
    let keyword = document.getElementById('keyword').value;
    document.getElementById('keyword').value = '';

    if (!keyword.replace(/^\s+|\s+$/g, '')) {
        alert('키워드를 입력해주세요!');
        return false;
    }
    ps.keywordSearch(keyword, placesSearchCB);
}

function searchSavedPlaces() {
    let param = {
        mapId: $("#mapId").val(),
        bigSubject: $("#bigSubject").val(),
        smallSubject: $("#smallSubject").val()
    }
    $.ajax({
        type: "POST",
        url: "/location/search",
        data: JSON.stringify(param),
        contentType: "application/json; charset=utf-8",
    }).done(function (data) {
        alert("위치 검색 성공");
        for (let i = 0; i < savedMarker.length; i++) {
            savedMarker[i].setVisible(false);
        }

        for (let i = 0; i < data.length; i++) {
            let saveLat = data[i]["latitude"];
            let saveLng = data[i]["longitude"];

            for (let j = 0; j < savedMarker.length; j++) {
                const position = savedMarker[j].getPosition();
                const lat = position.getLat();
                const lng = position.getLng();

                if (saveLat == lat && saveLng == lng){
                    savedMarker[j].setVisible(true);
                    break;
                }
            }
        }

    }).fail(function (response) {
        console.log(response.status + " " + response.responseText)
    });
}

/**
 * 위치 검색
 * @param data
 * @param status
 */
function placesSearchCB(data, status) {
    for (let i = 0; i < findMarker.length; i++) {
        findMarker[i].setVisible(false);
    }
    findMarker = [];
    if (status === kakao.maps.services.Status.OK) {
        let bounds = new kakao.maps.LatLngBounds();
        for (let i=0; i<data.length; i++) {
            displayMarker(data[i]);
            bounds.extend(new kakao.maps.LatLng(data[i].y, data[i].x));
        }
        map.setBounds(bounds);
    } else if (status === kakao.maps.services.Status.ZERO_RESULT) {
        alert('검색 결과가 존재하지 않습니다.');
        return;
    } else if (status === kakao.maps.services.Status.ERROR) {
        alert('검색 결과 중 오류가 발생했습니다.');
        return;
    }
}

/**
 * 마커 overlay 모두 닫기
 * @type {*[]}
 */
let overlayArr = [];
let selectedMarker = null;
function closeAllOverlay() {
    for(let i=0; i<overlayArr.length; i++){
        overlayArr[i].setMap(null);
    }
}

/**
 * 검색으로 찾은 위치 마커 표시
 * @param place
 */
function displayMarker(place) {
    let placeName = place.place_name;

    let latLng = new kakao.maps.LatLng(place.y, place.x);
    let marker = new kakao.maps.Marker({
        map: map,
        position: latLng
    });

    findMarker.push(marker);
    let content =
        `<div class="wrap">
            <div class="infoWindow">
                <div style="padding:5px;font-size:12px;"> 
                    ${placeName}  
                </div>
                <br>
                <button class="btn btn_primary btn_sm" 
                    onclick="addLocation(${latLng.getLat()}, ${latLng.getLng()}, '${placeName}')">저장
                </button>
            </div>
        </div>`;

    for (const savedMarkerElement of savedMarker) {
        if(marker.getPosition().getLat() == savedMarkerElement.getPosition().getLat()
            && marker.getPosition().getLng() == savedMarkerElement.getPosition().getLng()){
            content =
                `<div class="wrap">
                    <div class="infoWindow">
                        <div style="padding:5px;font-size:12px;"> 
                            제목 :  ${savedMarkerElement.getTitle()} 
                        </div>
                    </div>
                </div>`;
        }
    }

    let overlay = new kakao.maps.CustomOverlay({
        content: content,
        position: marker.getPosition()
    });

    overlayArr.push(overlay);

    /**
     * 클릭 시 overlay open
     * 나머지 마커들은 infoWindow close
     **/
    kakao.maps.event.addListener(marker, 'click', function() {
        closeAllOverlay()
        if (selectedMarker != marker) {
            overlay.setMap(map);
            selectedMarker = marker;
        } else {
            selectedMarker = null;
        }
    });
}

/**
 * 위치 저장 페이지 호출
 * @param lat
 * @param lng
 * @param placeName
 */
function addLocation(lat, lng, placeName) {
    const mapId = document.getElementById('mapId').value
    window.location.href="/location/map/" + mapId + "?lat=" + lat +"&lng=" + lng + "&placeName="+placeName;
}