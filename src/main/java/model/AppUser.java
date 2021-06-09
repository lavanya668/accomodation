package model;

public class AppUser {

	private int appUserId;
	private String firstName;
	private String lastName;
	private String email;
	private String appUserType;
	private String password;
	private String studentRegNum;

	public AppUser() {
	}

	public AppUser(int appUserId, String firstName, String lastName, String email, String appUserType, String password,
			String studentRegNum) {
		this.appUserId = appUserId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.appUserType = appUserType;
		this.password = password;
		this.studentRegNum = studentRegNum;
	}

	public int getAppUserId() {
		return appUserId;
	}

	public void setAppUserId(int appUserId) {
		this.appUserId = appUserId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAppUserType() {
		return appUserType;
	}

	public void setAppUserType(String appUserType) {
		this.appUserType = appUserType;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getStudentRegNum() {
		return studentRegNum;
	}

	public void setStudentRegNum(String studentRegNum) {
		this.studentRegNum = studentRegNum;
	}

}
