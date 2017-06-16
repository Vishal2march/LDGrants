<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>FS worksheet</title>        
    <script language="javascript" type="text/javascript" src="css/jsConservPreserv.js" > </script>
  </head>
  <body>
  
  
<h4>Budget Worksheet for FS forms</h4>
 
<p>Enter the total amount requested for each category on the FS form.  Your worksheet total
must equal the Required Total (the total amount requested on the Project Budget). The system
will create the FS 20 or FS-10-F form once the totals are equal.</p>


<form method="POST" action="DiFsFormServlet" name="fsform" target="_blank">
  <table width="70%" class="boxtype" align="center"  summary="for layout only">
    <tr>
      <th>CATEGORIES</th><TH>CODE</TH><TH>PROJECT COSTS</TH>
    </tr>
    <TR>
      <Td>Professional Salaries</Td><td align="center">15</td>
      <td><input type="TEXT" name="professional" onchange="calcFsTotal(fsform);" /></td>
    </TR>
    <TR>
      <Td>Support Staff Salaries</Td><td align="center">16</td>
      <td><input type="TEXT" name="supportstaff" onchange="calcFsTotal(fsform);" /></td>
    </TR>
    <TR>
      <Td>Purchased Services</Td><td align="center">40</td>
      <td><input type="TEXT" name="purchased" onchange="calcFsTotal(fsform);" /></td>
    </TR>
    <TR>
      <Td>Supplies and Materials</Td><td align="center">45</td>
      <td><input type="TEXT" name="supplies" onchange="calcFsTotal(fsform);" /></td>
    </TR>
    <TR>
      <Td>Travel Expenses</Td><td align="center">46</td>
      <td><input type="TEXT" name="travel" onchange="calcFsTotal(fsform);" /></td>
    </TR>
    <TR>
      <Td>Employee Benefits</Td><td align="center">80</td>
      <td><input type="TEXT" name="benefits" onchange="calcFsTotal(fsform);" /></td>
    </TR>
    <TR>
      <Td>Minor Remodeling</Td><td align="center">30</td>
      <td><input type="TEXT" name="remodeling" onchange="calcFsTotal(fsform);" /></td>
    </TR>
    <TR>
      <Td>Equipment</Td><td align="center">20</td>
      <td><input type="TEXT" name="equipment" onchange="calcFsTotal(fsform);" /></td>
    </TR>
    <TR>
      <th>Worksheet Total</th><td></td><td>
          <div id="total"></div></td>
    </TR>
    <TR>
      <th>Required Total</th><td></td><td><b>
          <fmt:formatNumber value="${totAmtReq}" type="currency"/>
          <input type="HIDDEN" name="reqtotal" value='<c:out value="${totAmtReq}" />' /></b></td>
    </TR>
  </table>        
  
 
  <div id="fslinks" style="visibility:hidden">
  <p >Three copies of the FS 20 must be signed in blue ink and mailed to the Division of Library
  Development when submitting the initial application.</p>
  
  <input type="SUBMIT" name="todo" value="FS 20 HTML"/><br/>
  <input type="SUBMIT" name="todo" value="FS 20 PDF" />
  
  <br/>
  <p>Three copies of the FS-10-F must be signed in blue ink and mailed to the Division of Library
  Development when submitting the final report.</p>
  
  <input type="SUBMIT" name="todo" value="FS-10-F HTML"/><br/>
  <input type="SUBMIT" name="todo" value="FS-10-F PDF" />  
  </div>    
    
</form>  





  
    
  
  </body>
</html>
