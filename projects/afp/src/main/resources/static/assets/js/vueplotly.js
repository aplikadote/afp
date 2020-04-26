// JavaScript source code
Vue.component('plotly-graph', {
    template: "<div v-bind:id='divName'></div>",
    props: {
        data: {type: Array},
        layout: {type: Object},
        options: {type: Object},
        divName: {type: String, required: true, default: "test1"}
//        resize: {type: Boolean, default: true}
    },
    mounted() {
        this.Plot();
//        $('#' + this.divId).on('plotly_hover', this.hover);
        this.$watch('data', this.Plot, {deep: true});
//        $(window).bind('resize', this.onResize);
    },
    beforeDestroy() {
//        this.$el.off('plotly_hover', this.hover);
    },
    methods: {
//        hover: function (event, eventData) {
//            this.$emit('hover', eventData.points, this.divId);
//        },
        Plot() {
            return Plotly.newPlot(this.divName, this.data, this.layout, this.options);
        },
//        onResize() {
//            if (this.resize) {
//                var d3 = Plotly.d3;
//                gd3 = d3.select('#' + this.divId);
//                gd3.style({
//                    width: '100%',
//                    height: this.height + '%'
//                });
//                Plotly.Plots.resize(gd3[0][0]);
//            }
//        }
    }
});
