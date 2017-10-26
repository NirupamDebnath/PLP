<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<style>
body{
background-image:url("/OnlineHotelBookings/views/images/a.jpg");
background-size:cover;
}

table{

  background-image:url("/OnlineHotelBookings/views/images/j.jpg");
  border:1px solid black;


}
.ui-datepicker {
    background: white;
    border: 1px solid gray;
    color: black;
}
</style>
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script>
	$(document).ready(function(){
    $("#txtFromDate").datepicker({
        onSelect: function(selected) {
          $("#txtToDate").datepicker("option","minDate", selected)
        }
    });
    $("#txtToDate").datepicker({ 
        onSelect: function(selected) {
           $("#txtFromDate").datepicker("option","maxDate", selected)
        }
    });  
});
</script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<center>
<form:form id="form" action="/OnlineHotelBookings/hotel/makeBooking.obj" modelAttribute="bookRoom" method="post">
			<br><br>
<h1>Booking Form</h1>
<br><br>
			
			<table border="1">
					<form:input path="roomId" value="${roomId}" hidden="hidden"/>
					<form:input path="userId" value="${userId}" hidden="hidden"/>
				<tr>
					<td>Check In Date</td>
					<td><form:input type="date" class="ui-datepicker" id="txtFromDate" path="bookedFrom" pattern="[0-9]{2}/[0-9]{2}/[0-9]{4}" placeholder="MM/dd/yyyy" title="use a date from the calender shown" required="required"/><form:errors path="bookedFrom"></form:errors></td>
				</tr>
				<tr>
					<td>Check Out Date</td>
					<td><form:input type="date" class="ui-datepicker" id="txtToDate" path="bookedTo" pattern="[0-9]{2}/[0-9]{2}/[0-9]{4}" placeholder="MM/dd/yyyy" title="use a date from the calender shown" required="required"/><form:errors path="bookedTo"></form:errors></td>
				</tr>
				<tr>
					<td>Number Of Adults</td>
					<td><form:input path="noOfAdults" pattern="[1-4]{1}" title="At least one adult should stay more than 4 adults is not allowed" required="required"/><form:errors path="noOfAdults"></form:errors></td>
				</tr>
				<tr>
					<td>Number Of Children</td>
					<td><form:input path="noOfChildren" pattern="[0-4]{1}" title="more than 4 children is not allowed" required="required"/><form:errors path="noOfChildren"></form:errors></td>
				</tr>
				</table>
			<input type="submit" value="Submit" />
</form:form>
</center>
</body>
</html>