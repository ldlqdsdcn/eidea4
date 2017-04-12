package com.dsdl.eidea.base.entity.bo;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by admin on 2016/12/19.
 */
@Data
@Getter
@Setter
public class DirectoryBo {

    private Integer id;
    @NotBlank(message = "pagemenu.name.check")
    @Length(min = 1,max = 45,message = "pagemenu.name.prompt")
    private String name;
    @NotBlank(message = "link.address.not.empty")
    @Length(min = 1,max = 50,message = "link.address.length")
    private String directory;
    @Length(max = 200,message = "pagemenu.remark.check")
    private String remark;
    @Length(min = 1,max = 1,message = "isactive.length")
    private String isactive;

    private String repository;;
    private boolean created=false;
}
