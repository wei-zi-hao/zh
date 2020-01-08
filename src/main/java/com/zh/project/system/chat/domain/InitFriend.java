package com.zh.project.system.chat.domain;

import java.io.Serializable;
import java.util.List;

/**
 * @author eric
 */
public class InitFriend implements Serializable {
	private static final long serialVersionUID = 1L;

	private String groupname;
	private Integer id;
	private Integer online;
	private List<InitUser> list;

	public String getGroupname() {
		return groupname;
	}

	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getOnline() {
		return online;
	}

	public void setOnline(Integer online) {
		this.online = online;
	}

	public List<InitUser> getList() {
		return list;
	}

	public void setList(List<InitUser> list) {
		this.list = list;
	}
}
