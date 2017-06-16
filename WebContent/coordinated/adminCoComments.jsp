<%--
 * @author  Stefanie Husak
 * @version 2.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the Application        :  adminCoComments.jsp
 * Creation/Modification History  :
 *     
 *     SHusak       7/23/07     Modified
 *
 * Description
 * This page allows the admin to view/add/delete/update/send previous comments and
 * corrections for the given CO grant.
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
  
  <h4>Admin Comments During Application Review/Approval</h4>
  

  <table width="80%" align="center" summary="for layout only">
    <tr>             
      <td><b>Project Num</b></td>
      <td><b>Institution</b></td>
      <td><b>Title</b></td>          
    </tr>        
    <tr>
      <td>03<fmt:formatNumber minIntegerDigits="2" pattern="##" type="number" value="${thisGrant.fccode}" />
          -<fmt:formatNumber value="${thisGrant.fycode}" minIntegerDigits="2" />
          -<fmt:formatNumber value="${thisGrant.projseqnum}" minIntegerDigits="4" pattern="####" />
      </td>
      <td ><c:out value="${thisGrant.instName}" /></td>
      <td><c:out value="${thisGrant.title}" /></td>
    </tr>       
  </table>
   
  
  
  <c:choose >
  <c:when test="${param.todo !='view'}">
  
    <c:if test="${param.todo=='delete'}">
      <h5>Confirm Comment Deletion</h5>
      <form method="POST" action="adminCommentNav.do?item=deletec" >
      
        Do you want to delete this comment? <br/>
        <input type="hidden" name="comid" value='<c:out value="${param.com_id}" />' />              
        <input name="btn" type="SUBMIT" value="Delete" />
        <input name="p" type="HIDDEN" value="co"/>
        <input type="BUTTON" value="Cancel" onclick="javascript:history.go(-1);" />
    </form>
   </c:if>
      
   <c:if test="${param.todo=='add'}">
      <h5>Create a new Comment</h5>
   
      <form method="POST" action="adminCommentNav.do?item=add">        
          <p>Enter a new comment or correction request. </p>
          <textarea cols="60" rows="10" name="appCorrection"></textarea>
          <br/><br/>
          <input type="HIDDEN" name="p" value="co" />
          <input type="SUBMIT" name="btn" value="Save Comment" />          
      </form>   
   </c:if>
    
  </c:when>
  <c:otherwise >
    
  
  <form action="adminCommentNav.do?item=updatec" method="POST">
  <table align="center" width="90%" border="1" summary="for layout only">    
    <tr>
      <th>Action</th>
      <th>Send Email</th>
      <th>Date of Comment</th>
      <th>Admin ID</th>
      <th>Comment</th>
    </tr>
    
    <c:set var="countRows" value="0" />
    <c:forEach var="commBean" items="${allComments}" >
      <c:url value="AdminCoComments.do" var="deleteURL">
        <c:param name="todo" value="delete" />
        <c:param name="com_id" value="${commBean.id}" />
      </c:url>      
      <c:url value="adminEmailNav.do" var="sendURL">
        <c:param name="item" value="comment" />
        <c:param name="id" value="${commBean.id}" />
        <c:param name="m" value="co" />
      </c:url>         
      
    <tr>      
      <td><a href='<c:out value="${deleteURL}"/>'>Delete</a></td>
      <td><a href='<c:out value="${sendURL}"/>'>Send Correction Email</a></td>
      <input type="HIDDEN" value='<c:out value="${commBean.id}"/>' name='acId_<c:out value="${countRows}"/>' />
      <td><fmt:formatDate value="${commBean.commentdate}" pattern="MM/dd/yyyy" /></td>     
      <td><c:out value="${commBean.createdby}" /></td>
      <td><textarea name='acComment_<c:out value="${countRows}"/>' rows="10" cols="30"><c:out value="${commBean.comment}" /></textarea></td>
    </tr>  
    <tr>
      <td height="20" colspan="5" />
    </tr>
    <c:set var="countRows" value="${countRows +1}" />
    </c:forEach>
    
    <tr>
      <td colspan="5" align="center"><input type="SUBMIT" name="btn" value="Save" />
            <input type="HIDDEN" name="rows" value='<c:out value="${countRows}" />' />
            <input name="p" type="HIDDEN" value="co"/></td>
    </tr>
  </table>
  </form>
  
  <p align="center">
    <a href="adminEmailNav.do?item=emails&m=co">View Sent Emails regarding this Grant</a>
    <br/><br/>
    <a href="AdminCoComments.do?todo=add">Add a new Comment</a>
  </p>
  
  </c:otherwise>
  </c:choose>
  
  
  <p align="center">
    <c:url var="backURL" value="coAdminNav.do">
      <c:param name="id" value="${grantid}" />
      <c:param name="item" value="grant" />
    </c:url>
    <a href='<c:out value="${backURL}" />' >Back to Application Checklist</a>
  </p>  
  
  
  
  </body>
</html>
