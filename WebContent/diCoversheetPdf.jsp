<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/pd4ml.tld" prefix="pd4ml" %>
<%@ page contentType="text/html;charset=ISO8859_1"%>
<pd4ml:transform screenWidth="600" encoding="default" pageFormat="LETTER" pageOrientation="portrait" enableImageSplit="false">
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>Discretionary Grant Coversheet</title>
  </head>
  <body>
  
  <font size="1">
  <h2 align="center">Conservation Preservation Program Grant Application
      <br/>The University of the State of New York 
      <br/>The State Education Department 
      <br/>Division of Library Development 
      <br/>Discretionary Grants
  </h2>
  </font>
  
  <font size="1">
  <table align="center" border="1" width="98%" summary="for layout only">
  <tr >
    <th colspan="2">Grant Application for Fiscal Year <c:out value="${thisGrant.fiscalyear}" /></th>
  </tr>
  
  <tr>
    <td><b>Project Number</b></td>
    <td>03<fmt:formatNumber minIntegerDigits="2" value="${thisGrant.fccode}" />
        -<fmt:formatNumber value="${thisGrant.fycode}" minIntegerDigits="2" />
        -<fmt:formatNumber value="${thisGrant.projseqnum}" pattern="####" minIntegerDigits="4" /></td>
  </tr>  
  <tr>
    <td><b>Project Title</b></td>
    <td><c:out value="${thisGrant.title}" /></td>
  </tr>
  <tr>
    <td><b>Institution ID</b></td>
    <td><c:out value="${thisGrant.instID}" /></td>
  </tr>
  <tr>
    <td><b>Institution</b></td>
    <td><c:out value="${thisGrant.instName}" /></td>
  </tr>
  <tr>
    <td><b>Address</b></td>
    <td><c:out value="${thisGrant.addr1}" /><br/><c:out value="${thisGrant.addr2}" /></td>
  </tr>
  <tr>
    <td><b>City, State</b></td>
    <td><c:out value="${thisGrant.city}" /> <c:out value="${thisGrant.state}" /> 
        <c:out value="${thisGrant.zipcd1}" /> <c:out value="${thisGrant.zipcd2}" /></td>
  </tr>
  <tr>
    <td><b>State Judicial District:</b> <c:out value="${distBean.judicialDistricts}" /></td>
    <td><b>State Assembly Districts:</b> <c:forEach var="dist" items="${distBean.assemblyDistricts}" >
                                    <c:out value="${dist}" />
                                 </c:forEach></td>
  </tr>
  <tr>
    <td><b>State Senate Districts:</b> <c:forEach var="sdist" items="${distBean.senateDistricts}" >
                                  <c:out value="${sdist}" />
                               </c:forEach></td>
    <td><b>State Congressional Districts:</b> <c:forEach var="cdist" items="${distBean.congressDistricts}" >
                                        <c:out value="${cdist}" />
                                      </c:forEach></td>
  </tr>
  <tr>
    <td><b>FEIN #:</b> <c:out value="${distBean.federalid}" /></td>
    <td><b>School District:</b> <c:out value="${distBean.schoolDistrict}" /></td>
  </tr>        
  <tr>
    <td><b>Institution Type</b></td>
    <td><c:out value="${distBean.insttype}" /></td>
  </tr>
  <tr>
    <td><b>Religious Affiliation</b></td>
    <td><c:out value="${coversheetBean.religious}" /></td>
  </tr>
  <tr>
    <td><b>Institutional Eligibility</b></td>
    <td>
      <table width="100%">
      <c:if test="${coversheetBean.chartered}" >
      <tr>
        <td>Chartered by the Board of Regents of NYS</td>
        <td>Date: <c:out value="${coversheetBean.charterdate}" /></td>
      </tr>
      </c:if>
      
      <c:if test="${coversheetBean.accepted}">
      <tr>
        <td>Accepted by the Board of Regents of the State of New York for filing under the not-for-
         profit section (Section 216) of the Education Law</td>
        <td>Date: <c:out value="${coversheetBean.acceptdate}"/></td>
      </tr>
      </c:if>
      
      <c:if test="${coversheetBean.charity}">
      <tr>
        <td>Registered with the Office of Charities of the NYS Department of State</td>
        <td>Date: <c:out value="${coversheetBean.charitydate}"/></td>
      </tr>
      </c:if>
      
      <c:if test="${coversheetBean.notprofit}">
      <tr>
        <td>Granted not-for-profit status under section 501(c)(3) of the United States Internal Revenue Code</td>
        <td>Date: <c:out value="${coversheetBean.notprofitdate}"/></td>
      </tr>
      </c:if>
      
      <c:if test="${coversheetBean.other}">
      <tr>
        <td>Other</td>
      </tr>
      </c:if>
    </table>
    </td>
  </tr>
  <tr>
    <td><b>Director of Institution</b></td>
    <td><c:out value="${libDirectorBean.salutation}" /> <c:out value="${libDirectorBean.fname}" /> <c:out value="${libDirectorBean.lname}" /></td>
  </tr>
  <tr>
    <td><b>Title</b></td>
    <td><c:out value="${libDirectorBean.title}" /></td>
  </tr>
  <tr>
    <td><b>Email</b></td>
    <td><c:out value="${libDirectorBean.email}" /></td>
  </tr>
  <tr>
    <td><b>Project Manager</b></td>
    <td>
      <c:out value="${coversheetBean.fname}" /> <c:out value="${coversheetBean.mname}" /> <c:out value="${coversheetBean.lname}" />
    </td>
  </tr>
  <tr>
    <td><b>Project Manager Phone</b></td>
    <td><c:out value="${coversheetBean.phone}" /></td>
  </tr>
  <tr>
    <td><b>Project Manager Email</b></td>
    <td><c:out value="${coversheetBean.email}" /></td>
  </tr>
  <tr>
    <td><b>Total amount requested</b></td>
    <td><fmt:formatNumber type="currency" value="${coversheetBean.amtrequested}" /></td>
  </tr>
  <tr>
    <th colspan="2">Participating Institutions</th>
  </tr>    
    
  <c:forEach var="partInstBean" items="${allPartInst}">
    <tr>
      <td colspan="2"><c:out value="${partInstBean.instName}" /></td>
    </tr>
  </c:forEach>  
  
  <tr>
    <th colspan="2">Summary Description</th>
  </tr>
  <tr>
    <td colspan="2"><bean:write name="coversheetBean" property="summaryDesc" filter="false" /></td>
  </tr>  
  </table>  
  </font>  

    
  </body>
</html>


</pd4ml:transform>