app         = require '../application'

geoLocation =
  getLocation: (callback) ->
    navigator.geolocation.getCurrentPosition callback

module.exports = geoLocation