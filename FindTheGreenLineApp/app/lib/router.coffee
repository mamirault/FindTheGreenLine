app = require 'application'
    
class AppRouter extends Backbone.Router
  routes:    
    "/*"   : "home"
    "/map" : "map"

  home: () =>
    app.views.homeView.render()

  map: () =>
    app.views.mapView.render()

module.exports = AppRouter
