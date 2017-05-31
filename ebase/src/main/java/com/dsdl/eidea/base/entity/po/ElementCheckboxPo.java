package com.dsdl.eidea.base.entity.po;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * Created by 刘大磊 on 2017/5/29.
 */
@Entity
@Table(name = "core_element_checkbox", catalog = "e_idea")
@Getter
@Setter
public class ElementCheckboxPo {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", length = 11, nullable = false, unique = true)
    @Id
    private Integer id;
    @Column(name = "element_id",nullable = false)
    private Integer elementId;
    @Column(name = "checked_value",nullable = false)
    private String checkedValue;
    @Column(name = "unchecked_value",nullable = false)
    private String uncheckedValue;
}
