<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>coversheet</title>
  </head>
  <body>
  
  
  <h4>Coversheet</h4>
       

    <table width="80%" border="1" align="center" summary="for layout only" class="graygrid">
      <tr>
        <td width="30%" >Institution:</td>
        <td width="70%" >
          <c:out value="${thisGrant.instName}" />
        </td>
      </tr>
      <tr>
        <td>Mailing Address:</td>
        <td><c:out value="${thisGrant.addr1}" /></td>
      </tr>
     <%-- <tr>
        <td>Address:</td>
        <td ><c:out value="${thisGrant.addr2}" /></td>
      </tr>--%>
      <tr>
        <td >City, State, Zip:</td>
        <td >
          <c:out value="${thisGrant.city}" />
          <c:out value="${thisGrant.state}" />
          <c:out value="${thisGrant.zipcd1}" />  
          <c:out value="${thisGrant.zipcd2}" />
        </td>
      </tr>
      <tr>
        <td>Director:</td>
        <td >
          <c:out value="${libDirectorBean.salutation}" /> <c:out value="${libDirectorBean.fname}" />
          <c:out value="${libDirectorBean.mname}" /> <c:out value="${libDirectorBean.lname}" />
        </td>
      </tr>
      <tr>          
        <td>Title:</td>
        <td ><c:out value="${libDirectorBean.title}" /></td>
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
        
         
    
   <%--per BLilley 10/22/14; no summary narrative for stateaid
    <html:form action="/saveAidNarrative" >      
        <table width="80%" align="center" summary="for layout only" class="boxtype">
          <tr>
            <td bgcolor="Silver">
              Summary description of proposed activities: (5-10 sentences).
              The summary should be brief, but should provide a clear, publishable statement of 
              how you intend to use State Aid funds.
            </td>
          </tr>
          
            <c:choose >
            <c:when test="${lduser.prgNycStateaid=='read' || appStatus.coversheetyn=='true' || appStatus.pendingReview=='true' }">
              <tr>
                <td><bean:write name="projNarrative" property="narrative" filter="false" /></td>
              </tr>
            </c:when>
            <c:otherwise >
              <tr>
                <td align="center">  
                  <div name="myToolBars" class="mceToolbarExternal"></div>
                  <html:textarea property="narrative" cols="80" rows="12" />
                 </td>
              </tr>
              <tr>
                <td align="center"><html:hidden property="id" /><html:hidden property="narrTypeID" />
                <html:hidden property="module" value="staid" />
                <html:submit value="Save" /></td>
              </tr>
            </c:otherwise>
            </c:choose>   
            
        </table>   
    </html:form>--%>
  
  
  </body>
</html>