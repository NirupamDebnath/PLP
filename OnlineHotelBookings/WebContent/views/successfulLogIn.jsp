<%@page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
 <style>
body{
background-image:url("/OnlineHotelBookings/views/images/a.jpg");
background-size:cover;
}

	h1 {
    text-shadow: 12px 12px 8px white;
}
table{

  background-image:url("/OnlineHotelBookings/views/images/j.jpg");
  border:1px solid black;


}</style> 
</head>
<body>
<br><br>
<c:if test="${empty userId}">
	<center><h1>Unauthorised Access</h1></center>
</c:if>
<c:if test="${not empty userId}">
<form action="/OnlineHotelBookings/hotel/viewBookingDetails.obj" method="post">

<fieldset>
    <legend align="right">Find Booking Details</legend>
		Booking Id<input  type="text" pattern="[0-9]{4}" required="required" name="bookingId"/>
		<input type="submit" value="Submit" />
</fieldset>
<br><br>
</form>
<center>
<br><br>
<h1>Hotel List</h1>
<br><br>
<table border="1">
<tr>
	<td>Hotel Id</td>
	<td>City</td>
	<td>Hotel Name</td>
	<td>Address</td>
	<td>Description</td>
	<td>Average Rate Per Night</td>
	<td>Phone Number 1</td>
	<td>Phone Number 2</td>
	<td>Rating</td>
	<td>Email</td>
	<td>Fax</td>
	<td>View Room</td>
</tr>
<c:forEach var="hotel" items="${hotelList}">
<tr>
	<td>${hotel.hotelId}</td>
	<td>${hotel.city}</td>
	<td>${hotel.hotelName}</td>
	<td>${hotel.address}</td>
	<td>${hotel.description}</td>
	<td>${hotel.avgRatePerNight}</td>
	<td>${hotel.phoneNo1}</td>
	<td>${hotel.phoneNo2}</td>
	<td>${hotel.rating}</td>
	<td>${hotel.email}</td>
	<td>${hotel.fax}</td>
	<td><a href="/OnlineHotelBookings/hotel/viewRooms.obj?hotelId=${hotel.hotelId}">View Room</a></td>
</tr>
</c:forEach>
</table>
</center>
</c:if>
</body>
</html>