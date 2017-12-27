package indi.liudalei.eidea.base.entity.bo;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by Bobo on 2016/12/15 10:31.
 */
@Getter
@Setter
public class ModuleBo {
    private Integer id;
    @NotBlank(message = "module.name.empty.check")
    @Length(min = 2,max = 45,message = "module.name.empty.check")
    private String name;
    @Length(max = 200,message = "module.name.check")
    private String remark;
    private String isactive;
    private Integer[] menuIds;
    private Integer[] dirIds;
    private boolean created=false;

}
