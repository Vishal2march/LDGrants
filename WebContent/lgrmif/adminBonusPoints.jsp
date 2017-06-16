<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>untitled</title>
  </head>
  <body>
  
  <h5>LGRMIF Admin - Update Application Form</h5>
  
  <table width="80%" summary="for layout only">
    <tr>
      <td>Project Number</td>
      <td>05<fmt:formatNumber value="${thisGrant.fccode}" />
          -<fmt:formatNumber value="${thisGrant.fycode}" minIntegerDigits="2" />
          -<fmt:formatNumber value="${thisGrant.projseqnum}" pattern="####" minIntegerDigits="4"/>
      </td>
    </tr>
    <tr>
      <td>Institution</td>
      <td><c:out value="${thisGrant.instName}" /> -<c:out value="${thisGrant.dorisname}" /></td>
    </tr>
    <tr>
      <td height="25"/>
    </tr>
  
    <html:form action="/updateBonusPts">
    <tr>
      <td>Project Category</td>
      <td><html:select property="projcategoryId">
              <html:optionsCollection name="dropDownLists" property="category" value="id" label="description" />
           </html:select></td>
    </tr>
    <tr>
        <td height="20"/>
    </tr>
    <tr>
        <td><a href="docs/lgrmif/countiesregion.htm" target="_blank">Region</a></td>
        <td><html:select property="govtRegionId">
              <html:option value="0">Choose Region...</html:option>
              <html:optionsCollection name="dropDownLists" property="regions" value="id" label="description" />
            </html:select>
        </td>
      </tr>
      <tr>
        <td height="20"/>
      </tr>
      <tr>
        <td><a href="docs/lgrmif/govtType.htm" target="_blank">Type</a></td>
        <td><html:select property="govtTypeId">
                <html:option value="0">Choose Type...</html:option>
                <html:optionsCollection name="dropDownLists" property="govttypes" value="id" label="description" />
            </html:select></td>
      </tr>
      
    <c:choose>
    <c:when test="${coversheetBean.fycode<15}">
        <tr>
          <td>1. Cooperative Project (10 Points)</td>
          <td><html:checkbox property="cooperative"/></td>
        </tr>
        <tr>
          <td>2. 1st Time Inventory & Planning (5 points)</td>
          <td><html:checkbox property="inventory" /></td>
        </tr>
        <tr>
          <td>3. Electronic records inventory projects (5 points)</td>
          <td><html:checkbox property="recordsmgmt" /></td>
        </tr>
        <tr>
          <td>4. Email Management projects (5 points)</td>
          <td><html:checkbox property="emailmgmt" /></td>
        </tr>
        <tr>
          <td>Total Bonus Points</td>
          <td><c:out value="${coversheetBean.score}"/></td>
        </tr>   
    </c:when>
    <c:otherwise>
        <tr>
          <td colspan="2">Bonus Points no longer used</td>
        </tr>
       <tr>
          <td>1. Cooperative Project (10 Points)</td>
          <td><html:checkbox property="cooperative" disabled="true"/>
              <html:hidden property="cooperative"/></td>
        </tr>
        <tr>
          <td>2. 1st Time Inventory & Planning (5 points)</td>
          <td><html:checkbox property="inventory"  disabled="true"/>
              <html:hidden property="inventory"/></td>
        </tr>
        <tr>
          <td>3. Electronic records inventory projects (5 points)</td>
          <td><html:checkbox property="recordsmgmt"  disabled="true"/>
              <html:hidden property="recordsmgmt"/></td>
        </tr>
        <tr>
          <td>4. Email Management projects (5 points)</td>
          <td><html:checkbox property="emailmgmt"  disabled="true"/>
              <html:hidden property="emailmgmt"/></td>
        </tr>
        <tr>
          <td>Total Bonus Points</td>
          <td><c:out value="${coversheetBean.score}"/></td>
        </tr>   
    
    </c:otherwise>
    </c:choose>
    <tr>
        <td height="20"/>
    </tr>
    <tr>
        <td>Application Type:</td>
        <td><html:radio property="applicationType" value="1" />Individual &nbsp;&nbsp;&nbsp; 
            <html:radio property="applicationType" value="2" />Demonstration &nbsp;&nbsp;&nbsp;
            <html:radio property="applicationType" value="3"/>Shared Services </td>
      </tr>
      
       <tr>
        <td height="20"/>
      </tr>    
      <tr>
        <th colspan="2" bgcolor="Silver">Project Director (PD)</th>
      </tr>          
      <tr>
        <td>First Name</td>
        <td><html:text property="fname" /></td>
      </tr>
      <tr>
        <td>Last Name</td>
        <td><html:text property="lname" /></td>
      </tr>
      <tr>
        <td>Title</td>
        <td><html:text property="title" maxlength="50" /></td>
      </tr>        
      <tr>
        <td>Phone (###-###-####)</td>
        <td><html:text property="phone" /></td>
      </tr>
      <tr>
        <td>Phone Extension</td>
        <td><html:text property="phoneext" /> <html:hidden property="phoneextId" /></td>
      </tr>
      <tr>
        <td>Email</td>
        <td><html:text property="email" /><html:hidden property="pmId" /></td>
      </tr>
      <tr>
        <td height="20"/>
      </tr>
      <tr>
        <th colspan="2" bgcolor="Silver">Records Management Officer (RMO)</th>
      </tr>          
      <tr>
        <td>First Name</td>
        <td><html:text property="rmofname" /></td>
      </tr>
      <tr>
        <td>Last Name</td>
        <td><html:text property="rmolname" /></td>
      </tr>
      <tr>
        <td>Title</td>
        <td><html:text property="rmotitle" maxlength="50" /></td>
      </tr>        
      <tr>
        <td>Phone (###-###-####)</td>
        <td><html:text property="rmophone" /></td>
      </tr>
      <tr>
        <td>Phone Extension</td>
        <td><html:text property="rmophoneext" /> <html:hidden property="rmophoneextId" /></td>
      </tr>
      <tr>
        <td>Email</td>
        <td><html:text property="rmoemail" /><html:hidden property="rmoId" /></td>
      </tr>
      
    <tr>
      <td colspan="2"><html:hidden property="govtId"/>
                      <html:hidden property="fycode"/>
                      <html:hidden property="grantid" /><html:submit value="Save"/></td>
    </tr>
    </html:form>
  </table>
        
        
  <br/>
  <c:url var="backURL" value="lgAdminNav.do">
    <c:param name="id" value="${thisGrant.grantid}" />
    <c:param name="item" value="grant" />
  </c:url>
  <p align="center"><a href='<c:out value="${backURL}" />' >Back to Application Checklist</a></p>
  
  
  </body>
</html>
