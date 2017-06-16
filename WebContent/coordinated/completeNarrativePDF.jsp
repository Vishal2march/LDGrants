<%--
 * @author  Stefanie Husak
 * @version 2.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the Application        :  completeNarrativePDF.jsp
 * Creation/Modification History  :
 *
 *     SH       7/25/07   Created
 *
 * Description
 * This is a PDF of all the narratives for the CO grant, including summary description
 * and final narrative.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="/WEB-INF/pd4ml.tld" prefix="pd4ml" %>
<pd4ml:transform screenWidth="750" pageInsets="20,20,20,20,points" encoding="default" pageFormat="LETTER" pageOrientation="portrait" enableImageSplit="false">
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>untitled</title>
  </head>
  <body>
  
  <font size="1">
  <table width="90%" align="center" summary="for layout only">
    <tr>
      <th colspan="2">Conservation Preservation Program Coordinated Grants - Project Description</th>
    </tr>
    <tr>
      <td>Project Number</td>
      <td>
          03<fmt:formatNumber minIntegerDigits="2" pattern="##" type="number" value="${thisGrant.fccode}" />
          -<fmt:formatNumber value="${thisGrant.fycode}" minIntegerDigits="2" />
          -<fmt:formatNumber value="${thisGrant.projseqnum}" minIntegerDigits="4" pattern="####" />
      </td>
    </tr>
    <tr>
      <td>Contract Number</td>
      <td><c:out value="${thisGrant.contractNum}" /></td>
    </tr>
    <tr>
      <td>Project Title</td>
      <td><c:out value="${thisGrant.title}" /></td>
    </tr>
  </table>
  </font>
  <br/><br/>
  
  
  <font size="1">
  <table align="center" width="90%" summary="for layout only"> 
    <tr>
      <th bgcolor="Silver">Summary Description</th>
    </tr>
    <tr>
      <td align="left"><bean:write name="projNarr1" property="narrative" filter="false" /></td>
    </tr>
     <tr>
      <td height="20"></td>
    </tr>
    <tr>
      <th bgcolor="Silver">Description of Materials to be preserved</th>
    </tr>
    <tr>  
      <td align="left"><bean:write name="projNarr6" property="narrative" filter="false"/></td>
    </tr>
    <tr>
      <td height="20"></td>
    </tr>  
    <tr>
      <th bgcolor="Silver">Significance of Materials for research</th>
    </tr>
    <tr>
      <td align="left"><bean:write name="projNarr7" property="narrative" filter="false"/></td>
    </tr>
    <tr>
      <td height="20"></td>
    </tr>  
    <tr>
      <th bgcolor="Silver">Involvement of Participating Libraries</th>
    </tr>
    <tr>
      <td align="left"><bean:write name="projNarr8" property="narrative" filter="false"/></td>
    </tr>
    <tr>
      <td height="20"></td>
    </tr>
    <tr>
      <th bgcolor="Silver">Coordination of Project Activities</th>
    </tr>
    <tr>
      <td align="left"><bean:write name="projNarr9" property="narrative" filter="false"/></td>
    </tr>
    <tr>
      <td height="20"></td>
    </tr>
    <tr>
      <th bgcolor="Silver">Cataloging or Bibliographic Control</th>
    </tr>
    <tr>
      <td align="left"><bean:write name="projNarr10" property="narrative" filter="false"/></td>
    </tr>
    <tr>
      <td height="20"></td>
    </tr>
    <tr>
      <th bgcolor="Silver">Use of Online Databases</th>
    </tr>
    <tr>
      <td align="left"><bean:write name="projNarr11" property="narrative" filter="false"/></td>
    </tr>
    <tr>
      <td height="20"></td>
    </tr>
    <tr>
      <th bgcolor="Silver">Project Timetable</th>
    </tr>
    <tr>
      <td align="left"><bean:write name="projNarr12" property="narrative" filter="false"/></td>
    </tr>
    <tr>
      <td height="20"></td>
    </tr>
    <tr>
      <th bgcolor="Silver">Conservation/Preservation Activities</th>
    </tr>
    <tr>
      <td align="left"><bean:write name="projNarr13" property="narrative" filter="false"/></td>
    </tr>
    <tr>
      <td height="20"></td>
    </tr>
    <tr>
      <th bgcolor="Silver">Special Equipment</th>
    </tr>
    <tr>
      <td align="left"><bean:write name="projNarr14" property="narrative" filter="false"/></td>
    </tr>
    <tr>
      <td height="20"></td>
    </tr>
    <tr>
      <th bgcolor="Silver">Personnel and Vendors</th>
    </tr>
    <tr>
      <td align="left"><bean:write name="projNarr15" property="narrative" filter="false"/></td>
    </tr>
    <tr>
      <td height="20"></td>
    </tr>
    <tr>
      <th bgcolor="Silver">Environmental Conditions</th>
    </tr>
    <tr>
      <td align="left"><bean:write name="projNarr16" property="narrative" filter="false"/></td>
    </tr>
    <tr>
      <td height="20"></td>
    </tr>
    <tr>
      <th bgcolor="Silver">Staff Contributions</th>
    </tr>
    <tr>
      <td align="left"><bean:write name="projNarr17" property="narrative" filter="false"/></td>
    </tr>
    <tr>
      <td height="20"></td>
    </tr>
    <tr>
      <th bgcolor="Silver">Financial Contributions</th>
    </tr>
    <tr>
      <td align="left"><bean:write name="projNarr18" property="narrative" filter="false"/></td>
    </tr>
    <tr>
      <td height="20"></td>
    </tr>
    <tr>
      <th bgcolor="Silver">Need for the proposed Training</th>
    </tr>
    <tr>
      <td align="left"><bean:write name="projNarr66" property="narrative" filter="false"/></td>
    </tr>
    <tr>
      <td height="20"></td>
    </tr>  
    <tr>
      <th bgcolor="Silver">Training objectives</th>
    </tr>
    <tr>
      <td align="left"><bean:write name="projNarr67" property="narrative" filter="false"/></td>
    </tr>
    <tr>
      <td height="20"></td>
    </tr>  
    <tr>
      <th bgcolor="Silver">Publicity</th>
    </tr>
    <tr>
      <td align="left"><bean:write name="projNarr68" property="narrative" filter="false"/></td>
    </tr>
    <tr>
      <td height="20"></td>
    </tr>  
    <tr>
      <th bgcolor="Silver">Information dissemination</th>
    </tr>
    <tr>
      <td align="left"><bean:write name="projNarr34" property="narrative" filter="false"/></td>
    </tr>
    <tr>
      <td height="20"></td>
    </tr>  
    
    <tr>
      <th bgcolor="Silver">Final Report</th>
    </tr>
    <tr>
      <td align="left"><bean:write name="projNarr2" property="narrative" filter="false" /></td>
    </tr>
  </table>
  </font>  
  
  
  </body>
</html>
</pd4ml:transform>