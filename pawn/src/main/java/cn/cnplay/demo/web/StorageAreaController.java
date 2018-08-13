package cn.cnplay.demo.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Description;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.cnplay.demo.annotation.UserRole;
import cn.cnplay.demo.model.StorageArea;
import cn.cnplay.demo.service.StorageAreaService;
import cn.cnplay.demo.vo.ResultVo;

@RestController
@RequestMapping("area")
@Description("区域管理")
public class StorageAreaController {
	
	@Autowired StorageAreaService areaService;
	
	
	@RequestMapping("{id}")
	public ResultVo<?> get(@PathVariable String id){
		return areaService.get(id);
	}
	
	@PostMapping("add")
	@UserRole("admin")
	@Description("添加")
	public ResultVo<?> add(StorageArea area){
		return areaService.add(area);
	}
	
	@PostMapping("update")
	@UserRole("admin")
	@Description("更新")
	public ResultVo<?> update(StorageArea area){
		return areaService.update(area);
	}
	
	@PostMapping("delete/{id}")
	@UserRole("admin")
	@Description("删除")
	public ResultVo<?> add(@PathVariable String id){
		return areaService.delete(id);
	}
	
	@RequestMapping("list")
	public ResultVo<?> list(StorageArea filter){
		return areaService.list(filter);
	}
}
