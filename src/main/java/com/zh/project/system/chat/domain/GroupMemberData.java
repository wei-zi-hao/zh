package com.zh.project.system.chat.domain;

import java.io.Serializable;
import java.util.List;

/**
 * @author eric
 */
public class GroupMemberData implements Serializable {
	private static final long serialVersionUID = 1L;

	private List<InitUser> list;

	public List<InitUser> getList() {
		return list;
	}

	public void setList(List<InitUser> list) {
		this.list = list;
	}
}
