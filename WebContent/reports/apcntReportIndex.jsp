<%--
 * @author  Stefanie Husak
 * @version 2.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the Application        :  apcntReportIndex.jsp
 * Creation/Modification History  :
 *     
 *     SHusak    3/24/08     Created
 *
 * Description
 * This jsp report allows DI apcnt to view all approved DI grants for selected fy, or search by
 * DI title to list all matching DI grant info. 12/29/10-also available to cp dg admin
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>untitled</title>
  </head>
  <body>
   
  <h4>C/P Discretionary Data</h4>  
  <p>Find information about prior year C/P Discretionary grant projects</p>  
   
  <table width="100%" summary="for layout only">
    <tr>
      <td><form method="POST" action="diApcntSearch.do?i=fysearch" >
          C/P Discretionary Award List by Fiscal Year<br/> 
          Fiscal year <select name="fy">
                  <c:forEach var="row" items="${dropDownList}">
                      <option value="<c:out value='${row.id}' />">
                      <c:out value="${row.description}" /></option>
                  </c:forEach>   
                 </select>
            <input type="SUBMIT" value="Search" />
         </form></td>
         
      <td><form method="POST" action="diApcntSearch.do?i=titlesearch" >   
            Search C/P Discretionary projects by Title<br/>
            Title <input type="TEXT" name="title" />
            <input type="SUBMIT" value="Search" />
          </form></td>
      
      <td><form method="POST" action="diApcntSearch.do?i=instsearch">
            Search C/P Discretionary projects by Institution<br/>
            Institution Name<input type="text" name="inst"/>
            <input type="submit" value="Search"/>
          </form>      
      </td>
    </tr>
  </table>
  
  
  
  <logic:notEmpty name="allGrants" >
  <br/><hr/>  
  <h5>Search Results</h5>
    
  <table width="100%" border="1" summary="for layout only">  
        
    <c:forEach var="row" items="${allGrants}" >
      <tr>
        <td>
          <table summary="for layout only">
            <tr>
              <td><b>Institution</b></td>
              <td><c:out value="${row.instName}" /></td>
            </tr>
            <tr>
              <td><b>Project</b></td>
              <td><c:out value="${row.title}" /></td>
            </tr>
            <tr>
              <td><b>City</b></td>
              <td><c:out value="${row.city}" /></td>
            </tr>
            <tr>
              <td><b>County</b></td>
              <td><c:out value="${row.county}" /></td>
            </tr>
            <tr>
              <td><b>Award</b></td>
              <td><fmt:formatNumber value="${row.totamtappr}" type="currency" maxFractionDigits="0" /></td>
            </tr>
            <tr>
              <td><b>Year</b></td>
              <td><c:out value="${row.fiscalyear}"/></td>
            </tr>
            <tr>
              <td><b>Project Manager</b></td>
              <td><c:out value="${row.projectManager.fname}" /> <c:out value="${row.projectManager.lname}" /></td>
            </tr>
            <tr>
              <td><b>Phone</b></td>
              <td><c:out value="${row.projectManager.phone}" /></td>
            </tr>
            <tr>
              <td><b>Email</b></td>
              <td><c:out value="${row.projectManager.email}" /></td>
            </tr>
            <tr>
              <td height="20" />
            </tr>
          </table>
        </td>
      </tr>
    </c:forEach>
  
  </table>
  </logic:notEmpty>
  
  </body>
</html>
