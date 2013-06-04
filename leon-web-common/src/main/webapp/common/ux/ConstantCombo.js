Ext.define('Leon.common.ux.ConstantCombo', {
	extend:'Ext.form.ComboBox',
	alias: ['widget.constantcombo'],
	 
	code:null,//Constant的code
	selectFirst:true,//默认选择第一行
	
	queryMode: 'remote',
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
		 me.store=myStore;
		 me.callParent();
	}
});