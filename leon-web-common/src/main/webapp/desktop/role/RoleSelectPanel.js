/**
 * 选择角色的封装类,当选择了某个角色的时候会抛出addRole事件，删除某个角色的时候会抛出removeRole事件
 */
Ext.define('Leon.desktop.role.RoleSelectPanel', {
    extend: 'Ext.panel.Panel',
    title: '选择角色',
    url:'',
	layout:{type:'hbox',align: 'stretch'},       
    initComponent: function () {
    	var me=this;
    	var selectedRoleTree=Ext.create('Ext.tree.Panel',{		
			fields:['id','name'],
			rootVisible: false,
			title:'拥有的角色',
			flex:1,
			store:{
	        		root: {
				        expanded: true,
				        name:'根节点' 
				    },
				    nodeParam :'id',
	        		autoLoad:false,
					proxy:{
						type:'ajax',
						url:me.url
						,reader:{//因为树会自己生成model，这个时候就有这个问题，不加就解析不了，可以通过   动态生成 模型，而不是通过树默认实现，哪应该就没有问题
								type:'json',
								root:'children',
								successProperty:'success',
								totalProperty:'total'	
						}
						,writer:{
							type:'json'
						}
					}
					
			},
			displayField:'name'
			,dockedItems: [{
		        xtype: 'toolbar',
		        dock: 'right',
		        items: [{xtype:'tbspacer',flex:1},{//选择角色
		        	icon:'/icons/arrow_180.png',
		            text: '',
		            handler:function(){
		            	var selectRoleNode=roleTree.getLastSelected();
		            	if(selectRoleNode && selectRoleNode.isLeaf()){
		            		me.fireEvent("addRole",selectedRoleTree,selectRoleNode);

		            	} else {
		            		Ext.Msg.alert('消息',"目录不能移动，请选择角色!");
		            	}
		            }
		        },{
		        	icon:'/icons/arrow.png',
		            text: '',
		            handler:function(){//去掉角色
		            	var selectRoleNode=selectedRoleTree.getSelectionModel( ).getLastSelected();
		            	if(selectRoleNode && selectRoleNode.isLeaf()){
		            		me.fireEvent("removeRole",selectedRoleTree,selectRoleNode);
		            	} else {
		            		Ext.Msg.alert('消息',"目录不能移动，请选择角色!");
		            	}
		            }
		        },{xtype:'tbspacer',flex:1}]
		    }],
		    listeners:{
				"itemdblclick":function(tree, selectRoleNode, item, index){
		            if(selectRoleNode && selectRoleNode.isLeaf()){
		            	me.fireEvent("removeRole",selectedRoleTree,selectRoleNode);
		            } else {
		            	Ext.Msg.alert('消息',"目录不能移动，请选择角色!");
		            }
				}
			}
		});
		var roleTree=Ext.create('Leon.common.ux.BaseTree',{
			url:'/role/query',
			fields:['id','name'],
			rootVisible: false,
			title:'未选择的角色',
			flex:1,
			displayField:'name',
			listeners:{
				"itemdblclick":function(tree, selectRoleNode, item, index){
		            if(selectRoleNode && selectRoleNode.isLeaf()){
		            	me.fireEvent("addRole",selectedRoleTree,selectRoleNode);
		            } else {
		            	Ext.Msg.alert('消息',"目录不能移动，请选择角色!");
		            }
				}
			}
		});
		
		this.addEvents("addRole","removeRole");
		me.items=[selectedRoleTree,roleTree];
		me.callParent();
    },
    /**
     * 刷新已经选择了的角色
     * @param {} params
     */
    reloadSelected:function(params){
    	this.items.getAt(0).getStore().load({params:params});
    }
})