<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>stateaidNavBar</title>
  </head>
  <body>
  
  
  <table summary="for layout only" width="95%">
    <tr>
      <td><a href="welcomePage.jsp">Home</a></td>
      <td><a href="stateaidNav.do?item=homepage&m=staid">State Aid Home</a></td>
      <td>
            <form action="stateaidForms.do" method="POST" style="margin-bottom:0;">
              <select name="i">
                <option value="checklist">Application Forms.....</option>
                <option value="checklist">Checklist</option>
                <option value="coversheet">Coversheet</option>
                <option value="narrative">Project Narrative</option>
                <option value="budget">Project Budget</option>       
                <option value="assurance">Assurance</option>
                <option value="attachment">Attachments/Uploads</option>               
              </select>
              <input type="HIDDEN" name="m" value="staid" />
              <input type="SUBMIT" value="Go"/>
            </form>
        </td>
      <td>Help<img src="images/helpimg.png" border="0"/></td>      
    </tr>
  </table> 
  <hr/>
  </body>
</html>