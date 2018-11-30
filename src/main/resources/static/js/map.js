var geocoder;
var map;
var address = "new york city";
function initMap() {
    map = new google.maps.Map(document.getElementById('map'), {
        zoom: 8,
        center: {lat: 0, lng: 0}
    });
    geocoder = new google.maps.Geocoder();
    codeAddress(geocoder, map);
}

function newLocation(newLat,newLng)
{
    map.setCenter({
        lat : newLat,
        lng : newLng
    });
}

var Mapss = function geoadres(adress) {
    var resultlat = ''; var resultlng = '';
    $.ajax({
        async: false,
        dataType: "json",
        url: 'http://maps.google.com/maps/api/geocode/json?address=' + adress,
        success: function(data){
            for (var key in data.results) {
                resultlat = data.results[key].geometry.location.lat;
                resultlng = data.results[key].geometry.location.lng;
            } }
    });
    return { lat: resultlat, lng: resultlng}
};


