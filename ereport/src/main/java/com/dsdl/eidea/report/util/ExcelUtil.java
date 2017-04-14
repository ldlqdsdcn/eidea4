package com.dsdl.eidea.report.util;

import net.sf.jxls.transformer.XLSTransformer;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.io.*;
import java.util.Map;

/**
 * Created by 刘大磊 on 2016/9/30 12:47.
 */
public class ExcelUtil {

    public static ResponseEntity<byte[]> exportXLS(String tempFileName, Map<String,Object> param, String disFileName)
    {
        XLSTransformer transformer = new XLSTransformer();
        //TODO 报表服务器路径暂时写死
        String tempFile= "e:/report/"+System.getProperty("file.separator")+tempFileName;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        String fileName=null;
        try {
            fileName= new String(disFileName.getBytes("gbk"),"iso-8859-1");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        headers.setContentDispositionFormData("attachment", fileName
                + DateUtil.getCurrentTimeForYyyyMMddHHmmssFormat() + ".xlsx");
        try {
            InputStream inputStream=new FileInputStream(tempFile);
            Workbook workbook= transformer.transformXLS(inputStream,param);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            workbook.write(baos);
            return new ResponseEntity<byte[]>(baos.toByteArray(), headers, HttpStatus.CREATED);
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
