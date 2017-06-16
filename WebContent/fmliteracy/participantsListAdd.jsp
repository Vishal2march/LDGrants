<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title></title>
  </head>
  <body>
  
  <h4>Coordination Partnering Organization</h4>  
  
  <table class="boxtype" align="center" width="90%" summary="for layout only" >
  <c:choose>
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
  
     
  <html:errors />
  <html:form action="/flSaveNarrative" >         
    <table width="80%" align="center" summary="for layout only" >
        <tr>
          <th>Institutions Participating in this Project</th>
        </tr>
                          
        <c:choose >
        <c:when test="${lduser.readaccess=='true' || appStatus.projdescyn=='true' || appStatus.pendingReview=='true'}" > 
          <tr>   
            <td><bean:write name="projNarrative" property="narrative" filter="false" /></td>
          </tr>
        </c:when>
        <c:otherwise >
          <tr>
            <td align="center">
              <html:textarea property="narrative" cols="60" rows="12"/>
            </td>
          </tr>
          <tr>          
            <td align="center"><html:hidden property="id" /> <html:hidden property="narrTypeID" />
            <html:hidden property="module" /><html:hidden property="narrativeDescr"/>
            <html:hidden property="narrativeTitle"/>
            <html:submit value="Save" /></td>
          </tr>                              
        </c:otherwise>
        </c:choose>              
        
      </table>
    </html:form>  
  
  </body>
</html>