package com.safe.UserLogon;

public class DoyouLogon {
	private String username="";
	private String userpassword="";
	private Checkstr check=new Checkstr();
	public DoyouLogon(){}
	public void setUsername(String username){
		this.username=check.dostring(username);
	}
	public String getUsername(){
		return this.username;
	}
	public void setUserpassword(String userpassword){
		this.userpassword=check.dostring(userpassword);
	}
	public String getUserpassword(){
		return this.userpassword;
	}
	public String checkuser(){
		String backstr="";
		if(this.username.equals("")){
			backstr+="<li>请输入<b>用户名！</b></li><br>";
		}
		if(this.userpassword.equals("")){
			backstr+="<li>请输入<b>密&nbsp;码！</b></li>";
		}
		return backstr;
	}
}
