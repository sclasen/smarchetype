<%@page import="com.vmforce.samples.entity.SampleDog"%>
<%@ page language="java" import="com.vmforce.samples.entity.*, java.util.*"%>
<HTML>
<HEAD>
<TITLE>Sample dog List</TITLE>
</HEAD>
<BODY>
	<H1>List of dogOwners</H1>
	
	<%
		List<SampleDogOwner> dogOwners = (List<SampleDogOwner>)request.getAttribute("dogOwners");
		
		for (SampleDogOwner dogOwner : dogOwners) {
			
			out.println("<h3>" + dogOwner.getFirstName() + " " + dogOwner.getLastName() + "</h3>");
			out.println("<ul>");
			out.println("<li>" + dogOwner.getPhoneNumber() + "</li>");
			
			out.println("<ul>");
			Set<SampleDog> dogs = dogOwner.getDogs();
			for (SampleDog dog : dogs) {
				out.println("<li><b><u>" + dog.getName() + "</u></b></li>");
				out.println("<li>Age : " + dog.getAge() + "</li>");
				out.println("<li>Breed : " + dog.getBreed() + "</li>");
			}
			out.println("</ul>");
			
			out.println("</ul>");
		}
	%>
	
	<p/>
	<a href="../logout">Logout</a>
</BODY>
</HTML>