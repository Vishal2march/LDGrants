<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>untitled</title>
  </head>
  <body>
  <h5>Final Report Signoff</h5>
  <a href="http://www.archives.nysed.gov/common/archives/files/grants_lgrmif_egrant_user_manual.pdf" target="_blank">Final signoff help</a>
  <br/>
    
  <table width="90%" class="boxtype" summary="for layout only">
    <tr>
      <th colspan="2">Final Report Signoff</th>
    </tr>
    <tr>
      <td height="15" />
    </tr>
    <tr>
      <td>Sponsoring Institution:</td>
      <td><c:out value="${thisGrant.instName}" /></td>
    </tr>
    <tr>
      <td>Project Number:</td>
      <td>05<fmt:formatNumber value="${thisGrant.fccode}" />
        -<fmt:formatNumber value="${thisGrant.fycode}" minIntegerDigits="2" />
        -<fmt:formatNumber value="${thisGrant.projseqnum}" minIntegerDigits="4" pattern="####" /></td>
    </tr>
    <tr>
        <td colspan="2"><br/></td>
    </tr>
    <tr>
      <td colspan="2">I hereby certify that all expenditures reported in the Final Expenditure
      Report (FS-10-F) are directly attributable to this project, and that the Final Project Narrative
      that has been entered is an accurate and complete account of the project.</td>
    </tr>
    <tr>
      <td valign="bottom" width="30%" height="50">Date:<hr/></td>
      <td valign="bottom">Signed:<hr/></td>
    </tr>
    <tr>
      <th width="30%" height="50">Chief Administrative Officer</th>
      <td valign="bottom">Print Name:<hr/></td>
    </tr>    
  </table>
  
  
  <br/><br/>
  <p><b>Instructions</b><br/>
    The LGRMIF Grant Program Final Signoff Form must be printed and signed at the conclusion of your project.
    Then scan the signed form and upload the form to your application as an attachment.  
    <a href="LgAddAttachment.do">Attach</a>  the form as a document/attachment to your
  grant application. Please put "Final Signoff" as the description for your attachment.<br/><br/>
    
    *For applicants who do not have access to a scanner, please fax the document to (518) 486-1647 or mail it to:<br/><br/>
    New York State Archives<br/>
    Grants Administration Unit<br/>
    9A81 Cultural Education Center,<br/>
    Albany, NY 12230<br/><br/>

    View <a href="ApcntFunctionsServlet?i=finalauth" target="_blank">PDF</a> version of Final Signoff Form
  </p>
  
  </body>
</html>
