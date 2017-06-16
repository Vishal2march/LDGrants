<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Public Library Construction - Reduced Match Justification Form</title>
  </head>
  <body>
  
  <h4>Reduced Match Justification Form</h4>
  
  <p>
   <b>This form is completed by library system staff.</b>
   This form is required to be completed and submitted as part of the online 
   application by library system staff for each public library deemed eligible 
   by the library system for a reduced match requirement below 
   50%.  State aid for public library construction can be provided 
   for “up to seventy-five percent of the total project approved costs for 
   buildings of public libraries that are located in an economically 
   disadvantaged community”, as determined by the library system.  <br/><br/>Public 
   libraries that are located in communities that are not identified by the 
   library system as economically disadvantaged communities are eligible for 
   a maximum of fifty percent of the total project approved costs.
   </p><br/><br/>

  
  
  <table align="center" width="95%" border="1" class="graygrid" summary="for layout only">
    <tr>
      <td><b>Project Number:</b></td>
      <td>
          03<fmt:formatNumber minIntegerDigits="2" value="${thisGrant.fccode}" />
          -<fmt:formatNumber value="${thisGrant.fycode}" minIntegerDigits="2" />
          -<fmt:formatNumber value="${thisGrant.projseqnum}" pattern="####" minIntegerDigits="4" />
      </td>
    </tr>
    <tr>
      <td width="30%"><b>Library Name:</b></td>
      <td width="70%"><c:out value="${thisGrant.instName}" /></td>
    </tr>
    <tr>
      <td><b>Mailing Address:</b></td>
      <td><c:out value="${thisGrant.addr1}" /></td>
    </tr>
    <tr>
      <td><b>Address:</b></td>
      <td><c:out value="${thisGrant.addr2}" /></td>
    </tr>
    <tr>
      <td ><b>City, State, Zip:</b></td>
      <td ><c:out value="${thisGrant.city}" />
            <c:out value="${thisGrant.state}" />
            <c:out value="${thisGrant.zipcd1}" />  
            <c:out value="${thisGrant.zipcd2}" /></td>
    </tr>
    <tr>
      <td><b>State Senate Districts:</b></td>
      <td><c:forEach var="sdist" items="${distBean.senateDistricts}" >
               <c:out value="${sdist}" />
            </c:forEach></td>
    </tr>
    <tr>
      <td><b>State Assembly Districts:</b></td>
      <td><c:forEach var="dist" items="${distBean.assemblyDistricts}" >
                <c:out value="${dist}" />
            </c:forEach></td>
    </tr>
    <tr>  
      <td colspan="2" align="left">
       <b>Project Description:</b><br/>
       <bean:write name="projNarrative" property="narrative" filter="false"/></td>
    </tr>
    <tr>
      <td><b>Amount Requested: (Project Cost)</b></td>
      <td><fmt:formatNumber value="${thisGrant.totalRequest}" type="currency" maxFractionDigits="0" /></td>
    </tr>
    <tr>
      <td><b>Award Amount:</b></td>
      <td><fmt:formatNumber value="${thisGrant.totalRecommend}" type="currency" maxFractionDigits="0" /></td>
    </tr>
    <tr>
      <td><b>Percentage of Funding Request:</b></td>
      <td><c:out value="${thisGrant.recommendPercentStr}" /></td>
    </tr>
  </table>
  <br/><br/>
    
    
  <html:form action="/saveCnRevJustification">  
  <table align="center" width="95%" class="boxtype" summary="for layout only">
    <tr>
        <td colspan="2">Does the library system deem the public library eligible 
             for a reduced match requirement below 50%?<br/>
             <html:radio property="reduceMatchExists" value="true" />Yes<br/>
             <html:radio property="reduceMatchExists" value="false" />No
             </td>
    </tr>
    
    <c:choose>
    <c:when test="${thisGrant.fycode<15}"><%--before 2014-15; used justification narrative--%>
        <tr>
            <td colspan="2"><hr/>        
            <b>Award Amount Justification:</b><br/>
            <html:textarea property="matchJustification" cols="80" rows="14" />        
            </td>
        </tr>
    </c:when>
    <c:otherwise>    
          <tr>
              <td colspan="2"><b>Reduce Match Criteria</b> (select all that apply):</td>
          </tr>
          <tr>
              <td><html:checkbox property="lunchEligible" />Free & Reduced Lunch Eligibility</td>
              <td><html:checkbox property="povertyRate" />Poverty Rate/Household Income</td>
          </tr>
          <tr>
               <td><html:checkbox property="unemployment" />Unemployment Rate</td>
               <td><html:checkbox property="education" />Education Level</td>  
          </tr>
          <tr>
              <td><html:checkbox property="englishLang" />English Language Learners</td>
              <td><html:checkbox property="housing" />Housing Values/Foreclosure Data</td>
          </tr>
          <tr>
              <td><html:checkbox property="otherRate" />Other</td>
              <td>Other Criteria - Please describe: <html:textarea property="otherDescr" cols="40" rows="4"/> </td>
          </tr>    
    </c:otherwise>
    </c:choose>
        
    <tr>
        <td colspan="2"><html:hidden property="grantId"/>
            <html:hidden property="assignmentId"/>
            <html:submit value="Save"/></td>
    <tr>
    
    
    <tr>
      <td colspan="2" height="30%"><br/></td>
    </tr>
    <tr>
      <td colspan="2"><b>Please attach to each application that is receiving a reduced match a copy of the criteria the system used to determine eligibility for the reduced match.  Please include sources of data used (ex. U.S. Census data, etc.) and any other information deemed applicable.  </b></td>
    </tr>
  </table>
  </html:form>
  
  
  
  </body>
</html>