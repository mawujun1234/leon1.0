Ext.define('Ext.ux.ms.SelLeftNav',{
   extend:'Ext.window.Window', 
   alias:'widget.selLeftNav',
   initComponent:function(){ 
   		var navigationStore = Ext.create('Ext.data.TreeStore', {
				fields:['id','text','link','leaf','parentId','memo'],
				proxy : {
						type : 'ajax',
						url : Ext.ContextPath + '/nav/loadNaviT.do',
						reader : {
						type : 'json',
						root : 'root'
					}
				},
				root : {
					text : '请选择常用功能',
					id : 'root',
					expanded : true
				},
				autoLoad:false,
				listeners:{
					beforeload:function( store){
						 store.getProxy().extraParams=Ext.apply(store.getProxy().extraParams,{
						 	showChecked:true
		    			});
					}
				}
			});

			var navigationTree = Ext.create('Ext.tree.Panel', {
				//title : '选择常用功能',
				rootVisible: true,
				store : navigationStore,
				listeners:{
					checkchange:function(node, checked){
						var params={
							navigation_id:node.get("id"),
							checked:checked
						};
						Ext.Ajax.request({
							url:Ext.ContextPath+"/nav/checkchange.do",
							params:params,
							method:'POST',
							success:function(response){
								
							}
							
						});
					}
					
				}
			});
 
        Ext.apply(this,{ 
            height: 560, 
            width: 380, 
            title : '选择常用功能', 
            closeAction: 'hide', 
            closable : true,
            layout: 'fit', 
            modal : true,  
            
            plain : true, 
            resizable: false,
            items:navigationTree 
        });
//        this.getLForm=function(){
//        	return navigationTree;
//        }
        
        this.on('close',function(){
        	top.location.reload();
        });
        this.callParent(arguments); 
    } 
});

var selLeftNavWin;
function showSselLeftNavWin(){
	requires:['Ext.ux.ms.SelLeftNav'] 
	if(!selLeftNavWin){
		selLeftNavWin=Ext.create('Ext.ux.ms.SelLeftNav',{iconCls: 'icon-login'});
	}
	selLeftNavWin.show();
}