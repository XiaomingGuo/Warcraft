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
			backstr+="<li>������<b>�û�����</b></li><br>";
		}
		if(this.userpassword.equals("")){
			backstr+="<li>������<b>��&nbsp;�룡</b></li>";
		}
		return backstr;
	}
}
