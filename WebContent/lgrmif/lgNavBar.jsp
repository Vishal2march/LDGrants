<%--
 * @author  Stefanie Husak
 * @version 2.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the Application        :  lgNavBar.jsp
 * Creation/Modification History  :
 *
 *     SHusak       2009     Created
 *
 * Description
 * navigation section for initial/final items of lgrmif application
--%>
<%@ page contentType="text/html;charset=windows-1252"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>untitled</title>
  </head>
  <body>
  
  <table summary="for layout only" width="95%">
    <tr>
      <td><a href="welcomePage.jsp">Home</a></td>
      <td><a href="lgrmifNav.do?item=homepage&m=lg">LGRMIF Home</a></td>
      <td>Initial Application Forms:</td>
      <td>Post-Grant Award Forms:</td>
      <td><a href="LgHelp.do">Help<img src="images/helpimg.png" border="0"/> </a></td>      
    </tr>
    <tr>
        <td></td>
        <td></td>
        <td>
            <form action="lgApplicant.do" method="POST" style="margin-bottom:0;">
              <select name="i">
                <option value="checklist">Checklist</option>
                <option value="coversheet">Application Sheet</option>
                <option value="narrative">Project Narrative</option>
                <option value="budget">Project Budget</option>    
                <option value="lgappendix">Appendix A & Appendix A-1 G </option>
                <option value="payeeinfo">Payee Information & Standard Data Capture Form</option>
                <option value="lgvq">Vendor Quote Form (if applicable)</option>
                <option value="auth">Institutional Authorization</option>     
                <option value="fs20">Proposed Budget Summary (FS-10)</option>  
                <option value="attachment">Attachments/Uploads</option>  
                <option value="lgpartinst">Participating Institutions (if applicable)</option>  
                <option value="coopagree">Shared Services Agreement Form (if applicable)</option> 
                <option value="mwbe">M/WBE (NEW Requirement)</option>
              </select>
              <input type="HIDDEN" name="m" value="lg" />
              <input type="SUBMIT" value="Go"/>
            </form>
        </td>
        <td>
          <form action="lgApplicant.do" method="POST" style="margin-bottom:0;">
          <select name="i">
            <option value="checklist">Checklist</option>
            <%--<option value="acceptform">Grant Acceptance Form (if applicable)</option>--%>   
            <option value="fs">Request for Additional Funds FS-25 (Optional)</option>
            <option value="fs10a">Budget Amendment Summary</option>
            <option value="fs">Amendment Form (FS-10-A)</option>    
            <option value="finalrpt">Final Project Narrative</option> 
            <option value="budget">Final Project Budget</option>  
            <option value="statistic">Final Statistical Report</option>
            <option value="lgfinaleduc">Final Report for Educational Uses Projects</option>   
            <option value="fs">Final Expenditure Report (FS-10-F)</option>
            <option value="finalauth">Final Report Sign-off</option>    
            <option value="attachment">Attachments/Uploads</option>                   
          </select>
          <input type="HIDDEN" name="m" value="lg" />
          <input type="submit" value="Go"/>
        </form>
      </td>        
    </tr>
  </table> 
  <hr style="background-color:rgb(0,0,0);" size="3"/>
  </body>
</html>
