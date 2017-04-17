package com.dsdl.eidea.devs.service;

import com.dsdl.eidea.devs.model.BoInfo;
import com.dsdl.eidea.devs.model.GenModelDto;
import com.dsdl.eidea.devs.model.GenSettings;

import java.util.List;

/**
 * Created by 刘大磊 on 2017/1/11 14:38.
 */
public interface CodeGenerationService {
     void generateCode(GenSettings genSettings);
}
