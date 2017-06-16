<%--
 * @author  Stefanie Husak
 * @version 1.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the Application        :  diFinalSignoff.jsp
 * Creation/Modification History  :
 *
 *     SHusak       12/20/07     Created
 *
 * Description
 * This is html view of the final signoff for Di apcnt. Has instructions and link to
 * pdf version of the form. 
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>untitled</title>
  </head>
  <body>
  
  <h4>Final Report Signoff</h4>
  
  <table width="90%" align="center" class="boxtype" summary="for layout only">
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
  <p><b>Instructions</b><br/>
    The Final Signoff Form must be printed and signed at the conclusion of your project.
    Then scan the signed form and upload the form to your application as an attachment.  
    Please put "Final Signoff" as the description for your attachment.<br/>
    View <a href="ApcntFunctionsServlet?i=finalsignoff" target="_blank">PDF</a> version of Final Signoff Form
  </p>
  
  </body>
</html>
