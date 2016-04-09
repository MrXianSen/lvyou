package com.qipilang.lvyouplatform.bean;


/************************************************
 * DESCRIPTION:	�û�����
 * @author 		albery
 * @since 		2016.3.2
 ************************************************/
public class UserInfo {
	private int id;						//�û�ID
	private String userName;			//�û���
	private String password;			//����
	private String confirmPassword;
	private String gender;				//�Ա�
	private float age;					//����
	private String birthday;
	private String teliphoneNumber;		//�绰����
	private String ppQuestion;			//�ܱ�����
	private String ppAnswer;			//�ܱ���
	private String address;				//סַ
	private String email;				//�����ʼ�
	private String hobby;				//��Ȥ����
	private String educationBackgtound;	//ѧ��
	private String headUrl;				//ͷ������
	
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
