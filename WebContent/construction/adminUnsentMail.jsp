<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Public Library Construction - Admin Email</title>
  </head>
  <body>
  
  <c:choose >
  <c:when test="${param.i=='delete'}">
      
    <form action="cnAdminEmailNav.do?item=deletewt" method="POST">
    <table width="100%" border="0" cellspacing="2" cellpadding="2">
      <tr>
        <th colspan="2">Are you sure you want to delete the unsent mail message?</th>
      </tr>
      <tr>
        <td>Subject:</td>
        <td><c:out value="${emailBean.subject}"/></td>
      </tr>
      <tr>
        <td>Message:</td>
        <td><c:out value="${emailBean.message}" /></td>
      </tr>
      <tr>
        <td colspan="2">
            <input type="HIDDEN" name="id" value='<c:out value="${emailBean.wtid}" />' />
            <input type="hidden" name="pr" value="cn"/>
            <input type="SUBMIT" value="Delete" />
        </td>
      </tr>
    </table>  
    </form>
  
  </c:when>
  <c:otherwise >
  
  
  <h4>Unsent email messages</h4>
      
  <table width="100%" summary="for layout only">
    <tr>
      <td><b>Date Created</b></td>
      <td><b>Subject</b></td>      
      <td><b>Update</b></td>
      <td><b>Delete</b></td>
      <td><b>Recipients</b></td>
      <td><b>Confirm & Send</b></td>
    </tr>
    
    <c:forEach var="row" items="${wtBeans}" >     
      <c:url var="wtupdate" value="cnAdminEmailNav.do">
        <c:param name="item" value="viewtemplate" />
        <c:param name="id" value="${row.id}" />
        <c:param name="pr" value="cn"/>
      </c:url>
      <c:url var="wtdelete" value="cnAdminEmailNav.do">
        <c:param name="item" value="confirmdelete" />
        <c:param name="id" value="${row.id}" />
        <c:param name="pr" value="cn"/>
      </c:url>  
      <c:url var="wtrecip" value="cnAdminEmailNav.do">
            <c:param name="item" value="recipientspage" />
            <c:param name="id" value="${row.id}" />
      </c:url>
      <c:url var="wtsend" value="cnAdminEmailNav.do">
        <c:param name="item" value="confirmsend" />
        <c:param name="id" value="${row.id}" />
        <c:param name="prog" value="cn"/>
      </c:url>
        <tr>
          <td><fmt:formatDate value="${row.datecreated}" /></td>
          <td><c:out value="${row.subject}" /></td>          
          <td><a href='<c:out value="${wtupdate}" />'>Update</a></td>
          <td><a href='<c:out value="${wtdelete}" />'>Delete</a></td>
          <td><a href='<c:out value="${wtrecip}" />'>Recipients</a></td>
          <td><a href='<c:out value="${wtsend}" />'>Send</a></td>
        </tr>
    </c:forEach>
  </table>
  
  
  </c:otherwise>
  </c:choose>
  
  </body>
</html>