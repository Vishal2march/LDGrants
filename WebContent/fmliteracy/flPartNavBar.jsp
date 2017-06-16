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
      <td width="40%">
        <a href="welcomePage.jsp">Home</a> 
      </td>
      <td>
        <form action="liParticipant.do" method="POST">
          <select name="item">
            <option value="coversheet">--Participating Grant--</option>
            <option value="coversheet">Cover page</option>
            <option value="narrative">Application Narrative</option>
            <option value="budget">Project Budget</option> 
            <option value="attachment">Uploads/Attachments</option>  
          </select>
          <input type="SUBMIT" value="Go"/>
        </form>
      </td>  
    </tr>
  </table>     
  
  </body>
</html>
