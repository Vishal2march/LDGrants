<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>untitled</title>
    <%--
  <noscript>
    <font size="3">You do not have Javascript enabled.  In order to use text editor options
    while typing the narrative, you must enable scripting.</font>
  </noscript>    --%> 
  <script language="javascript" type="text/javascript">
    function verifyMaxlength(form){
       form.summSize.value =form.summaryDesc.value.length;
    }  
    
    function addRemoveDemoType(form)
    {
        //alert(form.applicationType[0].checked);      
        if(form.applicationType[0].checked || form.applicationType[1].checked){
            //alert('not demo');//if demo type NOT selected->make sure demo types is reset to 0                  
            form.demoType[2].checked=true;
        }       
    }
  </script>
  </head>
  <body>
  
  <br/>
<a href="http://www.archives.nysed.gov/common/archives/files/grants_lgrmif_egrant_user_manual.pdf" target="_blank">Application sheet help</a>
  <br/>
        
  <h4>Application Sheet</h4>
 
    <p><html:errors /></p> 
    <table width="90%" align="center" border="1" class="graygrid" summary="for layout only">
     <tr>
        <td>Project Number</td>
        <td>05<fmt:formatNumber value="${thisGrant.fccode}" />
                -<fmt:formatNumber value="${thisGrant.fycode}" minIntegerDigits="2" />
                -<fmt:formatNumber value="${thisGrant.projseqnum}" minIntegerDigits="4" pattern="####" />
        </td>
      </tr>
      <tr>
        <td width="30%" >Sponsoring Institution:</td>
        <td width="70%" ><c:out value="${thisGrant.instName}" /></td>
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
        <td colspan="2"><b>NOTE:  &nbsp;</b>The institutional information listed above
        is pulled from the SEDREF database.  
        <a href="http://portal.nysed.gov/portal/pls/portal/SED.sed_inst_qry_vw$.startup" target="_blank">SEDREF</a>,
        the single authoritative 
        source of identifying information about institutions which the NYS Education Department
        determines compliance with applicable policy, law and/or regulation. <br/><br/>
        If your institutional information is incorrect, it can only be updated once
        your Payee Information Form is received by the Grants Administration Unit and approved
        by Grants Finance.  Grant Unit staff do not have authority to update SEDREF information.  
        <br/>The applicant <b>cannot</b> edit the SEDREF information above on their own.</td>
      </tr>
    </table>
    <br/><br/><br/><br/>
    
    
  <c:choose >
  <c:when test="${lduser.readaccess=='true' || appStatus.coversheetyn=='true' || appStatus.pendingReview=='true' }">
                        
   <br/>             
   <table width="90%" align="center" border="1" class="graygrid" summary="for layout only">   
      <tr>
        <td><a href="docs/lgrmif/nycAgencies.htm" target="_blank">New York City</a> (NYC) Grant?</td>
        <td><c:out value="${coversheetBean.dorisFlag}"/></td>
      </tr>
      <tr>
        <td>Name of NYC Agency (if applicable)</td>
        <td><c:out value="${coversheetBean.dorisName}"/></td>
      </tr>
      <tr>
        <th bgcolor="Silver" colspan="2">
            <a href="docs/lgrmif/eligibility.htm" style="background-color:silver;" target="_blank">Eligibility Requirements</a></th>
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
    <table border="1" width="90%" align="center" class="graygrid" summary="for layout only">
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
        <td><c:out value="${coversheetBean.rmoemail}" /> </td>
      </tr>            
    </table>  
    <br/>
     
    <table border="1" width="90%" align="center" class="graygrid" summary="for layout only">         
      <tr>
        <th colspan="2" bgcolor="Silver">Local Government Information</th>
      </tr>     
      <tr>
        <td width="30%">County</td>
        <td width="70%"><c:out value="${thisGrant.county}" /></td>
      </tr>
      <tr>
        <td><a href="docs/lgrmif/countiesregion.htm" target="_blank">Region</a></td>
        <td><c:out value="${coversheetBean.govtRegionName}" /></td>
      </tr>
      <tr>
        <td><a href="docs/lgrmif/govtType.htm" target="_blank">Type</a></td>
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
        <td>Amount Requested</td>
        <td><fmt:formatNumber value="${coversheetBean.amtrequested}" type="currency" maxFractionDigits="0"/></td>
      </tr>
    </table>
    <br/>
        
    <table border="1" width="90%" align="center" class="graygrid" summary="for layout only">
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
            </c:choose>   </td>
      </tr>
      <tr>
        <td>Project Category</td>
        <td><c:out value="${coversheetBean.projcategoryName}" /></td>
      </tr>
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
    </table>  
        
    <br/>
    <table width="90%" border="1" align="center" summary="for layout only" class="boxtype">
      <tr>
        <td bgcolor="Silver">
          <b>Summary description of proposed project activities:</b>  Describe the project, 
          including scope, objectives, and description of records.  The summary should be 
          brief, but should provide a clear statement of how you intend to use a LGRMIF 
          grant.<br/>The Project Summary field is limited to 2500 characters, including spaces.</td>
      </tr>
      <tr>
        <td><bean:write name="coversheetBean" property="summaryDesc" filter="false" /></td>
      </tr>
    </table>
         
  </c:when>
  <c:otherwise >
  

  
  <html:form action="/lgSaveApplication" >     
    
    <table width="90%" align="center" border="1" class="graygrid" summary="for layout only">  
      <tr>
        <td colspan="2">Fields marked with an (*) are required.  The Application Sheet 
        <b>cannot</b> be ‘Saved’ unless all required fields are completed.<br/>
                        <i><b>Remember to Save your work often.</b></i></td>
      </tr>
      <tr>
        <td>*<a href="docs/lgrmif/nycAgencies.htm" target="_blank">New York City</a> (NYC) Grant?</td>
        <td><html:radio property="dorisFlag" value="true"/>Yes &nbsp;&nbsp;&nbsp;  
            <html:radio property="dorisFlag" value="false"/>No</td>
      </tr>
      <tr>
        <td>Name of NYC Agency (if applicable)</td>
        <td><html:select property="dorisName">
                <html:optionsCollection name="dropDownLists" property="dorislist" value="description" label="description" />
            </html:select>
        </td>
      </tr>
      <tr>
        <th bgcolor="Silver" colspan="2">
            <a href="docs/lgrmif/eligibility.htm" style="background-color:silver;" target="_blank">Eligibility Requirements</a></th>
      </tr>
      <tr>
        <td width="30%">*RMO Appointed?</td>
        <td width="70%">
            <html:radio property="rmoAppointedint" value="0"/>Yes &nbsp;&nbsp;&nbsp; 
            <html:radio property="rmoAppointedint" value="1"/>No &nbsp;&nbsp;&nbsp; 
            <html:radio property="rmoAppointedint" value="2"/>N/A</td>
      </tr>
      <tr>
        <td>*Year RMO Appointed?<br/>(####)</td>
        <td><html:text property="rmoDate" maxlength="4" size="4" /></td>
      </tr>
      <tr>
        <td width="30%">*Appropriate Retention Schedule Adopted?</td>
        <td width="70%"><html:radio property="scheduleAdoptedint" value="0"/>Yes &nbsp;&nbsp;&nbsp; 
            <html:radio property="scheduleAdoptedint" value="1"/>No &nbsp;&nbsp;&nbsp; 
            <html:radio property="scheduleAdoptedint" value="2"/>N/A</td>
      </tr>
      <tr>
        <td>*Year Schedule was Adopted?<br/>(####)</td>
        <td><html:text property="scheduleDate" maxlength="4" size="4" /></td>
      </tr>
    </table>
  
 
         
    <table border="1" width="90%" align="center" class="graygrid" summary="for layout only">
      <tr>
        <th colspan="2" bgcolor="Silver">Project Director (PD)</th>
      </tr>          
      <tr>
        <td width="30%">*First Name</td>
        <td width="70%"><html:text property="fname" /></td>
      </tr>
      <tr>
        <td>*Last Name</td>
        <td><html:text property="lname" /></td>
      </tr>
      <tr>
        <td>*Title</td>
        <td><html:text property="title" maxlength="50" /></td>
      </tr>        
      <tr>
        <td>*Phone (###-###-####)</td>
        <td><html:text property="phone" /></td>
      </tr>
      <tr>
        <td>Phone Extension</td>
        <td><html:text property="phoneext" /> <html:hidden property="phoneextId" /></td>
      </tr>
      <tr>
        <td colspan="2">The Project Director email address listed below will receive notifications regarding the
        LGRMIF application</td>
      </tr>
      <tr>
        <td>*Email</td>
        <td><html:text property="email" /><html:hidden property="pmId" /></td>
      </tr>
      
      
      <tr>
        <th colspan="2" bgcolor="Silver">Records Management Officer (RMO)</th>
      </tr>          
      <tr>
        <td width="30%">*First Name</td>
        <td width="70%"><html:text property="rmofname" /></td>
      </tr>
      <tr>
        <td>*Last Name</td>
        <td><html:text property="rmolname" /></td>
      </tr>
      <tr>
        <td>*Title</td>
        <td><html:text property="rmotitle" maxlength="50" /></td>
      </tr>        
      <tr>
        <td>*Phone (###-###-####)</td>
        <td><html:text property="rmophone" /></td>
      </tr>
      <tr>
        <td>Phone Extension</td>
        <td><html:text property="rmophoneext" /> <html:hidden property="rmophoneextId" /></td>
      </tr>
      <tr>
        <td colspan="2">The RMO email address listed below will receive notifications regarding the
        LGRMIF application</td>
      </tr>
      <tr>
        <td>*Email</td>
        <td><html:text property="rmoemail" /><html:hidden property="rmoId" /></td>
      </tr>
      
      
      <tr>
        <th colspan="2" bgcolor="Silver">Local Government Information</th>
      </tr>     
      <tr>
        <td>County</td>
        <td><c:out value="${thisGrant.county}" /></td>
      </tr>
      <tr>
        <td>*<a href="docs/lgrmif/countiesregion.htm" target="_blank">Region</a>
            <html:hidden property="govtId" /></td>
        <td><html:select property="govtRegionId">
              <html:option value="0">Choose Region...</html:option>
              <html:optionsCollection name="dropDownLists" property="regions" value="id" label="description" />
            </html:select>
        </td>
      </tr>
      <tr>
        <td>*<a href="docs/lgrmif/govtType.htm" target="_blank">Type</a></td>
        <td><html:select property="govtTypeId">
                <html:option value="0">Choose Type...</html:option>
                <html:optionsCollection name="dropDownLists" property="govttypes" value="id" label="description" />
            </html:select></td>
      </tr>
      <tr>
        <td>Department/Unit</td>
        <td><html:text property="dept" maxlength="50"/></td>
      </tr>
      <tr>
        <td>*Population Served</td>
        <td><html:text property="populationStr"/></td>
      </tr>
      <tr>
        <td>*Annual Operating Budget</td>
        <td><html:text property="annualbudgetStr"/></td>
      </tr>
      <tr>
        <td>*Number of Employees</td>
        <td>Full-time:<html:text property="ftemployeesStr"/><br/>
            Part-time:<html:text property="ptemployeesStr"/></td>
      </tr>    
      <tr>
        <td colspan="2">The Amount Requested field below will be completed by the system.</td>
      </tr>
      <tr>
        <td><div title="Amount Requested field will be completed by the system">Amount Requested</div></td>
        <td><div title="Amount Requested field will be completed by the system">
        <fmt:formatNumber value="${coversheetBean.amtrequested}" type="currency" maxFractionDigits="0"/></div></td>
      </tr>
    </table>
    <br/><br/>
    
    
    <table border="1" class="graygrid" width="90%" align="center" summary="for layout only">
      <tr>
        <th colspan="2" bgcolor="Silver">Application</th>
      </tr>       
      <tr>
        <td>*Application Type:</td>
        <td><html:radio property="applicationType" value="1"  onclick="addRemoveDemoType(coversheetBean);"/>Individual &nbsp;&nbsp;&nbsp; 
           <%-- <html:radio property="applicationType" value="2" />Cooperative &nbsp;&nbsp;&nbsp;--%>
            <html:radio property="applicationType" value="3" onclick="addRemoveDemoType(coversheetBean);"/>Shared Services &nbsp;&nbsp;&nbsp;
            <html:radio property="applicationType" value="2"/>Demonstration<!--start fy2014-15-->
        </td>
      </tr>
      <tr>
        <td>*Project Category:<br/>
             <html:select property="projcategoryId">
                <html:option value="0">Choose Category...</html:option>
                <html:optionsCollection name="dropDownLists" property="category" value="id" label="description" />
             </html:select>            
        </td>
        <td>
          <html:hidden property="fycode"/>
          
          <c:choose>
          <c:when test="${coversheetBean.fycode==15}">
              Demo Projects Only: &nbsp;&nbsp;&nbsp;
              <html:radio property="demoType" value="1"/>Planning &nbsp;&nbsp;&nbsp; 
              <html:radio property="demoType" value="2"/>Implementation &nbsp;&nbsp;&nbsp; 
              <html:radio property="demoType" value="0" />Not Demo Project
          </c:when>
          <c:otherwise>
              <html:hidden property="demoType"/>
          </c:otherwise>
          </c:choose>
        </td>
      </tr>
    </table>
    <br/><br/>

    <table width="90%" align="center" summary="for layout only" class="boxtype">
     <tr>
        <td bgcolor="Silver">
          <b>Summary description of proposed project activities:</b>  Describe the project, 
          including scope, objectives, and description of records.  The summary should be 
          brief, but should provide a clear statement of how you intend to use a LGRMIF 
          grant.<br/>The Project Summary field is limited to 2500 characters, including spaces.</td>
      </tr>
      <%--<tr> removed 9/24/10 per FC, wants code for maxlength
        <td align="center">  
          <div name="myToolBars" class="mceToolbarExternal"></div>
          <html:textarea property="summaryDesc" cols="80" rows="12" />
         </td>
      </tr>--%>
      <tr>
        <td align="center">
            <html:textarea property="summaryDesc" cols="80" rows="12" onkeydown="verifyMaxlength(this.form);"  />
            <br/>
            <input readonly type=text name="summSize" size="4" style="background-color:silver"/>Character count
        </td>
      </tr>
    </table>
        
    <p align="center">
      <a href="#dataEntryAnchor" id="dataEntryAnchor"></a>
      <html:hidden property="narrId" /><html:hidden property="narrTypeId" />
      <html:hidden property="grantid" /><html:hidden property="module" value="lg" />
      <html:hidden property="sedrefinstid"/>
      <html:submit value="Save" />
      <i><b>Remember to Save your work often.</b></i>
    </p>         
  </html:form>
    
       
    </c:otherwise>
    </c:choose>
    
   
  <p align="center" >
    Select <a href="lgApplicant.do?i=lgpartinst">Participating Institutions</a> for this
    LGRMIF proposal <b>(Shared Services Projects only)</b> 
    <br/>
    <a href="docs/lgrmif/eligibility.htm" target="_blank">Eligibility Requirements</a>
  </p> 
  
  
  <c:if test="${anchorSection !=null}">
  <script type="text/Javascript">
    document.getElementById('dataEntryAnchor').focus();
  </script>  
  </c:if>
    
  </body>
</html>
