
/**
* 版权所有 刘大磊 2013-07-01
* 作者：刘大磊
* 电话：13336390671
* email:ldlqdsd@126.com
*/
package com.dsdl.eidea.sys.entity.po;

import java.util.Date;
import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;
/**
* table name sys_user_session2
*            用户Session
* Date:2017-05-08 09:55:07
**/
@Getter
@Setter
@Entity(name = "sys_user_session2")
public class UserSession2Po implements java.io.Serializable {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "[id]")
    @Id
    private Integer id;
    /**
    * 
    **/
    @Column(name = "[session_id]",length =100 )
    private String sessionId;
    /**
    * 
    **/
    @Column(name = "[login_date]",length =19 )
    private Date loginDate;
    /**
    * 
    **/
    @Column(name = "[logout_date]",length =19 )
    private Date logoutDate;
    /**
    * 
    **/
    @Column(name = "[remote_addr]",length =50 )
    private String remoteAddr;
    /**
    * 
    **/
    @Column(name = "[remote_host]",length =50 )
    private String remoteHost;
    /**
    * 
    **/
    @Column(name = "[sys_user_id]" )
    private Integer sysUserId;
    /**
    * 
    **/
    @Column(name = "[token]",length =800 )
    private String token;
}