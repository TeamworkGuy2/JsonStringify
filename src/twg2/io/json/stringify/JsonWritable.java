package twg2.io.json.stringify;

import java.io.IOException;

/** An object which can written itself in JSON format to an {@link Appendable} destination
 * @author TeamworkGuy2
 * @since 2016-10-06
 */
public interface JsonWritable<T> {

	public void toJson(Appendable dst, T params) throws IOException;

}
