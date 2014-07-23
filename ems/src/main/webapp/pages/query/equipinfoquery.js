Ext.onReady(function(){
	Ext.tip.QuickTipManager.init();
	var jobtype={REPAIR:1,OUTSOURCE:2};
	
	var ecode_field=Ext.create('Ext.form.field.Text',{
		name:'ecode',
		fieldLabel:'输入设备条形码',
		allowBlank:false,
		vtype:'alphanum',
		minLength:18,
		maxLength:18,
		msgTarget:'side',
		validateOnBlur:false,
		listeners:{
			blur:function(f,e){
				if(!f.getValue()||f.getValue()==''){
					f.clearInvalid();
				}
			},
			change:getEquipInfo
		}
	});
	
	function getEquipInfo(f,n,o,e){
	   if(f.isValid()){
		   var form= equipinfo_form.getForm();
		   
		   form.load({
				params : {ecode:n},//传递参数   
				url : 'getEquipInfo',//请求的url地址   
				method : 'GET',//请求方式   
				success : function(form, action) {//加载成功的处理函数   
        			repStore.load({params:{ecode:n,jobtype:jobtype.REPAIR}});
		    		outStore.load({params:{ecode:n,jobtype:jobtype.OUTSOURCE}});
				},
				failure : function(form, action) {//加载失败的处理函数   
					var ret=Ext.decode(action.response.responseText);
					if(ret.failure){
						Ext.Msg.alert('提示', '没有相关设备信息');
					}
				}
			});
	   }
	}
	
	Ext.define('repairinfo',{
		extend:'Ext.data.Model',
		fields:[
			{name:'ATime',mapping:'ATime'},
			{name:'AUcid',mapping:'AUcid'},
			{name:'DUcid',mapping:'DUcid'},
			{name:'ETime',mapping:'ETime'},
			{name:'FTime',mapping:'FTime'},
			{name:'RType',mapping:'RType'},
			{name:'STime',mapping:'STime'},
			{name:'aucname',mapping:'aucname'},
			{name:'descrip',mapping:'descrip'},
			{name:'ducname',mapping:'ducname'},
			{name:'ecode',mapping:'ecode'},
			{name:'eid',mapping:'eid'},
			{name:'iidO',mapping:'iidO'},
			{name:'jid',mapping:'jid'},
			{name:'jobList',mapping:'jobList'},
			{name:'lastjid',mapping:'lastjid'},
			{name:'memo',mapping:'memo'},
			{name:'outsource',mapping:'outsource'},
			{name:'price',mapping:'price'},
			{name:'reason',mapping:'reason'},
			{name:'rid',mapping:'rid'},
			{name:'status',mapping:'status'}]
		});
	
	var repStore = Ext.create('Ext.data.Store', {
	        model:'repairinfo',
	        proxy: {
	            type: 'ajax',
	            url: 'getRepairList.do',
	            reader: { root: 'list' }
	        },
	        listeners:{
	        	load:function(s,r,success,o,e){
	        		if(success){
	        			repairGrid.setTitle('设备历史维修信息('+r.length+')');
	        		}
	        	}
	        }
	    });
	var repairGrid=Ext.create('Ext.grid.Panel',{
        title:'设备历史维修信息',
        store:repStore,
        columns: [
	   		 {header:'任务流水号',dataIndex:'rid',width:150},
	   		 {header:'任务指派时间',dataIndex:'ATime'},
	   		 {header:'故障描述',dataIndex:'descrip'},
	   		 {header:'故障原因',dataIndex:'reason'},
	   		 {header:'费用',dataIndex:'price'},
	   		 {header:'状态',dataIndex:'status',renderer:statusRender},
	   		 {header:'备注',dataIndex:'memo',flex:1}
			]
	});

	var outStore = Ext.create('Ext.data.Store', {
	        model:'repairinfo',
	        proxy: {
	            type: 'ajax',
	            url: 'getRepairList.do',
	            reader: { root: 'list' }
	        },
	        listeners:{
	        	load:function(s,r,success,o,e){
	        		if(success){
	        			outSourceGrid.setTitle('设备历史外修信息('+r.length+')');
	        		}
	        	}
	        }
	    });
	
	var outSourceGrid=Ext.create('Ext.grid.Panel',{
        title:'设备历史外修信息',
        store:outStore,
        features: [{
            ftype: 'summary'
        }],
        columns: [
     	   		 {header:'任务流水号',dataIndex:'rid',width:150},
    	   		 {header:'任务指派时间',dataIndex:'ATime'},
    	   		 {header:'故障描述',dataIndex:'descrip'},
    	   		 {header:'故障原因',dataIndex:'reason'},
    	   		 {header:'费用',dataIndex:'price'},
    	   		 {header:'状态',dataIndex:'status',renderer:statusRender},
    	   		 {header:'备注',dataIndex:'memo',flex:1}
			]
	});
	
	var estatus_fieild=Ext.create('Ext.D.list.ComboBox',{labelWidth:75,width:220,xtype:'listcombox',url:'estatusList',fieldLabel:'设备状态',loadAuto:true,name:'estatus'});
	
	var equipinfo_form=Ext.create('Ext.form.Panel',{
   			region:'west',
   			layout:'fit',
   			split:true,
   			width:255,
   			border:false,
   			items:[{xtype:'fieldset',title:'设备基本信息',defaults:{xtype:'textfield',readOnly:true,labelWidth:75,width:220},
   				    margin:'0px',
						items:[{fieldLabel:'条形码',name:'ecode'},
						       estatus_fieild,
 					           {fieldLabel:'设备类型',name:'typename'},
 					     	   {fieldLabel:'型号',name:'style'},
 					           {fieldLabel:'厂家',name:'sname'},
					           {fieldLabel:'批次',name:'batchid'},
					           {xtype:'fieldcontainer',fieldLabel:'单价', margin:'0px',layout:'hbox',defaults:{hideLabel: true},items:[{xtype:'textfield',name:'price',width:120},{xtype:'displayfield',value:'元'}]},
					     	   {fieldLabel:'使用单位',name:'uname'}]
					}]
       		});
	
	Ext.create('Ext.container.Viewport',{
        layout: {
            type:'vbox',
            padding:'5',
            align:'stretch'
        },
        defaults:{border:false},
        items:[{xtype:'fieldcontainer',layout:'hbox',margin:'2px 0 0 0',items:[ecode_field,{margins:'0 0 0 5px',xtype:'button',text:'清除',handler:function(btn){ecode_field.reset();ecode_field.focus();}}]},
               {html:'<HR style="FILTER: alpha(opacity=100,finishopacity=0,style=3)" width="100%" color=#987cb9 SIZE=3>'},
               {flex:1,layout:'border',bodyStyle:'background-color:#FFFFFF',//设置面板体的背景色
           		defaults:{border:false},
           		items:[equipinfo_form,{
	        		   region:'center',
	        		   xtype:'tabpanel',
	        		   activeTab:0,
	        		   plain:true,
	        		   items:[repairGrid,outSourceGrid]
	        		}]
           	   }],
    	listeners:{
    		activate:function(p,e){
    			ecode="";
    			ecode_field.reset();
    			ecode_field.focus();
    		}
    	}
	});
	
	function statusRender(v){
		var diplayTxt="";
		estatus_fieild.getStore( ).each(function(record) {
	        var value = record.get('value');
	        if (v === value) {
	            diplayTxt = record.get('text');
	            return false;
	        }
	    });
		return diplayTxt;
	}
});