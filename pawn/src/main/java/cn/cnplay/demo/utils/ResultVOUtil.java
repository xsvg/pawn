package cn.cnplay.demo.utils;

import cn.cnplay.demo.vo.ResultVo;

public class ResultVOUtil<T> {

    public static <T> ResultVo<T> success(T data) {
        ResultVo<T> ResultVo = new ResultVo<T>();
        ResultVo.setData(data);
        ResultVo.setCode(0);
        ResultVo.setMsg("成功");
        ResultVo.setSuccess(true);
        return ResultVo;
    }

    public static <T> ResultVo<T> success() {
        return success(null);
    }

    public static <T>  ResultVo<T> error(String msg) {
        ResultVo<T> ResultVo = new ResultVo<T>();
        ResultVo.setMsg(msg);
        return ResultVo;
    }
    
    public static <T> ResultVo<T> error(Integer code, String msg) {
        ResultVo<T> ResultVo = new ResultVo<T>();
        ResultVo.setCode(code);
        ResultVo.setMsg(msg);
        return ResultVo;
    }
}
