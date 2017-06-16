<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>LGRMIF Vendor Quote Form</title>
    <link href="css/StateAid.css" rel="stylesheet" media="screen" type="text/css"/>
  </head>
  <body>
  
  <br/>
  <table align="center" summary="for layout only">
    <tr>
      <th colspan="2">Local Government Records Management Improvement Fund (LGRMIF) 
          <br/>Vendor Quote Form
      </th>
    </tr>
    <tr>
      <td>Project Number</td>
      <td>05<fmt:formatNumber minIntegerDigits="2" value="${thisGrant.fccode}" />
          -<fmt:formatNumber value="${thisGrant.fycode}" minIntegerDigits="2" />
          -<fmt:formatNumber value="${thisGrant.projseqnum}" pattern="####" minIntegerDigits="4" />
      </td>
    </tr>
    <tr>
      <td>Institution</td>
      <td><c:out value="${thisGrant.instName}" /> -<c:out value="${thisGrant.dorisname}" /></td>
    </tr>
  </table>
  <br/><br/>
  
  
  <logic:notEmpty name="vendorRecords">
  
  <b>Vendor Quote Form</b>
  <table border="1" summary="for layout only">
  <tr>
      <td><b>Vendor</b></td><td><b>Description of Item or Service</b></td>
      <td><b>Contract#</b></td><td><b>Preferred Vendor</b></td>
      <td><b>Sole Source Vendor</b></td><td><b>LGPR</b></td>
      <td><b>Quoted Price</b></td><td><b>Selected Quote</b></td>
  </tr>
  
  <c:forEach var="vb" items="${vendorRecords}">  
    <tr>
      <td><c:out value="${vb.name}"/></td>
      <td><c:out value="${vb.description}"/></td>
      <td><c:out value="${vb.statecontractnum}"/></td>
      <td><c:out value="${vb.preferredvendor}"/></td>
      <td><c:out value="${vb.solesource}"/></td>
      <td><c:out value="${vb.procurementreq}"/></td>
      <td><fmt:formatNumber value="${vb.pricequote}" type="currency" minFractionDigits="0"/></td>
      <td><c:out value="${vb.selectedquote}"/></td>
    </tr>  
  </c:forEach>
  </table>
  </logic:notEmpty>
  
    
  </body>
</html>
