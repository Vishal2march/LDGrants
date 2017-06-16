<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title></title>
  </head>
  <body>
  
  <h4>Edit a PLS Allocation Record</h4>
  
  
  <html:form action="/saveCnAdminAlloc">
  <table width="90%" align="center" class="boxtype" summary="for layout only">
    <tr>      
        <td>Fiscal Year</td>
        <td>
          <html:select name="allocBean" property="fycode">
            <html:optionsCollection name="allocBean" property="allPossibleYears" value="id" label="description"/>
          </html:select>     
        </td>      
    </tr>
    <tr>
        <td>System Name</td>
        <td>
          <html:select name="allocBean" property="librarySystemMapId">
            <html:optionsCollection name="allocBean" property="allPossibleSystems" value="id" label="description"/>
          </html:select>     
        </td>   
    </tr>
    <tr>
        <td>Initial Allocation</td>
        <td><html:text property="initialAllocStr"/></td>
    </tr>
    <tr>
        <td>Additional Allocation</td>
        <td><html:text property="additionalAllocStr"/></td>
    </tr>
    <tr>
        <td colspan="2">
            <html:hidden property="systemYearDetailId"/>
            <html:hidden property="fccode"/>
            <html:submit value="Save"/>
        </td>
    </tr> 
  </table>
  </html:form>
  
  </body>
</html>