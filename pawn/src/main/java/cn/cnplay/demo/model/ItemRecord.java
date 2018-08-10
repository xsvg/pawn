package cn.cnplay.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="item_record")
public class ItemRecord extends BaseEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Column(name="record_desc")
	private String recordDesc;	//记录描述
	@Column(name="ref_record_id")
	private String refRecordId; //关联记录ID
	public String getRecordDesc() {
		return recordDesc;
	}
	public void setRecordDesc(String recordDesc) {
		this.recordDesc = recordDesc;
	}
	public String getRefRecordId() {
		return refRecordId;
	}
	public void setRefRecordId(String refRecordId) {
		this.refRecordId = refRecordId;
	}

	
}
