





		
Ext.define("Order",{
	extend:"Ext.data.Model",
	idProperty:'id',
	fields:[
		{name:'id',type:'int'},
		{name:'text',type:'auto'},
		{name:'age',type:'int'},
		{name:'email',type:'auto'}
		//,{ name: 'orderLine_id', type: 'int'}//重要
	],
	associations:[//name是必须的，用来访问orderLine的
		{type:'hasMany',model:'OrderLine',name : 'orderLines'}
		//{ type: 'hasOne', model: 'OrderLine',associationKey:'orderLine',foreignKey:'orderLine_id' }//
	]
	,proxy:{
		type:'ajax',
		api:{
			read:'Order.js',
			load    : 'Order.js',
			create:'save.js',
			update:'update.js',
			destroy:'destroy.js'
		},
		reader:{
			type:'json',
			root:'root'
		}
		,writer:{
			type:'json'
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
			load    : 'OrderLine.js',
			create:'save.js',
			update:'update.js',
			destroy:'destroy.js'
		},
		reader:{
			type:'json',
			root:'root'
		}
		,writer:{
			type:'json'
		}
	
	}
});





Ext.onReady(function(){
	
		//var orderLine=null;
	OrderLine.load(1,{
		action:'load',
		success: function(record, operation) {
			orderLine=record;
			//alert(record.get('order_id'));//返回了1
			orderLine=record;
			//alert(orderLine.getOrder().getId());//弹出了11

			var order2=Ext.create('Order',{
				id:2,
				text:11
			});
			orderLine.setOrder(order2);			
			orderLine.save({includeAssociated:false});		
			
			orderLine.setOrder(2);			
			orderLine.save({includeAssociated:false});
		}
	});
	
	
	
	
////	var order = new Order({
////	    id: 100,
////	    orderLine_id: 20,
////	    name: 'John Smith'
////	});
//	Order.load(1,{
//		action:'load',
//		success: function(order, operation) {
//			alert(order.getOrderLine().getId());
//			alert(order.get('orderLine_id'));
//			alert(order.getOrderLine().getOrder().getId());
//			alert(order.getOrderLine().get("order_id"));
//
//		}
//	});
	
//	order.getOrderLine(function(orderLine, operation) {
//	    // do something with the address object
//	    alert(orderLine.get('id')); // alerts 20
//	}, this);
	
	

//	Order.load(1,{
//		action:'load',
//		success: function(record, operation) {
//			alert(record.get('text'));
//			order=record;
//			//双向关联已经配置好了
//			//alert(order.orderLines().getAt(0).getOrder().getId());
//			var line1=order.orderLines().getAt(0);
//			var orderline=Ext.create('OrderLine',{
//				id:33,
//				name:'更新的'//因为设置了id
//			});
//			var orderline1=Ext.create('OrderLine',{
//				//id:44,
//				name:'新增的'//因为没有设置id
//			});
//			var orderLines=order.orderLines();
//			orderLines.add(orderline);
//			orderLines.add(orderline1);
//			//orderLines.remove(line1);
//			orderLines.removeAt(0);
//			orderLines.sync();
//		}
//		//,failure: function(record, operation) {}
//		//,callback: function(record, operation) {}
//	});
	
	
	
	
//	//var orderLine=null;
//	OrderLine.load(1,{
//		action:'load',
//		success: function(record, operation) {
//			orderLine=record;
//			alert(record.get('order_id'));//返回了1
//			orderLine=record;
//			alert(orderLine.getOrder().getId());//弹出了11
//
//			var order2=Ext.create('Order',{
//				id:2
//			});
//			orderLine.setOrder(order2);
//			
//			orderLine.getOrder().getId();
//		}
//	});
	
	
	
	
	
	
	
	
//var store = Ext.create('Ext.data.TreeStore', {
////	proxy:{
////		type:'ajax',
////		url:'Order.js'
////	
////	},
//	model:'Order',
//	//nodeParam :'qq',
//    root: {
//        expanded: false
//        ,text:'11'
//        ,children: [
//            { text: "detention", leaf: true },
//            { text: "homework", expanded: true, children: [
//                { text: "book report", leaf: true },
//                { text: "algebra", leaf: true}
//            ] },
//            { text: "buy lottery tickets", leaf: true ,id:'test'}
//        ]
//    }
//});
//
//
//
//Ext.create('Ext.tree.Panel', {
//	tbar:[{text:'删除',handler:function(){
//		//store.removeAll();
//		var node=store.getNodeById("11");
//		node.destroy();
//		store.sync();
//	}}],
//    title: 'Simple Tree',
//    width: 200,
//    height: 150,
//    store: store,
//    rootVisible: true,
//    renderTo: Ext.getBody()
//});



//var node=store.getNodeById("test");
////alert(node.getId());
//store.load({
//	node:node,
//	params:{a:1,b:2}
//});
//	//用ext
//	var store=Ext.create('Ext.data.Store',{
//		model:'Order',
//		autoLoad:false
//	});
//	store.load({
//	    scope: this,
//	    callback: function(records, operation, success) {
//	    	store.getAt(0).set("name","order1111");
//	    	store.add({name:"order2"});
//	    	store.sync();
//	    }
//	});
////
//	var orderLine=null;
//	OrderLine.load(1,{
//		action:'load',
//		success: function(record, operation) {
//			alert(record.get('name'));
//			orderLine=record;
//			//alert(Ext.encode(operation.response.responseText ))
//			orderLine.save({
//				success: function(record, operation) {
//					alert(record.get('name'));
//				}
//			});
//		}
//		//,failure: function(record, operation) {}
//		//,callback: function(record, operation) {}
//	});
	
	
//	var order=null;
//	Order.load(1,{
//		action:'load',
//		success: function(record, operation) {
//			alert(record.get('name'));
//			order=record;
//			//alert(Ext.encode(operation.response.responseText ))
//			order.save({
//				success: function(record, operation) {
//					alert(record.get('name'));
//					order=record;
//				}
//			});
//		}
//		//,failure: function(record, operation) {}
//		//,callback: function(record, operation) {}
//	});

//	var orderLine=null;
//	OrderLine.load(1,{
//		action:'load',
//		success: function(record, operation) {
//			alert(record.get('name'));
//			orderLine=record;
//			//alert(Ext.encode(operation.response.responseText ))
//			orderLine.save({
//				includeAssociated:false,
//				success: function(record, operation) {
//					alert(record.get('name'));
//				}
//			});
//		}
//		//,failure: function(record, operation) {}
//		//,callback: function(record, operation) {}
//	});
//	
//	
//	var order=null;
//	Order.load(1,{
//		action:'load',
//		success: function(record, operation) {
//			alert(record.get('name'));
//			order=record;
////			//alert(Ext.encode(operation.response.responseText ))
////			order.destroy({
////				includeAssociated:false,
////				success: function(record, operation) {
////					alert(record.get('name'));
////					order=record;
////				}
////			});
//			order.orderLines().add({name:'orderline2'});
//			order.orderLines().sync();
//		}
//		//,failure: function(record, operation) {}
//		//,callback: function(record, operation) {}
//	});

	
//	var order=Ext.create('Order',{
//		id:1,
//		name:'11'
//	});
//	order.destroy({
//		success: function(record, operation) {
//			alert(record.get('name'));
//			order=record;
//		}
//	});
	
	
//=========hasmany的测试	
//	var order=Ext.create('Order',{
//		id:1,
//		name:'order1'
//	});
//	var orderlines=order.orderLines();
//	//alert(orderlines.getCount( ));
//	
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
	

});
