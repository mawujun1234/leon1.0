Ext.defineModel("Leon.desktop.parameter.Parameter",{
	extend:"Ext.data.Model",
	fields:[
		{name:'id',type:'auto'},
		{name:'name',type:'string'},
		{name:'desc',type:'string'},
		{name:'showModel',type:'string'},
		{name:'showModelName',type:'string'},
		{name:'valueEnum',type:'string'},
		{name:'valueEnumName',type:'string'},
		{name:'defaultValue',type:'string'},
		{name:'content',type:'string'},
		{name:'targets',type:'string'},
		{name:'useCount',type:'int'}//引用的次数
	]
	
});
