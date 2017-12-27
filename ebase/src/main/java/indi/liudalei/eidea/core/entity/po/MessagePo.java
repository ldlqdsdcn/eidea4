package indi.liudalei.eidea.core.entity.po;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * CoreMessage entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "core_message", catalog = "e_idea")
@Getter
@Setter
public class MessagePo implements java.io.Serializable {

    // Fields
    @Id
    @Column(name = "_key", unique = true, nullable = false, length = 100)
    private String key;

    @Column(name = "msgtext", nullable = false, length = 500)
    private String msgtext;

    @Column(name = "isactive", nullable = false, length = 1)
    private String isactive;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "coreMessage")
    private List<MessageTrlPo> coreMessageTrls = new ArrayList<MessageTrlPo>(0);

}