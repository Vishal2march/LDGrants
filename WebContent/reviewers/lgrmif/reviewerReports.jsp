<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title></title>
  </head>
  <body>
  
  <h5>Reviewer Reports</h5>
  
  <form method="POST" action="lgReports.do" target="_blank">
  <table border="1" class="graygrid" width="70%" summary="for layout only">
    <tr>
      <th>Reports will open in a new HTML window</th>
    </tr>
    
    <tr>
        <td><input type="RADIO" name="i" value="regionlist" checked="checked"/>
        Applications for Region including Project Manager
        <br/>Fiscal year <select name="regfy" >
                           <c:forEach var="row" items="${dropDownList}">
                              <option value="<c:out value='${row.id}' />">
                                <c:out value="${row.description}" /></option>
                          </c:forEach>              
                          </select><br/>
            Region <select name="regnum" >
                           <c:forEach var="row" items="${dropDownRegions}">
                              <option value="<c:out value='${row.id}' />">
                                <c:out value="${row.description}" /></option>
                          </c:forEach>              
                          </select></td>
    </tr>
    
    <tr>
        <td><input type="RADIO" name="i" value="allregion"/>
        Applications for all Regions
        <br/>Fiscal year <select name="allregfy" >
                           <c:forEach var="row" items="${dropDownList}">
                              <option value="<c:out value='${row.id}' />">
                                <c:out value="${row.description}" /></option>
                          </c:forEach>              
                          </select></td>
    </tr>
                          
    <tr>
        <td><input type="RADIO" name="i" value="countylist"/>Applications for County
        <br/>Fiscal year <select name="cofy" >
                           <c:forEach var="row" items="${dropDownList}">
                              <option value="<c:out value='${row.id}' />">
                                <c:out value="${row.description}" /></option>
                          </c:forEach>              
                          </select><br/>
            County <select name="conum" >
                           <c:forEach var="row" items="${dropDownCounties}">
                              <option value="<c:out value='${row.id}' />">
                                <c:out value="${row.description}" /></option>
                          </c:forEach>              
                          </select></td>
    </tr>
    
    <%--removed 4/11/11 per FC. replaced with apptyperpt which can filter on
    cooperative, shared serv, and indiv
    <tr>
        <td><input type="RADIO" name="i" value="cooprpt"/>Cooperative Projects
        <br/>Fiscal year <select name="coopfy" >
                           <c:forEach var="row" items="${dropDownList}">
                              <option value="<c:out value='${row.id}' />">
                                <c:out value="${row.description}" /></option>
                          </c:forEach>              
                          </select></td>
    </tr>--%>
    
    <tr>
        <td><input type="radio" name="i" value="apptyperpt"/>Applications by Application Type<br/>
            <select name="apptype">
                <option value="1">Cooperative</option>
                <option value="2">Shared Services</option>
                <option value="3">Individual</option>
                <option value="4">Demonstration</option>
            </select>
            <br/>Fiscal year <select name="apptypefy" >
                           <c:forEach var="row" items="${dropDownList}">
                              <option value="<c:out value='${row.id}' />">
                                <c:out value="${row.description}" /></option>
                          </c:forEach>              
                          </select></td>    
    </tr>
    
    <tr>
        <td><input type="RADIO" name="i" value="finalrpt"/>Final Report Submit/Not Submit
        <br/>Fiscal year <select name="finalrptfy" >
                           <c:forEach var="row" items="${dropDownList}">
                              <option value="<c:out value='${row.id}' />">
                                <c:out value="${row.description}" /></option>
                          </c:forEach>              
                          </select><br/>
             <select name="finalrptyn">
                <option value="1">Submitted</option>
                <option value="0">Not Submitted</option>
            </select></td>
    </tr>
    
        
    <tr>
        <td align="center"><input type="submit" value="View Report"/></td>
    </tr>
    
  
  </table>
  </form>
  
  
  </body>
</html>