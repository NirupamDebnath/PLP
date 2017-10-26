<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
table{

  background-image:url("/OnlineHotelBookings/views/images/j.jpg");
  border:1px solid black;


}
</style>
</head>
<body>
<center>
<br/>
<br/>
<br/>
<br/>
<c:if test="${empty bookingDetailsBean}">
	<h1>No details available</h1>
</c:if>
<c:if test="${not empty bookingDetailsBean}">
<table border="1">
<tr>
	<td>BookingId</td>
	<td>RoomId</td>
	<td>UserId</td>
	<td>Check In Date</td>
	<td>Check Out Date</td>
	<td>Number Of Adults</td>
	<td>Number Of Children</td>
	<td>Amount</td>
</tr>
<tr>
	<td>${bookingDetailsBean.bookingId}</td>
	<td>${bookingDetailsBean.roomId}</td>
	<td>${bookingDetailsBean.userId}</td>
	<td>${bookingDetailsBean.bookedFrom}</td>
	<td>${bookingDetailsBean.bookedTo}</td>
	<td>${bookingDetailsBean.noOfAdults}</td>
	<td>${bookingDetailsBean.noOfChildren}</td>
	<td>${bookingDetailsBean.amount}</td>
</tr>
</table>
</c:if>
</center>
</body>
</html>