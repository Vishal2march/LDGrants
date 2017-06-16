<%--
* @author  Stefanie Husak
 * @version 2.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the Application        :  revParticipationForm.jsp
 * Creation/Modification History  :
 *
 *     SH       7/30/07     Created
 *
 * Description
 * This page allows the reviewer to choose how many grants they want to
 * review for a year.  Used for co/di reviewers.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>untitled</title>
  </head>
  <body>
     
  <h4>Reviewer Participation Form</h4>      
    
    <html:errors />
    <table align="center" width="80%" summary="for layout only"> 
      <tr>
        <td colspan="2">If you would like to participate in the Conservation and Preservation Grant Review program
        for the given fiscal year, please indicate the maximum number of grants proposals you are willing to
        review. If you are not able to review proposals for the year, please enter a zero (0) in the
        space provided.</td>
      </tr>
         
      <html:form action="/updateParticipation">
      <tr>
        <td height="30" />
        <html:hidden property="id" /><html:hidden property="module" />
      </tr>
      <tr>
        <td>Grant Program</td>
        <td><html:select property="grantprogram" >
              <html:option value="coordinated" >Coordinated</html:option>
              <html:option value="discretionary" >Discretionary</html:option>
            </html:select>
        </td>
      </tr>
      <tr>
        <td>Fiscal Year</td>
        <td><html:select property="fycode">
                <html:optionsCollection name="dropDownList" value="id" label="description" />       
            </html:select>
          </td>
      </tr>    
      <tr>
        <td>Number of Proposals to Review</td>
        <td><html:text property="numaccepted" /></td>
      </tr>    
      <tr>
        <td>Brief Comments/Remarks (optional)</td>
        <td><html:textarea property="descrip" rows="4"/></td>
      </tr>
      <tr>
        <td colspan="2"><html:submit value="Submit" /></td>
      </tr>
      </html:form>
    </table>
  
  </body>
</html>
