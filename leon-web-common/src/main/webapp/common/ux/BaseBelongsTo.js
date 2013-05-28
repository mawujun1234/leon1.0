Ext.define("Leon.common.ux.BaseBelongsTo",{
	extend:"Ext.data.association.BelongsTo",
	alias: 'association.bbelongsto',
	constructor: function(config) {
		this.callParent(arguments);

        var me             = this,
            ownerProto     = me.ownerModel.prototype,
            associatedName = me.associatedName,
            associatedNameCapitalize=Ext.String.capitalize(associatedName),
            associatedNameUnCapitalize=Ext.String.uncapitalize(associatedName),
            getterName     = me.getterName || 'get' + associatedNameCapitalize,
            setterName     = me.setterName || 'set' + associatedNameCapitalize;

        Ext.applyIf(me, {
            name        : associatedName,
            foreignKey  : associatedNameUnCapitalize + "_id",
            instanceName: associatedName + 'BelongsToInstance',
            associationKey: associatedNameUnCapitalize
        });

        //ownerProto[getterName] = me.createGetter();
        //ownerProto[setterName] = me.createSetter();
	}
});