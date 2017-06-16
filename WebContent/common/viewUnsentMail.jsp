<%--
 * @author  Stefanie Husak
 * @version 2.0
 *
 * Development Environment:  JDeveloper 10g
 * Name of the Application:  viewUnsentMail.jsp
 * SHusak 5/26/09 Created
 *
 * Description
 * This page shows all unsent emails from SEDEMS.  Used for fl/al admin. Options
 * to update/send/delete the email, add recipients indivdually or group.
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
      
    <form action="litEmailNav.do">
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
      <th>Recipient Group</th>
      <th>Individual Recipient</th>
      <th>Send</th>
      <th>Delete</th>
    </tr>
    
    <c:forEach var="row" items="${wtBeans}" >     
      <c:url var="wtupdate" value="litEmailNav.do">
        <c:param name="item" value="viewtemplate" />
        <c:param name="id" value="${row.id}" />
      </c:url>
      <c:url var="wtdelete" value="litEmailNav.do">
        <c:param name="item" value="confirmdelete" />
        <c:param name="id" value="${row.id}" />
      </c:url>
      <c:url var="wtrecip" value="litAdminEmail.do">
        <c:param name="i" value="recipientspage" />
        <c:param name="id" value="${row.id}" />
      </c:url>
      <c:url var="wtindiv" value="litAdminEmail.do">
        <c:param name="i" value="indivrecipient" />
        <c:param name="id" value="${row.id}" />
      </c:url>
      <c:url var="wtsend" value="litEmailNav.do">
        <c:param name="item" value="confirmsend" />
        <c:param name="id" value="${row.id}" />
      </c:url>
        <tr>
          <td><fmt:formatDate value="${row.datecreated}" /></td>
          <td><c:out value="${row.subject}" /></td>          
          <td><a href='<c:out value="${wtupdate}" />'>Update</a></td>
          <td><a href='<c:out value="${wtrecip}" />'>Recipient Group</a></td>
          <td><a href='<c:out value="${wtindiv}" />'>Individual Recipient</a></td>
          <td><a href='<c:out value="${wtsend}" />'>Send</a></td>
          <td><a href='<c:out value="${wtdelete}" />'>Delete</a></td>
        </tr>
    </c:forEach>
  </table>
  
  </c:otherwise>
  </c:choose>    
  
  </body>
</html>
