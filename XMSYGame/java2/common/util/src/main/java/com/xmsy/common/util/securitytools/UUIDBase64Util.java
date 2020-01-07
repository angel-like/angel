package com.xmsy.common.util.securitytools;

import java.util.UUID;

public class UUIDBase64Util {

	public static void main(String[] args) {
		UUID uuid = UUID.randomUUID();
		System.out.println("UUID String: " + uuid.toString());
		System.out.println("Number of Bytes: " + uuid.toString().getBytes().length);
		System.out.println(getUUIDBase64(uuid));

//		byte[] uuidArr = asByteArray(uuid);
//		System.out.print("UUID Byte Array: ");
//		for (byte b : uuidArr) {
//			System.out.print(b + " ");
//		}
//		System.out.println();
//		System.out.println("Number of Bytes: " + uuidArr.length);
//		System.out.println();
//
//		try {
//			// Convert a byte array to base64 string
//			String s = new sun.misc.BASE64Encoder().encode(uuidArr);
//			System.out.println("UUID Base64 String: " + s);
//			System.out.println("Number of Bytes: " + s.getBytes().length);
//			System.out.println();
//
//			String trimmed = s.split("=")[0];
//			System.out.println("UUID Base64 String Trimmed: " + trimmed);
//			System.out.println("Number of Bytes: " + trimmed.getBytes().length);
//			System.out.println();
//
//			// Convert base64 string to a byte array
//			byte[] backArr = new sun.misc.BASE64Decoder().decodeBuffer(trimmed);
//			System.out.print("Back to UUID Byte Array: ");
//			for (byte b : backArr) {
//				System.out.print(b + " ");
//			}
//			System.out.println();
//			System.out.println("Number of Bytes: " + backArr.length);
//
//			byte[] fixedArr = new byte[16];
//			for (int i = 0; i < 16; i++) {
//				fixedArr[i] = backArr[i];
//			}
//			System.out.println();
//			System.out.print("Fixed UUID Byte Array: ");
//			for (byte b : fixedArr) {
//				System.out.print(b + " ");
//			}
//			System.out.println();
//			System.out.println("Number of Bytes: " + fixedArr.length);
//
//			System.out.println();
//			UUID newUUID = toUUID(fixedArr);
//			System.out.println("UUID String: " + newUUID.toString());
//			System.out.println("Number of Bytes: " + newUUID.toString().getBytes().length);
//			System.out.println();
//
//			System.out.println("Equal to Start UUID? " + newUUID.equals(uuid));
//			if (!newUUID.equals(uuid)) {
//				System.exit(0);
//			}
//
//		} catch (IOException e) {
//		}

	}

	public static String getUUIDBase64(UUID uuid) {
		byte[] uuidArr = asByteArray(uuid);
		String result = new String(Base64.encode(uuidArr));
		return result.split("=")[0];
	}

	public static String getUUIDBase64() {
		UUID uuid = UUID.randomUUID();
		return getUUIDBase64(uuid);
	}

	public static byte[] asByteArray(UUID uuid) {

		long msb = uuid.getMostSignificantBits();
		long lsb = uuid.getLeastSignificantBits();
		byte[] buffer = new byte[16];

		for (int i = 0; i < 8; i++) {
			buffer[i] = (byte) (msb >>> 8 * (7 - i));
		}
		for (int i = 8; i < 16; i++) {
			buffer[i] = (byte) (lsb >>> 8 * (7 - i));
		}

		return buffer;

	}

	public static UUID toUUID(byte[] byteArray) {

		long msb = 0;
		long lsb = 0;
		for (int i = 0; i < 8; i++)
			msb = (msb << 8) | (byteArray[i] & 0xff);
		for (int i = 8; i < 16; i++)
			lsb = (lsb << 8) | (byteArray[i] & 0xff);
		UUID result = new UUID(msb, lsb);

		return result;
	}

}
