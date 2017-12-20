package com.aiiju.util.excel;

import java.io.Serializable;

public class BaseResult implements Serializable {

	private static final long serialVersionUID = -5731480288824882639L;
	
	private Integer id;
    
    private String name;
    
    private String logo;
    
    private String letter;
    
    private String dept_ids;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getLetter() {
		return letter;
	}

	public void setLetter(String letter) {
		this.letter = letter;
	}

	public String getDept_ids() {
		return dept_ids;
	}

	public void setDept_ids(String dept_ids) {
		this.dept_ids = dept_ids;
	}

}
