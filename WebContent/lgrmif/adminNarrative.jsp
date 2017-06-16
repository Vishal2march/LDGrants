<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>untitled</title>
  </head>
  <body>
  
  <br/><br/>
  <table width="95%" align="center" class="boxtype" summary="for layout only" >
        <tr>
          <th><c:out value="${projNarrative.narrativeTitle}" /></th>
        </tr>
        <tr>
          <td><bean:write name="projNarrative" property="narrativeDescr" filter="false" /><br/></td>
        </tr>      
        <tr>
          <td height="20" />
        </tr>        
        <tr>   
          <td><bean:write name="projNarrative" property="narrative" filter="false" /></td>
        </tr>
    </table>
  
  </body>
</html>
