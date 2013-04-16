View     = require './view'
app      = require '../application'
template = require './templates/mapViewTemplate'
helpers  = require '../lib/helpers'
Location = require '../models/location'
Stops    = require '../collections/stops'
Stations = require '../collections/stations'
CheckIngs = require '../collections/checkIns'
Icons    = require '../lib/icons'

class MapView extends View
  isMap      : true
  el         : "#map-view-container"
  mapId      : "map-container"
  template   : template
  location   : null
  stops      : new Stops()
  stations   : new Stations()
  checkins   : new Checkins()
  infoWindow : new google.maps.InfoWindow()
  markers    : {}
  infoWindowContent : {}
  events: 
      'click #map-to-check-in': 'toCheckIn'
      'click #map-to-home' : 'toHome'

  initialize: =>
    @location = new Location @locationCallback, false
    @mapOptions =
      center    : new google.maps.LatLng(42.347031, -71.082788)
      zoom      : 15
      mapTypeId : google.maps.MapTypeId.ROADMAP
    @stops.fetch @createInfoWindowContent, true
    @stations.fetch @render
    @checkins.fetch @afterRender


  afterRender: =>
    @map = new google.maps.Map document.getElementById(@mapId), @mapOptions
    @transitLayer = new google.maps.TransitLayer()
    @transitLayer.setMap @map
    if !@location.isDefault
      @marker.setMap @map

    @renderStations()
    @renderCheckins()

  locationCallback: (location) =>
    app.newLocation location
    @location = location
    @mapOptions.center = new google.maps.LatLng location.coords.latitude, location.coords.longitude
    @marker = new google.maps.Marker
      position: @mapOptions.center
      title: "You"
      icon: Icons.person
    @afterRender()

  createInfoWindowContent: (render) =>
    @infoWindowContent = {}
    for stop in @stops.models
      info = 
        direction : stop.get "direction"
        time      : new Date stop.get "time"
      if !@infoWindowContent[stop.get "name"]
        @infoWindowContent[stop.get "name"] = []
      @infoWindowContent[stop.get "name"].push info

    if render
      @afterRender()

  getContentFromName: (name) =>
    infos = @infoWindowContent[name]
    if !infos
      @infoWindowContent[name] = []
      infos = []
    content = "<strong>#{name}</strong>"
    for info in infos
      time = new Date info.time
      timeReadable = "#{helpers.convertToTimeString time}"
      content += "<div>Direction: #{info.direction}<br>Arrival Time: #{timeReadable}</div>"
    content

  renderStations: =>
    for station in @stations.models
      @createStationMarker station

  createStationMarker: (station) =>
    name = station.get "name"
    content = @getContentFromName name
    @markers[name] = new google.maps.Marker
      position: new google.maps.LatLng station.get("latitude"), station.get("longitude")
      title: name
      icon: Icons.station
    @markers[name].setMap @map

    google.maps.event.addListener @markers[name], 'click', () =>
      @infoWindow.setContent content
      @infoWindow.open @map, @markers[name]

  toHome: () =>
    helpers.toHome()

  toCheckIn: () =>
    helpers.toCheckIn()

module.exports = MapView