<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>untitled</title>
  </head>
  <body>
    
  <h3>Search for Emails sent to Reviewer</h3>
    
  <table width="100%" summary="for layout only">
    <form method="POST" action="adminEmailNav.do?item=revsearch">
    <tr>
      <td>Choose a Reviewer:</td>
    </tr>
    <tr>
      <td><select name="revid">
            <c:forEach var="rev" items="${allReviewers}">
              <option value='<c:out value="${rev.revid}" />'><c:out value="${rev.fname}" /> <c:out value="${rev.lname}" /></option>
            </c:forEach>
          </select></td>
    </tr>  
    <tr>
      <td><input type="SUBMIT" value="Search"/>
          <input type="HIDDEN" name="m" value='<c:out value="${param.m}" />' /></td>
    </tr>
    </form>
  </table>
  
    
  <table width="100%" class="boxtype" summary="for layout only">
       
    <c:forEach var="el" items="${searchResults}">
      <tr>
        <td width="15%"><b>Date Sent:</b></td>
        <td><fmt:formatDate value="${el.datesent}"  /></td>
      </tr>
      <tr>
        <td><b>Subject:</b></td>
        <td><c:out value="${el.subject}" /></td>
      </tr>
      <tr>
        <td><b>Message:</b></td>
        <td><%--<pre class="emailMessage"><c:out value="${el.message}"  />--%></pre>
            <c:out value="${el.message}"  /></td>
      </tr>      
      <tr>
        <c:set var="req" value="${el.recipients}" />   
        <td><b>To:</b></td>
        <td>
          <c:forEach var="r" items="${req}" ><%--print each recipient info --%>
            <c:out value="${r.emailAddress}" /><br/>
          </c:forEach>
        </td>
      </tr>            
      <tr>
        <td height="20" colspan="2"><hr/></td>
      </tr>
    </c:forEach>
  </table>
  
  
  </body>
</html>
