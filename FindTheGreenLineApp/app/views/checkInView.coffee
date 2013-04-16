View     = require './view'
app      = require '../application'
template = require './templates/checkInViewTemplate'
helpers  = require '../lib/helpers'

class CheckInView extends View
  isCheckIn : true
  el        : "#check-in-view-container"
  template  : template
  please    : "Please help us Find the Green Line. Your fellow Bostonians and Green Line riders will thank you."
  thankYou  : ["Running late, no surprise there.  Now everybody else knows though. Thanks!!", "Classic Green Line, just being wherever it wants, whenever it wants. Thanks for the heads up!"]
  urlBase : "#{app.env.API_BASE}/checkin/new"
  events: 
      'click #check-in-to-map': 'toMap'
      'click #check-in-to-thanks': 'toThanks'

  render: =>
    @renderData = {}
    $(@el).html @template @getRenderData()
    @afterRender()
    @

  setRenderData: (line, name) =>
    @renderData =
      line : line
      name : name
    @afterRender()

  getRenderData: () =>
    checkin :
      please   : @please
      thankYou : @getThankYou()

  afterRender: () =>
    $("#station-select").val(@renderData.name)
    $("#line-select").val(@renderData.line)

  getThankYou: () =>
    helpers.getRandomElement @thankYou

  toMap: () =>
    helpers.toMap()

  toThanks: () =>
    station = $("#station-select").val()
    line = $("#line-select").val()
    direction = $("#direction-select").val()
    time = new Date()
    $.ajax
      url: "#{@urlBase}?name=#{station}+Station&line=#{line}&direction=#{direction}&time=#{new Date().getTime()}"
      type: 'GET'
      dataType: 'json'
      success: (data, textStatus, jqHXR) => 
        console.log data
      error: (xhr, status, error) => 
        console.log "Error saving check in"


    helpers.toThanks()

module.exports = CheckInView