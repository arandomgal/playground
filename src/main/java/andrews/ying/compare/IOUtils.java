package andrews.ying.compare;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class IOUtils extends org.apache.commons.io.IOUtils {
    protected IOUtils() {
    }

    public static byte[] readBytes(InputStream inputStream, int size) {
        byte[] bytes = new byte[size];

        try {
            int count = inputStream.read(bytes);
            if (size != count) {
                throw new RuntimeException("Premature end of stream");
            } else {
                return bytes;
            }
        } catch (IOException var4) {
            throw new RuntimeException(var4.getMessage(), var4);
        }
    }

    public static ByteArrayInputStream readBuffer(InputStream inputStream, int bufSize) {
        byte[] bytes = readBytes(inputStream, bufSize);
        return new ByteArrayInputStream(bytes);
    }

    public static String readString(InputStream inputStream, int size, String expected) {
        String value = readString(inputStream, size);
        if (!expected.equals(value)) {
            throw new RuntimeException("Unexpected contents: " + value);
        } else {
            return value;
        }
    }

    public static String readString(InputStream inputStream, int size) {
        return new String(readBytes(inputStream, size));
    }

    public static long readUInt32(InputStream inputStream) {
        byte[] bytes = readBytes(inputStream, 4);
        return (long)BinaryUtils.toInt(bytes);
    }

    public static long readLong64(InputStream inputStream) {
        byte[] bytes = readBytes(inputStream, 8);
        return BinaryUtils.toLong(bytes);
    }

    public static int readUShort16(InputStream inputStream) {
        byte[] bytes = readBytes(inputStream, 2);
        return BinaryUtils.toInt(bytes);
    }

    public static void skipExact(InputStream inputStream, long length) {
        try {
            long count;
            for(count = 0L; count < length && inputStream.available() > 0; count += inputStream.skip(length - count)) {
            }

            if (count != length) {
                throw new RuntimeException("Premature end of stream");
            }
        } catch (IOException var5) {
            throw new RuntimeException(var5.getMessage(), var5);
        }
    }
}
