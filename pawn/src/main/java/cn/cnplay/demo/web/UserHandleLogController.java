package cn.cnplay.demo.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Description;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.cnplay.demo.model.UserHandleLog;
import cn.cnplay.demo.service.UserHandleLogService;
import cn.cnplay.demo.vo.ResultVo;

@RestController
@RequestMapping("handleLog")
@Description("操作日志")
public class UserHandleLogController {

	@Autowired UserHandleLogService logService;
	
	@RequestMapping("list")
	@Description("日志查询")
	public ResultVo<?> list(UserHandleLog filter,Integer pageNo,Integer pageSize){
		return logService.list(filter, pageNo, pageSize);
	}
}
