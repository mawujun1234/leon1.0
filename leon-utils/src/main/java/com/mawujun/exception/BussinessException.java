package com.mawujun.exception;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.Map;
import java.util.TreeMap;

/**
 * 真个系统的异常总类，整个系统都可以使用这个异常
 * @author mawujun
 *
 */
public class BussinessException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public static BussinessException wrap(Throwable exception, ExceptionCode errorCode) {
        if (exception instanceof BussinessException) {
            BussinessException se = (BussinessException)exception;
        	if (errorCode != null && errorCode != se.getErrorCode()) {
                return new BussinessException(exception.getMessage(), exception, errorCode);
			}
			return se;
        } else {
            return new BussinessException(exception.getMessage(), exception, errorCode);
        }
    }
    
    public static BussinessException wrap(Throwable exception) {
    	return wrap(exception, null);
    }
    
    private ExceptionCode errorCode;
    private final Map<String,Object> properties = new TreeMap<String,Object>();
    
    public BussinessException(ExceptionCode errorCode) {
		this.errorCode = errorCode;
	}

	public BussinessException(String message, ExceptionCode errorCode) {
		super(message);
		this.errorCode = errorCode;
	}

	public BussinessException(Throwable cause, ExceptionCode errorCode) {
		super(cause);
		this.errorCode = errorCode;
	}

	public BussinessException(String message, Throwable cause, ExceptionCode errorCode) {
		super(message, cause);
		this.errorCode = errorCode;
	}
	
	public ExceptionCode getErrorCode() {
        return errorCode;
    }
	
	public BussinessException setErrorCode(ExceptionCode errorCode) {
        this.errorCode = errorCode;
        return this;
    }
	
	public Map<String, Object> getProperties() {
		return properties;
	}
	
    @SuppressWarnings("unchecked")
	public <T> T get(String name) {
        return (T)properties.get(name);
    }
	
    public BussinessException set(String name, Object value) {
        properties.put(name, value);
        return this;
    }
    
    public void printStackTrace(PrintStream s) {
        synchronized (s) {
            printStackTrace(new PrintWriter(s));
        }
    }

    public void printStackTrace(PrintWriter s) { 
        synchronized (s) {
            s.println(this);
            s.println("\t-------------------------------");
            if (errorCode != null) {
	        	s.println("\t" + errorCode + ":" + errorCode.getClass().getName()); 
			}
            for (String key : properties.keySet()) {
            	s.println("\t" + key + "=[" + properties.get(key) + "]"); 
            }
            s.println("\t-------------------------------");
            StackTraceElement[] trace = getStackTrace();
            for (int i=0; i < trace.length; i++)
                s.println("\tat " + trace[i]);

            Throwable ourCause = getCause();
            if (ourCause != null) {
                ourCause.printStackTrace(s);
            }
            s.flush();
        }
    }
    
}
