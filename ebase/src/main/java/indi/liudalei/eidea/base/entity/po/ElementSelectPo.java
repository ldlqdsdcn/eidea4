package indi.liudalei.eidea.base.entity.po;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;

/**
 * Created by 车东明 on 2017/5/27.
 */
@Entity
@Getter
@Setter
@Table(name = "core_element_select",catalog = "e_idea")
public class ElementSelectPo {
    @Id
    @Column(name = "id",nullable = false,unique = true,length = 11)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "[sql]",length = 4000)
    @Length(max = 4000,message = "")
    private String sql;
    @Column(name = "element_id",length = 11)
    private Integer elementId;
}
