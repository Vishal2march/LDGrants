<%--
 * @author  Stefanie Husak
 * @version 2.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the Application        :  coViewCoversheet.jsp
 * Creation/Modification History  :
 *
 *     SHusak       7/18/07     Created
 *
 * Description
 * This page allows the applicant to view a read only version of the
 *  fiscal year coversheet information.
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=windows-1252"%>

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title></title>
    <link href="css/StateAid.css" rel="stylesheet" media="screen" type="text/css" />
  </head>
  <body>
    
  <h4>Coversheet - Fiscal Year <c:out value="${thisGrant.fiscalyear}" /></h4>
    
    
  <table align="center" border="1" class="boxtype" width="90%" summary="for layout only">
    <tr>
      <th colspan="2">Coordinated Grant Application for Fiscal Year <c:out value="${thisGrant.fiscalyear}" /></th>
    </tr>
    <tr>
      <td><b>Project Number</b></td>
      <td><b>03<fmt:formatNumber minIntegerDigits="2" pattern="##" type="number" value="${thisGrant.fccode}" />
          -<fmt:formatNumber value="${thisGrant.fycode}" minIntegerDigits="2" />
          -<fmt:formatNumber value="${thisGrant.projseqnum}" minIntegerDigits="4" pattern="####" /></b></td>
    </tr>
    <tr>
      <td><b>Project Title</b></td>
      <td><c:out value="${thisGrant.title}" /></td>
    <tr>
      <td><b>Sponsoring Institution</b></td>
      <td><c:out value="${thisGrant.instName}" /></td>
    </tr>
    <tr>
      <td><b>Address</b></td>
      <td>
        <c:out value="${thisGrant.addr1}" /> <br/><c:out value="${thisGrant.addr2}" />
      </td>
    </tr>
    <tr>
      <td><b>City, State</b></td>
      <td><c:out value="${thisGrant.city}" /> <c:out value="${thisGrant.state}" /> 
          <c:out value="${thisGrant.zipcd1}" />-<c:out value="${thisGrant.zipcd2}" /></td>
    </tr>
    <tr>
      <td><b>Library Director</b></td>
      <td><c:out value="${libDirectorBean.salutation}" /> <c:out value="${libDirectorBean.fname}" />
      <c:out value="${libDirectorBean.lname}" /></td>
    </tr>
     <tr>
      <td><b>Preservation Administrative Officer</b></td>
      <td>
      <c:out value="${presOfficerBean.salutation}" /> <c:out value="${presOfficerBean.fname}" /> <c:out value="${presOfficerBean.lname}" />
      </td>
    </tr>
    <tr>
      <td><b>Project Manager</b></td>
      <td>
      <c:out value="${projManagerBean.fname}" /> <c:out value="${projManagerBean.mname}" /> <c:out value="${projManagerBean.lname}" />
      </td>
    </tr>    
    <tr>
      <td colspan="2" height="20" />
    </tr>
    <tr>
      <th colspan="2">Participating Institutions</th>
    </tr>
    <tr>
      <td colspan="2">Each participating institution must complete an online Cooperative Agreement
        form.  The form is accessed by the Cooperative Agreement menu link. </td>
    </tr>
    
    <c:forEach var="partInstBean" items="${allPartInst}">
      <tr>
        <td colspan="2"><c:out value="${partInstBean.instName}" /></td>
      </tr>
    </c:forEach>
  </table>
  
  <br/>
         
  <p align="center">
    <a href="coParticipantForms.do?item=appstatus" >View Submission/Approvals</a><br/>
    <a href="coParticipantForms.do?item=auth">View Authorizations</a><br/>
    <a href="coParticipantForms.do?item=ratings">View Reviewer Comments/Scores</a>
  </p>
          
  <p align="center">
    The following links will open in a new window<br/>
    <a href="PrintAppServlet?i=app" target="_blank">View Application - HTML</a>
    &nbsp;&nbsp;&nbsp;&nbsp;    
    <a href="PrintAppServlet?i=narr" target="_blank">View Narratives - HTML</a><br/>
    <a href="PrintAppServlet?i=app&a=false" target="_blank" >View Application - PDF</a>
    &nbsp;&nbsp;&nbsp;&nbsp;
    <a href="PrintAppServlet?i=narrpdf" target="_blank" >View Narratives - PDF</a>
  </p>
  
  </body>
</html>
