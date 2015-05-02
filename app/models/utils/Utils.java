package models.utils;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Random;

public class Utils {
	private static final char[] symbols = new char[62];
	private static final Random random = new Random(System.currentTimeMillis());
	static {
		for (int idx = 0; idx < 10; ++idx) {
			symbols[idx] = (char) ('0' + idx);
		}
		for (int idx = 10; idx < 36; ++idx) {
			symbols[idx] = (char) ('a' + idx - 10);
		}
		for (int idx = 36; idx < 62; ++idx) {
			symbols[idx] = (char) ('A' + idx - 36);
		}
	}

	public static String generateRandomString(int length) {
		StringBuilder sb = new StringBuilder();

		for(int i=0; i<length; i++) {
			sb.append(symbols[Math.abs(Utils.random.nextInt())%symbols.length]);
		}

		return sb.toString();
	}

	public static String generateRandomString(int minlength, int maxlength) {
		StringBuilder sb = new StringBuilder();

		int rand = (int)(((long)(Utils.random.nextDouble()*(long)((long)maxlength - (long)minlength + 1))) + minlength);
		for(int i=0; i<rand; i++) {
			sb.append(symbols[Math.abs(Utils.random.nextInt())%symbols.length]);
		}

		return sb.toString();
	}

	public static long generateRandomLong() {
		return Utils.random.nextLong();
	}

	public static long generateRandomLong(long min, long max) {
		return (((long)(Utils.random.nextDouble()*(max - min + 1))) + min);
	}

	public static int generateRandomInt() {
		return Utils.random.nextInt();
	}

	public static int generateRandomInt(int min, int max) {
		return (int)(((int)(Utils.random.nextDouble()*(int)((int)max - (int)min + 1))) + min);
	}

	public static short generateRandomShort() {
		return (short)Utils.random.nextInt();
	}

	public static short generateRandomShort(short min, short max) {
		return (short)(((short)(Utils.random.nextDouble()*(short)((short)max - (short)min + 1))) + min);
	}

	public static double generateRandomDouble() {
		return (double)Utils.random.nextDouble();
	}

	public static double generateRandomDouble(double min, double max) {
		return (double)(((double)(Utils.random.nextDouble()*(double)((double)min - (double)min + 1))) + min);
	}

	public static float generateRandomFloat() {
		return (float)Utils.random.nextFloat();
	}

	public static float generateRandomFloat(float min, float max) {
		return (float)(((float)(Utils.random.nextDouble()*(float)((float)min - (float)min + 1))) + min);
	}

	public static Calendar generateRandomDate() {
		Calendar cal = GregorianCalendar.getInstance();
		cal.setTimeInMillis(Utils.generateRandomLong(1100000000000L,1300000000000L));
		return cal;
	}

	public static boolean generateRandomBoolean(double prob_true) {
		if(Utils.random.nextDouble() < prob_true) {
			return true;
		} else {
			return false;
		}
	}

	public static String toHexString(byte[] bytes) {
		char[] hexArray = {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
		char[] hexChars = new char[bytes.length * 2];
		int v;
		for ( int j = 0; j < bytes.length; j++ ) {
			v = bytes[j] & 0xFF;
			hexChars[j*2] = hexArray[v/16];
			hexChars[j*2 + 1] = hexArray[v%16];
		}
		return new String(hexChars);
	}

}