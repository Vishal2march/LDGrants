<%--
 * @author  Stefanie Husak
 * @version 2.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the Application        :  confirmCorrectionEmail.jsp
 * Creation/Modification History  :
 * shusak created 1/27/09
 *
 * Description
 * This page is used to confirm sending admin comment in email to PO or PM. Used for
 * sa/co/di/fl/al admin. 
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>untitled</title>
    <script type="text/javascript">
      function validate_form(thisform)
      {            
        if (thisform.from.value==null|| thisform.from.value=="") {
          alert("The 'from' email address field must be filled in!");
          thisform.from.focus();
          return false;
        }
        if (thisform.subject.value==null|| thisform.subject.value=="") {
          alert("The Email Subject field must be filled in!");
          thisform.subject.focus();
          return false;
        }
        return true;
      }
    </script>
  </head>
  <body>
  
  <h4>Confirm Sending of Email</h4>
  
    <form method="POST" action="adminCommentNav.do?item=sendmail" onsubmit="return validate_form(this)">
    <table width="90%" align="center" summary="for layout only" >
      <tr>
        <td><b>To:</b></td>
        <td>Project Manager</td>
      </tr>
      <tr>
        <td><b>'From' email address:</b><br/>ex. barbara.lilley@nysed.gov</td>
        <td><input type="TEXT" name="from" /></td>
      </tr>
      <tr>
        <td><b>'Cc' email address (optional):</b></td>
        <td><input type="TEXT" name="cc" /></td>
      </tr>      
      <tr>
        <td><b>Email Subject:</b></td>
        <td><input type="TEXT" name="subject" /></td>
      </tr>
      
      <tr>
        <td><b>Email Body:</b></td>
        <td><c:out value="${commentBean.comment}" /></td>
      </tr>
      
      <tr>
        <td height="20"/>
      </tr>
      <tr>
        <th colspan="2">
        Do you want to send this comment email?<br/>
        <input type="hidden" name="id" value='<c:out value="${commentBean.id}" />' />
        <input type="hidden" name="graid" value='<c:out value="${commentBean.grantid}" />' />
        <input name="btn" type="SUBMIT" value="Send" />
        <input type="HIDDEN" name="p" value='<c:out value="${commentBean.module}" />' /> 
        </th>
      </tr>
        
    </table>
    </form>
  
  </body>
</html>
