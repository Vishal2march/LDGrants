<%--
 * @author  Stefanie Husak
 * @version 2.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the Application        :  discReports.jsp
 * Creation/Modification History  :
 *     
 *     SHusak    5/27/08     Created
 *
 * Description
 * This jsp report allows DI apcnt to view all approved DI grants for selected fy, or search by
 * DI title to list all matching DI grant info. 
--%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<html  xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>C/P Discretionary grant data</title>
    <link href="http://usny.nysed.gov/css_js/nysedmain.css" rel="stylesheet" type="text/css" />
    <link href="http://usny.nysed.gov/css_js/content.css" rel="stylesheet" type="text/css" />
    <link href="http://www.nysl.nysed.gov/css_js/nysl.css" rel="stylesheet" type="text/css" />   
    <link href="http://www.nysl.nysed.gov/libdev/css_js/libdev.css" rel="stylesheet" type="text/css" />
  </head>
  <body>
    
  
  <div id="subpage_container">
  <div id="header"> 		
    <div id="nysedlogo"><a href="http://www.nysed.gov"><img src="http://usny.nysed.gov/images/nysedlogosub.jpg" alt="NYSED Logo" /></a><a href="#content_column" class="skip">Skip To Content</a></div>
    <div id="deptlogo"><a href="http://www.nysl.nysed.gov"><img src="http://www.nysl.nysed.gov/images/nysllogo.gif" alt="NYSL LOGO" width="77" height="49" /></a></div>
    <div id="header_wrapper">
      <div id="depthead"><h1><a href="http://www.nysl.nysed.gov/">New York State Library</a></h1></div>
      <div id="darkbox"></div>  
    </div> 
  </div>
	
	<div id="content">	   	  
	  <div id="sub_body">
      <div id="breadcrumb">
        <a href="http://www.nysed.gov/"><acronym title="NYS Education Department">NYSED</acronym></a> / <a href="http://www.oce.nysed.gov/"><acronym title="Office of Cultural Education">OCE</acronym></a> / <a href="http://www.nysl.nysed.gov/"><acronym title="New York State Library">NYSL</acronym></a> / <a href="http://www.nysl.nysed.gov/libdev/">Library Development</a> /  
        <!-- InstanceBeginEditable name="Edit_breadcrumb1" -->Conservation Preservation Discretionary<!-- InstanceEndEditable -->					
        <hr/>
      </div>
      
      <div id="content_column">   
  
  
  <h1 class="paragraph_heading">C/P Discretionary Data</h1> 
  <p>Find information about prior year C/P Discretionary grant projects</p>  
   
  <table width="100%" summary="for layout only">
    <tr>
      <td><form method="POST" action="DiPublicReport" >
          View C/P Discretionary Aid award list for a Fiscal year<br/> 
          <input type="HIDDEN" name="task" value="rpt" />
          Fiscal year <select name="awardfy">
                  <c:forEach var="row" items="${dropDownList}">
                      <option value="<c:out value='${row.id}' />">
                      <c:out value="${row.description}" /></option>
                  </c:forEach>   
                 </select>
            <input type="SUBMIT" value="Search" />
         </form></td>
         
      <td><form method="POST" action="DiPublicReport" >   
            View Discretionary Aid projects by Title<br/>
            <input type="HIDDEN" name="task" value="search" />
            Title <input type="TEXT" name="title" />
            <input type="SUBMIT" value="Search" />
          </form></td>
    </tr>
  </table>
  
    
  <logic:notEmpty name="allGrants">
  <br/><h1 class="paragraph_heading">Search Results</h1>
      
  <table width="100%" border="1" summary="for layout only">          
    <c:forEach var="row" items="${allGrants}" >
      <tr>
        <td>
          <table>
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
              <td>20<fmt:formatNumber value="${row.fycode}" minIntegerDigits="2" /></td>
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
  
      </div><!--end contentcolumn-->
    </div><!--end subbody-->    
  </div><!--end content-->

    
  <div id="footer">
		<a href="http://www.nysl.nysed.gov/libdev/ldroster.htm">Contact</a> | <a href="http://www.nysl.nysed.gov/libdev/ldteam.htm">FAQ</a>
    <h3>The New York State Library is a unit within the</h3>
    <h3>University of the State of New York - New York State Education Department</h3>
  </div> 

</div>  
  </body>
</html>
