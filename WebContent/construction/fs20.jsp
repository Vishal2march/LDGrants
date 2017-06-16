<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>untitled</title>
  </head>
  <body>
  
  <h4>FS-10 Form</h4>
  
  <table width="90%" align="center" class="boxtype" summary="for layout only">
    <tr>
      <td><font color="red">WARNING:</font> You must fill out your Application Form first before
      printing the FS-10 form. The Project Manager name and contact information from your
      Application Form will be transferred to the FS-10 form.</td>
    </tr>
    <tr>
        <td height="20"/>
    </tr>
    
    <tr>
        <td>FS-10 Budget Summary forms are essential for payment of approved project 
        amounts.  Three FS-10 Budget Summary forms with original signatures in <b>blue 
        ink</b> must be submitted to the library system, who will batch the FS-10s from 
        all approved applications and send them to Kimberly Anderson at the Division 
        of Library Development/New York State Library for each application.  Each page 
        of the form must be on a single sheet of paper, not back-to-back.<br/><br/>
        <b>NOTE:</b> FS-10 forms are submitted with their category code amounts left 
        blank, as these code amounts will be filled in at the Division of 
        Library Development once a final award amount has been determined.  Proposed 
        budget code amounts are completed online by the applicant.        
        </td>
    </tr>
    <tr>
        <td height="20"/>
    </tr>
    <c:choose>
    <c:when test="${param.p!=null && param.p=='rev'}">
        <tr>
            <td>The Public Library System will mail 3 sets of the
            FS-10 form, and 1 Payee Form to Library Development.  All Forms must have
            original signatures in <b>blue ink</b>.<br/>
            Division of Library Development<br/>
            NYS Library<br/>
            Cultural Education Center, Room 10B41<br/>
            Albany NY 12230<br/>
            Attn: Kimberly Anderson<br/></td>
        </tr>
        <tr>
            <td height="20"/>
        </tr>
    </c:when>
    </c:choose>    
   
   <%-- rmvd 5/15/13 per MLT<tr> 
      <td><a href="FsFormServlet?i=fs20&fy=0" target="_blank">HTML FS-20 form</a> <br/>
          <a href="FsFormServlet?i=fs20pdf&fy=0" target="_blank">PDF FS-20 form</a> &nbsp;&nbsp;&nbsp;&nbsp;(PDF format preferred)</td>
    </tr>  --%>
    <tr>  
      <td><a href="FsFormServlet?i=fs10&fy=0" target="_blank">HTML FS-10 form</a> <br/>
          <a href="FsFormServlet?i=fs10pdf&fy=0" target="_blank">PDF FS-10 form</a> &nbsp;&nbsp;&nbsp;&nbsp;(PDF format preferred)</td>
    </tr>
    <tr>
      <td height="30" />
    </tr>    
  </table>
  
  
  </body>  
 </html> 