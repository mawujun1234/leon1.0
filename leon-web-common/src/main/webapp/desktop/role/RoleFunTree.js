Ext.define('Leon.desktop.role.RoleFunTree',{
	extend:'Ext.tree.Panel',
//	requires: [
//	    //'Leon.desktop.fun.Fun'
//	],
	rootVisible: false,
	stripeRows: true,
	columnLines:true,
//	viewConfig: {
//    	//lockable:true,
//        stripeRows: false
//    },
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
        		fields:[
	        		{name:'id',type:'string'},
					{name:'text',type:'string'},
					{name:'funEnum',type:'string'},
					{name:'permissionType',type:'string'}
        		],
//        		model:'Leon.desktop.role.RoleFunAssociation',
				proxy:{
					type:'ajax',
					url:'/fun/queryAll',
					reader:{//因为树会自己生成model，这个时候就有这个问题，不加就解析不了，可以通过   动态生成 模型，而不是通过树默认实现，哪应该就没有问题
							type:'json',
							root:'children',
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
			xtype:'treecolumn',dataIndex:'text',text:'名称',width: 200,
			renderer: function(val,metaData,record ,rowIndex ,colIndex ,store ){
//                if(record.get('permissionType')=='publicP'){
//                		metaData.style = 'background-color: #76EE00;' 
//                } else if(record.get('permissionType')=='privateP'){
//                		metaData.style = 'background-color: #FFFF00;'
//                }
                
                return val; 
            }
		},{
			dataIndex:'permissionType',text:'权限属性',
			editor:{
				xtype:'combo',
				store:comboStore ,
				editable:false,
				queryMode: 'local',
				displayField: 'name',
				valueField: 'id',
				listeners:{
					"select":function(combo,records){
						var node=me.getSelectionModel().getLastSelected();
						var params=node.roleAssociation;
						params.permissionType=records[0].getId();
						Ext.Ajax.request({
				    		url:'/roleFunAssociation/update',
				    		method:'POST',
				    		params :params,
				    		success:function(response){
				    			var obj=Ext.encode(response.responseText);
				    			//node.set('permissionType',params.permissionType);
				    			
				    		}   		
				    	});
					}
				}
			},
			renderer: function(val,metaData,record ,rowIndex ,colIndex ,store ){
                var index = comboStore.findExact('id',val); 
                if (index != -1){
                	
                	if(record.get('permissionType')=='publicP'){
                		metaData.style = 'background-color: #76EE00;' 
                	} else if(record.get('permissionType')=='privateP'){
                		metaData.style = 'background-color: #FFFF00;'
                	}
                	var data=comboStore.getAt(index).data;
                    return data.name; 
                }
            }
		},{
			dataIndex:'id',text:'权限来源',
			renderer: function(val,metaData,record ,rowIndex ,colIndex ,store ){
//                if(record.get('permissionType')=='publicP'){
//                		metaData.style = 'background-color: #76EE00;' 
//                } else if(record.get('permissionType')=='privateP'){
//                		metaData.style = 'background-color: #FFFF00;'
//                }
                
                return val; 
            }
		}];
		
		me.viewConfig= {
		    getRowClass: function(record, rowIndex, rowParams, store){
//		    	if(record.get('permissionType')=='publicP'){
//                		//metaData.style = 'background-color: #76EE00;' 
//		    		return "greenColor";
//                } else if(record.get('permissionType')=='privateP'){
//                	return "greenColor";
//                }
		        //return record.get("valid") ? "row-valid" : "row-error";
		        return "greenColor";
		    }
		}
		
		me.plugins=[{
			ptype:'cellediting',
			clicksToEdit :1,
			listeners:{
				beforeedit:function(e){return true;
					//if(e.record.isRoot()){ return false;}
				}
			}
		}];
		me.callParent();
	},
	/**
	 * 选择功能节点
	 * @param {} checkedFunes
	 */
	checkingFunes:function(checkedFunes){
		var funs=this.funs;
		if(!funs){
			funs=[];
			this.findAllFunes(funs,this.getRootNode());
			this.funs=funs;
		}
		
		//console.log(checkedFunes);
		for(var i=0;i<funs.length;i++){
			//console.log(funs.length);
			funs[i].set('checked',false);
			for(var j=0;j<checkedFunes.length;j++){
				if(funs[i].getId()==checkedFunes[j].funId){
					//funs[i].roleAssociationId=checkedFunes[j].id;
					funs[i].roleAssociation=checkedFunes[j];
					funs[i].set('checked',true);
					funs[i].set('permissionType',checkedFunes[j].permissionType);
				}
			}
		}
	},
	findAllFunes:function(funs,node){	
		var me=this;
		if(node.hasChildNodes( )){
			var children=node.childNodes;
			for(var i=0;i<children.length;i++){
				if(children[i].get('funEnum')=='fun'){
					funs.push(children[i]);
				}
				if(children[i].hasChildNodes( )){
					me.findAllFunes(funs,children[i]);
				}
			}
		}
	}	
});