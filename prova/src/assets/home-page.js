function MostraMappa() {
  const centro = { lat: 39.3534, lng: 16.2273 };
  alert("Ciao");
  const map = new google.maps.Map(document.getElementById("map"), {
    zoom: 14,
    center: centro,
  });

  new google.maps.Marker({
    position: centro,
    map: map,
    title: "Centro di Rende"
  });
}


