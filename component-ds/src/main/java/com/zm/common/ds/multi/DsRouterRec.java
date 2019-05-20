package com.zm.common.ds.multi;

import java.util.List;

public class DsRouterRec {
	private String cat, url, username, password;
	/** 门店代码集合 */
	private List<String> storecodes;
	/** 门店别名代码集合 */
	private List<String> aliascodes;

	public String getCat() {
		return cat;
	}

	public void setCat(String cat) {
		this.cat = cat;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<String> getStorecodes() {
		return storecodes;
	}

	public void setStorecodes(List<String> storecodes) {
		this.storecodes = storecodes;
	}

	public List<String> getAliascodes() {
		return aliascodes;
	}

	public void setAliascodes(List<String> aliascodes) {
		this.aliascodes = aliascodes;
	}

}
