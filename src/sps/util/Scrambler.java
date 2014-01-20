package sps.util;

//This isn't intended to be a full-stop encryption to prevent tampering.
//Just put up a small barrier for people poking around
// Basic XOR cipher
public class Scrambler {
    private static final String __key = "sps-gamelib";
    private static final int __keyLength = __key.length();

    public static String scramble(String plainText) {
        StringBuffer buffer = new StringBuffer(plainText);
        for (int ii = 0, jj = 0; ii < plainText.length(); ii++, jj++) {
            jj %= __keyLength;
            buffer.setCharAt(ii, (char) (plainText.charAt(ii) ^ __key.charAt(jj)));
        }

        return buffer.toString();
    }

    public static String descramble(String scrambledText) {
        return scramble(scrambledText);
    }
}
