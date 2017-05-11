package com.dsdl.eidea.common.web.controller;

import com.dsdl.eidea.base.entity.bo.CommonFileBo;
import com.dsdl.eidea.base.entity.bo.DirectoryBo;
import com.dsdl.eidea.base.entity.bo.ModuleDirectoryBo;
import com.dsdl.eidea.base.entity.po.CommonFilePo;
import com.dsdl.eidea.base.entity.po.FileSettingPo;
import com.dsdl.eidea.base.entity.po.ModuleDirectoryPo;
import com.dsdl.eidea.base.service.CommonFileService;
import com.dsdl.eidea.base.service.DirectoryService;
import com.dsdl.eidea.base.service.FileSettingService;
import com.dsdl.eidea.base.service.ModuleDirectoryService;
import com.dsdl.eidea.core.dto.PaginationResult;
import com.dsdl.eidea.core.params.QueryParams;
import com.dsdl.eidea.core.web.result.JsonResult;
import com.dsdl.eidea.core.web.util.SearchHelper;
import com.googlecode.genericdao.search.Search;
import org.fusesource.hawtbuf.BufferInputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
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
    private static final String URI = "sys_directory";
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

    @RequestMapping(value = "/attachmentUpload", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult<List<CommonFilePo>> attachmentUpload(MultipartFile file, CommonFileBo commonFileBo){
        if(file != null){
            List<ModuleDirectoryBo> moduleDirectoryBoList=new ArrayList<ModuleDirectoryBo>();
            Search search = SearchHelper.getSearchParam(URI, request.getSession());
            PaginationResult<DirectoryBo> directoryBoList = directoryService.findDirectory(search,new QueryParams());
            for(DirectoryBo directoryBo:directoryBoList.getData()){
                if(directoryBo.getDirectory().equals(commonFileBo.getDirectoryUrl())){
                    moduleDirectoryBoList=moduleDirectoryService.getModuleDirectoryList(directoryBo.getId());
                }
            }
            Search moduleSearch=new Search();
            moduleSearch.addFilterEqual("moduleId",moduleDirectoryBoList.get(0).getSysModuleId());
            List<FileSettingPo> fileSettingPoList=fileSettingService.getFileSettingList(moduleSearch);
            String filepath=fileSettingPoList.get(0).getRootDirectory();
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
            commonFileBo.setCommonFileSettingId(fileSettingPoList.get(0).getId());
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
        return JsonResult.success(list().getData());
    }

    @RequestMapping(value="/attachmentDelete", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult<List<CommonFilePo>> attachmentDelete(@RequestBody CommonFileBo commonFileBo){
        commonFileService.deleteAttachment(commonFileBo);
        return JsonResult.success(list().getData());
    }

    @RequestMapping("/attachmentDownload")
    public void attachmentDownload(HttpServletResponse response,CommonFileBo commonFileBo)throws Exception{
        CommonFilePo commonFilePo=commonFileService.getCommonFile(commonFileBo.getId());
        // 下载本地文件
        String fileName = commonFilePo.getFilename();
        // 读到流中
        InputStream fis=new FileInputStream(commonFilePo.getPath());
        BufferedInputStream bis=new BufferedInputStream(fis);//放到缓冲流里面
        // 设置输出的格式
        response.reset();
        response.setContentType("application/x-download");
        response.addHeader("Content-Disposition", "attachment; filename=" + new String(fileName.getBytes("utf-8"),"iso8859-1"));
        // 循环取出流中的数据
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

    public PaginationResult<CommonFilePo> list(){
        return commonFileService.getCommonFileListByPaging(new Search(), new QueryParams());
    }
}
