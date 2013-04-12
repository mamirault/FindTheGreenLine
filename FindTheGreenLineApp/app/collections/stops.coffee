Collection = require './collection'
app = require 'application'

class Stops extends Collection
  urlBase : app.env.API_BASE + "//v1/stats"

  fetch: (opts) =>
    $.fancybox.showActivity()

    $.ajax
      url: "#{@urlBase}?#{app.env.ACCESS_TOKEN_PARAM}=#{app.portalAccessToken}"
      type: 'GET'
      dataType: 'json'
      success: (data, textStatus, jqHXR) => 
        @reset @parse data
        $.fancybox.hideActivity()
        opts.callback()
      error: (xhr, status, error) => 
        app.helpers.errorDialog "Problem getting message info email stats from #{@urlBase}. Reason: #{error}."
        $.fancybox.hideActivity()

module.exports = Messages