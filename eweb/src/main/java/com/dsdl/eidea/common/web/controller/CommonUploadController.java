package com.dsdl.eidea.common.web.controller;

import com.dsdl.eidea.base.entity.bo.*;
import com.dsdl.eidea.base.entity.po.CommonFilePo;
import com.dsdl.eidea.base.entity.po.FileSettingPo;
import com.dsdl.eidea.base.entity.po.ModuleDirectoryPo;
import com.dsdl.eidea.base.service.*;
import com.dsdl.eidea.base.service.impl.FileRelationServiceImpl;
import com.dsdl.eidea.base.web.vo.UserResource;
import com.dsdl.eidea.core.dto.PaginationResult;
import com.dsdl.eidea.core.params.QueryParams;
import com.dsdl.eidea.core.web.def.WebConst;
import com.dsdl.eidea.core.web.result.JsonResult;
import com.dsdl.eidea.core.web.result.def.ErrorCodes;
import com.dsdl.eidea.core.web.util.SearchHelper;
import com.dsdl.eidea.util.StringUtil;
import com.googlecode.genericdao.search.Search;
import org.fusesource.hawtbuf.BufferInputStream;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by admin on 2017/5/4.
 */
@Controller
@RequestMapping("/common")
public class CommonUploadController {
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private FileSettingService fileSettingService;
    @Autowired
    private CommonFileService commonFileService;
    @Autowired
    private DirectoryService directoryService;
    @Autowired
    private ModuleDirectoryService moduleDirectoryService;
    @Autowired
    private FileRelationService fileRelationService;

    private ModelMapper modelMapper = new ModelMapper();

    @RequestMapping(value = "/attachmentList", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult<List<CommonFileBo>>  attachmentList(@RequestBody CommonFileBo commonFileBo){
        UserResource resource = (UserResource) request.getSession().getAttribute(WebConst.SESSION_RESOURCE);
        FileSettingBo fileSettingBo=getFileSetting(commonFileBo);
        if(fileSettingBo == null){
            return JsonResult.fail(ErrorCodes.BUSINESS_EXCEPTION.getCode(), resource.getMessage("common.upload.parameter.error"));
        }
       return JsonResult.success(list(commonFileBo.getTableId(),commonFileBo.getUri()));
    }
    @RequestMapping(value = "/attachmentUpload", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult<List<CommonFileBo>> attachmentUpload(MultipartFile file,CommonFileBo commonFileBo){
        UserResource resource = (UserResource) request.getSession().getAttribute(WebConst.SESSION_RESOURCE);
        FileSettingBo fileSettingBo=getFileSetting(commonFileBo);
        if(fileSettingBo == null){
            return JsonResult.fail(ErrorCodes.BUSINESS_EXCEPTION.getCode(), resource.getMessage("common.upload.parameter.error"));
        }
        if(file != null){
            String filepath=fileSettingBo.getRootDirectory();
            File attachmentDir = new File(filepath);
            if (!attachmentDir.exists()) {
                attachmentDir.mkdirs();
            }
            String originalFileName=file.getOriginalFilename();
            String extension=originalFileName.substring(originalFileName.lastIndexOf("."),originalFileName.length());
            String photoFileName = UUID.randomUUID().toString().replaceAll("-","") + extension;
            File attachmentFile = new File(filepath, photoFileName);
            try {
                file.transferTo(attachmentFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
            commonFileBo.setFileName(originalFileName);
            commonFileBo.setExtension(extension);
            commonFileBo.setPath(filepath+"/"+photoFileName);
            commonFileBo.setFileSize((int)file.getSize());
            commonFileBo.setFileCreated(new Date());
            commonFileBo.setFileIsreadonly(0);
            commonFileBo.setFileIshidden(0);
            commonFileBo.setCreated(new Date());
            commonFileBo.setCommonFileSettingId(fileSettingBo.getId());
            try{
                commonFileService.saveAttachmentUpload(commonFileBo);
            }catch (Exception e){
                e.printStackTrace();
                File oldFile = new File(filepath, photoFileName);
                if (oldFile.exists()) {
                    oldFile.delete();
                }
            }
        }
        return JsonResult.success(list(commonFileBo.getTableId(),commonFileBo.getUri()));
    }

    @RequestMapping(value="/attachmentDelete", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult<List<CommonFileBo>> attachmentDelete(@RequestBody CommonFileBo commonFileBo){
        UserResource resource = (UserResource) request.getSession().getAttribute(WebConst.SESSION_RESOURCE);
        FileSettingBo fileSettingBo=getFileSetting(commonFileBo);
        if(fileSettingBo == null){
            return JsonResult.fail(ErrorCodes.BUSINESS_EXCEPTION.getCode(), resource.getMessage("common.upload.parameter.error"));
        }
        CommonFilePo commonFilePo=commonFileService.getCommonFile(commonFileBo.getId());
        commonFileService.deleteAttachment(commonFileBo);
        File oldFile = new File(commonFilePo.getPath());
        if (oldFile.exists()) {
            oldFile.delete();
        }
        return JsonResult.success(list(commonFileBo.getTableId(),commonFileBo.getUri()));
    }

    @RequestMapping("/attachmentDownload")
    public void attachmentDownload(HttpServletResponse response,CommonFileBo commonFileBo)throws Exception{
        CommonFilePo commonFilePo=commonFileService.getCommonFile(commonFileBo.getId());
        String fileName = commonFilePo.getFilename();
        InputStream fis=new FileInputStream(commonFilePo.getPath());
        BufferedInputStream bis=new BufferedInputStream(fis);
        response.reset();
        response.setContentType("application/x-download");
        response.addHeader("Content-Disposition", "attachment; filename=" + new String(fileName.getBytes("utf-8"),"iso8859-1"));
        byte[] b = new byte[fileName.length()];
        int len;
        try {
            while ((len = bis.read(b)) > 0){
                response.getOutputStream().write(b, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            bis.close();
            fis.close();
        }
    }
    public FileSettingBo getFileSetting(CommonFileBo commonFileBo){
        List<ModuleDirectoryBo> moduleDirectoryBoList=new ArrayList<>();
        if(StringUtil.isNotEmpty(commonFileBo.getDirectoryUrl())){
            Search search=new Search();
            List<DirectoryBo> directoryBoList = directoryService.findAllDirectory(search);
            for(DirectoryBo directoryBo:directoryBoList){
                if(directoryBo.getDirectory().equals(commonFileBo.getDirectoryUrl())){
                    moduleDirectoryBoList=moduleDirectoryService.getModuleDirectoryList(directoryBo.getId());
                    break;
                }
            }
        }
        if(moduleDirectoryBoList == null || moduleDirectoryBoList.size() == 0){
            return null;
        }
        Search moduleSearch=new Search();
        moduleSearch.addFilterEqual("moduleId",moduleDirectoryBoList.get(0).getSysModuleId());
        List<FileSettingPo> fileSettingPoList=fileSettingService.getFileSettingList(moduleSearch);
        if(fileSettingPoList == null || fileSettingPoList.size() == 0){
            return null;
        }
        return modelMapper.map(fileSettingPoList.get(0), FileSettingBo.class);
    }
    public List<CommonFileBo> list(int tableId,String tableName){
        Search search=new Search();
        search.addFilterEqual("tableId",tableId);
        search.addFilterIn("tableName",tableName);
        List<FileRelationBo> FileRelationBoList=fileRelationService.getFileRelationList(search);
        List<CommonFilePo> commonFileBoList=new ArrayList<>();
        FileRelationBoList.forEach(fileRelationBo -> {
            CommonFilePo commonFilePo=commonFileService.getCommonFile(fileRelationBo.getFileId());
            commonFileBoList.add(commonFilePo);
        });
        return modelMapper.map(commonFileBoList,new TypeToken<List<CommonFileBo>>(){}.getType());
    }
}
