package andrews.ying.compare;

public class BinaryUtils {
    public BinaryUtils() {
    }

    public static int toInt(byte[] bytes) {
        int count = Math.min(4, bytes.length);
        int value = 0;

        for(int i = 0; i < count; ++i) {
            value <<= 8;
            value |= bytes[i] & 255;
        }

        return value;
    }

    public static long toLong(byte[] bytes) {
        int count = Math.min(8, bytes.length);
        long value = 0L;

        for(int i = 0; i < count; ++i) {
            value <<= 8;
            value |= (long)bytes[i] & 255L;
        }

        return value;
    }
}