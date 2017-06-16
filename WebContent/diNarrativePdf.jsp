<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/pd4ml.tld" prefix="pd4ml" %>
<%@ page contentType="text/html;charset=ISO8859_1"%>
<pd4ml:transform screenWidth="650" encoding="default" pageFormat="LETTER" pageOrientation="portrait" enableImageSplit="false">
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>Discretionary Grant Narratives</title>
  </head>
  <body>
  
  <font size="1">
  <table align="center" summary="for layout only">
    <tr>
      <th colspan="2">Conservation/Preservation Program - Discretionary Grants
          <br/>Project Description
      </th>
    </tr>
    <tr>
      <td>Project Number</td>
      <td>03<fmt:formatNumber minIntegerDigits="2" value="${thisGrant.fccode}" />
          -<fmt:formatNumber value="${thisGrant.fycode}" minIntegerDigits="2" />
          -<fmt:formatNumber value="${thisGrant.projseqnum}" pattern="####" minIntegerDigits="4" />
      </td>
    </tr>
    <tr>
      <td>Project Title</td>
      <td><c:out value="${thisGrant.title}" /></td>
    </tr>
  </table>
  </font>
  <br/><br/>
  
  
  <font size="1">
  <table align="center" width="95%" summary="for layout only"> 
    <tr>
      <th bgcolor="Silver">Size of Institution's operation</th>
    </tr>
    <tr>  
      <td align="left"><bean:write name="projNarr19" property="narrative" filter="false"/></td>
    </tr>
    <tr>
      <td height="30"></td>
    </tr>  
    <tr>
      <th bgcolor="Silver">Agency's total collection of library research materials</th>
    </tr>
    <tr>
      <td align="left"><bean:write name="projNarr20" property="narrative" filter="false"/></td>
    </tr>
    <tr>
      <td height="30"></td>
    </tr>  
    <tr>
      <th bgcolor="Silver">Institutional conservation/preservation activities</th>
    </tr>
    <tr>
      <td align="left"><bean:write name="projNarr3" property="narrative" filter="false"/></td>
    </tr>
    <tr>
      <td height="30"></td>
    </tr>  
    <tr>
      <th bgcolor="Silver">Environmental conditions in which preserved materials will be stored</th>
    </tr>
    <tr>
      <td align="left"><bean:write name="projNarr16" property="narrative" filter="false"/></td>
    </tr>
    <tr>
      <td height="30"></td>
    </tr>  
    <tr>
      <th bgcolor="Silver">Preparations for disasters</th>
    </tr>
    <tr>
      <td align="left"><bean:write name="projNarr21" property="narrative" filter="false"/></td>
    </tr>
    <tr>
      <td height="30"></td>
    </tr>  
    <tr>
      <th bgcolor="Silver">Security arrangements for protecting the collections</th>
    </tr>
    <tr>
      <td align="left"><bean:write name="projNarr22" property="narrative" filter="false"/></td>
    </tr>
    <tr>
      <td height="30"></td>
    </tr>  
    <tr>
      <th bgcolor="Silver">Participation in cooperative, regional or statewide conservation/preservation activities</th>
    </tr>
    <tr>
      <td align="left"><bean:write name="projNarr23" property="narrative" filter="false"/></td>
    </tr>
    <tr>
      <td height="30"></td>
    </tr>  
    <tr>
      <th bgcolor="Silver">Access policies and practices of the institution</th>
    </tr>
    <tr>
      <td align="left"><bean:write name="projNarr24" property="narrative" filter="false"/></td>
    </tr>
    <tr>
      <td height="30"></td>
    </tr>  
    <tr>
      <th bgcolor="Silver">Cataloging or other forms of bibliographic control</th>
    </tr>
    <tr>
      <td align="left"><bean:write name="projNarr10" property="narrative" filter="false"/></td>
    </tr>
    <tr>
      <td height="30"></td>
    </tr>  
    <tr>
      <th bgcolor="Silver">Ownership of materials</th>
    </tr>
    <tr>
      <td align="left"><bean:write name="projNarr25" property="narrative" filter="false"/></td>
    </tr>
    <tr>
      <td height="30"></td>
    </tr>  
    <tr>
      <th bgcolor="Silver">Description of materials to be preserved with grant funds</th>
    </tr>
    <tr>
      <td align="left"><bean:write name="projNarr6" property="narrative" filter="false"/></td>
    </tr>
    <tr>
      <td height="30"></td>
    </tr>  
    <tr>
      <th bgcolor="Silver">Significance of materials for research</th>
    </tr>
    <tr>
      <td align="left"><bean:write name="projNarr7" property="narrative" filter="false"/></td>
    </tr>
    <tr>
      <td height="30"></td>
    </tr>  
    <tr>
      <th bgcolor="Silver">Timetable for the project</th>
    </tr>
    <tr>
      <td align="left"><bean:write name="projNarr12" property="narrative" filter="false"/></td>
    </tr>
    <tr>
      <td height="30"></td>
    </tr>  
    <tr>
      <th bgcolor="Silver">Conservation/preservation activities to be carried out during the project</th>
    </tr>
    <tr>
      <td align="left"><bean:write name="projNarr13" property="narrative" filter="false"/></td>
    </tr>
    <tr>
      <td height="30"></td>
    </tr>  
    <tr>
      <th bgcolor="Silver">Personnel and vendors involved in the project</th>
    </tr>
    <tr>
      <td align="left"><bean:write name="projNarr15" property="narrative" filter="false"/></td>
    </tr>
    <tr>
      <td height="30"></td>
    </tr>  
    <tr>
      <th bgcolor="Silver">Contributions of staff time by existing institutional staff</th>
    </tr>
    <tr>
      <td align="left"><bean:write name="projNarr17" property="narrative" filter="false"/></td>
    </tr>
    <tr>
      <td height="30"></td>
    </tr>  
    <tr>
      <th bgcolor="Silver">Financial contribution towards overall costs of the project</th>
    </tr>
    <tr>
      <td align="left"><bean:write name="projNarr18" property="narrative" filter="false"/></td>
    </tr>
    <tr>
      <td height="30"></td>
    </tr>  
    <tr>
      <th bgcolor="Silver">Need for the proposed Training</th>
    </tr>
    <tr>
      <td align="left"><bean:write name="projNarr66" property="narrative" filter="false"/></td>
    </tr>
    <tr>
      <td height="30"></td>
    </tr>  
    <tr>
      <th bgcolor="Silver">Training objectives</th>
    </tr>
    <tr>
      <td align="left"><bean:write name="projNarr67" property="narrative" filter="false"/></td>
    </tr>
    <tr>
      <td height="30"></td>
    </tr>  
    <tr>
      <th bgcolor="Silver">Publicity</th>
    </tr>
    <tr>
      <td align="left"><bean:write name="projNarr68" property="narrative" filter="false"/></td>
    </tr>
    <tr>
      <td height="30"></td>
    </tr>  
    <tr>
      <th bgcolor="Silver">Information dissemination</th>
    </tr>
    <tr>
      <td align="left"><bean:write name="projNarr34" property="narrative" filter="false"/></td>
    </tr>
    <tr>
      <td height="30"></td>
    </tr>  
    <tr>
      <th bgcolor="Silver">Final Report Narrative</th>
    </tr>
    <tr>
      <td align="left"><bean:write name="projNarr2" property="narrative" filter="false"/></td>
    </tr>
  </table>
  </font>
  
  
  </body>
</html>


</pd4ml:transform>