package org.SUSCOM.Util;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class Localization {
	

	private final ResourceBundle bundle;


	private Localization(ResourceBundle bundle) {
		super();
		this.bundle = bundle;
	}


	public ResourceBundle getBundle() {
		return bundle;
	}


	public Locale getLocale() {
		return bundle.getLocale();
	}


	public String getString(String key) {
		try {
			if (bundle == null) {
				return key;
			} else {
				return bundle.getString(key);
			}
		} catch (MissingResourceException e) {
			return key;
		}
	}
	

	public boolean containsKey(String key) {
		if (bundle == null) {
			return false;
		}
			
		return bundle.containsKey(key);
	}


	public String getString(String key, Object... arguments) {
		MessageFormat format = new MessageFormat(getString(key), 
				getLocale());

		return format.format(arguments, new StringBuffer(), null).toString();
	}


	public static Localization getLocalization(String packageName) {
		return getLocalization(packageName, Locale.getDefault());
	}


	public static Localization getLocalization(String packageName,
			Locale locale) {
		ResourceBundle bundle = null;

		try {
			bundle = ResourceBundle.getBundle(packageName + ".LocalStrings", 
					locale);
		} catch (MissingResourceException e) {
		}

		return new Localization(bundle);
	}


	public static Localization getLocalization(Class<?> type) {
		return getLocalization(type.getPackage().getName());
	}

	public static Localization getLocalization(Class<?> type, Locale locale) {
		return getLocalization(type.getPackage().getName(), locale);
	}
	

	public static boolean containsKey(Class<?> type, String key) {
		return getLocalization(type).containsKey(type.getSimpleName() + "." +
				key);
	}

	public static String getString(Class<?> type, String key) {
		return getLocalization(type).getString(type.getSimpleName() + "." +
				key);
	}


	public static String getString(Class<?> type, String key, 
			Object... arguments) {
		return getLocalization(type).getString(type.getSimpleName() + "." + key,
				arguments);
	}


}
