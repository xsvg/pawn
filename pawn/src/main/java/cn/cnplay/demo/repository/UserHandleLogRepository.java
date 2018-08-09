package cn.cnplay.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cn.cnplay.demo.model.UserHandleLog;

@Repository
public interface UserHandleLogRepository extends JpaRepository<UserHandleLog, String>
{

}
