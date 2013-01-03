package com.huhuo.integration.util;

import javax.servlet.http.HttpServletRequest;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.Collection;
import java.util.Iterator;

/**
 * Title:
 * Description:
 * Company:易游科技
 * 
 * @author century
 * @version 1.0
 *          Date: 2006-10-14
 */
public class TestUtils {

    @SuppressWarnings("rawtypes")
	public static void testCharset(String s) {
        System.out.println("---Test Charset...");
        Collection col = Charset.availableCharsets().values();
        for (Iterator iterator = col.iterator(); iterator.hasNext();) {
            Charset charset = (Charset) iterator.next();
            CharsetDecoder decoder = charset.newDecoder();
            ByteBuffer asciiBytes = ByteBuffer.wrap(s.getBytes());
            System.out.print("Test Charset:" + charset.name() + "=");
            CharBuffer chars = null;
            try {
                chars = decoder.decode(asciiBytes);
                System.out.println(chars);
            } catch (CharacterCodingException e) {
                System.err.println("Error decoding");
            }
        }
        System.out.println("---Test Charset End");
    }

    @SuppressWarnings("rawtypes")
	public static void testGetParameter(HttpServletRequest request, String param, boolean testCharset) {
        System.out.println("---Test Get Parameter...");
        if (request.getParameter(param) == null) {
            System.out.println("Param " + param + " not found!");
            return;
        }
        Collection col = Charset.availableCharsets().values();
        for (Iterator iterator = col.iterator(); iterator.hasNext();) {
            Charset charset = (Charset) iterator.next();  /*
            CharsetDecoder decoder = charset.newDecoder();
            ByteBuffer asciiBytes = ByteBuffer.wrap(s.getBytes());*/
            System.out.print("Test Get Parameter:" + charset.name() + "=");
            //CharBuffer chars = null;
            try {
                //chars = decoder.decode(asciiBytes);
                request.setCharacterEncoding(charset.name());
                String s = request.getParameter(param);
                System.out.println(s);
                if (testCharset)
                    testCharset(s);
            } catch (Exception e) {
                System.err.println("Error decoding");
            }
        }
        System.out.println("---Test Get Parameter End");
    }


    public static void testConvert(String s) {
        System.out.println("---Test Convert");
        Object chatsets[] = Charset.availableCharsets().values().toArray();
        for (int i = 0; i < chatsets.length; i++) {
            String charset = ((Charset) chatsets[i]).name();
            for (int k = 0; k < chatsets.length; k++) {
                String charset2 = ((Charset) chatsets[k]).name();
                System.out.print(charset + "-->" + charset2 + ":" + StringUtils.convert(s, charset, charset2) + "\n");
            }
        }
        System.out.println("---Test Convert End");
    }

    public static void outPrint(Object obj) {
        java.lang.reflect.Method methods[] = obj.getClass().getMethods();
        for (int i = 0; i < methods.length; i++) {
            if (methods[i].getParameterTypes().length == 0
                    && (methods[i].getName().startsWith("get") || methods[i].getName().startsWith("is"))) {
                try {
                    System.out.println(methods[i].getName()+ ":" + obj.toString());//methods[i].invoke(obj, null).toString());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            }
        }
    }
}
