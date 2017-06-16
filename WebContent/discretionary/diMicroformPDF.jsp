<%--
 * @author  Stefanie Husak
 * @version 1.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the Application        :  diMicroformPDF.jsp
 * Creation/Modification History  :
 *
 *     SHusak       12/19/07     Created
 *
 * Description
 * This is pdf version of Di apcnt microform guidelines form with instructions. 
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/pd4ml.tld" prefix="pd4ml" %>
<%@ page contentType="text/html;charset=ISO8859_1"%>
<pd4ml:transform screenWidth="450" pageInsets="20,20,20,20,points" encoding="default" pageFormat="LETTER" pageOrientation="portrait" enableImageSplit="false">
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>untitled</title>
  </head>
  <body>

  <font size="1">
  <table width="90%"  align="center" class="boxtype" summary="for layout only">
    <tr>
      <th colspan="2">Discretionary Grant Microform Guidelines Agreement Form</th>
    </tr>
    <tr>
      <td align="center" colspan="2">New York State Program for the Conservation and Preservation of Library Research Materials</td>
    </tr>
    <tr>
      <td height="15" />
    </tr>
    <tr>
      <td colspan="2">The applicant institution, <c:out value="${thisGrant.instName}" /> and vendor ___________________________________________ agree to perform all microform work described
      in the attached application for <c:out value="${thisGrant.title}" /> according to all 
      <b>ANSI/AIIM</b> standards and the Research Library Group guidelines in the RLG Microfilming
      Handbook reviewed in the Discretionary Grant Microform Guidelines.</td>
    </tr>
    <tr>
      <td height="15" />
    </tr>
    <tr>
      <td height="50" valign="bottom" width="30%">Date:<hr/></td>
      <td valign="bottom">Signed:<hr/></td>
    </tr>
    <tr>
      <th>Library/Archives Director</th>
      <td height="50" valign="bottom">Type Name:<hr/></td>
    </tr>
    
    <tr>
      <td height="15" />
    </tr>
    <tr>
      <td height="50" valign="bottom" width="30%">Date:<hr/></td>
      <td valign="bottom">Signed:<hr/></td>
    </tr>
    <tr>
      <th>Authorized Vendor agent</th>
      <td height="50" valign="bottom">Type Name:<hr/></td>
    </tr>   
  </table>
  
  
  <br/><br/>
  <p>Instructions<br/>
      The Microform Guidelines Form must be printed and signed only if applicable to your project.
      Then scan the signed form and upload the form to your application as an attachment.  
      Please put 'Microform Guidelines' as the description for your uploaded attachment.</p>
  </font>
  
  </body>
</html>

</pd4ml:transform>