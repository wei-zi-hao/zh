package com.zh.project.system.chat.domain;

import java.io.Serializable;

/**
 * @author eric
 */
public class InitGroup implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String groupname;
	private String avatar;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getGroupname() {
		return groupname;
	}

	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
}
