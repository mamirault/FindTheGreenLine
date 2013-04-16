app = require 'application'
   
class AppRouter extends Backbone.Router
  routes:    
    "/thanks"  : "thanks"
    "/map"     : "map"
    "/checkin" : "checkIn"
    "/checkin/:line/:station" : "checkInStation"
    "/home"    : "home"
    "/*"       : "home"

  home: () ->
    app.showHomeView()
    app.views.homeView.render()

  map: () ->
    app.showMapView()
    app.views.mapView.render()

  checkIn: () ->
    app.showCheckInView()
    app.views.checkInView.render()

  checkInStation: (line, station) ->
    app.showCheckInView()
    app.views.checkInView.setRenderData(line, station)

  thanks: () ->
    app.showThanksView()
    app.views.thanksView.render()

module.exports = AppRouter
