package indi.liudalei.eidea.core.entity.bo;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 刘大磊 on 2016/12/17 13:12.
 */
@Getter
@Setter
public class SearchBo {

    private Integer id;
    @NotNull(message = "pagemenu.name.check")
    private String name;
    @NotNull(message = "identity.not.allowed.empty")
    private String uri;
    @NotNull(message = "query.types.empty")
    private Integer showType;
    @Length(min = 1,max = 1,message = "isactive.length")
    private String isactive;
    @Length(max = 200,message = "module.name.check")
    private String remark;

    private Integer windowId;
    /**
     * 查询页显示方式
     */
    private String showTypeStr;
    private List<SearchColumnBo> searchColumnBoList = new ArrayList<>(0);
}
