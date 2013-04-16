View     = require './view'
app      = require '../application'
template = require './templates/homeViewTemplate'
helpers  = require '../lib/helpers'

class HomeView extends View
  isHome : true
  el       : "#home-view-container"
  template : template
  urlBase : "#{app.env.API_BASE}/stations/closest"

  afterRender: () =>
    @isCurrent = true
    helpers.resizeText "waiting", 12

  getClosest: (location) =>
    $.ajax
      url: "#{@urlBase}?latitude=#{location.coords.latitude}&longitude=#{location.coords.longitude}"
      type: 'GET'
      dataType: 'json'
      success: (data, textStatus, jqHXR) =>
        if data.distance < 1000
          @closest = data
          @render()
        else
          ""
      error: (xhr, status, error) => 
        ""

  getRenderData: () =>
    if @closest
      home : @closest
    else
      {}

module.exports = HomeView