<%--
 * @author  Stefanie Husak
 * Development Environment        :  JDeveloper 10g
 * Name of the Application        :  liViewStatistics.jsp
 * Description:
 * This is admin read-only view of statistics page for al/fl admin.
 * Note: starting fy2013-14 FL has separate statistics form.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>untitled</title>
  </head>
  <body>
  
  <br/>
  <table width="95%" align="center" class="boxtype" summary="for layout only">
    <tr>
      <th colspan="2"><c:if test="${thisGrant.fccode==40}" >Adult</c:if>
      <c:if test="${thisGrant.fccode==42}" >Family</c:if>
      Literacy Library Services<br/>Project Statistics</th>
    </tr>
    <tr>
      <td><b>Project Number</b></td>
      <td>03<fmt:formatNumber minIntegerDigits="2" pattern="##" type="number" value="${thisGrant.fccode}" />
          -<fmt:formatNumber value="${thisGrant.fycode}" minIntegerDigits="2" />
          -<fmt:formatNumber  minIntegerDigits="4" pattern="####" value="${thisGrant.projseqnum}" /></td>
    </tr> 
    <tr>
      <td><b>Institution:</b></td>
      <td><c:out value="${thisGrant.instName}" /></td>
    </tr>
  </table><br/>
  
  
  <table width="95%" align="center" class="boxtype" summary="for layout only">
    <tr>
      <td></td>
      <td><b>Year 1</b></td><td><b>Year 2</b></td><td><b>Year 3</b></td>
      <td><b>Total</b></td>
    </tr>
    <tr>
      <td>A.	Number of participating sites</td>
      <td><c:out value="${StatBean.sites}" /></td>
      <td><c:out value="${StatBean.sites2}" /></td>
      <td><c:out value="${StatBean.sites3}" /></td>
      <td><c:out value="${StatBean.sites + StatBean.sites2 + StatBean.sites3}"/></td>
    </tr>
    <tr>
      <td height="20" />
    </tr>
   <%-- removed per KBALSEN 7/2/15 <tr>
      <td>B.	Hours of service per week</td>
      <td><c:out value="${StatBean.hours}" /></td>
      <td><c:out value="${StatBean.hours2}" /></td>
      <td><c:out value="${StatBean.hours3}" /></td>
      <td><c:out value="${StatBean.hours + StatBean.hours2 + StatBean.hours3}"/></td>
    </tr>
    <tr>
      <td height="20" />
    </tr>--%>
    <tr>
      <td>B.	Total number of users served</td>
      <td><c:out value="${StatBean.users}" /></td>
      <td><c:out value="${StatBean.users2}" /></td>
      <td><c:out value="${StatBean.users3}" /></td>
      <td><c:out value="${StatBean.users + StatBean.users2 + StatBean.users3}"/> </td>
    </tr>
    <tr>
      <td height="20" />
    </tr>
    <tr>
      <td>C.	Number of workshops/programs</td>
      <td><c:out value="${StatBean.programs}" /></td>
      <td><c:out value="${StatBean.programs2}" /></td>
      <td><c:out value="${StatBean.programs3}" /></td>
      <td><c:out value="${StatBean.programs + StatBean.programs2 + StatBean.programs3}"/></td>
    </tr>
    <tr>
      <td height="20" />
    </tr>
    <tr>
      <td>D.	Total number of workshop/program participants</td>
      <td><c:out value="${StatBean.participants}" /></td>
      <td><c:out value="${StatBean.participants2}" /></td>
     <td><c:out value="${StatBean.participants3}" /></td>
      <td><c:out value="${StatBean.participants + StatBean.participants2 + StatBean.participants3}"/></td>
    </tr>
    <tr>
      <td height="20" />
    </tr>
    <tr>
      <td>E.	Total number of materials circulated</td>
      <td><c:out value="${StatBean.circulate}" /></td>
      <td><c:out value="${StatBean.circulate2}" /></td>
      <td><c:out value="${StatBean.circulate3}" /></td>
      <td><c:out value="${StatBean.circulate + StatBean.circulate2 + StatBean.circulate3}"/></td>
    </tr>
    <tr>
      <td height="20" />
    </tr>
    <tr>
      <td>F.	Total number of materials distributed</td>
      <td><c:out value="${StatBean.distribute}" /></td>
      <td><c:out value="${StatBean.distribute2}" /></td>
      <td><c:out value="${StatBean.distribute3}" /></td>
      <td><c:out value="${StatBean.distribute + StatBean.distribute2 + StatBean.distribute3}"/></td>
    </tr>
  </table>
  
  </body>
</html>
