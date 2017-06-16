<%@ page contentType="text/html;charset=windows-1252"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>NYS Grants Logout</title>
  </head>
  <body>
  
  <%
    //invalidate session to delete user's login bean
    HttpSession sess = request.getSession();
    sess.removeAttribute("lduser");//6/5/13 per WH
    sess.invalidate();
    
  %>
  <table align="center">
    <tr>
      <th>Thank you for using the NYS Online Grant System</th>
    </tr>
    <tr>
      <td>Please close your browser window</td>
    </tr>  
  </table>
  </body>
</html>
