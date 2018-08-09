package cn.cnplay.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "user_token_log")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
public class UserTokenLog extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="user_id")
	private User user;
	@Column(name="jwt_token")
	private String jwtToken;
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getJwtToken() {
		return jwtToken;
	}
	public void setJwtToken(String jwtToken) {
		this.jwtToken = jwtToken;
	}
	
	
	
}
