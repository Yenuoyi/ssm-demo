package com.ye.domain;

import java.io.Serializable;
import java.util.Date;

public class UserBean implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
    private String name;
    private String password;
    private int roleid;
    private String professional;
    private String iphone;
	private String email;
	private String headpicurl;
    private Date create_time;
	private String code;
    public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getIphone() {
		return iphone;
	}

	public void setIphone(String iphone) {
		this.iphone = iphone;
	}
    public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getProfessional() {
		return professional;
	}

	public void setProfessional(String professional) {
		this.professional = professional;
	}



    public String getHeadpicurl() {
		return headpicurl;
	}

	public void setHeadpicurl(String headpicurl) {
		this.headpicurl = headpicurl;
	}

	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}

	public int getRoleid() {
		return roleid;
	}

	public void setRoleid(int roleid) {
		this.roleid = roleid;
	}

	public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

	@Override
	public String toString() {
		return "UserBean [id=" + id + ", name=" + name + ", password=" + password + ", roleid=" + roleid
				+ ", professional=" + professional + ", iphone=" + iphone + ", email=" + email + ", headpicurl="
				+ headpicurl + ", create_time=" + create_time + ", code=" + code + "]";
	}
	
	public String toEmailString() {
		return "id=" + id + ", name=" + name +  ", professional=" + professional + ", iphone=" + iphone + ", email=" + email  + ", create_time=" + create_time;
	}
	
}