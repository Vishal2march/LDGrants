<%@ page contentType="text/html;charset=windows-1252"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>untitled</title>
  </head>
  <body>
  
  <h5>FS-10 Form</h5>
  
  <table width="90%" align="center" class="boxtype" summary="for layout only">
    <tr>
      <td><font color="Red">WARNING:</font> You must fill out your Project Budget first before
      printing the FS-10 form.  The 'amount requested' from each of your Project Budget records
      will be transferred to your FS-10 form. If your FS-10 form is blank, make sure you have 
      saved your Project Budget records first.</td>
    </tr>
    <tr>
        <td height="10"/>
    </tr>
    <tr>
      <td>The <b>FS-10</b> form must be printed and completed with your application, and entitles 
      recipients to the first 50% of their respective award. Mail three copies of the FS-10 
      with <b><font color="blue">original signatures in blue ink</font></b> to: <br/><br/>
      The University of the State of New York<br/>
      THE STATE EDUCATION DEPARTMENT<br/>
      New York State Archives, Grants Administration Unit<br/>
      Room 9A81 Cultural Education Center<br/>
      Albany, NY 12230</td>
    </tr>      
    <%--7/8/14 change to fs10long per dmeadows
    <tr>
      <td><a href="FsFormServlet?i=fs20" target="_blank">HTML FS20 form</a> <br/>
          <a href="FsFormServlet?i=fs20pdf" target="_blank">PDF FS20 form</a> &nbsp;&nbsp;&nbsp;&nbsp;(PDF format preferred)</td>
    </tr>    --%>
    <tr>
      <td><a href="FsFormServlet?i=fs10&fy=0" target="_blank">HTML FS10 form</a> <br/>
          <a href="FsFormServlet?i=fs10pdf&fy=0" target="_blank">PDF FS10 form</a> &nbsp;&nbsp;&nbsp;&nbsp;(PDF format preferred)</td>
    </tr>
  </table>  
  
  </body>
</html>
