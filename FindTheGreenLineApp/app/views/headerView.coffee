View     = require './view'
app      = require '../application'
template = require './templates/headerViewTemplate'
helpers  = require '../lib/helpers'

class HomeView extends View
  el       : "#header-view-container"
  template : template

  initialize: =>
    $(window).resize @resizeHeader

  afterRender: =>
    @resizeHeader()

  resizeHeader: =>
    helpers.resizeText "header-banner", 12

module.exports = HomeView