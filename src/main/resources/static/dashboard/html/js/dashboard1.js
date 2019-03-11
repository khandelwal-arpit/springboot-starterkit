/*
Template Name: Material Pro Admin
Author: Themedesigner
Email: niravjoshi87@gmail.com
File: js
*/
$(function() {
    "use strict";
    // ============================================================== 
    // Sales overview
    // ============================================================== 
    var chart2 = new Chartist.Bar('.amp-pxl', {
        labels: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun'],
        series: [
            [9, 5, 3, 7, 5, 10, 3],
            [6, 3, 9, 5, 4, 6, 4]
        ]
    }, {
        axisX: {
            // On the x-axis start means top and end means bottom
            position: 'end',
            showGrid: false
        },
        axisY: {
            // On the y-axis start means left and end means right
            position: 'start'
        },
        high: '12',
        low: '0',
        plugins: [
            Chartist.plugins.tooltip()
        ]
    });

    var chart = [chart2];

    // ============================================================== 
    // This is for the animation
    // ==============================================================

    for (var i = 0; i < chart.length; i++) {
        chart[i].on('draw', function(data) {
            if (data.type === 'line' || data.type === 'area') {
                data.element.animate({
                    d: {
                        begin: 500 * data.index,
                        dur: 500,
                        from: data.path.clone().scale(1, 0).translate(0, data.chartRect.height()).stringify(),
                        to: data.path.clone().stringify(),
                        easing: Chartist.Svg.Easing.easeInOutElastic
                    }
                });
            }
            if (data.type === 'bar') {
                data.element.animate({
                    y2: {
                        dur: 500,
                        from: data.y1,
                        to: data.y2,
                        easing: Chartist.Svg.Easing.easeInOutElastic
                    },
                    opacity: {
                        dur: 500,
                        from: 0,
                        to: 1,
                        easing: Chartist.Svg.Easing.easeInOutElastic
                    }
                });
            }
        });
    }

    // ============================================================== 
    // Our visitor
    // ============================================================== 

    var chart = c3.generate({
        bindto: '#visitor',
        data: {
            columns: [
                ['Other', 30],
                ['Desktop', 10],
                ['Tablet', 40],
                ['Mobile', 50],
            ],

            type: 'donut',
            onclick: function(d, i) { console.log("onclick", d, i); },
            onmouseover: function(d, i) { console.log("onmouseover", d, i); },
            onmouseout: function(d, i) { console.log("onmouseout", d, i); }
        },
        donut: {
            label: {
                show: false
            },
            title: "Our visitor",
            width: 20,

        },

        legend: {
            hide: true
                //or hide: 'data1'
                //or hide: ['data1', 'data2']
        },
        color: {
            pattern: ['#eceff1', '#745af2', '#26c6da', '#1e88e5']
        }
    });





});
