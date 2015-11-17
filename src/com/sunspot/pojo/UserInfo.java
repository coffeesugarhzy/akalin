package com.sunspot.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

/**
 * UserInfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "user_info", catalog = "limmai")
public class UserInfo implements java.io.Serializable
{

    // Fields

    private String userId;
    private UserRole userRole;
    private String userName;
    private Integer isSysuser;
    private Integer userType;
    private String logo;
    private String loginName;
    private String loginPassword;
    private String telphone;
    private String remark;
    private String addDate;
    private String updateDate;

    // Constructors

    /** default constructor */
    public UserInfo()
    {
    }

    /** full constructor */
    public UserInfo(UserRole userRole, String userName, Integer isSysuser,
            Integer userType, String logo, String loginName,
            String loginPassword, String telphone, String remark,
            String addDate, String updateDate)
    {
        this.userRole = userRole;
        this.userName = userName;
        this.isSysuser = isSysuser;
        this.userType = userType;
        this.logo = logo;
        this.loginName = loginName;
        this.loginPassword = loginPassword;
        this.telphone = telphone;
        this.remark = remark;
        this.addDate = addDate;
        this.updateDate = updateDate;
    }

    // Property accessors
    @GenericGenerator(name = "generator", strategy = "uuid2")
    @Id
    @GeneratedValue(generator = "generator")
    @Column(name = "USER_ID", unique = true, nullable = false, length = 36)
    public String getUserId()
    {
        return this.userId;
    }

    public void setUserId(String userId)
    {
        this.userId = userId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ROLE_ID")
    public UserRole getUserRole()
    {
        return this.userRole;
    }

    public void setUserRole(UserRole userRole)
    {
        this.userRole = userRole;
    }

    @Column(name = "USER_NAME", length = 64)
    public String getUserName()
    {
        return this.userName;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    @Column(name = "IS_SYSUSER")
    public Integer getIsSysuser()
    {
        return this.isSysuser;
    }

    public void setIsSysuser(Integer isSysuser)
    {
        this.isSysuser = isSysuser;
    }

    @Column(name = "USER_TYPE")
    public Integer getUserType()
    {
        return this.userType;
    }

    public void setUserType(Integer userType)
    {
        this.userType = userType;
    }

    @Column(name = "LOGO")
    public String getLogo()
    {
        return this.logo;
    }

    public void setLogo(String logo)
    {
        this.logo = logo;
    }

    @Column(name = "LOGIN_NAME", length = 36)
    public String getLoginName()
    {
        return this.loginName;
    }

    public void setLoginName(String loginName)
    {
        this.loginName = loginName;
    }

    @Column(name = "LOGIN_PASSWORD", length = 36)
    public String getLoginPassword()
    {
        return this.loginPassword;
    }

    public void setLoginPassword(String loginPassword)
    {
        this.loginPassword = loginPassword;
    }

    @Column(name = "TELPHONE", length = 20)
    public String getTelphone()
    {
        return this.telphone;
    }

    public void setTelphone(String telphone)
    {
        this.telphone = telphone;
    }

    @Column(name = "REMARK", length = 65535)
    public String getRemark()
    {
        return this.remark;
    }

    public void setRemark(String remark)
    {
        this.remark = remark;
    }

    @Column(name = "ADD_DATE", length = 19)
    public String getAddDate()
    {
        return this.addDate;
    }

    public void setAddDate(String addDate)
    {
        this.addDate = addDate;
    }

    @Column(name = "UPDATE_DATE", length = 19)
    public String getUpdateDate()
    {
        return this.updateDate;
    }

    public void setUpdateDate(String updateDate)
    {
        this.updateDate = updateDate;
    }

}