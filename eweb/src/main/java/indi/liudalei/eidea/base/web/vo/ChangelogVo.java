package indi.liudalei.eidea.base.web.vo;

import indi.liudalei.eidea.base.entity.bo.ChangelogBo;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created by 刘大磊 on 2016/12/23 10:43.
 */
@Getter
@Setter
public class ChangelogVo {
    private ChangelogBo changelogBo;
    private List<String> header;
    private List<List<String>> bodyList;
}
