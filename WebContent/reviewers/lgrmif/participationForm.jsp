<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
        <td colspan="2">Please select “Yes” from the dropdown menu if you are willing and 
        available to participate in the LGRMIF 
        Grant Program review for the given year (one year added to the upcoming or in 
        January YYYY current, calendar year).  If you are not 
        available to review proposals for the year, please select “No” in the space provided.</td>
      </tr>
         
      <html:form action="/updateParticipation">
      <tr>
        <td height="30" />
        <html:hidden property="id" /><html:hidden property="module" />
      </tr>
      <tr>
        <td>Grant Program</td>
        <td><html:select property="grantprogram" >
              <html:option value="lgrmif" >LGRMIF</html:option>
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
        <td>Available to Review</td>
        <td><html:select property="numaccepted" >
              <html:option value="1" >Yes</html:option>
              <html:option value="0">No</html:option>
            </html:select></td>
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
