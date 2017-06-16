<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>untitled</title>
  </head>
  <body>
  
  <h4>Cover Page</h4>
 
      
    <table width="90%" align="center" border="1" class="graygrid" summary="for layout only">
      <c:choose>
      <c:when test="${thisGrant.fycode>13}">
          <tr>
            <td width="30%">Public Library System Applying for Grant:</td>
            <td width="70%"><c:out value="${thisGrant.instName}" /></td>
          </tr>
      </c:when>
      <c:otherwise>
          <tr>
            <td width="30%">Sponsoring Institution:</td>
            <td width="70%"><c:out value="${thisGrant.instName}" /></td>
          </tr>
      </c:otherwise>
      </c:choose>
      
      <tr>
        <td>Mailing Address:</td>
        <td><c:out value="${thisGrant.addr1}" /></td>
      </tr>
      <tr>
        <td>Address:</td>
        <td><c:out value="${thisGrant.addr2}" /></td>
      </tr>
      <tr>
        <td >City, State, Zip:</td>
        <td ><c:out value="${thisGrant.city}" />
            <c:out value="${thisGrant.state}" />
            <c:out value="${thisGrant.zipcd1}" />  
            <c:out value="${thisGrant.zipcd2}" /></td>
      </tr>
      <tr>
        <td>County:</td>
        <td><c:out value="${thisGrant.county}" /></td>
      </tr>
      <tr>
        <td>Director:</td>
        <td><c:out value="${libDirectorBean.fname}" /> <c:out value="${libDirectorBean.mname}" /> 
          <c:out value="${libDirectorBean.lname}" /></td>
      </tr>
      <tr>
        <td>Director Contact Information:</td>
        <td><c:out value="${libDirectorBean.email}" />    <c:out value="${libDirectorBean.phone}" /></td>
      </tr>
      <tr>
        <td>State Judicial District: <c:out value="${distBean.judicialDistricts}" /></td>
        <td>State Assembly Districts: <c:forEach var="dist" items="${distBean.assemblyDistricts}" >
                                        <c:out value="${dist}" />
                                     </c:forEach></td>
      </tr>
      <tr>
        <td>State Senate Districts: <c:forEach var="sdist" items="${distBean.senateDistricts}" >
                                      <c:out value="${sdist}" />
                                   </c:forEach></td>
        <td>State Congressional Districts: <c:forEach var="cdist" items="${distBean.congressDistricts}" >
                                            <c:out value="${cdist}" />
                                          </c:forEach></td>
      </tr> 
      
      
      <c:choose>
      <c:when test="${thisGrant.fycode==14}">
          <%--only for projects in 2013-14 per KBALSEN --%>
          <c:forEach var="row" items="${allAlloc}">
            <tr>
             <td>Allocation for FY <fmt:formatNumber value="${row.fycode}" minIntegerDigits="2" /></td>
             <td><fmt:formatNumber value="${row.initialAlloc}" type="currency"  /></td>
            </tr>
          </c:forEach>
      </c:when>
      <c:when test="${thisGrant.fycode>16}">
          <%--for projects in 2016-17 (3 year grants);  added on 1/15/16 per KBALSEN --%>
          <c:forEach var="row" items="${allAlloc}">
            <tr>
             <td>Ed Law Amount for FY <fmt:formatNumber value="${row.fycode}" minIntegerDigits="2" /></td>
             <td><fmt:formatNumber value="${row.initialAlloc}" type="currency"  /></td>
            </tr>
          </c:forEach>
      </c:when>
      <c:otherwise>
          <%--for projects before 2013-14  --%>
          <c:forEach var="row" items="${allsums}">
            <tr>
              <td>Amount requested for FY <fmt:formatNumber value="${row.fycode}" minIntegerDigits="2" /></td>
              <td><fmt:formatNumber value="${row.totAmtReq}" type="currency"  /></td>
            </tr>
          </c:forEach>
      </c:otherwise>
      </c:choose>
      
      
      <tr>
        <td colspan="2"><b>NOTE:</b>The institutional information listed above
        is pulled from the SEDREF database.
        <a href="http://portal.nysed.gov/portal/pls/portal/SED.sed_inst_qry_vw$.startup" target="_blank">SEDREF</a>
        is the single authoritative 
        source of identifying information about institutions which the NYS Education Department
        determines compliance with applicable policy, law and/or regulation. <br/><br/>
        If your payee information is incorrect, it can only be updated once
        your Payee Information Form is received by Division of Library Development and approved
        by Grants Finance.  If your institutional or director information is incorrect, 
        contact Barbara Massago barbara.massago@nysed.gov to update SEDREF information.</td>
      </tr>
    </table>
         
    
    <c:choose >
    <c:when test="${lduser.prgal=='read' || appStatus.coversheetyn=='true' || appStatus.pendingReview=='true' }">
                           
  
    <table border="1" width="90%" align="center" class="graygrid" summary="for layout only">
      <tr>
        <th colspan="2" bgcolor="Silver">Project Manager (is someone who has expertise in the area of this program (adult literacy/outreach/etc.))</th>
      </tr>          
      <tr>
        <td width="30%">Name</td>
        <td width="70%"><c:out value="${coversheetBean.fname}" />  
          <c:out value="${coversheetBean.lname}" /></td>
      </tr>
      <tr>
        <td>Title</td>
        <td><c:out value="${coversheetBean.title}" /></td>
      </tr>        
      <tr>
        <td>Phone</td>
        <td><c:out value="${coversheetBean.phone}" /> <c:out value="${coversheetBean.phoneext}" /></td>
      </tr>
      <tr>
        <td colspan="2">The Project Manager email address listed will receive notifications regarding the
        application</td>
      </tr>
      <tr>
        <td>Email</td>
        <td><c:out value="${coversheetBean.email}" /></td>
      </tr>
      
       <tr>
        <td height="20"/>
     </tr>
      <tr>
        <th colspan="2" bgcolor="Silver">Additional Contact Person (preferably Director or other responsible party)</th>
      </tr>          
      <tr>
        <td width="30%">Name</td>
        <td width="70%"><c:out value="${coversheetBean.rmofname}" />  
          <c:out value="${coversheetBean.rmolname}" /></td>
      </tr>
      <tr>
        <td>Title</td>
        <td><c:out value="${coversheetBean.rmotitle}" /></td>
      </tr>        
      <tr>
        <td>Phone</td>
        <td><c:out value="${coversheetBean.rmophone}" /> <c:out value="${coversheetBean.rmophoneext}" /></td>
      </tr>
      <tr>
        <td>Email</td>
        <td><c:out value="${coversheetBean.rmoemail}" /></td>
      </tr>
      <tr>
        <td height="20"/>
     </tr>
      <tr>
        <td>Project Title</td>
        <td><c:out value="${coversheetBean.projectTitle}" /></td>
      </tr>
    </table>  
    <br/>
     
    
  </c:when>
  <c:otherwise >
  

  
  <p><html:errors /></p>
  <html:form action="/alSaveCoverpage" >    
         
    <table border="1" width="90%" align="center" class="graygrid" summary="for layout only">
      <tr>
        <th colspan="2" bgcolor="Silver">Project Title</th>
      </tr>
      <tr>
        <td width="30%">Project Title</td>
        <td width="70%"><html:text property="projectTitle" size="50" /></td>
      </tr>
      <tr>
        <th colspan="2" bgcolor="Silver">Project Manager (is someone who has expertise in the area of this program (adult literacy/outreach/etc.))</th>
      </tr>          
      <tr>
        <td width="30%">First Name</td>
        <td width="70%"><html:text property="fname" /></td>
      </tr>
      <tr>
        <td >Last Name</td>
        <td ><html:text property="lname" /></td>
      </tr>
      <tr>
        <td >Title</td>
        <td ><html:text property="title" maxlength="50" /></td>
      </tr>        
      <tr>
        <td >Phone (format 111-111-1111)</td>
        <td ><html:text property="phone" /></td>
      </tr>
      <tr>
        <td >Phone Extension</td>
        <td ><html:text property="phoneext" /><html:hidden property="phoneextId" /></td>
      </tr>
      <tr>
        <td colspan="2">The Project Manager email address listed below will receive notifications regarding the
        application</td>
      </tr>
      <tr>
        <td>Email</td>
        <td><html:text property="email" /><html:hidden property="pmId" /></td>
      </tr>      
      
      <tr>
        <td height="20"/>
      </tr>
      <tr>
        <th colspan="2" bgcolor="Silver">Additional Contact (preferably Director or other responsible party)</th>
      </tr>          
      <tr>
        <td width="30%">First Name</td>
        <td width="70%"><html:text property="rmofname" /></td>
      </tr>
      <tr>
        <td>Last Name</td>
        <td><html:text property="rmolname" /></td>
      </tr>
      <tr>
        <td>Title</td>
        <td><html:text property="rmotitle" maxlength="50" /></td>
      </tr>        
      <tr>
        <td>Phone (111-111-1111)</td>
        <td><html:text property="rmophone" /></td>
      </tr>
      <tr>
        <td>Phone Extension</td>
        <td><html:text property="rmophoneext" /> <html:hidden property="rmophoneextId" /></td>
      </tr>
      <tr>
        <td>Email</td>
        <td><html:text property="rmoemail" /><html:hidden property="rmoId" /></td>
      </tr>
    </table>
    
        
    <p align="center">
      <html:hidden property="grantid" /><html:hidden property="module" value="al" />
      <html:submit value="Save" />
   </p>         
  </html:form> 
       
    
    </c:otherwise>
    </c:choose>
    
   
  <%--1/14/16 per KBALSEN
  <p align="center" >
    Select <a href="liInitialForms.do?item=partinst&p=al">Participating Institutions</a> for this
     proposal. 
  </p> 
  --%>
  
  
  </body>
</html>
