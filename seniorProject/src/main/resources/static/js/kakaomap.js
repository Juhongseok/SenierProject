let markers = [];
let mapContainer = document.getElementById('map'),
    mapOption = {
        center: new kakao.maps.LatLng(37.566826, 126.9786567),
        level: 3
    };
let map = new kakao.maps.Map(mapContainer, mapOption);
let ps = new kakao.maps.services.Places();

showMarker();

//기존에 저장되어 있던 위치들 찾아오기
function showMarker() {
    let mapId = window.location.pathname.split('/')[2];
    $.ajax({
        method: 'get',
        url: "/location/list",
        data: {"mapId": mapId},
        success: function (data) {
            for (let i = 0; i < data.length; i++) {
                let lat = data[i].latitude;
                let lng = data[i].longitude;
                let name = data[i].name;

                let marker = new kakao.maps.Marker({
                    map: map,
                    position: new kakao.maps.LatLng(lat, lng),
                    title: name
                });

                let iwContent = '<div class="customoverlay" style="padding:5px; width: max-content;">' +
                        '제목 : ' + name +
                        '<br><a href="#" style="color:blue" target="_blank">상세보기</a>';
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

function searchPlaces() {
    let keyword = document.getElementById('keyword').value;
    document.getElementById('keyword').value = '';

    if (!keyword.replace(/^\s+|\s+$/g, '')) {
        alert('키워드를 입력해주세요!');
        return false;
    }
    ps.keywordSearch(keyword, placesSearchCB);
}

function placesSearchCB(data, status, pagination) {
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

function displayMarker(place) {
    let placeName = place.place_name;

    let latLng = new kakao.maps.LatLng(place.y, place.x);
    let marker = new kakao.maps.Marker({
        map: map,
        position: latLng
    });

    let infoWindow = new kakao.maps.InfoWindow({zIndex: 1});
    let content = '<div style="padding:5px;font-size:12px;">' + placeName + '</div>' +
        `<br><button onclick="addLocation(${latLng.getLat()}, ${latLng.getLng()}, '${placeName}')">저장</button>`;

    kakao.maps.event.addListener(marker, 'click', function() {
        infoWindow.setContent(content);
        infoWindow.open(map, marker);
    });
}

function addLocation(lat, lng, placeName) {
    let currentURL = location.href;
    window.location.href=currentURL.substr(0, currentURL.length - 5)
        + "/add?lat=" + lat +"&lng=" + lng + "&placeName="+placeName;
}