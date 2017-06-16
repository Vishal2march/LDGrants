<%--
 * @author  Stefanie Husak
 * @version 1.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the Application        :  diFsForms.jsp
 * Creation/Modification History  :
 *
 *     SHusak       12/24/07     Created
 *
 * Description
 * This page gives instructions and links to all fs forms for discretionary apcnt. 
--%>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>untitled</title>
  </head>
  <body>
  
  <h4>FS10, FS10A, FS10F Forms</h4>
  
  <table width="90%" align="center" class="boxtype" summary="for layout only">
    <tr>
      <td><p class="error">NOTICE: You must fill out your Project Budget first before
      printing the FS-10 form.  The 'amount requested' from each of your Project Budget records
      will be transferred to your FS-10 form. If your FS-10 form is blank, make sure you have 
      saved your Project Budget records first.</p></td>
    </tr>
    <tr>
      <td>The <b>FS-10</b> form must be printed and completed with your application. 
      Mail three original FS-10 Forms with original signatures in <b>blue ink</b> to:<br/><br/>
          CONSERVATION/PRESERVATION PROGRAM<br/>
          ATTN: KERRY LYNCH<br/>
          DIVISION OF LIBRARY DEVELOPMENT<br/>
          NEW YORK STATE LIBRARY<br/>
          10B41 CULTURAL EDUCATION CENTER<br/>
          222 MADISON AVENUE<br/>
          ALBANY, NY  12230<br/></td>
    </tr>
    <%-- this version allows apcnt to enter category amts req themselves
         but 7/16/08 BL requested change to fs20long form w/ preprinted amts
    <tr>
      <td><a href="DiFsFormServlet?todo=fs20worksheet">FS worksheet</a></td>
    </tr>--%>
    
    <tr>
      <td><a href="FsFormServlet?i=fs10&fy=0" target="_blank">HTML FS10 form</a> <br/>
          <a href="FsFormServlet?i=fs10pdf&fy=0" target="_blank">PDF FS10 form</a> &nbsp;&nbsp;&nbsp;&nbsp;(PDF format preferred)</td>
    </tr>    
    <tr>
      <td><hr/></td>
    </tr>
    <tr>
      <td height="30" />
    </tr>
    <tr>
      <td>The <b>FS-10-A</b> Budget Amendment form must be submitted when you are requesting any type of 
      amendment to your original approved budget, such as a change of vendor or consultant.  
      Please call Barbara Lilley, the Conservation/ Preservation Program Administrator, 
      whose approval is necessary before you submit this form.  Ms. Lilley can be reached at 
      (518) 486-4864 or email - barbara.lilley@nysed.gov.  The FS-10-A form with original signature
      in <b>blue ink</b> can be mailed to:<br/><br/>
          CONSERVATION/PRESERVATION PROGRAM<br/>
          ATTN: KERRY LYNCH<br/>
          DIVISION OF LIBRARY DEVELOPMENT<br/>
          NEW YORK STATE LIBRARY<br/>
          10B41 CULTURAL EDUCATION CENTER<br/>
          222 MADISON AVENUE<br/>
          ALBANY, NY  12230<br/></td>
    </tr>
    <tr>
      <td><a href="FsFormServlet?i=fs10a" target="_blank">HTML FS-10-A form</a> <br/>
          <a href="FsFormServlet?i=fs10apdf" target="_blank">PDF FS-10-A form</a>   &nbsp;&nbsp;&nbsp;&nbsp;(PDF format preferred)</td>
    </tr>
    <tr>
      <td><hr/></td>
    </tr>
    <tr>
      <td height="30" />
    </tr>
    <tr>
      <td><p class="error">NOTICE: You must fill out your Project Budget expenses first before
      printing the FS-10-F form.  The 'expenses submitted' from each of your Project Budget records
      will be transferred to your FS-10-F form. If your FS-10-F form is blank, make sure you have 
      saved your expenses on the Project Budget page first.</p></td>
    </tr>
    <tr>
      <td>The <b>FS-10-F</b> must be printed and completed with your final report.  The form with original signature
      in <b>blue ink</b> can be mailed to:<br/><br/>
          CONSERVATION/PRESERVATION PROGRAM<br/>
          ATTN: KERRY LYNCH<br/>
          DIVISION OF LIBRARY DEVELOPMENT<br/>
          NEW YORK STATE LIBRARY<br/>
          10B41 CULTURAL EDUCATION CENTER<br/>
          222 MADISON AVENUE<br/>
          ALBANY, NY  12230<br/>
      </td>
    </tr>        
    <%--<tr>
      <td><a href="DiFsFormServlet?todo=fs10fworksheet">FS worksheet</a></td>
    </tr>  --%>    
    
    
    <%-- 8/4/14 no longer using short form per BL --%>    
    <c:choose>
    <c:when test="${thisGrant.fycode<15}">
      <tr>
        <td><a href="FsFormServlet?i=shortfs10f&fyf=0" target="_blank">HTML FS-10-F form</a> <br/>
            <a href="FsFormServlet?i=shortfs10fpdf&fyf=0" target="_blank">PDF FS-10-F form</a> &nbsp;&nbsp;&nbsp;&nbsp;(PDF format preferred)</td>
      </tr> 
    </c:when>
    <c:otherwise>    
      <tr>
        <td><a href="FsFormServlet?i=fs10f&fyf=0" target="_blank">HTML FS-10-F form</a> <br/>
            <a href="FsFormServlet?i=fs10fpdf&fyf=0" target="_blank">PDF FS-10-F form</a> &nbsp;&nbsp;&nbsp;&nbsp;(PDF format preferred)</td>
      </tr> 
    </c:otherwise>
    </c:choose>
    
    <tr>
      <td height="20" />
    </tr>    
  </table>
    
  </body>
</html>
