<%--
 * @author  Stefanie Husak
 * @version 2.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the Application        :  viewAuthorizations.jsp
 * Creation/Modification History  :
 *
 *     SHusak       7/19/07     Created
 *
 * Description
 * This page allows the applicant to view the inst authorizations and 
 * final signoffs for the given grant id.  Used for admin sa and co and
 * sa prior apcnt and co participating inst. 
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
  
  <h4>Application Authorizations</h4>
     
     
    <p align="left">The undersigned hereby give assurance to the New York State Education Department 
          that the statements in the preceding application are true and correct to the best of 
          their knowledge, information, and belief, that the requested funds will be used to 
          support the activities described, and that all materials whose preservation treatment
          is supported by funds from the State are, or will be, made available for reference, 
          on-site examination and/or loan.  It is understood by the applicant that this application constitutes an offer and,
          if accepted by the NYS Education Department or renegotiated to acceptance, will form a binding agreement.  
          It is also understood by the applicant that immediate written notice will be provided to the grant program 
          office if at any time the applicant learns that its certification was erroneous when submitted or has 
          become erroneous by reason of changed circumstances.  </p>
  
    <table align="center" class="boxtype" width="90%" summary="for layout only">
      <tr>
        <th colspan="4">Institutional Authorization</th>
      </tr>
      <tr>
        <th>Date Authorized</th>
        <th>Title</th>
        <th>Name</th>
        <th>User Authorized</th>
      </tr>
      
      <c:forEach var="authBean" items="${grantAuth}" >
      <c:if test="${authBean.version=='InstAuth'}" >
        <tr>
          <td ><fmt:formatDate value="${authBean.authdate}" pattern="MM/dd/yyyy" /></td>
          <td><c:out value="${authBean.title}" /></td>
          <td><c:out value="${authBean.name}" /></td>
          <td><c:out value="${authBean.user}" /></td>
        </tr>    
      </c:if>
      </c:forEach>          
    </table>
     
    <br/><br/>
    
    <table align="center" class="boxtype" width="90%" summary="for layout only">
      <tr>
        <th colspan="4">Final Report Signoff</th>
      </tr>
      <tr>
        <th>Date Authorized</th>
        <th>Title</th>
        <th>Name</th>
        <th>User Authorized</th>
      </tr>
      
      <c:forEach var="authBean" items="${grantAuth}" >
      <c:if test="${authBean.version=='FinalSignoff'}" >
        <tr>
          <td ><fmt:formatDate value="${authBean.authdate}" pattern="MM/dd/yyyy" /></td>
          <td><c:out value="${authBean.title}" /></td>
          <td><c:out value="${authBean.name}" /></td>
          <td><c:out value="${authBean.user}" /></td>
        </tr>    
      </c:if>
      </c:forEach>
    </table>
    <br/><br/>
      
    <table align="center" class="boxtype" width="90%" summary="for layout only">  
      <tr>
        <th colspan="4">Cooperative Agreements</th>
      </tr>
      <tr>
        <th>Date Authorized</th>
        <th>Title</th>
        <th>Name</th>
        <th>User Authorized</th>
      </tr>
      
      <c:forEach var="authBean" items="${grantAuth}" >
      <c:if test="${authBean.version=='CoopAgreement'}" >
      <tr>
        <td ><fmt:formatDate value="${authBean.authdate}" pattern="MM/dd/yyyy" /></td>
        <td><c:out value="${authBean.title}" /></td>
        <td><c:out value="${authBean.name}" /></td>
        <td><c:out value="${authBean.user}" /></td>
      </tr>    
      </c:if>
      </c:forEach>
    </table>
  
  </body>
</html>
