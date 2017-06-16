<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Public Library Construction Reports</title>
  </head>
  <body>
  
  <h4>DASNY Construction Program Reports</h4>
  
  
  <form method="POST" action="dasnyReportNav.do" target="_blank">
  <table border="1" class="graygrid" align="center" width="80%" summary="for layout only">
    <tr>
      <th>Construction grant reports will open in a new HTML window</th>
    </tr>    
    <tr>
        <td><b>***Select a Fiscal Year:</b><br/>
        Fiscal year <select name="fy" >
                              <c:forEach var="row" items="${dropDownList}">
                                  <option value="<c:out value='${row.id}' />">
                                    <c:out value="${row.description}" /></option>
                              </c:forEach>              
                              </select></td>
    </tr>
    <tr>
        <td height="20"/>
    </tr>    
    <tr>
        <td><b>***Select a Report:</b></td>
    </tr>    
   <tr>
      <td><input type="RADIO" name="i" value="systemalloc"/>
          System amount allocated, amount spent, for fiscal year</td>
    </tr>  
    <tr>
      <td><input type="RADIO" name="i" value="dasnyawardbysystem" checked="checked"/>
          LD approved applications with project cost, award amount for fiscal year</td>
    </tr>
    <tr>
        <td><input type="radio" name="i" value="dasnyreviewlist"/>
            Applications approved by DASNY</td>
    </tr>
           
    <tr>
        <td align="center"><input type="submit" value="View Report"/></td>
    </tr>
  </table>
  </form>
  
  </body>
</html>