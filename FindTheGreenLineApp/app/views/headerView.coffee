View     = require './view'
app      = require '../application'
template = require './templates/headerViewTemplate'
helpers  = require '../lib/helpers'

class HeaderView extends View
  el       : "#header-view-container"
  template : template
  isHeader : true

  initialize: =>
    $(window).resize @resizeHeader

  afterRender: =>
    @resizeHeader()

  resizeHeader: =>
    helpers.resizeText "header-banner", 12

module.exports = HeaderView