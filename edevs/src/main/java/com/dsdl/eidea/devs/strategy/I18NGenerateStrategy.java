package com.dsdl.eidea.devs.strategy;

import com.dsdl.eidea.core.def.JavaDataType;
import com.dsdl.eidea.core.def.FieldInputType;
import com.dsdl.eidea.core.entity.bo.*;
import com.dsdl.eidea.core.service.LabelService;
import com.dsdl.eidea.core.service.LanguageService;
import com.dsdl.eidea.core.service.MessageService;
import com.dsdl.eidea.devs.cons.IntelliKeyWord;
import com.dsdl.eidea.devs.def.ValidationDef;
import com.dsdl.eidea.devs.i18n.TranslateHelper;
import com.dsdl.eidea.devs.model.ColumnInfo;
import com.dsdl.eidea.devs.model.GenModelDto;
import com.dsdl.eidea.devs.model.JspModelProp;
import com.dsdl.eidea.util.StringUtil;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 刘大磊 on 2017/1/13 15:24.
 * 生成国际化label和message
 */
public class I18NGenerateStrategy {
    private LabelService labelService;
    private MessageService messageService;
    TableMetaDataBo tableMetaDataBo;
    LanguageService languageService;
    private GenModelDto genModelDto;

    /**
     *
     * @param genModelDto 生成 一个model的配置详情
     * @param tableMetaDataBo 读取数据库表元素信息
     * @param languageService 语言业务类
     * @param messageService 消息业务类
     * @param labelService 标签业务类
     */
    public I18NGenerateStrategy(GenModelDto genModelDto,TableMetaDataBo tableMetaDataBo,LanguageService languageService,MessageService messageService,LabelService labelService)
    {
        this.genModelDto=genModelDto;
        this.tableMetaDataBo=tableMetaDataBo;
        this.labelService=labelService;
        this.languageService=languageService;
        this.messageService=messageService;
    }
    public void generateLabel() {
        List<JspModelProp> jspModelProps = getOutPutList(tableMetaDataBo);

        String titleKey = StringUtil.lowerFirstChar(genModelDto.getModelName()) + ".title";
        LabelBo labelKeyBo = labelService.getLabelBo(titleKey);
        if (labelKeyBo == null) {
            labelKeyBo = new LabelBo();
            labelKeyBo.setCreated(true);
            labelKeyBo.setIsactive("Y");
            labelKeyBo.setKey(titleKey);
            labelKeyBo.setMsgtext(tableMetaDataBo.getRemark());

            List<LanguageBo> languageTrlList = languageService.getLanguageForActivated();
            for (LanguageBo languageBo : languageTrlList) {
                LabelTrlBo labelTrlBo = new LabelTrlBo();
                labelTrlBo.setLang(languageBo.getCode());
                if (!"zh_CN".equals(languageBo.getCode())) {
                    labelTrlBo.setMsgtext(TranslateHelper.translate(tableMetaDataBo.getRemark(), "zh_CN", languageBo.getCode()));
                } else {
                    labelTrlBo.setMsgtext(labelKeyBo.getMsgtext());
                }
                labelTrlBo.setLang(languageBo.getCode());
                labelTrlBo.setKey(titleKey);
                labelKeyBo.getLabelTrlBoList().add(labelTrlBo);

            }
            labelService.save(labelKeyBo);
        }

        for (JspModelProp jspModelProp : jspModelProps) {
            LabelBo labelBo = labelService.getLabelBo(jspModelProp.getPropertyLabel());
            if (labelBo == null) {
                labelBo = new LabelBo();
                labelBo.setCreated(true);
                labelBo.setIsactive("Y");
                labelBo.setKey(jspModelProp.getPropertyLabel());
                if(StringUtil.isEmpty(jspModelProp.getLabel()))
                {
                    labelBo.setMsgtext(StringUtil.propertyToLabel(jspModelProp.getProp()));
                }
                else
                {
                    labelBo.setMsgtext(jspModelProp.getLabel());
                }
                List<LanguageBo> languageTrlList = languageService.getLanguageForActivated();
                for (LanguageBo languageBo : languageTrlList) {
                    LabelTrlBo labelTrlBo = new LabelTrlBo();
                    labelTrlBo.setLang(languageBo.getCode());
                    if (!"zh_CN".equals(languageBo.getCode())) {
                        labelTrlBo.setMsgtext(TranslateHelper.translate(labelBo.getMsgtext(), "zh_CN", languageBo.getCode()));
                    } else {
                        labelTrlBo.setMsgtext(labelBo.getMsgtext());
                    }
                    labelTrlBo.setKey(jspModelProp.getPropertyLabel());
                    labelBo.getLabelTrlBoList().add(labelTrlBo);
                }

            }
            labelService.save(labelBo);
        }

    }

    public void generateMessage() {

    }

    private List<JspModelProp> getOutPutList(TableMetaDataBo tableData) {
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
                label = columnInfo.getRemarks();
                if (label == null) {
                    label = columnInfo.getColumnName();
                }
            }

            JspModelProp jspModelProp = new JspModelProp(StringUtil.fieldToProperty(columnInfo.getPropertyName()), label, date, !isreadOnly);
            jspModelProp.setPropertyLabel(StringUtil.lowerFirstChar(genModelDto.getModule()) + "." + StringUtil.lowerFirstChar(genModelDto.getModelName()) + ".label." + jspModelProp.getProp());
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
                    jspModelProp.setInputType(FieldInputType.DATETIMEPICKER.getKey());
                } else {
                    jspModelProp.setInputType(FieldInputType.DATETIMEPICKER.getKey());
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
