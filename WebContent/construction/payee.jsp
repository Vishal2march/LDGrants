<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>home</title>
  </head>
  <body>
  
  <h4>Payee Information Form</h4>

 <table align="center" width="90%" class="boxtype" summary="for layout only">
 <tr>
    <td>In order to receive funds from the NYS Education Department, libraries must be entered on the SED 
    vendor database, have been assigned a unique vendor ID number (not a number shared with the library's
    town or village) and have accurate payee information on record.<br/><br/>
    
    Each library must submit both a Payee and a W-9 form. Please complete these forms per the instructions 
    on the form <b>but please note despite what is indicated on the Payee Info Form, a Substitute W-9 Form must be 
    completed by ALL Construction  applicants and it should be sent to your public library system not directly to 
    the State.</b> This form is NOT submitted as a PDF attachment, but must be submitted with an original signature 
    signed in blue ink to the library system, who will batch the payee forms from all approved applications and 
    send them to Kimberly Anderson at the Division of Library Development/New York State Library.  </td>
 </tr>
 <tr>
    <td height="20"></td>
 </tr>
 <tr>
    <td>Questions concerning the Payee Information Form should be directed to 
    Barbara Massago at barbara.massago@nysed.gov  </td>
 </tr>
 <tr>
    <td height="20"></td>
 </tr>
 <tr>
    <td>PDF version of the 
        <a href="docs/PayeeInfoForm.pdf" target=_blank >Payee Information Form</a></td>
 </tr> 
 <tr>
    <td height="20"></td>
 </tr>
 <tr>
    <td><font color="Red">*Note</font> this form must be printed, 
        filled out by hand and mailed to your Public Library System (along with your printed
        and signed FS-10 Form).
        This form cannot be electronically attached to your application.</td>
 </tr>
 <tr>
    <td height="20"></td>
 </tr>
 <c:choose>
 <c:when test="${param.p!=null && param.p=='rev'}">
    <tr>
        <td>The Public Library System will mail 1 Payee Form, and 3 
        FS-20 Forms to Library Development:<br/>
        Division of Library Development<br/>
        NYS Library<br/>
        Cultural Education Center, Room 10B41<br/>
        Albany NY 12230<br/>
        Attn: Kimberly Anderson<br/></td>
    </tr>
 </c:when>
 </c:choose>
 
 </table> 
  </body>
</html>