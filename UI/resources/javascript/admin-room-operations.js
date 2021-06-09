$(document).ready(function () {
  if (localStorage.getItem("appUserType") == "admin") {
    displayFirstName(localStorage.getItem("fName"));

    $.ajax({
      url: "http://localhost:8080/student_accommodation/admin/room",
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
            { data: "paymentStatus" },
            { data: "studentRegId" },
            {
              sortable: false,
              render: function (data, type, full, meta) {
                return (
                  "<button onClick=editRoom(" +
                  full.roomId +
                  ') class="btn-warning btn">Edit</button>'
                );
              },
            },
            {
              sortable: false,
              render: function (data, type, full, meta) {
                return (
                  "<button onClick=deleteRoom(" +
                  full.roomId +
                  ') class="btn-danger btn">Delete</button>'
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

function editRoom(roomId) {
  window.location.href = "update-room.html?id=" + roomId;
}

function deleteRoom(roomId) {
  let confirmDelete = confirm(
    "Are you sure you want to delete the selected Room"
  );
  if (confirmDelete) {
    var deleteSubmit = {
      roomId: roomId,
    };
    $.ajax({
      url: "http://localhost:8080/student_accommodation/admin/room",
      method: "DELETE",
      data: JSON.stringify(deleteSubmit),
      dataType: "json",
      encode: true,
    })
      .done(function (data) {
        alert(data.returnMessage);
        window.location.href = "admin-home.html";
      })
      .fail(function (data) {
        alert(data.responseJSON.returnMessage);
      });
  }
}
