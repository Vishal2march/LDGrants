<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>untitled</title>
  </head>
  <body>
    
  <h4>Adult/Family Literacy Admin Reports</h4>
  
  <form method="POST" action="litReports.do" target="_blank">
  <table border="1" class="graygrid" align="center" width="80%" summary="for layout only">
    <tr>
        <td><b>***Select a program (Adult or Family Literacy):</b><br/>
        Program <select name="fc">
                  <option value="40">Adult Literacy</option>
                  <option value="42">Family Literacy</option></select></td>
    </tr>
    
    
    <tr>
        <td height="20"/>
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
      <td><input type="RADIO" name="item" value="litedlawtotal" checked="checked"/>
          Applications with Abstract and Ed Law Amounts ***NEW</td>
    </tr>
    <tr>
      <td><input type="RADIO" name="item" value="revcontact"/>
          Reviewer Contact Information</td>
    </tr>
    <tr>
      <td><input type="RADIO" name="item" value="revassign" />
          Reviewer's assigned to projects for Fiscal Year</td>
    </tr>
    <tr>
      <td><input type="RADIO" name="item" value="revscores" />
          Reviewer scores/comments for Project
          <br/> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                Last 4 digits of Project Number (ex. 0340-07-<b>0951</b>) 
                <input type="TEXT" name="projnum" /></td>
    </tr>
    
    <tr>
      <td><input type="RADIO" name="item" value="awardamt" />
          Literacy Amount Requested/Awarded for Fiscal Year </td>
    </tr>
    
    <tr>
      <td><input type="RADIO" name="item" value="scorerating" />
          Literacy Projects in Score/Rating Order</td>
    </tr>
    
    <tr>
        <td><input type="RADIO" name="item" value="nonsubgrants" />
            Applications started but not submitted for Fiscal Year</td>
    </tr>
    
    <tr>
      <td><input type="RADIO" name="item" value="projstatus" />
          Literacy Project Status (Coversheet, Narratives, Budget, etc).</td>
    </tr>
    
    <tr>
      <td><input type="RADIO" name="item" value="interimstatus" />
          Literacy Interim Report Status (Interim Narratives, Authorization).</td>
    </tr>
    
    <tr>
      <td><input type="RADIO" name="item" value="finalstatus" />
          Literacy Final Report Status (Final Report, Expenses, FS-10-F, etc).</td>
    </tr>
    
    <tr>
      <td align="center"><input type="SUBMIT" value="View Report" /></td>
    </tr>   
  </table>
  </form>
  
  </body>
</html>
