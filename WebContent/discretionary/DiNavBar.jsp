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
        <form action="diInitialForms.do" method="POST">
          <select name="i">
            <option value="checklist">--Initial Application Forms--</option>
            <option value="checklist">Checklist</option>
            <option value="coversheet">Coversheet</option>
            <option value="narrative">Project Narrative</option>
            <option value="budget">Project Budget</option>                    
            <option value="auth">Institutional Authorization Form</option>
            <option value="fs">FS-10 Form</option>
            <option value="attachment">Attachments/Uploads</option>    
            <option value="payeeinfo">Payee Information Form</option>
            <option value="coopagree">Cooperative Agreement Form</option>
            <option value="microform">Microform Guidelines Form</option> 
            <option value="prequal">Prequalification (NEW Requirement)</option> 
            <option value="mwbe">M/WBE (NEW Requirement)</option> 
          </select>
          <input type="HIDDEN" name="m" value="di" />
          <input type="SUBMIT" value="Go"/>
        </form>
      </td>
      <td>&nbsp;&nbsp;&nbsp;</td>
      <td>
        <form action="diInitialForms.do" method="POST">
          <select name="i">
            <option value="checklist">--Final Report Forms--</option>
            <option value="checklist">Checklist</option>
            <option value="finalrpt">Final Report Narrative</option>             
            <option value="budget">Project Budget (Expenses Submitted)</option>  
            <option value="finalauth">Final Report Signoff</option>                  
            <option value="fs">FS-10-F, FS-10-A Forms</option>
            <option value="attachment">Attachments/Uploads</option> 
          </select>
          <input type="HIDDEN" name="m" value="di" />
          <input type="SUBMIT" value="Go" />
        </form>
      </td>
      <td><a href="DiApcntHelp.do">Help</a></td>      
      <td />
      <td><a href="diApcntSearch.do?i=reports">General Reports</a></td>
    </tr>
  </table>
    
  
  </body>
</html>
