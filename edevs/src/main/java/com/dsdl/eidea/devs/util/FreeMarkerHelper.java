package com.dsdl.eidea.devs.util;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.util.Map;

/**
 * Created by admin on 2016/8/26.
 */
public class FreeMarkerHelper {
    private String   genActionPath;
    private static FreeMarkerHelper freeMarkerHelper;
    private Configuration config = new Configuration();
    private FreeMarkerHelper() throws FileNotFoundException {
        File file=new File("");
        File filepath = ResourceUtils.getFile("classpath:ftl");

        genActionPath=file.getAbsolutePath()+"/eweb/";

        try {
            config.setDirectoryForTemplateLoading(filepath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        config.setObjectWrapper(new DefaultObjectWrapper());
    }
    public static synchronized FreeMarkerHelper getInstance()
    {
        if(freeMarkerHelper==null)
        {
            try {
                freeMarkerHelper=new FreeMarkerHelper();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return freeMarkerHelper;
    }
    public void outFile(String templateFile, Map root, String outFile,boolean isAction)
    {
        if(root==null)
        {
            throw new IllegalArgumentException("root 参数不允许为空");
        }
        Template template = null;
        try {
            template = config.getTemplate(templateFile, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            System.out.println("path:"+outFile);
            File file=null;
            if(isAction)
            {
                file=new File(genActionPath+outFile);
            }
            else
            {
                file=new File(outFile);
            }

            if(!file.exists())
            {
                file.getParentFile().mkdirs();
            }
            OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(file),"UTF-8");
            template.process(root, out);
            out.flush();
            out.close();

        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        }
    }
    public void outFile(String templateFile, Map root, String outFile)
    {
      outFile(templateFile,root,outFile,false);
    }
}
