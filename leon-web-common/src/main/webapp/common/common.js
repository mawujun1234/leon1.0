/**
 * 为每个ajax请求设定一些固定值
 */
//Ext.Ajax.on('beforerequest', function(conn,options ){
//	options.method="POST";
//	options.headers=Ext.apply(options.headers, {
//		Accept :"text/json",
//		contentType:'application/json;charset=UTF-8'
//	});
//});
//Ext.Ajax.defaultHeaders = {
// 'Accept': 'application/json'
//};
Ext.Ajax.timeout=60000000;
Ext.Ajax.on('requestexception', function(conn,response,options ){
	 if(response.status==404){
	    Ext.Msg.alert("消息","服务器连接失败，服务器可能已经关闭!");
		return;
	 } else if(response.status>=500){
	 	Ext.Msg.alert("消息","服务器发生错误!");
	 	return;
	 }
	Ext.Msg.alert("错误","后台连接失败!")
});



Ext.override(Ext.ZIndexManager, { 
    tempHidden: [], 
    show: function() { 
        var comp, x, y; 
        while (comp = this.tempHidden.shift()) { 
            x = comp.x; 
            y = comp.y; 

            comp.show(); 
            comp.setPosition(x, y); 
        } 
    } 
}); 




/*
 * Overrides for fixing clearOnLoad for TreeStore
 */
Ext.override(Ext.data.TreeStore, {
			load : function(options) {
				options = options || {};
				options.params = options.params || {};

				var me = this, node = options.node || me.tree.getRootNode(), root;

				// If there is not a node it means the user hasnt defined a
				// rootnode yet. In this case lets just
				// create one for them.
				if (!node) {
					node = me.setRootNode({
								expanded : true
					});
				}

				if (me.clearOnLoad) {
					node.removeAll(false);
				}

				Ext.applyIf(options, {
							node : node
				});
				options.params[me.nodeParam] = node ? node.getId() : 'root';

				if (node) {
					node.set('loading', true);
				}

				return me.callParent([options]);
			}
}); 


		//覆盖setter方法,可以直接通过json设置关联对象
		Ext.override(Ext.data.BelongsToAssociation, { 
		    createSetter: function() {
		        var me              = this,
		            ownerModel      = me.ownerModel,
		            associatedModel = me.associatedModel,
		            instanceName    = me.instanceName,
		            foreignKey      = me.foreignKey,
		            primaryKey      = me.primaryKey;
		        //'this' refers to the Model instance inside this function
		        return function(value, options, scope) {
		            //this.set(foreignKey, value);
		        	var obj=null;
		        	if(value.self && hh.self.getName()!=associatedModel){
		        		Ext.Error.raise('Unknown Association type: "' + association.type + '"');
		        	} else if(value.self && hh.self.getName()==associatedModel) {
		        		obj=value;
		        	} else {
		        		obj=Ext.create(associatedModel,value);
		        	}
		        	
		        	this[instanceName]=obj;
		
		            if (typeof options == 'function') {
		                options = {
		                    callback: options,
		                    scope: scope || this
		                };
		            }
		
		            if (Ext.isObject(options)) {
		                return this.save(options);
		            }
		        };
		    }
		}); 

		
		//可以直接通过json，创建具有关联关系的Model实例
		Ext.apply(Ext,{
			/**
		     * Creates a new instance of a Model using the given data.
		     * 
		     * @param {String} className The name of the model to create
		     * @param {Object} data Data to initialize the Model's fields with
		     * @param {boolean} implicitIncludes 是否解析关联对象，默认是true
		     */
		    createModel:function(className,data,implicitIncludes){
		    	//参考Ext.data.reader.Reader的extractData 方法
		    	var me=this
		    		,values  = []
		    		,id,record;
		    	if(arguments.length<3){
		    		implicitIncludes=true;
		    	}
		        //<debug error>
	            if (typeof className !== 'string') {
	                Ext.Error.raise({
	                    sourceClass: "Ext",
	                    sourceMethod: "define",
	                    msg: "Invalid class name '" + className + "' specified, must be a non-empty string"
	                });
	            }
	            // </debug>
				//values = extractValues(data);
				// id = data.id?data.id:null;//me.getId(data);
				//record = new Model(values, id, data);
	            
				var record=Ext.create(className,data);
				if (implicitIncludes) {
					//alert(record.associations.items.length);
					me.readAssociated(record, data);
				}
				return record;
		    },
		    /**
		     * @private 初始化关联数据
		     * Loads a record's associations from the data object. This prepopulates hasMany and belongsTo associations
		     * on the record provided.
		     * @param {Ext.data.Model} record The record to load associations for
		     * @param {Mixed} data The data object
		     * @return {String} Return value description
		     */
		    readAssociated: function(record, data) {
		        var associations = record.associations.items,
		            i            = 0,
		            length       = associations.length,
		            association, associationData, proxy, reader;
		        
		        for (; i < length; i++) {
		            association     = associations[i];
		            associationData = this.getAssociatedDataRoot(data, association.associationKey || association.name);
		            
		            if (associationData) {
		                reader = association.getReader();
		                if (!reader) {
		                    proxy = association.associatedModel.proxy;
		                    // if the associated model has a Reader already, use that, otherwise attempt to create a sensible one
		                    if (proxy) {
		                        reader = proxy.getReader();
		                    } else {
		                        reader = new this.constructor({
		                            model: association.associatedName
		                        });
		                    }
		                }
		                association.read(record, reader, associationData);
		            }  
		        }
		    },
		    /**
		     * @private
		     */
		    getAssociatedDataRoot: function(data, associationName) {
		        return data[associationName];
		    }
		});
		
		
		Ext.override(Ext.data.writer.Writer, { 
		    getRecordData: function(record) { 
		    	var me=this;
		        var data=this.callOverridden(arguments);
		        var aa=record.getAssociatedData( );
	        	Ext.apply(data,aa)
		        return data;
		    } 
		}); 
		
		
		//在cud提交数据的时候，可以同时提交关联数据
		Ext.override(Ext.data.proxy.Server, { 
		     processResponse: function(success, operation, request, response, callback, scope){
		        var me = this,
		            reader,
		            result;
		
		        if (success === true) {
		            reader = me.getReader();
		            result = reader.read(me.extractResponseData(response));
		
		            if (result.success !== false) {
		                //see comment in buildRequest for why we include the response object here
		                Ext.apply(operation, {
		                    response: response,
		                    resultSet: result
		                });
		
		                operation.commitRecords(result.records);
		                operation.setCompleted();
		                operation.setSuccessful();
		            } else {
		                operation.setException(result.message);
		                //让operation可以访问response
		                operation.response=response;
		                me.fireEvent('exception', this, response, operation);
		            }
		        } else {
		            me.setException(operation, response);
		            //让operation可以访问response
		            operation.response=response;
		            me.fireEvent('exception', this, response, operation);
		        }
		
		        //this callback is the one that was passed to the 'read' or 'write' function above
		        if (typeof callback == 'function') {
		            callback.call(scope || me, operation);
		        }
		
		        me.afterRequest(request, success);
		    }
		}); 
		
		//批量重载
		Ext.override(Ext.data.proxy.Server, { 
		    batchCreate: function() {
			    return this.doRequest.apply(this, arguments);
			},
			batchUpdate: function() {
			    return this.doRequest.apply(this, arguments);
			},
			batchDestroy: function() {
			    return this.doRequest.apply(this, arguments);
			}
		}); 
		Ext.override(Ext.data.proxy.Ajax, { 
		    getMethod: function(request) {
			    return this.actionMethods[request.action]||"POST";
			}
		}); 
		Ext.override(Ext.data.proxy.Proxy, { 
		    batchOrder:'create,update,destroy,batchCreate,batchUpdate,batchDestroy'
		}); 
		
		
//grid显示对象导航的方式========================================
		//为了在开始编辑的时候显示对象级联的信息
 	Ext.override(Ext.grid.plugin.CellEditing, {
 		 startEdit: function(record, columnHeader) {
	        var me = this,
	            value = record.get(columnHeader.dataIndex),
	            context = me.getEditingContext(record, columnHeader),
	            ed;
	
	        record = context.record;
	        columnHeader = context.column;
	
	        // Complete the edit now, before getting the editor's target
	        // cell DOM element. Completing the edit causes a view refresh.
	        me.completeEdit();
	        
	        //--添加的,现在只支持两级
	        if(!value &&columnHeader.dataIndex&& columnHeader.dataIndex.indexOf('.')!=-1){
	        	var objectPath=columnHeader.dataIndex.split('.');
	        	var associations     = record.associations.items,
            		associationCount = associations.length;
            	for (i = 0; i < associationCount; i++) {
            		association = associations[i];
            		type = association.type;
            		if (type == 'belongsTo') {
            			//alert(association.name);
            			associatedRecord = record[association.instanceName];
		                if (associatedRecord !== undefined && association.name==objectPath[0]) {
		                    value=associatedRecord.get(objectPath[1]);
		                }
            		}
            	}
            		
	        }
	        //--添加结束
	
	        context.originalValue = context.value = value;
	        if (me.beforeEdit(context) === false || me.fireEvent('beforeedit', context) === false || context.cancel) {
	            return false;
	        }
	        
	        // See if the field is editable for the requested record
	        if (columnHeader && !columnHeader.getEditor(record)) {
	            return false;
	        }
	        
	        ed = me.getEditor(record, columnHeader);
	        if (ed) {
	            me.context = context;
	            me.setActiveEditor(ed);
	            me.setActiveRecord(record);
	            me.setActiveColumn(columnHeader);
	
	            // Defer, so we have some time between view scroll to sync up the editor
	            me.editTask.delay(15, ed.startEdit, ed, [me.getCell(record, columnHeader), value]);
	        } else {
	            // BrowserBug: WebKit & IE refuse to focus the element, rather
	            // it will focus it and then immediately focus the body. This
	            // temporary hack works for Webkit and IE6. IE7 and 8 are still
	            // broken
	            me.grid.getView().getEl(columnHeader).focus((Ext.isWebKit || Ext.isIE) ? 10 : false);
	        }
	    }
	    ,onEditComplete : function(ed, value, startValue) {
	    	var me = this,
	            grid = me.grid,
	            activeColumn = me.getActiveColumn(),
	            sm = grid.getSelectionModel(),
	            record;
	
	        if (activeColumn) {
	            record = me.context.record;
	
	            me.setActiveEditor(null);
	            me.setActiveColumn(null);
	            me.setActiveRecord(null);
	    
	            if (!me.validateEdit()) {
	                return;
	            }
	
	            // Only update the record if the new value is different than the
	            // startValue. When the view refreshes its el will gain focus
	            if (!record.isEqual(value, startValue)) {
	            	//========添加开始
	            	//var record=me.context.record;
	            	var dataIndex=activeColumn.dataIndex;
	            	if(dataIndex.indexOf('.')!=-1){
	            		var objectPath=dataIndex.split('.');
	        			var associations     = record.associations.items,
            			associationCount = associations.length;
		            	for (i = 0; i < associationCount; i++) {
		            		association = associations[i];
		            		type = association.type;
		            		if (type == 'belongsTo') {
		            			//alert(association.name);
		            			associatedRecord = record[association.instanceName];
				                if (associatedRecord !== undefined && association.name==objectPath[0]) {
				                    //value=associatedRecord.get(objectPath[1]);
				                	associatedRecord.set(objectPath[1],value);
				                }
		            		}
		            	}
		            	//刷新一行，这样就可以再grid中显示新的数据，否则不会
		            	grid.getView().refreshNode(me.context.rowIdx);//refresh();
		            	//添加小红旗,这样是不行的
		            	//obj[headerId + '-modified'] = record.isModified(header.dataIndex) ? Ext.baseCSSPrefix + 'grid-dirty-cell' : '';
		            } else {
		            	//record.data[activeColumn.dataIndex]=value;
		            	record.set(activeColumn.dataIndex, value);
		            } 
		            //=======添加结束
	            }
	
	            // Restore focus back to the view's element.
	            if (sm.setCurrentPosition) {
	                sm.setCurrentPosition(sm.getCurrentPosition());
	            }
	            grid.getView().getEl(activeColumn).focus();
	
	            me.context.value = value;
	            me.fireEvent('edit', me, me.context);
	        }
	    }
 	});
 	Ext.override(Ext.grid.header.Container, {
 		prepareData: function(data, rowIdx, record, view, panel) {
	        var me        = this,
	            obj       = {},
	            headers   = me.gridDataColumns || me.getGridColumns(),
	            headersLn = headers.length,
	            colIdx    = 0,
	            header,
	            headerId,
	            renderer,
	            value,
	            metaData,
	            store = panel.store;
	
	        for (; colIdx < headersLn; colIdx++) {
	            metaData = {
	                tdCls: '',
	                style: ''
	            };
	            header = headers[colIdx];
	            headerId = header.id;
	            renderer = header.renderer;
	            value = data[header.dataIndex];
	            
	            //=====添加的
	            if(!value && header.dataIndex &&header.dataIndex.indexOf('.')!=-1){
		    		//var data = Ext.apply({}, record.data, record.getAssociatedData());
        			var data=record.getAssociatedData();
        			var aa=header.dataIndex.split('.');
        			if(data[aa[0]]){
        				value= data[aa[0]][aa[1]];
        			}
		    		
		    	} 
				//=====添加的
		    	
	            if (typeof renderer == "function") {
	                value = renderer.call(
	                    header.scope || me.ownerCt,
	                    value,
	                    // metadata per cell passing an obj by reference so that
	                    // it can be manipulated inside the renderer
	                    metaData,
	                    record,
	                    rowIdx,
	                    colIdx,
	                    store,
	                    view
	                );
	            }
	
	            // <debug>
	            if (metaData.css) {
	                // This warning attribute is used by the compat layer
	                obj.cssWarning = true;
	                metaData.tdCls = metaData.css;
	                delete metaData.css;
	            }
	            // </debug>
	            if (me.markDirty) {
	                obj[headerId + '-modified'] = record.isModified(header.dataIndex) ? Ext.baseCSSPrefix + 'grid-dirty-cell' : '';
	            }
	            obj[headerId+'-tdCls'] = metaData.tdCls;
	            obj[headerId+'-tdAttr'] = metaData.tdAttr;
	            obj[headerId+'-style'] = metaData.style;
	            if (typeof value === 'undefined' || value === null || value === '') {
	                value = header.emptyCellText;
	            }
	            obj[headerId] = value;
	        }
	        return obj;
	    }
 	});
//显示对象导航的数据========================================
 	
 	
//form 支持对象导航的方式做为字段名==========================================
 	 	Ext.override(Ext.form.Panel, { 
 		getRecord: function(recordClass) {
 			return this.getForm().getRecord(recordClass);
 		}
 	});
 	Ext.override(Ext.form.Basic, { 
 		//recordClass:null,
 		getRecord: function(recordClass) {
 			var recordClassTemp=recordClass||_record.self.getName()
 			if(!recordClassTemp){
 				Ext.Error.raise('请传递参数recordClass,即model的类名');
 				return null;
 			}
 			//this.recordClass=recordClass||this.recordClass;
 			var values=this.getValues();
 			var obj={};
 			for(var p in values){  
		         // 方法 
		         if(typeof(values[p])=="function"){  
		             values[p](); 
		         } else {
		         	if(p.indexOf('.')==-1){
		         		obj[p]=values[p];
		         	} else {
		         		var aa=p.split('.');
		         		if(!obj[aa[0]]){
		         			obj[aa[0]]={};
		         			obj[aa[0]][aa[1]]=values[p];
		         		} else {
		         			obj[aa[0]][aa[1]]=values[p];
		         		}
		         	}   
		         }  
		     }  
		     this._record = Ext.createModel(recordClassTemp,obj);
		     return this._record;
	    },
	    loadRecord: function(record) {
	        this._record = record;
	    	var data = Ext.apply({}, record.data, record.getAssociatedData()); 
	    	var values={};
	    	for(var p in data){
	    		if(Ext.isObject(data[p])){
	    			//var pp=p;
	    			for(var pp in data[p]){
	    				if(!Ext.isObject(p)){
	    					values[p+"."+pp]=data[p][pp];
	    				}
	    			}
	    		} else {
	    			values[p]=data[p];
	    		}
	    	}
	        return this.setValues(values);
	    }
 	});
//form 支持对象导航的方式做为字段名========================================== 	
		
 	
 	
 	
//让read和load方法也支持通过请求体传送json数据，而不是通过请求体传送a=1&b=2这种形式==============================================================
 	Ext.override(Ext.data.proxy.Ajax, { 
 			//让read和load方法也支持通过请求体传送json数据，而不是通过请求体传送a=1&b=2这种形式
 			paramsType:{
				//read:'jsonData',//也可以是params
				//load:'params'//,这里基本是设置成params,以参数的方式传递，增，删，改，请看writer
			},
 			doRequest: function(operation, callback, scope) {
 				var me=this;
	        	var writer  = this.getWriter(),
	            request = this.buildRequest(operation, callback, scope);
	         var header={};   

	         //=============添加的
	         //增，删，该的话进这里进行设置
			if(operation.action!='read' && operation.action!='load'){
	            request = writer.write(request);
	        } else if(this.paramsType[operation.action]=='jsonData'){
	        	if(Ext.isString(request.params[me.sortParam])){
	        		request.params[me.sortParam]=Ext.decode(request.params[me.sortParam]);
	        	}
	        	if(Ext.isString(request.params[me.filterParam])){
	        		request.params[me.filterParam]=Ext.decode(request.params[me.filterParam]);
	        	}
				request.jsonData=request.params;
				request.params={};
			} else if(this.paramsType[operation.action]=='params'){
				//read和get如果指定是以params的方式传递到后台的话，就指定为参数的方式，忽略设置了的headers
				header={
					Accept:'text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8',
					'Content-Type':'application/x-www-form-urlencoded; charset=UTF-8'
				};
				if(Ext.isArray(request.params[me.sortParam])){
	        		request.params[me.sortParam]=Ext.encode(request.params[me.sortParam]);
	        	}
	        	if(Ext.isArray(request.params[me.filterParam])){
	        		request.params[me.filterParam]=Ext.encode(request.params[me.filterParam]);
	        	}
			} else {
				if(Ext.isArray(request.params[me.sortParam])){
	        		request.params[me.sortParam]=Ext.encode(request.params[me.sortParam]);
	        	}
	        	if(Ext.isArray(request.params[me.filterParam])){
	        		request.params[me.filterParam]=Ext.encode(request.params[me.filterParam]);
	        	}
			}
			//=============添加的
			
	        
	        Ext.apply(request, {
	            headers       : header||this.headers,
	            timeout       : this.timeout,
	            scope         : this,
	            callback      : this.createRequestCallback(request, operation, callback, scope),
	            method        : this.getMethod(request),
	            disableCaching: false // explicitly set it to false, ServerProxy handles caching
	        });
	        
	        Ext.Ajax.request(request);
	        
	        return request;
	    }
 	});
 	
 	//让filter带上操作符 op属性
 	Ext.override(Ext.data.proxy.Server, { 
 		encodeFilters: function(filters) {
	        var min = [],
	            length = filters.length,
	            i = 0;
	
	        for (; i < length; i++) {
	            min[i] = {
	                property: filters[i].property,
	                op:filters[i].op,//这行是添加的
	                value   : filters[i].value
	            };
	        }
	        return this.applyEncoding(min);
	    }
 	});
//让read和load方法也支持通过请求体传送json数据，而不是通过请求体传送a=1&b=2这种形式=====================================================


 	
 	
 	Ext.define("Ext.app.SimpleModel",{
		extend:"Ext.data.Model",
		onClassExtended: function(cls, data, hooks) {
			//alert(hooks.onBeforeCreated);
			if(Ext.getClassName(cls.prototype.superclass)=='Ext.app.SimpleModel'){
				//alert(Ext.getClassName(cls.prototype.superclass));
				//console.dir(cls.prototype.superclass.proxy1);
				//console.dir(data);
				//console.log(this.api);
				var proxyConfig={};
				proxyConfig=Ext.apply(proxyConfig,cls.prototype.superclass.proxyConfig);
				
				if(data.api && Ext.isObject(data.api)){
					proxyConfig.api=data.api;
					data.proxy=proxyConfig;
					delete data.api;
				}
				
			}
		},
		proxyConfig: {
			type:'ajax',
			sortParam:'sorts',//调用排序的时候，把排序参数改成和PageReuest一样
			filterParam:'wheres',//调用过滤的时候，把过滤参数改成和PageReuest一样
			actionMethods: { read: 'POST' },
			headers:{
				Accept :" application/json"
				,'Content-Type':'application/json;charset=UTF-8'//注意在read的时候，提交方式的冲突，一个是参数方式一个是请求体方式
			},
			writer:{
				type: 'json'
				//,encode: true,root: 'parameterName'//在增，删，改的时候会吧reccord以parameterName={record}这种方式传递到后台
			},
			api:{},
			paramsType:{
				read:'jsonData',//也可以是params
				load:'params'//,这里基本是设置成params,以参数的方式传递。增，删，改，请看writer
			},
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

