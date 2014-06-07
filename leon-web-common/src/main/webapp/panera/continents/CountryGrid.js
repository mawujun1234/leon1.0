Ext.define('Leon.panera.continents.CountryGrid',{
	extend:'Ext.grid.Panel',
	requires: [
	     'Leon.panera.continents.Country',
	     'Leon.panera.continents.CountryForm'
	],
	columnLines :true,
	stripeRows:true,
	viewConfig:{
		stripeRows:true,
		listeners:{
			refresh:function(){
				//this.select(0);
			}
		}
	},
	//xtype: 'cell-editing',
	initComponent: function () {
      var me = this;
      me.columns=[
		//{dataIndex:'id',text:'id'},
		
		{dataIndex:'continent_name',text:'五大洲'},
		{dataIndex:'continent',text:'五大洲_英文'},
		{dataIndex:'name',text:'国家名称'},
		{dataIndex:'name_en',text:'国家名称_英文'}
      ];
      
	  me.store=Ext.create('Ext.data.Store',{
			autoSync:false,
			pageSize:50,
			model: 'Leon.panera.continents.Country',
			autoLoad:true
	  });
	  
	  

	  var continents={
			width:500,
			labelWidth:60,
			labelAlign:'right',
            xtype: 'radiogroup',
            fieldLabel: '五大洲',
            //cls: 'x-check-group-alt',
            items: [
                {boxLabel: '欧洲', name: 'continent', inputValue:'Europe'},
                {boxLabel: '亚洲', name: 'continent', inputValue: 'Asia'},
                {boxLabel: '大洋洲', name: 'continent', inputValue: 'Oceania'},
                {boxLabel: '非洲', name: 'continent', inputValue: 'Africa'},
                {boxLabel: '亚洲', name: 'continent', inputValue: 'America'}
            ],
            listeners:{
            	'change':function(radiogroup, newValue, oldValue){
            		var grid=radiogroup.up("grid");
					grid.getStore().reload({params:{continent:newValue.continent}});
            	}
            }
		};
		
		var addContryButton={
            text: '添加国家',
            iconCls: 'form-add-button',
            //scope: this,
            
            handler: function(btn){
            	
            	var form=new Leon.panera.continents.CountryForm();
            	
            	var model=Ext.createModel('Leon.panera.continents.Country',{ 
            		
				});
				model.phantom =true;
				form.getForm().loadRecord(model);
				
            	var win=new Ext.window.Window({
            		layout:'fit',
            		modal:true,
            		items:[form]
            	});
            	form.win=win;
            	form.grid=btn.up("grid");
            	win.show();
            	
//                var rec = new Leon.panera.continents.Country({
//		            name_en: '',
//		            name: ''
//		        });
//		        me.actionButton="create";
//		        me.getStore().insert(0, rec);
//		        me.cellEditing.startEditByPosition({
//		            row: 0, 
//		            column: 0
//		        });
            }
        };
        
        var updateContryButton={
            text: '更新',
            iconCls: 'form-update-button',
            //scope: this,
            
            handler: function(btn){
            	var grid=btn.up("grid");
            	var record=grid.getSelectionModel( ).getLastSelected( );//.getLastSelected( );
            	 
            	var form=new Leon.panera.continents.CountryForm();
            	form.loadRecord(record);
            	
            	var win=new Ext.window.Window({
            		layout:'fit',
            		modal:true,
            		items:[form]
            	});
            	form.win=win;
            	win.show();
            }
        };
        
        var removeContryButton={
            text: '删除',
            iconCls: 'form-delete-button',
            //scope: this,
            handler: function(btn){
            	var grid=btn.up("grid");
                var record=grid.getSelectionModel( ).getLastSelected( );//.getLastSelected( );
				grid.getStore().remove( record );
				
				//me.actionButton="update";
				grid.getStore().sync({
					failure:function(){
						grid.getStore().reload();
					}
				});
            }
        };
        
		me.tbar={
			xtype: 'container',
		    layout: 'anchor',
		    defaults: {anchor: '0'},
		    defaultType: 'toolbar',
			items:[{xtype:'toolbar',border:false,items:[addContryButton,updateContryButton,removeContryButton]},{xtyle:'toolbar',items:[continents]}]
		};
   
//	  me.tbar=	[{
//			text: '刷新',
//			itemId:'reload',
//			disabled:me.disabledAction,
//			handler: function(btn){
//				var grid=btn.up("grid");
//				grid.getStore().reload();
//			},
//			iconCls: 'form-reload-button'
//		}]
       
      me.callParent();
	}
});
