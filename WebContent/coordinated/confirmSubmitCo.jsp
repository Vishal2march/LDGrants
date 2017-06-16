<%--
 * @author  Stefanie Husak
 * @version 2.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the Application        :  confirmSubmitCo.jsp
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
  <c:when test="${lduser.prgco=='submit'}">
  
    <c:if test="${param.todo=='initial'}">    
      <form method="POST" action="coSubmitApp.do?i=submitinitial">
      <table width="70%" summary="for layout only">
        <tr>
          <th>Submit Application</th>
        </tr>
        <tr>
          <td>
          If you submit the application you will no longer be able to edit it. If your 
          application is complete and accurate, click the Submit button.  Remember to mail 3 
          original FS-10 forms to the Division of Library Development.
          </td>
        </tr>
        <c:if test="${missingNarr!=null}">
        <tr>
          <td>
            <p class="error">Warning: The following narratives were not completed. If the narrative
            does not apply to your project - please put 'Not applicable' in the corresponding narrative
            box on the Project Narrative page.</p>
            <ul>
            <c:forEach var="row" items="${missingNarr}">
              <li><c:out value="${row}" /></li>
            </c:forEach>
            </ul>
          </td>
        </tr>
        </c:if>
        
        <c:if test="${missingBudg!=null}">
        <tr>
         <td><p class="error">Warning: The total amount requested for this 
         grant application is $0.  The amount requested should be entered on the Project Budget pages,
         under the appropriate budget category.</p></td>
        </tr>
        </c:if>     
        <tr>
          <th height="15" />
        </tr>
        <tr>
          <td>Do you want to submit the application?</td>
        </tr>
        <tr>
          <td><input type="SUBMIT" value="Submit"/>
              <input type="HIDDEN" name="prog" value="7" />
              <input type="BUTTON" value="Cancel" onclick="javascript:history.go(-1);" />
          </td>
        </tr>
      </table>        
      </form>
    </c:if>
    
    
    <c:if test="${param.todo=='final'}">
      <form method="POST" action="coSubmitApp.do?i=submitfinal">
      <table width="70%" summary="for layout only">
        <tr>
          <th>Submit Final Report</th>
        </tr>
        <tr>
          <td>If you submit the final report you will 
          no longer be able to edit it. If your application is
          complete and accurate, click the Submit button.  Remember to mail 3 original
          FS-10-F forms to the Division of Library Development.</td>
        </tr>
        <tr>
          <th height="15" />
        </tr>
        <tr>
          <td>Do you want to submit the final report?</td>
        </tr>
        <tr>
          <td>
            <input type="SUBMIT" value="Submit"/>
            <input type="HIDDEN" name="prog" value="7" />
            <input type="BUTTON" value="Cancel" onclick="javascript:history.go(-1);" />
          </td>
        </tr>
      </table>        
      </form>
    </c:if>
    
    
    <c:if test="${param.todo=='amend'}">
      <form method="POST" action="coSubmitApp.do?i=submitamend">
      <p>
          <b>Submit Budget Amendment Records</b><br/>
          If you submit the Budget Amendment Records you will 
          no longer be able to edit them. <br/> <br/>
          Do you want to submit the FS-10-A Budget Amendment Records?<br/>
          Remember to mail 3 original FS-10-A Forms (all signed in blue ink) to the Division of Library Development.<br/>
          <input type="SUBMIT" value="Submit"/>
          <input type="HIDDEN" name="prog" value="7" />
          <input type="BUTTON" value="Cancel" onclick="javascript:history.go(-1);" />
      </p>
      </form>
    </c:if>
  
  </c:when>
  <c:otherwise >
  
    <table width="70%" align="center" summary="for layout only">
      <tr>
        <td><b>Note:</b><br/><br/>
          You do not have access to Submit the application.  Only users
          with sufficient account priviledges may Submit the
          application. If you believe you should be able to Submit the application,
          contact Division of Library Development staff to update your account.<td>
      </tr>
    </table>
    
  </c:otherwise>
  </c:choose>  
  </body>
</html>
