package com.glaway.sddq.tools.mobiletools.mobileModel;

/**
 * 
 * 移动端数据库字段对象类
 * 
 * @author zhuhao
 * @version [版本号, 2019年1月10日]
 * @since [产品/模块版本]
 */
public class MobileColumn {

	public MobileColumn(String attribute, fieldType type, String searchColumn,
			String[] lookColumns, String description) {
		this.attribute = attribute;
		this.type = type;
		this.searchColumn = searchColumn;
		this.lookColumns = lookColumns;// 该属性目前仅前两个有用。[0]:查询有效字段。[1]:字段描述
		this.description = description;// 属性描述

	}

	public enum fieldType {
		TEXT, SELECT, TEXTAREA, SWITCH, DATETIME
	}

	private String attribute;
	private String value;
	private String status;
	private fieldType type;
	private String searchColumn;
	private String[] lookColumns;
	private boolean isEnableEdit = true;
	private String description;

	public String getAttribute() {
		return attribute;
	}

	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public fieldType getType() {
		return type;
	}

	public void setType(fieldType type) {
		this.type = type;
	}

	public String getSearchColumn() {
		return searchColumn;
	}

	public void setSearchColumn(String searchColumn) {
		this.searchColumn = searchColumn;
	}

	public String[] getLookColumns() {
		return lookColumns;
	}

	public void setLookColumns(String[] lookColumns) {
		this.lookColumns = lookColumns;
	}

	public boolean isEnableEdit() {
		return isEnableEdit;
	}

	public void setEnableEdit(boolean isEnableEdit) {
		this.isEnableEdit = isEnableEdit;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
