Ext.define('Leon.desktop.role.RoleFunTree',{
	extend:'Ext.tree.Panel',
//	requires: [
//	    //'Leon.desktop.fun.Fun'
//	],
	rootVisible: true,
	initComponent: function () {
		var me=this;
		var cofig={
        		root: {
			    	//id:'root',
			        expanded: true,
			        text:'根节点' 
			    },
			    nodeParam :'id',
        		autoLoad:true,
        		//fields:
        		model:'Leon.desktop.role.RoleFunAssociation',
				proxy:{
					type:'ajax',
					url:'/fun/queryAll',
					reader:{//因为树会自己生成model，这个时候就有这个问题，不加就解析不了，可以通过   动态生成 模型，而不是通过树默认实现，哪应该就没有问题
							type:'json',
							root:'root',
							successProperty:'success',
							totalProperty:'total'	
					}
					,writer:{
						type:'json'
					}
				}
				
		};
		me.store=Ext.create('Ext.data.TreeStore',cofig);
		
		
		
		var comboStore= Ext.create('Ext.data.Store', {
			fields: ['id', 'name'],
			data : [
				{"id":"publicP", "name":"公有"},
				{"id":"privateP", "name":"私有"}
			]
		});
		
		me.columns=[{
			xtype:'treecolumn',dataIndex:'text',text:'名称'
		},{
			dataIndex:'id',text:'权限',editor:{
				xtype:'combo',
				store:comboStore ,
			    queryMode: 'local',
			    displayField: 'name',
			    valueField: 'id'
			},
			renderer: function(val,metaData,record ,rowIndex ,colIndex ,store ){
                var index = comboStore.findExact('id',val); 
                if (index != -1){
                    rs = comboStore.getAt(index).data; 
                    console.dir(rs);
                    return rs.name; 
                }
            }
		}];
		
		me.plugins=[{
			ptype:'cellediting',
			clicksToEdit :1,
			listeners:{
				beforeedit:function(e){
					//if(e.record.isRoot()){ return false;}
				}
			}
		}];
		me.callParent();
	}
		
});