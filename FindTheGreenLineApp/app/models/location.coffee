Model = require './model'
GeoLocation = require '../lib/geoLocation'

class Location extends Model
	timestamp : new Date().getTime()
	coords    :
		latitude  : 42.347031
		longitude : -71.082788
	isDefault : true

	constructor: (callback) ->
		@getCurrentLocation callback

	getCurrentLocation: (callback) =>
		GeoLocation.getLocation (location) =>
			console.log location.coords
			@timestamp = location.timestamp
			@coords = location.coords
			@isDefault = false
			if callback
				callback location

module.exports = Location