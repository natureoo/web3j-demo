package utils;

import org.web3j.utils.Numeric;

import static org.web3j.utils.Numeric.cleanHexPrefix;

/**
 * @author nature
 * @date 14/9/21 下午3:06
 * @email 924943578@qq.com
 */
public class Util {

//    public static String toHexString(byte[] array) {
//        return DatatypeConverter.printHexBinary(array);
//    }
//
//    public static byte[] hexToByteArray(String s) {
//        return DatatypeConverter.parseHexBinary(s);
//    }

    public static String toHexString(byte[] array) {
        return  Numeric.toHexString(array);
    }


    public static byte[] hexToByteArray(String input) {
        String cleanInput = cleanHexPrefix(input);

        int len = cleanInput.length();

        if (len == 0) {
            return new byte[] {};
        }

        byte[] data;
        int startIdx;
        if (len % 2 != 0) {
            data = new byte[(len / 2) + 1];
            data[0] = (byte) Character.digit(cleanInput.charAt(0), 16);
            startIdx = 1;
        } else {
            data = new byte[len / 2];
            startIdx = 0;
        }

        for (int i = startIdx; i < len; i += 2) {
            data[(i + 1) / 2] =
                    (byte)
                            ((Character.digit(cleanInput.charAt(i), 16) << 4)
                                    + Character.digit(cleanInput.charAt(i + 1), 16));
        }
        return data;
    }
}
