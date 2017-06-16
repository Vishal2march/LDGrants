<%--
 * @author  Stefanie Husak
 * @version 2.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the Application        :  pbExpenseTotals.jsp
 * Creation/Modification History  :
 *
 *     SH      1/21/09     Created
 *
 * Description
 * This page allows the lit apnt and lit admin to view budget totals for years 1 and 2
 * and read narrative for the corresponding budget tab. Note: cannot put admin link back
 * to checkstatus on this page b/c based on param.p, the link will be available to apcnts too.
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>untitled</title>
  </head>
  <body>
  
  <table width="95%" align="center" summary="for layout only">
    <tr>
      <td colspan="4" ><b>Totals for Year 1</b></td>
    </tr>
    <tr>
      <td width="25%">Amt Requested</td>
      <td width="25%">Amt Approved</td>
      <td width="25%">Actual Expense</td>
      <td width="25%">&nbsp;</td>
    </tr>
    <tr>
      <td><fmt:formatNumber type="currency" value='${totalsMap["1"].totAmtReq}' maxFractionDigits="0" /></td> 
      <td><fmt:formatNumber value='${totalsMap["1"].totAmtAppr}' type="currency" maxFractionDigits="0" /></td>
      <td><fmt:formatNumber value='${totalsMap["1"].totExpSub}' type="currency" maxFractionDigits="0" /></td>
      <%--<td><fmt:formatNumber value='${totalsMap["1"].totExpAppr}' type="currency" maxFractionDigits="0" /></td>--%>
    </tr>
    <tr>
      <td height="15" colspan="4"><hr/></td>
    </tr>
    <tr>
      <td colspan="4"><b>Totals for Year 2</b></td>
    </tr>
    <tr>   
      <td>Amt Requested</td>
      <td>Amt Approved</td>
      <td>Actual Expense</td>
      <%--<td>Exp Approved</td>--%>
    </tr>
    <tr>
      <td><fmt:formatNumber value='${totalsMap["2"].totAmtReq}' type="currency" maxFractionDigits="0" /></td> 
      <td><fmt:formatNumber value='${totalsMap["2"].totAmtAppr}' type="currency" maxFractionDigits="0" /></td>
      <td><fmt:formatNumber value='${totalsMap["2"].totExpSub}' type="currency" maxFractionDigits="0" /></td>
      <%--<td><fmt:formatNumber value='${totalsMap["2"].totExpAppr}' type="currency" maxFractionDigits="0" /></td>--%>
    </tr>
    
    <c:if test="${thisGrant.fycode>13}"><%--starting FY2013-14; year 3 --%>  
        <tr>
          <td height="15" colspan="4"><hr/></td>
        </tr>
        <tr>
          <td colspan="4"><b>Totals for Year 3</b></td>
        </tr>
        <tr>   
          <td>Amt Requested</td>
          <td>Amt Approved</td>
          <td>Actual Expense</td>
        </tr>
        <tr>
          <td><fmt:formatNumber value='${totalsMap["3"].totAmtReq}' type="currency" maxFractionDigits="0" /></td> 
          <td><fmt:formatNumber value='${totalsMap["3"].totAmtAppr}' type="currency" maxFractionDigits="0" /></td>
          <td><fmt:formatNumber value='${totalsMap["3"].totExpSub}' type="currency" maxFractionDigits="0" /></td>
        </tr>    
    </c:if>
  </table>    
  <hr/><br/><br/>
 
 
 <html:form action="/flSaveNarrative">  
 <table width="95%" align="center" class="boxtype" summary="for layout only" >
    <tr>
      <th><c:out value="${projNarrative.narrativeTitle}" /></th>
    </tr>
    <tr>
      <td><c:out value="${projNarrative.narrativeDescr}" /><br/></td>
    </tr>      
    <tr>
      <td height="20" />
    </tr>           
    <tr>   
      <td><c:out value="${projNarrative.narrative}" /></td>
    </tr>
  </table>
  </form>
    
  </body>
</html>
