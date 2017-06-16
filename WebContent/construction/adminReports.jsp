<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Public Library Construction Application</title>
  </head>
  <body>
  
  <h4>Construction Program Admin Reports</h4>
  
  
  <form method="POST" action="cnAdminReportNav.do" target="_blank">
  <table border="1" class="graygrid" align="center" width="80%" summary="for layout only">
    <tr>
      <th>Construction grant reports will open in a new HTML window</th>
    </tr>    
    <tr>
        <td><b>***Select a Fiscal Year:</b><br/>
        Fiscal year <select name="fy" >
                              <c:forEach var="row" items="${dropDownList}">
                                  <option value="<c:out value='${row.id}' />">
                                    <c:out value="${row.description}" /></option>
                              </c:forEach>              
                              </select></td>
    </tr>
    <tr>
        <td height="20"/>
    </tr>    
    <tr>
        <td><b>***Select a Report:</b></td>
    </tr>    
    <tr>
      <td><input type="RADIO" name="i" value="duedates" checked="checked"/>
          System due dates for fiscal year</td>
    </tr>
   <tr>
      <td><input type="RADIO" name="i" value="systemalloc"/>
          System amount allocated, amount spent, for fiscal year</td>
    </tr>  
    <tr>
      <td><input type="RADIO" name="i" value="awardbysystem"/>
          Applications with project cost, system recommended amount for fiscal year</td>
    </tr>
    <tr>
      <td><input type="RADIO" name="i" value="awardbyldadmin"/>
          Applications with project cost, LD award amount for fiscal year</td>
    </tr>    
    <tr>
        <td><input type="radio" name="i" value="submittodasny"/>
            Applications submitted to DASNY, approved by DASNY</td>
    </tr>
    
    <tr>
        <td><input type="radio" name="i" value="certoccupy"/>
            Applications requiring local Certificate of Occupancy</td>
    </tr>
    
    <tr>
        <td><input type="radio" name="i" value="bondapp"/>
            Applications that answered yes to the Application Form Bonding question</td>
    </tr>
    
    <tr>
        <td><input type="radio" name="i" value="projcosttotal"/>
            Applications with Total Project Cost (Part A from Application Form)</td>
    </tr>
    
    <tr>
        <td><input type="radio" name="i" value="projcategory"/>
            Applications with Project Category (accessibility, safety, acquisition, etc.)</td>
    </tr>
       
    <tr>
        <td><input type="radio" name="i" value="dasnyawarddesc"/>
            DASNY report - applications with recommended amount, start date, & project
            description</td>
    </tr>
    
    <%--<tr>
        <td><input type="radio" name="i" value="reducematchrpt"/>
            All Reduced Match Justifications, for a fiscal year</td>
    </tr>--%>
    
    <tr>
        <td><input type="radio" name="i" value="reducematchcalcrpt"/>
            Reduced Match Justifications based on AmtAwarded/Cost of Project for which Funding is Requested Calculation</td>
    </tr>
    
    <tr>
        <td><input type="radio" name="i" value="reducematchsystem"/>
            Reduced Match Justifications, for a Library System<br/>
            &nbsp;&nbsp;&nbsp;&nbsp;
             <select name="sysInstId">
               <c:forEach var="row" items="${dropDownSystems}">
               <option value='<c:out value="${row.idLong}"/>'><c:out value="${row.description}"/></option>
               </c:forEach>       
             </select></td>
    </tr>
    
    <tr>
        <td><input type="radio" name="i" value="reducematchcriteria"/>
            Applications with Reduced Match Criteria selected (education level, housing values, etc.)
            <br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <input type="checkbox" name="filterMatch" value="true"/>Filter for projects with Award Amount % of Project Cost over 50%
            <br/></td>
    </tr>
    
    <tr>
        <td><input type="radio" name="i" value="projnumrpt"/>
            All Awarded projects, for a fiscal year, sorted by Project Number</td>
    </tr>
    
    <tr>
        <td><input type="radio" name="i" value="finalsubmitrpt"/>
            Projects with Final Expenses Submitted to LD, for a fiscal year</td>
    </tr>
    
    <tr>
        <td><input type="radio" name="i" value="finalnotsubmitrpt"/>
            Projects waiting on Final Expense submission, for a fiscal year</td>
    </tr>
    
    <tr>
        <td><input type="radio" name="i" value="shporpt"/>
            Libraries that are subject to SHPO, for a fiscal year</td>
    </tr>
    
    <tr>
        <td align="center"><input type="submit" value="View Report"/></td>
    </tr>
  </table>
  </form>
      
  
  </body>
</html>