

//Ext.define("Leon.desktop.fun.Fun",{
Ext.defineModel("Leon.desktop.fun.Fun",{
	extend:"Ext.data.Model",
	fields:[
		{name:'id',type:'auto'},
		//{name:'elementId',type:'string'},
		{name:'isEnable',type:'boolean',defaltValue:true},
		//{name:'leaf',type:'boolean',defaltValue:false},
		{name:'text',type:'string'},
		{name:'url',type:'string'},
		//{name:'reportCode',type:'string'},
		{name:'helpContent',type:'string'},
		//{name:'funEnum',type:'string'},
		//{name:'iconCls',type:'string'},
		{name:'bussinessType',type:'constant'}//Ext.data.Types.Constant
		//{name:'parent_id',type:'string'}
	]
//	associations:[
//		{type:'belongsTo',model:'Leon.desktop.fun.Fun',associatedName:'Parent'}
//	]
});
