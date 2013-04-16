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
    helpers.toThanks()

module.exports = CheckInView