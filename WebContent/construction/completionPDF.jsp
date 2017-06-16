<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/pd4ml.tld" prefix="pd4ml" %>
<%@ page contentType="text/html;charset=ISO8859_1"%>
<pd4ml:transform screenWidth="400" encoding="default" pageFormat="LETTER" pageOrientation="portrait" enableImageSplit="false">
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Public Library Construction - Completion Form</title>
  </head>
  <body>
  
  
  <font size="1">
  <table align="center" width="90%" summary="for layout only">
    <tr>
        <th colspan="2">Project Completion Form</th>
    </tr>
    <tr>
        <th colspan="2">Public Library Construction Grant Program</th>
    </tr>
     <tr>
        <td colspan="2">Education Law §273-a (as amended by Chapter 57 of 2007)
        and Commissioner's Regulations §90.12</td>
    </tr>
     <tr>
        <td colspan="2">Please print, sign, and fill in this form as needed.  The form can
        be scanned and electronically attached to the online grant application. 
        The original form with original signatures must be kept on file, and can be 
        requested at any point in the future, should the need arise.</td>
    </tr>
    <tr>
        <td height="20"></td>
    </tr>
    <tr>
        <td><b>SED Project Number:</b></td>
        <td>03<fmt:formatNumber value="${thisGrant.fccode}" />
        -<fmt:formatNumber value="${thisGrant.fycode}" minIntegerDigits="2" />
        -<fmt:formatNumber value="${thisGrant.projseqnum}" pattern="####" minIntegerDigits="4" /></td>
    </tr>  
    <tr>
        <td><b>Name of Library:</b></td>
        <td><c:out value="${thisGrant.instName}" /></td>
    </tr>
    <tr>
        <td><b>Mailing Address:</b></td>
        <td><c:out value="${thisGrant.addr1}" /></td>
      </tr>
      <tr>
        <td><b>City, State, Zip:</b></td>
        <td><c:out value="${thisGrant.city}" />
            <c:out value="${thisGrant.state}" />
            <c:out value="${thisGrant.zipcd1}" />  
            <c:out value="${thisGrant.zipcd2}" /></td>
      </tr>
      <tr>
        <td><b>Name of Project Manager:</b></td>
        <td><c:out value="${managerBean.fname}" /> 
            <c:out value="${managerBean.lname}" /></td>
      </tr>
      <tr>
        <td><b>Phone:</b></td>
        <td><c:out value="${managerBean.phone}" /> 
            <c:out value="${managerBean.phoneext}" /></td>
      </tr>
      <tr>
        <td><b>Email:</b></td>
        <td><c:out value="${managerBean.email}" /></td>
      </tr>
      <tr>
        <td height="20"></td>
      </tr>
      <tr>
        <td><b>Grant Amount:</b></td>
        <td><fmt:formatNumber value="${finalExpenseBean.grantAmount}" type="currency" /></td>
      </tr>
      <tr>
        <td><b>Total Cost of Project:</b><br/>(All funding sources)</td>
        <td><fmt:formatNumber value="${finalExpenseBean.projectCost}" type="currency" /></td>
      </tr>
      <tr>
        <td colspan="2">CERTIFICATION OF PROJECT COMPLETION:  I hereby certify 
        that the information contained in this report is true and accurate and 
        that this project, identified by the project number above, has been 
        completed to the satisfaction of this Library Board of Trustees and in 
        accordance with the approved project application and the assurances agreed 
        to therein.</td>
      </tr>
      <tr>
        <td height="20"></td>
      </tr>
      <tr>
        <td><b>Date:</b></td>
        <td>______________________________________________</td>
      </tr>
      <tr>
        <td height="20"></td>
      </tr>
      <tr>
        <td><b>Signature:</b><br/>President of the Library Board or Authorized Officer of Library</td>
        <td>______________________________________________</td>
      </tr>
      <tr>
        <td height="20"></td>
      </tr>
      <tr>
        <td><b>Name and Title:</b><br/>(Print)</td>
        <td>______________________________________________</td>
      </tr>      
    </table>
    </font>
  
  </body>
</html>
</pd4ml:transform>