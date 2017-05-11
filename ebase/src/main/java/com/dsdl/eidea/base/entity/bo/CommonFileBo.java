package com.dsdl.eidea.base.entity.bo;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * Created by admin on 2017/5/4.
 */
@Getter
@Setter
public class CommonFileBo {
    private Integer id;
    private String fileName;
    private String fileType;
    private String path;
    private String extension;
    private Integer fileSize;
    private Date fileCreated;
    private Date fileUpdated;
    private Integer fileIsreadonly;
    private Integer fileIshidden;
    private Date created;
    private Integer commonFileSettingId;
    private String fileAbstract;
    private String fileKeyword;
    private Integer fileMode;
    private String fileBlob;

    private String uri;
    private String directoryUrl;
    private String tableId;
}
