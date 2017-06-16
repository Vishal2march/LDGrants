<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/pd4ml.tld" prefix="pd4ml" %>
<%@ page contentType="text/html;charset=ISO8859_1"%>
<pd4ml:transform screenWidth="800" pageInsets="20,20,20,20,points" encoding="default" pageFormat="LETTER" pageOrientation="portrait" enableImageSplit="false">
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>LGRMIF Application Sheet</title>
  </head>
  <body>
  
  <font size="1">
  <table align="center" width="90%" border="1" summary="for layout only">
    <tr>
      <th colspan="2">Local Government Records Management Improvement Fund (LGRMIF) 
          <br/>Application Sheet</th>
    </tr>
    <tr>
      <td colspan="2" height="20"/>
    </tr>
    <tr>
      <td>Project Number</td>
      <td>05<fmt:formatNumber value="${thisGrant.fccode}" />
          -<fmt:formatNumber value="${thisGrant.fycode}" minIntegerDigits="2" />
          -<fmt:formatNumber value="${thisGrant.projseqnum}" pattern="####" minIntegerDigits="4" />
      </td>
    </tr>
    <tr>
      <td>Institution</td>
      <td><c:out value="${thisGrant.instName}" /> -<c:out value="${thisGrant.dorisname}" /></td>
    </tr>
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
      <td>Chief Administrative Officer:</td>
      <td><c:out value="${libDirectorBean.fname}" /> <c:out value="${libDirectorBean.mname}" /> 
        <c:out value="${libDirectorBean.lname}" />
      </td>
    </tr>
    <tr>
      <td>Title:</td>
      <td><c:out value="${libDirectorBean.title}" /></td>
    </tr>
    <tr>
      <td>Phone:</td>
      <td><c:out value="${libDirectorBean.phone}" /></td>
    </tr>
    <tr>
      <td>Email:</td>
      <td><c:out value="${libDirectorBean.email}" /></td>
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
    <tr>
      <td>Federal ID: <c:out value="${distBean.federalid}" /></td>
      <td>School District: <c:out value="${distBean.schoolDistrict}" /></td>
    </tr>        
    <tr>
      <td>Institution Type:</td>
      <td><c:out value="${distBean.insttype}" /></td>
    </tr>     
     <tr>
      <td colspan="2"><b>NOTE:</b>The institutional information listed above
        is pulled from the SEDREF database.  
        <a href="http://portal.nysed.gov/portal/pls/portal/SED.sed_inst_qry_vw$.startup" target="_blank">SEDREF</a>,
        the single authoritative 
        source of identifying information about institutions which the NYS Education Department
        determines compliance with applicable policy, law and/or regulation. <br/><br/>
        If your institutional information is incorrect, it can only be updated once
        your Payee Information Form is received by the Grants Administration Unit and approved
        by Grants Finance.  Grant Unit staff do not have authority to update SEDREF information.</td>
    </tr>
    <tr>
      <th colspan="2">Participating Institutions</th>
    </tr>    
     <logic:present name="PartCollectionBean" property="allPartInst" >
      <logic:iterate name="PartCollectionBean" property="allPartInst" id="partinstItem" >
  
        <tr>
          <td><c:out value="${partinstItem.instName}" /></td>
          <td>RMO Appointed? <c:choose><c:when test="${partinstItem.rmoappointed==0}">
                        Yes</c:when>
                        <c:when test="${partinstItem.rmoappointed==1}">
                        No</c:when>
                        <c:when test="${partinstItem.rmoappointed==2}">
                        N/A</c:when></c:choose>
            <br/>Year? <c:out value="${partinstItem.rmodate}"/></td>
        </tr>
        <tr>
            <td></td>
            <td>Schedule Adopted? <c:choose><c:when test="${partinstItem.scheduleadopted==0}">
                        Yes</c:when>
                        <c:when test="${partinstItem.scheduleadopted==1}">
                        No</c:when>
                        <c:when test="${partinstItem.scheduleadopted==2}">
                        N/A</c:when></c:choose>
            <br/>Year? <c:out value="${partinstItem.scheduledate}"/></td>
        </tr>
      </logic:iterate>
      </logic:present>
    <tr>
        <td height="20"/>
    </tr>     
  </table>
  <br/>
  
  
  <table width="90%" align="center" border="1" summary="for layout only">   
    <tr>
      <td>New York City (NYC) Grant?</td>
      <td><c:out value="${coversheetBean.dorisFlag}"/></td>
    </tr>
    <tr>
      <td>Name of NYC Agency (if applicable)</td>
      <td><c:out value="${coversheetBean.dorisName}"/></td>
    </tr>
    <tr>
      <th bgcolor="Silver" colspan="2">Eligibility Requirements</th>
    </tr>
    <tr>
      <td width="30%">RMO Appointed?</td>
      <td width="70%"><c:choose>
                        <c:when test="${coversheetBean.rmoAppointedint==0}">
                        Yes</c:when>
                        <c:when test="${coversheetBean.rmoAppointedint==1}">
                        No</c:when>
                        <c:when test="${coversheetBean.rmoAppointedint==2}">
                        N/A</c:when></c:choose></td>
    </tr>
    <tr>
      <td>Year RMO Appointed</td>
      <td><c:out value="${coversheetBean.rmoDate}" /></td>
    </tr>
    <tr>
      <td width="30%">Appropriate Retention Schedule Adopted?</td>
      <td width="70%"><c:choose>
                        <c:when test="${coversheetBean.scheduleAdoptedint==0}" >
                        Yes</c:when>
                        <c:when test="${coversheetBean.scheduleAdoptedint==1}" >
                        No</c:when>
                        <c:when test="${coversheetBean.scheduleAdoptedint==2}" >
                        N/A</c:when></c:choose></td>
    </tr>
    <tr>
      <td>Year Schedule was Adopted</td>
      <td><c:out value="${coversheetBean.scheduleDate}" /></td>
    </tr>
  </table>
        
  <br/>
  <table border="1" width="90%" align="center" summary="for layout only">
    <tr>
      <th colspan="2" bgcolor="Silver">Project Director</th>
    </tr>          
    <tr>
      <td width="30%">Name</td>
      <td width="70%"><c:out value="${coversheetBean.fname}" /> <c:out value="${coversheetBean.lname}" /></td>
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
      <td>Email</td>
      <td><c:out value="${coversheetBean.email}" /></td>
    </tr>      
    <tr>
      <th colspan="2" bgcolor="Silver">Records Management Officer (RMO)</th>
    </tr>    
    <tr>
      <td width="30%">Name</td>
      <td width="70%"><c:out value="${coversheetBean.rmofname}" /> <c:out value="${coversheetBean.rmolname}" /></td>
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
  </table>  
  <br/>
   
  <table border="1" width="90%" align="center" summary="for layout only">         
    <tr>
      <th colspan="2" bgcolor="Silver">Local Government Information</th>
    </tr>     
    <tr>
      <td width="30%">County</td>
      <td width="70%"><c:out value="${thisGrant.county}" /></td>
    </tr>
    <tr>
      <td>Region</td>
      <td><c:out value="${coversheetBean.govtRegionName}" /></td>
    </tr>
    <tr>
      <td>Type</td>
      <td><c:out value="${coversheetBean.govtTypeName}"/></td>
    </tr>
    <tr>
      <td>Department/Unit</td>
      <td><c:out value="${coversheetBean.dept}" /></td>
    </tr>
    <tr>
      <td>Population Served</td>
      <td><fmt:formatNumber value="${coversheetBean.population}" /></td>
    </tr>
    <tr>
      <td>Annual Operating Budget</td>
      <td><fmt:formatNumber value="${coversheetBean.annualbudget}" type="currency" /></td>
    </tr>
    <tr>
      <td>Number of Employees</td>
      <td>Full-time:<c:out value="${coversheetBean.ftemployees}" /><br/>
          Part-time:<c:out value="${coversheetBean.ptemployees}" /></td>
    </tr>
    <tr>
        <td><div title="Amount Requested field will be completed by the system">Amount Requested</div></td>
        <td><fmt:formatNumber value="${coversheetBean.amtrequested}" type="currency" maxFractionDigits="0"/></td>
    </tr>
  </table>
  <br/>
      
  <table border="1" width="90%" align="center" summary="for layout only">
    <tr>
      <th colspan="2" bgcolor="Silver">Application</th>
    </tr>       
    <tr>
      <td>Application Type</td>
      <td><c:choose >
            <c:when test="${coversheetBean.applicationType==1}">Individual</c:when>
            <c:when test="${coversheetBean.applicationType==2}">            
                <c:if test="${thisGrant.fycode<15}">Cooperative</c:if>
                <c:if test="${thisGrant.fycode>14}">Demonstration</c:if>
            </c:when>
            <c:when test="${coversheetBean.applicationType==3}">Shared Services</c:when>
          </c:choose>  </td>
    </tr>
    <tr>
      <td>Project Category</td>
      <td><c:out value="${coversheetBean.projcategoryName}" /></td>
    </tr>
    
    
    <c:if test="${thisGrant.fycode<16}">
        <tr>
            <td>Demo Projects Only</td>
            <td>
              <c:choose>
                <c:when test="${coversheetBean.demoType==1}">Planning</c:when>
                <c:when test="${coversheetBean.demoType==2}">Implementation </c:when>
                <c:otherwise>Not Demo Project</c:otherwise>
              </c:choose>
            </td>
          </tr>
        <tr>
          <td valign="top">Project Type</td>
          <td>First-Time Inventory: <c:out value="${coversheetBean.inventory}" /><br/>
              Electronic Records Inventory: <c:out value="${coversheetBean.recordsmgmt}" /><br/>
              Email Management: <c:out value="${coversheetBean.emailmgmt}" />
          </td>
        </tr>
    </c:if>
  </table>  
      
  <br/>
  <table width="90%" border="1" align="center" summary="for layout only" class="boxtype">
    <tr>
      <td bgcolor="Silver">
        Summary description of proposed project activities:  Describe the project, 
        including scope, objectives, and description of records.  The summary should be 
        brief, but should provide a clear statement of how you intend to use a LGRMIF grant.  </td>
    </tr>
    <tr>
      <td><bean:write name="coversheetBean" property="summaryDesc" filter="false" /></td>
    </tr>
  </table>
  </font>
  
  </body>
</html>
</pd4ml:transform>