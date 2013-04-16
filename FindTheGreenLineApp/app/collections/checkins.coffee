app        = require 'application'
Collection = require './collection'
CheckIn    = require '../models/checkIn'

class CheckIns extends Collection
  urlBase : "#{app.env.API_BASE}/checkin/all"
  model   : CheckIn

  fetch: (callback) =>
    $.ajax
      url: @urlBase
      type: 'GET'
      dataType: 'json'
      success: (data, textStatus, jqHXR) => 
        @reset @parse data
        callback()
      error: (xhr, status, error) => 
        app.helpers.errorDialog "Problem getting checkin information from #{@urlBase}. Reason: #{error}."

module.exports = CheckIns