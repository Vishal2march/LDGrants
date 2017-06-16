<%--
 * @author  Stefanie Husak
 * @version 2.0
 * Development Environment        :  JDeveloper 10g
 * Name of the Application        :  adminReports.jsp
 * Creation/Modification History  :
 *     
 *     SHusak       6/15/07     Created
 *
 * Description
 * This page  has options for internal jsp reports, some using DisplayTag tables. 
 * Used for sa/co/di admin. Modified 6/29/09 to remove sql:datasource and query tag.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>untitled</title>
  </head>
  <body>
  
    
  <h4>Admin Reports</h4>
  
  <form method="POST" action="cpAdminReport.do" target="_blank">
  <table border="1" align="center" width="80%" summary="for layout only" class="graygrid">
    <tr>
      <th>C/P Statutory and Coordinated grant reports will open in a new HTML window</th>
    </tr>
    
     <tr>
        <td><b>***Select a Fiscal Year:</b><br/>
        Fiscal year <select name="fy" >
                              <c:forEach var="row" items="${dropDownList}">
                                  <option value="<c:out value='${row.id}' />">
                                    <c:out value="${row.description}" /></option>
                              </c:forEach>              
                              </select></td>
    </tr>
    <tr>
        <td height="20"/>
    </tr>
    
    <tr>
        <td><b>***Select a Report:</b></td>
    </tr>
    
    <tr>
      <td><input type="RADIO" name="item" value="revcontact" checked="checked"/>
          Coordinated Reviewer Contact Information</td>
    </tr>
    <tr>
      <td><input type="RADIO" name="item" value="availability"/>
          Coordinated Reviewer Availability and Number of Assignments for Fiscal Year</td>
    </tr>
    
    <tr>
      <td><input type="RADIO" name="item" value="revassign" />
          Reviewer's assigned to Coordinated projects for Fiscal Year</td>
    </tr>
    
    <tr>
      <td><input type="RADIO" name="item" value="revscores" />
          Reviewer scores/comments for <b>Coordinated </b> Project
          <br/> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                Last 4 digits of the Project Number (ex. 0307-07-<b>0951</b>) 
                <input type="TEXT" name="projnum" /><br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                <input type="CHECKBOX" name="showrev" value="Y" />Show reviewer names
        </td>
    </tr>    
    
    <tr>
      <td><input type="RADIO" name="item" value="scoresrpt" />
          Coordinated Projects in score order for Fiscal Year</td>
    </tr>
    
    <tr>
        <td><input type="radio" name="item" value="coordrequestallyears"/>
        Coordinated Projects in score order for Fiscal Year, with Amount Requested
        for each year of project <font color="red">*New</font></td>
    </tr>
    
    <tr>
      <td><input type="RADIO" name="item" value="statutoryappr"/>
          Amount Approved for Statutory Aid for Fiscal Year</td>
    </tr>    
    
    
    <tr>
      <td>
        <input type="RADIO" name="item" value="appdeny" />
        Coordinated Aid Grants Denied for Fiscal year</td>
    </tr>
    
    <tr>
      <td><input type="RADIO" name="item" value="coordappr" />
          Amount Approved for Coordinated Aid for Fiscal year period
          <br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;   Beginning fiscal year <select name="sfycode">
                <c:forEach var="row" items="${dropDownList}">
                    <option value="<c:out value='${row.id}' />">
                    <c:out value="${row.description}" /></option>
                </c:forEach>   
               </select>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;End fiscal year <select name="efycode">
                <c:forEach var="row" items="${dropDownList}">
                    <option value="<c:out value='${row.id}' />">
                    <c:out value="${row.description}" /></option>
                </c:forEach>   
               </select></td>
    </tr>    
       
    <tr>
      <td align="center">
      <input type="hidden" name="fc" value="7"/><%-- fccode for coordinated--%>
      <input type="SUBMIT" value="View Report" /></td>
    </tr>
  </table>
  </form>
  <br/><br/>
  
  
  
   
  
  <form action="cpAdminReport.do" method="POST" target="_blank">
  <table width="80%" align="center" summary="for layout only" border="1" class="graygrid">
    <tr>
      <th>C/P Discretionary grant reports will open in a new HTML window</th>
    </tr>    
     <tr>
        <td><b>***Select a Fiscal Year:</b><br/>
        Fiscal year <select name="fy" >
                              <c:forEach var="row" items="${dropDownList}">
                                  <option value="<c:out value='${row.id}' />">
                                    <c:out value="${row.description}" /></option>
                              </c:forEach>              
                              </select></td>
    </tr>
    <tr>
        <td height="20"/>
    </tr>
    
    <tr>
        <td><b>***Select a Report:</b></td>
    </tr>
    
    <tr>
      <td><input type="RADIO" name="item" value="revcontact" checked="checked"/>
          Discretionary Reviewer Contact Information</td>
    </tr>
    <tr>
      <td><input type="RADIO" name="item" value="availability"/>
          Discretionary Reviewer Availability and Number of Assignments for Fiscal Year</td>
    </tr>
      
    <tr>
      <td><input type="RADIO" name="item" value="revassign" />
          Reviewer's assigned to Discretionary projects for Fiscal Year</td>
    </tr>
    
    <tr>
      <td><input type="RADIO" name="item" value="revscores" />
          Reviewer scores/comments for <b>Discretionary </b> Project
          <br/> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                Last 4 digits of Project Number (ex. 0305-07-<b>0951</b>) 
                <input type="TEXT" name="projnum" /><br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                <input type="CHECKBOX" name="showrev" value="Y" />Show reviewer names
        </td>
    </tr>
    
    <tr>
      <td><input type="RADIO" name="item" value="newapps" />
          Discretionary Applicants for Fiscal Year</td>
    </tr>
    
    
    <tr>
      <td><input type="RADIO" name="item" value="scoresrpt" />
          Discretionary Projects in score order for Fiscal Year</td>
    </tr>
    
    <tr>
      <td><input type="RADIO" name="item" value="awardlist" />
          Discretionary Aid award list for Fiscal year</td>
    </tr> 
       
    <tr>
      <td>
        <input type="RADIO" name="item" value="appdeny" />
        Discretionary Grants Denied for Fiscal year</td>
    </tr>
    
    <tr>
        <td><input type="RADIO" name="item" value="nonsubgrants" />
            Applications started but not submitted for Fiscal year
            </td>
    </tr>
    
    <tr>
      <td><input type="RADIO" name="item" value="blankavail" />
          Blank Form - Discretionary Reviewer Availability</td>
    </tr>
    
    <tr>
      <td><input type="RADIO" name="item" value="blankassign" />
          Blank Form - Assign Discretionary Reviewers</td>
    </tr>
    <tr>
      <td height="20">&nbsp;</td>
    </tr>
    <tr>
      <td align="center">
      <input type="hidden" name="fc" value="5"/>
      <input type="submit" value="View Report" /></td>
    </tr>
  </table>
  </form>
  
  
  <br/><br/>
  
  <form action="cpAdminReport.do" method="POST" target="_blank">
  <table width="80%" align="center" summary="for layout only" class="graygrid">  
    <tr>
      <td height="20">&nbsp;</td>
    </tr>
    <tr>
      <th>New Reports: Searches Filtered by Fiscal Year Range</th>
    </tr>
    <tr>
      <td>Select a start and end Fiscal Year Range for the reports below:<br/>
            Start fiscal year <select name="stitlefy">
                <c:forEach var="row" items="${dropDownList}">
                    <option value="<c:out value='${row.description}' />">
                    <c:out value="${row.description}" /></option>
                </c:forEach>   
               </select>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;End fiscal year <select name="etitlefy">
                <c:forEach var="row" items="${dropDownList}">
                    <option value="<c:out value='${row.description}' />">
                    <c:out value="${row.description}" /></option>
                </c:forEach>   
               </select>      
      </td>
    </tr>
    <tr>
      <td height="20"><hr/></td>
    </tr>
    
    <tr>
      <td><input type="RADIO" name="item" value="titleyearsearch" checked="checked" />
          Search Project Titles, Filter by Fiscal Year Range<br/>
          Title keyword <input type="TEXT" name="titleyear" />          
          </td>
    </tr>
    <tr>
      <td height="10"><hr/></td>
    </tr>
    <tr>
      <td><input type="RADIO" name="item" value="multititleyearsearch" />
          Search Project Titles using 2 keywords, Filter by Fiscal Year Range<br/>
          
          Title keyword 1 <input type="TEXT" name="titleyear1" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  
          <select name="operator">
            <option value="and">AND</option>
            <option value="or">OR</option>
          </select>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  
          Title keyword 2 <input type="text" name="titleyear2"/>
          </td>
    </tr>
    <tr>
      <td height="10"><hr/></td>
    </tr>
    <tr>
      <td><input type="RADIO" name="item" value="educproject" />
          Search "Education" Projects, Filter by Fiscal Year Range      
          </td>
    </tr>
    <tr>
      <td height="10"><hr/></td>
    </tr>
        
    <tr>
      <td align="center">
      <input type="hidden" name="fc" value="5"/>
      <input type="submit" value="View Report" /></td>
    </tr>
  </table>
  </form>
  
  <br/><br/>
  <form action="cpAdminReport.do" method="POST" target="_blank">
  <table summary="for layout only" width="80%" align="center" border="1" class="graygrid">
    <tr>
        <td><b>Reports available to CP Discretionary Applicants</b></td>
    </tr>
    <tr>
        <td><input type="radio" name="item" value="fysearch" checked="true"/>
          View Discretionary Award List by Fiscal Year<br/> 
          Fiscal year <select name="fy">
                  <c:forEach var="row" items="${dropDownList}">
                      <option value="<c:out value='${row.id}' />">
                      <c:out value="${row.description}" /></option>
                  </c:forEach>   
                 </select>
        </td>
    </tr>
    
    <tr>
        <td><input type="radio" name="item" value="titlesearch"/>
            Search Discretionary projects by Title<br/>
            Title <input type="TEXT" name="title" />
        </td>
    </tr>
    
    <tr>
        <td><input type="radio" name="item" value="instsearch"/>
            Search Discretionary projects by Institution Name<br/>
            Institution <input type="text" name="inst"/>
    </tr>
    
    <tr>
      <td align="center">
      <input type="hidden" name="fc" value="5"/>
      <input type="submit" value="View Report" /></td>
    </tr>  
  </table>
  </form>
  <br/>
  </body>
</html>
