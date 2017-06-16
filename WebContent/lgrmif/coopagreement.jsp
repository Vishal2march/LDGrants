<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>untitled</title>
  </head>
  <body>
  
  <br/>
  <a href="http://www.archives.nysed.gov/common/archives/files/grants_lgrmif_egrant_user_manual.pdf" target="_blank">Shared Services Agreement Form help</a>
  <br/><br/><br/>
      
  <table width="90%" align="center" class="boxtype" summary="for layout only">
    <tr>
      <th colspan="2">LGRMIF Grant Shared Services Agreement Form</th>
    </tr>
    <tr>
      <td height="20" />
    </tr>
    <tr>
      <td>Sponsoring Institution:</td>
      <td><c:out value="${thisGrant.instName}" /></td>
    </tr>
    <tr>
      <td>Project Number:</td>
      <td>05<fmt:formatNumber minIntegerDigits="2" value="${thisGrant.fccode}" />
          -<fmt:formatNumber value="${thisGrant.fycode}" minIntegerDigits="2" />
          -<fmt:formatNumber value="${thisGrant.projseqnum}" minIntegerDigits="4" pattern="####" /></td>
    </tr>
    <tr>
        <td height="20"/>
    </tr>
    <tr>
      <td colspan="2">I hereby give assurance to the New York State Education Department that the 
      undersigned supports the enclosed application and will cooperate to the extent described in
      the attached application. All records management project outcomes that are a direct result 
      of the support provided by funds from the State are, or will be, made available for on-site
      examination.</td>
    </tr>
    <tr>
      <td height="20" />
    </tr>
    <tr>
      <td width="30%" valign="bottom">Participating Local Government Institution:<hr/></td>
      <td valign="bottom" height="40"><hr/></td>
    </tr>
    <tr>
      <td height="10" />
    </tr>
    <tr>
      <td width="30%" valign="bottom">Date:<hr/></td>
      <td valign="bottom" height="40">Signed:<hr/></td>
    </tr>
    <tr>
      <th width="30%">Chief Administrative Officer</th>
      <td valign="bottom" height="60">Print Name:<hr/></td>
    </tr>    
  </table>
  
  
  
  <br/><br/>
  <p><b>Instructions</b><br/>
    The Shared Services Agreement Form must be printed and signed by each participating institution 
    of your project. Then scan the signed form(s) and upload the form(s) to your application as 
    an attachment. <a href="LgAddAttachment.do">Attach</a>  the form as a document/attachment to your
    grant application.  Please use "SS Agreement" as the description for your attachment.    
    <br/><br/>
    
    *For applicants who do not have access to a scanner, please fax the document to 
    (518) 486-1647 or mail it to:<br/><br/>
    New York State Archives<br/>
    Grants Administration Unit<br/>
    9A81 Cultural Education Center,<br/>
    Albany, NY 12230
  </p>
  View <a href="ApcntFunctionsServlet?i=coopagree&m=lg" target="_blank">PDF</a> version of Shared Services Agreement Form
  
  </body>
</html>
