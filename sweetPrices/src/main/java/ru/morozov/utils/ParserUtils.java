package ru.morozov.utils;

import java.math.BigDecimal;

public class ParserUtils {
	
	private Object obj;
	
	public ParserUtils(Object obj) {this.obj = obj;}
	
	public final String getStringResult(String defaultValue) {return obj == null ? defaultValue : obj.toString();}
	public final String getStringResult() {
		if (obj == null) return "";
		else if (obj instanceof Double) {
			Double doubleObj = (Double)obj;
			if (doubleObj.intValue() - doubleObj == 0)
				return (new Integer(doubleObj.intValue())).toString();
		}
		
		return obj == null ? "" : obj.toString();
		
	}
	
	public final Long getLongResult() {return getLongResult(this.obj);}
	public static final Long getLongResult(Object obj) {
		if (obj == null) return 0L;
		
		if (obj instanceof BigDecimal)
			return ((BigDecimal)obj).longValue();
		if (obj instanceof Long)
			return (Long)obj;
		else if (obj instanceof Integer)
			return ((Integer)obj).longValue();
		else if (obj instanceof Double)
			return ((Double)obj).longValue();
		else if (obj instanceof String)
			try {
				return Long.parseLong(obj.toString());
			}
			catch (Exception ex) {
				return 0L;
			}
		else
			return 0L;
	}
	
	public final Integer getIntResult() {return getIntResult(this.obj);}
	public static final Integer getIntResult(Object obj) {
		if (obj == null) return 0;
		
		if (obj instanceof Integer)
			return (Integer)obj;
		else if (obj instanceof BigDecimal)
			return ((BigDecimal)obj).intValue();
		else if (obj instanceof Boolean)
			return ((Boolean)obj).booleanValue() ? 1 : 0;
		else if (obj instanceof Long)
			return ((Long)obj).intValue();
		else if (obj instanceof Double)
			return ((Double)obj).intValue();
		else if (obj instanceof String)
			try {
				return Integer.parseInt(obj.toString());
			}
			catch (Exception ex) {
				return 0;
			}
		
		return 0;
	}
	
	public final Boolean getBooleanResult() {
		if (obj == null) return false;
		
		if (obj instanceof Integer)
			return ((Integer)obj) > 0 ? true : false;	
			
		return false;
	}
	
	public final Float getFloatResult() {
		Float res = 0F ;
		if (obj instanceof String || obj instanceof Double)
			try {res = Float.parseFloat(obj.toString());} catch (Exception ex) {}
				
		else
		     res = ( obj == null) ? 0f : (Float)obj;
		
		return res ;
	}
	public final Double getDoubleResult() {return getDoubleResult(this.obj);}

	public static final Double getDoubleResult(Object obj) {
		Double res = 0.0 ;
		if (obj == null) return res;
		
		if (obj instanceof String)
			try {res = Double.parseDouble(obj.toString());} catch (Exception ex) {}
	    else if (obj instanceof Float)
			try {res = Double.parseDouble(obj.toString());} catch (Exception ex) {}
	    else if (obj instanceof Integer) 
	    	try {res = Double.parseDouble(obj.toString());} catch (Exception ex) {}
		else if (obj instanceof Long) 
		  	try {res = Double.parseDouble(obj.toString());} catch (Exception ex) {}
		else if (obj instanceof BigDecimal)
			try {res = ((BigDecimal) obj).doubleValue();} catch (Exception ex) {}
		else 
			try {res = (Double)obj;	}catch (Exception ex) {}
			
		return res ;
	}
	
	public final BigDecimal getBigDecimalResult() {return obj == null ? BigDecimal.ZERO : new BigDecimal (obj.toString());}
	
	public final java.util.Date getDateResult() {
		if (obj == null) return null;
		else if (obj instanceof java.sql.Date) return new java.util.Date(((java.sql.Date)obj).getTime());
		else if (obj instanceof java.sql.Timestamp) return new java.util.Date(((java.sql.Timestamp)obj).getTime());
		
		return null;}
	
	public final java.sql.Date getSqlDateResult() {return obj == null ? null : (java.sql.Date)obj;}
	
	public final java.sql.Timestamp getTimestampResult() {
		if (obj == null) return null;
		else if (obj instanceof java.util.Date) return new java.sql.Timestamp(((java.util.Date) obj).getTime());
		
		return null;
	}
	
	public final Object getCastResult(Class<?> _class) {
		if (obj == null) return null;

		if (obj.getClass().getCanonicalName().equals(_class.getCanonicalName()))
			return obj;
		
		if (_class.equals(Object.class))
			return obj;		
		if (_class.equals(Boolean.class))
			return getBooleanResult();
		else if (_class.equals(Integer.class))
			return getIntResult();
		else if (_class.equals(Short.class))
			return getIntResult().shortValue();
		else if (_class.equals(Double.class))
			return getDoubleResult();
		else if (_class.equals(Long.class))
			return getLongResult();
		else if (_class.equals(String.class))
			return getStringResult();
		else if (_class.equals(BigDecimal.class))
			return getBigDecimalResult();
		else if (_class.equals(java.util.Date.class))
			return getDateResult();
		else if (_class.equals(java.sql.Date.class))
			return getSqlDateResult();
		else {
			System.err.println("Used default converter to cast \"" + obj.toString() + "\" of type " + obj.getClass().getCanonicalName() + " to " + _class.getCanonicalName());
			return null;
		}
	}
}
