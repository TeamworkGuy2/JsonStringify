package twg2.io.json.stringify;

import java.io.IOException;
import java.io.UncheckedIOException;

import twg2.functions.IoFunc;
import twg2.text.stringEscape.StringEscapeJson;

/**
 * @author TeamworkGuy2
 * @since 2015-12-13
 */
public class JsonStringify {


	// ==== Consumer ====
	/** Sames as {@link #join(Iterable, String, boolean, StringBuilder, IoFunc.FunctionIo)} except all strings are JSON escaped first ({@link StringEscapeJson#toJsonString(String)})
	 */
	public static final <T extends Object> void joinEscape(Iterable<T> objs, String delimiter, StringBuilder dst, IoFunc.FunctionIo<T, String> toString) {
		join(objs, delimiter, true, dst, toString);
	}


	/** Stringify a group of objects, but let a function convert the non-null objects to strings.  All this method does is write "null" for null values,
	 * the returned {@code toString} strings, and the separator between elements
	 */
	public static final <T extends Object> void join(Iterable<T> objs, String delimiter, boolean escape, StringBuilder dst, IoFunc.FunctionIo<T, String> toString) {
		try {
			join(objs, delimiter, escape, (Appendable)dst, toString);
		} catch(IOException ioe) {
			throw new UncheckedIOException(ioe);
		}
	}


	/** Sames as {@link #join(Iterable, String, boolean, Appendable, IoFunc.FunctionIo)} except all strings are JSON escaped first ({@link StringEscapeJson#toJsonString(String)})
	 */
	public static final <T extends Object> void join(Iterable<T> objs, String delimiter, Appendable dst, IoFunc.FunctionIo<T, String> toString) throws IOException {
		join(objs, delimiter, true, dst, toString);
	}


	/** Stringify a group of objects, but let a function convert the non-null objects to strings.  All this method does is write "null" for null values,
	 * the returned {@code toString} strings, and the separator between elements
	 */
	public static final <T extends Object> void join(Iterable<T> objs, String delimiter, boolean escape, Appendable dst, IoFunc.FunctionIo<T, String> toString) throws IOException {
		boolean firstLoop = true;
		for(T obj : objs) {
			if(!firstLoop) {
				dst.append(delimiter);
			}
			if(obj != null) {
				String str = toString.apply(obj);
				dst.append(escape ? StringEscapeJson.toJsonString(str) : str);
			}
			else {
				dst.append("null");
			}
			firstLoop = false;
		}
	}


	/** Stringify a group of objects, but let a consumer function write the non-null values.  All this method does is write "null" for null values and the separator between elements
	 */
	public static final <T extends Object> void joinConsume(Iterable<T> objs, String delimiter, StringBuilder dst, IoFunc.ConsumerIo<T> toString) {
		try {
			joinConsume(objs, delimiter, (Appendable)dst, toString);
		} catch(IOException ioe) {
			throw new UncheckedIOException(ioe);
		}
	}


	/** Stringify a group of objects, but let a consumer function write the non-null values.  All this method does is write "null" for null values and the separator between elements
	 */
	public static final <T extends Object> void joinConsume(Iterable<T> objs, String delimiter, Appendable dst, IoFunc.ConsumerIo<T> toString) throws IOException {
		boolean firstLoop = true;
		for(T obj : objs) {
			if(!firstLoop) {
				dst.append(delimiter);
			}
			if(obj != null) {
				toString.accept(obj);
			}
			else {
				dst.append("null");
			}
			firstLoop = false;
		}
	}


	public static final void toJsonProperty(String propName, boolean value, boolean lastProp, Appendable dst) throws IOException {
		dst.append('"').append(propName).append("\": ").append(Boolean.toString(value));
		if(!lastProp) {
			dst.append(", ");
		}
	}


	public static final void toJsonProperty(String propName, int value, boolean lastProp, Appendable dst) throws IOException {
		dst.append('"').append(propName).append("\": ").append(Integer.toString(value));
		if(!lastProp) {
			dst.append(", ");
		}
	}


	public static final void toJsonProperty(String propName, float value, boolean lastProp, Appendable dst) throws IOException {
		dst.append('"').append(propName).append("\": ").append(Float.toString(value));
		if(!lastProp) {
			dst.append(", ");
		}
	}


	public static final void toJsonProperty(String propName, long value, boolean lastProp, Appendable dst) throws IOException {
		dst.append('"').append(propName).append("\": ").append(Long.toString(value));
		if(!lastProp) {
			dst.append(", ");
		}
	}


	public static final void toJsonProperty(String propName, double value, boolean lastProp, Appendable dst) throws IOException {
		dst.append('"').append(propName).append("\": ").append(Double.toString(value));
		if(!lastProp) {
			dst.append(", ");
		}
	}


	public static final void toJsonProperty(String propName, char value, boolean lastProp, Appendable dst) throws IOException {
		dst.append('"').append(propName).append("\": \"");
		StringEscapeJson.toJsonString(value, dst);
		dst.append('"');
		if(!lastProp) {
			dst.append(", ");
		}
	}


	public static final void toJsonProperty(String propName, String value, boolean lastProp, Appendable dst) throws IOException {
		dst.append('"').append(propName).append("\": \"");
		StringEscapeJson.toJsonString(value, 0, value.length(), dst);
		dst.append('"');
		if(!lastProp) {
			dst.append(", ");
		}
	}

}
