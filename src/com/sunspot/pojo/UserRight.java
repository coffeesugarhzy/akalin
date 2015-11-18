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
 * UserRight entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "user_right", catalog = "limmai")
public class UserRight implements java.io.Serializable
{

    // Fields

    private String rightId;
    private UserMenus userMenus;
    private UserRole userRole;

    // Constructors

    /** default constructor */
    public UserRight()
    {
    }

    /** full constructor */
    public UserRight(UserMenus userMenus, UserRole userRole)
    {
        this.userMenus = userMenus;
        this.userRole = userRole;
    }

    // Property accessors
    @GenericGenerator(name = "generator", strategy = "uuid2")
    @Id
    @GeneratedValue(generator = "generator")
    @Column(name = "RIGHT_ID", unique = true, nullable = false, length = 36)
    public String getRightId()
    {
        return this.rightId;
    }

    public void setRightId(String rightId)
    {
        this.rightId = rightId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MENU_ID")
    public UserMenus getUserMenus()
    {
        return this.userMenus;
    }

    public void setUserMenus(UserMenus userMenus)
    {
        this.userMenus = userMenus;
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

}