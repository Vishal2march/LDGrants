<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>untitled</title>
  </head>
  <body>
   <c:choose>
    <c:when test="${thisGrant.fycode <=16}">
  <h4>FS-10, FS-10-A, FS-10-F Forms</h4>
  </c:when>
  <c:otherwise>
  <h4>FS-10-A</h4>
  </c:otherwise>
  </c:choose>
        <c:if test="${thisGrant.fycode <=16}">
  <form method="POST" action="FsFormServlet" target="_blank">
  <table width="90%" align="center" class="boxtype" summary="for layout only">
    <%--removed 9/11/15 as literacy now prints totals on last page
    <tr>
      <td><p class="error">FS-10 Note:</p>FS-10 forms are submitted with their category 
      code amounts left blank, as these code amounts will be filled in at the Division 
      of Library Development once a final award amount has been determined.</td>
    </tr>--%>

    <tr>
        <td height="15">&nbsp;</td>
    </tr>
    <tr>
      <td>
      Three copies of the FS-10 form for each project year must be signed in blue ink 
      and mailed to the Division of Library Development. 
      The form will open in a new browser window.</td>
    </tr>
    
    <c:choose>
    <c:when test="${thisGrant.fycode <14}">
    <%-- prior to 2013-14 use long fs10 for 2 yrs--%>
    <tr>
      <td>
        <select name="fy" id="fy" >
          <option value='<c:out value="${thisGrant.fycode}" />'>Budget Year 1</option>
          <option value='<c:out value="${thisGrant.fycode +1}" />'>Budget Year 2</option>
        </select>
      </td>
    </tr>
    <tr>
      <td><input type="RADIO" name="i" value="fs10">HTML<br/>
          <input type="RADIO" name="i" value="fs10pdf" checked="checked">PDF  (preferred)
      </td>
    </tr>
    </c:when>
    <c:otherwise>
    <%-- after 2013-14 use long fs10--%>
    <tr>
      <td>
        <select name="fy" id="fy" >
          <option value='<c:out value="${thisGrant.fycode}" />'>Budget Year 1</option>
          <option value='<c:out value="${thisGrant.fycode +1}" />'>Budget Year 2</option>
          <option value='<c:out value="${thisGrant.fycode +2}" />'>Budget Year 3</option>
        </select>
      </td>
    </tr>
    <tr>
      <td><input type="RADIO" name="i" value="fs10">HTML<br/>
          <input type="RADIO" name="i" value="fs10pdf" checked="checked">PDF  (preferred)
      </td>
    </tr>
    </c:otherwise>
    </c:choose>
    
    <tr>
      <td><input type="SUBMIT" value="View" /></td>
    </tr>
    <tr>
        <td align="center">
            FS-10 Form can be mailed to :<br/>
            Literacy Library Services Grant Program<br/>
            New York State Library<br/>
            Division of Library Development<br/>
            Cultural Education Center<br/>
            222 Madison Ave<br/>
            Albany, NY 12230 <br/>
            Attn: Barbara Massago, Room 10B41<br/>
        </td>
    </tr>
  </table>
  </form>
  <br/><br/>
 </c:if>   
        <c:if test="${thisGrant.fycode <=16}">
  <form method="POST" action="FsFormServlet" target="_blank">
  <table width="90%" class="boxtype" align="center" summary="for layout only">
    <tr>
      <td><p class="error">FS-10-F Note:</p>The applicant must type in and save the Project Budget 
      expenses before
      printing the FS-10-F form.  The 'actual expenses' for each Project Budget record
      will be transferred to the FS-10-F form. If the FS-10-F form is blank, please select 'Project
       Budget' from the menu, then type in and save your Project Budget expenses.</td>
    </tr>
    <tr>
        <td height="15">&nbsp;</td>
    </tr>
    <tr>
      <td>The <b>FS-10-F</b> form must be submitted at the end of each fiscal year.
      Choose the project year and HTML or PDF format. 
      Three copies must be signed in blue ink and mailed to the Division of Library Development. 
      The form will open in a new window.</td>
    </tr>
    <tr>
      <td>
        <select name="fyf" id="fyf" >
          <option value='<c:out value="${thisGrant.fycode}" />'>Budget Year 1</option>
          <option value='<c:out value="${thisGrant.fycode +1}" />'>Budget Year 2</option>
          <option value='<c:out value="${thisGrant.fycode +2}" />'>Budget Year 3</option>
        </select>
      </td>
    </tr>
   <%-- on 1/7/11: use fs10F short for 2011-12's per CA--%>
   <tr>
      <td><input type="RADIO" name="i" value="fs10f">HTML<br/>
          <input type="RADIO" name="i" value="fs10fpdf" checked="checked">PDF  (preferred)
      </td>
    </tr>
   
   <%--per LAreford 6/30/14, the 13-14's need FS10F Long <tr>
      <td><input type="RADIO" name="i" value="shortfs10f">HTML<br/>
          <input type="RADIO" name="i" value="shortfs10fpdf" checked="checked">PDF  (preferred)
      </td>--%>
    </tr>
    <tr>
      <td><input type="SUBMIT" value="View" /></td>
    </tr>
    <tr>
        <td align="center">
            FS-10-F Form can be mailed to :<br/>
            Literacy Library Services Grant Program<br/>
            New York State Library<br/>
            Division of Library Development<br/>
            Cultural Education Center<br/>
            222 Madison Ave<br/>
            Albany, NY 12230 <br/>
            Attn: Barbara Massago, Room 10B41<br/>
        </td>
    </tr>
  </table>
  </form>
  <br/><br/>
  </c:if>
  <form method="POST" action="FsFormServlet" target="_blank">
  <table width="90%" class="boxtype" align="center" summary="for layout only">
  
  	<tr>
  		<td>
  		<b>Amendment Notes:</b>
  		<ul>
  		<li>Please contact Carol A. Desch (carol.desch@nysed.gov) before submitting any amendment information.</li>
  		
  		<li>FS-10-A must be submitted by mid-May each year, in order to be considered.</li>
  		<li>3 copies completed and mailed only if there is an amendment to the approved project budget.</li>
  		</ul>
  		</td>
  	</tr>
    <tr>
        <td height="15">&nbsp;</td>
    </tr>
    <tr>
      <td>The <b>FS-10-A</b> Budget Amendment form must be submitted <b>only if</b> the applicant is requesting any type of 
      amendment to the original approved budget, such as a change of vendor or consultant. Complete if more than 10% or $1,000 
      (whichever is less) is moved from one budget category to another.
      Please call Carol A. Desch for approval before you submit this form.  Ms. Desch can be reached at 
      518-474-7196 or carol.desch@nysed.gov.  
      <br/><br/>Three copies of the FS-10-A form with original signature
      in <b>blue ink</b> must be mailed to the Division of Library Development.</td>
    </tr>
    <tr>
      <td>
        <select name="fya" id="fya" >
          <option value='<c:out value="${thisGrant.fycode}" />'>Budget Year 1</option>
          <option value='<c:out value="${thisGrant.fycode +1}" />'>Budget Year 2</option>
          <option value='<c:out value="${thisGrant.fycode +2}" />'>Budget Year 3</option>
        </select>
      </td>
    </tr>
    <tr>
      <td><input type="RADIO" name="i" value="fs10a">HTML<br/>
          <input type="RADIO" name="i" value="fs10apdf" checked="checked">PDF  (preferred)
      </td>
    </tr>
    <tr>
      <td><input type="SUBMIT" value="View" /></td>
    </tr>
    <tr>
        <td align="center">
            3 copies of the FS-10-A Form completed and mailed to :<br/>
            Literacy Library Services Grant Program<br/>
            New York State Library<br/>
            Division of Library Development<br/>
            Cultural Education Center<br/>
            222 Madison Ave<br/>
            Albany, NY 12230 <br/>
            Attn: Barbara Massago, Room 10B41<br/>
        </td>
    </tr>
  </table>
  </form>
  
  <br/>
    
  </body>
</html>
