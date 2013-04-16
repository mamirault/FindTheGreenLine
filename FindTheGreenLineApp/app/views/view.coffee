# Base class for all views.
module.exports = class View extends Backbone.View
  template: ->
    return

  getRenderData: ->
    return

  render: =>
    $(@el).html @template @getRenderData()
    @afterRender()
    @

  afterRender: ->
    return

  hide: ->
    @current = false
    $(@el).hide()

  show: ->
    @current = true
    $(@el).show()