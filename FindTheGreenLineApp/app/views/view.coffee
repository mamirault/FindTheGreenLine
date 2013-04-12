# Base class for all views.
module.exports = class View extends Backbone.View
  template: ->
    return

  getRenderData: ->
    return

  render: =>
  #  @show()
    $(@el).html @template @getRenderData()
    @afterRender()
    @

  afterRender: ->
    return

  hide: ->
    $(@el)._hide()

  show: ->
    $(@el)._show()