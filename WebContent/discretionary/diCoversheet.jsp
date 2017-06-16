<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%><%--
 * @author  Stefanie Husak
 * @version 2.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the Application        :  diCoversheet.jsp
 * Creation/Modification History  :
 *
 *     SHusak       12/19/07     Created
 *
 * Description
 * The Di apcnt coversheet displays institution, district, instType info from sedref, and has 
 * data entry for the project manager, title, and summary descr.  
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="mypackage.CoversheetBean" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>untitled</title>
    <script language="javascript" type="text/javascript" src="jscripts/tinymce/tinymce.js"></script>
    <script type="text/javascript">
    tinymce.init({
         selector: "textarea",
         menubar: false,
         toolbar: "bold italic underline | alignleft aligncenter alignright | bullist numlist outdent indent",
         statusbar: false,
         nowrap: false,
         width: 600
     });
    </script>
    
  <noscript>
    <font size="3">You do not have Javascript enabled.  In order to use text editor options
    while typing the narrative, you must enable scripting.</font>
  </noscript>    
  </head>
  <body>
  
  <h4>Cover Sheet</h4>
 
      
    <table width="90%" align="center" border="1" class="graygrid" summary="for layout only">
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
        <td>Institution Type:</td>
        <td><c:out value="${distBean.insttype}" /></td>
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
         
    
    <c:choose >
    <c:when test="${lduser.prgdi=='read' || appStatus.coversheetyn=='true' || appStatus.pendingReview=='true' }">
                        
                
    <table width="90%" align="center" border="1" class="graygrid" summary="for layout only">   
      <tr>
        <th bgcolor="Silver" colspan="2">Eligibility</th>
      </tr>
      <tr>
        <td width="30%">Is Institution Affiliated with Religious Denomination?</td>
        <td width="70%"><c:out value="${coversheetBean.religious}" /></td>
      </tr>

      <tr>
        <td>Institutional Eligibility</td>
        <td>
          <table width="100%">
            <c:if test="${coversheetBean.chartered}" >
            <tr>
              <td>Chartered by the Board of Regents of NYS</td>
              <td>Date: <c:out value="${coversheetBean.charterdate}" /></td>
            </tr>
            </c:if>
            
            <c:if test="${coversheetBean.accepted}">
            <tr>
              <td>Accepted by the Board of Regents of the State of New York for filing under the not-for-
               profit section (Section 216) of the Education Law</td>
              <td>Date: <c:out value="${coversheetBean.acceptdate}"/></td>
            </tr>
            </c:if>
            
            <c:if test="${coversheetBean.charity}">
            <tr>
              <td>Registered with the Office of Charities of the NYS Department of State</td>
              <td>Date: <c:out value="${coversheetBean.charitydate}"/></td>
            </tr>
            </c:if>
            
            <c:if test="${coversheetBean.notprofit}">
            <tr>
              <td>Granted not-for-profit status under section 501(c)(3) of the United States Internal Revenue Code</td>
              <td>Date: <c:out value="${coversheetBean.notprofitdate}"/></td>
            </tr>
            </c:if>
            
            <c:if test="${coversheetBean.other}">
            <tr>
              <td>Other</td>
            </tr>
            </c:if>
          </table>
        </td>
      </tr>
    </table>
        
    
  
    <table border="1" class="graygrid" width="90%" align="center" summary="for layout only">
      <tr>
        <th colspan="2" bgcolor="Silver">Project Manager</th>
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
        Discretionary grant application</td>
      </tr>
      <tr>
        <td>Email</td>
        <td><c:out value="${coversheetBean.email}" /></td>
      </tr>
      
      <tr>
        <td>Project Title</td>
        <td><c:out value="${coversheetBean.projectTitle}" /></td>
      </tr>
    </table>  
    <br/>
    
      
    
    <table width="90%" align="center" summary="for layout only" class="boxtype">
      <tr>
        <td bgcolor="Silver">
          Summary description of proposed preservation activities: (5-10 sentences).
          The summary should be brief, but should provide a clear, publishable statement of 
          how you intend to use State Aid funds.
        </td>
      </tr>
      <tr>
        <td><bean:write name="coversheetBean" property="summaryDesc" filter="false" /></td>
      </tr>
    </table>
     
    
  </c:when>
  <c:otherwise >
  

  
  <p><html:errors /></p>
  <html:form action="/diSaveCoversheet" >    
  
  
    <table width="90%" align="center" border="1" class="graygrid" summary="for layout only">  
      <tr>
        <th bgcolor="Silver" colspan="2">Eligibility</th>
      </tr>
      <tr>
        <td width="30%">Is Institution Affiliated with Religious Denomination?</td>
        <td width="70%">See <a href="docs/guidelinesDiscretionary.htm#eligibility" target="_blank">Guidelines</a> for eligibility.<br/>
            <html:radio property="religious" value="true" />Yes <br/>
            <html:radio property="religious" value="false" />No </td>
      </tr>
      <tr>
        <td>Institutional Eligibility</td>
        <td>
          <table width="100%">
            <tr>
              <td>
                <html:checkbox property="chartered"/>Chartered by the Board of Regents of NYS
              </td>
              <td>Date: format mm/dd/yyyy<br/><html:text property="charterdate" size="8" /></td>
            </tr>
            <tr>
              <td>
                <html:checkbox property="accepted"/>accepted by the Board of Regents of the State of New York for filing under the not-for-
                profit section (Section 216) of the Education Law 
              </td>
              <td><html:text property="acceptdate" size="8" /></td>
            </tr>
            <tr>
              <td>
                <html:checkbox property="charity"/>Registered with the Office of Charities of the NYS Department of State 
              </td>
              <td><html:text property="charitydate" size="8" /></td>
            </tr>
            <tr>
              <td>
                <html:checkbox property="notprofit"/>Granted not-for-profit status under section 501(c)(3) of the United States Internal Revenue Code  
              </td>
              <td><html:text property="notprofitdate" size="8" /></td>
            </tr>
            <tr>
              <td><html:checkbox property="other"/>Other</td>
            </tr>
          </table>
        </td>
      </tr>
    </table>
  
 
         
     <table border="1" class="graygrid" width="90%" align="center" summary="for layout only">
      <tr>
        <th colspan="2" bgcolor="Silver">Project Manager</th>
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
        <td ><html:text property="phoneext" /> <html:hidden property="phoneextId" /></td>
      </tr>
      <tr>
        <td colspan="2">The Project Manager email address listed below will receive notifications regarding the
        Discretionary grant application</td>
      </tr>
      <tr>
        <td>Email</td>
        <td><html:text property="email" /><html:hidden property="pmId" /></td>
      </tr>
      
      <tr>
        <td width="30%">Project Title</td>
        <td width="70%"><html:text property="projectTitle" size="50" /></td>
      </tr>
    </table>
    

    <table width="90%" align="center" summary="for layout only" class="boxtype">
      <tr>
        <td bgcolor="Silver">
          Summary description of proposed preservation activities: (5-10 sentences).
          The summary should be brief, but should provide a clear, publishable statement of 
          how you intend to use State Aid funds.
        </td>
      </tr>
      <tr>
        <td align="center">  
          <div name="myToolBars" class="mceToolbarExternal"></div>
          <html:textarea property="summaryDesc" cols="80" rows="12" />
         </td>
      </tr>
    </table>
        
    <p align="center">
      <html:hidden property="narrId" /><html:hidden property="narrTypeId" />
      <html:hidden property="grantid" /><html:hidden property="module" value="di" />
      <html:submit value="Save" />
   </p>         
  </html:form> 
    
   
    
    </c:otherwise>
    </c:choose>
    
   
  <p align="center" >
    Select <a href="diInitialForms.do?i=partinst&m=di">Participating Institutions</a> for this
    C/P Discretionary proposal. 
  </p> 
  
  </body>
</html>
