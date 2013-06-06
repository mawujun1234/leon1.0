Ext.define('Leon.common.ux.ConstantCombo', {
	extend:'Ext.form.ComboBox',
	alias: ['widget.constantcombo'],
	 
	code:null,//Constant的code,必填
	selectFirst:true,//默认选择第一行,还有一个问题就是，如果store已经加载了，在点击下拉的时候，不再去加载，封装掉
	
	queryMode: 'local',
    displayField: 'text',
    valueField: 'code',
    
	initComponent: function () {
		var me=this;
		var myStore = Ext.create('Ext.data.Store', {
		     //model: 'User',
			fields: ['id','code', 'text'],
		     proxy: {
		         type: 'ajax',
		         actionMethods: { read: 'POST' },
		         url: '/constantItem/queryByCode',
		         extraParams :{code:me.code},
		         reader:{
						type:'json',
						root:'root',
						successProperty:'success',
						totalProperty:'total'			
				}
		     },
		     autoLoad: true
		 });
		 if(me.selectFirst){
		 	myStore.on('load',function(){
		 		//alert(myStore.getCount( ));
		 		if(myStore.getCount( ) >0){
		 			var r=myStore.getAt(0);
		 			me.select( r );
		 		}
		 		
		 	});
		 }
		 me.store=myStore;
		 me.callParent();
	}
});