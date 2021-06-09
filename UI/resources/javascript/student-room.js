$(document).ready(function () {
  if (localStorage.getItem("appUserType") == "student") {
    var studentRegNum = localStorage.getItem("stuRegNum");
    $.ajax({
      url:
        "http://localhost:8080/student_accommodation/student/room?stuRegId=" +
        studentRegNum,
      type: "GET",
      datatype: "JSON",
      encode: true,
    }).done(function (data) {
      if (data.returnMessage == "NoRooms") {
        alert("Student has got no rooms allocated");
        window.location.href = "student-home.html";
      } else {
        $("#stu-room-type").val(data.roomType);
        $("#stu-room-location").val(data.roomLocation);
        $("#stu-room-charge").val(data.roomMonthlyCharge);
        $("#stu-room-payment").val(data.paymentStatus);
        $("#roomId").val(data.roomId);
      }
    });
  } else {
    alert("You are not authorised to view this page.");
    localStorage.clear();
    window.location.href = "index.html";
  }

  $("#releaseRoom").on("click", function () {
    var allocateRoom = {
      roomId: $("#roomId").val(),
    };
    console.log(allocateRoom);
    $.ajax({
      url: "http://localhost:8080/student_accommodation/student/room",
      type: "PUT",
      dataType: "json",
      data: JSON.stringify(allocateRoom),
      encode: true,
    })
      .done(function (data) {
        alert(data.returnMessage);
        window.location.href = "student-home.html";
      })
      .fail(function (data) {
        alert(data.responseJSON.returnMessage);
        window.location.href = "student-room.html";
      });
  });
});
