View     = require './view'
app      = require '../application'
template = require './templates/thanksViewTemplate'
helpers  = require '../lib/helpers'

class ThanksView extends View
  isThanks : true
  el       : "#thanks-view-container"
  template : template
  events: 
      'click #thanks-to-check-in': 'toCheckIn'
      'click #thanks-to-home' : 'toHome'

   toCheckIn: =>
   	helpers.toCheckIn()

   toHome: =>
   	helpers.toHome()

module.exports = ThanksView