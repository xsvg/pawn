package cn.cnplay.demo.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="storage_transfer")
public class StorageTransfer extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="from_dept_id")
	public Department from;
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="to_dept_id")
	public Department to;
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="item_id")
	public StorageItem item;
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="from_operator_id")
	public User fromOperator;
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="from_auditor_id")
	public User fromAuditor;
	@Column(name="from_audit_result")
	public Boolean fromAuditResult;
	@Column(name="from_audit_date")
	public Date fromAuditDate;
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="to_operator_id")
	public User toOperator;
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="to_auditor_id")
	public User toAuditor;
	@Column(name="to_audit_result")
	public Boolean toAuditResult;
	@Column(name="to_audit_date")
	public Date toAuditDate;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="storage_area_id")
	public StorageArea storageArea;
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="storeaman_id")
	public User storeman;
	
	public Department getFrom() {
		return from;
	}
	public void setFrom(Department from) {
		this.from = from;
	}
	public Department getTo() {
		return to;
	}
	public void setTo(Department to) {
		this.to = to;
	}
	public StorageItem getItem() {
		return item;
	}
	public void setItem(StorageItem item) {
		this.item = item;
	}
	public User getFromOperator() {
		return fromOperator;
	}
	public void setFromOperator(User fromOperator) {
		this.fromOperator = fromOperator;
	}
	public User getFromAuditor() {
		return fromAuditor;
	}
	public void setFromAuditor(User fromAuditor) {
		this.fromAuditor = fromAuditor;
	}
	public Boolean getFromAuditResult() {
		return fromAuditResult;
	}
	public void setFromAuditResult(Boolean fromAuditResult) {
		this.fromAuditResult = fromAuditResult;
	}
	public Date getFromAuditDate() {
		return fromAuditDate;
	}
	public void setFromAuditDate(Date fromAuditDate) {
		this.fromAuditDate = fromAuditDate;
	}
	public User getToOperator() {
		return toOperator;
	}
	public void setToOperator(User toOperator) {
		this.toOperator = toOperator;
	}
	public User getToAuditor() {
		return toAuditor;
	}
	public void setToAuditor(User toAuditor) {
		this.toAuditor = toAuditor;
	}
	public Boolean getToAuditResult() {
		return toAuditResult;
	}
	public void setToAuditResult(Boolean toAuditResult) {
		this.toAuditResult = toAuditResult;
	}
	public Date getToAuditDate() {
		return toAuditDate;
	}
	public void setToAuditDate(Date toAuditDate) {
		this.toAuditDate = toAuditDate;
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
	
	
	
}
