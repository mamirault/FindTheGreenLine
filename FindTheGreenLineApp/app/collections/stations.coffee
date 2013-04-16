app        = require 'application'
Collection = require './collection'
Station    = require '../models/station'

class Stations extends Collection
  urlBase : "#{app.env.API_BASE}/stations/all"
  model   : Station

  fetch: (callback) =>
    $.ajax
      url: @urlBase
      type: 'GET'
      dataType: 'json'
      success: (data, textStatus, jqHXR) => 
        @reset @parse data
        callback()
      error: (xhr, status, error) => 
        app.helpers.errorDialog "Problem getting stop informationfrom #{@urlBase}. Reason: #{error}."

module.exports = Stations