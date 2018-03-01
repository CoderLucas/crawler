package com.lujh.util;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by lujianhao on 2018/2/4.
 */
public class IdCodeUtil {
    /**
     *
     */
    private static final Hashids ASK_DEFAULT = new Hashids("crawler");

    public static String encode(int id) {
        return ASK_DEFAULT.encode(id);
    }

    public static int decode(String code) {
        if (StringUtils.isBlank(code)) {
            return 0;
        }
        long[] ids = ASK_DEFAULT.decode(code.trim());
        if (ArrayUtils.isNotEmpty(ids)) {
            return Long.valueOf(ids[0]).intValue();
        }
        return 0;
    }

    public static void main(String[] args) {
        System.out.println(encode(1));
    }

    /**
     * 支持将数值（或数值数组），自定义的编码成字符串；再通过字符串解码。
     * <p>
     * 编码后字符串的长度可变，minHashLength，可以最小长度
     * alphabet：指定编码成字符串所使用的字符集
     * Hashids a = new Hashids("salt", 0 , "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890");
     * long[] b = a.decode(a.encode(1L, 2L));
     */
    public static class Hashids {
        /**
         * Max number that can be encoded with Hashids.
         */
        public static final long MAX_NUMBER = 9007199254740992L;

        private static final String DEFAULT_ALPHABET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        private static final String DEFAULT_SEPS = "cfhistuCFHISTU";
        private static final String DEFAULT_SALT = "";

        private static final int DEFAULT_MIN_HASH_LENGTH = 0;
        private static final int MIN_ALPHABET_LENGTH = 16;
        private static final double SEP_DIV = 3.5;
        private static final int GUARD_DIV = 12;
        private static final Pattern ENCODE_HEX_PATTERN = Pattern.compile("[\\w\\W]{1,12}");
        private static final String HEX_REGEX = "^[0-9a-fA-F]+$";

        private final String salt;
        private final int minHashLength;
        private final String alphabet;
        private final String seps;
        private final String guards;

        public Hashids() {
            this(DEFAULT_SALT);
        }

        public Hashids(String salt) {
            this(salt, 0);
        }

        public Hashids(String salt, int minHashLength) {
            this(salt, minHashLength, DEFAULT_ALPHABET);
        }

        public Hashids(String salt, int minHashLength, String alphabet) {
            this.salt = salt != null ? salt : DEFAULT_SALT;
            this.minHashLength = minHashLength > 0 ? minHashLength : DEFAULT_MIN_HASH_LENGTH;

            final StringBuilder uniqueAlphabet = new StringBuilder();
            for (int i = 0; i < alphabet.length(); i++) {
                if (uniqueAlphabet.indexOf(String.valueOf(alphabet.charAt(i))) == -1) {
                    uniqueAlphabet.append(alphabet.charAt(i));
                }
            }

            alphabet = uniqueAlphabet.toString();

            if (alphabet.length() < MIN_ALPHABET_LENGTH) {
                throw new IllegalArgumentException(
                        "alphabet must contain at least " + MIN_ALPHABET_LENGTH + " unique characters");
            }

            if (alphabet.contains(StringUtils.SPACE)) {
                throw new IllegalArgumentException("alphabet cannot contains spaces");
            }

            // seps should contain only characters present in alphabet;
            // alphabet should not contains seps
            String seps = DEFAULT_SEPS;
            for (int i = 0; i < seps.length(); i++) {
                final int j = alphabet.indexOf(seps.charAt(i));
                if (j == -1) {
                    seps = seps.substring(0, i) + " " + seps.substring(i + 1);
                } else {
                    alphabet = alphabet.substring(0, j) + " " + alphabet.substring(j + 1);
                }
            }

            alphabet = alphabet.replaceAll("\\s+", "");
            seps = seps.replaceAll("\\s+", "");
            seps = Hashids.consistentShuffle(seps, this.salt);

            if ((seps.isEmpty()) || (((float) alphabet.length() / seps.length()) > SEP_DIV)) {
                int sepsLen = (int) Math.ceil(alphabet.length() / SEP_DIV);

                if (sepsLen == 1) {
                    sepsLen++;
                }

                if (sepsLen > seps.length()) {
                    final int diff = sepsLen - seps.length();
                    seps += alphabet.substring(0, diff);
                    alphabet = alphabet.substring(diff);
                } else {
                    seps = seps.substring(0, sepsLen);
                }
            }

            alphabet = Hashids.consistentShuffle(alphabet, this.salt);
            // use double to round up
            final int guardCount = (int) Math.ceil((double) alphabet.length() / GUARD_DIV);

            String guards;
            if (alphabet.length() < 3) {
                guards = seps.substring(0, guardCount);
                seps = seps.substring(guardCount);
            } else {
                guards = alphabet.substring(0, guardCount);
                alphabet = alphabet.substring(guardCount);
            }
            this.guards = guards;
            this.alphabet = alphabet;
            this.seps = seps;
        }

        /**
         * Encode numbers to string
         *
         * @param numbers the numbers to encode
         * @return the encoded string
         */
        public String encode(long... numbers) {
            if (numbers.length == 0) {
                return "";
            }

            for (final long number : numbers) {
                if (number < 0) {
                    return "";
                }
                if (number > MAX_NUMBER) {
                    throw new IllegalArgumentException("number can not be greater than " + MAX_NUMBER + "L");
                }
            }
            return this._encode(numbers);
        }

        /**
         * Decode string to numbers
         *
         * @param hash the encoded string
         * @return decoded numbers
         */
        public long[] decode(String hash) {
            if (hash.isEmpty()) {
                return new long[0];
            }

            String validChars = this.alphabet + this.guards + this.seps;
            for (int i = 0; i < hash.length(); i++) {
                if (validChars.indexOf(hash.charAt(i)) == -1) {
                    return new long[0];
                }
            }

            return this._decode(hash, this.alphabet);
        }

        /**
         * Encode hexa to string
         *
         * @param hexa the hexa to encode
         * @return the encoded string
         */
        public String encodeHex(String hexa) {
            if (!hexa.matches(HEX_REGEX)) {
                return "";
            }

            final List<Long> matched = new ArrayList<Long>();

            final Matcher matcher = ENCODE_HEX_PATTERN.matcher(hexa);

            while (matcher.find()) {
                matched.add(Long.parseLong("1" + matcher.group(), 16));
            }

            // conversion
            final long[] result = new long[matched.size()];
            for (int i = 0; i < matched.size(); i++) {
                result[i] = matched.get(i);
            }

            return this.encode(result);
        }

        /**
         * Decode string to numbers
         *
         * @param hash the encoded string
         * @return decoded numbers
         */
        public String decodeHex(String hash) {
            final StringBuilder result = new StringBuilder();
            final long[] numbers = this.decode(hash);

            for (final long number : numbers) {
                result.append(Long.toHexString(number).substring(1));
            }

            return result.toString();
        }

        public static int checkedCast(long value) {
            final int result = (int) value;
            if (result != value) {
                // don't use checkArgument here, to avoid boxing
                throw new IllegalArgumentException("Out of range: " + value);
            }
            return result;
        }

        /* Private methods */

        private String _encode(long... numbers) {
            long numberHashInt = 0;
            for (int i = 0; i < numbers.length; i++) {
                numberHashInt += (numbers[i] % (i + 100));
            }
            String alphabet = this.alphabet;
            final char ret = alphabet.charAt((int) (numberHashInt % alphabet.length()));

            long num;
            long sepsIndex, guardIndex;
            String buffer;
            final StringBuilder retSb = new StringBuilder(this.minHashLength);
            retSb.append(ret);
            char guard;

            for (int i = 0; i < numbers.length; i++) {
                num = numbers[i];
                buffer = ret + this.salt + alphabet;

                alphabet = Hashids.consistentShuffle(alphabet, buffer.substring(0, alphabet.length()));
                final String last = Hashids.hash(num, alphabet);

                retSb.append(last);

                if (i + 1 < numbers.length) {
                    if (last.length() > 0) {
                        num %= (last.charAt(0) + i);
                        sepsIndex = (int) (num % this.seps.length());
                    } else {
                        sepsIndex = 0;
                    }
                    retSb.append(this.seps.charAt((int) sepsIndex));
                }
            }

            String retStr = retSb.toString();
            if (retStr.length() < this.minHashLength) {
                guardIndex = (numberHashInt + (retStr.charAt(0))) % this.guards.length();
                guard = this.guards.charAt((int) guardIndex);

                retStr = guard + retStr;

                if (retStr.length() < this.minHashLength) {
                    guardIndex = (numberHashInt + (retStr.charAt(2))) % this.guards.length();
                    guard = this.guards.charAt((int) guardIndex);

                    retStr += guard;
                }
            }

            final int halfLen = alphabet.length() / 2;
            while (retStr.length() < this.minHashLength) {
                alphabet = Hashids.consistentShuffle(alphabet, alphabet);
                retStr = alphabet.substring(halfLen) + retStr + alphabet.substring(0, halfLen);
                final int excess = retStr.length() - this.minHashLength;
                if (excess > 0) {
                    final int startPos = excess / 2;
                    retStr = retStr.substring(startPos, startPos + this.minHashLength);
                }
            }

            return retStr;
        }

        private long[] _decode(String hash, String alphabet) {
            final ArrayList<Long> ret = new ArrayList<Long>();

            int i = 0;
            final String regexp = "[" + this.guards + "]";
            String hashBreakdown = hash.replaceAll(regexp, " ");
            String[] hashArray = hashBreakdown.split(" ");

            if (hashArray.length == 3 || hashArray.length == 2) {
                i = 1;
            }

            if (hashArray.length > 0) {
                hashBreakdown = hashArray[i];
                if (!hashBreakdown.isEmpty()) {
                    final char lottery = hashBreakdown.charAt(0);

                    hashBreakdown = hashBreakdown.substring(1);
                    hashBreakdown = hashBreakdown.replaceAll("[" + this.seps + "]", " ");
                    hashArray = hashBreakdown.split(" ");

                    String subHash, buffer;
                    for (final String aHashArray : hashArray) {
                        subHash = aHashArray;
                        buffer = lottery + this.salt + alphabet;
                        alphabet = Hashids.consistentShuffle(alphabet, buffer.substring(0, alphabet.length()));
                        ret.add(Hashids.unhash(subHash, alphabet));
                    }
                }
            }

            // transform from List<Long> to long[]
            long[] arr = new long[ret.size()];
            for (int k = 0; k < arr.length; k++) {
                arr[k] = ret.get(k);
            }

            if (!this.encode(arr).equals(hash)) {
                arr = new long[0];
            }

            return arr;
        }

        private static String consistentShuffle(String alphabet, String salt) {
            if (salt.length() <= 0) {
                return alphabet;
            }

            int ascVal, j;
            final char[] tmpArr = alphabet.toCharArray();
            for (int i = tmpArr.length - 1, v = 0, p = 0; i > 0; i--, v++) {
                v %= salt.length();
                ascVal = salt.charAt(v);
                p += ascVal;
                j = (ascVal + v + p) % i;
                final char tmp = tmpArr[j];
                tmpArr[j] = tmpArr[i];
                tmpArr[i] = tmp;
            }

            return new String(tmpArr);
        }

        private static String hash(long input, String alphabet) {
            String hash = "";
            final int alphabetLen = alphabet.length();

            do {
                final int index = (int) (input % alphabetLen);
                if (index >= 0 && index < alphabet.length()) {
                    hash = alphabet.charAt(index) + hash;
                }
                input /= alphabetLen;
            } while (input > 0);

            return hash;
        }

        private static Long unhash(String input, String alphabet) {
            long number = 0, pos;

            for (int i = 0; i < input.length(); i++) {
                pos = alphabet.indexOf(input.charAt(i));
                number = number * alphabet.length() + pos;
            }

            return number;
        }

        /**
         * Get Hashid algorithm version.
         *
         * @return Hashids algorithm version implemented.
         */
        public String getVersion() {
            return "1.0.0";
        }
    }
}
