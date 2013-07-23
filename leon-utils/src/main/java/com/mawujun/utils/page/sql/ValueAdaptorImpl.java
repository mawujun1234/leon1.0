package com.mawujun.utils.page.sql;

import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.Calendar;

import org.nutz.dao.impl.jdbc.BlobValueAdaptor;
import org.nutz.dao.impl.jdbc.ClobValueAdaptor;
import org.nutz.dao.jdbc.Jdbcs;
import org.nutz.dao.jdbc.ValueAdaptor;
import org.nutz.lang.Mirror;

/**
 * 用来获取ValueAdapter
 * @author mawujun 16064988@qq.com  
 *
 */
public class ValueAdaptorImpl<T> {
//	public static ValueAdaptor getAdaptor(Mirror<?> mirror) {
//		// String and char
//		if (mirror.isStringLike())
//			return Jdbcs.Adaptor.asString;
//		// Int
//		if (mirror.isInt())
//			return Jdbcs.Adaptor.asInteger;
//		// Boolean
//		if (mirror.isBoolean())
//			return Jdbcs.Adaptor.asBoolean;
//		// Long
//		if (mirror.isLong())
//			return Jdbcs.Adaptor.asLong;
//		// Enum
//		if (mirror.isEnum())
//			return Jdbcs.Adaptor.asEnumChar;
//		// Char
//		if (mirror.isChar())
//			return Jdbcs.Adaptor.asChar;
//		// Timestamp
//		if (mirror.isOf(Timestamp.class))
//			return Jdbcs.Adaptor.asTimestamp;
//		// Byte
//		if (mirror.isByte())
//			return Jdbcs.Adaptor.asByte;
//		// Short
//		if (mirror.isShort())
//			return Jdbcs.Adaptor.asShort;
//		// Float
//		if (mirror.isFloat())
//			return Jdbcs.Adaptor.asFloat;
//		// Double
//		if (mirror.isDouble())
//			return Jdbcs.Adaptor.asDouble;
//		// BigDecimal
//		if (mirror.isOf(BigDecimal.class))
//			return Jdbcs.Adaptor.asBigDecimal;
//		// Calendar
//		if (mirror.isOf(Calendar.class))
//			return Jdbcs.Adaptor.asCalendar;
//		// java.util.Date
//		if (mirror.isOf(java.util.Date.class))
//			return Jdbcs.Adaptor.asDate;
//		// java.sql.Date
//		if (mirror.isOf(java.sql.Date.class))
//			return Jdbcs.Adaptor.asSqlDate;
//		// java.sql.Time
//		if (mirror.isOf(java.sql.Time.class))
//			return Jdbcs.Adaptor.asSqlTime;
//		// Blob
//		if (mirror.isOf(Blob.class))
//			return new BlobValueAdaptor(conf.getPool());
//		// Clob
//		if (mirror.isOf(Clob.class))
//			return new ClobValueAdaptor(conf.getPool());
//		// byte[]
//		if (mirror.getType().isArray() && mirror.getType().getComponentType() == byte.class) {
//			return Jdbcs.Adaptor.asBytes;
//		}
//
//		// 默认情况
//		return Jdbcs.Adaptor.asString;
//	}
	
	/**
	 * 空值适配器
	 */
	public static final ValueAdaptor asNull = new ValueAdaptor() {
		public Object get(ResultSet rs, String colName) throws SQLException {
			return null;
		}

		public void set(PreparedStatement stat, Object obj, int i) throws SQLException {
			stat.setNull(i, Types.NULL);
		};

	};

	/**
	 * 字符串适配器
	 */
	public static final ValueAdaptor asString = new ValueAdaptor() {
		public Object get(ResultSet rs, String colName) throws SQLException {
			return rs.getString(colName);
		}

		public void set(PreparedStatement stat, Object obj, int i) throws SQLException {
			if (null == obj) {
				stat.setString(i, null);
			} else {
				stat.setString(i, Castors.me().castToString(obj));
			}
		}
	};

	/**
	 * 字符适配器
	 */
	public static final ValueAdaptor asChar = new ValueAdaptor() {
		public Object get(ResultSet rs, String colName) throws SQLException {
			String re = Strings.trim(rs.getString(colName));
			if (re == null || re.length() == 0)
				return null;
			return re;
		}

		public void set(PreparedStatement stat, Object obj, int i) throws SQLException {
			if (null == obj) {
				stat.setString(i, null);
			} else {
				String s;
				if (obj instanceof Character) {
					int c = ((Character) obj).charValue();
					if (c >= 0 && c <= 32)
						s = " ";
					else
						s = String.valueOf((char) c);
				} else
					s = obj.toString();
				stat.setString(i, s);
			}
		}
	};

	/**
	 * 整型适配器
	 */
	public static final ValueAdaptor asInteger = new ValueAdaptor() {
		public Object get(ResultSet rs, String colName) throws SQLException {
			int re = rs.getInt(colName);
			return rs.wasNull() ? null : re;
		}

		public void set(PreparedStatement stat, Object obj, int i) throws SQLException {
			if (null == obj) {
				stat.setNull(i, Types.INTEGER);
			} else {
				int v;
				if (obj instanceof Number)
					v = ((Number) obj).intValue();
				else
					v = Castors.me().castTo(obj.toString(), int.class);
				stat.setInt(i, v);
			}
		}
	};

	/**
	 * 大数适配器
	 */
	public static final ValueAdaptor asBigDecimal = new ValueAdaptor() {
		public Object get(ResultSet rs, String colName) throws SQLException {
			return rs.getBigDecimal(colName);
		}

		public void set(PreparedStatement stat, Object obj, int i) throws SQLException {
			if (null == obj) {
				stat.setNull(i, Types.BIGINT);
			} else {
				BigDecimal v;
				if (obj instanceof BigDecimal)
					v = (BigDecimal) obj;
				else if (obj instanceof Number)
					v = BigDecimal.valueOf(((Number) obj).longValue());
				else
					v = new BigDecimal(obj.toString());
				stat.setBigDecimal(i, v);
			}
		}
	};

	/**
	 * 布尔适配器
	 * <p>
	 * 对 Oracle，Types.BOOLEAN 对于 setNull 是不工作的 因此 OracleExpert 会用一个新的
	 * Adaptor 处理自己这种特殊情况
	 */
	public static final ValueAdaptor asBoolean = new ValueAdaptor() {
		public Object get(ResultSet rs, String colName) throws SQLException {
			boolean re = rs.getBoolean(colName);
			return rs.wasNull() ? null : re;
		}

		public void set(PreparedStatement stat, Object obj, int i) throws SQLException {
			if (null == obj) {
				stat.setNull(i, Types.BOOLEAN);
			} else {
				boolean v;
				if (obj instanceof Boolean)
					v = (Boolean) obj;
				else if (obj instanceof Number)
					v = ((Number) obj).intValue() > 0;
				else if (obj instanceof Character)
					v = Character.toUpperCase((Character) obj) == 'T';
				else
					v = Boolean.valueOf(obj.toString());
				stat.setBoolean(i, v);
			}
		}
	};

	/**
	 * 长整适配器
	 */
	public static final ValueAdaptor asLong = new ValueAdaptor() {
		public Object get(ResultSet rs, String colName) throws SQLException {
			long re = rs.getLong(colName);
			return rs.wasNull() ? null : re;
		}

		public void set(PreparedStatement stat, Object obj, int i) throws SQLException {
			if (null == obj) {
				stat.setNull(i, Types.INTEGER);
			} else {
				long v;
				if (obj instanceof Number)
					v = ((Number) obj).longValue();
				else
					v = Castors.me().castTo(obj.toString(), long.class);
				stat.setLong(i, v);
			}
		}
	};

	/**
	 * 字节适配器
	 */
	public static final ValueAdaptor asByte = new ValueAdaptor() {
		public Object get(ResultSet rs, String colName) throws SQLException {
			byte re = rs.getByte(colName);
			return rs.wasNull() ? null : re;
		}

		public void set(PreparedStatement stat, Object obj, int i) throws SQLException {
			if (null == obj) {
				stat.setNull(i, Types.TINYINT);
			} else {
				byte v;
				if (obj instanceof Number)
					v = ((Number) obj).byteValue();
				else
					v = Castors.me().castTo(obj.toString(), byte.class);
				stat.setByte(i, v);
			}
		}
	};

	/**
	 * 短整型适配器
	 */
	public static final ValueAdaptor asShort = new ValueAdaptor() {
		public Object get(ResultSet rs, String colName) throws SQLException {
			short re = rs.getShort(colName);
			return rs.wasNull() ? null : re;
		}

		public void set(PreparedStatement stat, Object obj, int i) throws SQLException {
			if (null == obj) {
				stat.setNull(i, Types.SMALLINT);
			} else {
				short v;
				if (obj instanceof Number)
					v = ((Number) obj).shortValue();
				else
					v = Castors.me().castTo(obj.toString(), short.class);
				stat.setShort(i, v);
			}
		}
	};

	/**
	 * 浮点适配器
	 */
	public static final ValueAdaptor asFloat = new ValueAdaptor() {
		public Object get(ResultSet rs, String colName) throws SQLException {
			float re = rs.getFloat(colName);
			return rs.wasNull() ? null : re;
		}

		public void set(PreparedStatement stat, Object obj, int i) throws SQLException {
			if (null == obj) {
				stat.setNull(i, Types.FLOAT);
			} else {
				float v;
				if (obj instanceof Number)
					v = ((Number) obj).floatValue();
				else
					v = Castors.me().castTo(obj.toString(), float.class);
				stat.setFloat(i, v);
			}
		}
	};

	/**
	 * 双精度浮点适配器
	 */
	public static final ValueAdaptor asDouble = new ValueAdaptor() {
		public Object get(ResultSet rs, String colName) throws SQLException {
			double re = rs.getDouble(colName);
			return rs.wasNull() ? null : re;
		}

		public void set(PreparedStatement stat, Object obj, int i) throws SQLException {
			if (null == obj) {
				stat.setNull(i, Types.DOUBLE);
			} else {
				double v;
				if (obj instanceof Number)
					v = ((Number) obj).doubleValue();
				else
					v = Castors.me().castTo(obj.toString(), double.class);
				stat.setDouble(i, v);
			}
		}
	};

	/**
	 * 日历适配器
	 */
	public static final ValueAdaptor asCalendar = new ValueAdaptor() {
		public Object get(ResultSet rs, String colName) throws SQLException {
			Timestamp ts = rs.getTimestamp(colName);
			if (null == ts)
				return null;
			Calendar c = Calendar.getInstance();
			c.setTimeInMillis(ts.getTime());
			return c;
		}

		public void set(PreparedStatement stat, Object obj, int i) throws SQLException {
			if (null == obj) {
				stat.setNull(i, Types.TIMESTAMP);
			} else {
				Timestamp v;
				if (obj instanceof Calendar)
					v = new Timestamp(((Calendar) obj).getTimeInMillis());
				else
					v = Castors.me().castTo(obj, Timestamp.class);
				stat.setTimestamp(i, v);
			}
		}
	};

	/**
	 * 时间戳适配器
	 */
	public static final ValueAdaptor asTimestamp = new ValueAdaptor() {
		public Object get(ResultSet rs, String colName) throws SQLException {
			return rs.getTimestamp(colName);
		}

		public void set(PreparedStatement stat, Object obj, int i) throws SQLException {
			if (null == obj) {
				stat.setNull(i, Types.TIMESTAMP);
			} else {
				Timestamp v;
				if (obj instanceof Timestamp)
					v = (Timestamp) obj;
				else
					v = Castors.me().castTo(obj, Timestamp.class);
				stat.setTimestamp(i, v);
			}
		}
	};

	/**
	 * 日期适配器
	 */
	public static final ValueAdaptor asDate = new ValueAdaptor() {
		public Object get(ResultSet rs, String colName) throws SQLException {
			Timestamp ts = rs.getTimestamp(colName);
			return null == ts ? null : new java.util.Date(ts.getTime());
		}

		public void set(PreparedStatement stat, Object obj, int i) throws SQLException {
			Timestamp v;
			if (null == obj) {
				stat.setNull(i, Types.TIMESTAMP);
			} else {
				if (obj instanceof java.util.Date)
					v = new Timestamp(((java.util.Date) obj).getTime());
				else
					v = Castors.me().castTo(obj, Timestamp.class);
				stat.setTimestamp(i, v);
			}
		}
	};

	/**
	 * Sql 日期适配器
	 */
	public static final ValueAdaptor asSqlDate = new ValueAdaptor() {
		public Object get(ResultSet rs, String colName) throws SQLException {
			return rs.getDate(colName);
		}

		public void set(PreparedStatement stat, Object obj, int i) throws SQLException {
			if (null == obj) {
				stat.setNull(i, Types.DATE);
			} else {
				java.sql.Date v;
				if (obj instanceof java.sql.Date)
					v = (java.sql.Date) obj;
				else
					v = Castors.me().castTo(obj, java.sql.Date.class);
				stat.setDate(i, v);
			}
		}
	};

	/**
	 * Sql 时间适配器
	 */
	public static final ValueAdaptor asSqlTime = new ValueAdaptor() {
		public Object get(ResultSet rs, String colName) throws SQLException {
			return rs.getTime(colName);
		}

		public void set(PreparedStatement stat, Object obj, int i) throws SQLException {
			java.sql.Time v;
			if (null == obj) {
				stat.setNull(i, Types.TIME);
			} else {
				if (obj instanceof java.sql.Time)
					v = (java.sql.Time) obj;
				else
					v = Castors.me().castTo(obj, java.sql.Time.class);
				stat.setTime(i, v);
			}
		}
	};

	/**
	 * 数字枚举适配器
	 */
	public static final ValueAdaptor asEnumInt = new ValueAdaptor() {
		public Object get(ResultSet rs, String colName) throws SQLException {
			int re = rs.getInt(colName);
			return rs.wasNull() ? null : re;
		}

		public void set(PreparedStatement stat, Object obj, int i) throws SQLException {
			if (null == obj) {
				stat.setNull(i, Types.INTEGER);
			} else {
				int v;
				if (obj instanceof Enum<?>)
					v = ((Enum<?>) obj).ordinal();
				else
					v = Castors.me().castTo(obj, int.class);
				stat.setInt(i, v);
			}
		}
	};

	/**
	 * 字符枚举适配器
	 */
	public static final ValueAdaptor asEnumChar = new ValueAdaptor() {
		public Object get(ResultSet rs, String colName) throws SQLException {
			return rs.getString(colName);
		}

		public void set(PreparedStatement stat, Object obj, int i) throws SQLException {
			if (null == obj) {
				stat.setString(i, null);
			} else {
				String v = obj.toString();
				stat.setString(i, v);
			}
		}
	};

	/**
	 * 默认对象适配器
	 */
	public static final ValueAdaptor asObject = new ValueAdaptor() {
		public Object get(ResultSet rs, String colName) throws SQLException {
			return rs.getObject(colName);
		}

		public void set(PreparedStatement stat, Object obj, int i) throws SQLException {
			stat.setObject(i, obj);
		}
	};

	/**
	 * 字节数组适配器
	 */
	public static final ValueAdaptor asBytes = new ValueAdaptor() {

		public Object get(ResultSet rs, String colName) throws SQLException {
			return rs.getBytes(colName);
		}

		public void set(PreparedStatement stat, Object obj, int index) throws SQLException {
			if (null == obj) {
				stat.setNull(index, Types.BINARY);
			} else {
				stat.setBytes(index, (byte[]) obj);
			}
		}

	};
}
