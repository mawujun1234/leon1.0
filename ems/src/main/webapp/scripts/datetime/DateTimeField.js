/**
 * @Description	带时间的日期输入控件
 * @author		侯飞(544735725@qq.com)
 * 只是对 张川(cr10210206@163.com) 的插件做了一个小修改，修正了在点击现在的时候，只有日期会变成今天，时间不变的BUG
 */
Ext.define('Ext.ux.DateTimeField', {
    extend:'Ext.form.field.Date',
    alias: 'widget.DatetimeField',
    requires: ['Ext.ux.DateTimePicker'],

    /**
     * @cfg {String} format
     * The default date format string which can be overriden for localization support. The format must be valid
     * according to {@link Ext.Date#parse}.
     */
    format : "Y-m-d H:i:s",
 
    /**
     * @cfg {String} altFormats
     * Multiple date formats separated by "|" to try when parsing a user input value and it does not match the defined
     * format.
     */
    altFormats : "Y-m-d H:i:s",

    createPicker: function() {
        var me = this,
            format = Ext.String.format;

        //修改picker为自定义picker
        return Ext.create('Ext.ux.DateTimePicker',{
            pickerField: me,
            ownerCt: me.ownerCt,
            renderTo: document.body,
            floating: true,
            hidden: true,
            focusOnShow: true,
            minDate: me.minValue,
            maxDate: me.maxValue,
            disabledDatesRE: me.disabledDatesRE,
            disabledDatesText: me.disabledDatesText,
            disabledDays: me.disabledDays,
            disabledDaysText: me.disabledDaysText,
            format: me.format,
            showToday: me.showToday,
            startDay: me.startDay,
            minText: format(me.minText, me.formatDate(me.minValue)),
            maxText: format(me.maxText, me.formatDate(me.maxValue)),
            listeners: {
                scope: me,
                select: me.onSelect
            },
            keyNavConfig: {
                esc: function() {
                    me.collapse();
                }
            }
        });
    },

    /**
     * @private
     */
    onExpand: function() {
        var value = this.getValue();

        //多传一个参数，从而避免时分秒被忽略。
        this.picker.setValue(Ext.isDate(value) ? value : new Date(), true);
    }
});
