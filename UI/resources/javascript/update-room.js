$(document).ready(function () {
  if (localStorage.getItem("appUserType") == "admin") {
    //set the initial values by room id
    url = window.location.href;
    var roomId = url.split("?")[1].split("=")[1];

    $.ajax({
      url:
        "http://localhost:8080/student_accommodation/admin/room?roomId=" +
        roomId,
      type: "GET",
      dataType: "json",
      encode: true,
    })
      .done(function (data) {
        $("select[name=room-type]").val(data.roomType);
        $("#room-location").val(data.roomLocation);
        $("#room-monthly-charge").val(data.roomMonthlyCharge);
      })
      .fail(function (data) {
        alert(data.responseJSON.message);
      });
    $("#update-room-form").on("submit", function (event) {
      event.preventDefault();
      var addRoomSubmit = {
        roomType: $("select[name=room-type] option").filter(":selected").val(),
        roomLocation: $("#room-location").val(),
        roomMonthlyCharge: $("#room-monthly-charge").val(),
        roomId: roomId,
      };

      $.ajax({
        url: "http://localhost:8080/student_accommodation/admin/room",
        type: "PUT",
        dataType: "json",
        data: JSON.stringify(addRoomSubmit),
        encode: true,
      })
        .done(function (data) {
          alert(data.returnMessage);
          window.location.href = "admin-home.html";
        })
        .fail(function (data) {
          alert(data.responseJSON.returnMessage);
          window.location.href = "admin-home.html";
        });
    });
  } else {
    alert("This is restricted, please login as an admin to view this page");
    localStorage.clear();
    window.location.href = "index.html";
  }
});
