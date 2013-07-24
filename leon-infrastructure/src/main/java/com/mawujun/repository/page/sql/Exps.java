package com.mawujun.repository.page.sql;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Iterator;

import com.mawujun.utils.BeanPropertiesCopy;
import com.mawujun.utils.ReflectionUtils;
import com.mawujun.utils.StringUtils;



/**
 * 表达式的帮助函数
 * 
 * @author 
 */
public abstract class Exps {

	public static SqlExpressionGroup begin() {
		return new SqlExpressionGroup();
	}

	public static Like like(String name, String value) {
		return Like.create(name, value, true);
	}

	public static Like like(String name, String value, boolean ignoreCase) {
		return Like.create(name, value, ignoreCase);
	}

	public static IsNull isNull(String name) {
		return new IsNull(name);
	}

	public static SimpleExpression eq(String name, Object val) {
		return new SimpleExpression(name, "=", val);
	}

	public static SimpleExpression gt(String name, long val) {
		return new SimpleExpression(name, ">", val);
	}

	public static SimpleExpression lt(String name, long val) {
		return new SimpleExpression(name, "<", val);
	}

	public static SimpleExpression gte(String name, long val) {
		return new SimpleExpression(name, ">=", val);
	}

	public static SimpleExpression lte(String name, long val) {
		return new SimpleExpression(name, "<=", val);
	}

	public static IntRange inInt(String name, int... ids) {
		return new IntRange(name, ids);
	}

	public static LongRange inLong(String name, long... ids) {
		return new LongRange(name, ids);
	}

	public static NameRange inStr(String name, String... names) {
		return new NameRange(name, names);
	}

	public static SqlRange inSql(String name, String subSql, Object... args) {
		return new SqlRange(name, subSql, args);
	}

	public static SqlExpression create(String name, String op, Object value) {
		op = StringUtils.trim(op.toUpperCase());

		// NULL
		if (null == value) {
			SqlExpression re;
			// IS NULL
			if ("=".equals(op) || "IS".equals(op) || "NOT IS".equals(op) || "IS NOT".equals(op)
					|| "IS NOT NULL".equals(op) || "IS NULL".equals(op)) {
				re = isNull(name);
			}
			// !!!
			else {
				throw new RuntimeException("null can only use 'IS' or 'NOT IS'");
			}
			//return re.setNot(op.startsWith("NOT") || op.endsWith("NOT") || op.contains("NOT"));
			return re.setNot(op.contains("NOT"));
		}
		// IN
		else if ("IN".equals(op) || "NOT IN".equals(op)) {
			Class<?> type = value.getClass();
			SqlExpression re;
			// 数组
			if (type.isArray()) {
				re = _evalRange(name, value);
			}
			// 集合
			else if (Collection.class.isAssignableFrom(type)) {
				Object first = first(value);
				if (null == first)
					return null;
				re = _evalRange(name, value);
			}
			// Sql Range
			else {
				re = inSql(name, value.toString());
			}
			return re.setNot(op.startsWith("NOT"));
		}
		// LIKE || IS
		else if ("LIKE".equals(op) || "NOT LIKE".equals(op)) {
			String v = value.toString();
			Like re;
			if (v.length() == 1) {
				re = like(name, v);
			} else {
				re = like(name, v.substring(1, v.length() - 1));
				re.left(v.substring(0, 1));
				re.right(v.substring(v.length() - 1, v.length()));
			}
			return re.ignoreCase(false).setNot(op.startsWith("NOT"));
		}
		// =
		else if ("=".equals(op)) {
			return eq(name, value);
		}
		// !=
		else if ("!=".equals(op) || "<>".equals(op)) {// TODO 检查一下,原本是&&, 明显永远成立
			return eq(name, value).setNot(true);
		} else if("IS NOT NULL".equals(op) || "IS NULL".equals(op)){
			SqlExpression re = isNull(name);
			return re.setNot(op.contains("NOT"));
		}
		// Others
		return new SimpleExpression(name, op, value);
	}

	private static SqlExpression _evalRange(String name, Object value) {
		if (ReflectionUtils.isInt(value))
			//return inInt(name, Castors.me().castTo(value, int[].class));
			return inInt(name, BeanPropertiesCopy.copyOrCast(value, int[].class));

		else if (ReflectionUtils.isLong(value))
			//return inLong(name, Castors.me().castTo(value, long[].class));
			return inLong(name, BeanPropertiesCopy.copyOrCast(value, long[].class));

		//return inStr(name, Castors.me().castTo(value, String[].class));
		return inStr(name, BeanPropertiesCopy.copyOrCast(value, String[].class));
	}
	

	/**
	 * 如果是数组或集合取得第一个对象。 否则返回自身
	 * 
	 * @param obj
	 *            任意对象
	 * @return 第一个代表对象
	 */
	private static Object first(Object obj) {
		if (null == obj)
			return obj;

		if (obj instanceof Collection<?>) {
			Iterator<?> it = ((Collection<?>) obj).iterator();
			return it.hasNext() ? it.next() : null;
		}

		if (obj.getClass().isArray())
			return Array.getLength(obj) > 0 ? Array.get(obj, 0) : null;

		return obj;
	}

}
