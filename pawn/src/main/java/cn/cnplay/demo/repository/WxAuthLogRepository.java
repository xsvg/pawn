package cn.cnplay.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cn.cnplay.demo.model.WxAuthLog;

@Repository
public interface WxAuthLogRepository extends JpaRepository<WxAuthLog, String>
{

}
