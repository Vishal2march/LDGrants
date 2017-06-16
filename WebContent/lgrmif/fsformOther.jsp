<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title></title>
  </head>
  <body>
  <h5>FS-25, FS-10-F, FS-10-A Forms</h5>
  
  <table width="90%" align="center" class="boxtype" summary="for layout only">
    <tr>
      <td height="30" />
    </tr>
    <tr>
      <td>The <b>FS-25</b> form will need to be filed monthly to obtain additional funding up to the 40%,
      but the money must be needed over the next month only.  Monies will be available when you 
      submit one form with <b><font color="blue">original signature in blue ink</font></b> to 
      Grants Finance Unit, New York State 
      Education Department, Room 510W EB, Albany, NY 12234.</td>
    </tr>      
    <tr>
      <td><a href="FsFormServlet?i=fs25" target="_blank">HTML FS25 form</a> <br/>
          <a href="FsFormServlet?i=fs25pdf" target="_blank">PDF FS25 form</a> &nbsp;&nbsp;&nbsp;&nbsp;(PDF format preferred)</td>
    </tr>    
    <tr>
      <td ><hr/></td>
    </tr>
    <tr>
      <td height="30" />
    </tr>
    <tr>
          <td>The <b>FS-10-A</b> Budget Amendment form must be submitted when you are requesting any 
          type of amendment to your original approved budget.
          Please contact your respective Regional Advisory Officer (RAO), whose 
          approval is necessary before you submit this form.  The FS-10-A form with 
          <b><font color="blue">original signature in blue ink</font></b> should be 
          mailed to: <br/><br/>
      The University of the State of New York<br/>
      THE STATE EDUCATION DEPARTMENT<br/>
      New York State Archives, Grants Administration Unit<br/>
      Room 9A81 Cultural Education Center<br/>
      Albany, NY 12230</td>
        </tr>
        <tr>
          <td><a href="FsFormServlet?i=fs10a" target="_blank">HTML FS-10-A form</a> <br/>
              <a href="FsFormServlet?i=fs10apdf" target="_blank">PDF FS-10-A form</a>   &nbsp;&nbsp;&nbsp;&nbsp;(PDF format preferred)</td>
        </tr>
        <tr>
          <td ><hr/></td>
        </tr>
        <tr>
          <td height="30" />
        </tr>    
        <tr>
          <td><font color="Red">WARNING:</font> You must fill out your Project Budget expenses first before
          printing the FS-10-F form.  The 'expenses submitted' from each of your Project Budget records
          will be transferred to your FS-10-F form. If your FS-10-F form is blank, make sure you have 
          saved your expenses on the Project Budget page first.</td>
        </tr>
        <tr>
            <td height="10"/>
        </tr>
    
    <c:choose>
    <c:when test="${thisGrant.fycode>14}">
        <%--starting fy 2014-15; all grants use fs10f long, per DMeadows 7/16/14 --%>
        <tr>
          <td>The <b>FS-10-F</b> (Long Form) must be printed and completed with your final report. 
          Three (3) copies of the form with <b><font color="blue">original signature in blue ink</font></b> should be 
          mailed to: <br/><br/>
      The University of the State of New York<br/>
      THE STATE EDUCATION DEPARTMENT<br/>
      New York State Archives, Grants Administration Unit<br/>
      Room 9A81 Cultural Education Center<br/>
      Albany, NY 12230</td>
        </tr>        
        <tr>
          <td><a href="FsFormServlet?i=fs10f&fyf=0" target="_blank">HTML FS-10-F form</a> <br/>
              <a href="FsFormServlet?i=fs10fpdf&fyf=0" target="_blank">PDF FS-10-F form</a> &nbsp;&nbsp;&nbsp;&nbsp;(PDF format preferred)</td>
        </tr>
        
        
    </c:when>
    <c:otherwise>
    
        <tr>
          <td>The <b>FS-10-F</b> (Short Form) must be printed and completed with your final report. 
          Three (3) copies of the form with <b><font color="blue">original signature in blue ink</font></b> should be 
          mailed to: <br/><br/>
      The University of the State of New York<br/>
      THE STATE EDUCATION DEPARTMENT<br/>
      New York State Archives, Grants Administration Unit<br/>
      Room 9A81 Cultural Education Center<br/>
      Albany, NY 12230</td>
        </tr>        
        <tr>
          <td><a href="FsFormServlet?i=shortfs10f" target="_blank">HTML FS-10-F form</a> <br/>
              <a href="FsFormServlet?i=shortfs10fpdf" target="_blank">PDF FS-10-F form</a> &nbsp;&nbsp;&nbsp;&nbsp;(PDF format preferred)</td>
        </tr> 
        <tr>
          <td height="30" />
        </tr>     
        <tr>
          <td>The <b>FS-10-F</b> (Long Form) will be required to be submitted, instead of the FS-10-F 
          (Short Form), IF the report is being submitted after the due date or if they were 
          directed to do so in the grant application/RFP or by Department staff.  The form with 
          <b><font color="blue">original signature in blue ink</font></b> should be mailed to: <br/><br/>
      The University of the State of New York<br/>
      THE STATE EDUCATION DEPARTMENT<br/>
      New York State Archives, Grants Administration Unit<br/>
      Room 9A81 Cultural Education Center<br/>
      Albany, NY 12230</td>
        </tr>        
        <tr>
          <td><a href="FsFormServlet?i=fs10f&fyf=0" target="_blank">HTML FS-10-F form</a> <br/>
              <a href="FsFormServlet?i=fs10fpdf&fyf=0" target="_blank">PDF FS-10-F form</a> &nbsp;&nbsp;&nbsp;&nbsp;(PDF format preferred)</td>
        </tr> 
        
    </c:otherwise>
    </c:choose>
  </table>
  
  
  </body>
</html>