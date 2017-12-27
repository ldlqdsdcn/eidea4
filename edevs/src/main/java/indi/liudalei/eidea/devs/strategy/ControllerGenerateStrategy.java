package indi.liudalei.eidea.devs.strategy;

import indi.liudalei.eidea.core.entity.bo.TableMetaDataBo;
import indi.liudalei.eidea.devs.model.GenModelDto;
import indi.liudalei.eidea.devs.util.FreeMarkerHelper;
import indi.liudalei.eidea.util.DateTimeHelper;
import indi.liudalei.eidea.util.StringUtil;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 刘大磊 on 2017/1/13 15:22.
 */
public class ControllerGenerateStrategy {
    private GenModelDto model;
    private TableMetaDataBo tableMetaDataBo;

    public ControllerGenerateStrategy(GenModelDto genModelDto,TableMetaDataBo tableMetaDataBo)
    {
        this.model=genModelDto;
        this.tableMetaDataBo=tableMetaDataBo;
    }

  public void generateController(String controllerModulePath)
  {
      Date date=new Date();
      Map<String,Object> root = new HashMap();
      String interfacepackage=model.getBasePackage()+"."+model.getModule()+".web.controller";
      String modelpackage=model.getBasePackage()+"."+model.getModule()+".entity.po";
      String datetime= DateTimeHelper.formatDateTime(date);
      root.put("packagename", interfacepackage);
      root.put("modelpackage", modelpackage);
      root.put("basePackage",model.getBasePackage());
      root.put("module",model.getModule());
      root.put("model",model.getModelName());
      root.put("datetime", datetime);
      root.put("lineList",model.getIncludeModelList());
      root.put("pkClass",tableMetaDataBo.getPkClass());
      root.put("pkProperty", StringUtil.fieldToProperty(tableMetaDataBo.getPkColumn()));

      root.put("memPaging",!model.isPagingByDb());
      //gc.setTime(date);
      try
      {
          FreeMarkerHelper.getInstance().outFile("controller/controller.ftl",root,controllerModulePath+"/src/main/java/"+interfacepackage.replace(".", "/")+"/"+model.getModelName()+"Controller.java");
      }
      catch(Exception e)
      {
          e.printStackTrace();
      }
  }
}
