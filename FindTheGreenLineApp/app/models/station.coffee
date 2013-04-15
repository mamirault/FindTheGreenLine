Model = require('./model');

class Station extends Model
  isStation : true
  name      : ""
  address   : ""
  latitude  : null
  longitude : null

module.exports = Station