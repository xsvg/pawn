package cn.cnplay.demo.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Description;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.cnplay.demo.annotation.UserRole;
import cn.cnplay.demo.model.Department;
import cn.cnplay.demo.service.DepartmentService;
import cn.cnplay.demo.vo.ResultVo;

@RestController
@RequestMapping("dept")
@Description("机构管理")
public class DepartmentController {

	@Autowired DepartmentService deptService;
	
	@GetMapping("/{id}")
	public ResultVo<Department> get(@PathVariable(name="id",required=true) String id){
		return deptService.get(id);
	}
	
	@PostMapping("add")
	@Description("添加")
	@UserRole("admin")
	public ResultVo<?> add(Department dept){
		return deptService.add(dept);
	}
	
	@PostMapping("update")
	@Description("更新")
	@UserRole("admin")
	public ResultVo<?> update(Department dept){
		return deptService.update(dept);
	}
	
	@PostMapping("delete")
	@Description("删除")
	@UserRole("admin")
	public ResultVo<?> delete(String id){
		return deptService.delete(id);
	}
}
