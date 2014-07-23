Ext.override(Ext.grid.RowEditor,
    {
      addFieldsForColumn : function(column, initial) {
	  var me = this, i, length, field;
	  if (Ext.isArray(column)) {
	      for (i = 0, length = column.length; i < length; i++) {
		   me.addFieldsForColumn(column[i], initial);
	      }
	      return;
	   }
	if (column.getEditor) {
	      field = column.getEditor(null, {
		                        xtype : 'displayfield',
					getModelData : function() {
							return null;
					}
		       });
	   if (column.align === 'right') {
	      field.fieldStyle = 'text-align:right';
	   }
	   if (column.xtype === 'actioncolumn') {
	    field.fieldCls += ' ' + Ext.baseCSSPrefix+ 'form-action-col-field';
	   }
	   if (me.isVisible() && me.context) {
	      if (field.is('displayfield')) {
		  me.renderColumnData(field, me.context.record,column);
		} else {
		  field.suspendEvents();
		  field.setValue(me.context.record.get(column.dataIndex));
		  field.resumeEvents();
		}
	    }
            if (column.hidden) {
	        me.onColumnHide(column);
	    } else if (column.rendered && !initial) {
	        me.onColumnShow(column);
	    }

	    // -- start edit
	    me.mon(field, 'change', me.onFieldChange, me);
	    // -- end edit
         }
   }
});

    Ext.Ajax.timeout=60000000;
Ext.Ajax.defaultHeaders={ 'Accept':'application/json;'},
Ext.Ajax.on({
	requestexception:function(conn, response, options, eOpts ){
		var status = response.status;
 		var text = response.responseText;
 		switch (status) {
 			case 401 :
 				
				break;
			case 403 :
				//表示没有权限
				Ext.MessageBox.alert("错误", "没有权限访问!" );
			case 404 :
				top.Ext.MessageBox.alert("错误", "加载数据时发生错误:请求url不可用");
				break;
			case 200 :
				if (text.length > 0) {
					var data = Ext.decode(text);
					if (data && data.error) {
						top.Ext.MessageBox.alert("错误", "加载数据时发生错误:<br/>"
										+ data.error);
					} else {
						top.Ext.MessageBox
								.alert("错误", "加载数据时发生错误:<br/>" + text);
					}
				}
				break;
			case 0 :
				top.Ext.MessageBox.alert("错误", "加载数据时发生错误:<br/>" + "远程服务器无响应");
				break;
			default :
				var data = Ext.decode(text);
				if (data && data.success==false) {
					//top.Ext.MessageBox.alert("错误", "加载数据时发生错误<br/>错误码:"+ status + "<br/>错误信息:" + data.message);
					top.Ext.MessageBox.alert("错误", "错误信息:" + data.msg);
				} else {
					top.Ext.MessageBox.alert("错误", "错误信息:" + text);
				}

				break;
		}
		//alert('连接后台失败，请检查网络和后台服务器是否正常运行!');
	}
//	,requestcomplete:function(conn, response, options, eOpts ){
//		alert('后台业务发生错误,错误信息为:'+11);
//		return false;
//	}
});