package StreamReader;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

/**
 * Class for Convert ByteArrayInputStream into String
 * Created by Sergey on 16.02.2016.
 */
public class StreamToString {
    /**
     * Method for convert ByteArrayInputStream into String
     *
     * @param stream ByteArrayInputStream that need  convert into stream
     * @return String of data from ByteArrayInputStream
     */
    public static String ConvertStream(ByteArrayInputStream stream) {
        int size = stream.available();
        byte[] bytes = new byte[size];
        stream.read(bytes, 0, size);
        String Data = new String(bytes, StandardCharsets.UTF_8); // Or any encoding.
        return Data;
    }
}
