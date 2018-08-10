package cn.cnplay.demo.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="storage_in")
public class StorageIn extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="item_id")
	private StorageItem item;
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="department_id")
	private Department department;
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="storage_area")
	private StorageArea storageArea; 
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="store_man_id")
	private User storeman;	//保管员
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="operator_id")
	private User operator;	//经办人
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="auditor_id")
	private User auditor;	//审批人
	@Column(name="audit_result")
	private Boolean auditResult; //审批结果
	@Column(name="audit_date")
	private Date autidDate;	//审批时间
	
	public StorageItem getItem() {
		return item;
	}
	public void setItem(StorageItem item) {
		this.item = item;
	}
	public Department getDepartment() {
		return department;
	}
	public void setDepartment(Department department) {
		this.department = department;
	}
	public StorageArea getStorageArea() {
		return storageArea;
	}
	public void setStorageArea(StorageArea storageArea) {
		this.storageArea = storageArea;
	}
	public User getStoreman() {
		return storeman;
	}
	public void setStoreman(User storeman) {
		this.storeman = storeman;
	}
	public User getOperator() {
		return operator;
	}
	public void setOperator(User operator) {
		this.operator = operator;
	}
	public User getAuditor() {
		return auditor;
	}
	public void setAuditor(User auditor) {
		this.auditor = auditor;
	}
	public Boolean getAuditResult() {
		return auditResult;
	}
	public void setAuditResult(Boolean auditResult) {
		this.auditResult = auditResult;
	}
	public Date getAutidDate() {
		return autidDate;
	}
	public void setAutidDate(Date autidDate) {
		this.autidDate = autidDate;
	}

	
}
