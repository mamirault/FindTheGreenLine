application =
  initialize: (env, onSuccess) ->
    @env = env

    HomeView   = require '/views/homeView'
    MapView    = require '/views/mapView' 
    HeaderView = require '/views/headerView'
    CheckInView = require '/views/checkInView' 

    @views =
      homeView    : new HomeView()
      mapView     : new MapView()
      headerView  : new HeaderView()
      checkInView : new CheckInView()

    AppRouter = require 'lib/router'
    @router   = new AppRouter
      pushState : true

    onSuccess()

  showMapView: () =>
    for viewName of app.views
      view = app.views[viewName]
      if view.isMap || view.isHeader
        view.render()
        view.show()
      else
        view.hide()

  showHomeView: () =>
    for viewName of app.views
      view = app.views[viewName]
      if view.isHome || view.isHeader
        view.render()
        view.show()
      else
        view.hide()

  showCheckInView: () =>
    for viewName of app.views
      view = app.views[viewName]
      if view.isCheckIn || view.isHeader
        view.render()
        view.show()
      else
        view.hide()

  newLocation: (location) =>
    if homeView.isCurrent
      homeView.prompt location


module.exports = application