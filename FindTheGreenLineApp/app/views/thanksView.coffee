View     = require './view'
app      = require '../application'
template = require './templates/thanksViewTemplate'
helpers  = require '../lib/helpers'

class ThanksView extends View
  isThanks : true
  el       : "#thanks-view-container"
  template : template
  events: 
      'click #thanks-to-map': 'toMap'
      'click #thanks-to-home' : 'toHome'

   toMap: =>
   	helpers.toMap()

   toHome: =>
   	helpers.toHome()

module.exports = ThanksView