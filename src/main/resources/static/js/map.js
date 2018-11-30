var geocoder;
var map;
function initMap() {
    map = new google.maps.Map(document.getElementById('map'), {
        zoom: 16,
        center: {lat: 50, lng: 50}
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

var lat = document.getElementById('lat').innerHTML;
var lng = document.getElementById('lng').innerHTML;
alert(lat)
alert(lng)
newLocation(parseFloat(lat), parseFloat(lng))

