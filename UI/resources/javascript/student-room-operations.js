$(document).ready(function () {
  if (localStorage.getItem("appUserType") == "student") {
    displayFirstName(localStorage.getItem("fName"));

    $.ajax({
      url: "http://localhost:8080/student_accommodation/student/room",
      method: "GET",
      success: function (data) {
        console.log("Success", data);
        $("#adminRooms").DataTable({
          data: data,
          columns: [
            { data: "roomId" },
            { data: "roomType" },
            { data: "roomLocation" },
            { data: "roomMonthlyCharge" },
            { data: "roomStatus" },
            {
              sortable: false,
              render: function (data, type, full, meta) {
                return (
                  "<button onClick=allocateRoom(" +
                  full.roomId +
                  ') class="btn-warning btn">Pick Room</button>'
                );
              },
            },
          ],
        });
      },
      error: function (data) {
        console.log(data);
        alert("Error");
        localStorage.clear();
        window.location.href = "index.html";
      },
    });
  } else {
    alert("This is restricted, please login as an admin to view this page");
    localStorage.clear();
    window.location.href = "index.html";
  }
});

function displayFirstName(firstName) {
  document.getElementById("welcomeString").textContent = "Welcome " + firstName;
}

function allocateRoom(roomId) {
  let confirmDelete = confirm(
    "Are you sure you want to allocate the selected Room"
  );
  if (confirmDelete) {
    var allocateRoom = {
      roomId: roomId,
      studentRegNum: localStorage.getItem("stuRegNum"),
    };
    $.ajax({
      url: "http://localhost:8080/student_accommodation/student/room",
      method: "POST",
      data: JSON.stringify(allocateRoom),
      dataType: "json",
      encode: true,
    })
      .done(function (data) {
        alert(data.returnMessage);
        window.location.href = "student-room.html";
      })
      .fail(function (data) {
        alert(data.responseJSON.returnMessage);
        window.location.href = "student-home.html";
      });
  }
}
