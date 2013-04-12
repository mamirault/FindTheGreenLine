(function(/*! Brunch !*/) {
  'use strict';

  if (!this.require) {
    var modules = {};
    var cache = {};
    var __hasProp = ({}).hasOwnProperty;

    var expand = function(root, name) {
      var results = [], parts, part;
      if (/^\.\.?(\/|$)/.test(name)) {
        parts = [root, name].join('/').split('/');
      } else {
        parts = name.split('/');
      }
      for (var i = 0, length = parts.length; i < length; i++) {
        part = parts[i];
        if (part == '..') {
          results.pop();
        } else if (part != '.' && part != '') {
          results.push(part);
        }
      }
      return results.join('/');
    };

    var getFullPath = function(path, fromCache) {
      var store = fromCache ? cache : modules;
      var dirIndex;
      if (__hasProp.call(store, path)) return path;
      dirIndex = expand(path, './index');
      if (__hasProp.call(store, dirIndex)) return dirIndex;
    };
    
    var cacheModule = function(name, path, contentFn) {
      var module = {id: path, exports: {}};
      try {
        cache[path] = module.exports;
        contentFn(module.exports, function(name) {
          return require(name, dirname(path));
        }, module);
        cache[path] = module.exports;
      } catch (err) {
        delete cache[path];
        throw err;
      }
      return cache[path];
    };

    var require = function(name, root) {
      var path = expand(root, name);
      var fullPath;

      if (fullPath = getFullPath(path, true)) {
        return cache[fullPath];
      } else if (fullPath = getFullPath(path, false)) {
        return cacheModule(name, fullPath, modules[fullPath]);
      } else {
        throw new Error("Cannot find module '" + name + "'");
      }
    };

    var dirname = function(path) {
      return path.split('/').slice(0, -1).join('/');
    };

    this.require = function(name) {
      return require(name, '');
    };

    this.require.brunch = true;
    this.require.define = function(bundle) {
      for (var key in bundle) {
        if (__hasProp.call(bundle, key)) {
          modules[key] = bundle[key];
        }
      }
    };
  }
}).call(this);
(this.require.define({
  "application": function(exports, require, module) {
    (function() {
  var application;

  application = {
    initialize: function(env, onSuccess) {
      var AppRouter, HeaderView, HomeView, MapView;
      this.env = env;
      HomeView = require('/views/homeView');
      MapView = require('/views/mapView');
      HeaderView = require('/views/headerView');
      this.views = {
        homeView: new HomeView(),
        mapView: new MapView(),
        headerView: new HeaderView()
      };
      AppRouter = require('lib/router');
      this.router = new AppRouter({
        pushState: true
      });
      this.views.headerView.render();
      return onSuccess();
    }
  };

  module.exports = application;

}).call(this);

  }
}));
(this.require.define({
  "collections/collection": function(exports, require, module) {
    (function() {
  var Collection,
    __hasProp = Object.prototype.hasOwnProperty,
    __extends = function(child, parent) { for (var key in parent) { if (__hasProp.call(parent, key)) child[key] = parent[key]; } function ctor() { this.constructor = child; } ctor.prototype = parent.prototype; child.prototype = new ctor; child.__super__ = parent.prototype; return child; };

  module.exports = Collection = (function(_super) {

    __extends(Collection, _super);

    function Collection() {
      Collection.__super__.constructor.apply(this, arguments);
    }

    return Collection;

  })(Backbone.Collection);

}).call(this);

  }
}));
(this.require.define({
  "collections/stops": function(exports, require, module) {
    (function() {
  var Collection, Stops, app,
    __bind = function(fn, me){ return function(){ return fn.apply(me, arguments); }; },
    __hasProp = Object.prototype.hasOwnProperty,
    __extends = function(child, parent) { for (var key in parent) { if (__hasProp.call(parent, key)) child[key] = parent[key]; } function ctor() { this.constructor = child; } ctor.prototype = parent.prototype; child.prototype = new ctor; child.__super__ = parent.prototype; return child; };

  Collection = require('./collection');

  app = require('application');

  Stops = (function(_super) {

    __extends(Stops, _super);

    function Stops() {
      this.fetch = __bind(this.fetch, this);
      Stops.__super__.constructor.apply(this, arguments);
    }

    Stops.prototype.urlBase = app.env.API_BASE + "//v1/stats";

    Stops.prototype.fetch = function(opts) {
      var _this = this;
      $.fancybox.showActivity();
      return $.ajax({
        url: "" + this.urlBase + "?" + app.env.ACCESS_TOKEN_PARAM + "=" + app.portalAccessToken,
        type: 'GET',
        dataType: 'json',
        success: function(data, textStatus, jqHXR) {
          _this.reset(_this.parse(data));
          $.fancybox.hideActivity();
          return opts.callback();
        },
        error: function(xhr, status, error) {
          app.helpers.errorDialog("Problem getting message info email stats from " + _this.urlBase + ". Reason: " + error + ".");
          return $.fancybox.hideActivity();
        }
      });
    };

    return Stops;

  })(Collection);

  module.exports = Messages;

}).call(this);

  }
}));
(this.require.define({
  "env": function(exports, require, module) {
    (function() {
  var constants;

  constants = module.exports = constants;

}).call(this);

  }
}));
(this.require.define({
  "initialize": function(exports, require, module) {
    (function() {
  var env;

  env = require('env');

  window.app = require('application');

  $(function() {
    var _this = this;
    return app.initialize(env, function() {
      return Backbone.history.start();
    });
  });

}).call(this);

  }
}));
(this.require.define({
  "lib/geoLocation": function(exports, require, module) {
    (function() {
  var app, geoLocation;

  app = require('../application');

  geoLocation = {
    getLocation: function(callback) {
      return navigator.geolocation.getCurrentPosition(callback);
    }
  };

  module.exports = geoLocation;

}).call(this);

  }
}));
(this.require.define({
  "lib/helpers": function(exports, require, module) {
    (function() {
  var app, helpers;

  app = require('../application');

  helpers = {
    getDate: function(millisSinceEpoch) {
      var date;
      date = new Date(millisSinceEpoch).toString();
      return date.substring(0, date.indexOf("GMT") - 4);
    },
    resizeText: function(className, originalFontSize) {
      var sectionWidth;
      sectionWidth = $("." + className).width();
      return $("." + className + " span").each(function() {
        var newFontSize, spanWidth;
        spanWidth = $(this).width();
        newFontSize = (sectionWidth / spanWidth) * originalFontSize;
        return $(this).css({
          "font-size": newFontSize,
          "line-height": newFontSize / 1.2 + "px"
        });
      });
    }
  };

  module.exports = helpers;

}).call(this);

  }
}));
(this.require.define({
  "lib/router": function(exports, require, module) {
    (function() {
  var AppRouter, app,
    __bind = function(fn, me){ return function(){ return fn.apply(me, arguments); }; },
    __hasProp = Object.prototype.hasOwnProperty,
    __extends = function(child, parent) { for (var key in parent) { if (__hasProp.call(parent, key)) child[key] = parent[key]; } function ctor() { this.constructor = child; } ctor.prototype = parent.prototype; child.prototype = new ctor; child.__super__ = parent.prototype; return child; };

  app = require('application');

  AppRouter = (function(_super) {

    __extends(AppRouter, _super);

    function AppRouter() {
      this.map = __bind(this.map, this);
      this.home = __bind(this.home, this);
      AppRouter.__super__.constructor.apply(this, arguments);
    }

    AppRouter.prototype.routes = {
      "/*": "home",
      "/map": "map"
    };

    AppRouter.prototype.home = function() {
      return app.views.homeView.render();
    };

    AppRouter.prototype.map = function() {
      return app.views.mapView.render();
    };

    return AppRouter;

  })(Backbone.Router);

  module.exports = AppRouter;

}).call(this);

  }
}));
(this.require.define({
  "models/location": function(exports, require, module) {
    (function() {
  var GeoLocation, Location, Model,
    __bind = function(fn, me){ return function(){ return fn.apply(me, arguments); }; },
    __hasProp = Object.prototype.hasOwnProperty,
    __extends = function(child, parent) { for (var key in parent) { if (__hasProp.call(parent, key)) child[key] = parent[key]; } function ctor() { this.constructor = child; } ctor.prototype = parent.prototype; child.prototype = new ctor; child.__super__ = parent.prototype; return child; };

  Model = require('./model');

  GeoLocation = require('../lib/geoLocation');

  Location = (function(_super) {

    __extends(Location, _super);

    Location.prototype.timestamp = new Date().getTime();

    Location.prototype.coords = {
      latitude: 42.347031,
      longitude: -71.082788
    };

    Location.prototype.isDefault = true;

    function Location(callback) {
      this.getCurrentLocation = __bind(this.getCurrentLocation, this);      this.getCurrentLocation(callback);
    }

    Location.prototype.getCurrentLocation = function(callback) {
      var _this = this;
      return GeoLocation.getLocation(function(location) {
        console.log(location.coords);
        _this.timestamp = location.timestamp;
        _this.coords = location.coords;
        _this.isDefault = false;
        if (callback) return callback(location);
      });
    };

    return Location;

  })(Model);

  module.exports = Location;

}).call(this);

  }
}));
(this.require.define({
  "models/model": function(exports, require, module) {
    (function() {
  var Model,
    __hasProp = Object.prototype.hasOwnProperty,
    __extends = function(child, parent) { for (var key in parent) { if (__hasProp.call(parent, key)) child[key] = parent[key]; } function ctor() { this.constructor = child; } ctor.prototype = parent.prototype; child.prototype = new ctor; child.__super__ = parent.prototype; return child; };

  module.exports = Model = (function(_super) {

    __extends(Model, _super);

    function Model() {
      Model.__super__.constructor.apply(this, arguments);
    }

    return Model;

  })(Backbone.Model);

}).call(this);

  }
}));
(this.require.define({
  "models/stop": function(exports, require, module) {
    (function() {
  var Model, Stop,
    __hasProp = Object.prototype.hasOwnProperty,
    __extends = function(child, parent) { for (var key in parent) { if (__hasProp.call(parent, key)) child[key] = parent[key]; } function ctor() { this.constructor = child; } ctor.prototype = parent.prototype; child.prototype = new ctor; child.__super__ = parent.prototype; return child; };

  Model = require('./model');

  Stop = (function(_super) {

    __extends(Stop, _super);

    Stop.prototype.defaults = {
      time: 0,
      isStop: true,
      name: "",
      direction: ""
    };

    Stop.prototype.initialize = function() {
      return console.log("I've been initialized!");
    };

    function Stop() {
      console.log("I've been constructed!");
    }

    return Stop;

  })(Model);

  module.exports = Stop;

}).call(this);

  }
}));
(this.require.define({
  "views/headerView": function(exports, require, module) {
    (function() {
  var HomeView, View, app, helpers, template,
    __bind = function(fn, me){ return function(){ return fn.apply(me, arguments); }; },
    __hasProp = Object.prototype.hasOwnProperty,
    __extends = function(child, parent) { for (var key in parent) { if (__hasProp.call(parent, key)) child[key] = parent[key]; } function ctor() { this.constructor = child; } ctor.prototype = parent.prototype; child.prototype = new ctor; child.__super__ = parent.prototype; return child; };

  View = require('./view');

  app = require('../application');

  template = require('./templates/headerViewTemplate');

  helpers = require('../lib/helpers');

  HomeView = (function(_super) {

    __extends(HomeView, _super);

    function HomeView() {
      this.resizeHeader = __bind(this.resizeHeader, this);
      this.afterRender = __bind(this.afterRender, this);
      this.initialize = __bind(this.initialize, this);
      HomeView.__super__.constructor.apply(this, arguments);
    }

    HomeView.prototype.el = "#header-view-container";

    HomeView.prototype.template = template;

    HomeView.prototype.initialize = function() {
      return $(window).resize(this.resizeHeader);
    };

    HomeView.prototype.afterRender = function() {
      return this.resizeHeader();
    };

    HomeView.prototype.resizeHeader = function() {
      return helpers.resizeText("header-banner", 12);
    };

    return HomeView;

  })(View);

  module.exports = HomeView;

}).call(this);

  }
}));
(this.require.define({
  "views/homeView": function(exports, require, module) {
    (function() {
  var HomeView, View, app, helpers, template,
    __hasProp = Object.prototype.hasOwnProperty,
    __extends = function(child, parent) { for (var key in parent) { if (__hasProp.call(parent, key)) child[key] = parent[key]; } function ctor() { this.constructor = child; } ctor.prototype = parent.prototype; child.prototype = new ctor; child.__super__ = parent.prototype; return child; };

  View = require('./view');

  app = require('../application');

  template = require('./templates/homeViewTemplate');

  helpers = require('../lib/helpers');

  HomeView = (function(_super) {

    __extends(HomeView, _super);

    function HomeView() {
      HomeView.__super__.constructor.apply(this, arguments);
    }

    HomeView.prototype.el = "#home-view-container";

    HomeView.prototype.template = template;

    return HomeView;

  })(View);

  module.exports = HomeView;

}).call(this);

  }
}));
(this.require.define({
  "views/mapView": function(exports, require, module) {
    (function() {
  var Location, MapView, View, app, helpers, template,
    __bind = function(fn, me){ return function(){ return fn.apply(me, arguments); }; },
    __hasProp = Object.prototype.hasOwnProperty,
    __extends = function(child, parent) { for (var key in parent) { if (__hasProp.call(parent, key)) child[key] = parent[key]; } function ctor() { this.constructor = child; } ctor.prototype = parent.prototype; child.prototype = new ctor; child.__super__ = parent.prototype; return child; };

  View = require('./view');

  app = require('../application');

  template = require('./templates/mapViewTemplate');

  helpers = require('../lib/helpers');

  Location = require('../models/location');

  MapView = (function(_super) {

    __extends(MapView, _super);

    function MapView() {
      this.locationCallback = __bind(this.locationCallback, this);
      this.afterRender = __bind(this.afterRender, this);
      this.initialize = __bind(this.initialize, this);
      MapView.__super__.constructor.apply(this, arguments);
    }

    MapView.prototype.el = "#map-view-container";

    MapView.prototype.mapId = "map-container";

    MapView.prototype.template = template;

    MapView.prototype.location = null;

    MapView.prototype.stops = null;

    MapView.prototype.initialize = function() {
      this.location = new Location(this.locationCallback, false);
      this.mapOptions = {
        center: new google.maps.LatLng(42.347031, -71.082788),
        zoom: 15,
        mapTypeId: google.maps.MapTypeId.ROADMAP
      };
      return this.stops = new Stops();
    };

    MapView.prototype.afterRender = function() {
      this.map = new google.maps.Map(document.getElementById(this.mapId), this.mapOptions);
      this.transitLayer = new google.maps.TransitLayer();
      this.transitLayer.setMap(this.map);
      if (!this.location.isDefault) return this.marker.setMap(this.map);
    };

    MapView.prototype.locationCallback = function(location) {
      this.location = location;
      this.mapOptions.center = new google.maps.LatLng(location.coords.latitude, location.coords.longitude);
      this.marker = new google.maps.Marker({
        position: this.mapOptions.center,
        title: "You"
      });
      return this.afterRender();
    };

    return MapView;

  })(View);

  module.exports = MapView;

}).call(this);

  }
}));
(this.require.define({
  "views/templates/headerViewTemplate": function(exports, require, module) {
    module.exports = Handlebars.template(function (Handlebars,depth0,helpers,partials,data) {
  helpers = helpers || Handlebars.helpers;
  var foundHelper, self=this;


  return "<div class=\"center header-banner\">\n	<span>Find The Green Line</span>\n</div>";});
  }
}));
(this.require.define({
  "views/templates/homeViewTemplate": function(exports, require, module) {
    module.exports = Handlebars.template(function (Handlebars,depth0,helpers,partials,data) {
  helpers = helpers || Handlebars.helpers;
  var foundHelper, self=this;


  return "<div>\n	<div class=\"home-view-full-button\">\n		<span>Are you on the T?</span>\n		<a class=\"big-button hs-button primary large green\">Check In</a>\n	</div>\n	<div>\n		<span>Waiting?</span>\n		<a href=\"#/map\" class=\"hs-button primary large big-button\">Map</a>\n	</div>\n</div>";});
  }
}));
(this.require.define({
  "views/templates/mapViewTemplate": function(exports, require, module) {
    module.exports = Handlebars.template(function (Handlebars,depth0,helpers,partials,data) {
  helpers = helpers || Handlebars.helpers;
  var foundHelper, self=this;


  return "<div id=\"map-container\"></div>";});
  }
}));
(this.require.define({
  "views/view": function(exports, require, module) {
    (function() {
  var View,
    __bind = function(fn, me){ return function(){ return fn.apply(me, arguments); }; },
    __hasProp = Object.prototype.hasOwnProperty,
    __extends = function(child, parent) { for (var key in parent) { if (__hasProp.call(parent, key)) child[key] = parent[key]; } function ctor() { this.constructor = child; } ctor.prototype = parent.prototype; child.prototype = new ctor; child.__super__ = parent.prototype; return child; };

  module.exports = View = (function(_super) {

    __extends(View, _super);

    function View() {
      this.render = __bind(this.render, this);
      View.__super__.constructor.apply(this, arguments);
    }

    View.prototype.template = function() {};

    View.prototype.getRenderData = function() {};

    View.prototype.render = function() {
      $(this.el).html(this.template(this.getRenderData()));
      this.afterRender();
      return this;
    };

    View.prototype.afterRender = function() {};

    View.prototype.hide = function() {
      return $(this.el)._hide();
    };

    View.prototype.show = function() {
      return $(this.el)._show();
    };

    return View;

  })(Backbone.View);

}).call(this);

  }
}));
