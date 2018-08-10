package cn.cnplay.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="storage_area")
public class StorageArea extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Column(name="area_no")
	private String areaNo;
	@Column(name="area_name")
	private String areaName;
	@Column(name="elecLabel")
	private String elecLabel;
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="parent_id")
	private StorageArea parentArea;
	@Column(name="deleted")
	private Boolean deleted = false;
	
	public String getAreaNo() {
		return areaNo;
	}
	public void setAreaNo(String areaNo) {
		this.areaNo = areaNo;
	}
	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	public String getElecLabel() {
		return elecLabel;
	}
	public void setElecLabel(String elecLabel) {
		this.elecLabel = elecLabel;
	}
	public StorageArea getParentArea() {
		return parentArea;
	}
	public void setParentArea(StorageArea parentArea) {
		this.parentArea = parentArea;
	}
	public Boolean getDeleted() {
		return deleted;
	}
	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}
	
	
}
