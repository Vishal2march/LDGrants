<%--
 * @author  Stefanie Husak
 * @version 1.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the Application        :  diMicroform.jsp
 * Creation/Modification History  :
 *
 *     SHusak       12/19/07     Created
 *
 * Description
 * This is html view of the microform guidelines form for Di apcnt. It has instructions to
 * print and scan and link to pdf version. 
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>untitled</title>
  </head>
  <body>
  
  <h4>Microform Guidelines Form</h4>
  
  <table width="90%"  align="center" class="boxtype" summary="for layout only">
    <tr>
      <th colspan="2">Discretionary Grant Microform Guidelines Agreement</th>
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
  <p><b>Instructions</b><br/>
  The Microform Guidelines Form must be printed and signed only if applicable to your project.
  Then scan the signed form and upload the form to your application as an attachment.  
  Please put 'Microform Guidelines' as the description for your uploaded attachment.<br/>
  View <a href="ApcntFunctionsServlet?i=microform" target="_blank">PDF</a> version of Microform Guidelines Form
  </p>
  
  </body>
</html>
