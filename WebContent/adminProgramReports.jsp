<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>untitled</title>
  </head>
  <body>   
  
  <form method="POST" target="_blank" action="litReports.do">
  <table width="80%" align="center" summary="for layout only" border="1">
    <tr>
      <th>Reports for all Online Grant System programs</th>
    </tr>    
    <tr>
      <td><input type="RADIO" name="item" value="allawardfy" checked="checked">Awards for All Programs by Fiscal Year
          <br/> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
          Fiscal year <select name="fyallaw" >
           <c:forEach var="row" items="${dropDownList}">
              <option value="<c:out value='${row.id}' />">
                <c:out value="${row.description}" /></option>
          </c:forEach>              
          </select></td>
    </tr>
    <tr>
      <td><input type="RADIO" name="item" value="allawardco">Awards for All Programs by Fiscal Year and County
          <br/> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
          Fiscal year <select name="fyawco" >
          <option value="0">All Years</option>
           <c:forEach var="row" items="${dropDownList}">
              <option value="<c:out value='${row.id}' />">
                <c:out value="${row.description}" /></option>
          </c:forEach>              
          </select>
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
          County <select name="county" >
           <c:forEach var="row" items="${dropDownCounties}">
              <option value="<c:out value='${row.id}' />">
                <c:out value="${row.description}" /></option>
          </c:forEach>              
          </select></td>
    </tr>    
    <tr>
      <td align="center"><input type="SUBMIT" value="View Report" /></td>
    </tr>
  </table>
  </form>
    
    
  </body>
</html>
