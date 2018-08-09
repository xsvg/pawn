package cn.cnplay.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import cn.cnplay.demo.model.UserTokenLog;

@Repository
public interface UserTokenLogRepository extends JpaRepository<UserTokenLog, String>
{

	@Query("from UserTokenLog token where token.user.id=?1 and not exists (select b.id from UserTokenLog b where b.user.id = token.user.id and b.timeCreate>token.timeCreate)")
	UserTokenLog getUserToken(String userId);
}
