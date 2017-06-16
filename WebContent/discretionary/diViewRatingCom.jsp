<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>untitled</title>
  </head>
  <body>
  
  <h4>Reviewer Comments and Scores</h4>
  
  
  <c:choose >
  <c:when test="${appStatus.showscorecomm=='true'}" >
  
  <a href="ApcntFunctionsServlet?i=scores" target="_blank">Reviewer Comments and Scores - PDF</a><br/>
  <br/><br/>
  
  <table width="80%" align="center" border="1" summary="for layout only">
    <tr>
      <th colspan="2">Average Scores by Rating Category</th>
    </tr>    
    <tr>  
      <td>Institutional Commitment to Conservation/Preservation Work</td>
      <td><fmt:formatNumber value="${totScores.instcpStr}" /></td>
    </tr>
    <tr>
      <td>Accessibility of Collections to the Public</td>
      <td><fmt:formatNumber value="${totScores.availabilityStr}" /></td>
    </tr>
    <tr>
      <td>Research Value of Materials to be Preserved</td>
      <td><fmt:formatNumber value="${totScores.significanceStr}" /></td>
    </tr>
    <tr>
      <td>Plan of Work</td>
      <td><fmt:formatNumber value="${totScores.timetableStr}" /></td>
    </tr>
    <tr>
      <td>Education Pre-planning</td>
      <td><fmt:formatNumber value="${totScores.educationStr}" /></td>
    </tr>
    <tr>
      <td>Institutional Contribution to the Project</td>
      <td><fmt:formatNumber value="${totScores.financialsupportStr}" /></td>
    </tr>
    <tr>
      <td>Budget</td>
      <td><fmt:formatNumber value="${totScores.consistentexpStr}" /></td>
    </tr>
    <tr>
      <td>Overall Rating</td>
      <td><fmt:formatNumber value="${totScores.overallscoreStr}" /></td>
    </tr>
    <tr>
      <td>Average Rating of Proposal</td>
      <td><b><fmt:formatNumber value="${totScores.scoreStr}" /></b></td>
    </tr>
  </table>
  <br/><br/>
  
  
  <table width="80%" align="center" border="1" summary="for layout only">
    <tr>
      <th>Comments made by Reviewers during approval process</th>
    </tr>    
    <c:set value="1" var="countrow" />
    <c:forEach var="row" items="${allComments}">
    <tr>  
      <td><b>Reviewer <c:out value="${countrow}" /> Comment</b></td>
    </tr>
    <tr>
      <td><c:out value="${row.comment}" /></td>
    </tr>
    <c:set var="countrow" value="${countrow +1}" />
    </c:forEach>  
  </table>
  
  
  </c:when>
  <c:otherwise >
  
  <p align="center">
    Comments and Scores will be available after the C/P Program Officer has reviewed the application.
    <br/><br/>
    <font color="Red">Comments and Scores are unavailable at this time</font>
  </p>
  
  </c:otherwise>
  </c:choose>
  
  
  </body>
</html>
