package com.qipilang.lvyouplatform.bean;


/************************************************
 * DESCRIPTION:	用户对象
 * @author 		albery
 * @since 		2016.3.2
 ************************************************/
public class UserInfo {
	private int id;						//用户ID
	private String userName;			//用户名
	private String password;			//密码
	private String confirmPassword;
	private String gender;				//性别
	private float age;					//年龄
	private String birthday;
	private String teliphoneNumber;		//电话号码
	private String ppQuestion;			//密保问题
	private String ppAnswer;			//密保答案
	private String address;				//住址
	private String email;				//电子邮件
	private String hobby;				//兴趣爱好
	private String educationBackgtound;	//学历
	private String headUrl;				//头像连接
	
	public UserInfo(){}

	public int getId() {
		return id;
	}
	public void setId(int id){
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPassword(){
		return this.confirmPassword;
	}
	
	public void setConfirmPassword(String confirmPassword){
		this.confirmPassword = confirmPassword;
	}
	
	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public float getAge() {
		return age;
	}

	public void setAge(float age) {
		this.age = age;
	}

	public String getTeliphoneNumber() {
		return teliphoneNumber;
	}

	public void setTeliphoneNumber(String teliphoneNumber) {
		this.teliphoneNumber = teliphoneNumber;
	}

	public String getPpQuestion() {
		return ppQuestion;
	}

	public void setPpQuestion(String ppQuestion) {
		this.ppQuestion = ppQuestion;
	}

	public String getPpAnswer() {
		return ppAnswer;
	}

	public void setPpAnswer(String ppAnswer) {
		this.ppAnswer = ppAnswer;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getHobby() {
		return hobby;
	}

	public void setHobby(String hobby) {
		this.hobby = hobby;
	}

	public String getEducationBackgtound() {
		return educationBackgtound;
	}

	public void setEducationBackgtound(String educationBackgtound) {
		this.educationBackgtound = educationBackgtound;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getHeadUrl() {
		return headUrl;
	}

	public void setHeadUrl(String headUrl) {
		this.headUrl = headUrl;
	}
}
