<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>untitled</title>
  </head>
  <body>
  
  <h4>Institutional Authorization</h4>
  
  <table width="90%" align="center" class="boxtype" summary="for layout only">
    <tr>
      <th colspan="2">LGRMIF Grant Project</th>
    </tr>
    <tr>
      <td colspan="2"><b>Sponsoring Institution:</b>&nbsp;&nbsp;
      <c:out value="${thisGrant.instName}" /></td>
    </tr>
    <tr>
      <td colspan="2"><b>Project Number: </b>&nbsp;&nbsp;
      05<fmt:formatNumber value="${thisGrant.fccode}" />
            -<fmt:formatNumber value="${thisGrant.fycode}" minIntegerDigits="2" />
            -<fmt:formatNumber value="${thisGrant.projseqnum}" minIntegerDigits="4" pattern="####" /></td>
    </tr>
    <tr>
        <td colspan="2"><b>Category:</b>&nbsp;&nbsp;
        <c:out value="${thisGrant.projcategory}" /></td>
    </tr>
    <tr>
      <td height="10" />
    </tr>
    <tr>
      <td colspan="2"><b>Certification and Approval</b><br/>
      The following signatures certify that the local government agrees to the conditions 
      outlined in Appendix A and Appendix A-1 G.  <b>Both</b> the CAO and RMO must sign the 
      Institutional Authorization form.<br/><br/> 
      I hereby certify that I am either the applicant’s Chief Administrative Officer (CAO) or the 
      Records Management Officer (RMO), and that the information contained in the application is,
      to the best of our knowledge, complete and accurate. I further certify, to the best of my 
      knowledge, that any ensuing program and activity will be conducted in accordance with all 
      applicable state laws and regulations, application guidelines and instructions, and that 
      the requested budget amounts are necessary for the implementation of this project. I 
      understand that the application constitutes an offer and, if accepted by the New York 
      State Education Department or renegotiated to acceptance, will form a binding agreement. 
      I also understand that immediate written notice will be provided to the grants program 
      office if at any time I learn that its certification was erroneous when submitted, or has 
      become erroneous by reason of changed circumstances.</td>
    </tr>
    <tr>
      <td height="10" />
    </tr>   
    <tr>
      <td colspan="2"><b>CHIEF ADMINISTRATIVE OFFICER</b></td>
    </tr>
    <tr>
      <td valign="bottom" height="50"><hr/>Signature <i>(original signature in blue ink)</i></td>
      <td valign="bottom" ><hr/>Date</td>
    </tr>
    <tr>
      <td colspan="2" height="10" />
    </tr>
    <tr>
      <td colspan="2" valign="bottom" height="50" ><hr/>Print Name and Title of CAO</td>
    </tr>
    <tr>
      <td colspan="2" height="20" />
    </tr>
    <tr>
      <td colspan="2"><b>RECORDS MANAGEMENT OFFICER (RMO)</b></td>
    </tr>
    <tr>
      <td valign="bottom" height="50"><hr/>Signature <i>(original signature in blue ink)</i></td>
      <td valign="bottom" ><hr/>Date</td>
    </tr>
    <tr>
      <td colspan="2" height="10" />
    </tr>
    <tr>
      <td valign="bottom" height="50" ><hr/>Print Name and Title of RMO</td>
      <td valign="bottom" ><hr/>Phone</td>
    </tr>
    <tr>
      <td colspan="2" height="20" />
    </tr>
  </table>
  
  
  <br/><br/>
  <p><b>Instructions</b><br/>
  The Authorization Form must be printed, completed, and signed in blue ink.  Please mail the completed form to:<br/><br/>
  New York State Archives<br/>
  Grants Administration Unit<br/>
  9A81 Cultural Education Center,<br/>
  Albany, NY 12230
  <br/><br/>
  View <a href="ApcntFunctionsServlet?i=lgauth" target="_blank">PDF</a> version of Institutional Authorization Form
  </p>
    
  </body>
</html>
