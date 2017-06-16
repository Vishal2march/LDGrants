<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%><%--
 * @author  Stefanie Husak
 * @version 2.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the Application        :  saCoverSheet.jsp
 * Creation/Modification History  :
 *     SLowe               Created
 *     SHusak   3/1/07     Modified
 *
 * Description
 * This is the coversheet for grant appliction.  The institution info and PO is prefilled from 
 * sedref. The user enters a summary description only if the initial app has not been 
 * submitted yet.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>saCoverSheet.jsp</title>
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

<h4>Coversheet</h4>
       

    <table width="80%" border="1" align="center" summary="for layout only" class="graygrid">
      <tr>
        <td width="30%" >Sponsoring Institution:</td>
        <td width="70%" >
          <c:out value="${thisGrant.instName}" />
        </td>
      </tr>
      <tr>
        <td>Mailing Address:</td>
        <td><c:out value="${thisGrant.addr1}" /></td>
      </tr>
      <tr>
        <td>Address:</td>
        <td ><c:out value="${thisGrant.addr2}" /></td>
      </tr>
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
        <td>Director of Library:</td>
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
        
        
    <table border="1" width="80%" align="center" summary="for layout only" class="graygrid">
      <tr>
        <th colspan="2" bgcolor="Silver">Preservation Program Officer</th>
      </tr>
      
      <tr>
        <td width="30%">Name</td>
        <td >
         <c:out value="${presOfficerBean.salutation}" /> <c:out value="${presOfficerBean.fname}"/> <c:out value="${presOfficerBean.mname}"/> <c:out value="${presOfficerBean.lname}"/> 
       </td>
      </tr>
      <tr>
        <td>Title</td>
        <td><c:out value="${presOfficerBean.title}" /></td>
      </tr>
       <tr>
        <td>Address:</td>
        <td><c:out value="${thisGrant.addr1}" /></td>
      </tr>
      <tr>
        <td>City, State, Zip:</td>
        <td><c:out value="${thisGrant.city}" />
          <c:out value="${thisGrant.state}" />
          <c:out value="${thisGrant.zipcd1}" />
        </td>
      </tr>
      <tr>
        <td>Phone</td>
        <td><c:out value="${presOfficerBean.phone}" /></td>
      </tr>
      <tr>
        <td>Email</td>
        <td><c:out value="${presOfficerBean.email}" /></td>
      </tr>
    </table>
     
     
    
    <html:form action="/saveSaNarrative" >      
        <table width="80%" align="center" summary="for layout only" class="boxtype">
          <tr>
            <td bgcolor="Silver">
              Summary description of proposed preservation activities: (5-10 sentences).
              The summary should be brief, but should provide a clear, publishable statement of 
              how you intend to use State Aid funds.
            </td>
          </tr>
          
            <c:choose >
            <c:when test="${lduser.prgsa=='read' || appStatus.coversheetyn=='true' || appStatus.pendingReview=='true' }">
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
                <html:hidden property="module" value="sa" />
                <html:submit value="Save" /></td>
              </tr>
            </c:otherwise>
            </c:choose>   
            
        </table>   
    </html:form>
  

 </body>
</html>
