package com.dsdl.eidea.sys.web.controller;

import com.dsdl.eidea.core.web.result.JsonResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Bobo on 2017/5/12.
 */
@Controller
@RequestMapping("/sys/workflow")
public class WorkflowSettingController {

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView list() {
        ModelAndView modelAndView = new ModelAndView("/sys/workFlow/workFlow");
        return modelAndView;
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult<Map<String,Object>> upload(MultipartFile file){
        try{
            InputStream in=file.getInputStream();
            FileOutputStream fos = new FileOutputStream("C:/delmarfile/system/"+file.getOriginalFilename());
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
