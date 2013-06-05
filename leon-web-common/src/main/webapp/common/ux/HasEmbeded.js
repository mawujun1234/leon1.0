Ext.define('Leon.common.ux.HasEmbeded', {
    extend: 'Ext.data.association.Association',
    //alternateClassName: 'Ext.data.BelongsToAssociation',
    alias: 'association.hasEmbeded',
    constructor: function(config) {
        this.callParent(arguments);

        var me             = this,
            ownerProto     = me.ownerModel.prototype,
            associatedName = me.associatedName,
            getterName     = me.getterName || 'get' + associatedName,
            setterName     = me.setterName || 'set' + associatedName;

        Ext.applyIf(me, {
            name        : associatedName,
            foreignKey  : associatedName.toLowerCase() + "_id",
            instanceName: associatedName + 'BelongsToInstance',
            associationKey: associatedName.toLowerCase()
        });

        ownerProto[getterName] = me.createGetter();
        ownerProto[setterName] = me.createSetter();
    },
    createSetter: function() {
        var me = this,
            foreignKey = me.foreignKey,
            instanceName = me.instanceName;

        //'this' refers to the Model instance inside this function
        return function(value, options, scope) {
            // If we were passed a record, the value to set is the key of that record.
            var setByRecord = value && value.isModel,
                valueToSet = setByRecord ? value.getId() : value;

            // Setter was passed a record.
            if (setByRecord) {
                this[instanceName] = value;
            }

            // Otherwise, if the key of foreign record !== passed value, delete the cached foreign record
            else if (this[instanceName] instanceof Ext.data.Model && !this.isEqual(this.get(foreignKey), valueToSet)) {
                delete this[instanceName];
            }

            // Set the forign key value
            //this.set(foreignKey, valueToSet);

//            if (Ext.isFunction(options)) {
//                options = {
//                    callback: options,
//                    scope: scope || this
//                };
//            }
//
//            if (Ext.isObject(options)) {
//                return this.save(options);
//            }
        };
    },

    /**
     * @private
     * Returns a getter function to be placed on the owner model's prototype. We cache the loaded instance
     * the first time it is loaded so that subsequent calls to the getter always receive the same reference.
     * @return {Function} The getter function
     */
    createGetter: function() {
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

//            if (options.reload === true || model[instanceName] === undefined) {
//                instance = Ext.ModelManager.create({}, associatedName);
//                instance.set(primaryKey, foreignKeyId);
//
//                if (typeof options == 'function') {
//                    options = {
//                        callback: options,
//                        scope: scope || model
//                    };
//                }
//                
//                // Overwrite the success handler so we can assign the current instance
//                success = options.success;
//                options.success = function(rec){
//                    model[instanceName] = rec;
//                    if (success) {
//                        success.apply(this, arguments);
//                    }
//                };
//
//                associatedModel.load(foreignKeyId, options);
//                // assign temporarily while we wait for data to return
//                model[instanceName] = instance;
//                return instance;
//            } else {
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
//            }
        };
    },

    /**
     * Read associated data
     * @private
     * @param {Ext.data.Model} record The record we're writing to
     * @param {Ext.data.reader.Reader} reader The reader for the associated model
     * @param {Object} associationData The raw associated data
     */
    read: function(record, reader, associationData){
        record[this.instanceName] = reader.read([associationData]).records[0];
    }

});