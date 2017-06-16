<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Construction Narratives</title>
    <link href="css/StateAid.css" rel="stylesheet" media="screen" />
  </head>
  <body>
  
  <br/><br/>
  <table align="center" summary="for layout only">
    <tr>
      <th colspan="2">Public Library Construction Program
          <br/>Project Narratives
      </th>
    </tr>
    <tr>
      <td>Project Number</td>
      <td>
          03<fmt:formatNumber minIntegerDigits="2" value="${thisGrant.fccode}" />
          -<fmt:formatNumber value="${thisGrant.fycode}" minIntegerDigits="2" />
          -<fmt:formatNumber value="${thisGrant.projseqnum}" pattern="####" minIntegerDigits="4" />
      </td>
    </tr>
    <tr>
      <td>Institution</td>
      <td><c:out value="${thisGrant.instName}" /></td>
    </tr>
    <tr>
      <td>Project Title</td>
      <td><c:out value="${thisGrant.title}" /></td>
    </tr>
  </table>
  <br/><br/>
  
  
   <table align="center" width="95%" summary="for layout only"> 
   <tr>
      <th bgcolor="Silver">Project Abstract</th>
    </tr>
    <tr>  
      <td align="left"><bean:write name="projNarr95" property="narrative" filter="false"/></td>
    </tr>
    <tr>
      <td height="30"></td>
    </tr>  
    <tr>
      <th bgcolor="Silver">Description of Project</th>
    </tr>
    <tr>  
      <td align="left"><bean:write name="projNarr94" property="narrative" filter="false"/></td>
    </tr>
    <tr>
      <td height="30"></td>
    </tr>  
    <tr>
      <th bgcolor="Silver">Impact of Project</th>
    </tr>
    <tr>
      <td align="left"><bean:write name="projNarr91" property="narrative" filter="false"/></td>
    </tr>
    <tr>
      <td height="30"></td>
    </tr>  
    <tr>
      <th bgcolor="Silver">Timetable</th>
    </tr>
    <tr>
      <td align="left"><bean:write name="projNarr12" property="narrative" filter="false"/></td>
    </tr>
    <tr>
      <td height="30"></td>
    </tr>  
    <tr>
      <th bgcolor="Silver">Budget Narrative</th>
    </tr>
    <tr>
      <td align="left"><bean:write name="projNarr93" property="narrative" filter="false"/></td>
    </tr>
    <tr>
      <td height="30"></td>
    </tr>  
    </table>
  
  
  </body>
</html>