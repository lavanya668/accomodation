$(document).ready(function () {
  $("#app-login-form").on("submit", function (event) {
    event.preventDefault();
    var formData = {
      firstName: $("#reg-first-name").val(),
      lastName: $("#reg-last-name").val(),
      email: $("#reg-email").val(),
      studentRegNum: $("#reg-stu-num").val(),
      password: $("#reg-password").val(),
      appUserType: "student",
    };
    console.log(formData);
    $.ajax({
      url: "http://localhost:8080/student_accommodation/appUserRegister",
      dataType: "json",
      type: "POST",
      encode: true,
      data: JSON.stringify(formData),
    })
      .done(function (data) {
        alert(data.returnMessage);
        window.location.href = "index.html";
      })
      .fail(function (xhr, data) {
        if (xhr.status == "400") {
          alert(data.responseJSON.returnMessage);
        } else if (xhr.status == "0") {
          alert("Backend not running");
        }
      });
  });
});
