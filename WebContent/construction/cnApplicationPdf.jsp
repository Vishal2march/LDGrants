<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@ taglib uri="/WEB-INF/pd4ml.tld" prefix="pd4ml" %>
<%@ page contentType="text/html;charset=ISO8859_1"%>
<pd4ml:transform screenWidth="600" encoding="default" pageFormat="LETTER" pageOrientation="portrait" enableImageSplit="false">
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Public Library Construction Application</title>
  </head>
  <body>
  
  <font size="1">
  <table align="center" summary="for layout only">
    <tr>
      <th colspan="2">Public Library Construction Program
          <br/>Application Form
      </th>
    </tr>
    <tr>
      <td>Project Number</td>
      <td>
          03<fmt:formatNumber minIntegerDigits="2" value="${thisGrant.fccode}" />
          -<fmt:formatNumber value="${thisGrant.fycode}" minIntegerDigits="2" />
          -<fmt:formatNumber value="${thisGrant.projseqnum}" pattern="####" minIntegerDigits="4" />
      </td>
    </tr>
    <tr>
      <td>Institution</td>
      <td><c:out value="${thisGrant.instName}" /></td>
    </tr>
    <tr>
        <td>SEDREF Institution ID:</td>
        <td><c:out value="${thisGrant.instID}" /></td>
      </tr>
    <tr>
      <td>Project Title</td>
      <td><c:out value="${thisGrant.title}" /></td>
    </tr>
  </table>
  </font>
  <br/>
  
  <font size="1">
  <table align="center" width="95%" border="1" class="boxtype" summary="for layout only">
    <tr>
        <td width="30%">Library or System Name:</td>
        <td width="70%"><c:out value="${thisGrant.instName}" /></td>
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
        <td>County:</td>
        <td><c:out value="${thisGrant.county}" /></td>
    </tr>
      <tr>
        <td>Director of Institution:</td>
        <td><c:out value="${libDirectorBean.fname}" /> <c:out value="${libDirectorBean.mname}" /> 
          <c:out value="${libDirectorBean.lname}" />
        </td>
      </tr>
      <tr>
        <td>Title:</td>
        <td><c:out value="${libDirectorBean.title}" /></td>
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
        <td>FEIN #: <c:out value="${distBean.federalid}" /></td>
        <td>School District: <c:out value="${distBean.schoolDistrict}" /></td>
      </tr>        
      <tr>
        <td>Public Library System: </td>
        <td><c:out value="${thisGrant.systemName}" /></td>
      </tr>
      <tr>
        <td colspan="2"><b>NOTE:</b>The institutional information listed above
        is pulled from the SEDREF database.
        <a href="http://portal.nysed.gov/portal/pls/portal/SED.sed_inst_qry_vw$.startup" target="_blank">SEDREF</a>
        is the single authoritative 
        source of identifying information about institutions which the NYS Education Department
        determines compliance with applicable policy, law and/or regulation. <br/><br/>
        If your institutional or director information is incorrect, it can only be updated once
        your Payee Information Form is received by Division of Library Development and approved
        by Grants Finance.  Library Development staff do not have authority to update SEDREF information.</td>
      </tr>
    </table>
    </font>
    <br />
 
  <font size="1">
  <table align="center" width="95%" border="1" class="boxtype" summary="for layout only">
     <tr>
        <td colspan="2" bgcolor="Silver" height="20"/>
     </tr>
    <tr>
        <td colspan="2"><b>Building Information</b> - Provide the following information for the building 
        (existing or proposed) or site that is the subject of this application.</td>
    </tr>
    <tr>
        <td width="60%">*Building Name:</td>
        <td width="40%"><c:out value="${applicationFormBean.buildingName}" /></td>
    </tr>
    <tr>
        <td>*Street Address:</td>
        <td><c:out value="${applicationFormBean.buildingStreet}" /></td>
    </tr>
    <tr>
        <td>*City:</td>
        <td><c:out value="${applicationFormBean.buildingCity}" /></td>
    </tr>
    <tr>
        <td>*State:</td>
        <td><c:out value="${applicationFormBean.buildingState}" /></td>
    </tr>
    <tr>
        <td>*Zip Code:</td>
        <td><c:out value="${applicationFormBean.buildingZip}" /></td>
    </tr>
    <tr>
        <td colspan="2"><b>*Building Type</b></td>
    </tr>
    <tr>
        <td colspan="2">
            <c:choose>
            <c:when test="${applicationFormBean.buildingTypeCode==1}">
                Main Library
            </c:when>
            <c:when test="${applicationFormBean.buildingTypeCode==2}">
                System Headquarters
            </c:when>
            <c:when test="${applicationFormBean.buildingTypeCode==3}">
                Branch Library
            </c:when>
            <c:when test="${applicationFormBean.buildingTypeCode==4}">
                Other
            </c:when>
            </c:choose>
        </td>
    </tr>
    <tr>
        <td height="20" bgcolor="Silver" colspan="2"/>
    </tr>
    <tr>
        <td colspan="2"><b>Library building is or will be accessible to persons with 
        disabilities:</b></td>
    </tr>
    <tr>
        <td>Physical access:</td>
        <td><c:out value="${applicationFormBean.physicalAccess}" /></td>
    </tr>
    <tr>
        <td>Program access:</td>
        <td><c:out value="${applicationFormBean.progAccess}" /></td>
    </tr>
    <tr>
        <td height="20" bgcolor="Silver" colspan="2"/>
    </tr>
    <tr>
        <td colspan="2"><b>Library building is:</b></td>
    </tr>
    <tr>
        <td colspan="2">
            <c:choose>
            <c:when test="${applicationFormBean.libOwned==1}">
                Owned by applicant.
            </c:when>
            <c:when test="${applicationFormBean.libOwned==2}">
                Leased by applicant with a minimum of 10 years lease term from the date of anticipated completion.
            </c:when>
            <c:when test="${applicationFormBean.libOwned==3}">
                Otherwise legally available (i.e., located in a municipal building).
            </c:when>
            </c:choose>
        </td>
    </tr>
    <tr>
        <td colspan="2" bgcolor="Silver" height="20"></td>
    </tr>
    <tr>
        <td colspan="2"><b>Library site is:</b></td>
    </tr>
    <tr>
        <td colspan="2">
            <c:choose>
            <c:when test="${applicationFormBean.siteOwned==1}">
                Owned by applicant.
            </c:when>
            <c:when test="${applicationFormBean.siteOwned==2}">
                Leased by applicant with a minimum of 10 years lease term from the 
                date of anticipated completion.
            </c:when>
            <c:when test="${applicationFormBean.siteOwned==3}">
                Otherwise legally available (i.e., located in a municipal building).
            </c:when>
            </c:choose>
        </td>
    </tr>
    <tr>
        <td colspan="2" bgcolor="Silver" height="20"></td>
    </tr>
    <tr>
        <td>*Date of initial construction of library building (yyyy).</td>
        <td><c:out value="${applicationFormBean.dateConstructedStr}" /></td>
    </tr>
    <tr>
        <td>*Number of floors.</td>
        <td><c:out value="${applicationFormBean.numberBuildingFloors}" /> </td>
    </tr>
    <tr>
        <td>*Square footage of building.</td>
        <td><c:out value="${applicationFormBean.buildingSquareFootage}" /> </td>
    </tr>
    <tr>
        <td>*The building is designated a historic landmark.</td>
        <td><c:out value="${applicationFormBean.historicLandmark}" /></td>
    </tr>
    <tr>
        <td>The building is in a historic district.</td>
        <td><c:out value="${applicationFormBean.historicDist}" /></td>
    </tr>
    <tr>
        <td>The building is over 50 years old.</td>
        <td><c:out value="${applicationFormBean.over50}" /></td>
    </tr>
    <tr>
        <td>Does your Project involve ground disturbance?</td>
        <td><c:out value="${applicationFormBean.groundDisturb}" /></td>
    </tr>
 <%--   <tr>
        <td>If you are taking an exemption based on Appendix A please certify that you have SHPO approval to use this exemption.  </td>
        <td><c:out value="${applicationFormBean.shpoExemptionFlag}" /></td>
    </tr> --%>
     <tr>
        <td>If your project is exempt from SHPO according to Appendix A, please state the reason and cite the language from Appendix A which provides evidence for the exemption.  If you are unsure that your project activity is exempt please contact SHPO.</td>
        <td><bean:write name="applicationFormBean" property="shpoExemption" filter="false" /></td>
    </tr>
    
    
    
    <tr>
        <td colspan="2"><font color="navy">Note:</font> If your library building is 50 years old or older, and/or the 
        project involves ground disturbance and/or demolition, please see the 
        <a href="http://www.nysl.nysed.gov/libdev/construc/14m/shpo.htm" target="_blank">SHPO information page</a> to determine 
        if your project requires a SHPO approval.  If appropriate, an approval letter from SHPO must be 
        attached your construction grant application as a signed PDF.</td>
    </tr>
    <tr>
        <td colspan="2" bgcolor="Silver" height="20"></td>
    </tr>
    <tr>
        <td>This library is owned by a school District.</td>
        <td><c:out value="${applicationFormBean.schoolOwned}" /></td>
    </tr>
    <tr>
        <td>Which school district? (if applicable):</td>
        <td><c:out value="${applicationFormBean.schoolDistrict}" /></td>
    </tr>
    <tr>
        <td>The total cost of this project will exceed $10,000.</td>
        <td><c:out value="${applicationFormBean.overCost}" /></td> 
    </tr>
    <tr>
        <td colspan="2"><font color="navy">Note:</font> If the library building is owned by a school district and the cost
        of this project will exceed $10,000, the applicant must contact SED Office of
        Facilities Planning and include an OFP Certificate of Project Approval 
        with this application.</td>
    </tr>
    <tr>
        <td colspan="2" bgcolor="Silver" height="20"></td>
    </tr>
    <tr>
        <td colspan="2"><b>Project Details</b></td> 
    </tr> 
    <tr>
        <td>Project Title</td>
        <td><c:out value="${applicationFormBean.projectTitle}"/></td>
    </tr>
    <tr>
        <td colspan="2">Construction Project Manager (must be Library Staff or Board Member)</td> 
    </tr> 
    <tr>
        <td>Name:</td>
        <td><c:out value="${applicationFormBean.managerFirstName}" /> 
            <c:out value="${applicationFormBean.managerLastName}" /></td>
    </tr>
    <tr>
        <td>Phone:</td>
        <td><c:out value="${applicationFormBean.managerPhone}" /> 
        <c:out value="${applicationFormBean.managerPhoneExt}" /></td>
    </tr>
    <tr>
        <td>E-mail:</td>
        <td><c:out value="${applicationFormBean.managerEmail}" /></td>
    </tr>
    
    <c:if test="${applicationFormBean.fycode > 16}">
      <tr>
        <td colspan="2" bgcolor="Silver">Library Director</td>
      </tr>          
      <tr>
        <td width="30%">Name:</td>
        <td width="70%"><c:out value="${applicationFormBean.directorFirstName}" /> <c:out value="${applicationFormBean.directorLastName}" /></td>
      </tr> 
      <tr>
        <td>Phone:</td>
        <td><c:out value="${applicationFormBean.directorPhone}" /> <c:out value="${applicationFormBean.directorPhoneExt}" /></td>
      </tr>
      <tr>
        <td>Email:</td>
        <td><c:out value="${applicationFormBean.directorEmail}" /></td>
      </tr>
    </c:if>
    
   
    <tr>
        <td bgcolor="Silver" colspan="2">Construction project application is for (select all that apply):</td>
    </tr>
    <tr>
        <td>New Construction: <c:out value="${applicationFormBean.newConstruction}" /> </td>
        <td>Building Expansion: <c:out value="${applicationFormBean.expansion}" /> </td>
    </tr>
    <tr>
        <td>Site Acquisition: <c:out value="${applicationFormBean.acquisition}" /> </td>
        <td>Renovation/Rehabilitation: <c:out value="${applicationFormBean.renovation}" /></td>
    </tr>
    <tr>
        <td>Energy Conservation: <c:out value="${applicationFormBean.conservation}" /> </td>
        <td>Accessibility: <c:out value="${applicationFormBean.access}" />  </td>
    </tr>
    <tr>
        <td>Safety: <c:out value="${applicationFormBean.otherProject}" /></td>
        <td>Broadband: <c:out value="${applicationFormBean.broadband}" /></td>
    </tr>
    <tr>
      <td>Will the library's completed project require a local Certificate of Occupancy?</td>
      <td><c:out value="${applicationFormBean.certOccupancy}" /></td>
    </tr>   
    <tr>
        <td colspan="2" bgcolor="Silver" height="20"></td>
    </tr>
    <tr>
        <td colspan="2"><b>Estimated Project Costs</b></td>
    </tr>
    <tr>
        <td>a. Total Project Cost<br/><b>NOTE:</b>
        If the project for which funding is being requested (b) is part of a larger comprehensive project, list the Total 
        Project Cost of the larger project in "a".  If the project for which funding is being requested (b) is not part 
        of a larger project, "a" will equal "b".</td>
        <td><fmt:formatNumber value="${applicationFormBean.totalCost}" type="currency" maxFractionDigits="0"/></td>
    </tr>
    <tr>
        <td>b. Cost of Project for Which Funding is Being Requested<br/><b>NOTE:</b>
            This system will populate this field with the total 'Cost' of all budget
            records entered on the Project Budget pages.</td>
        <td><fmt:formatNumber value="${applicationFormBean.requestedAmt}" type="currency" maxFractionDigits="0"/></td>
    </tr>
    
    
    <c:choose>
    <c:when test="${applicationFormBean.fycode < 13}">
      <tr>
        <td>c. Amount of Public Library Construction Program Funds requested for this project (cannot be more than
            50% of the amounted entered in question (b) above 
            <fmt:formatNumber value="${applicationFormBean.maxRequestCost}" type="currency" maxFractionDigits="0" />)</td>
        <td><fmt:formatNumber value="${applicationFormBean.requestCost}" type="currency" maxFractionDigits="0" /></td>
      </tr>
    </c:when>
    <c:otherwise><%--starting in FY 2012/13, match is 75% max--%>
      <tr>
        <td>c. Amount of Public Library Construction Funds requested for this Project (cannot be more than 50% of the amount 
        in question b or 75% if you qualify for the Reduced Match.) Note: Contact your System regarding eligibility for 
        reduced match.
            <fmt:formatNumber value="${applicationFormBean.maxRequestCost}" type="currency" maxFractionDigits="0" />)</td>
        <td><fmt:formatNumber value="${applicationFormBean.requestCost}" type="currency" maxFractionDigits="0" /></td>
      </tr>
    </c:otherwise>
    </c:choose>    
    
    
    <tr>     
        <td>d.  This project is or was funded, in whole or in part by funds
            secured through the issuance of tax exempt bonds, bond anticipation notes, or revenue anticipation notes.</td>
        <td><c:out value="${applicationFormBean.bondPaid}" /></td>
    </tr>
    <tr>
        <td colspan="2" bgcolor="Silver" height="20"></td>
    </tr>
    <tr>
        <td colspan="2"><b>Timeframe</b> (date format MUST BE mm/dd/yyyy)</td>
    </tr>
    <tr>
        <td colspan="2"><font color="Blue">A valid date should be entered for each of
        the Timeframe items below.  Valid date format is mm/dd/yyyy</font></td>
    </tr>
        <tr>
            <td>This project is expected to start on or before:</td>
            <td><c:out value="${applicationFormBean.expectStartDate}" /></td>
        </tr>
    
    <tr>
        <td colspan="2">This project was/will be started on <c:out value="${applicationFormBean.projStartedDate}" /> and is not complete at the time
            of this application. <br/>
            (NOTE: If the project has not started yet, the start date above should 
            be the same as the expected start date from question 1).</td>
    </tr>
    <tr>
        <td>This project is expected to be completed by:</td>
        <td><c:out value="${applicationFormBean.projCompleteDate}" /> </td>
    </tr>
 </table>  
 </font>
  
  </body>
</html>
</pd4ml:transform>