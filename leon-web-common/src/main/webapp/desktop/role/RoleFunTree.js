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
					{name:'permissionEnum',type:'string'},
					{name:'roleSources',type:'string'},
					{name:'fromParent',type:'boolean',defaultValue:false}
        		],
//        		model:'Leon.desktop.role.RoleFunAssociation',
				proxy:{
					type:'ajax',
					url:Ext.ContextPath+'/fun/query',
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
			funs[i].set('permissionEnum',null);
			funs[i].set('roleSources',null);
			funs[i].set('fromParent',false);
			for(var j=0;j<checkedFunes.length;j++){
				if(funs[i].getId()==checkedFunes[j].funId){
					//funs[i].roleAssociationId=checkedFunes[j].id;
					funs[i].roleAssociation=checkedFunes[j];
					funs[i].set('fromParent',checkedFunes[j].fromParent);
					funs[i].set('checked',true);
					funs[i].set('permissionEnum',checkedFunes[j].permissionEnum);
					funs[i].set('roleSources',checkedFunes[j].roleSources);
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