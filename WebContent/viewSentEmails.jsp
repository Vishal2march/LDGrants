<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>untitled</title>
  </head>
  <body>
  
  <h4>Emails sent regarding this grant proposal</h4>
  
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
        <td><bean:write name="el" property="message" filter="false" /></td>
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
