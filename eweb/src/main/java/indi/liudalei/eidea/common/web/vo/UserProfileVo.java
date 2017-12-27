package indi.liudalei.eidea.common.web.vo;

import indi.liudalei.eidea.base.entity.bo.UserBo;
import indi.liudalei.eidea.base.entity.bo.UserContent;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by 刘大磊 on 2017/1/24 10:07.
 * 用户设置
 */
@Getter
@Setter
public class UserProfileVo {
    private UserBo user;
    private UserContent userContent;
}
