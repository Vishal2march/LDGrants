<%--
 * @author  Stefanie Husak
 * @version 2.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the Application        :  confirmSubmit.jsp
 * Creation/Modification History  :
 *
 *     SHusak       7/10/07     Created
 *
 * Description
 * This page allows the applicant to confirm that they want to submit the 
 * initial or final application.  
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>untitled</title>
  </head>
  <body>
  
  <h4>Confirm Application Submission</h4>
      
  
  <c:choose >
  <c:when test="${lduser.prgsa=='submit'}">
  
    <c:if test="${param.todo=='initial'}">    
      <form method="POST" action="saSubmitApp.do?i=submitinitial">      
      <p>
          <b>Submit Application</b><br/>        
          If you submit the application you will no longer be able to edit it.<br/>If your 
          application is complete and accurate, click the Submit button.
         <br/><br/>
         
         Do you want to submit the application?<br/>
         <input type="SUBMIT" value="Submit"/>
         <input type="HIDDEN" name="prog" value="6" />
         <input type="BUTTON" value="Cancel" onclick="javascript:history.go(-1);" />
      </p> 
      </form>
    </c:if>
    
    
    <c:if test="${param.todo=='final'}">
      <form method="POST" action="saSubmitApp.do?i=submitfinal">
      <p>
          <b>Submit Final Report</b><br/>
          If you submit the final report you will 
          no longer be able to edit it. If your application is
          complete and accurate, click the Submit button.<br/> <br/>
          Do you want to submit the final report?<br/>
          <input type="SUBMIT" value="Submit"/>
          <input type="HIDDEN" name="prog" value="6" />
          <input type="BUTTON" value="Cancel" onclick="javascript:history.go(-1);" />
      </p>
      </form>
    </c:if>
    
    
    <c:if test="${param.todo=='amend'}">
      <form method="POST" action="saSubmitApp.do?i=submitamend">
      <p>
          <b>Submit Budget Amendment Records</b><br/>
          If you submit the budget amendment records you will 
          no longer be able to edit them. <br/> <br/>
          Do you want to submit the FS-10-A Budget Amendment Records?<br/>
          Remember to mail 3 copies of the FS-10-A Form (signed in blue ink) to the Division of Library Development.
          <input type="SUBMIT" value="Submit"/>
          <input type="HIDDEN" name="prog" value="6" />
          <input type="BUTTON" value="Cancel" onclick="javascript:history.go(-1);" />
      </p>
      </form>
    </c:if>
  
  </c:when>
  <c:otherwise >
  
    <p>
      Note:<br/><br/>
      You do not have access to Submit the application.<br/>Only users
      with sufficient account priviledges may Submit the
      application. If you believe you should be able to Submit the application,
      contact Library Development staff to update your account.
    </p> 
    
  </c:otherwise>
  </c:choose>  
  </body>
</html>
