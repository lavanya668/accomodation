package model;

public class AllocateRoomDTO {

	private int roomId;
	private String studentRegNum;

	public AllocateRoomDTO() {

	}

	public AllocateRoomDTO(int roomId, String studentRegNum) {

		this.roomId = roomId;
		this.studentRegNum = studentRegNum;
	}

	public int getRoomId() {
		return roomId;
	}

	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}

	public String getStudentRegNum() {
		return studentRegNum;
	}

	public void setStudentRegNum(String studentRegNum) {
		this.studentRegNum = studentRegNum;
	}

}
