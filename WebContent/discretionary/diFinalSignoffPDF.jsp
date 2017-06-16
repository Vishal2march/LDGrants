<%--
 * @author  Stefanie Husak
 * @version 2.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the Application        :  diFinalSignoffPDF.jsp
 * Creation/Modification History  :
 *
 *     SHusak       12/20/07     Created
 *
 * Description
 * This is pdf view of the Di apcnt final signoff with instructions. 
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
  <table width="90%" align="center" summary="for layout only">
    <tr>
      <th colspan="2">Discretionary Grant Program Final Report Signoff</th>
    </tr>
    <tr>
      <td colspan="2">New York State Program for the Conservation and Preservation of Library Research Materials</td>
    </tr>
    <tr>
      <td height="15" />
    </tr>
    <tr>
      <td>Sponsoring Institution:</td>
      <td><c:out value="${thisGrant.instName}" /></td>
    </tr>
    <tr>
      <td>Project Title:</td>
      <td><c:out value="${thisGrant.title}" /></td>
    </tr>
    <tr>
      <td colspan="2">I hereby certify that all expenditures reported in the attached budget report
      are directly attributable to this project, and that the attached narrative is an accurate 
      and complete account of the project.</td>
    </tr>
    <tr>
      <td valign="bottom" width="30%" height="50">Date:<hr/></td>
      <td valign="bottom">Signed:<hr/></td>
    </tr>
    <tr>
      <th width="30%" height="50">Library/Archives Director</th>
      <td valign="bottom">Type Name:<hr/></td>
    </tr>    
  </table>
  
  
  <br/><br/>
  <p>Instructions<br/>The Final Signoff Form must be printed and signed at the conclusion of your project.
    Then scan the signed form and upload the form to your application as an attachment.  
    Please put "Final Signoff" as the description for your attachment.  
  </p>
  </font>
  
  
  </body>
</html>

</pd4ml:transform>