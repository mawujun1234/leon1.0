Ext.define('Leon.desktop.role.RoleFunTree',{
	extend:'Ext.tree.Panel',
//	requires: [
//	    //'Leon.desktop.fun.Fun'
//	],
	rootVisible: false,
//	columnLines:true,
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
					{name:'funEnum',type:'string'}
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
			xtype:'treecolumn',dataIndex:'text',text:'名称',width: 200
		},{
			dataIndex:'id',text:'权限属性',
			editor:{
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
		},{
			dataIndex:'id',text:'权限来源'
		}];
		
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
		//使用treestore的data，或者treedate来获取所有节点，然后获取到里面的所有功能。
		//http://blog.csdn.net/zdb330906531/article/details/6668826
		hjhj
		var funs=this.funs;
		if(!funs){
			funs=[];
			this.findAllFunes(funs,this.getRootNode());
			this.funs=funs;
		}
		
		//console.log(checkedFunes);
		for(var i=0;i<funs.length;i++){
			//console.log(funs.length);
			for(var j=0;j<checkedFunes.length;j++){
				if(funs[i].getId()==checkedFunes[j].funId){
					funs[i].roleAssociationId=checkedFunes[j].id;
					funs[i].set('checked',true);
					//设置UI状态为未选中状态  
				     // funs[i].getUI().toggleCheck(false);  
				      //设置节点属性为未选中状态  
				      //funs[i].attributes.checked=false;  
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