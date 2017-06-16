<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.io.*,java.util.*" errorPage="error.jsp"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>NYSL Division of Library Development Online Grant System</title>
    <link href="css/StateAid.css" rel="stylesheet" media="screen" type="text/css" />
  </head>
  <body>
  
        
 <% 
// Get the file as an input stream
// The path is relative to application context,file is placed under public_html/docs
ArrayList fileLines = new ArrayList();

InputStream is = config.getServletContext().getResourceAsStream("/docs/constgrants.txt");
BufferedReader bis = null; 
if(is !=null)
{
  bis = new BufferedReader(new InputStreamReader(is));
  
  String line="";
  while((line=bis.readLine())!=null){
   fileLines.add(line);
  }
}    

if(is!=null)
  is.close();
if(bis!=null)
  bis.close();

%> 
  
<table width="100%" class="boxtype" summary="for layout only">
  <tr>
    <td>New York State Education Department<br/>
        New York State Library<br/>
        Division of Library Development<br/><br/>
        <a href="welcomePage.jsp">Home</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        <a href="http://eservices.nysed.gov/logoff/logoff.jsp?appName=LDGrants&link=https://eservices.nysed.gov/ldgrants">Logout</a>
    </td>
    <td align="right">
      <a href="http://www.nysl.nysed.gov/" target="_blank">
        <img src="images/nysllogo.jpg" border="0" alt="NYS Library"/>
      </a> <br/><font size="1">Opens in new window.</font>
    </td>        
  </tr>
</table>


<br/><br/>
<table width="90%" align="center" class="boxtype" summary="for layout only">
  <tr>
    <th>Construction Program updates</th>
  </tr>
  <% for(int i=0; i<fileLines.size(); i++) { %>
  
    <tr>
      <td><%= (String) fileLines.get(i)   %></td>
    </tr>
    
  <% }//end for loop %>               
</table>

  
<br/><br/><br/><br/><br/><br/><hr/>
<p align="center">Cultural Education Center, Albany, New York 12230. Phone: (518) 474-7890</p>
 
  
  </body>
</html>