<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
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
}</style> 
</head>
<body>
<center>
<br><br><br><br>
<h1>Room List</h1>
<br>
<table border="1">
<c:if test="${not empty msg}">
	<center><h1>${msg}</h1></center>
</c:if>
<c:if test="${empty msg}">
	<td>Room id</td>
	<td>Room No</td>
	<td>Room Type</td>
	<td>Per Night Rate</td>
	<td>Book Room</td>
</c:if>
<c:forEach var="room" items="${roomList}">
<tr>
	<td>${room.roomId}</td>
	<td>${room.roomNo}</td>
	<td>${room.roomType}</td>
	<td>${room.perNightRate}</td>
	<td><a href="/OnlineHotelBookings/hotel/bookRoom.obj?roomId=${room.roomId}">Book Room</a></td>
</tr>
</c:forEach>
</table>
</center>
</body>
</html>