exports.config =
  # Edit the next line to change default build path.
  buildPath: 'public'

  files:
    javascripts:
      # Defines what file will be generated with `brunch generate`.
      defaultExtension: 'js'
      # Describes how files will be compiled & joined together.
      # Available formats:
      # * 'outputFilePath'
      # * map of ('outputFilePath': /regExp that matches input path/)
      # * map of ('outputFilePath': function that takes input path)
      joinTo:
        'static/js/app.js': /^app/
        'static/js/vendor.js': /^vendor/
      # Defines compilation order.
      # `vendor` files will be compiled before other ones
      # even if they are not present here.
      order:
        before: [
          'vendor/scripts/jquery-1.7.1.js',
          'vendor/scripts/console-helper.js',
          'vendor/scripts/underscore-1.3.1.js',
          'vendor/scripts/chosen.jquery.js',
          'vendor/scripts/date.js',
          'vendor/scripts/backbone-0.9.1.js'
        ]

    stylesheets:
      defaultExtension: 'styl'
      joinTo: 'static/css/app.css'
      order:
        before: [
          'vendor/styles/normalize.css',
          'vendor/styles/chosen.css',
          'vendor/styles/bootstrap.css'
        ]
        after: ['vendor/styles/helpers.css']

    templates:
      defaultExtension: 'hbs'
      joinTo: 'static/js/app.js'

  # Change this if you're using something other than backbone (e.g. 'ember').
  # Content of files, generated with `brunch generate` depends on the setting.
  # framework: 'backbone'

  # Enable or disable minifying of result js / css files.
  minify: no

  # Settings of web server that will run with `brunch watch [--server]`.
  # server:
  #   # Path to your server node.js module.
  #   # If it's commented-out, brunch will use built-in express.js server.
  #   path: 'server.coffee'
  #   port: 3333
  #   # Run even without `--server` option?
  #   run: yes
