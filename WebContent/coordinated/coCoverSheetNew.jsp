<%--
 * @author  Stefanie Husak
 * @version 2.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the Application        :  coCoverSheet.jsp
 * Creation/Modification History  :
 *     SHusak   7/11/07     Modified
 *
 * Description
 * This is the coversheet for grant appliction.  The institution info is prefilled from 
 * sedref. The user enters a summary description, and chooses a project manager or
 * preservation officer, and lists any participating libraries.
--%>
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
  
  
  <h4>Cover Sheet</h4>
 
      
    <table width="85%" align="center" border="1" class="graygrid" summary="for layout only">
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
        <td ><c:out value="${thisGrant.addr2}" /></td>
      </tr>
      <tr>
        <td >City, State, Zip:</td>
        <td ><c:out value="${thisGrant.city}" />
            <c:out value="${thisGrant.state}" />
            <c:out value="${thisGrant.zipcd1}" />  
            <c:out value="${thisGrant.zipcd2}" /></td>
      </tr>
      <tr>
        <td>Director of Library:</td>
        <td>
          <c:out value="${libDirectorBean.fname}" /> <c:out value="${libDirectorBean.mname}" /> 
          <c:out value="${libDirectorBean.lname}" />
        </td>
      </tr>
      <tr>
        <td>Title:</td>
        <td><c:out value="${libDirectorBean.title}" /></td>
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
    <br/>
    
    
    
    
    <c:choose >
    <c:when test="${lduser.prgco=='read' || appStatus.coversheetyn=='true' || appStatus.pendingReview=='true' }">
        
        
        
        
    
    <table border="1" class="graygrid" width="85%" align="center" summary="for layout only">
      <tr>
        <th colspan="2" bgcolor="Silver">Project Manager</th>
      </tr>          
      <tr>
        <td width="30%">Name</td>
        <td width="70%"><c:out value="${coversheetBean.fname}" /> <c:out value="${coversheetBean.mname}" /> 
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
        <td>Email</td>
        <td><c:out value="${coversheetBean.email}" /></td>
      </tr>
      <tr>
        <td>Project Title</td>
        <td><c:out value="${coversheetBean.projectTitle}" /></td>
       </tr>
      </table>
    <br/><br/>
    
    <table border="1" class="graygrid" width="85%" align="center" summary="for layout only">
      <tr>
        <th colspan="2" bgcolor="Silver">Participating Institutions</th>
      </tr>          
      <c:forEach var="partBean" items="${allPartInst}">
        <tr>
          <td><c:out value="${partBean.instName}" /></td>
        </tr>
      </c:forEach>         
    </table>
            
     
     </c:when>
     <c:otherwise >
               
               
    <p><html:errors /></p>
    
    <html:form action="/coSaveCoversheet">    
        <table border="1" class="graygrid" width="85%" align="center" summary="for layout only">
          <tr>
            <th colspan="2" bgcolor="Silver">Project Manager</th>
          </tr>          
          <tr>
            <td width="30%">First Name</td>
            <td width="70%"><html:text property="fname" /></td>
          </tr>
          <tr>
            <td >Middle Name</td>
            <td ><html:text property="mname" /></td>
          </tr>
          <tr>
            <td >Last Name</td>
            <td ><html:text property="lname" />
                <html:hidden property="pmId" /></td>
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
            <td ><html:text property="phoneext" /><html:hidden property="phoneextId" /></td>
          </tr>
          <tr>
            <td>Email</td>
            <td><html:text property="email" /></td>
          </tr>
          <tr>
            <td>Project Title</td>
            <td><html:text property="projectTitle" size="40" /></td>
          </tr>
        </table>
        <br/><br/>
        
        
        <table border="1" class="graygrid" width="85%" align="center" summary="for layout only">
          <tr>
            <th colspan="2" bgcolor="Silver">Participating Institutions</th>
          </tr>
          <tr>
            <td><select name="inst1">
                  <c:forEach var="SedrefInstBean" items="${allInst}">
                    <c:choose >
                    <c:when test="${instBean1.instid==SedrefInstBean.instID}" >
                      <option selected="selected" value='<c:out value="${SedrefInstBean.instID}" />' ><c:out value="${SedrefInstBean.name}" /></option>                      
                    </c:when>
                    <c:otherwise >
                      <option value='<c:out value="${SedrefInstBean.instID}" />' ><c:out value="${SedrefInstBean.name}" /></option>
                    </c:otherwise>
                    </c:choose>
                  </c:forEach>
                </select>                 
            </td>
            <td><select name="inst2" >
                  <c:forEach var="SedrefInstBean" items="${allInst}">
                    <c:choose >
                    <c:when test="${instBean2.instid==SedrefInstBean.instID}" >
                      <option selected="selected" value='<c:out value="${SedrefInstBean.instID}" />' ><c:out value="${SedrefInstBean.name}" /></option>                      
                    </c:when>
                    <c:otherwise >
                      <option value='<c:out value="${SedrefInstBean.instID}" />'><c:out value="${SedrefInstBean.name}" /></option>
                    </c:otherwise>
                    </c:choose>
                  </c:forEach>
                </select>            
            </td>
          </tr>
          <tr>
            <td><select name="inst3" >
                  <c:forEach var="SedrefInstBean" items="${allInst}">
                    <c:choose >
                    <c:when test="${instBean3.instid==SedrefInstBean.instID}" >
                      <option selected="selected" value='<c:out value="${SedrefInstBean.instID}" />' ><c:out value="${SedrefInstBean.name}" /></option>                      
                    </c:when>
                    <c:otherwise >
                      <option value='<c:out value="${SedrefInstBean.instID}" />' ><c:out value="${SedrefInstBean.name}" /></option>
                    </c:otherwise>
                    </c:choose>
                  </c:forEach>
                </select>
            </td>
            <td><select name="inst4" >
                  <c:forEach var="SedrefInstBean" items="${allInst}">
                    <c:choose >
                    <c:when test="${instBean4.instid==SedrefInstBean.instID}" >
                      <option selected="selected" value='<c:out value="${SedrefInstBean.instID}" />' ><c:out value="${SedrefInstBean.name}" /></option>                      
                    </c:when>
                    <c:otherwise >
                      <option value='<c:out value="${SedrefInstBean.instID}" />' ><c:out value="${SedrefInstBean.name}" /></option>
                    </c:otherwise>
                    </c:choose>
                  </c:forEach>
                </select>
            </td>
          </tr>     
          <tr>
            <td><select name="inst5" >
                  <c:forEach var="SedrefInstBean" items="${allInst}">
                    <c:choose >
                    <c:when test="${instBean5.instid==SedrefInstBean.instID}" >
                      <option selected="selected" value='<c:out value="${SedrefInstBean.instID}" />' ><c:out value="${SedrefInstBean.name}" /></option>                      
                    </c:when>
                    <c:otherwise >
                      <option value='<c:out value="${SedrefInstBean.instID}" />' ><c:out value="${SedrefInstBean.name}" /></option>
                    </c:otherwise>
                    </c:choose>
                  </c:forEach>
                </select>
            </td>
            <td><select name="inst6"  >
                  <c:forEach var="SedrefInstBean" items="${allInst}">
                    <c:choose >
                    <c:when test="${instBean6.instid==SedrefInstBean.instID}" >
                      <option selected="selected" value='<c:out value="${SedrefInstBean.instID}" />' ><c:out value="${SedrefInstBean.name}" /></option>                      
                    </c:when>
                    <c:otherwise >
                      <option value='<c:out value="${SedrefInstBean.instID}" />' ><c:out value="${SedrefInstBean.name}" /></option>
                    </c:otherwise>
                    </c:choose>
                  </c:forEach>
                </select>
            </td>
          </tr>     
          <tr>
            <td><select name="inst7" >
                  <c:forEach var="SedrefInstBean" items="${allInst}">
                    <c:choose >
                    <c:when test="${instBean7.instid==SedrefInstBean.instID}" >
                      <option selected="selected" value='<c:out value="${SedrefInstBean.instID}" />' ><c:out value="${SedrefInstBean.name}" /></option>                      
                    </c:when>
                    <c:otherwise >
                      <option value='<c:out value="${SedrefInstBean.instID}" />' ><c:out value="${SedrefInstBean.name}" /></option>
                    </c:otherwise>
                    </c:choose>
                  </c:forEach>
                </select>
            </td>
            <td><select name="inst8"  >
                  <c:forEach var="SedrefInstBean" items="${allInst}">
                    <c:choose >
                    <c:when test="${instBean8.instid==SedrefInstBean.instID}" >
                      <option selected="selected" value='<c:out value="${SedrefInstBean.instID}" />' ><c:out value="${SedrefInstBean.name}" /></option>                      
                    </c:when>
                    <c:otherwise >
                      <option value='<c:out value="${SedrefInstBean.instID}" />' ><c:out value="${SedrefInstBean.name}" /></option>
                    </c:otherwise>
                    </c:choose>
                  </c:forEach>
                </select>
            </td>
          </tr>   
          <tr>
            <td><select name="inst9" >
                  <c:forEach var="SedrefInstBean" items="${allInst}">
                    <c:choose >
                    <c:when test="${instBean9.instid==SedrefInstBean.instID}" >
                      <option selected="selected" value='<c:out value="${SedrefInstBean.instID}" />' ><c:out value="${SedrefInstBean.name}" /></option>                      
                    </c:when>
                    <c:otherwise >
                      <option value='<c:out value="${SedrefInstBean.instID}" />' ><c:out value="${SedrefInstBean.name}" /></option>
                    </c:otherwise>
                    </c:choose>
                  </c:forEach>
                </select>
            </td>
            <td><select name="inst10"  >
                  <c:forEach var="SedrefInstBean" items="${allInst}">
                    <c:choose >
                    <c:when test="${instBean10.instid==SedrefInstBean.instID}" >
                      <option selected="selected" value='<c:out value="${SedrefInstBean.instID}" />' ><c:out value="${SedrefInstBean.name}" /></option>                      
                    </c:when>
                    <c:otherwise >
                      <option value='<c:out value="${SedrefInstBean.instID}" />' ><c:out value="${SedrefInstBean.name}" /></option>
                    </c:otherwise>
                    </c:choose>
                  </c:forEach>
                </select>
            </td>
          </tr>     
        </table>      
    <p align="center"><html:hidden property="module" value="co" /><html:submit value="Save" /></p>
    
  </html:form> 
    
    </c:otherwise>
    </c:choose>
    
 
  </body>
</html>
