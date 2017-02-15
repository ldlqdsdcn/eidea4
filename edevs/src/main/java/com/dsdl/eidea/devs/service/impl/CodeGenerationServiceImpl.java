package com.dsdl.eidea.devs.service.impl;

import com.dsdl.eidea.base.def.ActivateDef;
import com.dsdl.eidea.core.entity.bo.*;
import com.dsdl.eidea.core.service.LabelService;
import com.dsdl.eidea.core.service.LanguageService;
import com.dsdl.eidea.core.service.MessageService;
import com.dsdl.eidea.devs.i18n.TranslateHelper;
import com.dsdl.eidea.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by 刘大磊 on 2017/1/13 15:59.
 * 自动翻译国际化消息，把翻译记录生成
 */
@Service
public class CodeGenerationServiceImpl {
    @Autowired
    private MessageService messageService;
    @Autowired
    private LabelService labelService;
    @Autowired
    private LanguageService languageService;

    public void saveGenerateionLabelAndMessage(TableMetaDataBo tableMetaDataBo) {
        String remark = tableMetaDataBo.getRemark();
        String module = StringUtil.upperFirstChar(tableMetaDataBo.getName());
        LabelBo labelBo = new LabelBo();
        labelBo.setCreated(true);
        labelBo.setKey(module + ".title");
        labelBo.setIsactive(ActivateDef.ACTIVATED.getKey());
        labelBo.setMsgtext(remark);
        List<LanguageBo> languageBoList = languageService.findLanguageListForActivated();
        labelService.save(labelBo);
        for (LanguageBo languageBo : languageBoList) {
            LabelTrlBo labelTrlBo = new LabelTrlBo();
            labelTrlBo.setLang(languageBo.getCode());
            if ("zh_CN".equals(languageBo.getCode())) {
                labelTrlBo.setMsgtext(labelBo.getMsgtext());
            } else {
                String msgText = TranslateHelper.translate(labelBo.getMsgtext(), "zh_CN", languageBo.getCode());
                labelTrlBo.setMsgtext(msgText);
            }
            labelBo.getLabelTrlBoList().add(labelTrlBo);
        }

        List<ColumnMetaDataBo> columnList = tableMetaDataBo.getColumnList();
        for (ColumnMetaDataBo columnMetaDataBo : columnList) {
            LabelBo fieldLabel = new LabelBo();
            fieldLabel.setKey(module + ".label." + columnMetaDataBo.getColumnName());
            fieldLabel.setIsactive(ActivateDef.ACTIVATED.getKey());
            fieldLabel.setMsgtext(columnMetaDataBo.getRemarks());
            fieldLabel.setCreated(true);

            for (LanguageBo languageBo : languageBoList) {
                LabelTrlBo labelTrlBo = new LabelTrlBo();
                labelTrlBo.setLang(languageBo.getCode());
                if ("zh_CN".equals(languageBo.getCode())) {
                    labelTrlBo.setMsgtext(labelBo.getMsgtext());
                } else {
                    String msgText = TranslateHelper.translate(labelBo.getMsgtext(), "zh_CN", languageBo.getCode());
                    labelTrlBo.setMsgtext(msgText);
                }
                labelTrlBo.setKey(fieldLabel.getKey());
                labelBo.getLabelTrlBoList().add(labelTrlBo);
            }
            labelService.save(labelBo);
        }
    }
}
