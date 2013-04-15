app        = require 'application'
Collection = require './collection'
Stop       = require '../models/stop'

class Stops extends Collection
  #urlBase : "http://api.findthegreenline.com/stops/all"
  urlBase : "http://localhost:8080/stops/timeframe"
  model   : Stop

  fetch: (callback, args) =>
    $.ajax
      url: @urlBase
      type: 'GET'
      dataType: 'json'
      success: (data, textStatus, jqHXR) => 
        @reset @parse data
        callback args
      error: (xhr, status, error) => 
        app.helpers.errorDialog "Problem getting stop informationfrom #{@urlBase}. Reason: #{error}."

module.exports = Stops