package com.huhuo.integration.algorithm;
@SuppressWarnings("restriction")
public class BASE64Utils {
	/**
	 * BASE64 编码.
	 * 
	 * @param src
	 *            String inputed string
	 * @return String returned string
	 */
	public static String encode(String src) {
		sun.misc.BASE64Encoder encoder = new sun.misc.BASE64Encoder();

		return encoder.encode(src.getBytes());
	}

	/**
	 * BASE64 编码(byte[]).
	 * 
	 * @param src
	 *            byte[] inputed string
	 * @return String returned string
	 */
	public static String encode(byte[] src) {
		sun.misc.BASE64Encoder encoder = new sun.misc.BASE64Encoder();

		return encoder.encode(src);
	}

	/**
	 * BASE64 解码.
	 * 
	 * @param src
	 *            String inputed string
	 * @return String returned string
	 */
	public static String decode(String src) {
		sun.misc.BASE64Decoder decoder = new sun.misc.BASE64Decoder();

		try {
			return new String(decoder.decodeBuffer(src));
		} catch (Exception ex) {
			return null;
		}

	}

	/**
	 * BASE64 解码(to byte[]).
	 * 
	 * @param src
	 *            String inputed string
	 * @return String returned string
	 */
	public static byte[] decodeToBytes(String src) {
		sun.misc.BASE64Decoder decoder = new sun.misc.BASE64Decoder();

		try {
			return decoder.decodeBuffer(src);
		} catch (Exception ex) {
			return null;
		}

	}

}
