<%@ page contentType="text/html;charset=windows-1252"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>untitled</title>
  </head>
  <body>
    
  <br/>  
  <table summary="for layout only" bgcolor="Silver" width="100%">
    <tr>
      <td>
        <a href="welcomePage.jsp">Home</a> | <a href="discretionaryNav.do?item=homepage&m=di">Discretionary Home</a>
      </td>
      <td>
        <form action="diParticipatingForms.do" method="POST">
          <select name="item">
            <option value="none">--Participating Grant--</option>
            <option value="coversheet">Coversheet</option>
            <option value="narrative">Project Narrative</option>
            <option value="budget">Project Budget</option> 
            <option value="finalrpt">Final Report</option> 
            <option value="attachment">Attachments/Uploads</option>  
            <option value="coopagree">Cooperative Agreement</option>
          </select>
          <input type="SUBMIT" value="Go"/>
        </form>
      </td>  
    </tr>
  </table>
        
  </body>
</html>
