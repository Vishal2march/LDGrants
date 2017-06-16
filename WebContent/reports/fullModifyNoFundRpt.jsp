<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>Full, Modified , No Fund</title>
    <link href="css/StateAid.css" rel="stylesheet" media="screen" type="text/css" />
  </head>
  <body>
  
  <table summary="for layout only">
    <tr>
        <TH colspan="3">Number of LGRMIF applications by recommendation for fiscal year 
        <c:out value="${fybean.year}" /></TH>
    </tr>
    <tr>
        <td>Full Fund</td>
        <td>Modified Fund</td>
        <td>No Fund</td>
    </tr>
    
    <tr>
        <td><c:out value="${ResultBean.fullfund}" /></td>
        <td><c:out value="${ResultBean.modifyfund}" /></td>
        <td><c:out value="${ResultBean.nofund}" /></td>
    </tr>  
  </table>
  
    
  </body>
</html>
