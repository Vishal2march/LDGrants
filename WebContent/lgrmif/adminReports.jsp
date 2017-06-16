<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>adminReports</title>
    <script type="text/javascript" src="dwr/interface/panelsearchScript.js"></script>
    <script type="text/javascript" src="dwr/engine.js"></script>
    <script type="text/javascript" src="dwr/util.js"></script>
    <script language="javascript">
    function showPanelsYear(fyid) 
    {
        panelsearchScript.getPanelsByYear(fyid, displayLgPanels);
        document.getElementById("panelId").focus();
    }
    function showPanelsFy(fyid) 
    {
        panelsearchScript.getPanelsByYear(fyid, showLgPanels);
        document.getElementById("panelNum").focus();
    }
    function showPanelsFyAtHome(fyid) 
    {
        panelsearchScript.getPanelsByYear(fyid, showLgPanelsAtHome);
        document.getElementById("panelNumAtHome").focus();
    }
        
    function displayLgPanels(panelList)
    { 
        dwr.util.removeAllOptions("panelId");
        dwr.util.addOptions("panelId", panelList, 'panelid', 'affiliation');
    }  
    function showLgPanels(panelList)
    { 
        dwr.util.removeAllOptions("panelNum");
        dwr.util.addOptions("panelNum", panelList, 'panelid', 'affiliation');
    }   
    function showLgPanelsAtHome(panelList)
    { 
        dwr.util.removeAllOptions("panelNumAtHome");
        dwr.util.addOptions("panelNumAtHome", panelList, 'panelid', 'affiliation');
    }   
    </script> 
  </head>
  <body>
  
    
  <h5>Admin Reports</h5>
  
  <form method="POST" action="lgReports.do" target="_blank">
  <table border="1" width="70%" summary="for layout only">
    <tr>
      <td><b>Reviewer/Assignment Reports</b></td>
    </tr>
    
    <tr>
      <td><input type="RADIO" name="i" value="revcontact" checked="checked"/>
      LGRMIF Reviewer Contact Information</td>
    </tr>
    <tr>
      <td><input type="RADIO" name="i" value="revidinfo"/>
      LGRMIF Active Reviewers with SSN and Vendor ID</td>
    </tr>
    <tr>
      <td><input type="RADIO" name="i" value="availability"/>
           LGRMIF Reviewer Availability for 
        <br/>Fiscal year <select name="fycode" >
                               <c:forEach var="row" items="${dropDownList}">
                                  <option value="<c:out value='${row.id}' />">
                                    <c:out value="${row.description}" /></option>
                              </c:forEach>              
                              </select></td>
    </tr>
    <tr>
      <td><input type="RADIO" name="i" value="revassign" />
      Reviewer/Panel assignments for  
         <br/>Fiscal year <select name="revfycode" >
                              <c:forEach var="row" items="${dropDownList}">
                                  <option value="<c:out value='${row.id}' />">
                                    <c:out value="${row.description}" /></option>
                              </c:forEach>              
                              </select></td>
    </tr>
    <tr>
      <td><input type="RADIO" name="i" value="graassign" />
      Grant/Panel assignments for   
         <br/>Fiscal year <select name="grafycode" >
                              <c:forEach var="row" items="${dropDownList}">
                                  <option value="<c:out value='${row.id}' />">
                                    <c:out value="${row.description}" /></option>
                              </c:forEach>              
                              </select></td>
    </tr>
    <%--12/6/12 hide this report, per FC
    <tr>
        <td><input type="RADIO" name="i" value="nonsubgrants" />
            Applications started but not submitted for<br/>
            Fiscal year <select name="nonsubfy">
                            <c:forEach var="row" items="${dropDownList}">
                                  <option value="<c:out value='${row.id}' />">
                                    <c:out value="${row.description}" /></option>
                              </c:forEach>              
                        </select></td>
    </tr>
    --%>
    <tr>
      <td><input type="radio" name="i" value="revrating"/>
          Reviewer Scores for LGRMIF Project <br/>
          <input type="radio" name="i" value="revcomment"/>
          Reviewer Comments for LGRMIF Project<br/>
          Last 4 digits of the Project Number
          <input type="text" name="pn" /></td>
    </tr>
    
    <tr>
      <td><input type="radio" name="i" value="scoreorder"/>Projects by Panel/Score (Based on panel deliberation)
         <br/>Fiscal year <select name="scorefy" onchange="showPanelsFy(this.value);">
                            <c:forEach var="row" items="${dropDownList}">
                                  <option value="<c:out value='${row.id}' />">
                                    <c:out value="${row.description}" /></option>
                              </c:forEach>              
                        </select>
                        <br/>Panel <select name="panelNum" id="panelNum">
                            <option value="0">Choose...</option>
                        </select></td>
    </tr>
    
    
    <tr>
      <td><input type="radio" name="i" value="scoreorderathome"/>Projects by Panel/Score (Based on at-home evaluation)
         <br/>Fiscal year <select name="scorefyathome" onchange="showPanelsFyAtHome(this.value);">
                            <c:forEach var="row" items="${dropDownList}">
                                  <option value="<c:out value='${row.id}' />">
                                    <c:out value="${row.description}" /></option>
                              </c:forEach>              
                        </select>
                        <br/>Panel <select name="panelNumAtHome" id="panelNumAtHome">
                            <option value="0">Choose...</option>
                        </select></td>
    </tr>
    
    <tr>
        <td><input type="radio" name="i" value="scorerecpanel"/>Reviewer Score/Recommendation for Panel
            <br/>Fiscal Year <select name="scorerecfy" onchange="showPanelsYear(this.value);">
                            <c:forEach var="row" items="${dropDownList}">
                                  <option value="<c:out value='${row.id}' />">
                                    <c:out value="${row.description}" /></option>
                              </c:forEach>              
                        </select>
            <br/>Panel <select name="panelId" id="panelId">
                            <option value="0">Choose...</option>
                        </select>
        </td>
    </tr>
    
    <tr>
        <td><input type="radio" name="i" value="decnotes"/>Decision Notes with RMO/PD
            <br/>Fiscal Year <select name="decnotesfy">
                            <c:forEach var="row" items="${dropDownList}">
                                  <option value="<c:out value='${row.id}' />">
                                    <c:out value="${row.description}" /></option>
                              </c:forEach>              
                        </select>
            <br/>Funding <select name="decnotesfund">
                            <option value="F">Full Fund</option>
                            <option value="M">Modify Fund</option>
                            <option value="N">No Fund</option>
                        </select>
        </td>
    </tr>
    
    
    <tr>
      <td align="center"><input type="SUBMIT" value="View Report" /></td>
    </tr>  
  </table>
  </form>
  
  
  <br/><br/><br/>
  <form method="POST" action="lgReports.do" target="_blank">
  <table border="1" width="70%" summary="for layout only">
    <tr>
        <td><b>Application List/Count Reports</b></td>
    </tr>
    <tr>
        <td><input type="RADIO" name="i" value="awardlist"/>
        Award List- (non DORIS applications)<br/>
        <input type="checkbox" name="dorisaw" value="true"/>DORIS applications only
            <br/>Fiscal year <select name="awardfy" >
                               <c:forEach var="row" items="${dropDownList}">
                                  <option value="<c:out value='${row.id}' />">
                                    <c:out value="${row.description}" /></option>
                              </c:forEach>              
                              </select></td>
    </tr>
    
    <tr>
      <td><input type="RADIO" name="i" value="denylist"/>Denied Applications<br/>
      Fiscal year <select name="denyfy" >
                               <c:forEach var="row" items="${dropDownList}">
                                  <option value="<c:out value='${row.id}' />">
                                    <c:out value="${row.description}" /></option>
                              </c:forEach>              
                              </select></td>
    </tr>
    <tr>
        <td><input type="RADIO" name="i" value="countfmn"/>Count Full/Modified/No Fund 
        Applications<br/>Fiscal year <select name="fmnfy" >
                               <c:forEach var="row" items="${dropDownList}">
                                  <option value="<c:out value='${row.id}' />">
                                    <c:out value="${row.description}" /></option>
                              </c:forEach>              
                              </select></td>
    </tr>
    <tr>
        <td><input type="RADIO" name="i" value="fundlist"/>List of Full/Modified/No Fund 
        Applications with Institution<br/>Fiscal year <select name="fundlistfy" >
                               <c:forEach var="row" items="${dropDownList}">
                                  <option value="<c:out value='${row.id}' />">
                                    <c:out value="${row.description}" /></option>
                              </c:forEach>              
                              </select></td>
    </tr>
    
    <tr>
        <td><input type="RADIO" name="i" value="budgetcateg"/>Awarded projects with amt 
        requested/awarded by budget category
        <br/>Fiscal year <select name="categfy" >
                               <c:forEach var="row" items="${dropDownList}">
                                  <option value="<c:out value='${row.id}' />">
                                    <c:out value="${row.description}" /></option>
                              </c:forEach>              
                              </select></td>
    </tr>
    <tr>
        <td><input type="RADIO" name="i" value="directorrpt"/>
        Applications with no CEO
            <br/>Fiscal year <select name="dirfy" >
                               <c:forEach var="row" items="${dropDownList}">
                                  <option value="<c:out value='${row.id}' />">
                                    <c:out value="${row.description}" /></option>
                              </c:forEach>              
                              </select></td>
    </tr>
    <tr>
        <td><input type="RADIO" name="i" value="bonusptsrpt"/>
        Applications with Bonus Points
            <br/>Fiscal year <select name="bonusfy" >
                               <c:forEach var="row" items="${dropDownList}">
                                  <option value="<c:out value='${row.id}' />">
                                    <c:out value="${row.description}" /></option>
                              </c:forEach>              
                              </select></td>
    </tr>
    <tr>
        <td><input type="RADIO" name="i" value="peoplerpt"/>
        Applications with PM, RMO, CAO
            <br/>Fiscal year <select name="peoplefy" >
                               <c:forEach var="row" items="${dropDownList}">
                                  <option value="<c:out value='${row.id}' />">
                                    <c:out value="${row.description}" /></option>
                              </c:forEach>              
                              </select></td>
    </tr>
    <tr>
        <td><input type="RADIO" name="i" value="summarydescr"/>
        Applications with Summary Description
            <br/>Fiscal year <select name="descfy" >
                               <c:forEach var="row" items="${dropDownList}">
                                  <option value="<c:out value='${row.id}' />">
                                    <c:out value="${row.description}" /></option>
                              </c:forEach>              
                              </select></td>
    </tr>
    
    <tr>
        <td><input type="RADIO" name="i" value="statisticrpt"/>
        Applications with Final Statistical Report Data
            <br/>Fiscal year <select name="statfy" >
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
        <td><b>LGRAC Reports</b></td>
    </tr>
    <tr>
        <td><input type="RADIO" name="i" value="applications"/>
            Applications Received- (non DORIS applications)<br/>
            <input type="checkbox" name="doris" value="true"/>DORIS applications only
            <br/>Fiscal year <select name="fyapps" >
                               <c:forEach var="row" items="${dropDownList}">
                                  <option value="<c:out value='${row.id}' />">
                                    <c:out value="${row.description}" /></option>
                              </c:forEach>              
                              </select></td>
    </tr>    
    <tr>
        <td><input type="RADIO" name="i" value="categoryapps"/>
        Applications by Category
        <br/>Fiscal year <select name="fycategapp" >
                               <c:forEach var="row" items="${dropDownList}">
                                  <option value="<c:out value='${row.id}' />">
                                    <c:out value="${row.description}" /></option>
                              </c:forEach>              
                              </select></td>
    </tr>
    <tr>
        <td><input type="RADIO" name="i" value="categoryawards"/>
        Count Awards by Category
        <br/>Fiscal year <select name="fycateg" >
                               <c:forEach var="row" items="${dropDownList}">
                                  <option value="<c:out value='${row.id}' />">
                                    <c:out value="${row.description}" /></option>
                              </c:forEach>              
                              </select></td>
    </tr>
    <tr>
        <td><input type="RADIO" name="i" value="regionawards"/>Count Awards by Region
        <br/>Fiscal year <select name="fyreg" >
                           <c:forEach var="row" items="${dropDownList}">
                              <option value="<c:out value='${row.id}' />">
                                <c:out value="${row.description}" /></option>
                          </c:forEach>              
                          </select></td>
    </tr>
    <tr>
        <td><input type="RADIO" name="i" value="countyawards"/>Count Awards by County
            <br/>Fiscal year <select name="fycnty" >
                           <c:forEach var="row" items="${dropDownList}">
                              <option value="<c:out value='${row.id}' />">
                                <c:out value="${row.description}" /></option>
                          </c:forEach>              
                          </select></td>
    </tr>
    <tr>
        <td><input type="RADIO" name="i" value="govtypeawards"/>Count Awards by Government Type
            <br/>Fiscal year <select name="fygovtype" >
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
        <td><b>RAO Reports (these are the reports RAO's and certain reviewers can view)</b></td>
    </tr>
    
    <tr>
        <td><input type="RADIO" name="i" value="regionlist" checked="checked"/>
        Applications for Region including Project Manager
        <br/>Fiscal year <select name="regfy" >
                           <c:forEach var="row" items="${dropDownList}">
                              <option value="<c:out value='${row.id}' />">
                                <c:out value="${row.description}" /></option>
                          </c:forEach>              
                          </select><br/>
            Region <select name="regnum" >
                           <c:forEach var="row" items="${dropDownRegions}">
                              <option value="<c:out value='${row.id}' />">
                                <c:out value="${row.description}" /></option>
                          </c:forEach>              
                          </select></td>
    </tr>
    
    <tr>
        <td><input type="RADIO" name="i" value="allregion"/>
        Applications for all Regions
        <br/>Fiscal year <select name="allregfy" >
                           <c:forEach var="row" items="${dropDownList}">
                              <option value="<c:out value='${row.id}' />">
                                <c:out value="${row.description}" /></option>
                          </c:forEach>              
                          </select></td>
    </tr>
                          
    <tr>
        <td><input type="RADIO" name="i" value="countylist"/>Applications for County
        <br/>Fiscal year <select name="cofy" >
                           <c:forEach var="row" items="${dropDownList}">
                              <option value="<c:out value='${row.id}' />">
                                <c:out value="${row.description}" /></option>
                          </c:forEach>              
                          </select><br/>
            County <select name="conum" >
                           <c:forEach var="row" items="${dropDownCounties}">
                              <option value="<c:out value='${row.id}' />">
                                <c:out value="${row.description}" /></option>
                          </c:forEach>              
                          </select></td>
    </tr>
    
    <%--the cooprpt and apptyperpt (option cooperative) are the same; 
            3/21/11 FC requested new rpt for filtering on shared/individ projects
    <tr>
        <td><input type="RADIO" name="i" value="cooprpt"/>Cooperative Projects
        <br/>Fiscal year <select name="coopfy" >
                           <c:forEach var="row" items="${dropDownList}">
                              <option value="<c:out value='${row.id}' />">
                                <c:out value="${row.description}" /></option>
                          </c:forEach>              
                          </select></td>
    </tr>--%>
    
    <tr>
        <td><input type="radio" name="i" value="apptyperpt"/>Applications by Application Type<br/>
            <select name="apptype">
                <option value="1">Cooperative</option>
                <option value="2">Shared Services</option>
                <option value="3">Individual</option>
                <option value="4">Demonstration</option>
            </select>
            <br/>Fiscal year <select name="apptypefy" >
                           <c:forEach var="row" items="${dropDownList}">
                              <option value="<c:out value='${row.id}' />">
                                <c:out value="${row.description}" /></option>
                          </c:forEach>              
                          </select></td>    
    </tr>
    
    <tr>
        <td><input type="RADIO" name="i" value="finalrpt"/>Final Report Submit/Not Submit
        <br/>Fiscal year <select name="finalrptfy" >
                           <c:forEach var="row" items="${dropDownList}">
                              <option value="<c:out value='${row.id}' />">
                                <c:out value="${row.description}" /></option>
                          </c:forEach>              
                          </select><br/>
             <select name="finalrptyn">
                <option value="1">Submitted</option>
                <option value="0">Not Submitted</option>
            </select></td>
    </tr>
    <tr>
        <td align="center"><input type="submit" value="View Report"/></td>
    </tr>
  </table>
  </form>
  
  </body>
</html>