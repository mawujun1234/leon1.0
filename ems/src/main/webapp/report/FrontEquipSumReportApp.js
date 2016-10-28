
Ext.onReady(function(){

	     	var customer_2 = Ext.create('Ext.form.field.ComboBox', {
						fieldLabel : '区',
						labelAlign : 'right',
						labelWidth : 20,
						width : 150,
						// xtype:'combobox',
						// afterLabelTextTpl: Ext.required,
						name : 'customer_2',
						displayField : 'name',
						valueField : 'id',
						queryParam : 'name',
						queryMode : 'remote',
						store : Ext.create('Ext.data.Store', {
									fields : ['id', 'name'],
									proxy : {
										type : 'ajax',
										// extraParams:{type:1,edit:true},
										url : Ext.ContextPath
												+ "/customer/query4combo.do",
										reader : {
											type : 'json',
											rootProperty : 'root'
										}
									}
								}),
						listeners : {
							change : function(field, newValue, oldValue) {
								customer_0or1.clearValue();
								if (newValue) {
									customer_0or1.getStore().getProxy().extraParams = {
										parent_id : newValue
									};
									customer_0or1.getStore().reload();
								}

							}
						}
					});
			//actions0.push(customer_2);
			// 具体的客户
			var customer_0or1 = Ext.create('Ext.form.field.ComboBox', {
						fieldLabel : '客户',
						labelAlign : 'right',
						labelWidth : 40,
						width : 280,
						// xtype:'combobox',
						// afterLabelTextTpl: Ext.required,
						name : 'customer_0or1',
						displayField : 'name',
						valueField : 'id',
						queryParam : 'name',
						queryMode : 'remote',
						triggerAction : 'query',
						minChars : -1,
						//trigger1Cls : Ext.baseCSSPrefix + 'form-clear-trigger',
						//trigger2Cls : Ext.baseCSSPrefix + 'form-arrow-trigger',// 'form-search-trigger',
//						
//						onTrigger1Click : function() {
//							var me = this;
//							me.setValue('');
//						},
						triggers:{
							foo: {
					            cls: Ext.baseCSSPrefix + 'form-clear-trigger',
					            weight: -2, // negative to place before default triggers
					            handler: function() {
					                //console.log('foo trigger clicked');
					            	var me = this;
									me.setValue('');
					            }
					        }
						},
						//allowBlank : false,
						store : Ext.create('Ext.data.Store', {
									fields : ['id', 'name'],
									proxy : {
										type : 'ajax',
										// extraParams:{type:1,edit:true},
										method : 'POST',
										url : Ext.ContextPath
												+ "/customer/query4combo.do",
										reader : {
											type : 'json',
											rootProperty : 'root'
										}
									},
									listeners : {
										beforeload : function(store) {
											if (!customer_2.getValue()) {
												delete customer_0or1.lastQuery;
												alert("请先选择'区'");
												return false;
											}
										}

									}

								})
					});

	
	var tbar1=Ext.create('Ext.toolbar.Toolbar',{
		items:[customer_2,customer_0or1,{
			text:'导出',
			icon:'../icons/page_excel.png',
			handler:function(){
				var params=getParams();
				if(!params){
					return false;
				}
				params.isMaching=false;
				var pp=Ext.Object.toQueryString(params);
				window.open(Ext.ContextPath+"/report/frontequip/exportFrontEquipSumReport.do?"+pp, "_blank");
			}
		},{
			text:'导出-净值',
			icon:'../icons/page_excel.png',
			handler:function(){
				var params=getParams();
				if(!params){
					return false;
				}
				params.isMaching=false;
				var pp=Ext.Object.toQueryString(params);
				window.open(Ext.ContextPath+"/report/frontequip/exportFrontEquipSumReport_assetclean.do?"+pp, "_blank");
			}
		}]
	})

	
	function getParams(){
		var params={
			customer_2:customer_2.getValue(),
			customer_2_name:customer_2.getRawValue(),
			customer_0or1:customer_0or1.getValue()
		}
		if(!params.customer_2){
			Ext.Msg.alert("提醒","请先选择一个区!");
			return false;
		}

		return params;
	}
	
	var panel=Ext.create('Ext.panel.Panel',{
		tbar:tbar1
	});
	

	var viewPort=Ext.create('Ext.container.Viewport',{
		layout:'fit',
		items:[panel]
	});

});