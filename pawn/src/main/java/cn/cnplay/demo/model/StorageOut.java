package cn.cnplay.demo.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="storage_out")
public class StorageOut extends BaseEntity {

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
	@Column(name="confirm_id")
	private Boolean confirmId; //身份证原件确认
	@Column(name="take_Back_Certificate")
	private Boolean takeBackCertificate ; //抵押凭证客户联是否收回
	@Column(name="paid_off")
	private Boolean paidOff; //贷款是否还清
	@Column(name="sign_off")
	private Boolean selfSign; //是否本人签名
	
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
	public Boolean getConfirmId() {
		return confirmId;
	}
	public void setConfirmId(Boolean confirmId) {
		this.confirmId = confirmId;
	}
	public Boolean getTakeBackCertificate() {
		return takeBackCertificate;
	}
	public void setTakeBackCertificate(Boolean takeBackCertificate) {
		this.takeBackCertificate = takeBackCertificate;
	}
	public Boolean getPaidOff() {
		return paidOff;
	}
	public void setPaidOff(Boolean paidOff) {
		this.paidOff = paidOff;
	}
	public Boolean getSelfSign() {
		return selfSign;
	}
	public void setSelfSign(Boolean selfSign) {
		this.selfSign = selfSign;
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
