<%@ page contentType="text/html;charset=windows-1252"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>untitled</title>
  </head>
  <body>
  
  <table width="100%" class="boxtype" summary="for layout only">
    <tr>
        <td width="56%" >New York State Education Department<br/>
            New York State Archives<br/>
            Grants Administration Unit<br/>
            <tiles:getAsString name="grprogram" ignore="true" />    
            
            <p align="left" >
              <a href="welcomePage.jsp">Home</a>&nbsp;&nbsp; 
              <a href="http://eservices.nysed.gov/logoff/logoff.jsp?appName=LDGrants&link=https://eservices.nysed.gov/ldgrants" >Logout</a>
            </p>            
        </td>        
        
        <td  width="44%" align="right">
          <a href="http://www.archives.nysed.gov/" target="_blank">
            <img src="images/nysa.jpg" border="0" alt="NY State Archives"/>
          </a>
        </td>
    </tr>
</table>

  </body>
</html>
