package cn.cnplay.demo.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="storage_item")
public class StorageItem extends BaseEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Column(name="contract_no")
	private String contractNo;	//贷款合同号
	@Column(name="owner_id")
	private String ownerId;	//抵押物所有人身份证号
	@Column(name="owner_name")
	private String ownerName;	//抵押物所有人姓名
	@Column(name="rgister_date")
	private String registerDate;	//登记日期
	@Column(name="contract_start_date")
	private String contractStartDate; //合同起始日期
	@Column(name="contract_end_date")
	private String contractEndDate; //合同到期日期
	@Column(name="certificate_no")
	private String certificateNo; //产权证号码
	@Column(name="borrower_name")
	private String borrowerName;  //借款人姓名
	@Column(name="borrower_id")
	private String borrowerId;  //借款人身份证号
	@Column(name="assess_money")
	private BigDecimal assessMoney;	//评估金额
	@Column(name="actural_money")
	private BigDecimal actualMoney; //借款金额
	@Column(name="status")
	private Integer status;	//状态  1:在库 2:出库 3:转移 4:缺失 5:完成
	public String getContractNo() {
		return contractNo;
	}
	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}
	public String getOwnerId() {
		return ownerId;
	}
	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}
	public String getOwnerName() {
		return ownerName;
	}
	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}
	public String getRegisterDate() {
		return registerDate;
	}
	public void setRegisterDate(String registerDate) {
		this.registerDate = registerDate;
	}
	public String getContractStartDate() {
		return contractStartDate;
	}
	public void setContractStartDate(String contractStartDate) {
		this.contractStartDate = contractStartDate;
	}
	public String getContractEndDate() {
		return contractEndDate;
	}
	public void setContractEndDate(String contractEndDate) {
		this.contractEndDate = contractEndDate;
	}
	public String getCertificateNo() {
		return certificateNo;
	}
	public void setCertificateNo(String certificateNo) {
		this.certificateNo = certificateNo;
	}
	public String getBorrowerName() {
		return borrowerName;
	}
	public void setBorrowerName(String borrowerName) {
		this.borrowerName = borrowerName;
	}
	public String getBorrowerId() {
		return borrowerId;
	}
	public void setBorrowerId(String borrowerId) {
		this.borrowerId = borrowerId;
	}
	public BigDecimal getAssessMoney() {
		return assessMoney;
	}
	public void setAssessMoney(BigDecimal assessMoney) {
		this.assessMoney = assessMoney;
	}
	public BigDecimal getActualMoney() {
		return actualMoney;
	}
	public void setActualMoney(BigDecimal actualMoney) {
		this.actualMoney = actualMoney;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	
}
