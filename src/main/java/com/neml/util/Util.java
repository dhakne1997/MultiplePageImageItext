package com.neml.util;

public class Util {

	public static boolean isNeitherNullNorEmpty(Object obj) {
		boolean isNeitherNullNorEmpty = true;
		if (obj == null) {
			isNeitherNullNorEmpty = false;
		}
		if (obj instanceof String && ((String) obj).equals("")) {
			isNeitherNullNorEmpty = false;
		}
		if (obj instanceof String && ((String) obj).equals("null")) {
			isNeitherNullNorEmpty = false;
		}
		return isNeitherNullNorEmpty;
	}
}
