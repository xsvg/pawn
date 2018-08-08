package cn.cnplay.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cn.cnplay.demo.model.WxMessageLog;

@Repository
public interface WxMessageLogRepository extends JpaRepository<WxMessageLog, String>
{

}
