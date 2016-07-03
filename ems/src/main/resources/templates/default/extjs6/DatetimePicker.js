Ext.define('Platform.utils.DatetimePicker', {
    extend: 'Ext.picker.Date',
    alias: 'widget.datetimepicker',
    requires: [
        'Ext.picker.Date',
        'Ext.slider.Single',
        'Ext.form.field.Time',
        'Ext.form.Label'
    ],
    todayText: 'Current Date',

    initComponent: function() {
        var me = this,
            dtAux = me.value ? new Date(me.value) : new Date();

        me.selectedCls = me.baseCls + '-selected';
        me.disabledCellCls = me.baseCls + '-disabled';
        me.prevCls = me.baseCls + '-prevday';
        me.activeCls = me.baseCls + '-active';
        me.cellCls = me.baseCls + '-cell';
        me.nextCls = me.baseCls + '-prevday';
        me.todayCls = me.baseCls + '-today';
        //dtAux.setSeconds(0);

        if (!me.format) {
            me.format = Ext.Date.defaultFormat;
        }
        if (!me.dayNames) {
            me.dayNames = Ext.Date.dayNames;
        }
        me.dayNames = me.dayNames.slice(me.startDay).concat(me.dayNames.slice(0, me.startDay));

        me.callParent();
        me.value = new Date(dtAux);

        Ext.apply(me, {
            timeFormat: ~me.format.indexOf("h") ? 'h' : 'H'

        });

        me.initDisabledDays();
        me.hourSlider = new Ext.form.field.Number({
            value: 0,
            minValue: 0,
            maxValue: 23,
            width:58,
            padding:'5 0',
            listeners: {
                change: me.changeTimeValue,
                scope: me
            }
        });

        me.minuteSlider = new Ext.form.field.Number({
            value: 0,
            padding:'5 0',
            minValue: 0,
            maxValue: 59,
            width:58,
            listeners: {
                change: me.changeTimeValue,
                scope: me
            }
        });
        me.secondSlider = new Ext.form.field.Number({
            value: 0,
            padding:'5 0',
            minValue: 0,
            maxValue: 59,
            width:58,
            listeners: {
                change: me.changeTimeValue,
                scope: me
            }
        });

        me.submitBtn = new Ext.button.Button ({
            text: '确定',
            handler: me.submitHandler,
            scope: me
        });
    },
    afterRender: function() {
        var me = this,
            el = me.el;
        this.callParent();
        me.timePicker = Ext.create('Ext.menu.Menu', {
            layout: {
                type: 'hbox',
                align: 'stretch'
            },
            ownerCmp: me,
            //alwaysOnTop:true,
            border: false,
            defaults: {
                flex: 1
            },
            height: 70,
            floating: true,
            dockedItems: [{
                xtype: 'toolbar',
                dock: 'bottom',
                ui: 'footer',
                items: [
                    '->', me.submitBtn,
                    '->'
                ]
            }],
            items: [
                {
                    xtype:'container',
                    layout: {
                        type: 'hbox',
                        align: 'middle ',
                        pack: 'center'
                    },
                    width: '100%',
                    items:[ me.hourSlider,{html:':',padding:'5 5 5 0'}, me.minuteSlider,{html:':',padding:'5 5 5 0'},me.secondSlider]
                }
            ]

        });
        //me.on('move',me.showTimePicker);
    },
    submitHandler: function () {
        var me = this;
        me.ownerCmp.collapse();
    },

    changeTimeValue: function(slider, e, eOpts) {
        var me = this,
            //label = me.timePicker.down('label'),
            hourPrefix = '',
            minutePrefix = me.minuteSlider.getValue() < 10 ? '0' : '',
            timeSufix = '',
            hourDisplay = me.hourSlider.getValue(),
            auxDate = new Date();

        if (me.timeFormat == 'h') {
            timeSufix = me.hourSlider.getValue() < 12 ? ' AM' : ' PM';
            hourDisplay = me.hourSlider.getValue() < 13 ? hourDisplay : hourDisplay - 12;
            hourDisplay = hourDisplay || '12';
        }
        hourPrefix = hourDisplay < 10 ? '0' : '';

        //label.setText(hourPrefix + hourDisplay + ':' + minutePrefix + me.minuteSlider.getValue() + timeSufix);

        if (me.pickerField && me.pickerField.getValue()) {
            me.pickerField.setValue(new Date(me.pickerField.getValue().setHours(me.hourSlider.getValue(), me.minuteSlider.getValue(),me.secondSlider.getValue())));
        }else{
            me.pickerField.setValue(new Date((new Date).setHours(me.hourSlider.getValue(), me.minuteSlider.getValue(),me.secondSlider.getValue())));
        }
    },
    onShow: function() {
        var me = this;
        me.showTimePicker();
        me.callParent();
    },

    showTimePicker: function() {
        var me = this,
            el = me.el,
            timePicker = me.timePicker;
        Ext.defer(function() {
            var body = Ext.getBody(),
                bodyWidth = body.getViewSize().width,
                bodyHeight = body.getViewSize().height,
                alignTo = 'bl',
                yPos = 0,
                backgroundColor, toolbar;
            var top = el.getTop(),height = me.timePicker.height,
                bottom = bodyHeight-el.getBottom(),
                offsetY = el.getOffsetsTo(me.ownerCmp)[1];
            if(offsetY< 0) el.setTop(top - height);
            if(bottom < height && offsetY > 0) el.setTop(top-height-el.getHeight()-me.ownerCmp.getHeight());
                me.timePicker.setWidth(el.getWidth());
                me.timePicker.showBy(me, alignTo, [ 0, yPos]);

            toolbar = me.timePicker.down('toolbar').getEl();
            backgroundColor = toolbar.getStyle('background-color');
            if (backgroundColor == 'transparent') {
                toolbar.setStyle('background-color', toolbar.getStyle('border-color'));
            }
        }, 1);
    },

    beforeDestroy: function() {
        var me = this;

        if (me.rendered) {
            Ext.destroy(
                me.timePicker,
                me.minuteSlider,
                me.hourSlider
            );
        }
        me.callParent();
    },
    setValue: function(value) {
        //value.setSeconds(0);
        this.value = new Date(value);
        return this.update(this.value);
    },
    selectToday: function() {
        var me = this,
            btn = me.todayBtn,
            handler = me.handler
        auxDate = new Date;

        if (btn && !btn.disabled) {
            me.setValue(new Date(auxDate));
            //me.fireEvent('select', me, me.value);
            if (handler) {
                handler.call(me.scope || me, me, me.value);
            }
            me.onSelect();
        }
        return me;
    },
    handleDateClick: function(e, t) {
        var me = this,
            handler = me.handler,
            hourSet = me.hourSlider.getValue(),
            minuteSet = me.minuteSlider.getValue(),
            secondSet = me.secondSlider.getValue(),
            auxDate = new Date(t.dateValue);
        if (!me.disabled && t.dateValue && !Ext.fly(t.parentNode).hasCls(me.disabledCellCls)) {
            me.doCancelFocus = me.focusOnSelect === false;
            auxDate.setHours(hourSet, minuteSet, secondSet);
            me.setValue(new Date(auxDate));
            delete me.doCancelFocus;
            me.fireEvent('select', me, me.value);
            if (handler) {
                handler.call(me.scope || me, me, me.value);
            }

            me.onSelect();
        }
    },
    selectedUpdate: function(date) {
        var me = this,
            dateOnly = Ext.Date.clearTime(date, true),
            currentDate = (me.pickerField && me.pickerField.getValue()) || new Date();

        this.callParent([dateOnly]);

        if (currentDate) {
            Ext.defer(function() {
                me.hourSlider.setValue(currentDate.getHours());
                me.minuteSlider.setValue(currentDate.getMinutes());
                me.secondSlider.setValue(currentDate.getSeconds());
            }, 10);

        }

    },
    fullUpdate: function(date) {
        var me = this,
            dateOnly = Ext.Date.clearTime(date, true),
            currentDate = (me.pickerField && me.pickerField.getValue()) || new Date();

        this.callParent([dateOnly]);

        if (currentDate) {
            Ext.defer(function() {
                me.hourSlider.setValue(currentDate.getHours());
                me.minuteSlider.setValue(currentDate.getMinutes());
                me.secondSlider.setValue(currentDate.getSeconds());
            }, 10);

        }

    }
});