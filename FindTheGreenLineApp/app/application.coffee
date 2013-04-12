application =
  initialize: (env, onSuccess) ->
    @env = env

    HomeView   = require '/views/homeView'
    MapView    = require '/views/mapView' 
    HeaderView = require '/views/headerView' 

    @views =
      homeView   : new HomeView()
      mapView    : new MapView()
      headerView : new HeaderView()

    AppRouter = require 'lib/router'
    @router   = new AppRouter
      pushState : true

    @views.headerView.render()
    onSuccess()

module.exports = application