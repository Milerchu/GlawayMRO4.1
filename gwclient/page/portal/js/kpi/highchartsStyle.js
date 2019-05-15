$(function(){
	Highcharts.setOptions({
		credits:{
			enabled:false
		},
		lang: {
            drillUpText: '<< 返回 {series.name}'
        },
        legend:{
			enabled:false
		},
		chart:{
			backgroundColor:'rgba(255,255,255,0.8)',/*'rgba(166,200,222,0.7)',*/
			borderColor: '#96B8E6',
            borderWidth: 2,
            /*marginTop:65,*/
            shadow:true,
            borderRadius:8,
            reflow:true
		},
		title:{
			text:null,
			style:{
				color:'#001995'/*'#4d6ae1'*/,
				fontWeight: 'bold',
				fontFamily:'Microsoft YaHei',
				paddingTop:'3%'
			}
		},
		xAxis:{
			labels:{
				style:{
					color:'#001995'
					//color:'#2F7ED8'
					/*fontFamily: '宋体',		
					fontWeight: 'normal'*/

				}
			},
			gridLineColor:'#4d759e',
			lineColor:'#3e82c3',
			tickWidth:0
			//tickPosition: 'inside',
			//tickmarkPlacement: 'on',
			//tickColor:'#3e82c3'
		},
		yAxis:{
			labels:{
				style:{
					color:'#001995'
					//color:'#2F7ED8'
					/*fontFamily: '宋体',		
					fontWeight: 'normal'*/
				}
			},
			gridLineDashStyle: 'shortdash',
			gridLineColor:'rgba(62,130,195,0.5)',
			lineColor: '#3e82c3',
			lineWidth: 1
		},
		 drilldown: {
	           activeAxisLabelStyle: {
	        	   color:'#3e83c4',
	               textDecoration: 'none'
	           },
	           activeDataLabelStyle:{
	        	   color:'#3e83c4',
	               textDecoration: 'none',
	               textShadow:'none'
	           }
	     }
	});
});