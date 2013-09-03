Ext.define('Leon.desktop.generator.DirSelectPanel',{
	extend:'Ext.tree.Panel',
	rootVisible:false,
	initComponent: function () {
       var me = this;
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
					{name:'isFile',type:'boolean'}
        		],
//        		model:'Leon.desktop.role.RoleFunAssociation',
				proxy:{
					method:'POST',
					type:'ajax',
					url:me.url,
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
        me.tbar=[{
       		text:'新建目录',
       		handler:function(){
       			var parent=me.getSelectionModel( ).getLastSelected( );
       			if(!parent){
       				//Ext.Msg.alert("消息","请先父目录");
                    //return;
       				parent=me.getRootNode();
       			}
       			
       			Ext.Msg.prompt('请输入路径', '例：newName或<br>com.mawujun.newName', function(btn, text){
					if (btn == 'ok'){
						Ext.Ajax.request({
		       				url:'/app/generator/createDirectory',
		       				method:'POST',
		       				params:{parentId:parent.getId(),text:text},
		       				success:function(response){
		       					//var obj=Ext.decode(response.responseText);
		       					me.getStore().load({node:parent});
		       				}
		       			
		       			});
					}
				});

       		}
       },{
       	text:'刷新',
       	handler:function(){
	    	var parent=me.getSelectionModel( ).getLastSelected( )||me.getRootNode();
			if(parent){
			    me.getStore().reload({node:parent});
			} else {
			    me.getStore().reload();	
			}     
       	}
       }]
       me.callParent();
	}
});