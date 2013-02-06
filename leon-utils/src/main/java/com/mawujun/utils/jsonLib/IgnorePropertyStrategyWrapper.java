package com.mawujun.utils.jsonLib;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.PropertySetStrategy;

/**
 * jsonlib json-->object,忽略object中没有的属性 
 * 使用方法
 * JsonConfig cfg = new JsonConfig();
					cfg.setPropertySetStrategy(new PropertyStrategyWrapper(PropertySetStrategy.DEFAULT));
					cfg.setRootClass(clazz);		
					Object o= JSONObject.toBean(jsonObject,cfg);
 * http://envy2002.iteye.com/blog/1682738
 * @author mawujun qq:16064988 e-mail:16064988@qq.com 承接各种项目
 *
 */
public class IgnorePropertyStrategyWrapper extends PropertySetStrategy {
    private PropertySetStrategy original;
    public IgnorePropertyStrategyWrapper(PropertySetStrategy original) {
       this.original = original;
   }
    @Override
   public void setProperty(Object o, String string, Object o1) throws JSONException {
       try {
           original.setProperty(o, string, o1);
       } catch (Exception ex) {
           //ignore
       }
   }
}
