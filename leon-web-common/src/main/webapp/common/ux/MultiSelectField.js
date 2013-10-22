/**
 * 只要覆盖onTrigger2Click方法就可以了，例子看注释掉的
 */
Ext.define('Leon.common.ux.MultiSelectField', {
    extend: 'Ext.form.field.Trigger',
    requires:[
    	'Ext.ux.utils.OrgSelectPanel'
    ],
    
    alias: 'widget.selectorgfield',
    
    trigger1Cls: Ext.baseCSSPrefix + 'form-clear-trigger',
    
    trigger2Cls: Ext.baseCSSPrefix + 'form-search-trigger',
    
    value:null,

    initComponent: function(){
        this.callParent(arguments);
        this.editable =false;
        this.on('specialkey', function(f, e){
            if(e.getKey() == e.ENTER){
                this.onTrigger2Click();
            }
        }, this);
    },
    
    afterRender: function(){
        this.callParent();
        //this.triggerEl.item(0).setDisplayed('none');  
    },
    /**
     * 点删除按钮时清空数据
     */
    onTrigger1Click : function(){
        var me = this;
         
        me.setValue('');
        me.setRawValue('');//'' + Ext.value(value, '');

    },
	/**
	 * 这个是要覆盖的方法，在这里实现如何从弹出框里面获取值
	 * 点查询按钮的时候，弹出框进行查询
	 * 弹出panel返回的Models，要组装成，value是数组，rawValue是以逗号分隔的字符串，显示用的
	 */
    onTrigger2Click : function(){
        var me = this;
  //这是例子，不要删除    
//        var selOrgWin=Ext.create('Ext.ux.utils.SelectOrgPanel',{});
//        selOrgWin.show();
//        selOrgWin.on("selectedOrg",function(models){
//        	//alert(models.length);
//        	if(!models||models.length==0){
//        		return;
//        	}
//        	var rawValue='';
//        	var value=[];
//        	for(var i=0;i<models.length;i++){
//        		//value+=','+models[i].get('id');
//        		value.push(models[i].get('id'))
//        		rawValue+=','+models[i].get('name');
//        	}
//        	
//        	me.setValue(value);
//          me.setRawValue(rawValue.substring(1));
//        });
        
    },
    setValue:function(value){
    	var me = this;
        me.setRawValue(me.valueToRaw(value));
    	me.value=value;
    },
    getValue: function() {
        var me = this,val = me.value?me.value:me.rawToValue(me.processRawValue(me.getRawValue()));
        return val;
    },
    getSubmitValue: function() {
        return '' + Ext.value(this.value, '');
    }


});
/**
 * 用来显示框的，默认添加一个按
 */
Ext.define('Leon.common.ux.MultiSelectPanel', {
	
});