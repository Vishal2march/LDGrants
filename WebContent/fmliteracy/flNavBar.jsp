<%@ page contentType="text/html;charset=windows-1252"%>
<html lang="en">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>Family Literacy Navigation Bar</title>
  </head>
  <body>
  
  <br/>  
  <table width="100%" bgcolor="Silver" summary="for layout only">
    <tr>
      <td>
        <a href="welcomePage.jsp">Home</a> | 
        <a href="familylitlandingpage.action">Family Literacy Home</a>
        <%-- <a href="fmliteracyApplication.do?item=homepage&m=f">Family Literacy Home</a>  --%>
      </td>
      <td>
        <form action="liInitialForms.do" method="POST" title="dropdown navigation">
          <select name="item" title="initial application forms">
            <option value="checklist">--Initial Application Forms--</option>
            <option value="checklist">Checklist</option>
            <option value="coversheet">Cover Page</option>
            <%-- rmvd 2/2/16 per KB<option value="partinst">Coordination Partnering Organization</option>--%>
            <option value="narrative">Narrative</option>
            <%-- rmvd 1/20/16 per KB<option value="budget">Project Budget</option>    --%>              
            <option value="attachment">Attachments/Uploads</option>       
            <option value="certform">Certification Statement</option><%-- new 2/2/16 per kb--%>       
             <%-- rmvd 2/2/16 per KB<option value="auth">Cover Page Authorization Form</option>--%>
             <%-- rmvd 2/2/16 per KB<option value="bcert">Board Certification Form</option>--%>
            <%-- rmvd 1/20/16 per KB<option value="fs">FS-10 Form</option>  --%> 
            <%-- rmvd 10/01/12 per CA<option value="payeeinfo">Payee Info Form</option>  --%> 
          </select>
          <input type="HIDDEN" name="p" value="fl" title="family literacy module"/>
          <input type="SUBMIT" value="Go" title="Submit dropdown navigation"/>
        </form>
      </td>
      <!--td>&nbsp;&nbsp;&nbsp;</td>
      <td>
        <form action="liFinalForms.do" method="POST">
          <select name="item">
            <option value="checklist">--Final Report Forms--</option>
            <option value="checklist">Checklist</option>
           <%-- rmvd 10/10/12 per CA <option value="interim">Interim Narrative</option>--%>
           <%--removed 1/14/11 per CA <option value="intauth">Interim Authorization</option> --%>
            <option value="finalrpt">Final Report Narrative</option>      
            <option value="statisticsrpt">Final Report Statistics</option> 
            <option value="budget">Budget (Actual Expenses)</option>                      
            <option value="finalauth">Final Report Authorization</option> 
            <option value="attachment">Attachments/Uploads</option> 
            <option value="fs">FS-10-F Form</option>
            <option value="fs10a">FS-10-A Form (amendment only)</option>
          </select>
          <input type="HIDDEN" name="p" value="fl" />
          <input type="HIDDEN" name="y" value="1" />
          <input type="SUBMIT" value="Go" />
        </form>
      </td-->
      <td align="right"><a href="FlHelp.do">Help</a></td>      
    </tr>
  </table>
     
  
  </body>
</html>
