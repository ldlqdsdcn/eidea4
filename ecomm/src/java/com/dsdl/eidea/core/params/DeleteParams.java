package com.dsdl.eidea.core.params;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Created by 刘大磊 on 2017/4/19 17:51.
 */
@Getter
@Setter
public class DeleteParams<PK> implements java.io.Serializable{
    private QueryParams queryParams;
    private PK[] ids;
}
