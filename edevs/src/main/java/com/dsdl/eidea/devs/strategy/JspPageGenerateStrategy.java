package com.dsdl.eidea.devs.strategy;

import com.dsdl.eidea.core.def.JavaDataType;
import com.dsdl.eidea.core.def.SearchPageFieldInputType;
import com.dsdl.eidea.core.entity.bo.ColumnMetaDataBo;
import com.dsdl.eidea.core.entity.bo.TableMetaDataBo;
import com.dsdl.eidea.core.service.TableService;
import com.dsdl.eidea.devs.cons.IntelliKeyWord;
import com.dsdl.eidea.devs.def.ValidationDef;
import com.dsdl.eidea.devs.model.ColumnInfo;
import com.dsdl.eidea.devs.model.FormLine;
import com.dsdl.eidea.devs.model.GenModelDto;
import com.dsdl.eidea.devs.model.JspModelProp;
import com.dsdl.eidea.devs.util.FreeMarkerHelper;
import com.dsdl.eidea.util.DateTimeHelper;
import com.dsdl.eidea.util.StringUtil;
import org.modelmapper.ModelMapper;

import java.io.File;
import java.util.*;

/**
 * Created by 刘大磊 on 2017/1/16 8:57.
 */
public class JspPageGenerateStrategy {
    private String user = "刘大磊";
    private TableService tableService;
    /**
     * 填写对应的模块名
     */
    private GenModelDto model;
    private String namespace;
    private TableMetaDataBo tableMetaDataBo;

    public JspPageGenerateStrategy(GenModelDto model, TableMetaDataBo tableMetaDataBo, TableService tableService) {
        this.model = model;
        this.namespace = "/" + model.getModule();
        this.tableMetaDataBo = tableMetaDataBo;
        this.tableService = tableService;
    }

    /**
     * generate angular jsp
     */
    public void generateJspPage(String outputModulePath) {
        generateMainPage(outputModulePath);
        generateListPage(outputModulePath);
        generateFormPage(outputModulePath);

    }

    /**
     * 生成主页
     */
    public void generateMainPage(String outPath) {
        Map<String, Object> root = new HashMap();
        root.put("module", model.getModule());
        root.put("model", model.getModelName());
        root.put("user",user);
        root.put("modelName", model.getName());
        root.put("pkProp",StringUtil.fieldToProperty(tableMetaDataBo.getPkColumn()));
        root.put("datetime", DateTimeHelper.formatDateTime(new Date()));
        root.put("pagingByDb",model.isPagingByDb());
        String lowerFirstModel = StringUtil.lowerFirstChar(model.getModelName());
        FreeMarkerHelper.getInstance().outFile("jsp/model.ftl", root, outPath + "/src/main/webapp" + namespace + "/" + lowerFirstModel + "/" + lowerFirstModel + ".jsp");
    }

    private void generateFormPage(String outputModulePath) {
        List<JspModelProp> jspFormPropList = getOutPutList(StringUtil.lowerFirstChar(model.getModelName()), this.tableMetaDataBo);
        Map<String, Object> root = new HashMap();
        root.put("model", StringUtil.lowerFirstChar(model.getModelName()));
        root.put("title", model.getName());
        root.put("namespace", namespace);
        root.put("user", user);

        Date date = new Date();

        String datetime = DateTimeHelper.formatDateTime(date);

        root.put("datetime", datetime);
        root.put("propertyList", jspFormPropList);
        List<FormLine> formLines = new ArrayList<>();
        List<GenModelDto> genModelDtoList = this.model.getIncludeModelList();
        if (genModelDtoList != null)
            for (GenModelDto genModelDto : genModelDtoList) {
                FormLine formLine = new FormLine();
                formLine.setLabel(genModelDto.getName());
                formLine.setModel(genModelDto.getModelName());
                formLine.setTrl(genModelDto.isTrl());
                TableMetaDataBo tableMetaDataDto = tableService.getTableDescription(genModelDto.getTableName());
                List<JspModelProp> linePropList = getOutPutList(StringUtil.lowerFirstChar(model.getModelName()), tableMetaDataDto);
                formLine.setPropertyList(linePropList);
                formLines.add(formLine);
            }
        if (formLines.size() > 0) {
            root.put("lineList", formLines);
        }

        FreeMarkerHelper.getInstance().outFile("jsp/edit.ftl", root, outputModulePath+ "/src/main/webapp"  + namespace + "/" + StringUtil.lowerFirstChar(model.getModelName()) + "/edit.tpl.jsp");
    }

    public void generateListPage(String outputModulePath) {
        List<JspModelProp> jspListPropList = getOutPutList(StringUtil.lowerFirstChar(model.getModelName()), this.tableMetaDataBo);
        Map root = new HashMap();
        root.put("model", StringUtil.lowerFirstChar(model.getModelName()));
        root.put("title", model.getName());
        root.put("namespace", namespace);
        root.put("module", model.getModule());
        root.put("user", user);
        root.put("pagingByDb", model.isPagingByDb());
        root.put("pkProp",StringUtil.fieldToProperty(tableMetaDataBo.getPkColumn()));
        Date date = new Date();
        String datetime = DateTimeHelper.formatDateTime(date);
        root.put("datetime", datetime);
        root.put("propertyList", jspListPropList);
        FreeMarkerHelper.getInstance().outFile("jsp/list.ftl", root, outputModulePath + "/src/main/webapp" + namespace + "/" + StringUtil.lowerFirstChar(model.getModelName()) + "/list.tpl.jsp");
    }

    private List<JspModelProp> getOutPutList(String mode, TableMetaDataBo tableData) {
        ModelMapper modelMapper = new ModelMapper();
        List<JspModelProp> jspFormPropList = new ArrayList<>();
        List<ColumnMetaDataBo> columnMetaDataDtoList = tableData.getColumnList();

        for (ColumnMetaDataBo columnMetaDataDto : columnMetaDataDtoList) {

            List<Integer> validationList = new ArrayList<>();
            ColumnInfo columnInfo = modelMapper.map(columnMetaDataDto, ColumnInfo.class);

            columnInfo.setPropertyName(StringUtil.fieldToProperty(columnMetaDataDto.getColumnName()));
            if (IntelliKeyWord.hasSkipped(columnInfo.getPropertyName())) {
                continue;
            }

            boolean date = false;
            JavaDataType[] columnDataTypes = JavaDataType.values();

            boolean isreadOnly = IntelliKeyWord.isReadOnly(columnInfo.getPropertyName());
            if (!isreadOnly) {

                switch (columnDataTypes[columnInfo.getDataType()]) {
                    case DATE: {
                        validationList.add(ValidationDef.DATE.getKey());
                        break;
                    }
                    case INT: {
                        validationList.add(ValidationDef.INT.getKey());
                        break;
                    }
                    case FLOAT:
                    case DECIMAL: {
                        validationList.add(ValidationDef.FLOAT.getKey());
                        break;
                    }
                }
                if (!columnInfo.getNullable()) {
                    validationList.add(ValidationDef.REQUIRED.getKey());
                }
                if (columnInfo.getDataType() == JavaDataType.DATE.getKey()) {
                    date = true;
                    validationList.add(ValidationDef.DATE.getKey());
                }
            }

            String label = IntelliKeyWord.getLabel(columnInfo.getPropertyName());
            if (label == null) {
                label = columnInfo.getPropertyName();
            }

            JspModelProp jspModelProp = new JspModelProp(StringUtil.fieldToProperty(columnInfo.getPropertyName()), label, date, !isreadOnly);
            jspModelProp.setPropertyLabel(StringUtil.lowerFirstChar(model.getModule()) + "." + StringUtil.lowerFirstChar(model.getModelName()) + ".label." + jspModelProp.getProp());
            jspModelProp.setRequired(!columnInfo.getNullable());
            if (IntelliKeyWord.isBooleanTag(columnInfo.getPropertyName())) {
                jspModelProp.setBooleanTag(true);
            }
            jspModelProp.setForeignModel(IntelliKeyWord.getModule(columnInfo.getPropertyName()));
            if (jspModelProp.getForeignModel() != null) {
                jspModelProp.setForeign(true);
            }
            String width = IntelliKeyWord.getWidth(columnInfo.getPropertyName());
            //todo 不合理，代码
            if (width != null) {
                jspModelProp.setCssStyle("cssStyle=\"" + width + "\"");
            } else {
                jspModelProp.setCssStyle("");
            }
            if (validationList.size() > 0) {
                jspModelProp.setValidationList(validationList);
            }

            if (columnInfo.getDataType() == JavaDataType.DATE.getKey()) {
                if (columnInfo.getColumnName().toUpperCase().contains("TIME")) {
                    jspModelProp.setInputType(SearchPageFieldInputType.DATETIMEPICKER.getKey());
                } else {
                    jspModelProp.setInputType(SearchPageFieldInputType.DATETIMEPICKER.getKey());
                }
            } else {
                jspModelProp.setInputType(IntelliKeyWord.getDecorator(jspModelProp.getProp()));
            }
            if (IntelliKeyWord.isNotValidate(columnInfo.getPropertyName())) {
                jspModelProp.setValidationList(null);

            }

            jspFormPropList.add(jspModelProp);
        }
        return jspFormPropList;
    }
}
