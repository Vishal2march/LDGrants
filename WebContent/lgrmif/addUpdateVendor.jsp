<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>addUpdateVendor</title>
  </head>
  <body>
  
  <h5>Add/Update Vendor</h5> 
    
  <html:errors />
  <html:form action="/saveLgVendor">
  <table summary="for layout only">
    <tr>
        <td>Vendor Name</td>
        <td><html:text property="name" maxlength="150"/></td>
    </tr>
    <tr>
        <td>State Contract Number</td>
        <td><html:text property="statecontractnum" maxlength="50"/></td>
    </tr>
    <tr>
        <td>Address</td>
        <td><html:text property="address" maxlength="50"/></td>
    </tr>
    <tr>
        <td>City</td>
        <td><html:text property="city" maxlength="50"/></td>
    </tr>
    <tr>
        <td>State</td>
        <td><html:text property="state" maxlength="5"/></td>
    </tr>
    <tr>
        <td>Zip Code</td>
        <td><html:text property="zipcode" maxlength="15"/></td>
    </tr>
    <tr>
        <td colspan="2"><html:submit value="Save"/>
            <html:hidden property="id"/></td>
    </tr>
  </table>
  </html:form>
  
   
  </body>
</html>