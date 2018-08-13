package cn.cnplay.demo.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.cnplay.demo.model.Department;
import cn.cnplay.demo.repository.DepartmentRepository;
import cn.cnplay.demo.utils.ResultVOUtil;
import cn.cnplay.demo.vo.ResultVo;

@Service
@Transactional
public class DepartmentService {
	@Autowired DepartmentRepository departmentRepository;
	
	public ResultVo<Department> get(String id){
		Department dept = departmentRepository.findOne(id);
		if(dept==null){
			return ResultVOUtil.error("机构信息不存在");
		}else{
			return ResultVOUtil.success(dept);
		}
	}
	

	public ResultVo<?> add(Department dept){
		dept.setDeleted(false);
		if(StringUtils.isBlank(dept.getDeptNo())){
			return ResultVOUtil.error("机构编号不能为空");
		}
		if(StringUtils.isBlank(dept.getDeptName())){
			return ResultVOUtil.error("机构名称不能为空");
		}
		if(dept.getDeptType()==null){
			return ResultVOUtil.error("机构类型不能为空");
		}
		if(StringUtils.isNotBlank(dept.getParent().getId())){
			Department parent = departmentRepository.findOne(dept.getParent().getId());
			if(parent==null){
				return ResultVOUtil.error("上级机构不存在");
			}
			if(2==parent.getDeptType() || 3==parent.getDeptType()){
				return ResultVOUtil.error("网点和库房不能建立下级机构");
			}
			final List<Department> list = new ArrayList<>();
			list.add(parent);
			getParents(parent.getId(),list);
			if(list.size()>=5){
				return ResultVOUtil.error("机构层级最多5层");
			}
		}else{
			dept.setParent(null);
		}
		departmentRepository.save(dept);
		return ResultVOUtil.success();
	}
	
	public ResultVo<?> update(Department dept){
		Department po = departmentRepository.findOne(dept.getId());
		if(po==null){
			return ResultVOUtil.error("机构不存在");
		}
		if(StringUtils.isBlank(dept.getDeptNo())){
			return ResultVOUtil.error("机构编号不能为空");
		}
		po.setDeptNo(dept.getDeptNo());
		if(StringUtils.isBlank(dept.getDeptName())){
			return ResultVOUtil.error("机构名称不能为空");
		}
		po.setDeptName(dept.getDeptName());
		if(dept.getDeptType()==null){
			return ResultVOUtil.error("机构类型不能为空");
		}
		po.setDeptType(dept.getDeptType());
		if(StringUtils.isNotBlank(dept.getParent().getId())){
			Department parent = departmentRepository.findOne(dept.getParent().getId());
			if(parent==null){
				return ResultVOUtil.error("上级机构不存在");
			}
			if(2==parent.getDeptType() || 3==parent.getDeptType()){
				return ResultVOUtil.error("网点和库房不能建立下级机构");
			}
			final List<Department> list = new ArrayList<>();
			list.add(parent);
			getParents(parent.getId(),list);
			if(list.size()>=5){
				return ResultVOUtil.error("机构层级最多5层");
			}
			po.setParent(parent);
		}else{
			po.setParent(null);
		}
		po.setTimeUpdate(System.currentTimeMillis());
		departmentRepository.save(po);
		dept = po;
		return ResultVOUtil.success();
	}
	
	public ResultVo<?> delete(String id){
		Department po = departmentRepository.findOne(id);
		if(po==null){
			return ResultVOUtil.error("机构不存在");
		}
		List<Department> children = departmentRepository.findChildren(id);
		if(children.size()>0){
			return ResultVOUtil.error("存在子级机构,不能删除");
		}
		po.setDeleted(true);
		po.setTimeUpdate(System.currentTimeMillis());
		departmentRepository.save(po);
		return ResultVOUtil.success();
	}
	
	public void getParents(String id,final List<Department> list){
		Department dept = departmentRepository.findOne(id);
		Department parent = dept.getParent();
		if(parent!=null){
			list.add(dept);
			getParents(dept.getParent().getId(), list);
		}
	}
	

	public ResultVo<?> listTree(String root,String excludes){
		List<Department> departments = departmentRepository.findAll();
		List<Map<String,Object>> list = new ArrayList<>();
		for(Department dept:departments){
			if(BooleanUtils.isTrue(dept.getDeleted())){
				Map<String,Object> map = new HashMap<>();
				List<Map<String,Object>> children = new ArrayList<>();
				map.put("children", children);
				map.put("id", dept.getId());
				map.put("deptNo", dept.getDeptNo());
				map.put("deptName", dept.getDeptName());
				map.put("deptType", dept.getDeptType());
				if(dept.getParent()!=null&&StringUtils.isNotBlank(dept.getParent().getId())){
					map.put("pid", dept.getParent().getId());
				}
				if(BooleanUtils.isTrue(dept.getDeleted())){
					continue;
				}
				if(StringUtils.equals(dept.getId(), excludes)){
					continue;
				}
				list.add(map);
			}
		}
		List<Map<String,Object>> tree = new ArrayList<>();
		for(Map<String,Object> map:list){
			List<Map<String,Object>> children = (List<Map<String,Object>>)map.get("children");
			String id = (String)map.get("id");
			String pid = (String)map.get("pid");
			if(StringUtils.isNotBlank(root)&&StringUtils.equals(root, id)){
				tree.add(map);
			}else if(StringUtils.isBlank(pid)){
				tree.add(map);
			}
			for(Map<String,Object> map2:list){
				String id2 = (String)map.get("id");
				pid = (String)map2.get("pid");
				if(StringUtils.equals(id2, pid)){
					children.add(map2);
				}
			}
		}
		return ResultVOUtil.success(tree);
	}
	
	
}
