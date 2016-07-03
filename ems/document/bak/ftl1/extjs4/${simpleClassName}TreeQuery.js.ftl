<#assign simpleClassNameFirstLower = simpleClassName?uncap_first> 
<#-- //所在模块-->
<#assign module = basepackage?substring(basepackage?last_index_of(".")+1)> 
/**
 * 主要是用来查询的
 */
Ext.define('Ems.${module}.${simpleClassName}TreeQuery', {
    extend: 'Ext.tree.Panel',
    requires:['Ems.${module}.${simpleClassName}'],
    initComponent: function () {
		var me = this;

		//默认在model中就定义了查询的url
        me.store = Ext.create('Ext.data.TreeStore', {
	       	autoLoad:true,
	       	nodeParam :'id',//传递到后台的数据，默认是node
	       	model:'Ems.${module}.${simpleClassName}',
			root: {
			    expanded: true,
			    text:"根节点" 
			}
		});
		/* 这个是用于只有url的时候
		var cofig={
        	root: {
			    //id:'root',
			    expanded: true,
			    text:"根节点" 
			},
			//fields[],当有需要的时候自己定义,除了本来自己有的id,text
			nodeParam :'id',
        	autoLoad:true,
			proxy:{
				type:'ajax',
				url:Ext.ContextPath+"/${simpleClassNameFirstLower}/query",
				,reader:{//因为树会自己生成model，这个时候就有这个问题，不加就解析不了，可以通过   动态生成 模型，而不是通过树默认实现，哪应该就没有问题
					type:'json',
					root:"root",
					successProperty:'success',
					totalProperty:'total'	
				}
			}	
		};
        me.store=Ext.create('Ext.data.TreeStore',cofig);
        */
       
		me.callParent(arguments);
    },

    getLastSelected:function(){
    	return this.getSelectionModel( ).getLastSelected( );
    }
    
});
