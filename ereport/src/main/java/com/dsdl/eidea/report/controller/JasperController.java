package com.dsdl.eidea.report.controller;

import com.dsdl.eidea.report.service.AuthenticationManger;
import com.dsdl.eidea.report.util.PrivilegesHelper;
import com.dsdl.eidea.util.AesUtils;
import com.dsdl.eidea.util.ReadPropertiesUtil;
import com.dsdl.eidea.report.entity.param.ReportParam;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;
import org.apache.commons.collections.map.HashedMap;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.util.*;

/**
 * Created by 刘大磊 on 2016/11/16 13:43.
 */
@Controller
@RequestMapping("/jasper")
public class JasperController {
    private static final Logger logger = Logger.getLogger(JasperController.class);
    @Autowired
    private DataSource dataSource;
    @Autowired
    private AuthenticationManger authenticationManger;
    @RequestMapping("/export")
    @ResponseBody
    public void exportReport(String token,HttpServletRequest request, String reportName, HttpServletResponse response) throws Exception {
        if(authenticationManger.verifyUser(token))
        {
            PrivilegesHelper.outNoPrivilegesView(response);
            return;
        }
        Connection conn = dataSource.getConnection();
        Map<String, Object> params = new HashedMap();
        Enumeration<String> en = request.getParameterNames();
        while (en.hasMoreElements()) {
            String key = en.nextElement();
            String value = request.getParameter(key);
            logger.debug(key + "----------->" + value);
            //params.put(key,value);
        }
        String sqlWhereParam= request.getParameter("sqlWhereParam");
        //对参数aes进行解密
        sqlWhereParam= AesUtils.decrypt(sqlWhereParam, ReadPropertiesUtil.readValue("aeskey"));
        if(sqlWhereParam!=null&&sqlWhereParam.trim().length()!=0&&!"null".equals(sqlWhereParam))
        {
            sqlWhereParam="where "+sqlWhereParam;
            params.put("searchWhere",sqlWhereParam);
        }
        else
        {
            sqlWhereParam=null;
        }
        //TODO 需要替换掉
        String tempFile = "e:/report" + System.getProperty("file.separator") + reportName + ".jasper";
        InputStream jasperStream = new FileInputStream(tempFile);
        JasperReport jasperReport = (JasperReport) JRLoader.loadObject(jasperStream);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, conn);

        response.setContentType("application/pdf");
        response.setHeader("Content-disposition", "inline; filename=" + reportName + ".pdf");

        final OutputStream outStream = response.getOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint, outStream);
        conn.close();
    }

    /**
     * 同时，导出多个报表 暂时只只是string一种数据类型
     *
     * @param param
     * @param response
     * @throws Exception
     */
    @RequestMapping("/exports")
    @ResponseBody
    public void exportReportList(String token,String param, HttpServletResponse response) throws Exception {
        if(!authenticationManger.verifyUser(token))
        {
            PrivilegesHelper.outNoPrivilegesView(response);
            return;
        }
        logger.debug("param="+param);
        param=AesUtils.decrypt(param,ReadPropertiesUtil.readValue("aeskey"));
        logger.debug("param_decoder="+param);
        Gson gson = new Gson();
        List<ReportParam> reportParamList = gson.fromJson(param, new TypeToken<List<ReportParam>>() {
        }.getType());
        Connection conn = dataSource.getConnection();
        List<JasperPrint> jasperPrintList = new ArrayList<>();
        String exportName = null;
        for (ReportParam reportParam : reportParamList) {
            exportName = reportParam.getExportName();
            //TODO 需要替换掉
            String tempFile = "e:/report" + System.getProperty("file.separator") + reportParam.getReportName() + ".jasper";
            InputStream jasperStream = new FileInputStream(tempFile);
            JasperReport jasperReport = (JasperReport) JRLoader.loadObject(jasperStream);
            Map<String, Object> params = new HashMap<>();
            reportParam.getParamList().forEach(e -> {
                if (e.getKey().equals("sqlWhereParam")) {
                    String sqlWhereParam = e.getValue();
                    if (sqlWhereParam != null && sqlWhereParam.trim().length() != 0 && !"null".equals(sqlWhereParam)) {
                        sqlWhereParam = "where " + sqlWhereParam;
                        params.put("searchWhere", sqlWhereParam);
                    } else {
                        sqlWhereParam = null;
                    }

                } else {
                    params.put(e.getKey(), e.getValue());
                }

            });
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, conn);
            jasperPrintList.add(jasperPrint);
        }
        JRPdfExporter exporter = new JRPdfExporter();
        exporter.setExporterInput(SimpleExporterInput.getInstance(jasperPrintList));
        final OutputStream outStream = response.getOutputStream();
        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outStream));
        SimplePdfExporterConfiguration configuration = new SimplePdfExporterConfiguration();
        configuration.setCreatingBatchModeBookmarks(true);
        exporter.setConfiguration(configuration);
        exporter.exportReport();
        response.setContentType("application/pdf");
        response.setHeader("Content-disposition", "inline; filename=" + exportName + ".pdf");
        outStream.close();

    }
}
