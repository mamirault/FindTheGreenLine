app = require '../application'

helpers =
  getDate: (millisSinceEpoch) ->
    date = new Date(millisSinceEpoch).toString()
    date.substring(0, date.indexOf("GMT") - 4)

  resizeText: (className, originalFontSize) ->
    sectionWidth = $("." + className).width();
    $("." +  className + " span").each () ->
      spanWidth = $(this).width()
      newFontSize = (sectionWidth/spanWidth) * originalFontSize

      $(this).css
        "font-size" : newFontSize
        "line-height" : newFontSize/1.2 + "px"

  convertToTimeString: (date) ->
    minutes = date.getMinutes()
    if minutes.toString().length == 1
      minutes = "0#{minutes}"
    hours = date.getHours()
    if hours > 12
      hours -= 12
      AmPm = "pm"
    else
      AmPm = "am"
      if hours == 0
        hours = 12

    "#{hours}:#{minutes}#{AmPm}"

  getRandomElement: (anArray) ->
    length = anArray.length
    anArray[Math.floor(Math.random() * anArray.length)]

  metersToFeet: (meters) ->
    meters * 3.280839895

  toMap: () ->
    window.location.href="#/map"

  toCheckIn: () ->
    window.location.href="#/checkin"

  toHome: () ->
    window.location.href=""

  toThanks: () ->
    window.location.href="#/thanks"

module.exports = helpers