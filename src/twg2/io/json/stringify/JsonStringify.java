package twg2.io.json.stringify;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Iterator;
import java.util.List;

import twg2.functions.IoFunc;
import twg2.text.stringEscape.StringEscapeJson;

/**
 * @author TeamworkGuy2
 * @since 2015-12-13
 */
public final class JsonStringify {
	public static final JsonStringify inst = new JsonStringify();


	// ==== Function ====
	/** Same as {@link #join(Iterable, String, boolean, StringBuilder, IoFunc.FunctionIo)} except all strings are JSON escaped first ({@link StringEscapeJson#toJsonString(String)})
	 */
	public <T extends Object> JsonStringify joinEscape(Iterable<? extends T> objs, String delimiter, StringBuilder dst, IoFunc.FunctionIo<T, String> toString) {
		return join(objs, delimiter, true, dst, toString);
	}


	/** Stringify a group of objects, but let a function convert the non-null objects to strings.  All this method does is write "null" for null values,
	 * the returned {@code toString} strings, and the separator between elements
	 */
	public <T extends Object> JsonStringify join(Iterable<? extends T> objs, String delimiter, boolean escape, StringBuilder dst, IoFunc.FunctionIo<T, String> toString) {
		try {
			return join(objs, delimiter, escape, (Appendable)dst, toString);
		} catch(IOException ioe) {
			throw new UncheckedIOException(ioe);
		}
	}


	/** Same as {@link #join(Iterable, String, boolean, Appendable, IoFunc.FunctionIo)} except all strings are JSON escaped first ({@link StringEscapeJson#toJsonString(String)})
	 */
	public <T extends Object> JsonStringify join(Iterable<? extends T> objs, String delimiter, Appendable dst, IoFunc.FunctionIo<T, String> toString) throws IOException {
		return join(objs, delimiter, true, dst, toString);
	}


	/** Stringify a group of objects, but let a function convert the non-null objects to strings.  All this method does is write "null" for null values,
	 * the returned {@code toString} strings, and the separator between elements
	 */
	public <T extends Object> JsonStringify join(Iterable<? extends T> objs, String delimiter, boolean escape, Appendable dst, IoFunc.FunctionIo<T, String> toString) throws IOException {
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
		return this;
	}


	/** Stringify a group of objects, but let a consumer function write the non-null values.  All this method does is write "null" for null values and the separator between elements
	 */
	public <T extends Object> JsonStringify joinConsume(Iterable<? extends T> objs, String delimiter, StringBuilder dst, IoFunc.ConsumerIo<T> toString) {
		try {
			return joinConsume(objs, delimiter, (Appendable)dst, toString);
		} catch(IOException ioe) {
			throw new UncheckedIOException(ioe);
		}
	}


	/** Stringify a group of objects, but let a consumer function write the non-null values.  All this method does is write "null" for null values and the separator between elements
	 */
	public <T extends Object> JsonStringify joinConsume(Iterable<? extends T> objs, String delimiter, Appendable dst, IoFunc.ConsumerIo<T> toString) throws IOException {
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
		return this;
	}


	// ==== to array ====

	// object array (toString())
	public JsonStringify toStringArray(List<? extends CharSequence> vals,                             StringBuilder dst)                 { try { return toArray(vals, " ", true, true, dst); } catch(IOException ioe) { throw new UncheckedIOException(ioe); } }
	public JsonStringify toStringArray(List<? extends CharSequence> vals, String whitespaceSeparator, StringBuilder dst)                 { try { return toArray(vals, whitespaceSeparator, true, true, dst); } catch(IOException ioe) { throw new UncheckedIOException(ioe); } }
	public JsonStringify toStringArray(List<? extends CharSequence> vals,                             Appendable dst) throws IOException { return toArray(vals, " ", true, true, dst); }
	public JsonStringify toStringArray(List<? extends CharSequence> vals, String whitespaceSeparator, Appendable dst) throws IOException { return toArray(vals, whitespaceSeparator, true, true, dst); }

	public JsonStringify toArray(List<? extends Object> vals,                             StringBuilder dst)                 { try { return toArray(vals, " ", false, false, dst); } catch(IOException ioe) { throw new UncheckedIOException(ioe); } }
	public JsonStringify toArray(List<? extends Object> vals, String whitespaceSeparator, StringBuilder dst)                 { try { return toArray(vals, whitespaceSeparator, false, false, dst); } catch(IOException ioe) { throw new UncheckedIOException(ioe); } }
	public JsonStringify toArray(List<? extends Object> vals,                             Appendable dst) throws IOException { return toArray(vals, " ", false, false, dst); }
	public JsonStringify toArray(List<? extends Object> vals, String whitespaceSeparator, Appendable dst) throws IOException { return toArray(vals, whitespaceSeparator, false, false, dst); }

	public JsonStringify toArray(List<? extends Object> vals, String whitespaceSeparator, boolean quote, boolean escape, Appendable dst) throws IOException {
		boolean firstLoop = true;
		dst.append('[');
		for(int i = 0, size = vals.size(); i < size; i++) {
			Object obj = vals.get(i);
			if(!firstLoop) {
				dst.append(',').append(whitespaceSeparator);
			}
			if(obj != null) {
				String str = obj.toString();
				if(quote) { dst.append('"'); }
				if(escape) { StringEscapeJson.toJsonString(str, 0, str.length(), dst); }
				else { dst.append(str); }
				if(quote) { dst.append('"'); }
			}
			else {
				dst.append("null");
			}
			firstLoop = false;
		}
		dst.append(']');
		return this;
	}


	public JsonStringify toStringArray(Iterable<? extends CharSequence> vals,                             StringBuilder dst)                 { try { return toArray(vals.iterator(), " ", true, true, dst); } catch(IOException ioe) { throw new UncheckedIOException(ioe); } }
	public JsonStringify toStringArray(Iterable<? extends CharSequence> vals, String whitespaceSeparator, StringBuilder dst)                 { try { return toArray(vals.iterator(), whitespaceSeparator, true, true, dst); } catch(IOException ioe) { throw new UncheckedIOException(ioe); } }
	public JsonStringify toStringArray(Iterable<? extends CharSequence> vals,                             Appendable dst) throws IOException { return toArray(vals.iterator(), " ", true, true, dst); }
	public JsonStringify toStringArray(Iterable<? extends CharSequence> vals, String whitespaceSeparator, Appendable dst) throws IOException { return toArray(vals.iterator(), whitespaceSeparator, true, true, dst); }

	public JsonStringify toArray(Iterable<? extends Object> vals,                             StringBuilder dst)                 { try { return toArray(vals.iterator(), " ", false, false, dst); } catch(IOException ioe) { throw new UncheckedIOException(ioe); } }
	public JsonStringify toArray(Iterable<? extends Object> vals, String whitespaceSeparator, StringBuilder dst)                 { try { return toArray(vals.iterator(), whitespaceSeparator, false, false, dst); } catch(IOException ioe) { throw new UncheckedIOException(ioe); } }
	public JsonStringify toArray(Iterable<? extends Object> vals,                             Appendable dst) throws IOException { return toArray(vals.iterator(), " ", false, false, dst); }
	public JsonStringify toArray(Iterable<? extends Object> vals, String whitespaceSeparator, Appendable dst) throws IOException { return toArray(vals.iterator(), whitespaceSeparator, false, false, dst); }

	public JsonStringify toArray(Iterator<? extends Object> vals, String whitespaceSeparator, boolean quote, boolean escape, Appendable dst) throws IOException {
		boolean firstLoop = true;
		dst.append('[');
		while(vals.hasNext()) {
			if(!firstLoop) {
				dst.append(',').append(whitespaceSeparator);
			}
			Object obj = vals.next();
			if(obj != null) {
				String str = obj.toString();
				if(quote) { dst.append('"'); }
				if(escape) { StringEscapeJson.toJsonString(str, 0, str.length(), dst); }
				else { dst.append(str); }
				if(quote) { dst.append('"'); }
			}
			else {
				dst.append("null");
			}
			firstLoop = false;
		}
		dst.append(']');
		return this;
	}


	// object array Function
	public <T extends Object> JsonStringify toStringArray(List<? extends T> vals,                             StringBuilder dst, IoFunc.FunctionIo<T, String> toString)                    { try { return toArray(vals, " ", true, true, dst, toString); } catch(IOException ioe) { throw new UncheckedIOException(ioe); } }
	public <T extends Object> JsonStringify toStringArray(List<? extends T> vals, String whitespaceSeparator, StringBuilder dst, IoFunc.FunctionIo<T, String> toString)                    { try { return toArray(vals, whitespaceSeparator, true, true, dst, toString); } catch(IOException ioe) { throw new UncheckedIOException(ioe); } }
	public <T extends Object> JsonStringify toStringArray(List<? extends T> vals,                             Appendable dst,    IoFunc.FunctionIo<T, String> toString) throws IOException { return toArray(vals, " ", true, true, dst, toString); }
	public <T extends Object> JsonStringify toStringArray(List<? extends T> vals, String whitespaceSeparator, Appendable dst,    IoFunc.FunctionIo<T, String> toString) throws IOException { return toArray(vals, whitespaceSeparator, true, true, dst, toString); }

	public <T extends Object> JsonStringify toArray(List<? extends T> vals,                             StringBuilder dst, IoFunc.FunctionIo<T, String> toString)                    { try { return toArray(vals, " ", false, false, dst, toString);  } catch(IOException ioe) { throw new UncheckedIOException(ioe); } }
	public <T extends Object> JsonStringify toArray(List<? extends T> vals, String whitespaceSeparator, StringBuilder dst, IoFunc.FunctionIo<T, String> toString)                    { try { return toArray(vals, whitespaceSeparator, false, false, dst, toString);  } catch(IOException ioe) { throw new UncheckedIOException(ioe); } }
	public <T extends Object> JsonStringify toArray(List<? extends T> vals,                             Appendable dst,    IoFunc.FunctionIo<T, String> toString) throws IOException { return toArray(vals, " ", false, false, dst, toString); }
	public <T extends Object> JsonStringify toArray(List<? extends T> vals, String whitespaceSeparator, Appendable dst,    IoFunc.FunctionIo<T, String> toString) throws IOException { return toArray(vals, whitespaceSeparator, false, false, dst, toString); }

	public <T extends Object> JsonStringify toArray(List<? extends T> vals, String whitespaceSeparator,
			boolean quote, boolean escape, Appendable dst, IoFunc.FunctionIo<? super T, String> toString) throws IOException {
		boolean firstLoop = true;
		dst.append('[');
		for(int i = 0, size = vals.size(); i < size; i++) {
			T obj = vals.get(i);
			if(!firstLoop) {
				dst.append(',').append(whitespaceSeparator);
			}
			if(obj != null) {
				String str = toString.apply(obj);
				if(quote) { dst.append('"'); }
				if(escape) { StringEscapeJson.toJsonString(str, 0, str.length(), dst); }
				else { dst.append(str); }
				if(quote) { dst.append('"'); }
			}
			else {
				dst.append("null");
			}
			firstLoop = false;
		}
		dst.append(']');
		return this;
	}


	public <T extends Object> JsonStringify toStringArray(Iterable<? extends T> vals,                             StringBuilder dst, IoFunc.FunctionIo<T, String> toString)                    { try { return toArray(vals.iterator(), " ", true, true, dst, toString); } catch(IOException ioe) { throw new UncheckedIOException(ioe); } }
	public <T extends Object> JsonStringify toStringArray(Iterable<? extends T> vals, String whitespaceSeparator, StringBuilder dst, IoFunc.FunctionIo<T, String> toString)                    { try { return toArray(vals.iterator(), whitespaceSeparator, true, true, dst, toString); } catch(IOException ioe) { throw new UncheckedIOException(ioe); } }
	public <T extends Object> JsonStringify toStringArray(Iterable<? extends T> vals,                             Appendable dst,    IoFunc.FunctionIo<T, String> toString) throws IOException { return toArray(vals.iterator(), " ", true, true, dst, toString); }
	public <T extends Object> JsonStringify toStringArray(Iterable<? extends T> vals, String whitespaceSeparator, Appendable dst,    IoFunc.FunctionIo<T, String> toString) throws IOException { return toArray(vals.iterator(), whitespaceSeparator, true, true, dst, toString); }

	public <T extends Object> JsonStringify toArray(Iterable<? extends T> vals,                             StringBuilder dst, IoFunc.FunctionIo<T, String> toString)                    { try { return toArray(vals.iterator(), " ", false, false, dst, toString);  } catch(IOException ioe) { throw new UncheckedIOException(ioe); } }
	public <T extends Object> JsonStringify toArray(Iterable<? extends T> vals, String whitespaceSeparator, StringBuilder dst, IoFunc.FunctionIo<T, String> toString)                    { try { return toArray(vals.iterator(), whitespaceSeparator, false, false, dst, toString);  } catch(IOException ioe) { throw new UncheckedIOException(ioe); } }
	public <T extends Object> JsonStringify toArray(Iterable<? extends T> vals,                             Appendable dst,    IoFunc.FunctionIo<T, String> toString) throws IOException { return toArray(vals.iterator(), " ", false, false, dst, toString); }
	public <T extends Object> JsonStringify toArray(Iterable<? extends T> vals, String whitespaceSeparator, Appendable dst,    IoFunc.FunctionIo<T, String> toString) throws IOException { return toArray(vals.iterator(), whitespaceSeparator, false, false, dst, toString); }

	public <T extends Object> JsonStringify toArray(Iterator<? extends T> vals, String whitespaceSeparator,
			boolean quote, boolean escape, Appendable dst, IoFunc.FunctionIo<? super T, String> toString) throws IOException {
		boolean firstLoop = true;
		dst.append('[');
		while(vals.hasNext()) {
			if(!firstLoop) {
				dst.append(',').append(whitespaceSeparator);
			}
			T obj = vals.next();
			if(obj != null) {
				String str = toString.apply(obj);
				if(quote) { dst.append('"'); }
				if(escape) { StringEscapeJson.toJsonString(str, 0, str.length(), dst); }
				else { dst.append(str); }
				if(quote) { dst.append('"'); }
			}
			else {
				dst.append("null");
			}
			firstLoop = false;
		}
		dst.append(']');
		return this;
	}


	// object array Consumer
	public <T extends Object> JsonStringify toArrayConsume(List<? extends T> vals,                             StringBuilder dst, IoFunc.ConsumerIo<T> toString)                    { try { return toArrayConsume(vals, " ", (Appendable)dst, toString);  } catch(IOException ioe) { throw new UncheckedIOException(ioe); } }
	public <T extends Object> JsonStringify toArrayConsume(List<? extends T> vals, String whitespaceSeparator, StringBuilder dst, IoFunc.ConsumerIo<T> toString)                    { try { return toArrayConsume(vals, whitespaceSeparator, (Appendable)dst, toString);  } catch(IOException ioe) { throw new UncheckedIOException(ioe); } }

	public <T extends Object> JsonStringify toArrayConsume(List<? extends T> vals,                             Appendable dst,    IoFunc.ConsumerIo<T> toString) throws IOException { return toArrayConsume(vals, " ", dst, toString); }
	public <T extends Object> JsonStringify toArrayConsume(List<? extends T> vals, String whitespaceSeparator, Appendable dst,    IoFunc.ConsumerIo<T> toString) throws IOException {
		boolean firstLoop = true;
		dst.append('[');
		for(int i = 0, size = vals.size(); i < size; i++) {
			T obj = vals.get(i);
			if(!firstLoop) {
				dst.append(',').append(whitespaceSeparator);
			}
			if(obj != null) {
				toString.accept(obj);
			}
			else {
				dst.append("null");
			}
			firstLoop = false;
		}
		dst.append(']');
		return this;
	}


	public <T extends Object> JsonStringify toArrayConsume(Iterable<? extends T> vals,                             StringBuilder dst, IoFunc.ConsumerIo<T> toString)                    { try { return toArrayConsume(vals.iterator(), " ", dst, toString);  } catch(IOException ioe) { throw new UncheckedIOException(ioe); } }
	public <T extends Object> JsonStringify toArrayConsume(Iterable<? extends T> vals, String whitespaceSeparator, StringBuilder dst, IoFunc.ConsumerIo<T> toString)                    { try { return toArrayConsume(vals.iterator(), whitespaceSeparator, dst, toString);  } catch(IOException ioe) { throw new UncheckedIOException(ioe); } }

	public <T extends Object> JsonStringify toArrayConsume(Iterable<? extends T> vals,                             Appendable dst,    IoFunc.ConsumerIo<T> toString) throws IOException { return toArrayConsume(vals.iterator(), " ", dst, toString); }
	public <T extends Object> JsonStringify toArrayConsume(Iterable<? extends T> vals, String whitespaceSeparator, Appendable dst,    IoFunc.ConsumerIo<T> toString) throws IOException { return toArrayConsume(vals.iterator(), whitespaceSeparator, dst, toString); }

	public <T extends Object> JsonStringify toArrayConsume(Iterator<? extends T> vals, String whitespaceSeparator, Appendable dst, IoFunc.ConsumerIo<? super T> toString) throws IOException {
		boolean firstLoop = true;
		dst.append('[');
		while(vals.hasNext()) {
			if(!firstLoop) {
				dst.append(',').append(whitespaceSeparator);
			}
			T obj = vals.next();
			if(obj != null) {
				toString.accept(obj);
			}
			else {
				dst.append("null");
			}
			firstLoop = false;
		}
		dst.append(']');
		return this;
	}


	// int array
	public JsonStringify toArray(int[] vals, Appendable dst) throws IOException { return toArray(vals, 0, vals.length, dst); }
	public JsonStringify toArray(int[] vals, int off, int len, Appendable dst) throws IOException {
		dst.append('[');
		if(len > 0) {
			dst.append(Integer.toString(vals[off]));
			for(int i = off + 1, size = off + len; i < size; i++) {
				dst.append(',').append(' ').append(Integer.toString(vals[i]));
			}
		}
		dst.append(']');
		return this;
	}


	public JsonStringify toArray(int[] vals, StringBuilder dst) { return toArray(vals, 0, vals.length, dst); }
	public JsonStringify toArray(int[] vals, int off, int len, StringBuilder dst) {
		dst.append('[');
		if(len > 0) {
			dst.append(vals[off]);
			for(int i = off + 1, size = off + len; i < size; i++) {
				dst.append(',').append(' ').append(vals[i]);
			}
		}
		dst.append(']');
		return this;
	}


	// float array
	public JsonStringify toArray(float[] vals, Appendable dst) throws IOException { return toArray(vals, 0, vals.length, dst); }
	public JsonStringify toArray(float[] vals, int off, int len, Appendable dst) throws IOException {
		dst.append('[');
		if(len > 0) {
			dst.append(Float.toString(vals[off]));
			for(int i = off + 1, size = off + len; i < size; i++) {
				dst.append(',').append(' ').append(Float.toString(vals[i]));
			}
		}
		dst.append(']');
		return this;
	}


	public JsonStringify toArray(float[] vals, StringBuilder dst) { return toArray(vals, 0, vals.length, dst); }
	public JsonStringify toArray(float[] vals, int off, int len, StringBuilder dst) {
		dst.append('[');
		if(len > 0) {
			dst.append(vals[off]);
			for(int i = off + 1, size = off + len; i < size; i++) {
				dst.append(',').append(' ').append(vals[i]);
			}
		}
		dst.append(']');
		return this;
	}


	// long array
	public JsonStringify toArray(long[] vals, Appendable dst) throws IOException { return toArray(vals, 0, vals.length, dst); }
	public JsonStringify toArray(long[] vals, int off, int len, Appendable dst) throws IOException {
		dst.append('[');
		if(len > 0) {
			dst.append(Long.toString(vals[off]));
			for(int i = off + 1, size = off + len; i < size; i++) {
				dst.append(',').append(' ').append(Long.toString(vals[i]));
			}
		}
		dst.append(']');
		return this;
	}


	public JsonStringify toArray(long[] vals, StringBuilder dst) { return toArray(vals, 0, vals.length, dst); }
	public JsonStringify toArray(long[] vals, int off, int len, StringBuilder dst) {
		dst.append('[');
		if(len > 0) {
			dst.append(vals[off]);
			for(int i = off + 1, size = off + len; i < size; i++) {
				dst.append(',').append(' ').append(vals[i]);
			}
		}
		dst.append(']');
		return this;
	}


	// double array
	public JsonStringify toArray(double[] vals, Appendable dst) throws IOException { return toArray(vals, 0, vals.length, dst); }
	public JsonStringify toArray(double[] vals, int off, int len, Appendable dst) throws IOException {
		dst.append('[');
		if(len > 0) {
			dst.append(Double.toString(vals[off]));
			for(int i = off + 1, size = off + len; i < size; i++) {
				dst.append(',').append(' ').append(Double.toString(vals[i]));
			}
		}
		dst.append(']');
		return this;
	}


	public JsonStringify toArray(double[] vals, StringBuilder dst) { return toArray(vals, 0, vals.length, dst); }
	public JsonStringify toArray(double[] vals, int off, int len, StringBuilder dst) {
		dst.append('[');
		if(len > 0) {
			dst.append(vals[off]);
			for(int i = off + 1, size = off + len; i < size; i++) {
				dst.append(',').append(' ').append(vals[i]);
			}
		}
		dst.append(']');
		return this;
	}


	// ==== to 'prop: value' string ====
	public JsonStringify propName(String propName, StringBuilder dst)                 { dst.append('"').append(propName).append("\": "); return this; }
	public JsonStringify propName(String propName, Appendable dst) throws IOException { dst.append('"').append(propName).append("\": "); return this; }

	public JsonStringify propNameUnquoted(String propName, StringBuilder dst)                 { dst.append(propName).append(": "); return this; }
	public JsonStringify propNameUnquoted(String propName, Appendable dst) throws IOException { dst.append(propName).append(": "); return this; }


	// ==== to 'prop: value' string ====
	public JsonStringify toProp(String propName, boolean value, StringBuilder dst)                 { try { return toProp(propName, value, (Appendable)dst); } catch(IOException ioe) { throw new UncheckedIOException(ioe); } }
	public JsonStringify toProp(String propName, boolean value, Appendable dst) throws IOException {
		propName(propName, dst);
		dst.append(Boolean.toString(value));
		return this;
	}


	public JsonStringify toProp(String propName, int value, StringBuilder dst)                 { try { return toProp(propName, value, (Appendable)dst); } catch(IOException ioe) { throw new UncheckedIOException(ioe); } }
	public JsonStringify toProp(String propName, int value, Appendable dst) throws IOException {
		propName(propName, dst);
		dst.append(Integer.toString(value));
		return this;
	}


	public JsonStringify toProp(String propName, float value, StringBuilder dst)                 { try { return toProp(propName, value, (Appendable)dst); } catch(IOException ioe) { throw new UncheckedIOException(ioe); } }
	public JsonStringify toProp(String propName, float value, Appendable dst) throws IOException {
		propName(propName, dst);
		dst.append(Float.toString(value));
		return this;
	}


	public JsonStringify toProp(String propName, long value, StringBuilder dst)                 { try { return toProp(propName, value, (Appendable)dst); } catch(IOException ioe) { throw new UncheckedIOException(ioe); } }
	public JsonStringify toProp(String propName, long value, Appendable dst) throws IOException {
		propName(propName, dst);
		dst.append(Long.toString(value));
		return this;
	}


	public JsonStringify toProp(String propName, double value, StringBuilder dst)                 { try { return toProp(propName, value, (Appendable)dst); } catch(IOException ioe) { throw new UncheckedIOException(ioe); } }
	public JsonStringify toProp(String propName, double value, Appendable dst) throws IOException {
		propName(propName, dst);
		dst.append(Double.toString(value));
		return this;
	}


	public JsonStringify toProp(String propName, char value, StringBuilder dst)                 { try { return toProp(propName, value, (Appendable)dst); } catch(IOException ioe) { throw new UncheckedIOException(ioe); } }
	public JsonStringify toProp(String propName, char value, Appendable dst) throws IOException {
		dst.append('"').append(propName).append("\": \"");
		StringEscapeJson.toJsonString(value, dst);
		dst.append('"');
		return this;
	}


	public JsonStringify toProp(String propName, String value, StringBuilder dst)                 { try { return toProp(propName, value, (Appendable)dst); } catch(IOException ioe) { throw new UncheckedIOException(ioe); } }
	public JsonStringify toProp(String propName, String value, Appendable dst) throws IOException {
		dst.append('"').append(propName).append("\": \"");
		StringEscapeJson.toJsonString(value, 0, value.length(), dst);
		dst.append('"');
		return this;
	}


	// ==== comma separator ====
	public JsonStringify comma(                   StringBuilder dst) { dst.append(',').append(' '); return this; }
	public JsonStringify comma(char whitespace,   StringBuilder dst) { dst.append(',').append(whitespace); return this; }
	public JsonStringify comma(String whitespace, StringBuilder dst) { dst.append(',').append(whitespace); return this; }

	public JsonStringify comma(                   Appendable dst) throws IOException { dst.append(',').append(' '); return this; }
	public JsonStringify comma(char whitespace,   Appendable dst) throws IOException { dst.append(',').append(whitespace); return this; }
	public JsonStringify comma(String whitespace, Appendable dst) throws IOException { dst.append(',').append(whitespace); return this; }

	public JsonStringify comma(                      boolean inline, StringBuilder dst)                 { return comma(true, inline, dst); }
	public JsonStringify comma(boolean includeComma, boolean inline, StringBuilder dst)                 { try { return comma(includeComma, inline, (Appendable)dst); } catch(IOException ioe) { throw new UncheckedIOException(ioe); } }
	public JsonStringify comma(                      boolean inline, Appendable dst) throws IOException { return comma(true, inline, dst); }
	public JsonStringify comma(boolean includeComma, boolean inline, Appendable dst) throws IOException {
		if(includeComma) {
			dst.append(inline ? ", " : ",\n");
		}
		else if(!inline) {
			dst.append("\n");
		}
		return this;
	}


	// ==== indent ====
	public JsonStringify indent(char indent, StringBuilder dst)                 { dst.append(indent); return this; }
	public JsonStringify indent(char indent, Appendable dst) throws IOException { dst.append(indent); return this; }

	public JsonStringify indent(char indent, int repeat, StringBuilder dst)                 { for(int i = 0; i < repeat; i++) { dst.append(indent); } return this; }
	public JsonStringify indent(char indent, int repeat, Appendable dst) throws IOException { for(int i = 0; i < repeat; i++) { dst.append(indent); } return this; }

	public JsonStringify indent(String indent, StringBuilder dst)                 { dst.append(indent); return this; }
	public JsonStringify indent(String indent, Appendable dst) throws IOException { dst.append(indent); return this; }

	public JsonStringify indent(String indent, int repeat, StringBuilder dst)                 { for(int i = 0; i < repeat; i++) { dst.append(indent); } return this; }
	public JsonStringify indent(String indent, int repeat, Appendable dst) throws IOException { for(int i = 0; i < repeat; i++) { dst.append(indent); } return this; }


	// ==== append ====
	public JsonStringify append(char ch, StringBuilder dst)                 { dst.append(ch); return this; }
	public JsonStringify append(char ch, Appendable dst) throws IOException { dst.append(ch); return this; }

	public JsonStringify append(String str, StringBuilder dst)                 { dst.append(str); return this; }
	public JsonStringify append(String str, Appendable dst) throws IOException { dst.append(str); return this; }

	public JsonStringify append(String str, int start, int end, StringBuilder dst)                 { dst.append(str, start, end); return this; }
	public JsonStringify append(String str, int start, int end, Appendable dst) throws IOException { dst.append(str, start, end); return this; }
}
