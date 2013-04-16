app = require 'application'
   
class AppRouter extends Backbone.Router
  routes:    
    "/map"     : "map"
    "/checkin" : "checkIn"
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

module.exports = AppRouter
