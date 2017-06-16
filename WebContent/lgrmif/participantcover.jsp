<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>participantcover</title>
  </head>
  <body>
  
  <h5>Participating Application</h5>
  
  
<table width="90%" align="center" border="1" summary="for layout only">
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
    <td colspan="2"><b>NOTE:</b>The institution and director information listed above
    is data that is pulled from the SEDREF database.  
    <a href="http://portal.nysed.gov/portal/pls/portal/SED.sed_inst_qry_vw$.startup" target="_blank">SEDREF</a>
    is the single authoritative 
    source of identifying information about institutions which the NYS Education Department
    determines compliance with applicable policy, law and/or regulation. <br/><br/>
    If your institution or director information is incorrect, it can only be updated once
    your Payee Information Form is received by Grants Administration Unit and approved
    by Grants Finance.  Grant Unit staff do not have authority to update SEDREF information.</td>
  </tr>
  <tr>
    <td colspan="2" height="20"/>
  </tr>
  <tr>
    <td colspan="2" align="center">
    <b>Please use the following links to <i>print</i> or <i>save</i> the application
    to your desktop:</b><br/><br/>
                  
    <a href="PrintAppServlet?i=cover" target="_blank">Application Sheet HTML</a>
    &nbsp;&nbsp;&nbsp;<a href="PrintAppServlet?i=narr" target="_blank">Narratives HTML</a>
    &nbsp;&nbsp;&nbsp;<a href="PrintAppServlet?i=budget&a=false" target="_blank">Budget HTML</a><br/><br/>
                                          
    <a href="PrintAppServlet?i=coverpdf" target="_blank">Application Sheet PDF</a>
    &nbsp;&nbsp;&nbsp;<a href="PrintAppServlet?i=narrpdf" target="_blank">Narratives PDF</a>
    &nbsp;&nbsp;&nbsp;<a href="PrintAppServlet?i=budgetpdf&a=false" target="_blank">Budget PDF</a>

    </td>
  </tr>
</table>
         
  
  </body>
</html>