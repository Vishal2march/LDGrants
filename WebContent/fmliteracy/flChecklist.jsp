<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
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
  
    
  <h4>Checklist</h4>
  
    
  <c:if test="${appStatus.dateAcceptable=='false'}">
    <font color="Red">Warning:  The due date (<fmt:formatDate value="${appStatus.dueDate}" pattern="MM/dd/yyyy" />)
    for this application has expired. You may not submit a new application for this fiscal year.</font>
  </c:if>
  
  
  <p align="center">Due Date for new applications: <fmt:formatDate value="${appStatus.dueDate}" pattern="MM/dd/yyyy" /></p>
  
  
  <table align="center" summary="for layout only" width="70%">
    <tr>
      <th width="100%" bgcolor="Silver">Application Checklist</th>
    </tr>
    <tr>
      <td>
        <table width="100%" summary="for layout only">
          <tr>
            <th>Project Number</th>
            <th>03<fmt:formatNumber minIntegerDigits="2" pattern="##" type="number" value="${thisGrant.fccode}" />
                    -<fmt:formatNumber value="${thisGrant.fycode}" minIntegerDigits="2" />
                    -<fmt:formatNumber value="${thisGrant.projseqnum}" minIntegerDigits="4" pattern="####" />
            </th>
          </tr>
        </table>
      </td>
    </tr>
    
    
    
    <tr>
      <td>        
        <table width="100%" summary="for layout only" class="boxtype" >
            <tr>                          
              <td>
                <a href="liInitialForms.do?item=coversheet&p=fl" >Cover Page</a>
              </td>
            </tr>  
            
            <%--hide for fy 2016-19 per kbalsen 2/2/16--%>
            <c:if test="${thisGrant.fycode <16}">  
            <tr>                          
              <td>
                <a href="liInitialForms.do?item=partinst&p=fl">Participating Organizations </a>(optional for Family Literacy)
              </td>
            </tr>
            </c:if>
            
            <tr>             
              <td>
                <a href="liInitialForms.do?item=narrative&p=fl" >Narrative</a>
              </td>
            </tr>
                        
            <tr>              
              <td>
                <%--1/20/16 per KBALSEN; hide budget/approp for fy 2016-19; only show edlaw amts--%>
                <c:choose>
                <c:when test="${thisGrant.fycode <16}">   
                
                    <a href="liInitialForms.do?item=budget&p=fl" >Budget</a><br/>                    
                    Appropriation Amount:<br/>                
                    <logic:notEmpty name="allAllocAmounts" >                      
                      <c:forEach var="row" items="${allAllocAmounts}">
                        20<c:out value="${row.fycode}"/>: <fmt:formatNumber value="${row.finalRecommend}" maxFractionDigits="0" type="currency"/><br/>
                      </c:forEach>
                    </logic:notEmpty>
                    
                </c:when>
                <c:otherwise>
                
                    Ed Law Amount:<br/>                    
                    <logic:notEmpty name="allAllocAmounts" >                      
                      <c:forEach var="row" items="${allAllocAmounts}">
                        20<c:out value="${row.fycode}"/>: <fmt:formatNumber value="${row.initialAlloc}" maxFractionDigits="0" type="currency"/><br/>
                      </c:forEach>
                    </logic:notEmpty>
                    
                </c:otherwise>
                </c:choose>
              </td>
            </tr>            
                     
            <tr>              
              <td>
                <a href="liInitialForms.do?item=attachment&p=fl" >Attachments/Uploads</a>
              </td>
            </tr>    
            
            
            <%--1/20/16 per KBALSEN; hide for fy 2016-19--%>
            <c:if test="${thisGrant.fycode <16}">
              <tr>             
                <td>
                  <a href="liInitialForms.do?item=auth&p=fl" >Cover Page Authorization</a> (3 copies)</td>
              </tr>
              <tr>
                <td>
                  <a href="liInitialForms.do?item=bcert&p=fl" >Board Certification</a> (3 copies)
                </td>
              </tr>        
              <tr>              
                <td>
                  <a href="liInitialForms.do?item=fs&p=fl">FS-10</a>
                  (3 Copies of the FS-10 must be completed and mailed for years 1, 2, and 3)
                </td>
              </tr>
            </c:if>
            
            
            <%--2/2/16 per KBALSEN; new cert process for 2016-19--%>
            <c:if test="${thisGrant.fycode >16}">
              <tr>
                <td>
                  <a href="liInitialForms.do?item=certform&p=fl" >Certification Statement</a> - Final step to submit
                </td>
              </tr>     
            </c:if>
            
            
            
           <%-- not needed starting FY 2013-14<tr>              
              <td>
                <html:checkbox property="fs20Comp" />
                <a href="liInitialForms.do?item=payeeinfo&p=fl">Payee Info Form</a>
                (1 Copy of the Payee Info Form must be completed and mailed)
              </td>
            </tr>--%>
            
          <%--1/20/16 per KBALSEN; hide for fy 2016-19--%>
          <c:if test="${thisGrant.fycode <16}">                
            <form action="flSubmitApp.do?i=verifyinitial" method="POST">
            <tr>
              <td align="center">
                <c:choose >
                <c:when  test="${appStatus.allowSubmitInitial=='false' || lduser.prgfl!='submit' || appStatus.dateAcceptable=='false'}">
                  <input type="button" value="Submit" disabled="disabled" />
                </c:when>
                <c:otherwise >        
                  <input type="HIDDEN" name="fund" value="42" />
                  <input type="HIDDEN" name="id" value='<c:out value="${thisGrant.grantid}" />' />
                  <input type="submit" value="Submit" name="btn" />
                </c:otherwise>
                </c:choose>             
              </td>
            </tr>
            </form>
          </c:if>
            
            
          </table>        
      </td>
    </tr>
    <tr>
      <td height="20" />
    </tr>
    
    
    <%--10/10/12 INTERIM REPORT not needed starting fy 2013-14--%>
    <c:if test="${thisGrant.fycode <14}">
    <tr>
      <th width="100%" bgcolor="Silver">Interim Report</th>
    </tr>
    <tr>
      <td>        
        <table width="100%" border="1" summary="for layout only" class="graygrid">
            <tr>      
              <td><a href="liFinalForms.do?item=interim&p=fl" >Interim Narrative</a></td>   
            </tr>            
                                
          <form action="FlSubmit.do?t=interim" method="POST">
            <tr>
              <td align="center">
                <c:choose >
                <c:when test="${appStatus.allowSubmitFinal=='false' || appStatus.interimsubmitted=='true' || lduser.prgfl!='submit'}">
                  <input type="BUTTON" value="Submit" disabled="disabled" />
                </c:when>
                <c:otherwise >
                  <input type="HIDDEN" name="prog" value="fl" />
                  <input type="submit" value="Submit" name="btn" />
                </c:otherwise>
                </c:choose>
                
                <br/>
                <b>Due Date for interim reports: <fmt:formatDate value="${appStatus.interimdueDate}" pattern="MM/dd/yyyy" /></b>
              </td>
            </tr>
          </form>
          </table>        
      </td>
    </tr>    
    </c:if>
    
    
    
    
    <%--1/20/16 per KBALSEN; hide for fy 2016-19.  new format for final checklist for 16-19--%>
    <c:choose>
    <c:when test="${thisGrant.fycode <16}">
    
        
    <tr>
      <td height="20" />
    </tr>    
    <tr>
      <td>        
        <table width="100%" summary="for layout only" class="boxtype">
            <tr>
              <th width="100%" bgcolor="Silver">Final Report Checklist</th>
            </tr>
            <tr>      
              <td>
                <a href="liFinalForms.do?item=finalrpt&p=fl" >Final Report Narrative</a>
                (For each year of the project)</td>
            </tr>
            <tr>
              <td>              
              <a href="liFinalForms.do?item=statisticsrpt&p=fl">Final Report Statistics</a>
              (For each year of the project)</td>   
            </tr>            
            
            <tr>   
              <td>
                <a href="liFinalForms.do?item=budget&p=fl" >Budget</a> (Actual Expenses - populates FS-10-F form)
              </td>
            </tr>            
                                            
            <tr>                            
               <td>
                 <a href="liFinalForms.do?item=finalauth&p=fl" >Final Report Director Authorization</a>
                 (For each year of the project)
               </td>
            </tr>
          
            <tr>
              <td>
                <a href="liFinalForms.do?item=fs&p=fl">FS-10-F</a>
                (3 Copies of the FS-10-F must be 
                completed and mailed for each year of the project)</td>
            </tr>
                      
              
              
          <form action="flSubmitApp.do?i=verifyfinal" method="POST">
            <tr>
              <td align="center">
                <c:choose >
                <c:when test="${appStatus.allowSubmitFinal=='false' || lduser.prgfl!='submit'}">
                  <input type="BUTTON" value="Submit Year 1 Final Report" disabled="disabled" />
                </c:when>
                <c:otherwise >
                  <input type="HIDDEN" name="fund" value="42" />
                  <input type="HIDDEN" name="id" value='<c:out value="${thisGrant.grantid}" />' />
                  <input type="submit" value="Submit Year 1 Final Report" name="btn" />
                </c:otherwise>
                </c:choose>
              </td>
            </tr>
          </form>       
          
          <tr>
            <td height="10"/>
          </tr>
          
          <form action="flSubmitApp.do?i=verifyFinalYr2" method="POST">
            <tr>
              <td align="center">
                <c:choose >
                <c:when test="${appStatus.allowSubmitFinal2=='false' || lduser.prgfl!='submit'}">
                  <input type="BUTTON" value="Submit Year 2 Final Report" disabled="disabled" />
                </c:when>
                <c:otherwise >
                  <input type="HIDDEN" name="fund" value="42" />
                  <input type="HIDDEN" name="id" value='<c:out value="${thisGrant.grantid}" />' />
                  <input type="HIDDEN" name="fy" value='<c:out value="${thisGrant.fycode+1}" />' />
                  <input type="submit" value="Submit Year 2 Final Report" name="btn" />
                </c:otherwise>
                </c:choose>
              </td>
            </tr>
          </form>  
          
          <tr>
            <td height="10"/>
          </tr>
          
          <form action="flSubmitApp.do?i=verifyFinalYr3" method="POST">
            <tr>
              <td align="center">
                <c:choose >
                <c:when test="${appStatus.allowSubmitFinal3=='false' || lduser.prgfl!='submit'}">
                  <input type="BUTTON" value="Submit Year 3 Final Report" disabled="disabled" />
                </c:when>
                <c:otherwise >
                  <input type="HIDDEN" name="fund" value="42" />
                  <input type="HIDDEN" name="id" value='<c:out value="${thisGrant.grantid}" />' />
                  <input type="HIDDEN" name="fy" value='<c:out value="${thisGrant.fycode+2}" />' />
                  <input type="submit" value="Submit Year 3 Final Report" name="btn" />
                </c:otherwise>
                </c:choose>
                <br/>
                <b>Due Date for final reports: <fmt:formatDate value="${appStatus.finaldueDate}" pattern="MM/dd/yyyy" /></b>
              </td>
            </tr>
          </form>   
          </table>        
      </td>
    </tr>       
    
    
    </c:when>
    <c:otherwise> <%--end if fy<16 statement --%>
        
    
    <tr>
      <td>        
        <table width="100%" summary="for layout only" class="boxtype">
            <tr>
              <th width="100%" bgcolor="Silver">Year 1 Reporting Checklist</th>
            </tr>
            <tr>      
              <td>
                <a href="familyLitYr1FinalNarrative.do" >Final Narrative</a></td>
            </tr>            
            <tr>   
              <td><a href="familyLitYr1ContractedServices.do">Project Budget</a></td>
            </tr>  
            <tr>   
              <td><a href="familyLitYr1Statistics.do">Final Report Statistics</a> (only for Family Literacy Program)</td>
            </tr>   
            
            <tr>                            
              <td><a href="familyLitYr1FinalSignoff.do">Yearly Final Report Sign-off</a?</td>
            </tr> 
            <form action="FlSubmit.do?t=final" method="POST">              
                  <tr>
                    <td align="center">
                      <c:choose >
                      <c:when test="${appStatus.finalnarrativeComp=='true'}">
                        <input type="BUTTON" value="Submit" disabled="disabled" />
                      </c:when>
                      <c:otherwise >
                        <input type="HIDDEN" name="prog" value="fl" />
                        <input type="submit" value="Submit" name="final" />
                      </c:otherwise>
                      </c:choose>                
                    </td>
                  </tr>
                 </form>    
            </table>        
      </td>
    </tr>  
    <tr>
      <td height="20"/>
    </tr>
    <tr>
      <td>        
        <table width="100%" summary="for layout only" class="boxtype">
            <tr>
              <th width="100%" bgcolor="Silver">Year 2 Reporting Checklist</th>
            </tr>
            <tr>      
              <td>
                <a href="liFinalForms.do?item=finalrpt&p=fl&y=2" >Final Narrative</a></td>
            </tr>            
            <tr>   
              <td>Project Budget</td>
            </tr>  
            <tr>   
              <td>Final Report Statistics (only for Family Literacy Program)</td>
            </tr>   
            
            <tr>                            
              <td>Final Report Authorization</td>
            </tr>   
             <!-- Button should be added later on 
               <form action="FlSubmit.do?t=final2" method="POST">              
                  <tr>
                    <td align="center">
                      <c:choose >
                      <c:when test="${appStatus.finalnarrativeComp=='true'}">
                        <input type="BUTTON" value="Submit" disabled="disabled" />
                      </c:when>
                      <c:otherwise >
                        <input type="HIDDEN" name="prog" value="fl" />
                        <input type="submit" value="Submit" name="final2" />
                      </c:otherwise>
                      </c:choose>                
                    </td>
                  </tr>
                 </form> -->
            </table>        
      </td>
    </tr>  
    <tr>
      <td height="20"/>
    </tr>
    <tr>
      <td>        
        <table width="100%" summary="for layout only" class="boxtype">
            <tr>
              <th width="100%" bgcolor="Silver">Year 3 Reporting Checklist</th>
            </tr>
            <tr>      
              <td>
                <a href="liFinalForms.do?item=finalrpt&p=fl&y=3" >Final Narrative</a></td>
            </tr>            
            <tr>   
              <td>Project Budget</td>
            </tr>  
            <tr>   
              <td>Final Report Statistics (only for Family Literacy Program)</td>
            </tr>   
            
            <tr>                            
              <td>Final Report Authorization</td>
            </tr> 
             <!-- Button should be added later on 
            <form action="FlSubmit.do?t=final3" method="POST">              
                  <tr>
                    <td align="center">
                      <c:choose >
                      <c:when test="${appStatus.finalnarrativeComp=='true'}">
                        <input type="BUTTON" value="Submit" disabled="disabled" />
                      </c:when>
                      <c:otherwise >
                        <input type="HIDDEN" name="prog" value="fl" />
                        <input type="submit" value="Submit" name="final3" />
                      </c:otherwise>
                      </c:choose>                
                    </td>
                  </tr>
                 </form>  -->    
            </table>        
      </td>
    </tr>    
    
    </c:otherwise>
    </c:choose>
    
    
    
    
          
      <tr>
        <td height="20"/>
      </tr>
     
      <tr>
          <td>        
            <table width="100%" summary="for layout only" class="boxtype">
              <tr>
                <th width="100%" bgcolor="Silver">FS-10-A Budget Amendments (Optional)</th>
              </tr>
              <tr>
                  <td><a href="liFinalForms.do?item=fs10a&p=fl">Amendment Summary</a> 
                  Please contact Carol A. Desch (carol.desch@nysed.gov) before submitting any amendment information.</td>
              </tr>       
              <tr>
                <td height="10%"/>
              </tr>
              <tr>
                 <td><a href="liFinalForms.do?item=fs&p=fl">FS-10-A</a> 
                 FS-10-A must be submitted by mid-May each year, in order to be considered.<br/>
                 (3 copies completed and mailed only if there is an amendment to the approved project budget)</td>
               </tr>
               <form action="FlSubmit.do?t=amend" method="POST">              
                  <tr>
                    <td align="center">
                      <c:choose >
                      <c:when test="${appStatus.fs10aComp=='true'}">
                        <input type="BUTTON" value="Submit" disabled="disabled" />
                      </c:when>
                      <c:otherwise >
                        <input type="HIDDEN" name="prog" value="fl" />
                        <input type="submit" value="Submit" name="amend" />
                      </c:otherwise>
                      </c:choose>                
                    </td>
                  </tr>
                 </form>      
            </table>
          </td>
      </tr>          
      
      
    <%--1/20/16 per KBALSEN; hide for fy 2016-19--%>
    <c:if test="${thisGrant.fycode <16}">
        <tr>
            <td height="25"/>
        </tr>  
        <tr>
          <td>
            <table width="100%" class="boxtype" summary="for layout only">       
                <tr>
                  <td width="100%" bgcolor="Silver" align="center"><b>Project Manager Update</b><br/>
                    If Project Manager or Additional Contact information has changed, use the link below
                    to update the data.</td>
                </tr>
                <tr>
                    <td><a href="liFinalForms.do?item=viewProjManager&p=fl">Project Manager/Contact Update</a> 
                    (May be completed anytime during the project)<br/>&nbsp;</td> 
                </tr>
            </table>
          </td>
        </tr>    
    </c:if>
    
    
    
    
    
    <tr>
        <td height="25"/>
    </tr>  
    <tr>
      <td>
        <table width="100%" class="boxtype" summary="for layout only">
          <tr>
            <td align="center"><a href="liFinalForms.do?item=status&p=fl">View Application Submission/Approvals</a><br/><br/>                
                
                <b>Please use the following links to <i>print</i> or <i>save</i> your completed application to your desktop:</b><br/><br/>
                
                <a href="PrintAppServlet?i=cover" target="_blank">Coversheet HTML</a>
          &nbsp;&nbsp;&nbsp;<a href="PrintAppServlet?i=narr" target="_blank">Narratives HTML</a>
          
          <%--1/20/16 per KBALSEN; hide for fy 2016-19--%>
          <c:if test="${thisGrant.fycode <16}">
              &nbsp;&nbsp;&nbsp;<a href="PrintAppServlet?i=budget&a=false" target="_blank">Budget HTML</a>
              &nbsp;&nbsp;&nbsp;<a href="PrintAppServlet?i=ifnarr&a=false" target="_blank">Final Narratives HTML</a>
          </c:if>
          
          <br/><br/>
                
                <a href="PrintAppServlet?i=coverpdf" target="_blank">Coversheet PDF</a>
                &nbsp;&nbsp;&nbsp;<a href="PrintAppServlet?i=narrpdf" target="_blank">Narratives PDF</a>
          
          <%--1/20/16 per KBALSEN; hide for fy 2016-19--%>
          <c:if test="${thisGrant.fycode <16}">
            &nbsp;&nbsp;&nbsp;<a href="PrintAppServlet?i=budgetpdf&a=false" target="_blank">Budget PDF</a>
            &nbsp;&nbsp;&nbsp;<a href="PrintAppServlet?i=ifnarrpdf&a=false" target="_blank">Final Narratives PDF</a>
          </c:if>
          
            </td>
          </tr>
        </table>
      </td>
    </tr>        
  </table>
  
    
  </body>
</html>
