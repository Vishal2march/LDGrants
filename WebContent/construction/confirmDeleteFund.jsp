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
  
  <h4>Confirm Delete - Additional Funding Record</h4>
  
  
      <form method="POST" action="otherFundsNav.do?i=deleterecord"> 
  
      <table width="70%" summary="for layout only">
        <tr>
          <th>Delete Additional Funding Record</th>
        </tr>
        <tr>
          <td>Do you want to delete the funding record?</td>
        </tr>
        <tr>
          <td><input type="SUBMIT" value="Delete"/>
              <input type="HIDDEN" name="id" value='<c:out value="${param.fid}" />'  />
              <input type="hidden" name="m" value='<c:out value="${param.m}" />' />
              <input type="BUTTON" value="Cancel" onclick="javascript:history.go(-1);" />
          </td>
        </tr>
      </table>        
      </form>
  
    
  </body>
</html>