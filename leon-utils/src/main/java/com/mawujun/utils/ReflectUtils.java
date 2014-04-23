
package com.mawujun.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 反射工具类.
 * 
 * 提供访问私有变量,获取泛型类型Class,提取集合中元素的属性,转换字符串到对象等Util函数.
 * 
 * @author mawujun
 */
public class ReflectUtils {

	private static Logger logger = LoggerFactory.getLogger(ReflectUtils.class);
	//private static Set<Class> baseType=new HashSet<Class>();

	static {
		DateConverter dc = new DateConverter();
		dc.setUseLocaleFormat(true);
		dc.setPatterns(new String[] { "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss" });
		ConvertUtils.register(dc, Date.class);
		
	}

	/**
	 * 是不是包装类型
	 * @param clz
	 * @return
	 */
	public static boolean isWrapClass(Object value) { 
        try { 
           Class clz=value.getClass();
           return ((Class) clz.getField("TYPE").get(null)).isPrimitive();
        } catch (Exception e) { 
            return false; 
        } 
    } 

	public static boolean isWrapClass(Class clz) {
		try {
			return ((Class) clz.getField("TYPE").get(null)).isPrimitive();
		} catch (Exception e) {
			return false;
		}
	}
	
	
	/**
	 * 如果是基本类型和基本类型的包装类，或是String，Date，就返回true
	 * @param clz
	 * @return
	 */
	public static boolean isBaseType(Object value){
		//如果是基本类型就返回true
		if(value instanceof String || value instanceof Date || value.getClass().isPrimitive() || isWrapClass(value.getClass())){
			return true;
		}
		return false;
	}
	
	/**
	 * 如果是基本类型和基本类型的包装类，或是string，Date，就返回true
	 * @param clz
	 * @return
	 */
	public static boolean isBaseType(Class clz){
		//如果是基本类型就返回true
		if(clz == String.class || clz==Date.class || clz==java.sql.Date.class || clz==java.sql.Timestamp.class || clz.isPrimitive() || isWrapClass(clz)){
			return true;
		}
		return false;
	}
	/**
	 * 判断是不是集合类型
	 * @param value
	 * @return
	 */
	public static boolean  isCollectionMap(Object value){
		if(Collection.class.isInstance(value) || Map.class.isInstance(value)){
			return true;
		}
		return false;
	}
	/**
	 * 判断当前类型是否为数组
	 * 
	 * @return true of false
	 */
	public static boolean isArray(Object obj) {
		return obj.getClass().isArray();
	}
	/**
	 * 判断当前类型是否为容器，包括 Map，Collection, 以及数组
	 * 
	 * @return true of false
	 */
	public static boolean isContainer(Object obj) {
		return isColl(obj) || isMap(obj);
	}
	/**
	 * @return 当前类型是否是数组或者集合
	 */
	public static boolean isColl(Object obj) {
		return isArray(obj) || isCollection(obj);
	}
	/**
	 * 判断当前类型是否为 Map
	 * 
	 * @return true of false
	 */
	public static boolean isMap(Object obj) {
		return isOf(obj,Map.class);
	}
	/**
	 * 判断当前类型是否为 Collection
	 * 
	 * @return true of false
	 */
	public static boolean isCollection(Object obj) {
		return isOf(obj,Collection.class);
	}
	/**
	 * @param type
	 *            类型或接口名
	 * @return 当前对象是否为一个类型的子类，或者一个接口的实现类
	 */
	public static boolean isOf(Object obj,Class<?> type) {
		return type.isAssignableFrom(obj.getClass());
	}
	
	/**
	 * 判断当前对象是否为一个类型。精确匹配，即使是父类和接口，也不相等
	 * 
	 * @param type
	 *            类型
	 * @return 是否相等
	 */
	public static boolean is(Object obj,Class<?> type) {
		return null != type && obj.getClass() == type;
	}
	
	
	/**
	 * @return 当前对象是否为字符串
	 */
	public static boolean isString(Object obj) {
		return is(obj,String.class);
	}

	/**
	 * @return 当前对象是否为CharSequence的子类
	 */
	public static boolean isStringLike(Object obj) {
		return CharSequence.class.isAssignableFrom(obj.getClass());
	}

	/**
	 * @return 当前对象是否为字符
	 */
	public static boolean isChar(Object obj) {
		return is(obj,char.class) || is(obj,Character.class);
	}

	/**
	 * @return 当前对象是否为枚举
	 */
	public static boolean isEnum(Object obj) {
		return obj.getClass().isEnum();
	}

	/**
	 * @return 当前对象是否为布尔
	 */
	public static boolean isBoolean(Object obj) {
		return is(obj,boolean.class) || is(obj,Boolean.class);
	}

	/**
	 * @return 当前对象是否为浮点
	 */
	public static boolean isFloat(Object obj) {
		return is(obj,float.class) || is(obj,Float.class);
	}

	/**
	 * @return 当前对象是否为双精度浮点
	 */
	public static boolean isDouble(Object obj) {
		return is(obj,double.class) || is(obj,Double.class);
	}

	/**
	 * @return 当前对象是否为整型
	 */
	public static boolean isInt(Object obj) {
		return is(obj,int.class) || is(obj,Integer.class);
	}

	/**
	 * @return 当前对象是否为整数（包括 int, long, short, byte）
	 */
	public static boolean isIntLike(Object obj) {
		return isInt(obj) || isLong(obj) || isShort(obj) || isByte(obj) || is(obj,BigDecimal.class);
	}
	
	public static boolean isBigDecimal(Object obj) {
		return is(obj,BigDecimal.class);
	}

	/**
	 * @return 当前类型是不是接口
	 */
	public static boolean isInterface(Object obj) {
		return null == obj ? null : obj.getClass().isInterface();
	}

	/**
	 * @return 当前对象是否为小数 (float, dobule)
	 */
	public static boolean isDecimal(Object obj) {
		return isFloat(obj) || isDouble(obj);
	}

	/**
	 * @return 当前对象是否为长整型
	 */
	public static boolean isLong(Object obj) {
		return is(obj,long.class) || is(obj,Long.class);
	}

	/**
	 * @return 当前对象是否为短整型
	 */
	public static boolean isShort(Object obj) {
		return is(obj,short.class) || is(obj,Short.class);
	}

	/**
	 * @return 当前对象是否为字节型
	 */
	public static boolean isByte(Object obj) {
		return is(obj,byte.class) || is(obj,Byte.class);
	}

	/**
	 * @return 当前对象是否在表示日期或时间
	 */
	public static boolean isDateTimeLike(Object obj) {
		Class klass=obj.getClass();
		return Calendar.class.isAssignableFrom(klass)
				|| java.util.Date.class.isAssignableFrom(klass)
				|| java.sql.Date.class.isAssignableFrom(klass)
				|| java.sql.Time.class.isAssignableFrom(klass);
	}
	/**
	 * 如果不是容器，也不是 POJO，那么它必然是个 Obj
	 * 
	 * @return true or false
	 */
	public static boolean isObj(Object obj) {
		return isContainer(obj) || isPojo(obj);
	}
	/**
	 * 判断当前类型是否为POJO。 除了下面的类型，其他均为 POJO
	 * <ul>
	 * <li>原生以及所有包裹类
	 * <li>类字符串
	 * <li>类日期
	 * <li>非容器
	 * </ul>
	 * 
	 * @return true or false
	 */
	public static boolean isPojo(Object obj) {
		Class klass=obj.getClass();
		if (klass.isPrimitive() || isEnum(obj))
			return false;

		if (isStringLike(obj) || isDateTimeLike(obj))
			return false;

		if (isPrimitiveNumber(obj) || isBoolean(obj) || isChar(obj))
			return false;

		return !isContainer(obj);
	}
	/**
	 * @return 当前对象是否为原生的数字类型 （即不包括 boolean 和 char）
	 */
	public static boolean isPrimitiveNumber(Object obj) {
		return isInt(obj) || isLong(obj) || isFloat(obj) || isDouble(obj) || isByte(obj) || isShort(obj);
	}
	/**
	 * 判断一个字符串是不是数字
	 * @author mawujun email:16064988@163.com qq:16064988
	 * @param input
	 * @return
	 */
	public static boolean isNumber(String input){  
		return StringUtils.isNumber(input);
	}


	/**
	 * 判断value实例是不是clz类型，具有继承关系的检查能力，即使value是clz的子类也会返回true
	 * @param clz
	 * @param value
	 * @return
	 */
	public static boolean isInstance(Class clz,Object value){
		return clz.isInstance(value);
	}
	/**
	 * 调用Getter方法.
	 */
	public static Object invokeGetterMethod(Object target, String propertyName) {
		String getterMethodName = "get" + StringUtils.capitalize(propertyName);
		return invokeMethod(target, getterMethodName, new Class[] {}, new Object[] {});
	}

	/**
	 * 调用Setter方法.使用value的Class来查找Setter方法.
	 */
	public static void invokeSetterMethod(Object target, String propertyName, Object value) {
		invokeSetterMethod(target, propertyName, value, null);
	}

	/**
	 * 调用Setter方法.
	 * 
	 * @propertyType 用于查找Setter方法,为空时使用value的Class替代.
	 */
	public static void invokeSetterMethod(Object target, String propertyName, Object value, Class<?> propertyType) {
		Class<?> type = propertyType != null ? propertyType : value.getClass();
		String setterMethodName = "set" + StringUtils.capitalize(propertyName);
		invokeMethod(target, setterMethodName, new Class[] { type }, new Object[] { value });
	}

	/**
	 * 直接读取对象属性值, 无视private/protected修饰符, 不经过getter函数.
	 */
	public static Object getFieldValue(final Object object, final String fieldName) {
		Field field = getDeclaredField(object, fieldName);

		if (field == null) {
			throw new IllegalArgumentException("Could not find field [" + fieldName + "] on target [" + object + "]");
		}

		makeAccessible(field);

		Object result = null;
		try {
			result = field.get(object);
		} catch (IllegalAccessException e) {
			logger.error("不可能抛出的异常{}", e.getMessage());
		}
		return result;
	}

	/**
	 * 直接设置对象属性值, 无视private/protected修饰符, 不经过setter函数.
	 */
	public static void setFieldValue(final Object object, final String fieldName, final Object value) {
		Field field = getDeclaredField(object, fieldName);

		if (field == null) {
			throw new IllegalArgumentException("Could not find field [" + fieldName + "] on target [" + object + "]");
		}

		makeAccessible(field);

		try {
			field.set(object, value);
		} catch (IllegalAccessException e) {
			logger.error("不可能抛出的异常:{}", e.getMessage());
		}
	}

	/**
	 * 直接调用对象方法, 无视private/protected修饰符.
	 */
	public static Object invokeMethod(final Object object, final String methodName, final Class<?>[] parameterTypes,
			final Object[] parameters) {
		Method method = getDeclaredMethod(object, methodName, parameterTypes);
		if (method == null) {
			throw new IllegalArgumentException("Could not find method [" + methodName + "] on target [" + object + "]");
		}

		method.setAccessible(true);

		try {
			return method.invoke(object, parameters);
		} catch (Exception e) {
			throw convertReflectionExceptionToUnchecked(e);
		}
	}

	/**
	 * 循环向上转型, 获取对象的DeclaredField.
	 * 
	 * 如向上转型到Object仍无法找到, 返回null.
	 */
	protected static Field getDeclaredField(final Object object, final String fieldName) {
		AssertUtils.notNull(object, "object不能为空");
		AssertUtils.hasText(fieldName, "fieldName");
		for (Class<?> superClass = object.getClass(); superClass != Object.class; superClass = superClass
				.getSuperclass()) {
			try {
				return superClass.getDeclaredField(fieldName);
			} catch (NoSuchFieldException e) {
				// Field不在当前类定义,继续向上转型
			}
		}
		return null;
	}

	/**
	 * 强行设置Field可访问.
	 */
	protected static void makeAccessible(final Field field) {
		if (!Modifier.isPublic(field.getModifiers()) || !Modifier.isPublic(field.getDeclaringClass().getModifiers())) {
			field.setAccessible(true);
		}
	}

	/**
	 * 循环向上转型,获取对象的DeclaredMethod.
	 * 
	 * 如向上转型到Object仍无法找到, 返回null.
	 */
	protected static Method getDeclaredMethod(Object object, String methodName, Class<?>[] parameterTypes) {
		AssertUtils.notNull(object, "object不能为空");

		for (Class<?> superClass = object.getClass(); superClass != Object.class; superClass = superClass
				.getSuperclass()) {
			try {
				return superClass.getDeclaredMethod(methodName, parameterTypes);
			} catch (NoSuchMethodException e) {
				// Method不在当前类定义,继续向上转型
			}
		}
		return null;
	}

	/**
	 * 通过反射,获得Class定义中声明的父类的泛型参数的类型.
	 * 如无法找到, 返回Object.class.
	 * eg.
	 * public UserDao extends HibernateDao<User>
	 *
	 * @param clazz The class to introspect
	 * @return the first generic declaration, or Object.class if cannot be determined
	 */
	@SuppressWarnings("unchecked")
	public static <T> Class<T> getSuperClassGenricType(final Class clazz) {
		return getSuperClassGenricType(clazz, 0);
	}

	/**
	 * 通过反射,获得定义Class时声明的父类的泛型参数的类型.
	 * 如无法找到, 返回Object.class.
	 * 
	 * 如public UserDao extends HibernateDao<User,Long>
	 *
	 * @param clazz clazz The class to introspect
	 * @param index the Index of the generic ddeclaration,start from 0.
	 * @return the index generic declaration, or Object.class if cannot be determined
	 */
	@SuppressWarnings("unchecked")
	public static Class getSuperClassGenricType(final Class clazz, final int index) {

		Type genType = clazz.getGenericSuperclass();

		if (!(genType instanceof ParameterizedType)) {
			logger.warn(clazz.getSimpleName() + "'s superclass not ParameterizedType");
			return Object.class;
		}

		Type[] params = ((ParameterizedType) genType).getActualTypeArguments();

		if (index >= params.length || index < 0) {
			logger.warn("Index: " + index + ", Size of " + clazz.getSimpleName() + "'s Parameterized Type: "
					+ params.length);
			return Object.class;
		}
		if (!(params[index] instanceof Class)) {
			logger.warn(clazz.getSimpleName() + " not set the actual class on superclass generic parameter");
			return Object.class;
		}

		return (Class) params[index];
	}

	/**
	 * 提取集合中的对象的属性(通过getter函数), 组合成List.
	 * 
	 * @param collection 来源集合.
	 * @param propertyName 要提取的属性名.
	 */
	@SuppressWarnings("unchecked")
	public static List convertElementPropertyToList(final Collection collection, final String propertyName) {
		List list = new ArrayList();

		try {
			for (Object obj : collection) {
				list.add(PropertyUtils.getProperty(obj, propertyName));
			}
		} catch (Exception e) {
			throw convertReflectionExceptionToUnchecked(e);
		}

		return list;
	}

	/**
	 * 提取集合中的对象的属性(通过getter函数), 组合成由分割符分隔的字符串.
	 * 
	 * @param collection 来源集合.
	 * @param propertyName 要提取的属性名.
	 * @param separator 分隔符.
	 */
	@SuppressWarnings("unchecked")
	public static String convertElementPropertyToString(final Collection collection, final String propertyName,
			final String separator) {
		List list = convertElementPropertyToList(collection, propertyName);
		return StringUtils.join(list, separator);
	}

	/**
	 * 转换字符串到相应类型.
	 * 
	 * @param value 待转换的字符串
	 * @param toType 转换目标类型
	 */
	public static Object convertStringToObject(String value, Class<?> toType) {
		try {
			return ConvertUtils.convert(value, toType);
		} catch (Exception e) {
			throw convertReflectionExceptionToUnchecked(e);
		}
	}

	/**
	 * 将反射时的checked exception转换为unchecked exception.
	 */
	public static RuntimeException convertReflectionExceptionToUnchecked(Exception e) {
		if (e instanceof IllegalAccessException || e instanceof IllegalArgumentException
				|| e instanceof NoSuchMethodException) {
			return new IllegalArgumentException("Reflection Exception.", e);
		} else if (e instanceof InvocationTargetException) {
			return new RuntimeException("Reflection Exception.", ((InvocationTargetException) e).getTargetException());
		} else if (e instanceof RuntimeException) {
			return (RuntimeException) e;
		}
		return new RuntimeException("Unexpected Checked Exception.", e);
	}
	/**
	 * 获取所有的字段（private,protected,public），包括父类的
	 * @param clz
	 * @return
	 */
	public static Field[] getAllDeclaredFields(Class clz){
		List<Field> list=new ArrayList<Field>();
		getDeclaredFields(clz,list);
		return list.toArray(new Field[list.size()]);
	}
	
	private static void getDeclaredFields(Class clz, List<Field> list){
		Field[] fields=clz.getDeclaredFields();
		list.addAll(CollectionUtils.arrayToList(fields));
		if(clz.getSuperclass()!=null){
			getDeclaredFields(clz.getSuperclass(),list);
		}
	}
	/**
	 * 获取所有的借口，包括父类实现的借口
	 * @param clz
	 * @return
	 */
	public static Field[] getAllInterfaces(Class clz){
		List<Class> list=new ArrayList<Class>();
		getInterfaces(clz,list);
		return list.toArray(new Field[list.size()]);
	}
	private static void getInterfaces(Class clz, List<Class> list){
		Class[] interfaces=clz.getInterfaces();
		list.addAll(CollectionUtils.arrayToList(interfaces));
		if(clz.getSuperclass()!=null){
			getInterfaces(clz.getSuperclass(),list);
		}
	}
	
	public static boolean containInterface(Class clz,Class interf){
		if(!interf.isInterface()){
			return false;
		}
		List<Class> list=new ArrayList<Class>();
		getInterfaces(clz,list);
		if(list.contains(interf)){
			return true;
		}
		return false;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * Attempt to find a {@link Field field} on the supplied {@link Class} with
	 * the supplied <code>name</code>. Searches all superclasses up to {@link Object}.
	 * @param clazz the class to introspect
	 * @param name the name of the field
	 * @return the corresponding Field object, or <code>null</code> if not found
	 */
	public static Field findField(Class clazz, String name) {
		return findField(clazz, name, null);
	}

	/**
	 * Attempt to find a {@link Field field} on the supplied {@link Class} with
	 * the supplied <code>name</code> and/or {@link Class type}. Searches all
	 * superclasses up to {@link Object}.
	 * @param clazz the class to introspect
	 * @param name the name of the field (may be <code>null</code> if type is specified)
	 * @param type the type of the field (may be <code>null</code> if name is specified)
	 * @return the corresponding Field object, or <code>null</code> if not found
	 */
	public static Field findField(Class clazz, String name, Class type) {
		AssertUtils.notNull(clazz, "Class must not be null");
		AssertUtils.isTrue(name != null || type != null, "Either name or type of the field must be specified");
		Class searchType = clazz;
		while (!Object.class.equals(searchType) && searchType != null) {
			Field[] fields = searchType.getDeclaredFields();
			for (int i = 0; i < fields.length; i++) {
				Field field = fields[i];
				if ((name == null || name.equals(field.getName()))
						&& (type == null || type.equals(field.getType()))) {
					return field;
				}
			}
			searchType = searchType.getSuperclass();
		}
		return null;
	}

	/**
	 * Set the field represented by the supplied {@link Field field object} on
	 * the specified {@link Object target object} to the specified
	 * <code>value</code>. In accordance with
	 * {@link Field#set(Object, Object)} semantics, the new value is
	 * automatically unwrapped if the underlying field has a primitive type.
	 * <p>Thrown exceptions are handled via a call to
	 * {@link #handleReflectionException(Exception)}.
	 * @param field the field to set
	 * @param target the target object on which to set the field
	 * @param value the value to set; may be <code>null</code>
	 */
	public static void setField(Field field, Object target, Object value) {
		try {
			field.set(target, value);
		}
		catch (IllegalAccessException ex) {
			handleReflectionException(ex);
			throw new IllegalStateException(
					"Unexpected reflection exception - " + ex.getClass().getName() + ": " + ex.getMessage());
		}
	}

	/**
	 * Get the field represented by the supplied {@link Field field object} on
	 * the specified {@link Object target object}. In accordance with
	 * {@link Field#get(Object)} semantics, the returned value is
	 * automatically wrapped if the underlying field has a primitive type.
	 * <p>Thrown exceptions are handled via a call to
	 * {@link #handleReflectionException(Exception)}.
	 * @param field the field to get
	 * @param target the target object from which to get the field
	 * @return the field's current value
	 */
	public static Object getField(Field field, Object target) {
		try {
			return field.get(target);
		}
		catch (IllegalAccessException ex) {
			handleReflectionException(ex);
			throw new IllegalStateException(
					"Unexpected reflection exception - " + ex.getClass().getName() + ": " + ex.getMessage());
		}
	}

	/**
	 * Attempt to find a {@link Method} on the supplied class with the supplied name
	 * and no parameters. Searches all superclasses up to <code>Object</code>.
	 * <p>Returns <code>null</code> if no {@link Method} can be found.
	 * @param clazz the class to introspect
	 * @param name the name of the method
	 * @return the Method object, or <code>null</code> if none found
	 */
	public static Method getMethod(Class clazz, String name) {
		return getMethod(clazz, name, new Class[0]);
	}

	/**
	 * Attempt to find a {@link Method} on the supplied class with the supplied name
	 * and parameter types. Searches all superclasses up to <code>Object</code>.
	 * <p>Returns <code>null</code> if no {@link Method} can be found.
	 * @param clazz the class to introspect
	 * @param name the name of the method
	 * @param paramTypes the parameter types of the method
	 * (may be <code>null</code> to indicate any signature)
	 * @return the Method object, or <code>null</code> if none found
	 */
	public static Method getMethod(Class clazz, String name, Class[] paramTypes) {
		AssertUtils.notNull(clazz, "Class must not be null");
		AssertUtils.notNull(name, "Method name must not be null");
		Class searchType = clazz;
		while (!Object.class.equals(searchType) && searchType != null) {
			Method[] methods = (searchType.isInterface() ? searchType.getMethods() : searchType.getDeclaredMethods());
			for (int i = 0; i < methods.length; i++) {
				Method method = methods[i];
				if (name.equals(method.getName()) &&
						(paramTypes == null || Arrays.equals(paramTypes, method.getParameterTypes()))) {
					return method;
				}
			}
			searchType = searchType.getSuperclass();
		}
		return null;
	}

	/**
	 * Invoke the specified {@link Method} against the supplied target object
	 * with no arguments. The target object can be <code>null</code> when
	 * invoking a static {@link Method}.
	 * <p>Thrown exceptions are handled via a call to {@link #handleReflectionException}.
	 * @param method the method to invoke
	 * @param target the target object to invoke the method on
	 * @return the invocation result, if any
	 * @see #invokeMethod(java.lang.reflect.Method, Object, Object[])
	 */
	public static Object invokeMethod(Method method, Object target) {
		return invokeMethod(method, target, null);
	}

	/**
	 * Invoke the specified {@link Method} against the supplied target object
	 * with the supplied arguments. The target object can be <code>null</code>
	 * when invoking a static {@link Method}.
	 * <p>Thrown exceptions are handled via a call to {@link #handleReflectionException}.
	 * @param method the method to invoke
	 * @param target the target object to invoke the method on
	 * @param args the invocation arguments (may be <code>null</code>)
	 * @return the invocation result, if any
	 */
	public static Object invokeMethod(Method method, Object target, Object[] args) {
		try {
			return method.invoke(target, args);
		}
		catch (Exception ex) {
			handleReflectionException(ex);
		}
		throw new IllegalStateException("Should never get here");
	}

	/**
	 * Invoke the specified JDBC API {@link Method} against the supplied
	 * target object with no arguments.
	 * @param method the method to invoke
	 * @param target the target object to invoke the method on
	 * @return the invocation result, if any
	 * @throws SQLException the JDBC API SQLException to rethrow (if any)
	 * @see #invokeJdbcMethod(java.lang.reflect.Method, Object, Object[])
	 */
	public static Object invokeJdbcMethod(Method method, Object target) throws SQLException {
		return invokeJdbcMethod(method, target, null);
	}

	/**
	 * Invoke the specified JDBC API {@link Method} against the supplied
	 * target object with the supplied arguments.
	 * @param method the method to invoke
	 * @param target the target object to invoke the method on
	 * @param args the invocation arguments (may be <code>null</code>)
	 * @return the invocation result, if any
	 * @throws SQLException the JDBC API SQLException to rethrow (if any)
	 * @see #invokeMethod(java.lang.reflect.Method, Object, Object[])
	 */
	public static Object invokeJdbcMethod(Method method, Object target, Object[] args) throws SQLException {
		try {
			return method.invoke(target, args);
		}
		catch (IllegalAccessException ex) {
			handleReflectionException(ex);
		}
		catch (InvocationTargetException ex) {
			if (ex.getTargetException() instanceof SQLException) {
				throw (SQLException) ex.getTargetException();
			}
			handleInvocationTargetException(ex);
		}
		throw new IllegalStateException("Should never get here");
	}

	/**
	 * Handle the given reflection exception. Should only be called if
	 * no checked exception is expected to be thrown by the target method.
	 * <p>Throws the underlying RuntimeException or Error in case of an
	 * InvocationTargetException with such a root cause. Throws an
	 * IllegalStateException with an appropriate message else.
	 * @param ex the reflection exception to handle
	 */
	public static void handleReflectionException(Exception ex) {
		if (ex instanceof NoSuchMethodException) {
			throw new IllegalStateException("Method not found: " + ex.getMessage());
		}
		if (ex instanceof IllegalAccessException) {
			throw new IllegalStateException("Could not access method: " + ex.getMessage());
		}
		if (ex instanceof InvocationTargetException) {
			handleInvocationTargetException((InvocationTargetException) ex);
		}
		if (ex instanceof RuntimeException) {
			throw (RuntimeException) ex;
		}
		handleUnexpectedException(ex);
	}

	/**
	 * Handle the given invocation target exception. Should only be called if
	 * no checked exception is expected to be thrown by the target method.
	 * <p>Throws the underlying RuntimeException or Error in case of such
	 * a root cause. Throws an IllegalStateException else.
	 * @param ex the invocation target exception to handle
	 */
	public static void handleInvocationTargetException(InvocationTargetException ex) {
		rethrowRuntimeException(ex.getTargetException());
	}

	/**
	 * Rethrow the given {@link Throwable exception}, which is presumably the
	 * <em>target exception</em> of an {@link InvocationTargetException}.
	 * Should only be called if no checked exception is expected to be thrown by
	 * the target method.
	 * <p>Rethrows the underlying exception cast to an {@link RuntimeException}
	 * or {@link Error} if appropriate; otherwise, throws an
	 * {@link IllegalStateException}.
	 * @param ex the exception to rethrow
	 * @throws RuntimeException the rethrown exception
	 */
	public static void rethrowRuntimeException(Throwable ex) {
		if (ex instanceof RuntimeException) {
			throw (RuntimeException) ex;
		}
		if (ex instanceof Error) {
			throw (Error) ex;
		}
		handleUnexpectedException(ex);
	}

	/**
	 * Rethrow the given {@link Throwable exception}, which is presumably the
	 * <em>target exception</em> of an {@link InvocationTargetException}.
	 * Should only be called if no checked exception is expected to be thrown by
	 * the target method.
	 * <p>Rethrows the underlying exception cast to an {@link Exception} or
	 * {@link Error} if appropriate; otherwise, throws an
	 * {@link IllegalStateException}.
	 * @param ex the exception to rethrow
	 * @throws Exception the rethrown exception (in case of a checked exception)
	 */
	public static void rethrowException(Throwable ex) throws Exception {
		if (ex instanceof Exception) {
			throw (Exception) ex;
		}
		if (ex instanceof Error) {
			throw (Error) ex;
		}
		handleUnexpectedException(ex);
	}

	/**
	 * Throws an IllegalStateException with the given exception as root cause.
	 * @param ex the unexpected exception
	 */
	private static void handleUnexpectedException(Throwable ex) {
		// Needs to avoid the chained constructor for JDK 1.4 compatibility.
		IllegalStateException isex = new IllegalStateException("Unexpected exception thrown");
		isex.initCause(ex);
		throw isex;
	}

	/**
	 * Determine whether the given method explicitly declares the given exception
	 * or one of its superclasses, which means that an exception of that type
	 * can be propagated as-is within a reflective invocation.
	 * @param method the declaring method
	 * @param exceptionType the exception to throw
	 * @return <code>true</code> if the exception can be thrown as-is;
	 * <code>false</code> if it needs to be wrapped
	 */
	public static boolean declaresException(Method method, Class exceptionType) {
		AssertUtils.notNull(method, "Method must not be null");
		Class[] declaredExceptions = method.getExceptionTypes();
		for (int i = 0; i < declaredExceptions.length; i++) {
			Class declaredException = declaredExceptions[i];
			if (declaredException.isAssignableFrom(exceptionType)) {
				return true;
			}
		}
		return false;
	}


	/**
	 * Determine whether the given field is a "public static final" constant.
	 * @param field the field to check
	 */
	public static boolean isPublicStaticFinal(Field field) {
		int modifiers = field.getModifiers();
		return (Modifier.isPublic(modifiers) && Modifier.isStatic(modifiers) && Modifier.isFinal(modifiers));
	}

//	/**
//	 * Make the given field accessible, explicitly setting it accessible if necessary.
//	 * The <code>setAccessible(true)</code> method is only called when actually necessary,
//	 * to avoid unnecessary conflicts with a JVM SecurityManager (if active).
//	 * @param field the field to make accessible
//	 * @see java.lang.reflect.Field#setAccessible
//	 */
//	public static void makeAccessible(Field field) {
//		if (!Modifier.isPublic(field.getModifiers()) ||
//				!Modifier.isPublic(field.getDeclaringClass().getModifiers())) {
//			field.setAccessible(true);
//		}
//	}

	/**
	 * Make the given method accessible, explicitly setting it accessible if necessary.
	 * The <code>setAccessible(true)</code> method is only called when actually necessary,
	 * to avoid unnecessary conflicts with a JVM SecurityManager (if active).
	 * @param method the method to make accessible
	 * @see java.lang.reflect.Method#setAccessible
	 */
	public static void makeAccessible(Method method) {
		if (!Modifier.isPublic(method.getModifiers()) ||
				!Modifier.isPublic(method.getDeclaringClass().getModifiers())) {
			method.setAccessible(true);
		}
	}

	/**
	 * Make the given constructor accessible, explicitly setting it accessible if necessary.
	 * The <code>setAccessible(true)</code> method is only called when actually necessary,
	 * to avoid unnecessary conflicts with a JVM SecurityManager (if active).
	 * @param ctor the constructor to make accessible
	 * @see java.lang.reflect.Constructor#setAccessible
	 */
	public static void makeAccessible(Constructor ctor) {
		if (!Modifier.isPublic(ctor.getModifiers()) ||
				!Modifier.isPublic(ctor.getDeclaringClass().getModifiers())) {
			ctor.setAccessible(true);
		}
	}


	/**
	 * Perform the given callback operation on all matching methods of the
	 * given class and superclasses.
	 * <p>The same named method occurring on subclass and superclass will
	 * appear twice, unless excluded by a {@link MethodFilter}.
	 * @param targetClass class to start looking at
	 * @param mc the callback to invoke for each method
	 * @see #doWithMethods(Class, MethodCallback, MethodFilter)
	 */
	public static void doWithMethods(Class targetClass, MethodCallback mc) throws IllegalArgumentException {
		doWithMethods(targetClass, mc, null);
	}

	/**
	 * Perform the given callback operation on all matching methods of the
	 * given class and superclasses.
	 * <p>The same named method occurring on subclass and superclass will
	 * appear twice, unless excluded by the specified {@link MethodFilter}.
	 * @param targetClass class to start looking at
	 * @param mc the callback to invoke for each method
	 * @param mf the filter that determines the methods to apply the callback to
	 */
	public static void doWithMethods(Class targetClass, MethodCallback mc, MethodFilter mf)
			throws IllegalArgumentException {

		// Keep backing up the inheritance hierarchy.
		do {
			Method[] methods = targetClass.getDeclaredMethods();
			for (int i = 0; i < methods.length; i++) {
				if (mf != null && !mf.matches(methods[i])) {
					continue;
				}
				try {
					mc.doWith(methods[i]);
				}
				catch (IllegalAccessException ex) {
					throw new IllegalStateException(
							"Shouldn't be illegal to access method '" + methods[i].getName() + "': " + ex);
				}
			}
			targetClass = targetClass.getSuperclass();
		}
		while (targetClass != null);
	}

	/**
	 * Get all declared methods on the leaf class and all superclasses.
	 * Leaf class methods are included first.
	 */
	public static Method[] getAllDeclaredMethods(Class leafClass) throws IllegalArgumentException {
		final List list = new ArrayList(32);
		doWithMethods(leafClass, new MethodCallback() {
			public void doWith(Method method) {
				list.add(method);
			}
		});
		return (Method[]) list.toArray(new Method[list.size()]);
	}


	/**
	 * Invoke the given callback on all fields in the target class,
	 * going up the class hierarchy to get all declared fields.
	 * @param targetClass the target class to analyze
	 * @param fc the callback to invoke for each field
	 */
	public static void doWithFields(Class targetClass, FieldCallback fc) throws IllegalArgumentException {
		doWithFields(targetClass, fc, null);
	}

	/**
	 * Invoke the given callback on all fields in the target class,
	 * going up the class hierarchy to get all declared fields.
	 * @param targetClass the target class to analyze
	 * @param fc the callback to invoke for each field
	 * @param ff the filter that determines the fields to apply the callback to
	 */
	public static void doWithFields(Class targetClass, FieldCallback fc, FieldFilter ff)
			throws IllegalArgumentException {

		// Keep backing up the inheritance hierarchy.
		do {
			// Copy each field declared on this class unless it's static or file.
			Field[] fields = targetClass.getDeclaredFields();
			for (int i = 0; i < fields.length; i++) {
				// Skip static and final fields.
				if (ff != null && !ff.matches(fields[i])) {
					continue;
				}
				try {
					fc.doWith(fields[i]);
				}
				catch (IllegalAccessException ex) {
					throw new IllegalStateException(
							"Shouldn't be illegal to access field '" + fields[i].getName() + "': " + ex);
				}
			}
			targetClass = targetClass.getSuperclass();
		}
		while (targetClass != null && targetClass != Object.class);
	}

	/**
	 * Given the source object and the destination, which must be the same class
	 * or a subclass, copy all fields, including inherited fields. Designed to
	 * work on objects with public no-arg constructors.
	 * @throws IllegalArgumentException if the arguments are incompatible
	 */
	public static void shallowCopyFieldState(final Object src, final Object dest) throws IllegalArgumentException {
		if (src == null) {
			throw new IllegalArgumentException("Source for field copy cannot be null");
		}
		if (dest == null) {
			throw new IllegalArgumentException("Destination for field copy cannot be null");
		}
		if (!src.getClass().isAssignableFrom(dest.getClass())) {
			throw new IllegalArgumentException("Destination class [" + dest.getClass().getName() +
					"] must be same or subclass as source class [" + src.getClass().getName() + "]");
		}
		doWithFields(src.getClass(), new FieldCallback() {
			public void doWith(Field field) throws IllegalArgumentException, IllegalAccessException {
				makeAccessible(field);
				Object srcValue = field.get(src);
				field.set(dest, srcValue);
			}
		}, COPYABLE_FIELDS);
	}


	/**
	 * Action to take on each method.
	 */
	public static interface MethodCallback {

		/**
		 * Perform an operation using the given method.
		 * @param method the method to operate on
		 */
		void doWith(Method method) throws IllegalArgumentException, IllegalAccessException;
	}


	/**
	 * Callback optionally used to method fields to be operated on by a method callback.
	 */
	public static interface MethodFilter {

		/**
		 * Determine whether the given method matches.
		 * @param method the method to check
		 */
		boolean matches(Method method);
	}


	/**
	 * Callback interface invoked on each field in the hierarchy.
	 */
	public static interface FieldCallback {

		/**
		 * Perform an operation using the given field.
		 * @param field the field to operate on
		 */
		void doWith(Field field) throws IllegalArgumentException, IllegalAccessException;
	}


	/**
	 * Callback optionally used to filter fields to be operated on by a field callback.
	 */
	public static interface FieldFilter {

		/**
		 * Determine whether the given field matches.
		 * @param field the field to check
		 */
		boolean matches(Field field);
	}


	/**
	 * Pre-built FieldFilter that matches all non-static, non-final fields.
	 */
	public static FieldFilter COPYABLE_FIELDS = new FieldFilter() {
		public boolean matches(Field field) {
			return !(Modifier.isStatic(field.getModifiers()) ||
					Modifier.isFinal(field.getModifiers()));
		}
	};
}
