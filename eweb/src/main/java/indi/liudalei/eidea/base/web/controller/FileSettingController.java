/**
* 版权所有 刘大磊 2013-07-01
* 作者：刘大磊
* 电话：13336390671
* email:ldlqdsd@126.com
*/
package indi.liudalei.eidea.base.web.controller;

import indi.liudalei.eidea.base.entity.bo.ModuleBo;
import indi.liudalei.eidea.base.entity.po.FileSettingPo;
import indi.liudalei.eidea.base.service.FileSettingService;
import indi.liudalei.eidea.base.service.ModuleService;
import indi.liudalei.eidea.core.web.controller.BaseController;
import indi.liudalei.eidea.core.dto.PaginationResult;
import indi.liudalei.eidea.core.params.DeleteParams;
import indi.liudalei.eidea.core.web.def.WebConst;
import indi.liudalei.eidea.core.web.result.def.ErrorCodes;
import indi.liudalei.eidea.core.web.util.SearchHelper;
import indi.liudalei.eidea.core.web.vo.PagingSettingResult;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import indi.liudalei.eidea.core.web.result.JsonResult;
import com.googlecode.genericdao.search.Search;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import indi.liudalei.eidea.core.params.QueryParams;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.List;

/**
* Created by 刘大磊 on 2017-05-02 13:07:50.
*/ @Controller
@RequestMapping("/base/fileSetting")
public class FileSettingController extends BaseController {
private static final String URI = "fileSetting";
@Autowired
private FileSettingService fileSettingService;
@Autowired
 private ModuleService moduleService;
@RequestMapping(value = "/showList", method = RequestMethod.GET)
@RequiresPermissions("view")
public ModelAndView showList() {
ModelAndView modelAndView = new ModelAndView("/base/fileSetting/fileSetting");
modelAndView.addObject(WebConst.PAGING_SETTINGS, PagingSettingResult.getDbPaging());
modelAndView.addObject(WebConst.PAGE_URI, URI);
return modelAndView;
}
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions("view")
public JsonResult<PaginationResult<FileSettingPo>> list(HttpSession session, @RequestBody QueryParams queryParams) {
    Search search = SearchHelper.getSearchParam(URI, session);
    PaginationResult<FileSettingPo> paginationResult = fileSettingService.getFileSettingListByPaging(search, queryParams);
    return JsonResult.success(paginationResult);
    }
    @RequiresPermissions("view")
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    @ResponseBody
    public JsonResult<FileSettingPo> get(Integer id) {
        FileSettingPo fileSettingPo = null;
        if (id == null) {
        return JsonResult.fail(ErrorCodes.VALIDATE_PARAM_ERROR.getCode(),getMessage("common.errror.get_object",getLabel("fileSetting.title")));
        } else {
        fileSettingPo = fileSettingService.getFileSetting(id);
            List<ModuleBo> moduleBos=moduleService.getModulePos();
            fileSettingPo.setModuleBos(moduleBos);
        }
        return JsonResult.success(fileSettingPo);
        }

        @RequiresPermissions("add")
        @RequestMapping(value = "/create", method = RequestMethod.GET)
        @ResponseBody
        public JsonResult<FileSettingPo> create() {
            FileSettingPo fileSettingPo = new FileSettingPo();
            List<ModuleBo> moduleBos=moduleService.getModulePos();
            fileSettingPo.setModuleBos(moduleBos);
            return JsonResult.success(fileSettingPo);
            }

    /**
    * @param fileSettingPo
    * @return
    */
    @RequiresPermissions("add")
    @RequestMapping(value = "/saveForCreated", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult<FileSettingPo> saveForCreate(@Validated @RequestBody FileSettingPo fileSettingPo,BindingResult bindingResult) {
        if (bindingResult.getFieldErrorCount()>0){
            String message="";
            for (int i=0;i<bindingResult.getFieldErrors().size();i++){
                message=message+" "+bindingResult.getFieldErrors().get(i).getDefaultMessage();
            }
            return JsonResult.fail(ErrorCodes.VALIDATE_PARAM_ERROR.getCode(), message);
        }
        Search search=new Search();
        search.addFilterEqual("name",fileSettingPo.getName());
        List<FileSettingPo> fileSettingPos= fileSettingService.getFileSettingList(search);
        if (fileSettingPos.size()>0){
            return JsonResult.fail(ErrorCodes.VALIDATE_PARAM_ERROR.getCode(), getMessage("filename.validate.exists"));
        }
        File [] root = File.listRoots();
        int location=fileSettingPo.getRootDirectory().indexOf(":");
        String disk=fileSettingPo.getRootDirectory().substring(0,location).toUpperCase();
        boolean isTrue=false;
        for (File r:root){
            if (r.getAbsolutePath().contains(disk)){
                isTrue=true;
                break;
            }
        }
        if (!isTrue){
            return JsonResult.fail(ErrorCodes.VALIDATE_PARAM_ERROR.getCode(),getMessage("root.directory.validate.exist"));
        }
        String path=fileSettingPo.getRootDirectory();
        File file=new File(path);
        if (!file.exists()&&!file.isDirectory()){
            file.mkdirs();
        }
        fileSettingService.saveFileSetting(fileSettingPo);
        return get(fileSettingPo.getId());
        }

        @RequiresPermissions("update")
        @RequestMapping(value = "/saveForUpdated", method = RequestMethod.POST)
        @ResponseBody
        public JsonResult<FileSettingPo> saveForUpdate(@Validated @RequestBody FileSettingPo fileSettingPo, BindingResult bindingResult) {
            if (bindingResult.getFieldErrorCount() > 0) {
                String message="";
                for (int i = 0; i < bindingResult.getFieldErrors().size(); i++) {
                    message=message+" "+getMessage(bindingResult.getFieldErrors().get(i).getDefaultMessage());
                }
                return JsonResult.fail(ErrorCodes.VALIDATE_PARAM_ERROR.getCode(), message);
            }
            if(fileSettingPo.getId() == null){
            return JsonResult.fail(ErrorCodes.VALIDATE_PARAM_ERROR.getCode(), getMessage("common.errror.pk.required"));
            }
            File [] root = File.listRoots();
            int location=fileSettingPo.getRootDirectory().indexOf(":");
            String disk=fileSettingPo.getRootDirectory().substring(0,location).toUpperCase();
            boolean isTrue=false;
            for (File r:root){
               if (r.getAbsolutePath().contains(disk)){
                   isTrue=true;
                   break;
               }
            }
            if (!isTrue){
                return JsonResult.fail(ErrorCodes.VALIDATE_PARAM_ERROR.getCode(), getMessage("root.directory.validate.exist"));
            }
            String path=fileSettingPo.getRootDirectory();
            File file=new File(path);
            if (!file.exists()&&!file.isDirectory()){
                file.mkdirs();
            }
            fileSettingService.saveFileSetting(fileSettingPo);
            return get(fileSettingPo.getId());
            }

    @RequiresPermissions("delete")
    @RequestMapping(value = "/deletes", method = RequestMethod.POST)
    @ResponseBody

    public JsonResult<PaginationResult<FileSettingPo>> deletes(@RequestBody DeleteParams<Integer> deleteParams, HttpSession session) {
    if (deleteParams.getIds() == null||deleteParams.getIds().length == 0)  {
                return JsonResult.fail(ErrorCodes.VALIDATE_PARAM_ERROR.getCode(), getMessage("common.error.delete.failure",getMessage("fileSetting.title")));
                }
            fileSettingService.deletes(deleteParams.getIds());
                return list(session,deleteParams.getQueryParams());
        }


}
