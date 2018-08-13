package cn.cnplay.demo.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.cnplay.demo.model.Department;
import cn.cnplay.demo.model.User;
import cn.cnplay.demo.repository.DepartmentRepository;
import cn.cnplay.demo.repository.UserRepository;
import cn.cnplay.demo.utils.ResultVOUtil;
import cn.cnplay.demo.utils.Sha1Util;
import cn.cnplay.demo.vo.ResultVo;

@Service
public class UserService {

	private final static String DEFAULT_PSWD = "88888888";
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired DepartmentRepository deptRepository;
	
	@Autowired EntityManager entityManager;
	
	@Transactional
	public ResultVo<?> add(User user){
		if(StringUtils.isBlank(user.getUsername())){
			return ResultVOUtil.error("用户账号不能为空");
		}
		List<User> list = userRepository.findByUsernameAndDeleted(user.getUsername(), false);
		if(list.size()>0){
			return ResultVOUtil.error("账号已存在");
		}
		if(StringUtils.isBlank(user.getName())){
			return ResultVOUtil.error("用户姓名不能为空");
		}
		if(StringUtils.isBlank(user.getUserType())){
			return ResultVOUtil.error("用户类型不能为空");
		}
		if(user.getDepartment()!=null&&StringUtils.isNotBlank(user.getDepartment().getId())){
			Department department = deptRepository.findOne(user.getDepartment().getId());
			if(department==null){
				return ResultVOUtil.error("机构不存在");
			}
		}else{
			user.setDepartment(null);
		}
		byte[] salt = Sha1Util.generateSalt();
		String password = Hex.encodeHexString(salt)+ Hex.encodeHexString(Sha1Util.encrypt(DEFAULT_PSWD.getBytes(), salt));
		user.setPassword(password);
		user = userRepository.save(user);
		return ResultVOUtil.success(user);
	}
	
	
	@Transactional
	public ResultVo<User> update(User user){
		User userPo = userRepository.findOne(user.getId());
		if(userPo==null){
			return ResultVOUtil.error("更新失败,用户不存在或已被删除");
		}else{
			if(StringUtils.isBlank(user.getName())){
				return ResultVOUtil.error("用户姓名不能为空");
			}
			if(StringUtils.isBlank(user.getUserType())){
				return ResultVOUtil.error("用户类型不能为空");
			}
			userPo.setName(user.getName());
			userPo.setUserType(user.getUserType());
			if(user.getDepartment()!=null&&StringUtils.isNotBlank(user.getDepartment().getId())){
				Department department = deptRepository.findOne(user.getDepartment().getId());
				if(department==null){
					return ResultVOUtil.error("机构不存在");
				}
				userPo.setDepartment(department);
			}else{
				userPo.setDepartment(null);
			}
			userPo.setTimeUpdate(System.currentTimeMillis());
			userPo.setAddress(user.getAddress());
			userPo.setContactNumber(user.getContactNumber());
			userPo.setMemo(user.getMemo());
			if(StringUtils.isNotBlank(user.getPassword())){
				String password = user.getPassword();
				byte[] salt = Sha1Util.generateSalt();
				password = Hex.encodeHexString(salt)+ Hex.encodeHexString(Sha1Util.encrypt(password.getBytes(), salt));
				userPo.setPassword(password);
			}
			userRepository.save(userPo);
			return ResultVOUtil.success();
		}
	}
	
	@Transactional
	public ResultVo<?> updatePswd(String userId,String oldPswd,String newPswd){
		User user = userRepository.findOne(userId);
		if(user==null){
			return ResultVOUtil.error("用户不存在");
		}else{
			String password = user.getPassword();
			String saltStr = StringUtils.substring(password, 0, 16);
			password = StringUtils.substring(password, 16);
			String encrypt = Sha1Util.encrypt(oldPswd, saltStr);
			if(StringUtils.equals(password, encrypt)){
				encrypt = Sha1Util.encrypt(newPswd, saltStr);
				password = saltStr + encrypt;
				user.setPassword(password);
				userRepository.save(user);
				return ResultVOUtil.success();
			}else{
				return ResultVOUtil.error("原密码错误");
			}
		}
	}
	
	@Transactional
	public ResultVo<User> delete(String id){
		User user = userRepository.findOne(id);
		if(user==null){
			return ResultVOUtil.error("用户不存在");
		}
		user.setDeleted(true);
		userRepository.save(user);
		return ResultVOUtil.success();
	}

	public ResultVo<?> list(User filter,Integer page,Integer pageSize){
		StringBuilder builder = new StringBuilder();
		List<Object> params = new ArrayList<>();
		builder.append(" and user.deleted = ?");
		params.add(false);
		if(filter!=null){
			if(StringUtils.isNotBlank(filter.getUsername())){
				builder.append(" and user.username like ?");
				params.add("%"+filter.getUsername()+"%");
			}
			if(StringUtils.isNotBlank(filter.getName())){
				builder.append(" and user.name like ?");
				params.add("%"+filter.getName()+"%");
			}
			if(StringUtils.isNotBlank(filter.getUserType())){
				builder.append(" and user.userType = ?");
				params.add(filter.getUserType());
			}
			if(filter.getDepartment()!=null&&StringUtils.isNotBlank(filter.getDepartment().getId())){
				builder.append(" and user.department.id = ?");
				params.add(filter.getDepartment().getId());
			}
		}
		String criteria = builder.toString();
		builder.setLength(0);
		criteria = StringUtils.substringAfter(criteria, "and");
		StringBuilder countHql = new StringBuilder("select count(user.id) from User user");
		StringBuilder hql = new StringBuilder("from User user");
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
		if(page==null||pageSize==null){
			list = q.getResultList();
			total = list.size();
		}else{
			total = qCount.getSingleResult();
			q.setFirstResult((page-1)*pageSize);
			q.setMaxResults(page*pageSize);
		}
		list = q.getResultList();
		Map<String,Object> data = new HashMap<>();
		data.put("total", total==null?0:total);
		data.put("rows", list);
		return ResultVOUtil.success(data);
	}
}
