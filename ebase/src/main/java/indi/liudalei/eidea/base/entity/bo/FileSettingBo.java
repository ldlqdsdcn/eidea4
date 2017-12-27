package indi.liudalei.eidea.base.entity.bo;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * Created by admin on 2017/5/17.
 */
@Getter
@Setter
public class FileSettingBo {
    private Integer id;
    private String name;
    private String rootDirectory;
    private Integer fileSize;
    private String fileTypes;
    private Integer storageMode;
    private Integer ftpcommectionId;
    private Date created;
    private Integer moduleId;
}
