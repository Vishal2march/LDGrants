<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>untitled</title>
  </head>
  <body>
  
  <h4>Admin Comments Regarding Application</h4>
  

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
  <c:when test="${param.todo!='view' }">
  
    <c:if test="${param.todo=='delete'}">
      <h5>Confirm Comment Deletion</h5>
      <form method="POST" action="adminCommentNav.do?item=deletec" >
      
        Do you want to delete this comment? <br/>
        <input type="hidden" name="comid" value='<c:out value="${param.com_id}" />' />              
        <input name="btn" type="SUBMIT" value="Delete" />
        <input name="p" type="HIDDEN" value="al"/>
        <input type="BUTTON" value="Cancel" onclick="javascript:history.go(-1);" />
      </form>
   </c:if>
   
   <c:if test="${param.todo=='add'}" >
      <h5>Create a new Comment</h5>
   
      <form method="POST" action="adminCommentNav.do?item=add">
        <p>Enter a new comment.</p>
        <textarea cols="60" rows="10" name="appCorrection"></textarea></td>
        <br/><br/>
        <input type="HIDDEN" name="p" value="al" />
        <input type="SUBMIT" name="btn" value="Save Comment" />           
      </form>
   </c:if>
   
  
  </c:when>
  <c:otherwise >
    
  <p>
    <a href="AlAdminComments.do?todo=add">Add a new Comment</a>
  </p>
  <form action="adminCommentNav.do?item=updatec" method="POST">
  <table width="100%" class="boxtype" summary="for layout only">    
    <tr>
      <th>Action</th>
      <th>Send Email</th>
      <th>Date of Comment</th>
      <th>Admin ID</th>
      <th>Comment</th>
    </tr>
    
    <c:set var="countRows" value="0" />
    <c:forEach var="commBean" items="${allComments}" >
      <c:url value="AlAdminComments.do" var="deleteURL">
        <c:param name="todo" value="delete" />
        <c:param name="com_id" value="${commBean.id}" />
      </c:url>      
     <c:url value="adminEmailNav.do" var="sendURL">
        <c:param name="item" value="comment" />
        <c:param name="id" value="${commBean.id}" />
        <c:param name="m" value="al" />
      </c:url>      
      
    <tr>      
      <td><a href='<c:out value="${deleteURL}"/>'>Delete</a></td>
      <td><a href='<c:out value="${sendURL}"/>'>Send Email</a></td>
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
      <td colspan="5" align="center"><input name="p" type="HIDDEN" value="al"/>
        <input type="HIDDEN" name="rows" value='<c:out value="${countRows}" />' />
        <input type="SUBMIT" name="btn" value="Save" /></td>
    </tr>
  </table>
  </form>
 
    
  </c:otherwise>
  </c:choose> 
  
  
  <p align="center">
    <c:url var="backURL" value="alAdminNav.do">
      <c:param name="id" value="${grantid}" />
      <c:param name="item" value="grant" />
    </c:url>   
    <a href='<c:out value="${backURL}" />' >Back to Application Checklist</a>
  </p>  
  
  </body>
</html>
