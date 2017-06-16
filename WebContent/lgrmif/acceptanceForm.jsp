<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>acceptanceForm</title>
  </head>
  <body>
  
  
  <h4>LGRMIF Grant Acceptance Form</h4>
  
  <table width="90%" align="center" class="boxtype" summary="for layout only">
    <tr>
      <th colspan="2">LGRMIF Grant Acceptance Form</th>
    </tr>
    <tr>
      <td width="40%"><b>Local Government:</b></td>
      <td><c:out value="${thisGrant.instName}" /></td>
    </tr>
    <tr>
      <td><b>Project Number:</b></td>
      <td>05<fmt:formatNumber value="${thisGrant.fccode}" />
            -<fmt:formatNumber value="${thisGrant.fycode}" minIntegerDigits="2" />
            -<fmt:formatNumber value="${thisGrant.projseqnum}" minIntegerDigits="4" pattern="####" /></td>
    </tr>
    <tr>
      <td><b>Budget Summary:</b></td>
    </tr>
    <tr>
        <td height="30"/>
    </tr>
    <tr>
        <td>Code 15 Professional Salaries:</td>
        <td><fmt:formatNumber value="${totalsBean.proffAmtAppr}" type="currency" maxFractionDigits="0"/></td>
    </tr>
    <tr>
        <td>Code 16 Support Staff Salaries:</td>
        <td><fmt:formatNumber value="${totalsBean.profsuppAmtAppr}" type="currency" maxFractionDigits="0"/></td>
    </tr>
    <tr>
        <td>Code 40 Purchased Services:</td>
        <td><fmt:formatNumber value="${totalsBean.purchAmtAppr}" type="currency" maxFractionDigits="0"/></td>
    </tr>
    <tr>
        <td>Code 45 Supplies and Materials:</td>
        <td><fmt:formatNumber value="${totalsBean.supplyAmtAppr}" type="currency" maxFractionDigits="0"/></td>
    </tr>
    <tr>
        <td>Code 46 Travel Expenses:</td>
        <td><fmt:formatNumber value="${totalsBean.travAmtAppr}" type="currency" maxFractionDigits="0"/></td>
    </tr>
    <tr>
        <td>Code 80 Employee Benefits:</td>
        <td><fmt:formatNumber value="${totalsBean.benAmtAppr}" type="currency" maxFractionDigits="0"/></td>
    </tr>
    <tr>
        <td>Code 49 BOCES Services:</td>
        <td><fmt:formatNumber value="${totalsBean.bocesAmtAppr}" type="currency" maxFractionDigits="0"/></td>
    </tr>
    <tr>
        <td>Code 30 Minor Remodeling:</td>
        <td><fmt:formatNumber value="${totalsBean.othAmtAppr}" type="currency" maxFractionDigits="0"/></td>
    </tr>
    <tr>
        <td>Code 20 Equipment:</td>
        <td><fmt:formatNumber value="${totalsBean.equipAmtAppr}" type="currency" maxFractionDigits="0"/></td>
    </tr>
    
    <tr>
      <td><b>Amount of Grant Award:</b></td>
      <td><fmt:formatNumber value="${totalsBean.totAmtAppr}" type="currency" maxFractionDigits="0"/></td>
    </tr>
    <tr>
        <td height="30"/>
    </tr>
    <tr>
      <td colspan="2">I hereby accept a grant from the Local Government Records Management 
      Improvement Fund in the amount indicated above and agree to comply with all reporting 
      requirements. These funds will be expended in accordance with the budget as detailed 
      above and approved by the State Education Department.</td>
    </tr>
    <tr>
      <td height="40"><b>Chief Administrative Officer:</b></td>
    </tr>
    <tr>
      <td height="40"><b>Signature:</b></td>
    </tr>
    <tr>
      <td height="40"><b>Title:</b></td>
    </tr>
    <tr>
      <td height="40"><b>Date:</b></td>
    </tr>
    <tr>
      <td height="80"/>
    </tr>
    <tr>
      <td>Complete this form and return it to:<br/>
        The New York State Archives<br/>
        Grant Administration Unit<br/>
        9A81 Cultural Education Center<br/>
        Albany, NY  12230</td>
      <td>If you have any questions, please contact the<br/>Grant Administration Unit:<br/>
        Telephone: (518) 474 - 6926<br/>
        E-mail: archgrants@nysed.gov<br/></td>
    </tr>
  </table>
  
  </body>
</html>