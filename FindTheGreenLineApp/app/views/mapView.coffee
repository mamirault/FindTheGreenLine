View     = require './view'
app      = require '../application'
template = require './templates/mapViewTemplate'
helpers  = require '../lib/helpers'
Location = require '../models/location'

class MapView extends View
  el       : "#map-view-container"
  mapId    : "map-container"
  template : template
  location : null
  stops    : null

  initialize: =>
    @location = new Location @locationCallback, false
    @mapOptions =
      center    : new google.maps.LatLng(42.347031, -71.082788)
      zoom      : 15
      mapTypeId : google.maps.MapTypeId.ROADMAP
    @stops = new Stops()

  afterRender: =>
    @map = new google.maps.Map document.getElementById(@mapId), @mapOptions
    @transitLayer = new google.maps.TransitLayer()
    @transitLayer.setMap @map
    if !@location.isDefault
      @marker.setMap @map

  locationCallback: (location) =>
    @location = location
    @mapOptions.center = new google.maps.LatLng location.coords.latitude, location.coords.longitude
    @marker = new google.maps.Marker
      position: @mapOptions.center
      title: "You"
    @afterRender()



module.exports = MapView