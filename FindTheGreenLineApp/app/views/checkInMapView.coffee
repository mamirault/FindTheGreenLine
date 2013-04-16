View     = require './view'
app      = require '../application'
template = require './templates/checkInMapTemplate'
helpers  = require '../lib/helpers'
Location = require '../models/location'
Stations = require '../collections/stations'
CheckIns = require '../collections/checkIns'
Icons    = require '../lib/icons'

class CheckInMapView extends View
  isCheckInMap      : true
  el         : "#check-in-map-view-container"
  mapId      : "check-in-map-container"
  template   : template
  location   : null
  stations   : new Stations()
  checkIns   : new CheckIns()
  infoWindow : new google.maps.InfoWindow()
  markers    : {}
  infoWindowContent : {}
  events: 
      'click #check-in-map-to-check-in': 'toCheckIn'
      'click #check-in-map-to-home' : 'toHome'

  initialize: =>
    @mapOptions =
      center    : new google.maps.LatLng(42.347031, -71.082788)
      zoom      : 15
      mapTypeId : google.maps.MapTypeId.ROADMAP

    @checkIns.fetch @createInfoWindowContent, true
    @stations.fetch

  afterRender: =>
    @map = new google.maps.Map document.getElementById(@mapId), @mapOptions
    @transitLayer = new google.maps.TransitLayer()
    @transitLayer.setMap @map
    if !@location.isDefault
      @marker.setMap @map

    @renderStations()

  createInfoWindowContent: (render) =>
    @infoWindowContent = {}
    for checkIn in @checkIns.models
      info = 
        direction : checkIn.get "direction"
        time      : new Date checkIn.get "time"
      if !@infoWindowContent[checkIn.get "name"]
        @infoWindowContent[checkIn.get "name"] = []
      @infoWindowContent[checkIn.get "name"].push info

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

module.exports = CheckInMapView