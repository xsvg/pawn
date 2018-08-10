package cn.cnplay.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "department")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
public class Department extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Column(name="dept_no")
	private String deptNo;
	@Column(name="dept_name")
	private String deptName;
	@Column(name="dept_type")
	private Integer deptType; // 1:管理机构,2:网点,3:
	
	@ManyToOne(fetch=FetchType.LAZY,targetEntity=Department.class)
	@JoinColumn(name="parent_id")
	private Department parent;
	@Column(name="deleted")
	private Boolean deleted;
	
	public String getDeptNo() {
		return deptNo;
	}
	public void setDeptNo(String deptNo) {
		this.deptNo = deptNo;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public Integer getDeptType() {
		return deptType;
	}
	public void setDeptType(Integer deptType) {
		this.deptType = deptType;
	}
	public Department getParent() {
		return parent;
	}
	public void setParent(Department parent) {
		this.parent = parent;
	}
	public Boolean getDeleted() {
		return deleted;
	}
	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}
	
	

}
