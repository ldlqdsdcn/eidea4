package indi.liudalei.eidea.base.web.vo;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by 刘大磊 on 2016/12/27 16:55.
 * 权限验证结果
 */
@Getter
@Setter
public class VerifiedResult {
    private boolean canAccessed;
    private String message;
}
