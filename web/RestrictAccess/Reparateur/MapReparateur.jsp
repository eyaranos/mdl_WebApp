<%--
  User: Enzo
  Date: 31-10-17
  Time: 14:57
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Maps</title>
    <%@ include file="/resources/includeCss.html" %>
</head>

<body>
<div class="wrap-all">
    <div class="container-fluid">
        <%@ include file="/resources/header.jsp" %>
    </div>

    <h3 style="margin-top:70px;">Map</h3>
    <p>Vous pouvez voir sur cette carte, l'ensemble des vélos</p>
    <div id="map"></div>


</div>
<%@ include file="/resources/includeJS.html" %>
<script>

    function initMap() {

        var uluru = {lat: 50.466604, lng: 4.860509};
        var map = new google.maps.Map(document.getElementById('map'), {
            zoom: 11,
            center: uluru
        });


        var results_velo = ${liste_velo};

        $.each(results_velo, function(key, data){

            var latLng = new google.maps.LatLng(data.latitude, data.longitude);

            var marker = new google.maps.Marker({
                position: latLng,
                map: map
            });

            var infowindow = new google.maps.InfoWindow({
                content: "id vélo : " +(data.id).toString()
            });

            bindInfoWindow(marker, map, infowindow);

        });

        var results_velo_casser = ${liste_velo_casser};

        if(results_velo_casser.length != 0){
            $.each(results_velo_casser, function(key, data){

                var latLng = new google.maps.LatLng(data.latitude, data.longitude);

                var marker = new google.maps.Marker({
                    position: latLng,
                    map: map,
                    icon: "http://maps.google.com/mapfiles/ms/icons/blue-dot.png"
                });

                var infowindow = new google.maps.InfoWindow({
                    content: "id vélo cassé : " +(data.id).toString()
                });

                bindInfoWindow(marker, map, infowindow);

            });
        }

        var results_zone = ${liste_zone};

        $.each(results_zone, function(key, data) {

            var cityCircle = new google.maps.Circle({
                strokeColor: '#f8c74b',
                strokeOpacity: 0.8,
                strokeWeight: 2,
                fillColor: '#f8c74b',
                fillOpacity: 0.35,
                map: map,
                center: {lat:data.lat, lng:data.lon},
                radius: (data.rayon)
            });
        });


    var results_station = ${liste_station};

    $.each(results_station, function(key, data) {

        var cityCircle = new google.maps.Circle({
            strokeColor: '#f8251d',
            strokeOpacity: 0.8,
            strokeWeight: 2,
            fillColor: '#f84e39',
            fillOpacity: 0.35,
            map: map,
            center: {lat:data.latitude, lng:data.longitude},
            radius: (data.rayon)
        });
    });
    }

    function bindInfoWindow(marker, map, infowindow) {
        google.maps.event.addListener(marker, 'click', function () {
            infowindow.open(map, marker);
        });
    }


</script>
<script async defer
        src="https://maps.googleapis.com/maps/api/js?key=AIzaSyB7z2Op2h0qoY13tUnGktmTkqbgs-dIT4A&callback=initMap">
</script>
</body>
</html>