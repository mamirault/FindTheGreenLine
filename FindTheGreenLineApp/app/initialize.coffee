env = require 'env'
window.app = require 'application'

$ ->
  app.initialize env, () =>
    Backbone.history.start()
