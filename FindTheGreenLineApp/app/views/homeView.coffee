View     = require './view'
app      = require '../application'
template = require './templates/homeViewTemplate'
helpers  = require '../lib/helpers'

class HomeView extends View
  el       : "#home-view-container"
  template : template

module.exports = HomeView