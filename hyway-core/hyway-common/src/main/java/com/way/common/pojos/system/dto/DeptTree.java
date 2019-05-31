package com.way.common.pojos.system.dto;

public class DeptTree extends TreeNode {
    private String name;
    
    private String label;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
    
}
