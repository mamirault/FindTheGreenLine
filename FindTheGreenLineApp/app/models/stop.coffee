Model = require('./model');

class Stop extends Model
	defaults:
		time : 0
		isStop : true
		name : ""
		direction : ""

	initialize: ->
		console.log "I've been initialized!"

	constructor: () ->
		console.log "I've been constructed!"
	

module.exports = Stop