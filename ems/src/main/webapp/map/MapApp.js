Ext.require("Ems.baseinfo.Pole");
Ext.onReady(function(){
	Ext.define('Ext.ux.MultiComboBox', {
	    extend: 'Ext.form.ComboBox',
	    alias: 'widget.multicombobox',
	    xtype: 'multicombobox',
	    initComponent: function(){
	    	this.multiSelect = true;
	    	this.listConfig = {
		    	  itemTpl : Ext.create('Ext.XTemplate',
		  	    	    '<input type=checkbox>{name}'),
		  	      onItemSelect: function(record) {    
		  	    	  var node = this.getNode(record);
	    	          if (node) {
	    	             Ext.fly(node).addCls(this.selectedItemCls);
	    	             
	    	             var checkboxs = node.getElementsByTagName("input");
	    	             if(checkboxs!=null)
	    	             {
	    	            	 var checkbox = checkboxs[0];
	  	    				 checkbox.checked = true;
	    	             }
	    	          }
		  	      },
		  	      listeners:{
	  	    		  itemclick:function(view, record, item, index, e, eOpts ){
	  	    			  var isSelected = view.isSelected(item);
	  	    			  var checkboxs = item.getElementsByTagName("input");
	  	    			  if(checkboxs!=null)
	  	    			  {
	  	    				  var checkbox = checkboxs[0];
	  	    				  if(!isSelected)
	  	    				  {
	  	    					  checkbox.checked = true;
	  	    				  }else{
	  	    					  checkbox.checked = false;
	  	    				  }
	  	    			  }
	  	    		  }
		  	      }    	  
		  	}   	
	    	this.callParent();
	    }
	});


	//区
     	var customer_2=Ext.create('Ext.form.field.ComboBox',{
	        fieldLabel: '区',
	        labelAlign:'right',
            labelWidth:20,
            width:120,
	        //xtype:'combobox',
	        //afterLabelTextTpl: Ext.required,
            editable:false,
	        name: 'customer_2',
		    displayField: 'name',
		    valueField: 'id',
		    queryParam: 'name',
    		queryMode: 'remote',
//	        allowBlank: false,
	        store:Ext.create('Ext.data.Store', {
		    	fields: ['id', 'name'],
			    proxy:{
			    	type:'ajax',
			    	//extraParams:{type:1,edit:true},
			    	url:Ext.ContextPath+"/customer/query4combo.do",
			    	reader:{
			    		type:'json',
			    		root:'root'
			    	}
			    }
		   }),
		   listeners:{
		   	 change:function(field,newValue, oldValue){
				customer_0or1.clearValue( );
				if(newValue){
					customer_0or1.getStore().getProxy().extraParams={parent_id:newValue};
					customer_0or1.getStore().reload();
				}
				
			 }
		   }
		});
		var customer_0or1=Ext.create('Ext.ux.MultiComboBox',{
	        fieldLabel: '客户',
	        labelAlign:'right',
            labelWidth:40,
            width:250,
	        //xtype:'combobox',
	        //afterLabelTextTpl: Ext.required,
	        name: 'customer_0or1',
		    displayField: 'name',
		    valueField: 'id',
		    editable:false,
//		    queryParam: 'name',
//    		queryMode: 'remote',
//    		triggerAction: 'query',
//    		minChars:-1,
//		    trigger1Cls: Ext.baseCSSPrefix + 'form-clear-trigger',
//		    trigger2Cls: Ext.baseCSSPrefix + 'form-arrow-trigger',//'form-search-trigger',
//			onTrigger1Click : function(){
//			    var me = this;
//			    me.setValue('');
//			},
	        allowBlank: false,
	        store:Ext.create('Ext.data.Store', {
		    	fields: ['id', 'name'],
			    proxy:{
			    	type:'ajax',
			    	//extraParams:{type:1,edit:true},
			    	method:'POST',
			    	url:Ext.ContextPath+"/customer/query4combo.do",
			    	reader:{
			    		type:'json',
			    		root:'root'
			    	}
			    },
			    listeners:{
			    	beforeload:function(store){
				    	if(!customer_2.getValue()){
				    		delete customer_0or1.lastQuery;
				    		alert("请先选择'区'");
				    		return false;
				    	}
				    }
			    
			    }
			    
		   })
		});
		
//		var customer_0or1=Ext.create('Ext.form.field.ComboBox',{
//	        fieldLabel: '客户',
//	        labelAlign:'right',
//            labelWidth:40,
//            width:250,
//	        //xtype:'combobox',
//	        //afterLabelTextTpl: Ext.required,
//	        name: 'customer_0or1',
//		    displayField: 'name',
//		    valueField: 'id',
//		    queryParam: 'name',
//    		queryMode: 'remote',
//    		triggerAction: 'query',
//    		minChars:-1,
//		    trigger1Cls: Ext.baseCSSPrefix + 'form-clear-trigger',
//		    trigger2Cls: Ext.baseCSSPrefix + 'form-arrow-trigger',//'form-search-trigger',
//			onTrigger1Click : function(){
//			    var me = this;
//			    me.setValue('');
//			},
//	        allowBlank: false,
//	        store:Ext.create('Ext.data.Store', {
//		    	fields: ['id', 'name'],
//			    proxy:{
//			    	type:'ajax',
//			    	//extraParams:{type:1,edit:true},
//			    	method:'POST',
//			    	url:Ext.ContextPath+"/customer/query4combo.do",
//			    	reader:{
//			    		type:'json',
//			    		root:'root'
//			    	}
//			    },
//			    listeners:{
//			    	beforeload:function(store){
//				    	if(!customer_2.getValue()){
//				    		delete customer_0or1.lastQuery;
//				    		alert("请先选择'区'");
//				    		return false;
//				    	}
//				    }
//			    
//			    }
//			    
//		   })
//		});
		var workUnit_combox=Ext.create('Ext.form.field.ComboBox',{
	        fieldLabel: '作业单位',
	        labelAlign:'right',
            labelWidth:60,
	        //xtype:'combobox',
	        //afterLabelTextTpl: Ext.required,
	        name: 'workUnit_id',
		    displayField: 'name',
		    valueField: 'id',
	        allowBlank: false,
	        store:Ext.create('Ext.data.Store', {
		    	fields: ['id', 'name'],
			    proxy:{
			    	type:'ajax',
			    	url:Ext.ContextPath+"/workUnit/queryCombo.do",
			    	reader:{
			    		type:'json',
			    		root:'root'
			    	}
			    }
		   })
	    });
	var reload = new Ext.Action({
		    text: '查询',
		    itemId:'reload',
		    handler: function(){

		    	if(!customer_2.getValue() && !workUnit_combox.getValue()){
		    		alert("请选择一个客户或者作业单位!");
		    		return;
		    	}
		    	//console.log(customer_0or1.getValue().length==0);
		    	//return;
		    	if(customer_2.getValue() && (!customer_0or1.getValue() || customer_0or1.getValue().length==0)){
		    		alert("请选择一个客户,不选的话，将会由于数据量太大，导致浏览那群奔溃!");
		    		return;
		    	}
		    	//alert("在查询的同时，将会对所有查询结果在地图上进行初始化!如果数据量较大，速度将会有点慢!");
		    	poleStore.getProxy().extraParams=Ext.apply(poleStore.getProxy().extraParams,{
		    		queryNoLngLatPole:false,
		    		customer_2_id:customer_2.getValue(),
		    		customer_0or1_id:customer_0or1.getValue(),
		    		workunit_id:workUnit_combox.getValue()
		    	});
		    	poleStore.loadPage(1);
		    	showMap(poleStore.getProxy().extraParams);
		    },
		    iconCls: 'form-reload-button'
		});	
		
	var initAllPoleNoLngLat = new Ext.Action({
		    text: '初始化',
		    
		    handler: function(){
				Ext.Msg.confirm("提醒","这里将初始化所有没有设置经纬度的点位!不受查询条件影响!",function(btn){
					if(btn=='yes'){
						Ext.getBody().mask("正在初始化....");
						Ext.Ajax.request({
							url:Ext.ContextPath+'/map/initAllPoleNoLngLat.do',
							success:function(response){
								Ext.getBody().unmask();
								var obj=Ext.decode(response.responseText);
								if(obj.root){
									alert(obj.root+"\n等点位由于地址问题获取失败，请手工进行调整或者修改地址!");
								} else {
									alert("成功");
								}
								poleStore.loadPage(1);
								
							},
							failure:function(){
								Ext.getBody().unmask();
							}
						});
					}
				});
		    },
		    icon: '../icons/database_refresh.png'
		});	
		
		
		var queryNoLngLatPole = new Ext.Action({
		    text: '未初始化点位',
		    
		    handler: function(){
				Ext.Msg.confirm("提醒","这里查询的是没有设置经纬度的点位!!不受查询条件影响!",function(btn){
					if(btn=='yes'){
						poleStore.getProxy().extraParams={
							queryNoLngLatPole:true
						}
						poleStore.loadPage(1);
					}
				});
		    },
		    icon: '../icons/zoom_refresh.png'
		});	
	var poleStore=Ext.create('Ext.data.Store',{
				autoSync:false,
				pageSize:50,
				model: 'Ems.baseinfo.Pole',
				proxy: {
				        type: 'ajax',
				        url: Ext.ContextPath+'/map/queryPoles.do',
				        reader: {
				        	type:'json',
				            root: 'root'
				        }
				},
				autoLoad:false
		  });
    var polePanel=Ext.create('Ext.grid.Panel',{
    	region:'west',
    	width:400,
    	split: true,
		collapsible: true,
    	tbar:{
			xtype: 'container',
			layout: 'anchor',
			defaults: {anchor: '0'},
			defaultType: 'toolbar',
			items: [{
				items: [customer_2,customer_0or1] // toolbar 1
			},{
				items: [workUnit_combox,reload] // toolbar 1
			},{
				items: [queryNoLngLatPole,initAllPoleNoLngLat] // toolbar 1
			}]
		},
    	columns:[
    		Ext.create('Ext.grid.RowNumberer'),
	      	{dataIndex:'status',text:'状态',width:40,menuDisabled:true,renderer : function(value,metadata, record, rowIndex, columnIndex, store) {
	      	   metadata.tdAttr = "data-qtip='" + record.get("status_name")+ "'";
			   if (value == 'uninstall') {
			    return "<img src='../icons/help_circle_blue.png' />";
			   } else if (value == 'installing'){
			    return "<img src='../icons/circle_blue.png' />";
			   }else if (value == 'using'){
			    return "<img src='../icons/circle_green.png' />";
			   }else if (value == 'hitch'){
			    return "<img src='../icons/circle_yellow.png' />";
			   }else if (value == 'cancel'){
			    return "<img src='../icons/circle_red.png' />";
			   }
			   return record.get("status_name");
			 }
			},
			{dataIndex:'code',text:'编号',width:60},
			{dataIndex:'name',text:'点位名称',width:160},
	      	{dataIndex:'province',text:'地址',flex:1,renderer:function(value,metaData ,record){
	      		var fulladdress= value+record.get("city")+record.get("area")+record.get("address");
	      		metaData.tdAttr = "data-qtip='" + fulladdress+ "'";
	      		return fulladdress;
	      	}}
	      ],
	      store:poleStore,
		  dockedItems: [{
		        xtype: 'pagingtoolbar',
		        store:poleStore,  
		        dock: 'bottom',
		        displayInfo: true
		  }]
    });   
	
    polePanel.on("itemclick",function(view, record, item, index, e, eOpts){
    	
    	var id=record.get("id");
    	selectedMarker(markeres[id]);
    });
	
	
	var viewPort=Ext.create('Ext.container.Viewport',{
		layout:'border',
		items:[polePanel,{html:'<div id="allmap" style="height:100%;width:100%;"></div>',region:'center',
		split: true
		}]
	});
 
});
//档点位被选中的时候
function selectedMarker(marker){
	if(markeres.lastedClickMarker){
    	markeres.lastedClickMarker.setAnimation(null);
    }
	marker.setAnimation(BMAP_ANIMATION_BOUNCE);
    markeres.lastedClickMarker=marker;
    map.setCenter(marker.getPosition());
}
	function addClickHandler(content,marker){
		marker.addEventListener("mouseover",function(e){		
			openMarkerInfo(content,e);
		});
		marker.addEventListener("mouseout",function(e){		
			map.closeInfoWindow();
		});
	}
	function openMarkerInfo(content,e){
		var marker = e.target;
		var point = new BMap.Point(marker.getPosition().lng, marker.getPosition().lat);
		var infoWindow = new BMap.InfoWindow(content,opts);  // 创建信息窗口对象 
		map.openInfoWindow(infoWindow,point); //开启信息窗口
	}
var markeres={};
var map=null;
var opts = {
	width : 250,     // 信息窗口宽度
	height: 80,     // 信息窗口高度
	title : "" , // 信息窗口标题
	enableMessage:true//设置允许信息窗发送短息
};
//http://www.easyicon.net/510568-CCTV_Camera_icon.html  icon下载地址
var poleIcon = new BMap.Icon("./images/camera48.png", new BMap.Size(48,48));
//http://www.easyicon.net/1187198-Status_dialog_error_symbolic_icon.html
//http://www.easyicon.net/1187197-Status_dialog_error_icon.html
var brokenIcon = new BMap.Icon("./images/broken48.png", new BMap.Size(48,48));
function showMap(params){
	// 百度地图API功能
	map = new BMap.Map("allmap");
	var point = new BMap.Point(121.551852,29.834513);//定位到宁波的某个地方，中心点显示
	map.centerAndZoom(point, 15);
	map.addEventListener("zoomstart", function(type, target){
		map.orgion_center_point=map.getCenter();
		//map.setCenter(markeres.lastedClickMarker.getPosition());
	});
	map.addEventListener("zoomend", function(type, target){
		//map.setCenter(markeres.lastedClickMarker.getPosition());
		map.setCenter(map.orgion_center_point);
	});
	
	//查询符合条件的所有数据,排除了没有经纬度的数据，然后再地图上进行展示
	Ext.Ajax.request({
		url : Ext.ContextPath + '/map/queryPolesAll.do',
		params:params,
		method:'POST',
		success : function(response) {
			Ext.getBody().unmask();
			var obj = Ext.decode(response.responseText);
			for(var i=0;i<obj.root.length;i++){
				//console.log("longitude:"+pole.longitude+",latitude:"+pole.latitude);
				var pole=obj.root[i];
				var point = new BMap.Point(pole.longitude,pole.latitude);
				var marker =null;
				if(pole.status=='hitch'){
					marker = new BMap.Marker(point,{icon:brokenIcon});  // 创建标注
				} else {
					marker = new BMap.Marker(point,{icon:poleIcon});  // 创建标注
				}
				
				map.addOverlay(marker);               // 将标注添加到地图中
				marker.enableDragging(); 
				marker.pole_id=pole.id;
				
				addClickHandler("编码:"+pole.code+"<br/>名称:"+pole.name+"<br/>地址:"+pole.province+pole.city+pole.area+pole.address,marker);
				
				marker.addEventListener("dragstart", function(type, target){
					type.target.orgin_point=type.target.getPosition();
				});
				marker.addEventListener("dragend", function(type, target, pixel, point){

					var marker=type.target;
					Ext.Msg.confirm("消息","确定是否要修改这个点位的经纬度?",function(btn){
						if(btn=='no'){
							marker.setPosition(marker.orgin_point);
							marker.orgin_point=null;
							//map.setCenter(marker.orgin_point)
						} else {
							Ext.Ajax.request({
								url:Ext.ContextPath+'/map/updatePoleLngLat.do',
								method:'POST',
								params:{
									pole_id:marker.pole_id,
									longitude:marker.getPosition().lng,
									latitude:marker.getPosition().lat
								},
								success:function(response){
									var obj=Ext.decode(response.responseText);
									if(obj.success){
									
									} else {
										alert("更新失败!");
										marker.setPosition(marker.orgin_point);
										marker.orgin_point=null;
									}
								},
								failure:function(){
									alert("更新失败!");
									marker.setPosition(marker.orgin_point);
									marker.orgin_point=null;
								}
							});
							
						}
					})
				});
				markeres[pole.id]=marker;
				//marker.setAnimation(BMAP_ANIMATION_BOUNCE); //跳动的动画
			}
		},
		failure : function() {
				//Ext.getBody().unmask();
		}
	});
}