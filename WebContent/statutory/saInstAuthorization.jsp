<%--
 * @author  Stefanie Husak
 * @version 2.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the Application        :  saInstAuthorization.jsp
 * Creation/Modification History  :
 *
 *     SHusak       3/1/07     Created
 *
 * Description
 * This page allows the applicant to save the Preservation Officer and 
 * Library Director authorization for initial application.  Kind of like a 
 * digital signature, they check off next to thier name for authorization.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>Institutional Authorization</title>
  </head>
  <body>  
 <jsp:useBean id="now" class="java.util.Date" />
 <fmt:formatDate var="todayDate" value="${now}" pattern="MM/dd/yyyy" />
  
  <h4>Institutional Authorization</h4>
  
  
  <c:forEach var="authBean" items="${grantAuth}" >
    <c:if test="${authBean.title == 'Preservation Officer' && authBean.version=='InstAuth'}" >
      <c:set var="poAuth" value="true" />
    </c:if>
    <c:if test="${authBean.title == 'Library Director' && authBean.version=='InstAuth'}" >
      <c:set var="ldAuth" value="true" />
    </c:if>
    <c:if test="${authBean.title == 'Research Foundation' && authBean.version=='InstAuth'}" >
      <c:set var="rfAuth" value="true" />
    </c:if>
  </c:forEach>
    
  
  <form method="POST" action="AuthorizationsServlet">
  <table align="center" width="80%" class="boxtype" summary="for layout only"  >
    <tr >
      <td colspan="2">The undersigned hereby give assurance to the New York State Education Department 
          that the statements in the preceding application are true and correct to the best of 
          their knowledge, information, and belief, that the requested funds will be used to 
          support the activities described, and that all materials whose preservation treatment
          is supported by funds from the State are, or will be, made available for reference, 
          on-site examination and/or loan. It is understood by the applicant that this application constitutes an offer and,
          if accepted by the NYS Education Department or renegotiated to acceptance, will form a binding agreement.  
          It is also understood by the applicant that immediate written notice will be provided to the grant program 
          office if at any time the applicant learns that its certification was erroneous when submitted or has 
          become erroneous by reason of changed circumstances.</td>
    </tr>
  </table>
     
    
    
    <%-- NEED TO GET LIBRARY DIRECTOR AND PRESRVATION OFFICER FROM SEDREF --%>
    <table align="center" border="1" class="graygrid" width="80%" summary="for layout only">
      <tr>
        <td width="40%">Date: <c:out value="${todayDate}" /> </td>
        <td>
          <c:choose >
          <c:when test="${poAuth=='true'}" >
            <b>Submitted</b>
            <input type="CHECKBOX" checked="checked" disabled="disabled" />
          </c:when>
          <c:otherwise >
            <input type="CHECKBOX" name="PresOfficer" value="Y">
          </c:otherwise>
          </c:choose>            
          <c:out value="${presOfficerBean.salutation}" /> <c:out value="${presOfficerBean.fname}" /> <c:out value="${presOfficerBean.mname}" /> <c:out value="${presOfficerBean.lname}" />
          <br/>Preservation Program Officer
        </td>
      </tr>
      <tr>
        <td height="25"></td>
      </tr>
        
      <tr>
        <td>Date: <c:out value="${todayDate}" /> </td>
        <td>
          <c:choose >
          <c:when test="${ldAuth=='true'}" > 
            <b>Submitted</b>
            <input type="CHECKBOX" checked="checked" disabled="disabled" />
          </c:when>
          <c:otherwise >
            <input type="CHECKBOX" name="LibDir" value="Y">
          </c:otherwise>
          </c:choose>
          <c:out value="${libDirectorBean.salutation}" /> <c:out value="${libDirectorBean.fname}" />
          <c:out value="${libDirectorBean.mname}" /> <c:out value="${libDirectorBean.lname}" />
          <br/>Library Director
        </td>
      </tr>
      
      <%--Only display research foundation auth for SUNY institutions--%>
      <%--per BL 5/21/14 remove for 2014-15 grants for all suny --%>
      <%--<c:if test="${isSuny=='true'}" >
        <tr>
          <td height="25"></td>
        </tr>   
        <tr>
          <td>Date: <c:out value="${todayDate}" /> </td>
          <td>
            <c:choose >
            <c:when test="${rfAuth=='true'}" > 
              <b>Submitted</b>
              <input type="CHECKBOX" checked="checked" disabled="disabled" />
            </c:when>
            <c:otherwise >
              <input type="CHECKBOX" name="ResFnd" value="Y">
            </c:otherwise>
            </c:choose>
            SUNY Research Foundation
          </td>
        </tr>
      </c:if>--%>
    </table>
    <input type="HIDDEN" name="currpage" value="saInstAuth" />
    <input type="hidden" name="p" value="sa" />
    
    <table align="center">
      <c:choose >
      <c:when test="${lduser.prgsa=='read' || appStatus.instauthyn=='true' || appStatus.pendingReview=='true'}" >
        <tr>
          <td align="center"><input type="BUTTON" value="Submit Authorization" disabled="disabled" /> </td>
        </tr>    
      </c:when>
      <c:otherwise >
        <tr>
          <td align="center"><input type="SUBMIT" value="Submit Authorization" /></td>
        </tr>
      </c:otherwise>
      </c:choose>    
    </table>
  
  
  </form> 
  </body>
</html>
