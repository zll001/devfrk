package com.bocloud.paas.auth.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
/**
 * 用户bean
 * @author xzg
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User implements Serializable{

	// @Fields serialVersionUID : TODO
	private static final long serialVersionUID = -8676220962139304566L;

	private Integer userId;

	private String ldapUid;
	 
	private String userName;

	private String userPass;

	private String userMail;

	private String userPhone;

	private String userCompany;
	
	private String userRealName;

	private Byte userStatus;
	
	private Byte userType;

	private Date userCreatedate;

	private Integer userCreator;

	/* #多租户情况，添加租户的ID信息 */
	//private Integer tenantId;
	private String userLogingSessionId;
	
	private Byte isAdmin;
	
	private Byte loginStatus;
	
	private Integer defaultEnvId;
	
	private Integer errorCount;
	
	private Integer firstLogin;
	
	private Date lastTime;
	
	//部门
	private String userDept;
	//用户
	private String userJobNum;
	
	//用户关联的组织机构
	private List<Integer> orgIds;
	
	//新增用户所属DN信息
	private String distinguishedName;
	

	public String getUserDept() {
		return userDept;
	}

	public void setUserDept(String userDept) {
		this.userDept = userDept;
	}

	public String getUserJobNum() {
		return userJobNum;
	}

	public void setUserJobNum(String userJobNum) {
		this.userJobNum = userJobNum;
	}

	public Date getLastTime() {
		return lastTime;
	}

	public void setLastTime(Date lastTime) {
		this.lastTime = lastTime;
	}

	public Integer getErrorCount() {
		return errorCount;
	}

	public void setErrorCount(Integer errorCount) {
		this.errorCount = errorCount;
	}

	public Integer getFirstLogin() {
		return firstLogin;
	}

	public void setFirstLogin(Integer firstLogin) {
		this.firstLogin = firstLogin;
	}

	public String getUserLogingSessionId() {
		return userLogingSessionId;
	}

	public void setUserLogingSessionId(String userLogingSessionId) {
		this.userLogingSessionId = userLogingSessionId;
	}

	public Byte getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(Byte isAdmin) {
		this.isAdmin = isAdmin;
	}

	
	public User() {

	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}


	public String getLdapUid() {
		return ldapUid;
	}

	public void setLdapUid(String ldapUid) {
		this.ldapUid = ldapUid;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName == null ? null : userName.trim();
	}

	public String getUserPass() {
		return userPass;
	}

	public void setUserPass(String userPass) {
		this.userPass = userPass == null ? null : userPass.trim();
	}

	public String getUserMail() {
		return userMail;
	}

	public void setUserMail(String userMail) {
		this.userMail = userMail == null ? null : userMail.trim();
	}

	public String getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone == null ? null : userPhone.trim();
	}

	public String getUserCompany() {
		return userCompany;
	}

	public void setUserCompany(String userCompany) {
		this.userCompany = userCompany == null ? null : userCompany.trim();
	}

/*	public Integer getUserLevel() {
		return userLevel;
	}

	public void setUserLevel(Integer userLevel) {
		this.userLevel = userLevel;
	}*/

	public Byte getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(Byte userStatus) {
		this.userStatus = userStatus;
	}



	public Date getUserCreatedate() {
		return userCreatedate;
	}

	public void setUserCreatedate(Date userCreatedate) {
		this.userCreatedate = userCreatedate;
	}

	public Integer getUserCreator() {
		return userCreator;
	}

	public void setUserCreator(Integer userCreator) {
		this.userCreator = userCreator;
	}

	/**
	 * @return the loginStatus
	 */
	public Byte getLoginStatus() {
		return loginStatus;
	}

	/**
	 * @param loginStatus the loginStatus to set
	 */
	public void setLoginStatus(Byte loginStatus) {
		this.loginStatus = loginStatus;
	}

	public Integer getDefaultEnvId() {
		return defaultEnvId;
	}

	public void setDefaultEnvId(Integer defaultEnvId) {
		this.defaultEnvId = defaultEnvId;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", userName=" + userName + ", errorCount=" + errorCount + "]";
	}

	public String getUserRealName() {
		return userRealName;
	}

	public void setUserRealName(String userRealName) {
		this.userRealName = userRealName;
	}

	public List<Integer> getOrgIds() {
		return orgIds;
	}

	public void setOrgIds(List<Integer> orgIds) {
		this.orgIds = orgIds;
	}

	public Byte getUserType() {
		return userType;
	}

	public void setUserType(Byte userType) {
		this.userType = userType;
	}

	public String getDistinguishedName() {
		return distinguishedName;
	}

	public void setDistinguishedName(String distinguishedName) {
		this.distinguishedName = distinguishedName;
	}
	
	
}