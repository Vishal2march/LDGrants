<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>vendorQuote</title>
  </head>
  <body>
  
  <h4>Vendor Quote</h4>
  
  <p>To add a new Vendor Quote record, either select your vendor name from the List of Vendors
  or Add a new Vendor to the list.</p>
  
  <a href="lgVendorNav.do?i=loadsearch">List of Vendors</a><br/>
  <a href="LgEditVendor.do">Add new Vendor</a><br/>
  <br/>
  
  
  <c:choose>
  <c:when test="${param.vlist=='true'}">
  
  <table summary="for layout only">
    <tr>
        <th colspan="2">Search by Vendor Name</th>
    </tr>
    <form method="post" action="lgVendorNav.do?i=searchvendor">
    <tr>
        <td>Enter a portion of the Vendor name:</td>
        <td><input type="text" name="vname"/></td>
    </tr>
    <tr>
        <td colspan="2"><input type="submit" value="Search"/></td>
    </tr>
    </form>
    
    <tr>
        <td height="30"/>
    </tr>    
    <c:forEach var="row" items="${vendorlist}">
    <tr>
        <td><c:url var="vurl" value="lgVendorNav.do">
            <c:param name="i" value="selectv"/>
            <c:param name="id" value="${row.id}"/>
            </c:url>
            <c:url var="editurl" value="lgVendorNav.do">
            <c:param name="i" value="vrecord"/>
            <c:param name="id" value="${row.id}"/>
            </c:url>
        <a href='<c:out value="${vurl}" />'>Add to Vendor Quote</a><br/>
        <a href='<c:out value="${editurl}" />'>Update Vendor Info</a></td>
    </tr>
    <tr>
        <td>Name:</td>
        <td><c:out value="${row.name}" /></td>
    </tr>
    <tr>
        <td>State Contract Number:</td>
        <td><c:out value="${row.statecontractnum}"/></td>
    </tr>
    <tr>
        <td>Address:</td>
        <td><c:out value="${row.address}"/></td>
    </tr>
    <tr>
        <td>City:</td>
        <td><c:out value="${row.city}"/></td>
    </tr>
    <tr>
        <td>State:</td>
        <td><c:out value="${row.state}"/></td>
    </tr>
    <tr>
        <td>Zip Code:</td>
        <td><c:out value="${row.zipcode}"/></td>
    </tr>
    <tr>
        <td colspan="2"><hr/></td>
    </tr>
    </c:forEach>
  </table>  
  
  
  
  </c:when> 
  <c:when test="${param.confdel=='true'}">
  
    <form method="POST" action="lgVendorNav.do?i=deletequote">
    Do you want to delete the Vendor Quote record for <c:out value="${param.n}"/> ?<br/>
    <input type="hidden" name="id" value='<c:out value="${param.id}"/>'/>
    <input type="submit" value="Delete"/>
    <input type="button" value="Cancel" onclick="javascript:history.go(-1);" />
    </form>    
  
  </c:when>
  <c:otherwise>
  
      <c:choose >
      <c:when test="${lduser.readaccess=='true' || appStatus.vqimyn=='true' || appStatus.pendingReview=='true' }">
                 
      <table width="100%" summary="for layout only">
        <tr>
            <td colspan="6"><b>Vendor Quote Form</b>
            (<a href="docs/lgrmif/vq.htm" target="_blank">Instructions</a>)
            <br/><br/>Please complete this form to provide evidence that you have contacted 
            three vendors for price quotes, unless you have one quote for
            a State Contract, Preferred Source, or Sole Source service or product.<br/><br/>
            For services rendered, provide more detail in the Budget Category and Narrative form, Code 40</td>
        </tr>
        <tr>
            <td colspan="7" height="20"/>
        </tr>
        <tr>
            <th>Vendor</th><th>Description of Item or Service</th>
            <th>Contract#</th><th>Preferred Vendor</th>
            <th>Sole Source Vendor</th>
            <th>LGPR</th><th>Quoted Price</th><th>Selected Quote</th>
        </tr>
        
        <logic:notEmpty name="AssignCollectionBean" property="allVendorRecords">          
        <c:forEach var="vendorItem" items="${AssignCollectionBean.allVendorRecords}">                      
            <tr>
                <td valign="top"><c:out value="${vendorItem.name}"/></td>
                <td><c:out value="${vendorItem.description}"/></td>
                <td><c:out value="${vendorItem.statecontractnum}"/></td>
                <td><c:out value="${vendorItem.preferredvendor}"/></td>
                <td><c:out value="${vendorItem.solesource}" /></td>    
                <td><c:out value="${vendorItem.procurementreq}"/></td>
                <td><fmt:formatNumber value="${vendorItem.pricequote}" type="currency" minFractionDigits="0"/></td>
                <td><c:out value="${vendorItem.selectedquote}"/></td>        
            </tr>
       </c:forEach>       
            <tr>
                <td colspan="5"><input type="button" value="Save" disabled="disabled"/></td>
            </tr>
       </logic:notEmpty>      
      </table>      
    
      </c:when>
      <c:otherwise>
      
      
      <html:errors />
      <html:form action="/saveLgVendorQuote">
      <table width="100%" summary="for layout only">
        <tr>
            <td colspan="7"><b>Vendor Quote Form</b>
            (<a href="docs/lgrmif/vq.htm" target="_blank">Instructions</a>)
            <br/><br/>Please complete this form to provide evidence 
            that you have contacted three vendors for price quotes, unless you have one quote for
            a State Contract, Preferred Source, or Sole Source service or product.<br/><br/>
            For services rendered, provide more detail in the Budget Category and Narrative form, Code 40</td>
        </tr>
        <tr>
            <td colspan="7" height="20"/>
        </tr>
        <tr>
            <th>Vendor</th><th>Description of Item or Service</th>
            <th>Contract#</th><th>Preferred Vendor</th>
            <th>Sole Source Vendor</th><th>LGPR</th><th>Quoted Price</th><th>Selected Quote</th>
        </tr>
        
        <logic:notEmpty name="AssignCollectionBean" property="allVendorRecords">          
        <logic:present name="AssignCollectionBean" property="allVendorRecords" >
        <logic:iterate name="AssignCollectionBean" property="allVendorRecords" id="vendorItem" >   
            
            <c:url var="delurl" value="LgVendorQuote.do">
                <c:param name="confdel" value="true"/>
                <c:param name="id" value="${vendorItem.vendorquoteId}"/>
                <c:param name="n" value="${vendorItem.name}"/>
            </c:url>
            <tr>
                <td valign="top"><c:out value="${vendorItem.name}"/><br/>
                    <a href='<c:out value="${delurl}"/>'>Delete</a> this vendor quote record
                    <html:hidden name="vendorItem" property="id" indexed="true"/>
                    <html:hidden name="vendorItem" property="grantid" indexed="true"/>
                    <html:hidden name="vendorItem" property="vendorquoteId" indexed="true"/>
                    <html:hidden name="vendorItem" property="name" indexed="true"/></td>
                <td><html:textarea name="vendorItem" property="description" indexed="true" rows="5" cols="30"/></td>
                <td><c:out value="${vendorItem.statecontractnum}" />
                    <html:hidden name="vendorItem" property="statecontractnum" indexed="true"/></td>
                <td><html:radio name="vendorItem" property="preferredvendor" value="true" indexed="true"/>Yes<br/>
                    <html:radio name="vendorItem" property="preferredvendor" value="false" indexed="true"/>No</td>
                <td><html:radio name="vendorItem" property="solesource" value="true" indexed="true"/>Yes<br/>
                    <html:radio name="vendorItem" property="solesource" value="false" indexed="true"/>No</td>
            
                <td><html:radio name="vendorItem" property="procurementreq" value="true" indexed="true"/>Yes<br/>
                    <html:radio name="vendorItem" property="procurementreq" value="false" indexed="true"/>No</td>
                <td><html:text name="vendorItem" property="pricequote" indexed="true" size="6"/></td>
                <td><html:radio name="vendorItem" property="selectedquote" value="true" indexed="true"/>Yes<br/>
                    <html:radio name="vendorItem" property="selectedquote" value="false" indexed="true"/>No</td>        
            </tr>
                
       </logic:iterate>
       </logic:present>      
            <tr>
                <td colspan="5"><html:submit value="Save"/></td>
            </tr>
       </logic:notEmpty>
      
      </table> 
      </html:form>
  
    </c:otherwise>
    </c:choose>
    
  
  </c:otherwise>
  </c:choose>
  
  </body>
</html>