$(document).ready(function () {
  if (localStorage.getItem("appUserType") == "admin") {
    window.location.href = "admin-home.html";
  } else if (localStorage.getItem("appUserType") == "student") {
    window.location.href = "student-home.html";
  } else {
    $("#app-login-form").on("submit", function (event) {
      event.preventDefault();
      var formData = {
        email: $("#login-email").val(),
        password: $("#login-password").val(),
      };
      $.ajax({
        url: "http://localhost:8080/student_accommodation/appLogin",
        dataType: "json",
        type: "POST",
        encode: true,
        data: JSON.stringify(formData),
      })
        .done(function (data) {
          setHomePageByUserType(data);
        })
        .fail(function (xhr) {
          if (xhr.status == "400") {
            alert("Bad Credentials");
          } else if (xhr.status == "0") {
            alert("Backend not running");
          }
        });
    });
  }
});

function setHomePageByUserType(data) {
  if (data.appUserType == "admin") {
    localStorage.setItem("appUserId", data.appUserId);
    localStorage.setItem("appUserType", data.appUserType);
    localStorage.setItem("fName", data.firstName);
    localStorage.setItem("lName", data.lastName);
    window.location.href = "admin-home.html";
  } else if (data.appUserType == "student") {
    localStorage.setItem("appUserId", data.appUserId);
    localStorage.setItem("appUserType", data.appUserType);
    localStorage.setItem("fName", data.firstName);
    localStorage.setItem("lName", data.lastName);
    localStorage.setItem("stuRegNum", data.studentRegNum);
    window.location.href = "student-room.html";
  }
}
