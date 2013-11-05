Ext.define('Leon.desktop.monitor.SystemInfoPanel',{
	extend:'Ext.form.Panel',
	title:'概要',
	border:false, 
	frame:true,
	initComponent:function(){
		var me=this;
		me.items=[{//第一行  
			xtype: 'fieldset',
			title: '系统信息',
			collapsible: true,
            layout:"column",  
            frame:true,
            defaults:{frame:true,layout: 'form',border:false,columnWidth:.3},
            items:[{  
            	 style: {
					borderStyle: 'none'
				}, 
                items:[{  border:false ,
                    xtype:"textfield",  
                    readOnly:true,
                    fieldLabel: '内核',  
                    name: 'arch'
                }] 
            },{  
				style: {
					borderStyle: 'none'
				}, 
                items:[{ 
                    xtype:"textfield",  
                    readOnly:true,
                    fieldLabel: 'dataModel',  
                    name: 'dataModel'
                }]  
            },{  
            	style: {
					borderStyle: 'none'
				}, 
                items:[{  
                    xtype:"textfield",  
                    readOnly:true,
                    fieldLabel: 'cpuEndian',  
                    name: 'cpuEndian'
                }]  
            } ,{  
            	style: {
					borderStyle: 'none'
				}, 
                items:[{  
                    xtype:"textfield",  
                    readOnly:true,
                    fieldLabel: '操作系统描述',  
                    name: 'description'
                }]  
            },{ 
            	style: {
					borderStyle: 'none'
				}, 
                items:[{  
                    xtype:"textfield",  
                    readOnly:true,
                    fieldLabel: '操作系统名称',  
                    name: 'name'
                }]  
            },{  
            	style: {
					borderStyle: 'none'
				}, 
                items:[{  
                    xtype:"textfield",  
                    readOnly:true,
                    fieldLabel: '操作系统版本',  
                    name: 'version'
                }]  
            } ,{  
            	style: {
					borderStyle: 'none'
				}, 
                items:[{  
                    xtype:"textfield",  
                    readOnly:true,
                    fieldLabel: '操作系统补丁包',  
                    name: 'patchlevel'
                }]  
            },{  
            	style: {
					borderStyle: 'none'
				}, 
                items:[{  
                    xtype:"textfield",  
                    readOnly:true,
                    fieldLabel: '操作系统厂商',  
                    name: 'vendor'
                }]  
            },{  
            	style: {
					borderStyle: 'none'
				}, 
                items:[{  
                    xtype:"textfield",  
                    readOnly:true,
                    fieldLabel: '操作系统厂商版本',  
                    name: 'vendorVersion'
                }]  
            }
            ]},
            {
            	xtype: 'fieldset',
            	title: 'CPU信息',
            	collapsible: true,
	            layout:"column",  
	            frame:true,
	            defaults:{frame:true,layout: 'form',border:false,columnWidth:.3},
	            items:[
	            	{  
		            	style: {borderStyle: 'none'}, 
		                items:[{  xtype:"textfield",  readOnly:true,fieldLabel: 'vendor',   name: 'cpu_vendor'}]  
		            },
		            {  
		            	style: {borderStyle: 'none'}, 
		                items:[{  xtype:"textfield",  readOnly:true,fieldLabel: 'model',   name: 'cpu_model'}]  
		            },
		            {  
		            	style: {borderStyle: 'none'}, 
		                items:[{  xtype:"textfield",  readOnly:true,fieldLabel: 'mhz',   name: 'cpu_mhz'}]  
		            },
		            {  
		            	style: {borderStyle: 'none'}, 
		                items:[{  xtype:"textfield",  readOnly:true,fieldLabel: 'totalCPUs',   name: 'cpu_totalCPUs'}]  
		            },
		            {  
		            	style: {borderStyle: 'none'}, 
		                items:[{  xtype:"textfield",  readOnly:true,fieldLabel: 'userTime',   name: 'cpu_userTime'}]  
		            },
		            {  
		            	style: {borderStyle: 'none'}, 
		                items:[{  xtype:"textfield",  readOnly:true,fieldLabel: 'sysTime',   name: 'cpu_sysTime'}]  
		            },
		            {  
		            	style: {borderStyle: 'none'}, 
		                items:[{  xtype:"textfield",  readOnly:true,fieldLabel: 'idleTime',   name: 'cpu_idleTime'}]  
		            },
		            {  
		            	style: {borderStyle: 'none'}, 
		                items:[{  xtype:"textfield",  readOnly:true,fieldLabel: 'waitTime',   name: 'cpu_waitTime'}]  
		            },
		            {  
		            	style: {borderStyle: 'none'}, 
		                items:[{  xtype:"textfield",  readOnly:true,fieldLabel: 'niceTime',   name: 'cpu_niceTime'}]  
		            },
		            {  
		            	style: {borderStyle: 'none'}, 
		                items:[{  xtype:"textfield",  readOnly:true,fieldLabel: 'combined',   name: 'cpu_combined'}]  
		            },
		            {  
		            	style: {borderStyle: 'none'}, 
		                items:[{  xtype:"textfield",  readOnly:true,fieldLabel: 'irqTime',   name: 'cpu_irqTime'}]  
		            }
	            ]
            },{
            	xtype: 'fieldset',
            	title: 'Memory信息',
            	collapsible: true,
	            layout:"column",  
	            frame:true,
	            defaults:{frame:true,layout: 'form',border:false,columnWidth:.3},
	            items:[
	            	{  
		            	style: {borderStyle: 'none'}, 
		                items:[{  xtype:"textfield",  readOnly:true,fieldLabel: 'total',   name: 'mem_total'}]  
		            },
		            {  
		            	style: {borderStyle: 'none'}, 
		                items:[{  xtype:"textfield",  readOnly:true,fieldLabel: 'used',   name: 'mem_used'}]  
		            },
		            {  
		            	style: {borderStyle: 'none'}, 
		                items:[{  xtype:"textfield",  readOnly:true,fieldLabel: 'free',   name: 'mem_free'}]  
		            },
		            {  
		            	style: {borderStyle: 'none'}, 
		                items:[{  xtype:"textfield",  readOnly:true,fieldLabel: 'actual_total',   name: 'mem_actual_total'}]  
		            },
		            {  
		            	style: {borderStyle: 'none'}, 
		                items:[{  xtype:"textfield",  readOnly:true,fieldLabel: 'actual_used',   name: 'mem_actual_used'}]  
		            },
		            {  
		            	style: {borderStyle: 'none'}, 
		                items:[{  xtype:"textfield",  readOnly:true,fieldLabel: 'actual_free',   name: 'mem_actual_free'}]  
		            },
		            {  
		            	style: {borderStyle: 'none'}, 
		                items:[{  xtype:"textfield",  readOnly:true,fieldLabel: 'swap_total',   name: 'mem_swap_total'}]  
		            },
		            {  
		            	style: {borderStyle: 'none'}, 
		                items:[{  xtype:"textfield",  readOnly:true,fieldLabel: 'swap_used',   name: 'mem_swap_used'}]  
		            },
		            {  
		            	style: {borderStyle: 'none'}, 
		                items:[{  xtype:"textfield",  readOnly:true,fieldLabel: 'swap_free',   name: 'mem_swap_free'}]  
		            }
	            ]
            },{
            	xtype: 'fieldset',
            	title: '文件系统信息',
            	collapsible: true,
	            layout:"column",  
	            frame:true,
	            defaults:{frame:true,layout: 'form',border:false,columnWidth:.3},
	            items:[
	            	{  
		            	style: {borderStyle: 'none'}, 
		                items:[{  xtype:"textfield",  readOnly:true,fieldLabel: '',   name: ''}]  
		            }
	            ]
            },{
            	xtype: 'fieldset',
            	title: '网络信息',
            	collapsible: true,
	            layout:"column",  
	            frame:true,
	            defaults:{frame:true,layout: 'form',border:false,columnWidth:.3},
	            items:[
	            	{  
		            	style: {borderStyle: 'none'}, 
		                items:[{  xtype:"textfield",  readOnly:true,fieldLabel: 'net_name',   name: 'net_name'}]  
		            },
		            {  
		            	style: {borderStyle: 'none'}, 
		                items:[{  xtype:"textfield",  readOnly:true,fieldLabel: 'net_address',   name: 'net_address'}]  
		            },
		            {  
		            	style: {borderStyle: 'none'}, 
		                items:[{  xtype:"textfield",  readOnly:true,fieldLabel: 'net_netmask',   name: 'net_netmask'}]  
		            },
		            {  
		            	style: {borderStyle: 'none'}, 
		                items:[{  xtype:"textfield",  readOnly:true,fieldLabel: 'net_macAddr',   name: 'net_macAddr'}]  
		            },
		            {  
		            	style: {borderStyle: 'none'}, 
		                items:[{  xtype:"textfield",  readOnly:true,fieldLabel: 'net_rxpackets',   name: 'net_rxpackets'}]  
		            },
		            {  
		            	style: {borderStyle: 'none'}, 
		                items:[{  xtype:"textfield",  readOnly:true,fieldLabel: 'net_txpackets',   name: 'net_txpackets'}]  
		            },
		            {  
		            	style: {borderStyle: 'none'}, 
		                items:[{  xtype:"textfield",  readOnly:true,fieldLabel: 'net_rxbytes',   name: 'net_rxbytes'}]  
		            },
		            {  
		            	style: {borderStyle: 'none'}, 
		                items:[{  xtype:"textfield",  readOnly:true,fieldLabel: 'net_txbytes',   name: 'net_txbytes'}]  
		            },
		            {  
		            	style: {borderStyle: 'none'}, 
		                items:[{  xtype:"textfield",  readOnly:true,fieldLabel: 'net_rxerrors',   name: 'net_rxerrors'}]  
		            },
		            {  
		            	style: {borderStyle: 'none'}, 
		                items:[{  xtype:"textfield",  readOnly:true,fieldLabel: 'net_txerrors',   name: 'net_txerrors'}]  
		            },
		            {  
		            	style: {borderStyle: 'none'}, 
		                items:[{  xtype:"textfield",  readOnly:true,fieldLabel: 'net_rxdropped',   name: 'net_rxdropped'}]  
		            },
		            {  
		            	style: {borderStyle: 'none'}, 
		                items:[{  xtype:"textfield",  readOnly:true,fieldLabel: 'net_txdropped',   name: 'net_txdropped'}]  
		            }
	            ]
            },{
            	xtype: 'fieldset',
            	title: 'JVM信息',
            	collapsible: true,
	            layout:"column",  
	            frame:true,
	            defaults:{frame:true,layout: 'form',border:false,columnWidth:.3},
	            items:[
	            	{  
		            	style: {
							borderStyle: 'none'
						}, 
		                items:[{  
		                    xtype:"textfield",  
		                    readOnly:true,
		                    fieldLabel: 'java_vm_version',  
		                    name: 'java_vm_version'
		                }]  
		            },{  
		            	style: {
							borderStyle: 'none'
						}, 
		                items:[{  
		                    xtype:"textfield",  
		                    readOnly:true,
		                    fieldLabel: 'java_vm_vendor',  
		                    name: 'java_vm_vendor'
		                }]  
		            },{  
		            	style: {
							borderStyle: 'none'
						}, 
		                items:[{  
		                    xtype:"textfield",  
		                    readOnly:true,
		                    fieldLabel: 'java_home',  
		                    name: 'java_home'
		                }]  
		            }
	            ]
            }
            
        ]
		me.callParent();
	}
});