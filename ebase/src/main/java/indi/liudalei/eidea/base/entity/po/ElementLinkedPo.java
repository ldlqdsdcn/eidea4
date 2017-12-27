package indi.liudalei.eidea.base.entity.po;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * Created by 刘大磊 on 2017/5/29.
 */
@Entity
@Table(name = "core_element_linked", catalog = "e_idea")
@Getter
@Setter
public class ElementLinkedPo {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", length = 11, nullable = false, unique = true)
    @Id
    private Integer id;
    @Column(name = "element_id")
    private Integer elementId;
    @Column(name = "linked_table_id")
    private Integer linkedTableId;
    @Column(name = "linked_column_id")
    private Integer linkedColumnId;
    @Column(name = "linked_column_value")
    private Integer linkedColumnValue;
}
