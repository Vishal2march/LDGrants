<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>untitled</title>
  </head>
  <body>
  
  <c:if test="${param.prog=='lg'}">
  <br/>
  <a href="http://www.archives.nysed.gov/common/archives/files/grants_lgrmif_egrant_user_manual.pdf" target="_blank">Participating institution help</a>
  <br/>
  </c:if>
  
  <h4>Participating Institutions</h4>  
  
  <table class="boxtype" align="center" width="90%" summary="for layout only" >
    <c:choose >
    <c:when test="${param.prog=='di'}">
        <tr>
          <td>Cooperative conservation/preservation projects are encouraged. Two or more eligible 
          institutions may apply for a grant involving related collections, shared resources or 
          facilities by designating one member of the group to submit the application. 
          Each participating institution must individually satisfy all eligibility requirements.
          <br/><br/>
          Each participating agency must also agree to perform the services outlined for its institution in 
          the application and indicate their agreement by submitting a signed copy of the 
          <a href="diInitialForms.do?i=coopagree&m=di" >Cooperative Applications Agreement</a>.
          </td>
        </tr>
    </c:when>
    <c:when test="${param.prog=='lg'}">
        <tr>
          <td>Shared Services records management projects are encouraged. Shared Services 
          projects involve two or more local governments working together on one activity, 
          with one government acting as the lead. These projects must also establish a 
          permanent cooperative relationship between governments, resulting in sustainable
          programmatic change. 
          
          <br/><br/>Each participating institution must individually satisfy all 
          eligibility requirements.  The items below are eligibility requirements that must 
          be met by the lead local government and by each participant in the project before the 
          grant application is submitted.  For exceptions to this, see 
          <a href="docs/lgrmif/eligibility.htm" target="_blank">Eligibility Requirements</a>.  
          The eligibility information must be completed by the lead local government on behalf
          of each shared services member.
          <br/><br/>
          Each participating agency must also agree to perform the services outlined for its 
          institution in the application and indicate their agreement by submitting a signed copy 
          of the Shared Services Agreement Form, which can also be found on the
          Application Checklist.</td>
        </tr>
    </c:when>
    <c:when test="${param.prog=='fl'}"><%--family lit--%>
        <tr>
          <td>Commissioner’s Regulations 90.3 (n) (1) (i) Adult literacy library services program shall mean a public library service program for adult literacy planned and operated in direct coordination with local public schools, colleges or other organizations which are operating similar adult literacy programs, which is designed to initiate, enhance or extend services to adults to increase their literacy skills.
          </td>
        </tr>
    </c:when>
    <c:otherwise ><!--adult literacy -->
        <tr>
          <td>Commissioner’s Regulations 90.3 (n) (1) (i) Adult literacy library services program shall mean a public library service program for adult literacy planned and operated in direct coordination with local public schools, colleges or other organizations which are operating similar adult literacy programs, which is designed to initiate, enhance or extend services to adults to increase their literacy skills.<br/><br/><br/>
          Please identify at least one organization for direct coordination. For libraries or library systems that serve as a site for an organization or government agency program such as DOL or LVA, that organization or government agency can be listed as the coordinating partner. For library systems where member libraries will partner with various local public schools, colleges or other organizations which are operating similar adult literacy programs, and those are not all known at the time of the application, please indicate: “As part of this program, individual member libraries will partner with various local public schools, colleges or other organizations which are operating similar adult literacy programs in their communities.” 
          </td>
        </tr>
    </c:otherwise>
    </c:choose>
  </table>
  <br/><br/>
  
   
  
  <table align="center" width="100%" class="boxtype" summary="for layout only">
    <tr>
      <th colspan="5">Institutions Participating in this Project</th>
    </tr>
    
    <c:choose >
    <c:when test="${param.prog=='lg'}">    
      <tr><td colspan="6"><html:errors /></td></tr>
      
      <!--this section used for lgrmif participants - need to save eligibility info-->
      <tr>
        <td><b>Action</b></td><td><b>Institution Name</b></td>
        <td><b>RMO Appointed</b></td><td><b>Year RMO Appointed</b></td><td><b>Schedule Adopted</b></td>
        <td><b>Year Schedule Adopted</b></td>
      </tr>      
         
    <html:form action="/savePartEligibility">
    <logic:present name="PartCollectionBean" property="allPartInst" >
    <logic:iterate name="PartCollectionBean" property="allPartInst" id="partinstItem" >
  
      <c:url var="delURL" value="participantTask.do" >
        <c:param name="i" value="deleteinst" />
        <c:param name="id" value="${partinstItem.id}" />
        <c:param name="p" value="${param.prog}" />
      </c:url>
      <tr>
        <td><c:choose >
              <c:when test="${lduser.readaccess=='true'}">Delete</c:when>
              <c:otherwise ><a href='<c:out value="${delURL}" />'> Delete</a></c:otherwise>
            </c:choose></td>                
        <td><c:out value="${partinstItem.instName}" /></td>
                           
        <td><html:radio name="partinstItem" property="rmoappointed" value="0" indexed="true"/>Yes &nbsp;&nbsp;&nbsp; 
            <html:radio name="partinstItem" property="rmoappointed" value="1" indexed="true"/>No &nbsp;&nbsp;&nbsp; 
            <html:radio name="partinstItem" property="rmoappointed" value="2" indexed="true"/>N/A 
        </td>
        <td><html:text name="partinstItem" property="rmodate" size="5" indexed="true" /></td>
        
        <td><html:radio name="partinstItem" property="scheduleadopted" value="0" indexed="true" />Yes &nbsp;&nbsp;&nbsp; 
            <html:radio name="partinstItem" property="scheduleadopted" value="1" indexed="true"/>No &nbsp;&nbsp;&nbsp; 
            <html:radio name="partinstItem" property="scheduleadopted" value="2" indexed="true"/>N/A
        </td>
        <td><html:text name="partinstItem" property="scheduledate" size="5" indexed="true" />
            <html:hidden name="partinstItem" property="id" indexed="true" />
            <html:hidden name="partinstItem" property="instName" indexed="true" /></td>
     </tr>
     <tr>
        <td colspan="6"><hr/></td>
     </tr>
    </logic:iterate>
    </logic:present>
    
    
    <logic:notEmpty name="PartCollectionBean" property="allPartInst">
      <c:choose >
      <c:when test="${lduser.readaccess=='true' || appStatus.coversheetyn=='true' || appStatus.pendingReview=='true' }">
        <tr><td colspan="6" align="center"><input type="BUTTON" value="Save" disabled="disabled" /></td></tr>
     </c:when>
     <c:otherwise >
        <tr><td colspan="6" align="center"><html:submit value="Save" /></td></tr>
      </c:otherwise>
      </c:choose>
    </logic:notEmpty> 
    
    </html:form>
    
    
    </c:when>
    <c:otherwise >
    <!-- this section used for di/fl/al participants -->    
        <tr>
          <td><b>Action</b></td><td><b>Institution Name</b></td>
          <td><b>Address</b></td><td><b>City</b></td><td><b>State</b></td>
        </tr>
          
        <c:forEach var="row" items="${allParts}">
          <c:url var="delURL" value="participantTask.do" >
            <c:param name="i" value="deleteinst" />
            <c:param name="id" value="${row.id}" />
            <c:param name="p" value="${param.prog}" />
          </c:url>
          <tr>
            <td><c:choose >
                <c:when test="${lduser.readaccess=='true'}">
                  Delete
                </c:when>
                <c:otherwise >        
                  <a href='<c:out value="${delURL}" />'> Delete</a>
                </c:otherwise>
                </c:choose></td>
            <td><c:out value="${row.instName}" /></td>
            <td><c:out value="${row.address}" /></td>
            <td><c:out value="${row.city}" /></td>
            <td><c:out value="${row.state}" /></td>
          </tr>
        </c:forEach>
    
    </c:otherwise>
    </c:choose>
  </table>
  <br/><br/>
    
  
  <table class="boxtype" align="center" width="90%" summary="for layout only" >
    <tr>
      <th>Search for Participating Institutions</th>
    </tr>
    <tr>
      <td>Type in the legal name of each Participating Institution to search the SEDREF
          database. The State Education Department maintains the SEDREF database which contains
          information on institutions across NYS. If the institution is displayed in the search 
          results, then click 'Select' to add this institution as a Project Participant.<br/><br/>
          
          The ability to add an institution to this form depends on a record existing in the SEDREF
          database for that institution. <br/><br/>
          
          <c:choose >
          <c:when test="${param.prog=='lg'}"> 
          If you cannot find the institution in the SEDREF database, please contact the NYS 
          Archives’ Grant Administration Unit @ 518-474-6926 to add the institution to SEDREF.  
          </c:when>
          <c:otherwise>
          If you cannot find the institution in the SEDREF database, be sure to include the participating
          institution name and address information in the
          Application Narrative section.
          </c:otherwise>
          </c:choose></td>
    </tr>
    
    <form action="participantTask.do?i=searchinst" method="post">
      <tr>
        <td><input type="TEXT" name="instName" />  <input type="SUBMIT" value="Search" />
            <input type="HIDDEN" name="p" value='<c:out value="${param.prog}" />' /></td>
      </tr>
    </form>
  </table>
  
    
  <br/>
  <table class="boxtype" align="center" width="90%" summary="for layout only">
    <tr>
      <td><b>Action</b></td><td><b>Institution Name</b></td><td><b>Address</b></td>
      <td><b>City</b></td><td><b>State</b></td>
    </tr>
    
    <c:forEach var="row" items="${instResults}">
      <c:url var="instURL" value="participantTask.do" >
        <c:param name="i" value="addinst" />
        <c:param name="id" value="${row.instid}" />
        <c:param name="p" value="${param.prog}" />
      </c:url>
      <tr>
        <td><c:choose >
        <c:when test="${lduser.readaccess=='true' || appStatus.coversheetyn=='true' || appStatus.pendingReview=='true' }">
                 Select
            </c:when>
            <c:otherwise >
              <a href='<c:out value="${instURL}" />'> Select</a>
            </c:otherwise>
            </c:choose></td>
        <td><c:out value="${row.instName}" /></td>
        <td><c:out value="${row.address}" /></td>
        <td><c:out value="${row.city}" /></td>
        <td><c:out value="${row.state}" /></td>
      </tr>
    </c:forEach>
  </table>

  
  </body>
</html>
