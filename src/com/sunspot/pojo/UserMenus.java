package com.sunspot.pojo;

import java.util.HashSet;
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
 * UserMenus entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "user_menus", catalog = "limmai")
public class UserMenus implements java.io.Serializable
{

    // Fields

    private String menuId;
    private String parentId;
    private String menuName;
    private String menuUrl;
    private Integer menuOrder;
    private Integer level;
    private Integer menuType;
    private Set<UserRight> userRights = new HashSet<UserRight>(0);

    // Constructors

    /** default constructor */
    public UserMenus()
    {
    }

    /** full constructor */
    public UserMenus(String parentId, String menuName, String menuUrl,
            Integer menuOrder, Integer level, Integer menuType,
            Set<UserRight> userRights)
    {
        this.parentId = parentId;
        this.menuName = menuName;
        this.menuUrl = menuUrl;
        this.menuOrder = menuOrder;
        this.level = level;
        this.menuType = menuType;
        this.userRights = userRights;
    }

    // Property accessors
    @GenericGenerator(name = "generator", strategy = "uuid2")
    @Id
    @GeneratedValue(generator = "generator")
    @Column(name = "MENU_ID", unique = true, nullable = false, length = 36)
    public String getMenuId()
    {
        return this.menuId;
    }

    public void setMenuId(String menuId)
    {
        this.menuId = menuId;
    }

    @Column(name = "PARENT_ID", length = 36)
    public String getParentId()
    {
        return this.parentId;
    }

    public void setParentId(String parentId)
    {
        this.parentId = parentId;
    }

    @Column(name = "MENU_NAME", length = 36)
    public String getMenuName()
    {
        return this.menuName;
    }

    public void setMenuName(String menuName)
    {
        this.menuName = menuName;
    }

    @Column(name = "MENU_URL", length = 128)
    public String getMenuUrl()
    {
        return this.menuUrl;
    }

    public void setMenuUrl(String menuUrl)
    {
        this.menuUrl = menuUrl;
    }

    @Column(name = "MENU_ORDER")
    public Integer getMenuOrder()
    {
        return this.menuOrder;
    }

    public void setMenuOrder(Integer menuOrder)
    {
        this.menuOrder = menuOrder;
    }

    @Column(name = "LEVEL")
    public Integer getLevel()
    {
        return this.level;
    }

    public void setLevel(Integer level)
    {
        this.level = level;
    }

    @Column(name = "MENU_TYPE")
    public Integer getMenuType()
    {
        return this.menuType;
    }

    public void setMenuType(Integer menuType)
    {
        this.menuType = menuType;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "userMenus")
    public Set<UserRight> getUserRights()
    {
        return this.userRights;
    }

    public void setUserRights(Set<UserRight> userRights)
    {
        this.userRights = userRights;
    }

}