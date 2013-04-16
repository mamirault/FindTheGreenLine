application =
  initialize: (env, onSuccess) ->
    @env = env

    HomeView   = require '/views/homeView'
    MapView    = require '/views/mapView' 
    CheckInMapView    = require '/views/checkInMapView' 
    CheckInView    = require '/views/checkInView' 
    HeaderView = require '/views/headerView'
    ThanksView = require '/views/thanksView' 

    @views =
      headerView  : new HeaderView()
      homeView    : new HomeView()
      mapView     : new MapView()
      checkInMapView : new CheckInMapView()
      checkInView : new CheckInView()
      thanksView : new ThanksView()

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

  showCheckInMapView: () =>
    for viewName of app.views
      view = app.views[viewName]
      if view.isCheckInMap || view.isHeader
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

  showThanksView: () =>
    for viewName of app.views
      view = app.views[viewName]
      if view.isThanks || view.isHeader
        view.render()
        view.show()
      else
        view.hide()

  newLocation: (location) =>
    if app.views.homeView.isCurrent
      app.views.homeView.getClosest location

module.exports = application