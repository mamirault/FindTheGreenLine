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
  var application,
    _this = this;

  application = {
    initialize: function(env, onSuccess) {
      var AppRouter, CheckInView, HeaderView, HomeView, MapView, ThanksView;
      this.env = env;
      HomeView = require('/views/homeView');
      MapView = require('/views/mapView');
      HeaderView = require('/views/headerView');
      CheckInView = require('/views/checkInView');
      ThanksView = require('/views/thanksView');
      this.views = {
        homeView: new HomeView(),
        mapView: new MapView(),
        headerView: new HeaderView(),
        checkInView: new CheckInView(),
        thanksView: new ThanksView()
      };
      AppRouter = require('lib/router');
      this.router = new AppRouter({
        pushState: true
      });
      return onSuccess();
    },
    showMapView: function() {
      var view, viewName, _results;
      _results = [];
      for (viewName in app.views) {
        view = app.views[viewName];
        if (view.isMap || view.isHeader) {
          view.render();
          _results.push(view.show());
        } else {
          _results.push(view.hide());
        }
      }
      return _results;
    },
    showHomeView: function() {
      var view, viewName, _results;
      _results = [];
      for (viewName in app.views) {
        view = app.views[viewName];
        if (view.isHome || view.isHeader) {
          view.render();
          _results.push(view.show());
        } else {
          _results.push(view.hide());
        }
      }
      return _results;
    },
    showCheckInView: function() {
      var view, viewName, _results;
      _results = [];
      for (viewName in app.views) {
        view = app.views[viewName];
        if (view.isCheckIn || view.isHeader) {
          view.render();
          _results.push(view.show());
        } else {
          _results.push(view.hide());
        }
      }
      return _results;
    },
    showThanksView: function() {
      var view, viewName, _results;
      _results = [];
      for (viewName in app.views) {
        view = app.views[viewName];
        if (view.isThanks || view.isHeader) {
          view.render();
          _results.push(view.show());
        } else {
          _results.push(view.hide());
        }
      }
      return _results;
    },
    newLocation: function(location) {
      if (app.views.homeView.isCurrent) {
        return app.views.homeView.getClosest(location);
      }
    }
  };

  module.exports = application;

}).call(this);

  }
}));
(this.require.define({
  "collections/checkins": function(exports, require, module) {
    (function() {
  var CheckIn, Collection, Stations, app,
    __bind = function(fn, me){ return function(){ return fn.apply(me, arguments); }; },
    __hasProp = Object.prototype.hasOwnProperty,
    __extends = function(child, parent) { for (var key in parent) { if (__hasProp.call(parent, key)) child[key] = parent[key]; } function ctor() { this.constructor = child; } ctor.prototype = parent.prototype; child.prototype = new ctor; child.__super__ = parent.prototype; return child; };

  app = require('application');

  Collection = require('./collection');

  CheckIn = require('../models/checkin');

  Stations = (function(_super) {

    __extends(Stations, _super);

    function Stations() {
      this.fetch = __bind(this.fetch, this);
      Stations.__super__.constructor.apply(this, arguments);
    }

    Stations.prototype.urlBase = "" + app.env.API_BASE + "/checkin/all";

    Stations.prototype.model = CheckIn;

    Stations.prototype.fetch = function(callback) {
      var _this = this;
      return $.ajax({
        url: this.urlBase,
        type: 'GET',
        dataType: 'json',
        success: function(data, textStatus, jqHXR) {
          console.log("CHECKIN DATA");
          _this.reset(_this.parse(data));
          return callback();
        },
        error: function(xhr, status, error) {
          return app.helpers.errorDialog("Problem getting checkin information from " + _this.urlBase + ". Reason: " + error + ".");
        }
      });
    };

    return Stations;

  })(Collection);

  module.exports = Stations;

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
  "collections/stations": function(exports, require, module) {
    (function() {
  var Collection, Station, Stations, app,
    __bind = function(fn, me){ return function(){ return fn.apply(me, arguments); }; },
    __hasProp = Object.prototype.hasOwnProperty,
    __extends = function(child, parent) { for (var key in parent) { if (__hasProp.call(parent, key)) child[key] = parent[key]; } function ctor() { this.constructor = child; } ctor.prototype = parent.prototype; child.prototype = new ctor; child.__super__ = parent.prototype; return child; };

  app = require('application');

  Collection = require('./collection');

  Station = require('../models/station');

  Stations = (function(_super) {

    __extends(Stations, _super);

    function Stations() {
      this.fetch = __bind(this.fetch, this);
      Stations.__super__.constructor.apply(this, arguments);
    }

    Stations.prototype.urlBase = "" + app.env.API_BASE + "/stations/all";

    Stations.prototype.model = Station;

    Stations.prototype.fetch = function(callback) {
      var _this = this;
      return $.ajax({
        url: this.urlBase,
        type: 'GET',
        dataType: 'json',
        success: function(data, textStatus, jqHXR) {
          _this.reset(_this.parse(data));
          return callback();
        },
        error: function(xhr, status, error) {
          return app.helpers.errorDialog("Problem getting stop informationfrom " + _this.urlBase + ". Reason: " + error + ".");
        }
      });
    };

    return Stations;

  })(Collection);

  module.exports = Stations;

}).call(this);

  }
}));
(this.require.define({
  "collections/stops": function(exports, require, module) {
    (function() {
  var Collection, Stop, Stops, app,
    __bind = function(fn, me){ return function(){ return fn.apply(me, arguments); }; },
    __hasProp = Object.prototype.hasOwnProperty,
    __extends = function(child, parent) { for (var key in parent) { if (__hasProp.call(parent, key)) child[key] = parent[key]; } function ctor() { this.constructor = child; } ctor.prototype = parent.prototype; child.prototype = new ctor; child.__super__ = parent.prototype; return child; };

  app = require('application');

  Collection = require('./collection');

  Stop = require('../models/stop');

  Stops = (function(_super) {

    __extends(Stops, _super);

    function Stops() {
      this.fetch = __bind(this.fetch, this);
      Stops.__super__.constructor.apply(this, arguments);
    }

    Stops.prototype.urlBase = "" + app.env.API_BASE + "/stops/timeframe";

    Stops.prototype.model = Stop;

    Stops.prototype.fetch = function(callback, args) {
      var _this = this;
      return $.ajax({
        url: this.urlBase,
        type: 'GET',
        dataType: 'json',
        success: function(data, textStatus, jqHXR) {
          _this.reset(_this.parse(data));
          return callback(args);
        },
        error: function(xhr, status, error) {
          return app.helpers.errorDialog("Problem getting stop informationfrom " + _this.urlBase + ". Reason: " + error + ".");
        }
      });
    };

    return Stops;

  })(Collection);

  module.exports = Stops;

}).call(this);

  }
}));
(this.require.define({
  "env": function(exports, require, module) {
    (function() {
  var constants;

  constants = {
    API_BASE: 'http://localhost:8080'
  };

  module.exports = constants;

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
    },
    convertToTimeString: function(date) {
      var AmPm, hours, minutes;
      minutes = date.getMinutes();
      if (minutes.toString().length === 1) minutes = "0" + minutes;
      hours = date.getHours();
      if (hours > 12) {
        hours -= 12;
        AmPm = "pm";
      } else {
        AmPm = "am";
        if (hours === 0) hours = 12;
      }
      return "" + hours + ":" + minutes + AmPm;
    },
    getRandomElement: function(anArray) {
      var length;
      length = anArray.length;
      return anArray[Math.floor(Math.random() * anArray.length)];
    },
    metersToFeet: function(meters) {
      return meters * 3.280839895;
    },
    toMap: function() {
      return window.location.href = "#/map";
    },
    toCheckIn: function() {
      return window.location.href = "#/checkin";
    },
    toHome: function() {
      return window.location.href = "";
    },
    toThanks: function() {
      return window.location.href = "#/thanks";
    }
  };

  module.exports = helpers;

}).call(this);

  }
}));
(this.require.define({
  "lib/icons": function(exports, require, module) {
    (function() {
  var icons;

  icons = {
    person: "./person.png",
    train: "./train.png",
    station: "./station.png"
  };

  module.exports = icons;

}).call(this);

  }
}));
(this.require.define({
  "lib/router": function(exports, require, module) {
    (function() {
  var AppRouter, app,
    __hasProp = Object.prototype.hasOwnProperty,
    __extends = function(child, parent) { for (var key in parent) { if (__hasProp.call(parent, key)) child[key] = parent[key]; } function ctor() { this.constructor = child; } ctor.prototype = parent.prototype; child.prototype = new ctor; child.__super__ = parent.prototype; return child; };

  app = require('application');

  AppRouter = (function(_super) {

    __extends(AppRouter, _super);

    function AppRouter() {
      AppRouter.__super__.constructor.apply(this, arguments);
    }

    AppRouter.prototype.routes = {
      "/thanks": "thanks",
      "/map": "map",
      "/checkin": "checkIn",
      "/checkin/:line/:station": "checkInStation",
      "/home": "home",
      "/*": "home"
    };

    AppRouter.prototype.home = function() {
      app.showHomeView();
      return app.views.homeView.render();
    };

    AppRouter.prototype.map = function() {
      app.showMapView();
      return app.views.mapView.render();
    };

    AppRouter.prototype.checkIn = function() {
      app.showCheckInView();
      return app.views.checkInView.render();
    };

    AppRouter.prototype.checkInStation = function(line, station) {
      app.showCheckInView();
      return app.views.checkInView.setRenderData(line, station);
    };

    AppRouter.prototype.thanks = function() {
      app.showThanksView();
      return app.views.thanksView.render();
    };

    return AppRouter;

  })(Backbone.Router);

  module.exports = AppRouter;

}).call(this);

  }
}));
(this.require.define({
  "models/checkIn": function(exports, require, module) {
    (function() {
  var CheckIn, Model,
    __hasProp = Object.prototype.hasOwnProperty,
    __extends = function(child, parent) { for (var key in parent) { if (__hasProp.call(parent, key)) child[key] = parent[key]; } function ctor() { this.constructor = child; } ctor.prototype = parent.prototype; child.prototype = new ctor; child.__super__ = parent.prototype; return child; };

  Model = require('./model');

  CheckIn = (function(_super) {

    __extends(CheckIn, _super);

    CheckIn.prototype.time = 0;

    CheckIn.prototype.isStop = true;

    CheckIn.prototype.name = "";

    CheckIn.prototype.direction = "";

    function CheckIn() {
      console.log("I've been constructed because I'm a model");
    }

    return CheckIn;

  })(Model);

  module.exports = CheckIn;

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
  "models/station": function(exports, require, module) {
    (function() {
  var Model, Station,
    __hasProp = Object.prototype.hasOwnProperty,
    __extends = function(child, parent) { for (var key in parent) { if (__hasProp.call(parent, key)) child[key] = parent[key]; } function ctor() { this.constructor = child; } ctor.prototype = parent.prototype; child.prototype = new ctor; child.__super__ = parent.prototype; return child; };

  Model = require('./model');

  Station = (function(_super) {

    __extends(Station, _super);

    function Station() {
      Station.__super__.constructor.apply(this, arguments);
    }

    Station.prototype.isStation = true;

    Station.prototype.name = "";

    Station.prototype.address = "";

    Station.prototype.latitude = null;

    Station.prototype.longitude = null;

    return Station;

  })(Model);

  module.exports = Station;

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

    function Stop() {
      Stop.__super__.constructor.apply(this, arguments);
    }

    Stop.prototype.time = 0;

    Stop.prototype.isStop = true;

    Stop.prototype.name = "";

    Stop.prototype.direction = "";

    return Stop;

  })(Model);

  module.exports = Stop;

}).call(this);

  }
}));
(this.require.define({
  "views/checkInView": function(exports, require, module) {
    (function() {
  var CheckInView, View, app, helpers, template,
    __bind = function(fn, me){ return function(){ return fn.apply(me, arguments); }; },
    __hasProp = Object.prototype.hasOwnProperty,
    __extends = function(child, parent) { for (var key in parent) { if (__hasProp.call(parent, key)) child[key] = parent[key]; } function ctor() { this.constructor = child; } ctor.prototype = parent.prototype; child.prototype = new ctor; child.__super__ = parent.prototype; return child; };

  View = require('./view');

  app = require('../application');

  template = require('./templates/checkInViewTemplate');

  helpers = require('../lib/helpers');

  CheckInView = (function(_super) {

    __extends(CheckInView, _super);

    function CheckInView() {
      this.toThanks = __bind(this.toThanks, this);
      this.toMap = __bind(this.toMap, this);
      this.getThankYou = __bind(this.getThankYou, this);
      this.afterRender = __bind(this.afterRender, this);
      this.getRenderData = __bind(this.getRenderData, this);
      this.setRenderData = __bind(this.setRenderData, this);
      this.render = __bind(this.render, this);
      CheckInView.__super__.constructor.apply(this, arguments);
    }

    CheckInView.prototype.isCheckIn = true;

    CheckInView.prototype.el = "#check-in-view-container";

    CheckInView.prototype.template = template;

    CheckInView.prototype.please = "Please help us Find the Green Line. Your fellow Bostonians and Green Line riders will thank you.";

    CheckInView.prototype.thankYou = ["Running late, no surprise there.  Now everybody else knows though. Thanks!!", "Classic Green Line, just being wherever it wants, whenever it wants. Thanks for the heads up!"];

    CheckInView.prototype.events = {
      'click #check-in-to-map': 'toMap',
      'click #check-in-to-thanks': 'toThanks'
    };

    CheckInView.prototype.render = function() {
      this.renderData = {};
      $(this.el).html(this.template(this.getRenderData()));
      this.afterRender();
      return this;
    };

    CheckInView.prototype.setRenderData = function(line, name) {
      this.renderData = {
        line: line,
        name: name
      };
      return this.afterRender();
    };

    CheckInView.prototype.getRenderData = function() {
      return {
        checkin: {
          please: this.please,
          thankYou: this.getThankYou()
        }
      };
    };

    CheckInView.prototype.afterRender = function() {
      $("#station-select").val(this.renderData.name);
      return $("#line-select").val(this.renderData.line);
    };

    CheckInView.prototype.getThankYou = function() {
      return helpers.getRandomElement(this.thankYou);
    };

    CheckInView.prototype.toMap = function() {
      return helpers.toMap();
    };

    CheckInView.prototype.toThanks = function() {
      return helpers.toThanks();
    };

    return CheckInView;

  })(View);

  module.exports = CheckInView;

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

    HomeView.prototype.isHeader = true;

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
    __bind = function(fn, me){ return function(){ return fn.apply(me, arguments); }; },
    __hasProp = Object.prototype.hasOwnProperty,
    __extends = function(child, parent) { for (var key in parent) { if (__hasProp.call(parent, key)) child[key] = parent[key]; } function ctor() { this.constructor = child; } ctor.prototype = parent.prototype; child.prototype = new ctor; child.__super__ = parent.prototype; return child; };

  View = require('./view');

  app = require('../application');

  template = require('./templates/homeViewTemplate');

  helpers = require('../lib/helpers');

  HomeView = (function(_super) {

    __extends(HomeView, _super);

    function HomeView() {
      this.getRenderData = __bind(this.getRenderData, this);
      this.getClosest = __bind(this.getClosest, this);
      this.afterRender = __bind(this.afterRender, this);
      HomeView.__super__.constructor.apply(this, arguments);
    }

    HomeView.prototype.isHome = true;

    HomeView.prototype.el = "#home-view-container";

    HomeView.prototype.template = template;

    HomeView.prototype.urlBase = "" + app.env.API_BASE + "/stations/closest";

    HomeView.prototype.afterRender = function() {
      this.isCurrent = true;
      return helpers.resizeText("waiting", 12);
    };

    HomeView.prototype.getClosest = function(location) {
      var _this = this;
      return $.ajax({
        url: "" + this.urlBase + "?latitude=" + location.coords.latitude + "&longitude=" + location.coords.longitude,
        type: 'GET',
        dataType: 'json',
        success: function(data, textStatus, jqHXR) {
          if (data.distance < 1000) {
            _this.closest = data;
            return _this.render();
          } else {
            return "";
          }
        },
        error: function(xhr, status, error) {
          return "";
        }
      });
    };

    HomeView.prototype.getRenderData = function() {
      if (this.closest) {
        return {
          home: this.closest
        };
      } else {
        return {};
      }
    };

    return HomeView;

  })(View);

  module.exports = HomeView;

}).call(this);

  }
}));
(this.require.define({
  "views/mapView": function(exports, require, module) {
    (function() {
  var Icons, Location, MapView, Stations, Stops, View, app, helpers, template,
    __bind = function(fn, me){ return function(){ return fn.apply(me, arguments); }; },
    __hasProp = Object.prototype.hasOwnProperty,
    __extends = function(child, parent) { for (var key in parent) { if (__hasProp.call(parent, key)) child[key] = parent[key]; } function ctor() { this.constructor = child; } ctor.prototype = parent.prototype; child.prototype = new ctor; child.__super__ = parent.prototype; return child; };

  View = require('./view');

  app = require('../application');

  template = require('./templates/mapViewTemplate');

  helpers = require('../lib/helpers');

  Location = require('../models/location');

  Stops = require('../collections/stops');

  Stations = require('../collections/stations');

  Icons = require('../lib/icons');

  MapView = (function(_super) {

    __extends(MapView, _super);

    function MapView() {
      this.toCheckIn = __bind(this.toCheckIn, this);
      this.toHome = __bind(this.toHome, this);
      this.createMarker = __bind(this.createMarker, this);
      this.renderStations = __bind(this.renderStations, this);
      this.getContentFromName = __bind(this.getContentFromName, this);
      this.createInfoWindowContent = __bind(this.createInfoWindowContent, this);
      this.locationCallback = __bind(this.locationCallback, this);
      this.afterRender = __bind(this.afterRender, this);
      this.initialize = __bind(this.initialize, this);
      MapView.__super__.constructor.apply(this, arguments);
    }

    MapView.prototype.isMap = true;

    MapView.prototype.el = "#map-view-container";

    MapView.prototype.mapId = "map-container";

    MapView.prototype.template = template;

    MapView.prototype.location = null;

    MapView.prototype.stops = new Stops();

    MapView.prototype.stations = new Stations();

    MapView.prototype.infoWindow = new google.maps.InfoWindow();

    MapView.prototype.markers = {};

    MapView.prototype.infoWindowContent = {};

    MapView.prototype.events = {
      'click #map-to-check-in': 'toCheckIn',
      'click #map-to-home': 'toHome'
    };

    MapView.prototype.initialize = function() {
      this.location = new Location(this.locationCallback, false);
      this.mapOptions = {
        center: new google.maps.LatLng(42.347031, -71.082788),
        zoom: 15,
        mapTypeId: google.maps.MapTypeId.ROADMAP
      };
      this.stops.fetch(this.createInfoWindowContent, true);
      return this.stations.fetch(this.render);
    };

    MapView.prototype.afterRender = function() {
      this.map = new google.maps.Map(document.getElementById(this.mapId), this.mapOptions);
      this.transitLayer = new google.maps.TransitLayer();
      this.transitLayer.setMap(this.map);
      if (!this.location.isDefault) this.marker.setMap(this.map);
      return this.renderStations();
    };

    MapView.prototype.locationCallback = function(location) {
      app.newLocation(location);
      this.location = location;
      this.mapOptions.center = new google.maps.LatLng(location.coords.latitude, location.coords.longitude);
      this.marker = new google.maps.Marker({
        position: this.mapOptions.center,
        title: "You",
        icon: Icons.person
      });
      return this.afterRender();
    };

    MapView.prototype.createInfoWindowContent = function(render) {
      var info, stop, _i, _len, _ref;
      this.infoWindowContent = {};
      _ref = this.stops.models;
      for (_i = 0, _len = _ref.length; _i < _len; _i++) {
        stop = _ref[_i];
        info = {
          direction: stop.get("direction"),
          time: new Date(stop.get("time"))
        };
        if (!this.infoWindowContent[stop.get("name")]) {
          this.infoWindowContent[stop.get("name")] = [];
        }
        this.infoWindowContent[stop.get("name")].push(info);
      }
      if (render) return this.afterRender();
    };

    MapView.prototype.getContentFromName = function(name) {
      var content, info, infos, time, timeReadable, _i, _len;
      infos = this.infoWindowContent[name];
      if (!infos) {
        this.infoWindowContent[name] = [];
        infos = [];
      }
      content = "<strong>" + name + "</strong>";
      for (_i = 0, _len = infos.length; _i < _len; _i++) {
        info = infos[_i];
        time = new Date(info.time);
        timeReadable = "" + (helpers.convertToTimeString(time));
        content += "<div>Direction: " + info.direction + "<br>Arrival Time: " + timeReadable + "</div>";
      }
      return content;
    };

    MapView.prototype.renderStations = function() {
      var station, _i, _len, _ref, _results;
      _ref = this.stations.models;
      _results = [];
      for (_i = 0, _len = _ref.length; _i < _len; _i++) {
        station = _ref[_i];
        _results.push(this.createMarker(station));
      }
      return _results;
    };

    MapView.prototype.createMarker = function(station) {
      var content, name,
        _this = this;
      name = station.get("name");
      content = this.getContentFromName(name);
      this.markers[name] = new google.maps.Marker({
        position: new google.maps.LatLng(station.get("latitude"), station.get("longitude")),
        title: name,
        icon: Icons.station
      });
      this.markers[name].setMap(this.map);
      return google.maps.event.addListener(this.markers[name], 'click', function() {
        _this.infoWindow.setContent(content);
        return _this.infoWindow.open(_this.map, _this.markers[name]);
      });
    };

    MapView.prototype.toHome = function() {
      return helpers.toHome();
    };

    MapView.prototype.toCheckIn = function() {
      return helpers.toCheckIn();
    };

    return MapView;

  })(View);

  module.exports = MapView;

}).call(this);

  }
}));
(this.require.define({
  "views/templates/checkInViewTemplate": function(exports, require, module) {
    module.exports = Handlebars.template(function (Handlebars,depth0,helpers,partials,data) {
  helpers = helpers || Handlebars.helpers;
  var buffer = "", stack1, foundHelper, self=this, functionType="function", helperMissing=helpers.helperMissing, undef=void 0, escapeExpression=this.escapeExpression;


  buffer += "<div class=\"check-in-copy\">";
  foundHelper = helpers.checkin;
  stack1 = foundHelper || depth0.checkin;
  stack1 = (stack1 === null || stack1 === undefined || stack1 === false ? stack1 : stack1.please);
  if(typeof stack1 === functionType) { stack1 = stack1.call(depth0, { hash: {} }); }
  else if(stack1=== undef) { stack1 = helperMissing.call(depth0, "checkin.please", { hash: {} }); }
  buffer += escapeExpression(stack1) + "</div>\n<div id=\"hs_field_wrapper_select\" class=\"field  \">\n    <label for=\"basic_form_id_select\">\n        Line\n    </label>\n    <div class=\"input line-select\">\n        <select name=\"select\" required=\"required\" id=\"line-select\" class=\"hs-input\">\n        		<option value=\"A\">\n               Trunk\n            </option>\n            <option value=\"B\">\n               B Line\n            </option>\n            <option value=\"C\">\n                C Line\n            </option>\n            <option value=\"D\">\n                D Line\n            </option>\n            <option value=\"E\">\n                E Line\n            </option>\n        </select>\n    </div>\n</div>\n<div id=\"hs_field_wrapper_select\" class=\"field\">\n    <label for=\"basic_form_id_select\">\n        Station\n    </label>\n    <div class=\"input station-select\">\n        <select name=\"select\" required=\"required\" id=\"station-select\" class=\"hs-input\">\n        	<option value=\"Lechmere\">\n               Lechmere\n            </option>\n            <option value=\"Science Park\">\n               Science Park\n            </option>\n        		<option value=\"North\">\n               North Station\n            </option>\n            <option value=\"Haymarket\">\n               Haymarket\n            </option>\n            <option value=\"Government Center\">\n                Government Center\n            </option>\n            <option value=\"Park St.\">\n                Park St.\n            </option>\n            <option value=\"Boylston\">\n            	Boylston\n            </option>\n            <option value=\"Arlington\">\n            	Arlington\n            </option>\n            <option value=\"Copley\">\n            	Copley\n            </option>\n            <option value=\"Prudential\">\n            	Prudential\n            </option>\n            <option value=\"Symphony\">\n            	Symphony\n            </option>\n            <option value=\"Northeastern University\">\n            	Northeastern University\n            </option>\n            <option value=\"Museum of Fine Arts\">\n            	Museum of Fine Arts\n            </option>\n            <option value=\"Longwood Medical Area\">\n            	Longwood Medical Area\n            </option>\n            <option value=\"Brigham Circle\">\n            	Brigham Circle\n            </option>\n            <option value=\"Fenwood Rd.\">\n            	Fenwood Rd.\n            </option>\n            <option value=\"Mission Park\">\n            	Mission Park\n            </option>\n            <option value=\"Riverway\">\n            	Riverway\n            </option>\n            <option value=\"Back of the Hill\">\n            	Back of the Hill\n            </option>\n            <option value=\"Heath St.\">\n            	Heath St.\n            </option>\n        </select>\n    </div>\n</div>\n<div id=\"hs_field_wrapper_select\" class=\"field  \">\n    <label for=\"basic_form_id_select\">\n        Direction\n    </label>\n    <div class=\"input direction-select\">\n        <select name=\"select\" required=\"required\" id=\"basic_form_id_select\" class=\"hs-input\">\n        		<option value=\"Lechmere\">\n        			East Towards Lechmere\n            </option>\n            <option value=\"Heath St.\">\n               West Towards Heath St.\n            </option>\n        </select>\n    </div>\n</div>\n<div class=\"submit-check-in-button center\"> <button id=\"check-in-to-thanks\" class=\"hs-button large green\">Submit Check In</button> <button id=\"check-in-to-map\" class=\"hs-button large red\">Back to Map</button></div>";
  return buffer;});
  }
}));
(this.require.define({
  "views/templates/headerViewTemplate": function(exports, require, module) {
    module.exports = Handlebars.template(function (Handlebars,depth0,helpers,partials,data) {
  helpers = helpers || Handlebars.helpers;
  var foundHelper, self=this;


  return "<div class=\"center header-banner\">\n	<span><Strong> Find The Green Line</Strong></span>\n</div>";});
  }
}));
(this.require.define({
  "views/templates/homeViewTemplate": function(exports, require, module) {
    module.exports = Handlebars.template(function (Handlebars,depth0,helpers,partials,data) {
  helpers = helpers || Handlebars.helpers;
  var buffer = "", stack1, stack2, foundHelper, tmp1, self=this, functionType="function", helperMissing=helpers.helperMissing, undef=void 0, escapeExpression=this.escapeExpression;

function program1(depth0,data) {
  
  var buffer = "", stack1;
  buffer += "\n	<div class=\"waiting center needs-bottom\">\n		<div>\n			<span><h4>We calculated that you are ";
  foundHelper = helpers.home;
  stack1 = foundHelper || depth0.home;
  stack1 = (stack1 === null || stack1 === undefined || stack1 === false ? stack1 : stack1.distance);
  if(typeof stack1 === functionType) { stack1 = stack1.call(depth0, { hash: {} }); }
  else if(stack1=== undef) { stack1 = helperMissing.call(depth0, "home.distance", { hash: {} }); }
  buffer += escapeExpression(stack1) + "ft from ";
  foundHelper = helpers.home;
  stack1 = foundHelper || depth0.home;
  stack1 = (stack1 === null || stack1 === undefined || stack1 === false ? stack1 : stack1.name);
  if(typeof stack1 === functionType) { stack1 = stack1.call(depth0, { hash: {} }); }
  else if(stack1=== undef) { stack1 = helperMissing.call(depth0, "home.name", { hash: {} }); }
  buffer += escapeExpression(stack1) + " Station.  		Are you waiting for a train there?</h4>\n			</span>\n		</div>\n		<a class=\"waiting-button hs-button primary large green inline\" href=\"#/checkin/E/";
  foundHelper = helpers.home;
  stack1 = foundHelper || depth0.home;
  stack1 = (stack1 === null || stack1 === undefined || stack1 === false ? stack1 : stack1.name);
  if(typeof stack1 === functionType) { stack1 = stack1.call(depth0, { hash: {} }); }
  else if(stack1=== undef) { stack1 = helperMissing.call(depth0, "home.name", { hash: {} }); }
  buffer += escapeExpression(stack1) + "\">Yes</a>\n		<a href=\"#/map\" class=\"waiting-button hs-button primary large red inline\">No </a>\n	</div>\n";
  return buffer;}

  foundHelper = helpers.home;
  stack1 = foundHelper || depth0.home;
  stack2 = helpers['if'];
  tmp1 = self.program(1, program1, data);
  tmp1.hash = {};
  tmp1.fn = tmp1;
  tmp1.inverse = self.noop;
  stack1 = stack2.call(depth0, stack1, tmp1);
  if(stack1 || stack1 === 0) { buffer += stack1; }
  buffer += "\n<div class=\"center needs-top-margin\">\n	<div class=\"home-view-full-button\">\n		<h4><span class=\"inline\">Currently on the T? Spread your knowledge and check in! The world will become a better place.</span></h4>\n		<a href=\"#/checkin\" class=\"big-button hs-button primary large green inline\">Check In</a>\n	</div>\n	<div class=\"needs-top-margin\">\n		<h4><span class=\"inline\">Waiting for the T? We're not surprised, so are we. Check the map out here.</span></h4>\n		<a href=\"#/map\" class=\"hs-button primary large big-button inline\">Map</a>\n	</div>\n</div>";
  return buffer;});
  }
}));
(this.require.define({
  "views/templates/mapViewTemplate": function(exports, require, module) {
    module.exports = Handlebars.template(function (Handlebars,depth0,helpers,partials,data) {
  helpers = helpers || Handlebars.helpers;
  var foundHelper, self=this;


  return "<div id=\"map-container\"></div>\n<div class=\"submit-check-in-button center\"> <button id=\"map-to-check-in\" class=\"hs-button large green\">Submit Check In</button> <button id= \"map-to-home\" class=\"hs-button large red\">Back to Home</button></div>";});
  }
}));
(this.require.define({
  "views/templates/thanksViewTemplate": function(exports, require, module) {
    module.exports = Handlebars.template(function (Handlebars,depth0,helpers,partials,data) {
  helpers = helpers || Handlebars.helpers;
  var foundHelper, self=this;


  return "<div><span><h4>Thanks</h3></span></div>\n<div class=\"center\"> <button id=\"thanks-to-home\" class=\"hs-button large green\">Back to Home</button> <button id=\"thanks-to-map\" class=\"hs-button large red\">Back to Map</button></div>";});
  }
}));
(this.require.define({
  "views/thanksView": function(exports, require, module) {
    (function() {
  var ThanksView, View, app, helpers, template,
    __bind = function(fn, me){ return function(){ return fn.apply(me, arguments); }; },
    __hasProp = Object.prototype.hasOwnProperty,
    __extends = function(child, parent) { for (var key in parent) { if (__hasProp.call(parent, key)) child[key] = parent[key]; } function ctor() { this.constructor = child; } ctor.prototype = parent.prototype; child.prototype = new ctor; child.__super__ = parent.prototype; return child; };

  View = require('./view');

  app = require('../application');

  template = require('./templates/thanksViewTemplate');

  helpers = require('../lib/helpers');

  ThanksView = (function(_super) {

    __extends(ThanksView, _super);

    function ThanksView() {
      this.toHome = __bind(this.toHome, this);
      this.toCheckIn = __bind(this.toCheckIn, this);
      ThanksView.__super__.constructor.apply(this, arguments);
    }

    ThanksView.prototype.isThanks = true;

    ThanksView.prototype.el = "#thanks-view-container";

    ThanksView.prototype.template = template;

    ThanksView.prototype.events = {
      'click #thanks-to-check-in': 'toCheckIn',
      'click #thanks-to-home': 'toHome'
    };

    ThanksView.prototype.toCheckIn = function() {
      return helpers.toCheckIn();
    };

    ThanksView.prototype.toHome = function() {
      return helpers.toHome();
    };

    return ThanksView;

  })(View);

  module.exports = ThanksView;

}).call(this);

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
      this.current = false;
      return $(this.el).hide();
    };

    View.prototype.show = function() {
      this.current = true;
      return $(this.el).show();
    };

    return View;

  })(Backbone.View);

}).call(this);

  }
}));
