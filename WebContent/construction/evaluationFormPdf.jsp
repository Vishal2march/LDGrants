<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@ taglib uri="/WEB-INF/pd4ml.tld" prefix="pd4ml" %>
<%@ page contentType="text/html;charset=ISO8859_1"%>
<pd4ml:transform screenWidth="600" encoding="default" pageFormat="LETTER" pageOrientation="portrait" enableImageSplit="false">
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Public Library Construction Evaluation Form</title>
  </head>
  <body>
  
  <font size="1">
  <b>Public Library Construction Evaluation Form</b>
  <table align="center" width="95%" border="1" summary="for layout only">
    <tr>
        <td><b>Project Number:</b></td>
        <td>03<fmt:formatNumber value="${thisGrant.fccode}" />
        -<fmt:formatNumber value="${thisGrant.fycode}" minIntegerDigits="2" />
        -<fmt:formatNumber value="${thisGrant.projseqnum}" pattern="####" minIntegerDigits="4" /></td>
    </tr>
    <tr>
        <td><b>Member Library:</b></td>
        <td><c:out value="${thisGrant.instName}" /></td>
    </tr>
    <tr>
        <td colspan="2" height="20"/>
    </tr>
    <tr>
        <td>1. Cost of Project for Which Funding is Being Requested:</td>
        <td><fmt:formatNumber value="${reviewFormBean.amountRequested}" type="currency" maxFractionDigits="0" /></td>
    </tr>
    
    
    <c:choose>
    <c:when test="${reviewFormBean.fycode <13}">
      <tr>
        <td>2. Amount of Public Library Construction Program Funds requested for this project (cannot be more than
            50% of the amounted in question #1 above :</td>
        <td><fmt:formatNumber value="${reviewFormBean.requestCost}" type="currency" maxFractionDigits="0"/></td>
      </tr>
      <tr>
        <td>3. Maximum Recommendation Amount (50% of question #1 above):</td>
        <td><fmt:formatNumber value="${reviewFormBean.maxRecommendAmt}" type="currency" maxFractionDigits="0"/></td>
      </tr>
    </c:when>
    <c:otherwise>
      <tr>
        <td>2. Amount of Public Library Construction Program Funds requested for this project (cannot be more than
            75% of the amounted in question #1 above :</td>
        <td><fmt:formatNumber value="${reviewFormBean.requestCost}" type="currency" maxFractionDigits="0"/></td>
      </tr>
      <tr>
        <td>3. Maximum Recommendation Amount (75% of question #1 above):</td>
        <td><fmt:formatNumber value="${reviewFormBean.maxRecommendAmt}" type="currency" maxFractionDigits="0"/></td>
      </tr>
    </c:otherwise>
    </c:choose>
    
    
    <tr>
        <td>4. System Allocation:</td>
        <td><fmt:formatNumber value="${reviewFormBean.initialAlloc}" type="currency" maxFractionDigits="0"/></td>
    </tr>
    <tr>
        <td>5. Total Amount Recommended by System to Members:</td>
        <td><fmt:formatNumber value="${reviewFormBean.tallyAmountRecommend}" type="currency" maxFractionDigits="0"/></td>
    </tr>
    <c:if test="${reviewFormBean.tallyAmountRecommend > reviewFormBean.initialAlloc}">
    <tr>
        <td colspan="2"><font color="Red">Warning: The Total Amount Recommended by System to Members
        is more than the System Allocation.</font></td>
    </tr>
    </c:if>
    <tr>
        <td colspan="2" height="20"/>
    </tr>
    <tr>
        <td><b>Project Recommended for Funding:</b></td>
        <td><c:out value="${reviewFormBean.projectrecommend}" /></td>
    </tr>
    <tr>
        <td><b>Recommended Amount:</b></td>
        <td><fmt:formatNumber value="${reviewFormBean.amtrecommended}" type="currency" maxFractionDigits="0" />  </td>
    </tr>
  </table>
  </font>
  <br/>
  
  <font size="1">
  <table align="center" width="95%" border="1" summary="for layout only">
      <tr bgcolor="Silver">
        <th colspan="3">Listed below are the criteria and requirements
                        extracted from Commissioner's Regulations &sect; 90.12</th>
        <th width="5%">Meet Criteria</th>
        <th width="50%">Evaluation Comments</th>
      </tr>
      <tr>
        <td width="10%">90.12 (b)(2)</td>
        <td width="5%">l.</td>
        <td width="25%">How will this project assist the applicant to provide
                        more effective service within the system's standards of
                        organization and service?</td>
        <td width="5%"><c:out value="${reviewFormBean.questionone}" /></td>
        <td width="50%"><c:out value="${reviewFormBean.commentone}"/></td>
      </tr>
      <tr>
        <td width="10%">90.12 (c)(1)</td>
        <td width="5%">2.</td>
        <td width="25%">When this project is completed, will adequate operating
                        support and resources be available to sustain an
                        improved level of service?</td>
        <td width="5%"><c:out value="${reviewFormBean.questiontwo}" /></td>
        <td width="50%"><c:out value="${reviewFormBean.commenttwo}" /></td>
      </tr>
      <tr>
        <td width="10%">90.12 (c)(2)</td>
        <td width="5%">3.</td>
        <td width="25%">Is total cost of the project, minus the grant award
                        amount, available?</td>
        <td width="5%"><c:out value="${reviewFormBean.questionthree}" /></td>
        <td width="50%"><c:out value="${reviewFormBean.commentthree}" /></td>
      </tr>
      <tr>
        <td width="10%">90.12 (c)(2)</td>
        <td width="5%">4.</td>
        <td width="25%">Can this project be under contract to start within 180
                        days of project funding approval?</td>
        <td width="5%"><c:out value="${reviewFormBean.questionfour}"/></td>
        <td width="50%"><c:out value="${reviewFormBean.commentfour}" /></td>
      </tr>
      <tr>
        <td width="10%">90.12 (c)(2)</td>
        <td width="5%">5.</td>
        <td width="25%">Can this project be completed promptly in accordance
                        with the application?</td>
        <td width="5%"><c:out value="${reviewFormBean.questionfive}" /></td>
        <td width="50%"><c:out value="${reviewFormBean.commentfive}" /></td>
      </tr>
      <tr>
        <td width="10%">90.12 (c)(3) (c) (5)</td>
        <td width="5%">6.</td>
        <td width="25%">Is the project being conducted in accordance with all
                        applicable laws and are required competitive bidding
                        procedures being followed?</td>
        <td width="5%"><c:out value="${reviewFormBean.questionsix}"/></td>
        <td width="50%"><c:out value="${reviewFormBean.commentsix}" /></td>
      </tr>
      <tr>
        <td width="10%">90.12 (d)(1)</td>
        <td width="5%">7.</td>
        <td width="25%">Will this project increase and improve building space
                        and capacity?</td>
        <td width="5%"><c:out value="${reviewFormBean.questionseven}"/></td>
        <td width="50%"><c:out value="${reviewFormBean.commentseven}" /></td>
      </tr>
      <tr>
        <td width="10%">90.12 (d)(1) (d) (3)</td>
        <td width="5%">8.</td>
        <td width="25%">Will the project result in new library programs and
                        increased user accommodations including access by
                        individuals with disabilities?</td>
        <td width="5%"><c:out value="${reviewFormBean.questioneight}"/></td>
        <td width="50%"><c:out value="${reviewFormBean.commenteight}"/></td>
      </tr>
      <tr>
        <td width="10%">90.12 (d)(2)</td>
        <td width="5%">9.</td>
        <td width="25%">Will the project result in such economies as increased
                        energy conservation and greater operational efficiency?</td>
        <td width="5%"><c:out value="${reviewFormBean.questionnine}"/></td>
        <td width="50%"><c:out value="${reviewFormBean.commentnine}" /></td>
      </tr>
      <tr>
        <td width="10%">90.12 (d)(4)</td>
        <td width="5%">10.</td>
        <td width="25%">Will the project extend library service in communities
                        that are geographically isolated?</td>
        <td width="5%"><c:out value="${reviewFormBean.questionten}" /></td>
        <td width="50%"><c:out value="${reviewFormBean.commentten}"/></td>
      </tr>
      <tr>
        <td width="10%">90.12 (d)(4)</td>
        <td width="5%">11.</td>
        <td width="25%">Will the project extend library service in communities
                        that are economically disadvantaged?</td>
        <td width="5%"><c:out value="${reviewFormBean.questioneleven}" /></td>
        <td width="50%"><c:out value="${reviewFormBean.commenteleven}"/></td>    
      </tr>
      
      <c:if test="${thisGrant.fycode>16}"><%--starting FY 2016-17 --%>
          <tr>
            <td width="10%">90.12 (e)(vi)</td>
            <td width="5%">12.</td>
            <td width="25%">Will this project have the capacity to deliver internet services at a 
            connectivity rate in accordance with recommended Program Guidelines?</td>
            <td width="5%"><c:out value="${reviewFormBean.questiontwelve}" /></td>
            <td width="50%"><c:out value="${reviewFormBean.commenttwelve}"/></td>    
          </tr>
      </c:if>
    </table>    
    </font>
  
  
  <pd4ml:page.break />
  
 
   <font size="1">
   <table align="center" width="95%" border="1" summary="for layout only">
    <tr>
        <th colspan="2">Reduced Match Justification Form</th>
    </tr>
    <tr>
        <td colspan="2">
        <b>This form is completed by library system staff.</b>
           This form is required to be completed and submitted as part of the online 
           application by library system staff for each public library deemed eligible 
           by the library system for a reduced match requirement below 
           50%.  State aid for public library construction can be provided 
           for ?up to seventy-five percent of the total project approved costs for 
           buildings of public libraries that are located in an economically 
           disadvantaged community?, as determined by the library system.  <br/><br/>Public 
           libraries that are located in communities that are not identified by the 
           library system as economically disadvantaged communities are eligible for 
           a maximum of fifty percent of the total project approved costs.</td>
    </tr>
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
  
    
  <table align="center" width="95%" border="1" summary="for layout only">
    <tr>
        <td>Does the library system deem the public library eligible 
             for a reduced match requirement below 50%?</td>
        <td><c:out value="${assignBean.reduceMatchExists}" /></td>
    </tr>
    
    <c:choose>
    <c:when test="${thisGrant.fycode<15}"><%--before 2014-15; used justification narrative--%>
          <tr>
              <td><b>Award Amount Justification:</b></td>
              <td><c:out value="${assignBean.matchJustification}"/></td>
          </tr>
    </c:when>
    <c:otherwise> 
          <tr>
              <td colspan="2"><b>Reduce Match Criteria</b> (select all that apply):</td>
          </tr>
          <tr>
              <td>Free & Reduced Lunch Eligibility:  <c:out value="${assignBean.lunchEligible}" /></td>
              <td>Poverty Rate/Household Income:  <c:out value="${assignBean.povertyRate}" /></td>
          </tr>
          <tr>
               <td>Unemployment Rate:  <c:out value="${assignBean.unemployment}" /></td>
               <td>Education Level:  <c:out value="${assignBean.education}" /></td>  
          </tr>
          <tr>
              <td>English Language Learners:  <c:out value="${assignBean.englishLang}" /></td>
              <td>Housing Values/Foreclosure Data:  <c:out value="${assignBean.housing}" /></td>
          </tr>
          <tr>
              <td>Other:  <c:out value="${assignBean.otherRate}" /></td>
              <td>Other Criteria - Please describe: <c:out value="${assignBean.otherDescr}"/> </td>
          </tr>           
    </c:otherwise>
    </c:choose>
    
    <tr>
      <td colspan="2" height="30%"><br/></td>
    </tr>
    <tr>
      <td colspan="2"><b>Please attach to each application that is receiving a reduced match a copy of the criteria the system used to determine eligibility for the reduced match.  Please include sources of data used (ex. U.S. Census data, etc.) and any other information deemed applicable.  </b></td>
    </tr>
    
  </table>
  </font>
  
  </body>
</html>
</pd4ml:transform>