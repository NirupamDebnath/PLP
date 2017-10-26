<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
   pageEncoding="ISO-8859-1"%>
   <%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
   <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Online Hotel Bookings Management system</title>
<style>
body{
background-image:url("/OnlineHotelBookings/views/images/a.jpg");
background-size:cover;
}
#form2{
  	border-style: solid;
    border-width: 10px;
	max-height:350px;
 	border: 2px solid red;
    border-radius: 150px;
 	
	margin:50px auto;
	max-width:500px;

	background-image:url("views/images/t.jpg");
	background-size:cover;
}

	h1 {
    text-shadow: 12px 12px 8px white;
}

</style>
</head>
<body>
<center><h1>Online Hotel Bookings Management system</h1></center>
<c:if test="${not empty msg}">
	<center><h1>${msg}</h1></center>
</c:if>
<form action="/OnlineHotelBookings/hotel/logIn.obj" method="post">
<fieldset>
    <legend align="right">Login</legend>
		User Id:<input  type="text" name="userId"/>
		Password:<input type="password" name="password"/>
		<input type="submit" value="Submit" />
</fieldset>
</form>
<!-- 
<marquee behavior="alternate"> -->
<center>
<img src="/OnlineHotelBookings/views/images/s.jpg" width="185" height="152" alt="Flying Bat">
<img src="/OnlineHotelBookings/views/images/b.jpg" width="185" height="152" alt="Flying Bat">
<img src="/OnlineHotelBookings/views/images/c.jpg" width="185" height="152" alt="Flying Bat">
<img src="/OnlineHotelBookings/views/images/2.jpg" width="185" height="152" alt="Flying Bat">
<img src="/OnlineHotelBookings/views/images/e.jpg" width="185" height="152" alt="Flying Bat">
</center><!-- </marquee> -->
<br>
<center><h1>Register If you are a new user !!</h1></center>
<center>
<form:form id="form2" action="/OnlineHotelBookings/hotel/storeUser.obj" modelAttribute="user" method="post">
			<table style="with: 50%">
				<tr>
					<td>User Name</td>
					<td><form:input path="userName" pattern="[A-Za-z\\s]{3,12}" title="Can not contain number and should be within 3 to 12 character" required="required"/><form:errors path="userName"></form:errors></td>
				</tr>
				<tr>
					<td>Password</td>
					<td><form:input path="password" type="password" pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{4,7}" title="Must contain at least one number and one uppercase and lowercase letter, and at least 4 to 7 characters" required="required"/><form:errors path="password"></form:errors></td>
				</tr>
				<form:hidden path="role" value="customer"/><form:errors path="role"></form:errors>
				<tr>
					<td>Mobile</td>
					<td><form:input path="mobileNo" pattern="[789]{1}[0-9]{9}" title="should start with 7/8/9 and should be of 10 digits" required="required"/><form:errors path="mobileNo"></form:errors></td>
				</tr>
				<tr>
					<td>Phone</td>
					<td><form:input path="phone" pattern="[0]{1}[0-9]{9}" title="should start with 0 and should be of 10 digits"/><form:errors path="phone"></form:errors></td>
				</tr>
				<tr>
					<td>Address</td>
					<td><form:input path="address" required="required" /><form:errors path="address"></form:errors></td>
				</tr>
				<tr>
					<td>Email</td>
					<td><form:input path="email" type="email" required="required"/><form:errors path="email"></form:errors></td>
				</tr>
					
				</table>
			<input type="submit" value="Submit" />
			 </form:form></center>
</body>
</html>