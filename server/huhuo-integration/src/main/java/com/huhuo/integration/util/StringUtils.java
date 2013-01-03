package com.huhuo.integration.util;
/**
 * <p>
 * Title:  工具类
 * </p>
 * <p>
 * Description: 对基本的字符或是字符串进行操作的一个基类
 * </p>
 * 
 * <p>
 * Company: eu
 * </p>
 * <p>
 * Creat Date:2006-7-30
 * 
 * @version 1.0
 */
import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Vector;

/**
 * Utility class to peform common String manipulation algorithms.
 */
public class StringUtils {

    /**
     * Initialization lock for the whole class. Init's only happen once per
     * class load so this shouldn't be a bottleneck.
     */
    private static Object initLock = new Object();
    private static final char[] BR_TAG = "<BR>".toCharArray();

    /**
     * Replaces all instances of oldString with newString in line.
     *
     * @param line      the String to search to perform replacements on
     * @param oldString the String that should be replaced by newString
     * @param newString the String that will replace all instances of oldString
     * @return a String will all instances of oldString replaced by newString
     */
    public static final String replace(String line, String oldString, String newString) {
        int i = 0;
        if ((i = line.indexOf(oldString, i)) >= 0) {
            char[] line2 = line.toCharArray();
            char[] newString2 = newString.toCharArray();
            int oLength = oldString.length();
            StringBuffer buf = new StringBuffer(line2.length);
            buf.append(line2, 0, i).append(newString2);
            i += oLength;
            int j = i;
            while ((i = line.indexOf(oldString, i)) > 0) {
                buf.append(line2, j, i - j).append(newString2);
                i += oLength;
                j = i;
            }
            buf.append(line2, j, line2.length - j);
            return buf.toString();
        }
        return line;
    }

    /**
     * Replaces all instances of oldString with newString in line.
     * The count Integer is updated with number of replaces.
     *
     * @param line      the String to search to perform replacements on
     * @param oldString the String that should be replaced by newString
     * @param newString the String that will replace all instances of oldString
     * @return a String will all instances of oldString replaced by newString
     */
    public static final String replace(String line, String oldString, String newString,
                                       Integer count) {
        if (line == null) {
            return null;
        }
        int i = 0;
        if ((i = line.indexOf(oldString, i)) >= 0) {
            int counter = 0;
            counter++;
            char[] line2 = line.toCharArray();
            char[] newString2 = newString.toCharArray();
            int oLength = oldString.length();
            StringBuffer buf = new StringBuffer(line2.length);
            buf.append(line2, 0, i).append(newString2);
            i += oLength;
            int j = i;
            while ((i = line.indexOf(oldString, i)) > 0) {
                counter++;
                buf.append(line2, j, i - j).append(newString2);
                i += oLength;
                j = i;
            }
            buf.append(line2, j, line2.length - j);
            count = new Integer(counter);
            return buf.toString();
        }
        return line;
    }

    private final static String LT = "&lt;";
    private final static String GT = "&gt;";
    private final static String D_QUTO = "&quot;";
    private final static String S_QUTO = "&#039;";

    /**
     * This method takes a string which may contain HTML tags (ie, &lt;b&gt;,
     * &lt;table&gt',";, etc) and converts the '&lt'' and '&gt; ' \",\' characters to
     * their HTML escape sequences.
     *
     * @param input the text to be converted.
     * @return the input string with the characters '&lt;' and '&gt;' replaced
     *         with their HTML escape sequences.
     */
    public static final String escapeHTMLTags(String input) {        
        if (input == null || input.length() == 0) {
            return input;
        }        
        StringBuffer buf = new StringBuffer(input.length());
        char ch = ' ';
        for (int i = 0; i < input.length(); i++) {
            ch = input.charAt(i);
            if (ch == '<') {
                buf.append(LT);
            } else if (ch == '>') {
                buf.append(GT);
            } else if (ch == '\'') {
                buf.append(S_QUTO);
            } else if (ch == '"') {
                buf.append(D_QUTO);
            } else {
                buf.append(ch);
            }
        }
        return buf.toString();
    }

    /**
     * 对String.Replace(regx,repl)中的repl参数转义
     */
    public static final String escapeRepl(String input) {       
        if (input == null || input.length() == 0) {
            return input;
        }
        return input.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$");
    }

    /**
     * 把 url 中要传输的等号 转换成 %3D
     *
     * @param input
     * @return
     */
    public static final String escapeUrlTags(String input) {        
        if (input == null || input.length() == 0) {
            return input;
        }
        StringBuffer buf = new StringBuffer(input.length());
        char ch = ' ';
        for (int i = 0; i < input.length(); i++) {
            ch = input.charAt(i);
            if (ch == '=') {
                buf.append("%3D");
            } else if (ch == '\'') {
                buf.append(S_QUTO);
            } else if (ch == '"') {
                buf.append(D_QUTO);
            } else
                buf.append(ch);
        }
        return buf.toString();
    }

    /**
     * 把',"转义成 \',\"
     *
     * @param input
     * @return
     */
    public static final String escapeJScriptTags(String input) {
        if (input == null || input.length() == 0) {
            return input;
        }
        StringBuffer buf = new StringBuffer(input.length());
        char ch = ' ';
        for (int i = 0; i < input.length(); i++) {
            ch = input.charAt(i);
            if (ch == '\'') {
                buf.append("\\'");
            } else if (ch == '"') {
                buf.append("\\\"");
            } else {
                buf.append(ch);
            }
        }
        return buf.toString();
    }

    /**
     * Used by the hash method.
     */
    private static MessageDigest digest = null;
   
    public synchronized static final String hash(String data) {
        if (digest == null) {
            try {
                digest = MessageDigest.getInstance("MD5");
            } catch (NoSuchAlgorithmException nsae) {
                System.err.println("Failed to load the MD5 MessageDigest. " +
                        "Jive will be unable to function normally.");
                nsae.printStackTrace();
            }
        }       
        digest.update(data.getBytes());
        return toHex(digest.digest());
    }

    /**
     * Turns an array of bytes into a String representing each byte as an
     * unsigned hex number.
     * <p/>
     * Method by Santeri Paavolainen, Helsinki Finland 1996<br>
     * (c) Santeri Paavolainen, Helsinki Finland 1996<br>
     * Distributed under LGPL.
     *
     * @param hash an rray of bytes to convert to a hex-string
     * @return generated hex string
     */
    public static final String toHex(byte hash[]) {
        StringBuffer buf = new StringBuffer(hash.length * 2);
        int i;

        for (i = 0; i < hash.length; i++) {
            if (((int) hash[i] & 0xff) < 0x10) {
                buf.append("0");
            }
            buf.append(Long.toString((int) hash[i] & 0xff, 16));
        }
        return buf.toString();
    }

    /**
     * Converts a line of text into an array of lower case words. Words are
     * delimited by the following characters: , .\r\n:/\+
     * <p/>
     * In the future, this method should be changed to use a
     * BreakIterator.wordInstance(). That class offers much more fexibility.
     *
     * @param text a String of text to convert into an array of words
     * @return text broken up into an array of words.
     */
    public static final String[] toLowerCaseWordArray(String text) {
        StringTokenizer tokens = new StringTokenizer(text, " ,\r\n.:/\\+");
        String[] words = new String[tokens.countTokens()];
        for (int i = 0; i < words.length; i++) {
            words[i] = tokens.nextToken().toLowerCase();
        }
        return words;
    }

    /**
     * A list of some of the most common words. For searching and indexing, we
     * often want to filter out these words since they just confuse searches.
     * The list was not created scientifically so may be incomplete :)
     */
    private static final String[] commonWords = new String[]{
        "a", "and", "as", "at", "be", "do", "i", "if", "in", "is", "it", "so",
        "the", "to"
    };
    
	private static Map commonWordsMap = null;

    /**
     * Returns a new String array with some of the most common English words
     * removed. The specific words removed are: a, and, as, at, be, do, i, if,
     * in, is, it, so, the, to
     */
    public static final String[] removeCommonWords(String[] words) {
        if (commonWordsMap == null) {
            synchronized (initLock) {
                if (commonWordsMap == null) {
                    commonWordsMap = new HashMap();
                    for (int i = 0; i < commonWords.length; i++) {
                        commonWordsMap.put(commonWords[i], commonWords[i]);
                    }
                }
            }
        }       
        ArrayList results = new ArrayList(words.length);
        for (int i = 0; i < words.length; i++) {
            if (!commonWordsMap.containsKey(words[i])) {
                results.add(words[i]);
            }
        }
        return (String[]) results.toArray(new String[results.size()]);
    }

    /**
     * 输出转换
     */
    public static final String parseNormal(String in) {
        String s = "";
        if (in != null)
            s = in;
        return s;
    }

    public static final String parseNormal(Date in) {
        String s = "";
        if (in != null)
            s = in.toString();
        return s;
    }

    public static final String parseNormal(double in) {
        String s;
        int ilen, dlen, index;
        char ch[];
        s = Double.toString(in);
        index = s.indexOf('E');
        if (index >= 0) {
            ilen = Integer.parseInt(s.substring(index + 1));
            dlen = index - ilen - 2;
            ch = s.toCharArray();
            s = s.copyValueOf(ch, 0, 1);
            if (ilen < index - 2) {
                s += s.copyValueOf(ch, 2, ilen);
            } else {
                s += s.copyValueOf(ch, 2, index - 2);
                for (int i = 0; i < ilen - index + 2; i++) {
                    s += "0";
                }
            }
            if (dlen > 0) {
                s += ".";
                s += s.copyValueOf(ch, 2 + ilen, dlen);
            }
        }
        return s;
    }

    public static final String parseNormal(int in) {
        String s = "";
        s = Integer.toString(in);
        return s;
    }

    public static final String parseNormal(long in) {
        String s = "";
        int ilen, index;
        char ch[];
        s = Long.toString(in);
        index = s.indexOf('E');
        if (index >= 0) {
            ilen = Integer.parseInt(s.substring(index + 1));
            ch = s.toCharArray();
            s = s.copyValueOf(ch, 0, 1);
            if (ilen < index - 2) {
                s += s.copyValueOf(ch, 2, ilen);
            } else {
                s += s.copyValueOf(ch, 2, index - 2);
                for (int i = 0; i < ilen - index + 2; i++) {
                    s += "0";
                }
            }
        }
        return s;
    }

    /**
     * 中文字符集转换
     */

    public static final String parseChinese(String in) {
        String s = null;
        byte temp [];
        if (in == null) {
            return null;
        }
        try {
            temp = in.getBytes("iso-8859-1");
            s = new String(temp);
        } catch (UnsupportedEncodingException e) {
        	e.printStackTrace();
        }
        return s;
    }

    /**
     * 中文字符集转换
     */

    public static final String getChinese(String in) {
        String s = null;
        byte temp [];
        if (in == null) {
            return null;
        }
        try {
            temp = in.getBytes("GBK");
            s = new String(temp);
        } catch (UnsupportedEncodingException e) {
        	e.printStackTrace();
        }
        return s;
    }

    /**
     * Oracle查询字符窜转换
     */
    public static final String parseOracleQuery(String in) {
        return in.replace('*', '%');
    }

    /**
     * Oracle查询字符窜转换
     */
    public static final String escapeSQL(String in) {
        if (in == null)
            return null;
        return in.replaceAll("'", "''");
    }

    /**
     * 按分隔符号读出字符串的内容
     *
     * @param strlist 含有分隔符号的字符串
     * @param ken     分隔符号
     * @return 列表
     */
	public static final Vector parseStringToVector(String strlist, String ken) {
        StringTokenizer st = new StringTokenizer(strlist, ken);

        if (strlist == null || strlist.equals("") || st.countTokens() <= 0) {
            return new Vector();
        }

        int size = st.countTokens();
        Vector strv = new Vector();

        for (int i = 0; i < size; i++) {
            String nextstr = st.nextToken();
            if (!nextstr.equals("")) {
                strv.add(nextstr);
            }
        }
        return strv;
    }

    /**
     * 按分隔符号读出字符串的内容
     *
     * @param strlist 含有分隔符号的字符串
     * @param ken     分隔符号
     * @return 列表
     */
    public static final ArrayList parseStringToArrayList(String strlist, String ken) {
        StringTokenizer st = new StringTokenizer(strlist, ken);

        if (strlist == null || strlist.equals("") || st.countTokens() <= 0) {
            return new ArrayList();
        }

        int size = st.countTokens();
        ArrayList strv = new ArrayList();

        for (int i = 0; i < size; i++) {
            String nextstr = st.nextToken();
            if (!nextstr.equals("")) {
                strv.add(nextstr);
            }
        }
        return strv;
    }

    public static String convertNewlines(String input) {
        char[] chars = input.toCharArray();
        int cur = 0;
        int len = chars.length;
        StringBuffer buf = new StringBuffer(len);       
        for (int i = 0; i < len; i++) {          
            if (chars[i] == '\n') {
                buf.append(chars, cur, i - cur).append(BR_TAG);
                cur = i + 1;
            }         
            else if (chars[i] == '\r' && i < len - 1 && chars[i + 1] == '\n') {
                buf.append(chars, cur, i - cur).append(BR_TAG);
                i++;
                cur = i + 1;
            }
        }     
        buf.append(chars, cur, len - cur);
        return buf.toString();
    }

    /**
     * Breaks up words that are longer than <tt>maxCount</tt> with spaces.
     *
     * @param input the String to check for long words in.
     * @return a new String with words broken apart as necessary.
     */
    public static String createBreaks(String input) {
        char[] chars = input.toCharArray();
        int len = chars.length;
        StringBuffer buf = new StringBuffer(len);
        int count = 0;
        int cur = 0;     
        for (int i = 0; i < len; i++) {         
            if (chars[i] == '\n') {
                buf.append(chars, cur, i - cur).append(BR_TAG);
                cur = i + 1;
            }           
            else if (chars[i] == '\r' && i < len - 1 && chars[i + 1] == '\n') {
                buf.append(chars, cur, i - cur).append(BR_TAG);
                i++;
                cur = i + 1;
            }

            count++;
        }      
        buf.append(chars, cur, len - cur);
        return buf.toString();
    }


    /**
     * ISO_8859_1---->GBK
     *
     * @param s
     * @return
     */
    public static String UnicodeToChinese(String s) {
        try {
            if (s == null || s.equals("")) return "";
            String newstring = null;
            newstring = new String(s.getBytes("ISO_8859_1"), "GBK");
            return newstring;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return s;
        }
    }

    /**
     * gb2312---->ISO8859_1
     *
     * @param s
     * @return
     */
    public static String ChineseToUnicode(String s) {
        try {
            if (s == null || s.equals("")) return "";
            String newstring = null;
            newstring = new String(s.getBytes("gb2312"), "ISO8859_1");
            return newstring;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return s;
        }
    }

    /**
     * gb2312---->UTF8
     *
     * @param s
     * @return
     */
    public static String ChineseToUTF8(String s) {
        try {
            if (s == null || s.equals("")) return "";
            String newstring = null;
            newstring = new String(s.getBytes("gb2312"), "UTF-8");
            return newstring;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return s;
        }
    }

    /**
     * gb2312---->ISO8859_1
     *
     * @param s
     * @return
     */
    public static String encodeUTF8(String s) {
        try {
            if (s == null || s.equals("")) return "";
            s = URLEncoder.encode(s, "UTF-8");
            s = s.replaceAll("\\+", "%20");
            return s;
        } catch (Exception e) {
            e.printStackTrace();
            return s;
        }
    }

    /**
     *
     * @param line      the String to search to perform replacements on
     * @param oldString the String that should be replaced by newString
     * @param newString the String that will replace all instances of oldString
     * @param count     a value that will be updated with the number of replaces
     *                  performed.
     * @return a String will all instances of oldString replaced by newString
     */
    public static final String replaceIgnoreCase(String line, String oldString,
                                                 String newString, int[] count) {
        if (line == null) {
            return null;
        }
        String lcLine = line.toLowerCase();
        String lcOldString = oldString.toLowerCase();
        int i = 0;
        if ((i = lcLine.indexOf(lcOldString, i)) >= 0) {
            int counter = 0;
            char[] line2 = line.toCharArray();
            char[] newString2 = newString.toCharArray();
            int oLength = oldString.length();
            StringBuffer buf = new StringBuffer(line2.length);
            buf.append(line2, 0, i).append(newString2);
            i += oLength;
            int j = i;
            while ((i = lcLine.indexOf(lcOldString, i)) > 0) {
                counter++;
                buf.append(line2, j, i - j).append(newString2);
                i += oLength;
                j = i;
            }
            buf.append(line2, j, line2.length - j);
            count[0] = counter;
            return buf.toString();
        }
        return line;
    }

    /**
     * Replaces all instances of oldString with newString in line.
     * The count Integer is updated with number of replaces.
     *
     * @param line      the String to search to perform replacements on
     * @param oldString the String that should be replaced by newString
     * @param newString the String that will replace all instances of oldString
     * @return a String will all instances of oldString replaced by newString
     */
    public static final String replace(String line, String oldString,
                                       String newString, int[] count) {
        if (line == null) {
            return null;
        }
        int i = 0;
        if ((i = line.indexOf(oldString, i)) >= 0) {
            int counter = 0;
            counter++;
            char[] line2 = line.toCharArray();
            char[] newString2 = newString.toCharArray();
            int oLength = oldString.length();
            StringBuffer buf = new StringBuffer(line2.length);
            buf.append(line2, 0, i).append(newString2);
            i += oLength;
            int j = i;
            while ((i = line.indexOf(oldString, i)) > 0) {
                counter++;
                buf.append(line2, j, i - j).append(newString2);
                i += oLength;
                j = i;
            }
            buf.append(line2, j, line2.length - j);
            count[0] = counter;
            return buf.toString();
        }
        return line;
    }

    /**
     * convert values to be string with @param splt as separator, with @param prex as prefix, with @param suf as suffix
     * @param values
     * @param splt
     * @param prex
     * @param suf
     * @return
     */
    public static String getArrayString(String[] values, String splt, String prex, String suf) {
        StringBuffer s = new StringBuffer();
        String exp = null;
        int size = values.length;
        for (int i = 0; i < size; i++) {
            s.append(prex + values[i] + suf);
            if (i < size - 1)
                s.append(splt);
        }
        exp = s.toString();
        return exp;
    }
    

    public static String getArrayString(String[] values, String splt, String prex) {
        return getArrayString(values, splt, prex, "");
    }

    public static String getArrayString(String[] values, String splt) {
        return getArrayString(values, splt, "");
    }
    
    /**
     * 读取文件文本
     *
     * @param fileName 文件名
     * @return 文件文本
     * @
     */
    public static String getFileTxt(String fileName) {
        String txt = null;
        try {
            FileInputStream fs = new FileInputStream(fileName);
            byte buff[] = new byte[fs.available()];
            fs.read(buff);
            String s = new String(buff);
            txt = s.trim();
            fs.close();
        } catch (Exception e) {
            System.out.print("\nError in read file:" + e.getMessage());
        }
        return txt;
    }

    /**
     * 读取指定url的内容
     *
     * @param url
     * @return
     * @throws Exception
     */
    public static String getUrlTxt(String url) throws Exception {
        String sLine = "";
        String sText = "";
        java.io.InputStream l_urlStream;
        java.net.URL l_url = new java.net.URL(url);
        java.net.HttpURLConnection l_connection = (java.net.HttpURLConnection) l_url.openConnection();
        l_connection.connect();
        l_urlStream = l_connection.getInputStream();
        java.io.BufferedReader l_reader = new java.io.BufferedReader(new java.io.InputStreamReader(l_urlStream));
        while ((sLine = l_reader.readLine()) != null) {
            sText += sLine;
        }
        return sText;
    }

    public static boolean isNull(String s) {
        return s == null || s.trim().length() == 0;
    }

    public static String convert(String s, String srcCharset, String destCharset) {
        if (s == null) return null;      
        Charset charset = Charset.forName(srcCharset);
        CharsetDecoder decoder = charset.newDecoder();
        ByteBuffer asciiBytes = ByteBuffer.wrap(s.getBytes());
        CharBuffer chars = null;
        try {
            chars = decoder.decode(asciiBytes);
        } catch (Exception e) {
            System.err.println("Error decoding:" + srcCharset);
            return s;
        }

        charset = Charset.forName(destCharset);
        CharsetEncoder encoder = charset.newEncoder();
        ByteBuffer ret = null;
        try {
            ret = encoder.encode(chars);
        } catch (CharacterCodingException e) {
            System.err.println("Error encoding:" + destCharset);
            return s;
        }
        return new String(ret.array());
    }  
}
