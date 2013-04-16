Model = require('./model');

class CheckIn extends Model
	time : 0
	isStop : true
	name : ""
	direction : ""

	constructor: () ->
		console.log "I've been constructed because I'm a model"
	
module.exports = CheckIn