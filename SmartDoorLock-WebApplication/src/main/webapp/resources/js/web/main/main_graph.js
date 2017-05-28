$(function() {
	console.log("main_graph.js!");
	var email = $("#login_email").text();
	console.log(email);
		//그래프를 그립니다.
	serviceInfo(email);
	userInfo(email);
});
/*
 /{email}/graph

 private String email;
 private String collection;
 private String serial_no;
 private String member_id;
 private String cat_id;
 private String date;

 */
//  /{email}/graph/service/info
// 


function serviceInfo(email) {
	$.ajax({
		url : "/" + email + "/graph/service/info",
		dataType : "json",
		method : "POST",
		success : function(res) {
			console.log(res);
			$("#service_info_card .tile-stats > .count:eq(0)").html(res.usageUserCount.COUNT);
			$("#service_info_card .tile-stats > .count:eq(1)").html(res.usageDoorlockCount.COUNT);
			$("#service_info_card .tile-stats > .count:eq(2)").html(res.usageKeyCount.COUNT);
			$("#service_info_card .tile-stats > .count:eq(3)").html(res.usageServiceCount[res.usageServiceCount.length - 1].COUNT);

			//res.usageServiceCount
			//res.usageWebCount
			//res.usageAppCount
			var service=[];
			var web=[];
			var app=[];
			var temp = {};

			for(var i=0;i<res.usageServiceCount.length;i++){
				temp = res.usageServiceCount[i];
				service.push([new Date(temp.LOG_DATE).getTime(),temp.COUNT]);
			}
			for(var i=0;i<res.usageWebCount.length;i++){
				temp = res.usageWebCount[i];
				web.push([new Date(temp.LOG_DATE).getTime(),temp.COUNT]);
			}
			for(var i=0;i<res.usageAppCount.length;i++){
				temp = res.usageAppCount[i];
				app.push([new Date(temp.LOG_DATE).getTime(),temp.COUNT]);
			}
			
			//Flot Start -- end
			//define chart clolors ( you maybe add more colors if you want or flot will add it automatic )
			var chartColours = [ '#96CA59', '#3F97EB', '#72c380', '#6f7a8a', '#f7cb38', '#5a8022', '#2c7282' ];

			var tickSize = [ 1, "day" ];
			var tformat = "%y-%m-%d";

			//graph options
			var options = {
				grid : {
					show : true,
					aboveData : true,
					color : "#3f3f3f",
					labelMargin : 10,
					axisMargin : 0,
					borderWidth : 0,
					borderColor : null,
					minBorderMargin : 5,
					clickable : true,
					hoverable : true,
					autoHighlight : true,
					mouseActiveRadius : 100
				},
				series : {
					lines : {
						show : true,
						fill : true,
						lineWidth : 2,
						steps : false
					},
					points : {
						show : true,
						radius : 4.5,
						symbol : "circle",
						lineWidth : 3.0
					}
				},
				legend : {
					position : "ne",
					margin : [ 0, -25 ],
					noColumns : 0,
					labelBoxBorderColor : null,
					labelFormatter : function(label, series) {
						// just add some space to labes
						return label + '&nbsp;&nbsp;';
					},
					width : 40,
					height : 1
				},
				colors : chartColours,
				shadowSize : 0,
				tooltip : true, //activate tooltip
				tooltipOpts : {
					content : "%s: %y.0",
					xDateFormat : "%y-%m-%d",
					shifts : {
						x : -30,
						y : -50
					},
					defaultTheme : false
				},
				yaxis : {
					min : 0
				}
				,
				xaxis : {
					mode : "time",
					minTickSize : tickSize,
					timeformat : tformat,
					min : service[0][0],
					max : service[service.length-1][0]
				}
			};
			
			
			var plot = $.plot($("#placeholder33x"),[ 
				{label : "전체 서비스 사용량",data : service},
				{label : "웹 사용량",data : web},
				{label : "앱 사용량",data : app}
			], options);
			//Flot Chart -- end
		},
		error : function(err, req, status) {
			console.log(err);
			console.log(req);
			console.log(status);
		}
	});
}
function userInfo(email) {
	$.ajax({
		url : "/" + email + "/graph",
		data : {collection : "USER_USAGE"},
		dataType : "json",
		method : "POST",
		success : function(res) {
			var response = res[0].GRAPH;
			
			Morris.Bar({
	          element: 'user_usage',
	          data: response,
	          ykeys: ['COUNT'],
	          xkey: 'LOG_DATE',
	          hideHover: 'auto',
	          barColors: ['#26B99A', '#34495E', '#ACADAC', '#3498DB'],
	          labels: ['접속량'],
	          xLabelAngle: 60,
	          resize: true
	        });
		},
		error : function(err, req, status) {
			console.log(err);
			console.log(req);
			console.log(status);
		}
	});
	$.ajax({
		url : "/" + email + "/graph",
		data : {
			collection : "USER_HAVE_KEY"
		},
		dataType : "json",
		method : "POST",
		success : function(res) {
			// Moris.Donut End
			console.log(res);
			var response = res[0].GRAPH[res[0].GRAPH.length-1];
			var result = [];
			result.push({label:"MASTER",value:response.MASTER});
			result.push({label:"MANAGER",value:response.MANAGER});
			result.push({label:"MEMBER",value:response.MEMBER});
			
			Morris.Donut({
		          element: 'user_have_key',
		          data: result,
		          ykeys: ['COUNT'],
		          xkey: 'LOG_DATE',
		          hideHover: 'auto',
		          barColors: ['#26B99A', '#34495E', '#ACADAC', '#3498DB'],
		          labels: ['접속량'],
		          xLabelAngle: 60,
		          resize: true
		        });
			// Moris.Donut End
			
			res = res[0].GRAPH
			var master = [];
			var manager = []; 
			var member = []; 
			
			for(var temp in res){
				master.push({label:res[temp].DATE,y:res[temp].MASTER});
				manager.push({label:res[temp].DATE,y:res[temp].MANAGER});
				member.push({label:res[temp].DATE,y:res[temp].MEMBER});
			}
			
			var chart = new CanvasJS.Chart("user_have_key2", {
				title : {
					text : "날짜별 열쇠 소지 그래프"
				},
				animationEnabled : true,
				legend : {
					cursor : "pointer",
					itemclick : function(e) {
						if (typeof (e.dataSeries.visible) === "undefined"
								|| e.dataSeries.visible) {
							e.dataSeries.visible = false;
						} else {
							e.dataSeries.visible = true;
						}
						chart.render();
					}
				},
				axisY : {
					title : "권한별 열쇠 소지량"
				},
				toolTip : {
					shared : true,
					content : function(e) {
						var str = '';
						var total = 0;
						var str3;
						var str2;
						for (var i = 0; i < e.entries.length; i++) {
							var str1 = "<span style= 'color:"
									+ e.entries[i].dataSeries.color + "'> "
									+ e.entries[i].dataSeries.name
									+ "</span>: <strong>" + e.entries[i].dataPoint.y
									+ "</strong> <br/>";
							total = e.entries[i].dataPoint.y + total;
							str = str.concat(str1);
						}
						str2 = "<span style = 'color:#263238; '><strong>"
								+ e.entries[0].dataPoint.label
								+ "</strong></span><br/>";
						str3 = "<span style = 'color:#880e4f; '>총 열쇠 개수: </span><strong>"
								+ total + "</strong><br/>";

						return (str2.concat(str)).concat(str3);
					}
				},
				data : [{
					type : "bar",
					showInLegend : true,
					name : "마스터",
					color : "#2A3F54",
					dataPoints : master
				},{
					type : "bar",
					showInLegend : true,
					name : "매니저",
					color : "#4caf50",
					dataPoints : manager
				}, {
					type : "bar",
					showInLegend : true,
					name : "일반 사용자",
					color : "#00bfa5",
					dataPoints : member
				}]
			});
			chart.render();
		},
		error : function(err, req, status) {
			console.log(err);
			console.log(req);
			console.log(status);
		}
	});
	

	

	$.ajax({
		url : "/" + email + "/graph",
		data : {
			collection : "USER_MANAGE_DOORLOCK"
		},
		dataType : "json",
		method : "POST",
		success : function(res) {
			console.log(res);
		    // Then, let's add some data to display:
			var s = new sigma('doorlock_usage');
			s.graph.addNode({
			      // Main attributes:
			      id: email,
			      label: email,
			      // Display attributes:
			      x: 0,
			      y: 0,
			      size: 1,
			      color: '#2A3F54'
			    });
			
			for(var i in res){
				s.graph.addNode({
				      // Main attributes:
				      id: res[i].SERIAL_NO,
				      label: `${res[i].SERIAL_NO}`,
				      // Display attributes:
				      x: i,
				      y: i,
				      size: 1,
				      color: "#009999"
				}).addEdge({
					id: 'manageDoorlock#'+i,
				    source: email,
				    target: res[i].SERIAL_NO
				});
				for(var j in res[i].GRAPH){
					s.graph.addNode({
					      // Main attributes:
					      id: "LOG_DATE"+res[i].SERIAL_NO+"_"+j,
					      label: res[i].GRAPH[j].LOG_DATE+"["+res[i].GRAPH[j].COUNT+"]",
					      // Display attributes:
					      x: i,
					      y: i,
					      size: 1,
					      color: '#ff66cc'
					}).addEdge({
						id: 'manageDoorlock#'+i+"_"+j,
					    source: res[i].SERIAL_NO,
					    target: "LOG_DATE"+res[i].SERIAL_NO+"_"+j
					});
				}
		    }
			s.graph.nodes().forEach(function(node, index, all) {
			  if(node.id==email){
				  node.x= (Math.PI * 2 * index/all.length);
				  node.y= (Math.PI * 2 * index/all.length);
			  }else{
				  if(node.id.includes("LOG_DATE")){
					  node.x = 2*(Math.cos(Math.PI * 2 * index / (all.length-1)));
				      node.y = 2*(Math.sin(Math.PI * 2 * index / (all.length-1)));
				  }else{
					  node.x = (Math.cos(Math.PI * 2 * index / (all.length-1)));
				      node.y = (Math.sin(Math.PI * 2 * index / (all.length-1)));
				  }
				}
		    });
		    s.refresh();


		},
		error : function(err, req, status) {
			console.log(err);
			console.log(req);
			console.log(status);
		}
	});
	$.ajax({
		url : "/" + email + "/graph",
		data : {
			collection : "USER_MANAGE_DOORLOCK_USAGE"
		},
		dataType : "json",
		method : "POST",
		success : function(res) {
			console.log(res);
			var s = new sigma('doorlock_have_keys');
			
			s.graph.addNode({
			      // Main attributes:
			      id: email,
			      label: email,
			      // Display attributes:
			      x: 0,
			      y: 0,
			      size: 1,
			      color: '#2A3F54'
			    });
			
			for(var i in res){
				s.graph.addNode({
				      // Main attributes:
				      id: res[i].SERIAL_NO,
				      label: `${res[i].SERIAL_NO}`,
				      // Display attributes:
				      x: i,
				      y: i,
				      size: 1,
				      color: "#009999"
				}).addEdge({
					id: 'manageDoorlock#'+i,
				    source: email,
				    target: res[i].SERIAL_NO
				});
				
				//등급별 카운팅 -MASTER
				if(res[i].GRAPH[res[i].GRAPH.length-1].MASTER!=0){
					s.graph.addNode({
					      // Main attributes:
					      id: "MASTER_"+res[i].SERIAL_NO,
					      label: "MASTER["+res[i].GRAPH[res[i].GRAPH.length-1].MASTER+"]",
					      // Display attributes:
					      x: i,
					      y: i,
					      size: 1,
					      color: '#663300'
					}).addEdge({
						id: "#MASTER_"+res[i].SERIAL_NO,
					    source: res[i].SERIAL_NO,
					    target: "MASTER_"+res[i].SERIAL_NO
					});				
				}
				//등급별 카운팅 -MANAGER
				if(res[i].GRAPH[res[i].GRAPH.length-1].MANAGER!=0){
					s.graph.addNode({
					      // Main attributes:
					      id: "MANAGER_"+res[i].SERIAL_NO,
					      label: "MANAGER["+res[i].GRAPH[res[i].GRAPH.length-1].MANAGER+"]",
					      // Display attributes:
					      x: i,
					      y: i,
					      size: 1,
					      color: "#cc6600"
					}).addEdge({
						id: "#MANAGER_"+res[i].SERIAL_NO,
					    source: res[i].SERIAL_NO,
					    target: "MANAGER_"+res[i].SERIAL_NO
					});
				}
				//등급별 카운팅 -MEMBER
				if(res[i].GRAPH[res[i].GRAPH.length-1].MEMBER!=0){
					s.graph.addNode({
					      // Main attributes:
					      id: "MEMBER_"+res[i].SERIAL_NO,
					      label: "MEMBER["+res[i].GRAPH[res[i].GRAPH.length-1].MEMBER+"]",
					      // Display attributes:
					      x: i,
					      y: i,
					      size: 1,
					      color: "#ff8c1a"
					}).addEdge({
						id: "#MEMBER_"+res[i].SERIAL_NO,
					    source: res[i].SERIAL_NO,
					    target: "MEMBER_"+res[i].SERIAL_NO
					});
				}
			}
			
			s.graph.nodes().forEach(function(node, index, all) {
			  if(node.id==email){
				  node.x= (Math.PI * 2 * index/all.length);
				  node.y= (Math.PI * 2 * index/all.length);
			  }else{
				  if(node.id.includes("MASTER")||node.id.includes("MANAGER")||node.id.includes("MEMBER")){
					  node.x = 2*(Math.cos(Math.PI * 2 * index / (all.length-1)));
				      node.y = 2*(Math.sin(Math.PI * 2 * index / (all.length-1)));
				  }else{
					  node.x = (Math.cos(Math.PI * 2 * index / (all.length-1)));
				      node.y = (Math.sin(Math.PI * 2 * index / (all.length-1)));
				  }
				}
		    });
			
		    s.refresh();
			
		},
		error : function(err, req, status) {
			console.log(err);
			console.log(req);
			console.log(status);
		}
	});
}

