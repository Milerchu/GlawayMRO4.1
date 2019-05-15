OpenLayers.Layer.GWG.GIS = OpenLayers.Class(OpenLayers.Layer.GWG, {
    initialize: function(name, options) {
        var url = [
            OPenLayersURL + "/GWGIS/tiles/$" + "{z}/$" + "{x}/$" + "{y}.png"
        ];
        options = OpenLayers.Util.extend({
            numZoomLevels: 10,
            attribution: "",
            buffer: 0,
            transitionEffect: "resize"
        }, options);
        var newArguments = [name, url, options];
        OpenLayers.Layer.GWG.prototype.initialize.apply(this, newArguments);
    },

    CLASS_NAME: "OpenLayers.Layer.GWG.GIS"
});

