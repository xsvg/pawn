package cn.cnplay.demo.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.cnplay.demo.model.Department;
import cn.cnplay.demo.model.StorageArea;
import cn.cnplay.demo.repository.DepartmentRepository;
import cn.cnplay.demo.repository.StorageAreaRepository;
import cn.cnplay.demo.utils.ResultVOUtil;
import cn.cnplay.demo.vo.ResultVo;

@Service
@Transactional
public class StorageAreaService {
	@Autowired StorageAreaRepository areaRepository;
	@Autowired EntityManager entityManager;
	@Autowired DepartmentRepository deptRepository;
	
	
	public ResultVo<?> get(String id){
		StorageArea area = areaRepository.findOne(id);
		if(area==null){
			return ResultVOUtil.error("区域信息不存在");
		}else{
			return ResultVOUtil.success(area);
		}
	}
	
	public ResultVo<?> add(StorageArea area){
		if(StringUtils.isBlank(area.getAreaName())){
			return ResultVOUtil.error("区域名称不能为空");
		}
		if(area.getParentArea()!=null&&StringUtils.isNotBlank(area.getParentArea().getId())){
			StorageArea parent = areaRepository.findOne(area.getParentArea().getId());
			if(parent==null){
				return ResultVOUtil.error("父级区域不存在");
			}
			List<StorageArea> parents = new ArrayList<>();
			parents.add(parent);
			getParents(parent.getId(),parents);
			if(parents.size()>=3){
				return ResultVOUtil.error("只支持区域的三级划分");
			}
		}else{
			area.setParentArea(null);
		}
		if(area.getDepartment()==null||StringUtils.isBlank(area.getDepartment().getId())){
			return ResultVOUtil.error("所属机构不能为空");
		}else{
			Department dept = deptRepository.findOne(area.getDepartment().getId());
			if(dept==null){
				return ResultVOUtil.error("所属机构不存在");
			}
		}
		areaRepository.save(area);
		return ResultVOUtil.success();
	}
	
	public ResultVo<?> update(StorageArea area){
		StorageArea areaPo = areaRepository.findOne(area.getId());
		if(areaPo==null){
			return ResultVOUtil.error("区域不存在");
		}
		if(StringUtils.isBlank(area.getAreaName())){
			return ResultVOUtil.error("区域名称不能为空");
		}
		if(area.getParentArea()!=null&&StringUtils.isNotBlank(area.getParentArea().getId())){
			StorageArea parent = areaRepository.findOne(area.getParentArea().getId());
			if(parent==null){
				return ResultVOUtil.error("父级区域不存在");
			}
			List<StorageArea> parents = new ArrayList<>();
			parents.add(parent);
			getParents(parent.getId(),parents);
			if(parents.size()>=3){
				return ResultVOUtil.error("只支持区域的三级划分");
			}
			areaPo.setParentArea(parent);
		}else{
			areaPo.setParentArea(null);
		}
		if(area.getDepartment()==null||StringUtils.isBlank(area.getDepartment().getId())){
			return ResultVOUtil.error("所属机构不能为空");
		}else{
			Department dept = deptRepository.findOne(area.getDepartment().getId());
			if(dept==null){
				return ResultVOUtil.error("所属机构不存在");
			}
			areaPo.setDepartment(dept);
		}
		
		areaPo.setElecLabel(area.getElecLabel());
		areaPo.setAreaNo(area.getAreaNo());
		areaPo.setAreaName(area.getAreaName());
		areaPo.setTimeUpdate(System.currentTimeMillis());
		areaRepository.save(area);
		return ResultVOUtil.success();
	}
	
	public ResultVo<?> delete(String id){
		StorageArea area = areaRepository.findOne(id);
		if(area==null){
			return ResultVOUtil.error("区域不存在");
		}
		List<StorageArea> children = areaRepository.findChildren(id);
		if(children.size()>0){
			return ResultVOUtil.error("存在子级区域不能删除");
		}
		area.setDeleted(true);
		areaRepository.save(area);
		return ResultVOUtil.success();
	}
	
	public void getParents(String id,List<StorageArea> list){
		StorageArea area = areaRepository.findOne(id);
		StorageArea parent = area.getParentArea();
		if(parent!=null){
			list.add(area);
			getParents(area.getParentArea().getId(), list);
		}
	}

	public ResultVo<?> list(StorageArea filter){
		StringBuilder builder = new StringBuilder("from StorageArea area where");
		List<Object> params = new ArrayList<>();
		builder.append(" and area.deleted = ?");
		params.add(false);
		if(filter.getDepartment()!=null && StringUtils.isNotBlank(filter.getDepartment().getId())){
			builder.append(" and area.department.id = ?");
			params.add(filter.getDepartment().getId());
		}
		if(filter.getParentArea()!=null && StringUtils.isNotBlank(filter.getParentArea().getId())){
			builder.append(" and area.parentArea.id = ?");
			params.add(filter.getParentArea().getId());
		}
		if(StringUtils.isNotBlank(filter.getAreaName())){
			builder.append(" and area.areaName like ?");
			params.add("%"+filter.getAreaName()+"%");
		}
		if(StringUtils.isNotBlank(filter.getAreaNo())){
			builder.append(" and area.areaNo like ?");
			params.add("%"+filter.getAreaNo()+"%");
		}
		if(StringUtils.isNotBlank(filter.getElecLabel())){
			builder.append(" and area.elecLabel like ?");
			params.add("%"+filter.getElecLabel()+"%");
		}
		Query query = entityManager.createQuery(builder.toString());
		int i=1;
		for(Object param:params){
			query.setParameter(i++, param);
		}
		return ResultVOUtil.success(query.getResultList());
	}
}
