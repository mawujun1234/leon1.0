Ext.override(Ext.data.BelongsToAssociation, {
	createSetter : function() {
		var me = this, foreignKey = me.foreignKey;
		// 'this' refers to the Model instance inside this function
		return function(value, options, scope) {
//			// If we pass in an instance, pull the id out
//			//原来不的代码
//			if (value && value.isModel) {
//				value = value.getId();
//			}
//			this.set(foreignKey, value); 
//			//原来不的代码
			//添加的内容
			if (value && value.isModel) {
				this[me.instanceName]=value;
				this.set(foreignKey, value.getId());
			} else {
		        var obj=Ext.create(me.associatedModel,value);
		        this[me.instanceName]=obj;
		        this.set(foreignKey, obj.getId());
		    }
		    //体哪家的内容

			
			if (Ext.isFunction(options)) {
				options = {
					callback : options,
					scope : scope || this
				};
			}
			if (Ext.isObject(options)) {
				return this.save(options);
			}
		};
	}
	
	,createGetter: function() {
        var me              = this,
            associatedName  = me.associatedName,
            associatedModel = me.associatedModel,
            foreignKey      = me.foreignKey,
            primaryKey      = me.primaryKey,
            instanceName    = me.instanceName;

        //'this' refers to the Model instance inside this function
        return function(options, scope) {
            options = options || {};

            var model = this,
                foreignKeyId = model.get(foreignKey),
                success,
                instance,
                args;
                
                ///=======这样就支持面向对象的方式获取关联对象的id了，不用定义order_id这种Filed，只要定义普通field和关联关系就可以延迟从后台加载数据了
                if(!foreignKeyId){
                	foreignKeyId=model.raw[Ext.String.uncapitalize(associatedName)].id;
                }
                //=========

            if (options.reload === true || model[instanceName] === undefined) {
                instance = Ext.ModelManager.create({}, associatedName);
                instance.set(primaryKey, foreignKeyId);

                if (typeof options == 'function') {
                    options = {
                        callback: options,
                        scope: scope || model
                    };
                }
                
                // Overwrite the success handler so we can assign the current instance
                success = options.success;
                options.success = function(rec){
                    model[instanceName] = rec;
                    if (success) {
                        success.apply(this, arguments);
                    }
                };

                associatedModel.load(foreignKeyId, options);
                // assign temporarily while we wait for data to return
                model[instanceName] = instance;
                return instance;
            } else {
                instance = model[instanceName];
                args = [instance];
                scope = scope || options.scope || model;

                //TODO: We're duplicating the callback invokation code that the instance.load() call above
                //makes here - ought to be able to normalize this - perhaps by caching at the Model.load layer
                //instead of the association layer.
                Ext.callback(options, scope, args);
                Ext.callback(options.success, scope, args);
                Ext.callback(options.failure, scope, args);
                Ext.callback(options.callback, scope, args);

                return instance;
            }
        };
    }
});

Ext.apply(Ext,{
	 createModel:function(className,data){
	 	if (typeof className !== 'string') {
	                Ext.Error.raise({
	                    sourceClass: "Ext",
	                    sourceMethod: "define",
	                    msg: "Invalid class name '" + className + "' specified, must be a non-empty string"
	                });
	     }
	            
		var record=Ext.create(className,data);
		//if (implicitIncludes ) {
			var reader=record.getProxy().getReader();
			reader.readAssociated(record, data);
		//}
		return record;

	 }
});

//var reader=Ext.create('Ext.data.reader.Json',{
//			type:'json',
//			root:'root'
//		});
		
Ext.define("Order",{
	extend:"Ext.data.Model",
	idProperty:'id',
	fields:[
		{name:'id',type:'int'},
		{name:'name',type:'auto'},
		{name:'age',type:'int'},
		{name:'email',type:'auto'}
	],
	associations:[//name是必须的，用来访问orderLine的
		{type:'hasMany',model:'OrderLine',name : 'orderLines'}//,name : 'orderLines',associationKey:'orderLines'
	]
	,proxy:{
		type:'ajax',
		api:{
			read:'Order.js',
			create:'',
			update:'',
			destory:''
		},
		reader:{
			type:'json',
			root:'root'
		}
	
	}
	
});


Ext.define("OrderLine",{
	extend:"Ext.data.Model",
	idProperty:'id',
	fields:[
		{name:'id',type:'int'},
		{name:'name',type:'auto'},
		{name:'num',type:'int'}
		,{name:'order_id',type:'int'}
	],
	associations:[//,associationKey:"order",foreignKey:'order',associatedName:'Order'
		{type:'belongsTo',model:'Order',autoLoad:false}//后台传过来的数据，如果有order，就不会使用ajax加载，否则就会使用ajax
	]
	,proxy:{
		type:'ajax',
		api:{
			read:'OrderLine.js',
			create:'OrderLine.js',
			update:'OrderLine.js',
			destory:''
		},
		reader:{
			type:'json',
			root:'root'
		}
	
	}
});



Ext.onReady(function(){
	
	
//=========hasmany的测试	
	var order=Ext.create('Order',{
		id:1,
		name:'order1'
	});
	var orderlines=order.orderLines();
	//alert(orderlines.getCount( ));
	
//	order.orderLines().each(function(post) {//使用aotoLoad=true，获取不到数据
//            console.log("Comments for post: " + post.get('name'));
//     });
//        
//	orderlines.load({//这种方法才可以
//		filters: [{
//	         property: 'firstName',
//	         value: 'Ed'
//	     }],
//		callback:function(posts){
//			 Ext.each(posts, function(post) {
//                    alert("orderlines for order: " + post.get('name'));
//           });
//		}
//	});
//has many的测试
	
	
//========================下面的是belongsTO的
     
//	OrderLine.load(1,{
//		 success: function(record, operation) {
//		 	alert(record.get("id"));
//		 	//alert(record.getOrder().get("name"));获取不到数据，因为异步加载
//		 	record.getOrder(function(order, operation){
//		 		 alert(order.get('name'));
//		 	});
//		 }
//	});

//	var orderLine=Ext.create('OrderLine',{
//		id:1,
//		name:'line1',
//		num:1,
//		order_id:'1'
//	});
//	
//	orderLine.getOrder(function(order, operation){
//		  alert(order.get('name'));
//	});
//     
//     

//	//即时加载
//	Order.load(1,{
//		 success: function(order, operation) {
//		 	//console.dir(order);
//		 	alert(order.get("id"));
//		 	//alert(order.get('orderLines'));
//		 	//alert(order.orderLines().getCount( ));
//		 	order.orderLines().each(function(post) {
//	            console.log("Comments for post: " + post.get('name'));
//	        });
//		 }
//	});

//==========================================================前面的都是正常可用的
	
//	OrderLine.load(1,{
//		 success: function(record, operation) {
//		 	alert(record.get("id"));
//		 	alert(record.getOrder().get("name"));
//		 }
//	});
//	
//	Order.load(1,{
//		 success: function(record, operation) {
//		 	alert(record.get("id"));
//		 	alert(record.orderLines());
//		 }
//	});
	
//	var orderLine=Ext.create('OrderLine',{
//		id:1,
//		name:'line1',
//		num:1
//	});

//	var orderLine=OrderLine.create({
//		id:1,
//		name:'line1',
//		num:1
//		,order:{
//			id:11,
//			name:'order1'
//		}
//	});
//	alert(orderLine.getOrder().get("name"));


	//console.dir(orderLine);
	//alert(orderLine.get("id"));
	//orderLine.setOrder({id:22,name:'222',age:122222});
	//orderLine.setOrder(Ext.create("Order",{id:22,name:'222',age:122222}));
//	var order=orderLine.getOrder(function(){
//		console.dir(orderLine.getData( true));
//	});
////	console.dir(orderLine.getData( true));
//	var order=orderLine.getOrder();
//	console.dir(order);
//	alert(order.get("name"));
	
//	orderLine.save();

//	var orderLine=Ext.createModel('OrderLine',{
//		id:1,
//		name:'line1',
//		num:1
//		,order:{
//			id:11,
//			name:'order1'
//		}
//	});
//	var order=orderLine.getOrder();
//	console.dir(order);
//	alert(order.get("name"));


});
