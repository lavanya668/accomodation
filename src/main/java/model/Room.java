package model;

public class Room {
	private int roomId;
	private String roomType;
	private String roomLocation;
	private String roomMonthlyCharge;
	private String roomStatus;
	private String paymentStatus;
	private String studentRegId;

	public Room() {
	}

	/* Show all rooms for admin */
	public Room(int roomId, String roomType, String roomLocation, String roomMonthlyCharge, String roomStatus,
			String paymentStatus, String studentRegId) {
		this.roomId = roomId;
		this.roomType = roomType;
		this.roomLocation = roomLocation;
		this.roomMonthlyCharge = roomMonthlyCharge;
		this.roomStatus = roomStatus;
		this.paymentStatus = paymentStatus;
		this.studentRegId = studentRegId;
	}

	/* Show available rooms for student */
	public Room(int roomId, String roomType, String roomLocation, String roomMonthlyCharge, String roomStatus) {
		this.roomId = roomId;
		this.roomType = roomType;
		this.roomLocation = roomLocation;
		this.roomMonthlyCharge = roomMonthlyCharge;
		this.roomStatus = roomStatus;
	}

	public int getRoomId() {
		return roomId;
	}

	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}

	public String getRoomType() {
		return roomType;
	}

	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}

	public String getRoomLocation() {
		return roomLocation;
	}

	public void setRoomLocation(String roomLocation) {
		this.roomLocation = roomLocation;
	}

	public String getRoomMonthlyCharge() {
		return roomMonthlyCharge;
	}

	public void setRoomMonthlyCharge(String roomMonthlyCharge) {
		this.roomMonthlyCharge = roomMonthlyCharge;
	}

	public String getRoomStatus() {
		return roomStatus;
	}

	public void setRoomStatus(String roomStatus) {
		this.roomStatus = roomStatus;
	}

	public String getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public String getStudentRegId() {
		return studentRegId;
	}

	public void setStudentRegId(String studentRegId) {
		this.studentRegId = studentRegId;
	}

}
