package com.example.jsonexercise;

public class UserInfo {
	
	private static String ID = null;
	private static String FirstName = null;
	private static String LastName = null;
	private static String Gender = null;
	private static String UserName = null;
	private static String Link = null;
	private static String Locale = null;
	
	
	private static int ErrorCode = 0;
	private static String ErrorMsg = null;
	
	public UserInfo() {

	}
	public String getID() {
		return ID;
	}
	public void setID(Long iD) {
		ID = iD.toString();
	}
	public String getFirstName() {
		return FirstName;
	}
	public void setFirstName(String firstName) {
		FirstName = firstName;
	}
	public String getLastName() {
		return LastName;
	}
	public void setLastName(String lastName) {
		LastName = lastName;
	}
	public String getGender() {
		return Gender;
	}
	public void setGender(String gender) {
		Gender = gender;
	}
	public String getUserName() {
		return UserName;
	}
	public void setUserName(String userName) {
		UserName = userName;
	}
	public String getLink() {
		return Link;
	}
	public void setLink(String link) {
		Link = link;
	}
	public String getLocale() {
		return Locale;
	}
	public void setLocale(String locale) {
		Locale = locale;
	}
	public int getErrorCode() {
		return ErrorCode;
	}
	public void setErrorCode(int errorCode) {
		ErrorCode = errorCode;
	}
	public String getErrorMsg() {
		return ErrorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		ErrorMsg = errorMsg;
	}
}
