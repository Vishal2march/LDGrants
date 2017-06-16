<%--
 * @author  Stefanie Husak
 * @version 2.0
 *
 * Development Environment:  JDeveloper 10g
 * Name of the Application:  cpunsentmail.jsp
 * SHusak 5/26/09 Created
 *
 * Description
 * This page shows all unsent emails from SEDEMS.  Used for sa/co/di admin. Options
 * to update/send/delete the email.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>untitled</title>
  </head>
  <body>
  
  
  <c:choose >
  <c:when test="${param.i=='delete'}">
      
    <form action="cpAdminEmail.do">
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
            <input type="HIDDEN" name="item" value="deletewt" />
            <input type="SUBMIT" value="Delete" />
        </td>
      </tr>
    </table>  
    </form>
  
  </c:when>
  <c:otherwise >
  
  <h4>All Unsent email messages</h4>
      
  <table width="100%" class="borderbox" summary="for layout only">
    <tr>
      <th>Date Created</th>
      <th>Subject</th>      
      <th>Update</th>
      <th>Delete</th>
      <th>Recipients</th>
      <th>Confirm&Send</th>
    </tr>
    
    <c:forEach var="row" items="${wtBeans}" >     
      <c:url var="wtupdate" value="cpAdminEmail.do">
        <c:param name="item" value="viewtemplate" />
        <c:param name="id" value="${row.id}" />
      </c:url>
      <c:url var="wtdelete" value="cpAdminEmail.do">
        <c:param name="item" value="confirmdelete" />
        <c:param name="id" value="${row.id}" />
      </c:url>  
      <c:url var="recurl" value="adminMassEmail.do">
        <c:param name="item" value="loadrecips" />
        <c:param name="id" value="${row.id}" />
      </c:url> 
      <c:url var="sendurl" value="cpAdminEmail.do">
        <c:param name="item" value="confirmsend" />
        <c:param name="id" value="${row.id}" />
      </c:url> 
        <tr>
          <td><fmt:formatDate value="${row.datecreated}" /></td>
          <td><c:out value="${row.subject}" /></td>          
          <td><a href='<c:out value="${wtupdate}" />'>Update</a></td>
          <td><a href='<c:out value="${wtdelete}" />'>Delete</a></td>
          <td><a href='<c:out value="${recurl}" />'>Recipients</a></td>
          <td><a href='<c:out value="${sendurl}" />'>Send</a></td>
        </tr>
    </c:forEach>
  </table>
  
  </c:otherwise>
  </c:choose>
  
  </body>
</html>
