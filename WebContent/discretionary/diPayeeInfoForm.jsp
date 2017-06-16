<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%--
 * @author  Stefanie Husak
 * @version 1.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the Application        :  diPayeeInfoForm.jsp
 * Creation/Modification History  :
 *
 *     SHusak       12/24/07     Created
 *
 * Description
 * This page has instructions and links to word/pdf versions of payee
 * information form. The PI forms are NOT prefilled. Used for di/lg
--%>
<%@ page contentType="text/html;charset=windows-1252"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>untitled</title>
  </head>
  <body>  
  
  <h4>Forms</h4>
    
  <c:choose >
  <c:when test="${param.prog=='di'}">
  
  <table width="90%" align="center" class="boxtype" summary="for layout only">
    <tr>
      <td>The Payee Information Form is required for each Discretionary Grant application being
      submitted.  Please print and complete one copy of the Payee Information Form
      found below.  The form must be <b><font color="blue">signed in blue ink</font></b>.
      Mail the Payee Information Form
      with an original signature to:<br/><br/>
          CONSERVATION/PRESERVATION PROGRAM<br/>
          DIVISION OF LIBRARY DEVELOPMENT<br/>
          NEW YORK STATE LIBRARY<br/>
          10B41 CULTURAL EDUCATION CENTER<br/>
          222 MADISON AVENUE<br/>
          ALBANY, NY  12230<br/></td>
    </tr>
    <tr>
      <td height="15" />
    </tr>
    <tr>
      <td colspan="2">The following form will open in a new window.</td>
    </tr>
    <tr>
      <td><a href="docs/PayeeInfoForm.pdf" target="_blank">PDF</a> Payee Information Form</td>
    </tr>
  </table>
  
  </c:when>
  <c:when test="${param.prog=='lg'}">
  
  
  <table width="90%" align="center" class="boxtype" summary="for layout only">
    <tr>
      <td>The Payee Information Form and the Standard Data Capture Form are required for each 
      Competitive Grant application being submitted. Please print and complete one copy of 
      each form found below. The Payee Information Form must be <b><font color="blue">signed 
      in blue ink</font></b>.  Mail both 
      the Payee Information Form and the Standard Data Capture Form to the Grants 
      Administration Unit at the address below.<br/><br/>
      New York State Archives<br/>
      Grants Administration Unit<br/>
      9A81 Cultural Education Center,<br/>
      Albany, NY 12230</td>
    </tr>
    <tr>
      <td height="15" />
    </tr>
    <tr>
      <td colspan="2">The following forms will open in a new window.</td>
    </tr>
    <tr>
      <td>PI/Data Capture Form <a href="docs/lgrmif/piform.pdf" target="_blank">PDF</a></td>
    </tr>
    <tr>
      <td>PI/Data Capture Form <a href="docs/lgrmif/piform.doc" target="_blank">Microsoft Word</a></td>
    </tr>
    <tr>
      <td>PI/Data Capture Form <a href="docs/lgrmif/piform.htm" target="_blank">HTML</a></td>
    </tr>
    <tr>
      <td height="20" />
    </tr>
  </table>
  
  </c:when>  
  <c:when test="${param.prog=='lit'}">
  
  <table width="90%" align="center" class="boxtype" summary="for layout only">
    <tr>
      <td>The Payee Information Form is required in order to change or update your institution's
      data in SEDREF.  If your institution address or Library Director information from the 
      Cover Page is incorrect, submit the Payee Info Form so that NYSED may correct the information.
      Please print and complete one copy of the Payee Information Form
      found below.  The form must be signed in blue ink.  Mail the Payee Information Form
      with an original signature to the Division of Library Development.</td>
    </tr>
    <tr>
      <td height="15" />
    </tr>
    <tr>
      <td colspan="2">The following form will open in a new window.</td>
    </tr>
    <tr>
      <td><a href="docs/PayeeInfoForm.pdf" target="_blank">PDF</a> Payee Information Form</td>
    </tr>
  </table>
  
  </c:when>
  </c:choose> 
  
  </body>
</html>
