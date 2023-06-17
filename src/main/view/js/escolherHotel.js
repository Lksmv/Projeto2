let map;
let markers = [];

function initMap() {
    map = new google.maps.Map(document.getElementById("mapaHotel"), {
        center: { lat: -34.397, lng: 150.644 },
        zoom: 8,
    });
}

function searchHotels() {

    for (let i = 0; i < markers.length; i++) {
        markers[i].setMap(null);
    }
    markers = [];

    const city = document.getElementById("cityInput").value;
    const service = new google.maps.places.PlacesService(document.createElement('div'));
    const request = {
        query: `hotels in ${city}`,
        type: 'lodging'
    };

    service.textSearch(request, function (results, status) {
        if (status === google.maps.places.PlacesServiceStatus.OK) {
            let bounds = new google.maps.LatLngBounds();

            for (let hotel of results) {
                let marker = new google.maps.Marker({
                    position: hotel.geometry.location,
                    map: map,
                    title: hotel.name
                });

                markers.push(marker);

                bounds.extend(marker.getPosition());
            }

            map.fitBounds(bounds);
        } else {
            console.error(status);
        }
    });
}