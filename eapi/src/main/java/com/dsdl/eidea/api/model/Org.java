package com.dsdl.eidea.api.model;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by 刘大磊 on 2017/4/13 14:21.
 */
@Getter
@Setter
public class Org {
    private Integer id;
    private String name;
    private Client client;
}
