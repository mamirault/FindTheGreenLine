View     = require './view'
app      = require '../application'
template = require './templates/homeViewTemplate'
helpers  = require '../lib/helpers'

class HomeView extends View
  isHome : true
  el       : "#home-view-container"
  template : template

  afterRender: () =>
    @isCurrent = true

module.exports = HomeView