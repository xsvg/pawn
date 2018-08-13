package cn.cnplay.demo.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.cnplay.demo.model.UserHandleLog;
import cn.cnplay.demo.repository.UserHandleLogRepository;
import cn.cnplay.demo.utils.ResultVOUtil;
import cn.cnplay.demo.vo.ResultVo;

@Service
@Transactional
public class UserHandleLogService {

	@Autowired UserHandleLogRepository handleLogRepository;
	
	@Autowired EntityManager entityManager;
	
	public UserHandleLog addLog(UserHandleLog log){
		return handleLogRepository.save(log);
	}
	
	public ResultVo<?> list(UserHandleLog filter,Integer pageNo,Integer pageSize){
		StringBuilder builder = new StringBuilder();
		List<Object> params = new ArrayList<>();
		if(filter!=null){
			if(filter.getUser()!=null){
				if(StringUtils.isNotBlank(filter.getUser().getUsername())){
					builder.append(" and (log.user.username = ? or log.userNo = ?)");
					params.add(filter.getUser().getUsername());
					params.add(filter.getUser().getUsername());
				}
				if(StringUtils.isNotBlank(filter.getUser().getName())){
					builder.append(" and log.user.name like ? ");
					params.add("%"+filter.getUser().getUsername()+"%");
				}
			}
		}
		String criteria = builder.toString();
		builder.setLength(0);
		criteria = StringUtils.substringAfter(criteria, "and");
		StringBuilder countHql = new StringBuilder("select count(log.id) from UserHandleLog log");
		StringBuilder hql = new StringBuilder("from UserHandleLog log");
		if(StringUtils.isNotBlank(criteria)){
			countHql.append(" where ").append(criteria);
			hql.append(" where ").append(criteria);
		}
		Query q = entityManager.createQuery(hql.toString());
		Query qCount = entityManager.createQuery(countHql.toString());
		int i=1;
		for(Object param:params){
			q.setParameter(i, param);
			qCount.setParameter(i, param);
			i++;
		}
		Object total = null;
		List<?> list = null;
		if(pageNo==null||pageSize==null){
			list = q.getResultList();
			total = list.size();
		}else{
			total = qCount.getSingleResult();
			q.setFirstResult((pageNo-1)*pageSize);
			q.setMaxResults(pageNo*pageSize);
		}
		list = q.getResultList();
		Map<String,Object> data = new HashMap<>();
		data.put("total", total==null?0:total);
		data.put("rows", list);
		return ResultVOUtil.success(data);
	}
}
