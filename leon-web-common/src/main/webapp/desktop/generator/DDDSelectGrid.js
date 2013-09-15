Ext.define('Leon.desktop.generator.DDDSelectGrid',{
	extend:'Ext.grid.Panel',
	requires: [
	     //'Leon.desktop.menu.Menu'
	],
	columnLines :true,
	stripeRows:true,
	viewConfig:{
		stripeRows:true,
		listeners:{
			refresh:function(){
				this.select(0);
			}
		}
	},
	initComponent: function () {
       var me = this;
       me.columns=[
	        //{ text: 'id',  dataIndex: 'id' },
	        { text: '类名', dataIndex: 'text', width:150 },
	        { text: '全限定类名', dataIndex: 'className', flex: 1 }
       ];
       me.store=Ext.create('Ext.data.Store',{
       		//autoSync:true,
       		//model: 'Leon.desktop.menu.Menu',
       		fields:['text','className'],
       		autoLoad:true,
       		proxy:{
       			type:'ajax',
				url:Ext.ContextPath+'/generator/listAllClass',
				reader:{
					type:'json',
					root:'root',
					successProperty:'success',
					totalProperty:'total'	
				}
				,writer:{
					type:'json'
				}
       		}
       });
       me.tbar=[{
       	xtype:'textfield'
       },{
       	xtype:'button',
       	text:'查询',
       	handler:function(btn){
       		var text=btn.previousSibling().getValue();
       		if(text){//.z这个过滤还没有做好
       			var reg=new RegExp("user", "gi");
       			me.store.filter("text", reg);
       		} else {
       			me.store.clearFilter();
       		}
       	}
       }]
       me.callParent();
	}
});