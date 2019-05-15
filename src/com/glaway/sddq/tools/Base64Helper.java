package com.glaway.sddq.tools;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import org.apache.commons.codec.binary.Base64;

public class Base64Helper {
	public static String getImageStr(String imgFile) throws UnsupportedEncodingException {
		InputStream in = null;
		byte[] data = null;
		try {
			in = new FileInputStream(imgFile);
			data = new byte[in.available()];
			in.read(data);
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Base64 encoder = new Base64();
		return new String(encoder.encode(data), "UTF-8");
	}

	public static String getBase64Str(byte[] data) throws UnsupportedEncodingException {
		Base64 encoder = new Base64();
		return new String(encoder.encode(data), "UTF-8");
	}

	public static byte[] getByte(String base64) {
		Base64 decoder = new Base64();
		return decoder.decode(base64);
	}

	public static boolean generateImage(String imgFile, String base64) {
		if ((imgFile == null) || (base64 == null)) {
			return false;
		}
		Base64 decoder = new Base64();
		try {
			byte[] b = decoder.decode(base64);
			for (int i = 0; i < b.length; i++) {
				if (b[i] < 0) {
					int tmp44_42 = i;
					byte[] tmp44_41 = b;
					tmp44_41[tmp44_42] = ((byte) (tmp44_41[tmp44_42] + 256));
				}
			}
			OutputStream out = new FileOutputStream(imgFile);
			out.write(b);
			out.flush();
			out.close();
			return true;
		} catch (Exception e) {

		}
		return false;
	}
}
