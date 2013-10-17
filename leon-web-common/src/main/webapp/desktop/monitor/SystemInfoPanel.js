Ext.define('Leon.desktop.monitor.SystemInfoPanel',{
	extend:'Ext.form.Panel',
	title:'概要',
	border:false, 
	//frame:true,
	initComponent:function(){
		var me=this;
		me.items=[{//第一行  
            layout:"column",  
            border:false, 
            items:[{  
                columnWidth:.3,//第一列  
                layout:"form",  
                border:false, 
                items:[{  
                    xtype:"textfield",  
                    readOnly:true,
                    fieldLabel: '内核',  
                    name: 'arch'
                }]  
            },{  
                columnWidth:.3,//第二列  
                layout:"form",  
                border:false, 
                items:[{  
                    xtype:"textfield",  
                    readOnly:true,
                    fieldLabel: 'dataModel',  
                    name: 'dataModel'
                }]  
            },{  
                columnWidth:.3,//第三列  
                layout:"form",  
                border:false, 
                items:[{  
                    xtype:"textfield",  
                    readOnly:true,
                    fieldLabel: 'cpuEndian',  
                    name: 'cpuEndian'
                }]  
            }]},//第一行结束  
            {
            layout:"column",  
            border:false, 
            items:[{  
                columnWidth:.3,//第一列  
                layout:"form",  
                border:false, 
                items:[{  
                    xtype:"textfield",  
                    readOnly:true,
                    fieldLabel: '操作系统描述',  
                    name: 'description'
                }]  
            },{  
                columnWidth:.3,//第二列  
                layout:"form",  
                border:false, 
                items:[{  
                    xtype:"textfield",  
                    readOnly:true,
                    fieldLabel: '操作系统名称',  
                    name: 'name'
                }]  
            },{  
                columnWidth:.3,//第三列  
                layout:"form",  
                border:false, 
                items:[{  
                    xtype:"textfield",  
                    readOnly:true,
                    fieldLabel: '操作系统版本',  
                    name: 'version'
                }]  
            }]},
             {
            layout:"column",  
            border:false, 
            items:[{  
                columnWidth:.3,//第一列  
                layout:"form",  
                border:false, 
                items:[{  
                    xtype:"textfield",  
                    readOnly:true,
                    fieldLabel: '操作系统补丁包',  
                    name: 'patchlevel'
                }]  
            },{  
                columnWidth:.3,//第二列  
                layout:"form",  
                border:false, 
                items:[{  
                    xtype:"textfield",  
                    readOnly:true,
                    fieldLabel: '操作系统厂商',  
                    name: 'vendor'
                }]  
            },{  
                columnWidth:.3,//第三列  
                layout:"form",  
                border:false, 
                items:[{  
                    xtype:"textfield",  
                    readOnly:true,
                    fieldLabel: '操作系统厂商版本',  
                    name: 'vendorVersion'
                }]  
            }]},
            {
            layout:"column",  
            border:false, 
            items:[{  
                columnWidth:.3,//第一列  
                layout:"form",  
                border:false, 
                items:[{  
                    xtype:"textfield",  
                    readOnly:true,
                    fieldLabel: 'java_vm_version',  
                    name: 'java_vm_version'
                }]  
            },{  
                columnWidth:.3,//第二列  
                layout:"form",  
                border:false, 
                items:[{  
                    xtype:"textfield",  
                    readOnly:true,
                    fieldLabel: 'java_vm_vendor',  
                    name: 'java_vm_vendor'
                }]  
            },{  
                columnWidth:.3,//第三列  
                layout:"form",  
                border:false, 
                items:[{  
                    xtype:"textfield",  
                    readOnly:true,
                    fieldLabel: 'java_home',  
                    name: 'java_home'
                }]  
            }]}
        ]
		me.callParent();
	}
});