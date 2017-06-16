<%--
 * @author  Stefanie Husak
 * @version 2.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the Application        :  saFinalRptSignoff.jsp
 * Creation/Modification History  :
 *
 *     SH       3/1/07     Created
 *
 * Description
 * This is the final report signoff for application.  The library director will authorize
 * submission of the app by checking off the box on the page - the save will create a new
 * entry in the Authorization table. 
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>State Aid Final Report Sign-off</title>
  </head>
  <body>
  
  <jsp:useBean id="now" class="java.util.Date" />
  <fmt:formatDate var="todayDate" value="${now}" pattern="MM/dd/yyyy" />
  
  <h4>Final Report Sign-off</h4>
        
  <table width="80%" class="boxtype" align="center" summary="for layout only" >
    <tr>
      <td align="center" >I hereby certify that all expenditures reported in the attached budget 
      report are directly attributable to this project, and that the attached 
      narrative is an accurate and complete account of the project.
      </td>
    </tr>
  </table><br/>
  
  
  <c:forEach var="authBean" items="${grantAuth}" >
    <c:if test="${authBean.title == 'Library Director' && authBean.version=='FinalSignoff'}" >
      <c:set var="ldAuth" value="true" />
    </c:if>
  </c:forEach>
  
  
  <form method="POST" action="AuthorizationsServlet" >
  <table width="80%" align="center" border="1" summary="for layout only">
    <tr >
      <td>Date: <c:out value="${todayDate}" /></td>
      <td>
        <c:choose >
        <c:when test="${ldAuth=='true'}" >
          <b>Submitted</b>
          <input type="CHECKBOX" checked="checked" disabled="disabled" />
        </c:when>
        <c:otherwise >
          <input type="CHECKBOX" name="LibDir" value="Y"/> 
        </c:otherwise>
        </c:choose>
        <c:out value="${libDirectorBean.salutation}" /> <c:out value="${libDirectorBean.fname}" /> 
        <c:out value="${libDirectorBean.mname}" /> <c:out value="${libDirectorBean.lname}" />
      </td>
    </tr>
    <tr>
      <td></td>
      <td>Library Director</td>
    </tr>
    <tr>          
      <c:choose >
      <c:when test="${lduser.prgsa=='read' || appStatus.finalsignoffyn=='true' || appStatus.pendingFinalRev=='true'}" >
        <td colspan="2" align="center">
         <input type="button" value="Submit Authorization" disabled="disabled" />
        </td>
      </c:when>
      <c:otherwise >
      <td colspan="2" align="center">
        <input type="SUBMIT" value="Submit Authorization" name="btn" />
      </td>
      </c:otherwise>
      </c:choose>
    </tr>
  </table>            
  <input type="HIDDEN" name="currpage" value="saFinalRptSignoff" />
  <input type="hidden" name="p" value="sa" />
  </form>  
  <br/><br/>  
  
  </body>
</html>
