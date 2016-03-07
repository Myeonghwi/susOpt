package org.SUSCOM.DB;

import org.SUSCOM.Util.Localization;

public class ConnectInformation {
	
	private static Localization localization = Localization.getLocalization(
			ConnectInformation.class);
	
	private String url;

	private String user;

	private String password;
	
	public ConnectInformation() {
		
		url = localization.getString("text.url");
		user = localization.getString("text.user");
		password = localization.getString("text.password");
		
	}
	
	public String getURL() {
		return url;
	}
	
	public String getUser() {
		return user;
	}
	
	public String getPassword() {
		return password;
	}

}
