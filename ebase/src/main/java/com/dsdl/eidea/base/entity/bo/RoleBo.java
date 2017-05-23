package com.dsdl.eidea.base.entity.bo;


import com.dsdl.eidea.base.entity.po.ModuleRolePo;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import java.util.List;

@Getter
@Setter
public class RoleBo {
	private Integer id;
	@NotBlank(message = "role.number.validate")
	@Length(min =2 ,max =20 ,message = "role.number.length")
	private String no;
	@NotBlank(message = "pagemenu.name.check")
	@Length(min = 1,max = 45,message = "pagemenu.name.prompt")
	private String name;
	@Length(max = 200,message = "pagemenu.remark.check")
	private String remark;
	@Length(min = 1,max = 1,message = "isactive.length")
	private String isactive;
	private List<RoleOrgaccessBo> roleOrgAccessBoList;
	private List<ModuleRoleBo> moduleRoleBoList;
}
