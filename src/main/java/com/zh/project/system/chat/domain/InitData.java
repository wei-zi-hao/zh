package com.zh.project.system.chat.domain;

import java.io.Serializable;
import java.util.List;

/**
 * @author eric
 */
public class InitData implements Serializable {
	private static final long serialVersionUID = 1L;

	private InitUser mine;
	private List<InitFriend> friend;
	private List<InitGroup> group;


	public InitUser getMine() {
		return mine;
	}

	public void setMine(InitUser mine) {
		this.mine = mine;
	}

	public List<InitFriend> getFriend() {
		return friend;
	}

	public void setFriend(List<InitFriend> friend) {
		this.friend = friend;
	}

	public List<InitGroup> getGroup() {
		return group;
	}

	public void setGroup(List<InitGroup> group) {
		this.group = group;
	}
}
