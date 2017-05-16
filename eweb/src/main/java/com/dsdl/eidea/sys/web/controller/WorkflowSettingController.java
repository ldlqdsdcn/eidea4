package com.dsdl.eidea.sys.web.controller;

import com.dsdl.eidea.base.entity.po.FileSettingPo;
import com.dsdl.eidea.base.service.FileSettingService;
import com.dsdl.eidea.core.web.result.JsonResult;
import com.dsdl.eidea.core.web.result.def.ErrorCodes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Bobo on 2017/5/12.
 */
@Controller
@RequestMapping("/sys/workflow")
@Slf4j
public class WorkflowSettingController {
    @Autowired
    private FileSettingService fileSettingService;
    @RequestMapping(value = "/showList", method = RequestMethod.GET)
    public ModelAndView list() {
        ModelAndView modelAndView = new ModelAndView("/sys/workflow/workflow");
        return modelAndView;
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult<Map<String,Object>> upload(MultipartFile file, HttpServletRequest request){

        FileSettingPo fileSettingPo=fileSettingService.getFileSettingsByRequestPath(request.getServletPath());
        if(fileSettingPo==null)
        {
            log.error("根据访问路径："+request.getServletPath()+"取不到管理的文件设置");
            JsonResult.fail(ErrorCodes.BUSINESS_EXCEPTION.getCode(),"文件设置异常，保存失败");
        }
        try{
            File fileDirectory=new File(fileSettingPo.getRootDirectory());
            if(!fileDirectory.exists())
            {
                fileDirectory.mkdirs();
            }
            InputStream in=file.getInputStream();
            FileOutputStream fos = new FileOutputStream(new File(fileDirectory, UUID.randomUUID().toString()));
            byte[] b = new byte[Integer.parseInt(file.getSize()+"")];
            int len=0;
            while((len=in.read(b))!= -1){
                fos.write(b,0,len);
            }
            in.close();
            fos.close();
        }catch (IOException io){
            io.printStackTrace();
        }finally {
            return JsonResult.success(new HashMap<String,Object>());
        }
    }
}
