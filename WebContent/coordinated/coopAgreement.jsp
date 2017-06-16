<%--
 * @author  Stefanie Husak
 * @version 2.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the Application        :  coopAgreement.jsp
 * Creation/Modification History  :
 *     SHusak   7/20/07     Modified
 *
 * Description
 * This is the cooperative agreement for participating libraries in CO. Each lib
 * has to check off next to thier PO and LD to acknowledge authorization of the
 * application for the lead library.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>untitled</title>
  </head>
  <body>
  
 <jsp:useBean id="now" class="java.util.Date" />
 <fmt:formatDate var="todayDate" value="${now}" pattern="MM/dd/yyyy" />
  
  <h4>Cooperative Agreement</h4>
      
 
  <table width="80%" align="center" class="boxtype" summary="for layout only">
    <tr>
      <td align="center" >I hereby give assurance to the New York State Education Department 
      that the undersigned supports the enclosed application and will cooperate to 
      the extent described in the attached application.  All materials whose
      preservation is supported by funds from the State are, or will be, made 
      available for reference, on-site examination, and/or loan.
      </td>
    </tr>
  </table>
  
      
  <form method="POST" action="AuthorizationsServlet">  
    
    <c:forEach var="authBean" items="${grantAuth}" >
      <c:if test="${authBean.title == 'Preservation Officer' && authBean.version=='CoopAgreement'}" >
        <c:set var="poAuth" value="true" />
      </c:if>
      <c:if test="${authBean.title == 'Library Director' && authBean.version=='CoopAgreement'}" >
        <c:set var="ldAuth" value="true" />
      </c:if>
    </c:forEach>
    
    
   
    <%-- NEED TO GET LIBRARY DIRECTOR AND PRESRVATION OFFICER FROM SEDREF --%>  
    <table cellpadding="2" border="1" width="80%" align="center" summary="for layout only">
      <tr>
        <td width="40%">Date: <c:out value="${todayDate}" /> </td>
        <td>
          <c:choose >
          <c:when test="${poAuth=='true'}" >
             <b>Submitted</b><input type="CHECKBOX" checked="checked" disabled="disabled" />
          </c:when>
          <c:otherwise >
            <input type="CHECKBOX" name="PresOfficer" value="Y">
          </c:otherwise>
          </c:choose>            
          <c:out value="${presOfficerBean.salutation}" /> <c:out value="${presOfficerBean.fname}" /> <c:out value="${presOfficerBean.mname}" /> <c:out value="${presOfficerBean.lname}" />
        </td>
      </tr>
      <tr>
        <td></td>
        <td>Preservation Program Officer</td>
      </tr>
      <tr>
        <td height="25"></td>
      </tr>

      <tr>
        <td>Date: <c:out value="${todayDate}" /> </td>
        <td>
          <c:choose >
          <c:when test="${ldAuth=='true'}" >              
            <b>Submitted</b><input type="CHECKBOX" checked="checked" disabled="disabled" />
          </c:when>
          <c:otherwise >
            <input type="CHECKBOX" name="LibDir" value="Y">
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
    </table>
        
           
    
   
    
    <c:choose >
    <c:when test="${lduser.prgco=='read'}" >
      <p align="center">
        <input type="BUTTON" value="Submit Authorization" disabled="disabled" /> 
      </p>
    </c:when>
    <c:otherwise >
      <p align="center">
        <input type="HIDDEN" name="currpage" value="coCoopAgree" />
        <input type="hidden" name="p" value="co" />
        <input type="SUBMIT" value="Submit Authorization" />
      </p>
    </c:otherwise>
    </c:choose>
    

  </form>  
  
  </body>
</html>
