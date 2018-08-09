package cn.cnplay.demo.config;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWTSigner;
import com.auth0.jwt.JWTVerifier;

import cn.cnplay.demo.model.User;

@Component
public class JwtHelper {
	private final static Logger LOG = LoggerFactory.getLogger(JwtHelper.class);
	
	@Value("${jwt.secret}")
	private String secret;
	@Value("${jwt.issuser}")
	private String issuser;
	@Value("${jwt.exprie}")
	private int expire=-1;
	
	
	public String createToken(User user){
		JWTSigner signer = new JWTSigner(secret);
		HashMap<String, Object> claims = new HashMap<String, Object>();
		Calendar now = Calendar.getInstance();
		claims.put("iat", now.getTimeInMillis()/1000L);
		if(expire>0){
			now.add(Calendar.MINUTE, expire);
			claims.put("exp", now.getTimeInMillis()/1000L);
		}
		claims.put("iss", issuser);
		claims.put("id", user.getId());
		claims.put("userName", user.getUsername());
		claims.put("name", user.getName());
		claims.put("timestamp", System.currentTimeMillis());
		return signer.sign(claims);
	}
	
	public User verifyToken(String token){
		try {
			final JWTVerifier verifier = new JWTVerifier(secret);
			final Map<String, Object> claims= verifier.verify(token);
			User user = new User();
			user.setId(claims.get("id").toString());
			user.setName(claims.get("name").toString());
			user.setUsername(claims.get("userName").toString());
			return user;
		} catch (Exception e){
			if(LOG.isDebugEnabled()){
				LOG.debug("令牌校验失败",e);
			}
			return null;
		}
	}
	
	
	public String getSecret() {
		return secret;
	}
	public void setSecret(String secret) {
		this.secret = secret;
	}
	public String getIssuser() {
		return issuser;
	}
	public void setIssuser(String issuser) {
		this.issuser = issuser;
	}
	public int getExpire() {
		return expire;
	}
	public void setExpire(int expire) {
		this.expire = expire;
	}
	
	
	
}
