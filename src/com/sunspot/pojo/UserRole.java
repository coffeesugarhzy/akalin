package com.sunspot.pojo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * UserRole entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "user_role", catalog = "limmai")
public class UserRole implements java.io.Serializable
{

    // Fields

    private String roleId;
    private String roleName;
    private Integer isSysrole;
    private String addDate;
    private String updateDate;
    private List<UserRight> userRights = new ArrayList<UserRight>(0);
    private List<UserInfo> userInfos = new ArrayList<UserInfo>(0);

    // Constructors

    /** default constructor */
    public UserRole()
    {
    }

    /** full constructor */
    public UserRole(String roleName, Integer isSysrole, String addDate,
            String updateDate, List<UserRight> userRights,
            List<UserInfo> userInfos)
    {
        this.roleName = roleName;
        this.isSysrole = isSysrole;
        this.addDate = addDate;
        this.updateDate = updateDate;
        this.userRights = userRights;
        this.userInfos = userInfos;
    }

    // Property accessors
    @GenericGenerator(name = "generator", strategy = "uuid2")
    @Id
    @GeneratedValue(generator = "generator")
    @Column(name = "ROLE_ID", unique = true, nullable = false, length = 36)
    public String getRoleId()
    {
        return this.roleId;
    }

    public void setRoleId(String roleId)
    {
        this.roleId = roleId;
    }

    @Column(name = "ROLE_NAME", length = 64)
    public String getRoleName()
    {
        return this.roleName;
    }

    public void setRoleName(String roleName)
    {
        this.roleName = roleName;
    }

    @Column(name = "IS_SYSROLE")
    public Integer getIsSysrole()
    {
        return this.isSysrole;
    }

    public void setIsSysrole(Integer isSysrole)
    {
        this.isSysrole = isSysrole;
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

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "userRole")
    public List<UserRight> getUserRights()
    {
        return this.userRights;
    }

    public void setUserRights(List<UserRight> userRights)
    {
        this.userRights = userRights;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "userRole")
    public List<UserInfo> getUserInfos()
    {
        return this.userInfos;
    }

    public void setUserInfos(List<UserInfo> userInfos)
    {
        this.userInfos = userInfos;
    }

}