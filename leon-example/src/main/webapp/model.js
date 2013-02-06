/**
 * 
 */
 Ext.onReady(start);
 function start(){
 	
// 	Ext.override(Ext.data.Model, { rrrrr
//		    get: function(field) { 
//		    	if(!field){
//		    		return null;
//		    	}
//		    	var value=this.callParent(arguments);
//		    	if(!value && field.indexOf('.')!=-1){
//		    		//var data = Ext.apply({}, record.data, record.getAssociatedData());
//        			var data=this.getAssociatedData();
//        			//alert(field);
//        			//alert(Ext.encode(data));
//        			var aa=field.split('.');
//        			var temp=data;
//        			for(var i=0;i< aa.length;i++){
//        				temp=temp[aa[i]];
//        			}
//		    		return temp;
//		    	} else {
//		    		return value;
//		    	
//		    	}
//		    } 
//		}); 
 	

 	
 	Ext.define("Ext.example.ExampleType",{
		//extend:"Ext.data.Model",
 		extend:'Ext.app.SimpleModel',
		//idProperty:undefined,
		clientIdProperty:'id',
		fields:[
			{name:'aaaaaa',type:'auto'},
			{name:'code',type:'auto'},
			{name:'name',type:'auto'}
		]
 	});
	
 	//在创建具有关联数据的时候 使用Ext.createModel();
 	//或者在创建模型数据的时候就使用这个
	Ext.define("Ext.example.Example",{
		extend:"Ext.app.SimpleModel",
		idProperty:'id',
		fields:[
			{name:'comments',type:'string'},
			{name:'createDate',type:'date'},
			{name:'name',type:'string'}
		],
		belongsTo:[
			{model: 'Ext.example.ExampleType',associationKey: 'exampleType',associatedName:'exampleType',getterName:'getExampleType'},
			{model: 'Ext.example.AssoObj',associationKey: 'assoObj',associatedName:'assoObj',getterName:'getAssoObj'}
		],
		//belongsTo:  {model: 'ExampleType',associationKey: 'exampleType',associatedName:'exampleType',getterName:'getExampleType'},
		api: {
			create  : Ext.ContextPath+'/example/insert.do',
			read    : Ext.ContextPath+'/example/qeryPage.do',
			load    : Ext.ContextPath+'/example/get.do',//在load的时候指定
			update  : Ext.ContextPath+'/example/update.do',
			destroy : Ext.ContextPath+'/example/delete.do'
		}
	});
 	
//这个不要删除，这个是没有继承SimpleMode的时候的创建方法	
// 	Ext.define("Example",{
//		extend:"Ext.data.Model",
//		fields:[
//			{name:'name',type:'auto'},
//			{name:'comments',type:'auto'},
//			{name:'createDate',type:'date'}
//		],
//		belongsTo:  {model: 'ExampleType',associationKey: 'exampleType',associatedName:'exampleType',getterName:'getExampleType'},
//
//		proxy: {
//			type:'ajax',
//			sortParam:'sorts',//调用排序的时候，把排序参数改成和PageReuest一样
//			filterParam:'wheres',//调用过滤的时候，把过滤参数改成和PageReuest一样
//			actionMethods: { read: 'POST' },
//			headers:{
//				Accept :" application/json"
//				,'Content-Type':'application/json;charset=UTF-8'//注意在read的时候，提交方式的冲突，一个是参数方式一个是请求体方式
//			},
//			writer:{
//				type: 'json'
//				//,encode: true,root: 'parameterName'//在增，删，改的时候会吧reccord以parameterName={record}这种方式传递到后台
//			},
//			paramsType:{
//				read:'jsonData',//也可以是params
//				load:'params'//,这里基本是设置成params,以参数的方式传递。增，删，改，请看writer
//			},
//			api: {
//			    create  : Ext.ContextPath+'/example/insert.do',
//			    read    : Ext.ContextPath+'/example/qeryPage.do',
//			    load    : Ext.ContextPath+'/example/get.do',//在load的时候指定
//			    update  : Ext.ContextPath+'/example/update.do',
//			    destroy : Ext.ContextPath+'/example/delete.do'
//			},
//			reader:{//后台传递过来的信息解析器
//					type:'json',
//					//这个要加上，这样才能通过operation.resultSet.message获取到后台的信息
//					messageProperty:'message',
//					root:'root'//返回的对象要放在root数组里
//					,totalProperty:'totalProperty'
//					,successProperty:'success'
//			}
//		}
//	});//Ext.define("person",{
	
	
	var store = Ext.create('Ext.data.Store', {
		model: 'Ext.example.Example',
		remoteSort:true,
		remoteFilter:true
	});
	var grid=Ext.create('Ext.grid.Panel',{
		region:'center',
		store:store,
		multiSelect:true,
		columns:[
			{text:"name",dataIndex:'name',width:100,field:{
						xtype:'textfield',
						allowBlank:false
					}},
			{text:"comments",dataIndex:'comments',width:100},
			{text:"createDate",dataIndex:'createDate',width:100},
			{text:"exampleType.aaaaaa",dataIndex:'exampleType.aaaaaa',field:{
						xtype:'textfield',
						allowBlank:false
					}},
			{text:"exampleType.code",dataIndex:'exampleType.code',editor: new Ext.form.field.ComboBox({
                typeAhead: true,
                triggerAction: 'all',
                selectOnTab: true,
                store: [
                    ['Shade','Shade'],
                    ['Mostly Shady','Mostly Shady'],
                    ['Sun or Shade','Sun or Shade'],
                    ['Mostly Sunny','Mostly Sunny'],
                    ['Sunny','Sunny']
                ],
                lazyRender: true,
                listClass: 'x-combo-list-small'
            })},
			{text:"exampleType.name",xtype:'templatecolumn',tpl:"{exampleType.name}",field:{
						xtype:'textfield',
						allowBlank:false
					}}
		],
		plugins:[
				Ext.create("Ext.grid.plugin.CellEditing",{
					clicksToEdit : 2,
					listeners:{
						edit:function(edit,e){
							alert(e.field);
							alert(e.value);
							var record=e.record;
							record.save();
						}
					}
				})
		]
	});
	
	var formPanel=Ext.create('Ext.form.Panel',{
		region:'south',
		height:400,
		fieldDefaults: {
            msgTarget: 'side',
            labelWidth: 150
        },
        defaultType: 'textfield',
		items:[{
            fieldLabel: 'id',
            name: 'id',
            allowBlank:true
        },{
            fieldLabel: 'name',
            name: 'name',
            allowBlank:false
        },{
            fieldLabel: 'comments',
            name: 'comments'
        }
        ,{
            fieldLabel: 'exampleType.aaaaaa',
            name: 'exampleType.aaaaaa'
        },{
            fieldLabel: 'exampleType.code',
            name: 'exampleType.code'
        }
        ],
        buttons: [{
            text: '加载',
            handler:function(){
            	var example = Ext.createModel('Ext.example.Example', {
											id : 1,
											name : '更新名称1',
											comments : '注视',
											exampleType : {
												aaaaaa : '1111--aaaaaa',
												code : '1111-code'
											}
				});
				formPanel.loadRecord(example);
				//或者是Ext.example.Example.load(1,{
				//	action: 'load',
				//  success:function(record){
				//		formPanel.loadRecord(record);
				//  }
				//});
            	
            }
        },{
            text: '保存',
            handler:function(){
            	var model=formPanel.getRecord('Ext.example.Example');
            	model.save();
            }
        }]
	});
	
	var example1={};
	var panel=Ext.create('Ext.panel.Panel',{
		layout:'border',
		items:[grid,formPanel],
		tbar:[
			{text:'load',handler:function(){
				Ext.example.Example.load(1,{
					action: 'load',
					success: function(user, operation) {
						alert(user.get('id'));
						//alert(user.get('name'));
						//alert(operation.resultSet.message);
						//注意即时获取，而不是使用延迟加载是怎么设置的
						//alert(user.getExampleType().get('aaaaaa'));
						alert(user.get('exampleType.aaaaaa'));
						example1=user;
				    },
				    failure: function(record, operation) {
                      //do something if the load failed
				    	//success:false,就会跳到这里来
				    	alert(record+"failure");
				    	//获取后台返回的错误的信息(message属性的值)
				    	alert(Ext.encode(operation.getError()));
				    	//访问不到，方法是Ext-data-proxy-Server类的processResponse方法中进行设置的，没有返回返回的值
				    	//这是通过重构实现的，否则访问不到后台返回来的具体的值
				    	alert(Ext.encode(operation.response.responseText ));
                    },
                    callback: function(record, operation) {
                     //do something whether the load succeeded or failed
                    	//alert(record+"callback");
                    }
				});
			}},
			{
				text:'save',
				handler:function(){
					var example=Ext.createModel('Ext.example.Example',{
						//id
						name:'名称1',
						comments:'注视',
						exampleType:{
							aaaaaa:'1111--aaaaaa',
							code:'1111-code'
						}
					});
					//在保存的时候，会把ExampleType的当做一个实体，即会把id传递过去，
					//解决方案，1：在接受的时候以实体属性为准，如果不存在属性就不从json中拷贝。2：不传递id。3：建立ExampleType的字段类型
					example.save({
							failure: function(record, operation) {
					        },
					        success: function(record, operation) {
					        	alert(record.get('id'))
					        	alert(operation.resultSet.message);
					        },
					        callback: function(record, operation) {
					        }
					});
				}
			},
			{
				text:'update',
				handler:function(){
//					example1.set('name','更新名称');
//					//在保存的时候，会把ExampleType的当做一个实体，即会把id传递过去，
//					//解决方案，1：在接受的时候以实体属性为准，如果不存在属性就不从json中拷贝。2：不传递id。3：建立ExampleType的字段类型
//					example1.save({
//							failure: function(record, operation) {
//					        },
//					        success: function(record, operation) {
//					        	alert(record.get('id'))
//					        	alert(operation.resultSet.message);
//					        },
//					        callback: function(record, operation) {
//					        }
//					});
					
//					var example=Ext.createModel('Example',{
//						id:1,
//						name:'更新名称1',
//						comments:'注视',
//						exampleType:{
//							aaaaaa:'1111--aaaaaa',
//							code:'1111-code'
//						}
//					});
					var example=Ext.example.Example.create({
						id:1,
						name:'更新名称1',
						comments:'注视'
					});
					example.save({
							failure: function(record, operation) {
					        },
					        success: function(record, operation) {
					        	alert(record.get('id'))
					        	alert(operation.resultSet.message);
					        },
					        callback: function(record, operation) {
					        }
					});
				}
			},
			{
				text:'delete',
				handler:function(){
					
//					Example.getProxy( ).headers={
//						Accept :" application/json"
//						,'Content-Type':'application/json;charset=UTF-8'//请求的类型是json,导致extjs传递参数时候有问题
//					};
					var example=Ext.createModel('Ext.example.Example',{
						id:1,
						name:'名称1',
						comments:'注视',
						exampleType:{
							aaaaaa:'1111--aaaaaa',
							code:'1111-code'
						}
					});
					
					example.destroy({
							failure: function(record, operation) {
					        },
					        success: function(record, operation) {
					        	alert(record.get('id'))
					        	alert(operation.resultSet.message);
					        },
					        callback: function(record, operation) {
					        }
					});
				}
			},{
				text:'查数据grid',
				handler:function(){					
					grid.getStore().load({params:{name:"111"}});
				}
			},{
				text:'动态查数据grid',//前台确定sql
				handler:function(){					
					grid.getStore().load({params:{
						wheres:[{property:'name',op:'>',value:'aa'}]
						////具体的操作符请看WhereOperation中的值
						,sorts:[{property : 'name', direction: 'ASC'},{property : 'comments',direction: 'DESC'}
						]
					}});
				}
			}
			,{
				text:'改数据Grid',
				handler:function(){					
					alert("直接在单元格中进行修改");
				}
			},{
				text:'删数据Grid',
				handler:function(){					
					var selMode=grid.getSelectionModel();
					var record=selMode.getLastSelected();
					alert(record.get('id'))
					record.destroy();
				}
			},{
				text:'批量删数据Grid',
				handler:function(){					
					var selMode=grid.getSelectionModel();
					var records=selMode.getSelection( );

					Ext.example.Example.getProxy( ).batch({
						destroy: records
					});
				}
			},{
				text:'排序Grid',
				handler:function(){		
					
					//grid.getStore().sort('name');//只过滤一个属性的时候
					grid.getStore().sort([{property : 'name',direction: 'ASC'},
					    {property : 'comments',direction: 'DESC'}
					]);
				}
			},{
				text:'过滤(filter)Grid',
				handler:function(){		
					//grid.getStore().filter('name','Brown');
					//这里会保存过滤条件，所以再次使用过滤的时候要先clearFilter
					grid.getStore().clearFilter(true);
					//具体的操作符请看WhereOperation中的值
					grid.getStore().filter([{property : 'name',op:'=', value: 'aaaa'},{ property : 'comments', op:'<',value: 'bbbbb'}]);
				}
			},{
				text:'查数据grid（新的store）',
				handler:function(){					
						var store=new Ext.data.Store({
							fields:['name'],
							remoteSort:true,//这样才能远程排序
							remoteFilter:true,
							
							proxy: {
//								paramsType:{
//				read:'params',//也可以是params
//				load:'params'//,这里基本是设置成params,以参数的方式传递。增，删，改，请看writer
//			},
								type:'ajax',
								sortParam:'sorts',//必须的
								filterParam:'wheres',//必须的
								actionMethods: { read: 'POST' },
								url:Ext.ContextPath+'/example/queryPage1.do',
								reader:{//后台传递过来的信息解析器
										type:'json',
										//这个要加上，这样才能通过operation.resultSet.message获取到后台的信息
										messageProperty:'message',
										root:'root'//返回的对象要放在root数组里
										,totalProperty:'totalProperty'
										,successProperty:'success'
								}
							}
						});
						
						//这里的问题wheres，sorts都必须是紫府串，现在改成可以是数组了，而不是必须用双引号括起来的
						//1:直接指定也是可以的
						store.load({params:{wheres:[{property:'name',value:'张三'},{property:'name',value:'李四'}],sorts:[{property:'name',direction:'asc'}]}});
						//store.load({params:{wheres:"[{property:'name',value:'张三'},{property:'name',value:'李四'}]",sorts:"[{property:'name',direction:'asc'}]"}});
//						//2:这种方法才以为下一个排序查询保存查询条件
//						store.getProxy().extraParams={wheres:"[{property:'name',value:'张三'},{property:'name',value:'李四'}]",sorts:"[{property:'name',direction:'asc'}]"};
//						store.load();
						window.aaaStore=store;
				}
			},{
				text:'查数据grid（新的store）排序',
				handler:function(){					
					var store=window.aaaStore;//这里sort不会带上上次查询的条件
					//store.load({params:{wheres:[{property:'name',value:'张三'},{property:'name',value:'李四'}],sorts:[{property:'name',direction:'asc'}]}});
					store.sort([{property : 'name',direction: 'ASC'},{property : 'comments',direction: 'DESC'}]);
				}
			}
		]
	});
	
	
	//http://www.iteye.com/topic/1127120 手动产生请求
	var viewPort=Ext.create('Ext.container.Viewport',{
		items:[panel],
		layout:'fit'
	})

 }