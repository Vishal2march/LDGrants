<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>Library Development Online Grant System</title>
  </head>
  <body>
  
  <table width="100%" class="boxtype" summary="for layout only">
    <tr >
        <td width="56%">                
                    New York State Education Department<br/>
                    New York State Library<br/>
                    Division of Library Development<br/>
                    <tiles:getAsString name="grprogram" ignore="true" />
        </td>        
        
        <td width="44%" align="right" >
          <a href="http://www.nysl.nysed.gov/" target="_blank">
            <img src="images/nysllogo.jpg" border="0" alt="NY State Library"/>
          </a>
        </td>
    </tr>
</table>

  </body>
</html>
