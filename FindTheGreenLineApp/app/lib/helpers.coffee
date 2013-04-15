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

  convertToTimeString: (date) =>
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

module.exports = helpers