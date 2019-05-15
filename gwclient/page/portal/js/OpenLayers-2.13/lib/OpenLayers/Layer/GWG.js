
OpenLayers.Layer.GWG = OpenLayers.Class(OpenLayers.Layer.XYZ, {

    name: "Glaway",

    url: [
        ""
    ],

    attribution: "<a href=''></a>",

    sphericalMercator: true,

    wrapDateLine: true,

    tileOptions: null,

    initialize: function(name, url, options) {
        OpenLayers.Layer.XYZ.prototype.initialize.apply(this, arguments);
        this.tileOptions = OpenLayers.Util.extend({
            crossOriginKeyword: 'anonymous'
        }, this.options && this.options.tileOptions);
    },

    clone: function(obj) {
        if (obj == null) {
            obj = new OpenLayers.Layer.GWG(
                this.name, this.url, this.getOptions());
        }
        obj = OpenLayers.Layer.XYZ.prototype.clone.apply(this, [obj]);
        return obj;
    },

    CLASS_NAME: "OpenLayers.Layer.GWG"
});
