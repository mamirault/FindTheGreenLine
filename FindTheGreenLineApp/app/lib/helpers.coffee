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

module.exports = helpers