var map;
var flightPlanCoordinates;

function initMap() {
    flightPlanCoordinates = JSON.parse(document.getElementById('way').innerHTML);

    map = new google.maps.Map(document.getElementById('map'), {
        zoom: 16,
        center: flightPlanCoordinates[0],
        mapTypeId: 'roadmap'
    });

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
