//    Ext.define('equipment',{
//        extend: 'Ext.data.Model',
//        fields: [
//            {name: 'ecode', mapping: 'ecode'},
//            {name: 'etype', mapping: 'etype'},
//            {name: 'typename', mapping: 'typename'},
//            {name: 'esubtype', mapping: 'esubtype'},
//            {name: 'subtypename', mapping: 'subtypename'},
//            {name: 'sid', mapping: 'sid'},
//            {name: 'sname', mapping: 'sname'},
//            {name: 'nums', mapping: 'nums'},
//            {name: 'price', mapping: 'price'},
//            {name: 'totalprice', mapping: 'totalprice'},
//            {name: 'stid', mapping: 'stid'},
//            {name: 'stock', mapping: 'stock'},
//            {name: 'style', mapping: 'style'},
//            {name: 'stmemo', mapping: 'stmemo'},
//            {name: 'flag', mapping: 'flag'},
//            {name: 'estatus', mapping: 'estatus'},
//            {name: 'did', mapping: 'did'}
//        ]
//    });
    
    Ext.define('equipment',{
        extend: 'Ext.data.Model',
        fields: [
            {name: 'batchid', mapping: 'batchid'},
            {name: 'cpid', mapping: 'cpid'},
            {name: 'datebuying', mapping: 'datebuying'},
            {name: 'did', mapping: 'did'},
            {name: 'ecode', mapping: 'ecode'},
            {name: 'ename', mapping: 'ename'},
            {name: 'estatus', mapping: 'estatus'},
            {name: 'esubtype', mapping: 'esubtype'},
            {name: 'etype', mapping: 'etype'},
            {name: 'fstJid', mapping: 'fstJid'},
            {name: 'iid', mapping: 'iid'},
            {name: 'lstJid', mapping: 'lstJid'},
            {name: 'lstMjid', mapping: 'lstMjid'},
            {name: 'lstOid', mapping: 'lstOid'},
            {name: 'lstRid', mapping: 'lstRid'},
            {name: 'memo', mapping: 'memo'},
            {name: 'nums', mapping: 'nums'},
            {name: 'price', mapping: 'price',type:'int'},
            {name: 'sid', mapping: 'sid'},
            {name: 'sname', mapping: 'sname'},
            {name: 'stid', mapping: 'stid'},
            {name: 'stmemo', mapping: 'stmemo'},
            {name: 'stock', mapping: 'stock'},
            {name: 'style', mapping: 'style'},
            {name: 'subtypename', mapping: 'subtypename'},
            {name: 'timesMaintained', mapping: 'timesMaintained'},
            {name: 'timesOutsourcing', mapping: 'timesOutsourcing'},
            {name: 'timesRepairing', mapping: 'timesRepairing'},
            {name: 'typename', mapping: 'typename'},
            {name: 'uname', mapping: 'uname'},
            {name: 'unitid', mapping: 'unitid'},
            {name:'rucid',mapping:'rucid'},
            {name:'rucname',mapping:'rucname'}
        ]
    });
    
    //var equipstatus={USING:0,NEW_STOCK:1,BACK_OUTSOURCE:2,BACK_REPAIR:3,TOREPAIRING:4,BACK_OTHER:5,OUT_SOURCING:12,REPAIRING:13,DISCARD:14,OTHER:255},estatus=0;