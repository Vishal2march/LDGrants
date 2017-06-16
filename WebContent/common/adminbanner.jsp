<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>untitled</title>
  </head>
  <body>
  
  <table width="100%" class="boxtype" summary="for layout only">
  <tr>
     <td >New York State Education Department<br/>
        New York State Library<br/>
        Division of Library Development<br/>
        <tiles:getAsString name="grprogram" ignore="true" /><br/><br/>
        
        <a href="welcomePage.jsp">Home</a>&nbsp;&nbsp;&nbsp;     
        <a href="http://eservices.nysed.gov/logoff/logoff.jsp?appName=LDGrants&link=https://eservices.nysed.gov/ldgrants">Logout</a>
    </td>
    <td width="44%" align="right" >
      <a href="http://www.nysl.nysed.gov/" target="_blank">
        <img src="images/nysllogo.jpg" border="0" alt="NY State Library"/>
        <br/>NYS Library
      </a>
    </td>
  </tr>
</table>
  </body>
</html>
