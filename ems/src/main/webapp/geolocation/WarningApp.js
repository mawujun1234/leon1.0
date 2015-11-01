
Ext.onReady(function(){
//	 var date_start=Ext.create('Ext.form.field.Date',{
//	  	fieldLabel: '上班时间',
//	  	labelWidth:60,
//	  	width:160,
//	  	format:'Y-m-d'
//        //name: 'date_start',
//        //value:  Ext.Date.add(new Date(), Ext.Date.DAY, -7)
//	  });
//	  var date_end=Ext.create('Ext.form.field.Date',{
//	  	fieldLabel: '到',
//	  	format:'Y-m-d',
//	  	labelWidth:15,
//	  	width:115,
//        //name: 'date_end',
//        value: new Date()
//	  });
	
	
	// Create the combo box, attached to the states data store
	var status_combobox=Ext.create('Ext.form.ComboBox', {
	    fieldLabel: '状态',
	    labelWidth:60,
	    store: Ext.create('Ext.data.Store', {
		    fields: ['id', 'name'],
		    data : [
		        {"id":"unuploadgps", "name":"未上传gps"},
		        {"id":"logined", "name":"所有已登录用户"}
		    ]
		}),
	    queryMode: 'local',
	    displayField: 'name',
	    valueField: 'id',
	    value:"unuploadgps"
	});
	var button=Ext.create('Ext.button.Button',{
		text:'查询/刷新',
		handler:function(){
			store.getProxy().extraParams={
				status:status_combobox.getValue()
			}
			store.reload();
		}
	});
	var store=Ext.create('Ext.data.Store', {
	    fields:['loginName', 'lastedUploadTime', 'loginTime','isUploadGps'],
	    autoLoad:true,
	    proxy: {
	        type: 'ajax',
	        url:Ext.ContextPath+"/geolocation/unuploadGpsWorkunit.do",
	        reader: {
	            type: 'json',
	            root: 'root'
	        }
	    }
	});
	var grid=Ext.create('Ext.grid.Panel',{
		store:store,
		tbar:[status_combobox,button],
		columns: [
	        { text: '用户名',  dataIndex: 'loginName' },
	        { text: '登录时间', dataIndex: 'loginTime',width:130},
	        { text: 'gps是否已上传', dataIndex: 'isUploadGps',renderer:function(value){
	        	if(value){
	        		return "是";
	        	}
	        	return "<span style='color:red;'>否</span>";
	        }},
	        { text: 'gps最后上传时间', dataIndex: 'lastedUploadTime',width:130 }
	    ]
	});
	
	
	var viewPort=Ext.create('Ext.container.Viewport',{
		layout:'fit',
		items:[grid]
	});

});