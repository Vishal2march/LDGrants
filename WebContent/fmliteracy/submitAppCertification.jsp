<%--
 * @author  shusak
 * @version 1.0
 *
 * Development Environment        :  JDeveloper 11g
 * Name of the Application        :  submitAppCertification.jsp
 * Creation/Modification History  :    
 *     SHusak  2/3/16 Created
 *
 * Description
 * This is new "confirm submit" page for literacy starting FY16-19 per KB. Has different validations than 
 * previous years.  Also, on "confirm submit", must also insert "certification" into database (AUTHORIZATIONS table).
 * This jsp will likely be re-used for submitting final reports years 1/2/3 for 16-19.
 * Note: old submitApp.jsp is still being used for year 3 of FY13-16 apps.
--%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>submitAppCertification</title>
  </head>
  <body>
  
  <h4>Confirm Application Submission</h4> 
  
  
    <p style="margin:5.0pt;">
    <b>Submit Application</b><br/><br/>
    If you submit the application you will no longer be able to edit it. Submission certifies that the application 
    is complete and accurate and approved by the System Director.  
    </p>
          
    <c:if test="${missingNarr!=null}">
      <p style="margin:5.0pt;"><b><font color="Red">**</font>Warning:</b> The following narratives were not completed.</p>
      <ul>
      <c:forEach var="row" items="${missingNarr}">
        <li><c:out value="${row}" /></li>
      </c:forEach>
      </ul>
    </c:if>
  
  
  
    <c:choose>
    <c:when test="${missingNarr!=null}">
          <p><b><font color="red">**Warning </font>Your application cannot be submitted 
          until all required portions of the application are completed.</b>   </p> 
    </c:when>
    <c:otherwise>
        <form method="POST" action="liSubmitCertApp.do?i=submitliteracycert">    
            <p style="margin:5.0pt;">
            <br/><br/><br/>
            Confirm application submission<br/>
            <input type="SUBMIT" value="Submit"/>
            <input type="HIDDEN" name="t" value="initial" />  
            <input type="HIDDEN" name="p" value='<c:out value="${prog}" />' />
            <input type="BUTTON" value="Cancel" onclick="javascript:history.go(-1);" />  
            </p>
        </form>
    </c:otherwise>
    </c:choose>
  
  
  </body>
</html>