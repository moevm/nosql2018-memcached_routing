var map;
var flightPlanCoordinates;

function initMap() {
    map = new google.maps.Map(document.getElementById('map'), {
        zoom: 3,
        center: {lat: 0, lng: 0},
        mapTypeId: 'roadmap'
    });

    flightPlanCoordinates = JSON.parse(document.getElementById('way').innerHTML);

    var flightPath = new google.maps.Polyline({
        path: flightPlanCoordinates,
        geodesic: true,
        strokeColor: '#FF0000',
        strokeOpacity: 1.0,
        strokeWeight: 2
    });

    var myLatLng = JSON.parse(document.getElementById('coords').innerHTML);

    myLatLng.forEach(function (element) {
        new google.maps.Marker({
            position: element,
            map: map
        });
    });

    flightPath.setMap(map);
}

function newLocation(newLat, newLng) {
    map.setCenter({
        lat: newLat,
        lng: newLng
    });
}

newLocation(flightPlanCoordinates[0])