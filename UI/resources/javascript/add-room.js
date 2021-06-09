$(document).ready(function () {
  if (localStorage.getItem("appUserType") == "admin") {
    $("#add-room-form").submit(function (event) {
      event.preventDefault();
      var addRoomSubmit = {
        roomType: $("select[name=room-type] option").filter(":selected").val(),
        roomLocation: $("#room-location").val(),
        roomMonthlyCharge: $("#room-monthly-charge").val(),
        roomStatus: "available",
        paymentStatus: "",
        studentRegId: "",
      };
      $.ajax({
        url: "http://localhost:8080/student_accommodation/admin/room",
        type: "POST",
        data: JSON.stringify(addRoomSubmit),
        dataType: "json",
        encode: true,
      })
        .done(function (data) {
          alert(data.returnMessage);
          window.location.href = "admin-home.html";
        })
        .fail(function (xhr, data) {
          if (xhr.status == "400") {
            alert(data.responseJSON.returnMessage);
          } else if (xhr.status == "0") {
            alert("Backend not running");
          }
        });
    });
  } else {
    alert("This is restricted, please login as an admin to view this page");
    localStorage.clear();
    window.location.href = "index.html";
  }
});
