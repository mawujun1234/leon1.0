/** 
 * @class Ext.ux.aboutDialog 
 * @extends Ext.window 
 * @cfg isXXL {Boolean} (Optional) true if about Dialog will look more larger(for some skins/thmem). 
 * @cfg AppName {String} Application Name 
 * @cfg AppVersion {String}  Application Version 
 * @cfg AppText {String}  Text to display about App. 
 * @cfg AppLinkHref {String}  The App. URL to open with new Window. 
 * @cfg AppLinkText {String}  The App. URL to display text,must be started with 'http://' 
 * @cfg showExtJS {Boolean} (Optional) true if show the text about ExtJS 
 * @cfg ExtVersion {String} (Optional) the version of Ext 
 * @cfg icon {String} (Optional) App. icon path ,size in 15x15 is better 
 * @cfg picture {String}  The picture on dialog's left,size in 15x15 is better 
 **/  
Ext.define('Ext.ux.ms.aboutDialog', {
	extend  : 'Ext.window.Window',  
    alias:'widget.aboutBox',  
     cls                : 'x-aboutDialog',
     modal              : true,
     resizable          : false,
     title              : '关于RETP',
     width              : 488,
     height             : Ext.isIE? 350:343,
     isXXL              : false,
     showExtJS          : true,
     ExtVersion         : 'Ext 4.0.7',
     closeAction        : 'hide',
     initComponent      : function(){
        this.html = {
            tag         :  'table',  
            style       :  'background:#f4f4f4',  
            children    : {  
                tag: 'tr',  
                children: [{  
                     tag      : 'td',
                     valign   : 'top',
                     children : {  
                         tag    : 'img', 
                         height : 316,
                         src    : this.picture  
                    }  
                }, {  
                    tag      : 'td',
                    valign   : 'top',  
                    children : [{  
                         tag    : 'div',  
                         cls    : 'aboutDlg',  
                        html    : '<p style="text-align:right;" mce_style="text-align:right;">' +  
		                        '当前版本：' +  
		                        this.AppVersion +  
		                        '<br />Powered by ExtJs UI Engine<br /> ---- version:' + this.ExtVersion +
		                        '</p>' +  
		                        '<p>' +  
		                        '数字化视频监控服务调度系统<br />' +  
		                        this.AppText +  
		                        '</p><br />' +  
		                        '<p>' +  
		                        'RETP采用java技术开发，并以Extjs+Ajax技术做为系统界面及交互手段' +  
		                        '提供安全，美观，灵活高效的解决方案' +  
		                        '</p>'  
                    }, {  
                         tag  : 'div',
                         cls  : 'aboutDlg_btn', 
                         html : Ext.String.format('<br /><button id="us-button" style="width:75%;margin-right:8px;">Upgrde/Support：{0}</button><button id="ok-button">OK</button>', this.AppLinkText)  
                    }]  
                }]  
            }  
        };
        Ext.ux.ms.aboutDialog.superclass.initComponent.apply(this, arguments);  
    },
    // @overried   
    afterRender: function(){  
    	Ext.ux.ms.aboutDialog.superclass.afterRender.apply(this, arguments);  
        
        // 打开网址   
        //this.getEl().child('button:first').on('click', function(){  
    	this.getEl().getById('us-button').on('click', function(){
            window.open(this);  
        }, this.AppLinkHref);  
          
        // this已经是aboutDialog实例，即可被委托了，故this.close即可直接被引用到事件处理器。   
        //this.body.child('button:last').on('click', this.close, this);
    	this.body.getById('ok-button').on('click', this.close, this);
    }  
});