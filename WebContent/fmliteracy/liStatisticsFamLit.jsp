<%--
 * @author  Stefanie Husak
 * Development Environment        :  JDeveloper 11g
 * Name of the Application        :  liStatisticsFamLit.jsp
 * Description:
 * For Family Lit starting FY2013-14, this is new applicant statistics form, 
 * uses new save action.  read-only version is final rpt is submitted
--%>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title></title>
  </head>
  <body>
  <br/>
  <table width="95%" align="center" class="boxtype" summary="for layout only">
    <tr>
      <th colspan="2">Family Literacy Library Services<br/>Project Statistics</th>
    </tr>
    <tr>
      <td><b>Project Number</b></td>
      <td>03<fmt:formatNumber minIntegerDigits="2" value="${thisGrant.fccode}" />
          -<fmt:formatNumber value="${thisGrant.fycode}" minIntegerDigits="2" />
          -<fmt:formatNumber  minIntegerDigits="4" pattern="####" value="${thisGrant.projseqnum}" /></td>
    </tr> 
    <tr>
      <td><b>Institution:</b></td>
      <td><c:out value="${thisGrant.instName}" /></td>
    </tr>
  </table><br/>
  
  
  
  
  <c:choose >
  <c:when test="${lduser.readaccess=='true' || appStatus.allowSubmitFinal3=='false'}" > 
  
  <%-- READ ONLY VERSION --%>
  
  <table width="95%" align="center" class="boxtype" summary="for layout only">
      <tr>
        <td colspan="2">Complete all that are applicable</td>
      </tr>
      <tr>
        <td width="80%"></td>
        <td><b>Year 1</b></td>
        <td><b>Year 2</b></td>
        <td><b>Year 3</b></td>
        <td><b>Totals</b></td>
      </tr>
      <tr>
        <td>A.	Number of libraries and/or branches in the system that offered a 
        summer reading program for children and/or teens this year?</td>
        <td><c:out value="${StatBean.sites}" /></td>
        <td><c:out value="${StatBean.sites2}" /></td>
        <td><c:out value="${StatBean.sites3}" /></td>
        <td><c:out value="${StatBean.sites + StatBean.sites2 + StatBean.sites3}"/></td>
      </tr>
      <tr>
        <td>Plan to offer next year?</td>
        <td><c:out value="${StatBean.planSites}" /></td>
        <td><c:out value="${StatBean.planSites2}" /></td>
        <td><c:out value="${StatBean.planSites3}" /></td>
        <td><c:out value="${StatBean.planSites + StatBean.planSites2 + StatBean.planSites3}"/></td>
      </tr>
      <tr>
        <td>B. Number of libraries and/or branches in the system that used the 
        CSLP children's slogan for this year?</td>
        <td><c:out value="${StatBean.childSlogan}"/></td>
        <td><c:out value="${StatBean.childSlogan2}"/></td>
        <td><c:out value="${StatBean.childSlogan3}"/></td>
        <td><c:out value="${StatBean.childSlogan + StatBean.childSlogan2 + StatBean.childSlogan3}"/></td>
      </tr>
      <tr>
        <td>Plan to use the CSLP Children’s slogan for next year?</td>
        <td><c:out value="${StatBean.planChildSlogan}" /></td>
        <td><c:out value="${StatBean.planChildSlogan2}" /></td>
        <td><c:out value="${StatBean.planChildSlogan3}" /></td>
        <td><c:out value="${StatBean.planChildSlogan + StatBean.planChildSlogan2 + StatBean.planChildSlogan3}"/></td>
      </tr>
      <tr>
        <td>C. Number of libraries and/or branches in the system that used 
        the CSLP teen slogan for this year? </td>
        <td><c:out value="${StatBean.teenSlogan}"/></td>  
        <td><c:out value="${StatBean.teenSlogan2}"/></td> 
        <td><c:out value="${StatBean.teenSlogan3}"/></td> 
         <td><c:out value="${StatBean.teenSlogan + StatBean.teenSlogan2 + StatBean.teenSlogan3}"/></td>
      </tr>
      <tr>
        <td>Plan to use the CSLP teen slogan for next year?</td>
        <td><c:out value="${StatBean.planTeenSlogan}" /></td>  
        <td><c:out value="${StatBean.planTeenSlogan2}" /></td> 
        <td><c:out value="${StatBean.planTeenSlogan3}" /></td> 
        <td><c:out value="${StatBean.planTeenSlogan + StatBean.planTeenSlogan2 + StatBean.planTeenSlogan3}"/></td>
      </tr>
      <tr>
        <td>D. TOTAL number of children who registered for the summer 
        reading program this year</td>
        <td><c:out value="${StatBean.participants}" /></td>  
        <td><c:out value="${StatBean.participants2}" /></td> 
        <td><c:out value="${StatBean.participants3}" /></td> 
        <td><c:out value="${StatBean.participants + StatBean.participants2 + StatBean.participants3}"/></td>
      </tr>
      <tr>
        <td>E. TOTAL number of teens who registered 
        for the summer reading program this year</td>
        <td><c:out value="${StatBean.teenParticipants}" /></td>   
        <td><c:out value="${StatBean.teenParticipants2}" /></td>
        <td><c:out value="${StatBean.teenParticipants3}" /></td>
        <td><c:out value="${StatBean.teenParticipants + StatBean.teenParticipants2 + StatBean.teenParticipants3}"/></td>
      </tr>
      <tr>
        <td height="20" />
      </tr>
      
      <tr>
        <td height="20" />
      </tr>
      <tr>
        <td><b>F. Reading totals for this year:</b><br/>For participants recording by time read:</td>
      </tr>  
      <tr>
        <td>1. TOTAL number of children who recorded by minutes read</td>
        <td><c:out value="${StatBean.numChildMinutes}" /></td>  
        <td><c:out value="${StatBean.numChildMinutes2}" /></td>  
        <td><c:out value="${StatBean.numChildMinutes3}" /></td>  
        <td><c:out value="${StatBean.numChildMinutes + StatBean.numChildMinutes2 + StatBean.numChildMinutes3}"/></td>
      </tr>
      <tr>
        <td>2. TOTAL minutes read by these children</td>
        <td><c:out value="${StatBean.childMinutesRead}" /></td> 
        <td><c:out value="${StatBean.childMinutesRead2}" /></td> 
        <td><c:out value="${StatBean.childMinutesRead3}" /></td> 
        <td><c:out value="${StatBean.childMinutesRead + StatBean.childMinutesRead2 + StatBean.childMinutesRead3}"/></td>
      </tr>
      <tr>
        <td>3. TOTAL number of teens who recorded by minutes read</td>
        <td><c:out value="${StatBean.numTeenMinutes}" /></td>
        <td><c:out value="${StatBean.numTeenMinutes2}" /></td>
        <td><c:out value="${StatBean.numTeenMinutes3}" /></td>
        <td><c:out value="${StatBean.numTeenMinutes + StatBean.numTeenMinutes2 + StatBean.numTeenMinutes3}"/></td>
      </tr>
      <tr>
        <td>4. TOTAL minutes read by these teens</td>
        <td><c:out value="${StatBean.teenMinutesRead}" /></td>  
        <td><c:out value="${StatBean.teenMinutesRead2}" /></td>  
        <td><c:out value="${StatBean.teenMinutesRead3}" /></td>  
        <td><c:out value="${StatBean.teenMinutesRead + StatBean.teenMinutesRead2 + StatBean.teenMinutesRead3}"/></td>
      </tr>
      <tr>
        <td>For participants recording by books read:</td>
      </tr>
      <tr>
        <td>5. TOTAL number of children who recorded by books read</td>
        <td><c:out value="${StatBean.numChildBooks}" /></td> 
        <td><c:out value="${StatBean.numChildBooks2}" /></td> 
        <td><c:out value="${StatBean.numChildBooks3}" /></td> 
        <td><c:out value="${StatBean.numChildBooks + StatBean.numChildBooks2 + StatBean.numChildBooks3}"/></td>
      </tr>
      <tr>
        <td>6. TOTAL number of books children read</td>
        <td><c:out value="${StatBean.childBooksRead}" /></td>
        <td><c:out value="${StatBean.childBooksRead2}" /></td>
        <td><c:out value="${StatBean.childBooksRead3}" /></td>
        <td><c:out value="${StatBean.childBooksRead + StatBean.childBooksRead2 + StatBean.childBooksRead3}"/></td>
      </tr>
      <tr>
        <td>7. TOTAL number of teens who recorded by books read</td>
        <td><c:out value="${StatBean.numTeenBooks}" /></td>  
        <td><c:out value="${StatBean.numTeenBooks2}" /></td>  
        <td><c:out value="${StatBean.numTeenBooks3}" /></td>  
        <td><c:out value="${StatBean.numTeenBooks + StatBean.numTeenBooks2 + StatBean.numTeenBooks3}"/></td>
      </tr>
      <tr>
        <td>8. TOTAL number of books teens read</td>
        <td><c:out value="${StatBean.teenBooksRead}" /></td>
        <td><c:out value="${StatBean.teenBooksRead2}" /></td>
        <td><c:out value="${StatBean.teenBooksRead3}" /></td>
        <td><c:out value="${StatBean.teenBooksRead + StatBean.teenBooksRead2 + StatBean.teenBooksRead3}"/></td>
      </tr>
      <tr>
        <td height="20"/>
      </tr>
      <tr>
        <td height="20"/>
      </tr>
      <tr>
        <Td><b>G. Programs</b><br/>A program is defined as one planned 
        session conducted by a staff member, outside performer or other 
        programmer – it does not include informal visits to the library 
        to report on reading, etc.  Please answer each question or 
        type 0 where a question does not apply to your system.</td>
      </tr>
      <tr>
        <td>1. How many total programs did libraries in the system 
        offer for children during summer of this year?</td>
        <td><c:out value="${StatBean.childPrograms}" /></td> 
        <td><c:out value="${StatBean.childPrograms2}" /></td> 
        <td><c:out value="${StatBean.childPrograms3}" /></td> 
        <td><c:out value="${StatBean.childPrograms + StatBean.childPrograms2 + StatBean.childPrograms3}"/></td>
      </tr>
      <tr>
        <td>Total attendance (including parents/caregivers)?</td>
        <td><c:out value="${StatBean.childProgramAttendance}" /></td>
        <td><c:out value="${StatBean.childProgramAttendance2}" /></td>
        <td><c:out value="${StatBean.childProgramAttendance3}" /></td>
        <td><c:out value="${StatBean.childProgramAttendance + StatBean.childProgramAttendance2 + StatBean.childProgramAttendance3}"/></td>
      </tr>
      <tr>
        <td>2. How many total programs did libraries in the 
        system offer for teens during summer of this year?</td>
        <td><c:out value="${StatBean.teenPrograms}"/></td>
        <td><c:out value="${StatBean.teenPrograms2}"/></td>
        <td><c:out value="${StatBean.teenPrograms3}"/></td>
        <td><c:out value="${StatBean.teenPrograms + StatBean.teenPrograms2 + StatBean.teenPrograms3}"/></td>
      </tr>
      <tr>
        <td>Total attendance (including parents/caregivers)?</td>
        <td><c:out value="${StatBean.teenProgramAttendance}" /></td>
        <td><c:out value="${StatBean.teenProgramAttendance2}" /></td>
        <td><c:out value="${StatBean.teenProgramAttendance3}" /></td>
        <td><c:out value="${StatBean.teenProgramAttendance + StatBean.teenProgramAttendance2 + StatBean.teenProgramAttendance3}"/></td>
      </tr>
      <tr>
        <td>3. Total of 1 & 2, Programs?</td>
        <td><c:out value="${StatBean.childTeenProgramTotal}" /></td>  
        <td><c:out value="${StatBean.childPrograms2 + StatBean.teenPrograms2}" /></td> 
        <td><c:out value="${StatBean.childPrograms3 + StatBean.teenPrograms3}" /></td> 
        <td><c:out value="${StatBean.childTeenProgramTotal + StatBean.childPrograms2 + StatBean.teenPrograms2 + StatBean.childPrograms3 + StatBean.teenPrograms3}" /></td> 
      </tr>
      <tr>
        <td>Total of 1 & 2, Attendance?</td>
        <td><c:out value="${StatBean.childTeenAttendanceTotal}" /></td>  
        <td><c:out value="${StatBean.childProgramAttendance2 + StatBean.teenProgramAttendance2}" /></td> 
        <td><c:out value="${StatBean.childProgramAttendance3 + StatBean.teenProgramAttendance3}" /></td> 
        <td><c:out value="${StatBean.childTeenAttendanceTotal + StatBean.childProgramAttendance2 + StatBean.teenProgramAttendance2 + StatBean.childProgramAttendance3 + StatBean.teenProgramAttendance3}" /></td>
      </tr>
      <tr>
        <td>4. How many programs listed in #3 included parents/caregivers?</td>
        <td><c:out value="${StatBean.parentPrograms}" /></td>
        <td><c:out value="${StatBean.parentPrograms2}" /></td>
        <td><c:out value="${StatBean.parentPrograms3}" /></td>
        <td><c:out value="${StatBean.parentPrograms + StatBean.parentPrograms2 + StatBean.parentPrograms3}"/></td>
      </tr>
      <tr>
        <td>Attendance of parents/caregivers?</td>
        <td><c:out value="${StatBean.parentProgramAttendance}" /></td> 
        <td><c:out value="${StatBean.parentProgramAttendance2}" /></td> 
        <td><c:out value="${StatBean.parentProgramAttendance3}" /></td> 
        <td><c:out value="${StatBean.parentProgramAttendance + StatBean.parentProgramAttendance2 + StatBean.parentProgramAttendance3}"/></td>
      </tr>
      <tr>
        <td>5. How many workshops (professional development) were offered by the system from 
        July 1 through June 30 (grant program cycle)?</td>
        <td><c:out value="${StatBean.workshops}" /></td> 
        <td><c:out value="${StatBean.workshops2}" /></td> 
        <td><c:out value="${StatBean.workshops3}" /></td> 
        <td><c:out value="${StatBean.workshops + StatBean.workshops2 + StatBean.workshops3}"/></td>
      </tr>      
      <Tr>
        <td>Total attendance?</td>
        <td><c:out value="${StatBean.workshopAttendance}" /></td>
        <td><c:out value="${StatBean.workshopAttendance2}" /></td> 
        <td><c:out value="${StatBean.workshopAttendance3}" /></td>
        <td><c:out value="${StatBean.workshopAttendance + StatBean.workshopAttendance2 + StatBean.workshopAttendance3}"/></td>
      </tr>
      <tr>
        <td height="20"/>
      </tr>
    </table>   
  
  
  </c:when>
  <c:otherwise >
  
  <%-- DATA ENTRY FORM --%>
  
  <html:form action="/saveFamLitStatisticsNew">
    <table width="95%" align="center" class="boxtype" summary="for layout only">
      <tr>
        <td colspan="2">Complete all that are applicable (enter whole numbers, no decimals)</td>
      </tr>
      <tr>
        <td></td>
        <td><b>Year 1</b></td>
        <td><b>Year 2</b></td>
        <td><b>Year 3</b></td>
        <td><b>Totals</b></td>
      </tr>
      <tr>
        <td>A.	Number of libraries and/or branches in the system that offered a 
        summer reading program for children and/or teens this year?</td>
        <td><html:text property="sitesStr" size="9" /></td>
        <td><html:text property="sites2Str" size="9" /></td>
        <td><html:text property="sites3Str" size="9" /></td>
        <td><c:out value="${StatBean.sites + StatBean.sites2 + StatBean.sites3}"/></td>
      </tr>
      <tr>
        <td>Plan to offer next year?</td>
        <td><html:text property="planSitesStr" size="9" /></td>
        <td><html:text property="planSitesStr2" size="9" /></td>
        <td><html:text property="planSitesStr3" size="9" /></td>
        <td><c:out value="${StatBean.planSites + StatBean.planSites2 + StatBean.planSites3}"/></td>
      </tr>
      <tr>
        <td>B. Number of libraries and/or branches in the system that used the 
        CSLP children's slogan for this year?</td>
        <td><html:text property="childSloganStr" size="9" /></td>
        <td><html:text property="childSloganStr2" size="9" /></td>
        <td><html:text property="childSloganStr3" size="9" /></td>
        <td><c:out value="${StatBean.childSlogan + StatBean.childSlogan2 + StatBean.childSlogan3}"/></td>
      </tr>
      <tr>
        <td>Plan to use the CSLP Children’s slogan for next year?</td>
        <td><html:text property="planChildSloganStr" size="9" /></td> 
        <td><html:text property="planChildSloganStr2" size="9" /></td> 
        <td><html:text property="planChildSloganStr3" size="9" /></td> 
        <td><c:out value="${StatBean.planChildSlogan + StatBean.planChildSlogan2 + StatBean.planChildSlogan3}"/></td>
      </tr>
      <tr>
        <td>C. Number of libraries and/or branches in the system that used 
        the CSLP teen slogan for this year? </td>
        <td><html:text property="teenSloganStr" size="9" /></td>   
        <td><html:text property="teenSloganStr2" size="9" /></td>   
        <td><html:text property="teenSloganStr3" size="9" /></td>   
        <td><c:out value="${StatBean.teenSlogan + StatBean.teenSlogan2 + StatBean.teenSlogan3}"/></td>
      </tr>
      <tr>
        <td>Plan to use the CSLP teen slogan for next year?</td>
        <td><html:text property="planTeenSloganStr" size="9" /></td>   
        <td><html:text property="planTeenSloganStr2" size="9" /></td>   
        <td><html:text property="planTeenSloganStr3" size="9" /></td>   
        <td><c:out value="${StatBean.planTeenSlogan + StatBean.planTeenSlogan2 + StatBean.planTeenSlogan3}"/></td>
      </tr>
      <tr>
        <td>D. TOTAL number of children who registered for the summer 
        reading program this year</td>
        <td><html:text property="participantsStr" size="9" /></td>   
        <td><html:text property="participants2Str" size="9" /></td>   
        <td><html:text property="participants3Str" size="9" /></td>   
        <td><c:out value="${StatBean.participants + StatBean.participants2 + StatBean.participants3}"/></td>
      </tr>
      <tr>
        <td>E. TOTAL number of teens who registered 
        for the summer reading program this year</td>
        <td><html:text property="teenParticipantsStr" size="9" /></td>  
        <td><html:text property="teenParticipantsStr2" size="9" /></td>  
        <td><html:text property="teenParticipantsStr3" size="9" /></td>  
        <td><c:out value="${StatBean.teenParticipants + StatBean.teenParticipants2 + StatBean.teenParticipants3}"/></td>
      </tr>
      <tr>
        <td height="20" />
      </tr>
      <tr>
        <td><b>F. Reading totals for this year:</b><br/>For participants recording by time read:</td>
      </tr>  
      <tr>
        <td>1. TOTAL number of children who recorded by minutes read</td>
        <td><html:text property="numChildMinutesStr" size="9" /></td>  
        <td><html:text property="numChildMinutesStr2" size="9" /></td>  
        <td><html:text property="numChildMinutesStr3" size="9" /></td>  
        <td><c:out value="${StatBean.numChildMinutes + StatBean.numChildMinutes2 + StatBean.numChildMinutes3}"/></td>
      </tr>
      <tr>
        <td>2. TOTAL minutes read by these children</td>
        <td><html:text property="childMinutesReadStr" size="9" /></td> 
        <td><html:text property="childMinutesReadStr2" size="9" /></td> 
        <td><html:text property="childMinutesReadStr3" size="9" /></td> 
        <td><c:out value="${StatBean.childMinutesRead + StatBean.childMinutesRead2 + StatBean.childMinutesRead3}"/></td>
      </tr>
      <tr>
        <td>3. TOTAL number of teens who recorded by minutes read</td>
        <td><html:text property="numTeenMinutesStr" size="9" /></td> 
        <td><html:text property="numTeenMinutesStr2" size="9" /></td> 
        <td><html:text property="numTeenMinutesStr3" size="9" /></td>
        <td><c:out value="${StatBean.numTeenMinutes + StatBean.numTeenMinutes2 + StatBean.numTeenMinutes3}"/></td>
      </tr>
      <tr>
        <td>4. TOTAL minutes read by these teens</td>
        <td><html:text property="teenMinutesReadStr" size="9" /></td> 
        <td><html:text property="teenMinutesReadStr2" size="9" /></td> 
        <td><html:text property="teenMinutesReadStr3" size="9" /></td> 
        <td><c:out value="${StatBean.teenMinutesRead + StatBean.teenMinutesRead2 + StatBean.teenMinutesRead3}"/></td>
      </tr>
      <tr>
        <td>For participants recording by books read:</td>
      </tr>
      <tr>
        <td>5. TOTAL number of children who recorded by books read</td>
        <td><html:text property="numChildBooksStr" size="9" /></td> 
        <td><html:text property="numChildBooksStr2" size="9" /></td> 
        <td><html:text property="numChildBooksStr3" size="9" /></td> 
        <td><c:out value="${StatBean.numChildBooks + StatBean.numChildBooks2 + StatBean.numChildBooks3}"/></td>
      </tr>
      <tr>
        <td>6. TOTAL number of books children read</td>
        <td><html:text property="childBooksReadStr" size="9" /></td> 
        <td><html:text property="childBooksReadStr2" size="9" /></td> 
        <td><html:text property="childBooksReadStr3" size="9" /></td> 
        <td><c:out value="${StatBean.childBooksRead + StatBean.childBooksRead2 + StatBean.childBooksRead3}"/></td>
      </tr>
      <tr>
        <td>7. TOTAL number of teens who recorded by books read</td>
        <td><html:text property="numTeenBooksStr" size="9" /></td> 
        <td><html:text property="numTeenBooksStr2" size="9" /></td> 
        <td><html:text property="numTeenBooksStr3" size="9" /></td> 
        <td><c:out value="${StatBean.numTeenBooks + StatBean.numTeenBooks2 + StatBean.numTeenBooks3}"/></td>
      </tr>
      <tr>
        <td>8. TOTAL number of books teens read</td>
        <td><html:text property="teenBooksReadStr" size="9" /></td>  
        <td><html:text property="teenBooksReadStr2" size="9" /></td>  
        <td><html:text property="teenBooksReadStr3" size="9" /></td>  
        <td><c:out value="${StatBean.teenBooksRead + StatBean.teenBooksRead2 + StatBean.teenBooksRead3}"/></td>
      </tr>
      <tr>
        <td height="20"/>
      </tr>
      <tr>
        <Td><b>G. Programs</b><br/>A program is defined as one planned 
        session conducted by a staff member, outside performer or other 
        programmer – it does not include informal visits to the library 
        to report on reading, etc.  Please answer each question or 
        type 0 where a question does not apply to your system.</td>
      </tr>
      <tr>
        <td>1. How many total programs did libraries in the system 
        offer for children during summer of this year?</td>
        <td><html:text property="childProgramsStr" size="9" /></td> 
        <td><html:text property="childProgramsStr2" size="9" /></td>
        <td><html:text property="childProgramsStr3" size="9" /></td>
        <td><c:out value="${StatBean.childPrograms + StatBean.childPrograms2 + StatBean.childPrograms3}"/></td>
      </tr>
      <tr>
        <td>Total attendance (including parents/caregivers)?</td>
        <td><html:text property="childProgramAttendanceStr" size="9" /></td>  
        <td><html:text property="childProgramAttendanceStr2" size="9" /></td> 
        <td><html:text property="childProgramAttendanceStr3" size="9" /></td> 
        <td><c:out value="${StatBean.childProgramAttendance + StatBean.childProgramAttendance2 + StatBean.childProgramAttendance3}"/></td>
      </tr>
      <tr>
        <td>2. How many total programs did libraries in the 
        system offer for teens during summer of this year?</td>
        <td><html:text property="teenProgramsStr" size="9" /></td>
        <td><html:text property="teenProgramsStr2" size="9" /></td>
        <td><html:text property="teenProgramsStr3" size="9" /></td>
        <td><c:out value="${StatBean.teenPrograms + StatBean.teenPrograms2 + StatBean.teenPrograms3}"/></td>
      </tr>
      <tr>
        <td>Total attendance (including parents/caregivers)?</td>
        <td><html:text property="teenProgramAttendanceStr" size="9" /></td>  
        <td><html:text property="teenProgramAttendanceStr2" size="9" /></td>  
        <td><html:text property="teenProgramAttendanceStr3" size="9" /></td>  
        <td><c:out value="${StatBean.teenProgramAttendance + StatBean.teenProgramAttendance2 + StatBean.teenProgramAttendance3}"/></td>
      </tr>
      <tr>
        <td>3. Total of 1 & 2, Programs?</td>
        <td><c:out value="${StatBean.childTeenProgramTotal}" /></td> 
        <td><c:out value="${StatBean.childPrograms2 + StatBean.teenPrograms2}" /></td> 
        <td><c:out value="${StatBean.childPrograms3 + StatBean.teenPrograms3}" /></td> 
        <td><c:out value="${StatBean.childTeenProgramTotal + StatBean.childPrograms2 + StatBean.teenPrograms2 + StatBean.childPrograms3 + StatBean.teenPrograms3}" /></td> 
      </tr>
      <tr>
        <td>Total of 1 & 2, Attendance?</td>
        <td><c:out value="${StatBean.childTeenAttendanceTotal}" /></td>  
        <td><c:out value="${StatBean.childProgramAttendance2 + StatBean.teenProgramAttendance2}" /></td> 
        <td><c:out value="${StatBean.childProgramAttendance3 + StatBean.teenProgramAttendance3}" /></td>
        <td><c:out value="${StatBean.childTeenAttendanceTotal + StatBean.childProgramAttendance2 + StatBean.teenProgramAttendance2 + StatBean.childProgramAttendance3 + StatBean.teenProgramAttendance3}" /></td>
      </tr>
      <tr>
        <td>4. How many programs listed in #3 included parents/caregivers?</td>
        <td><html:text property="parentProgramsStr" size="9" /></td>  
        <td><html:text property="parentProgramsStr2" size="9" /></td>  
        <td><html:text property="parentProgramsStr3" size="9" /></td>  
        <td><c:out value="${StatBean.parentPrograms + StatBean.parentPrograms2 + StatBean.parentPrograms3}"/></td>
      </tr>
      <tr>
        <td>Attendance of parents/caregivers?</td>
        <td><html:text property="parentProgramAttendanceStr" size="9" /></td> 
        <td><html:text property="parentProgramAttendanceStr2" size="9" /></td> 
        <td><html:text property="parentProgramAttendanceStr3" size="9" /></td> 
        <td><c:out value="${StatBean.parentProgramAttendance + StatBean.parentProgramAttendance2 + StatBean.parentProgramAttendance3}"/></td>
      </tr>
      <tr>
        <td>5. How many workshops (professional development) were offered by the system from 
        July 1 through June 30 (grant program cycle)?</td>
        <td><html:text property="workshopsStr" size="9" /></td> 
        <td><html:text property="workshopsStr2" size="9" /></td> 
        <td><html:text property="workshopsStr3" size="9" /></td> 
        <td><c:out value="${StatBean.workshops + StatBean.workshops2 + StatBean.workshops3}"/></td>
      </tr>      
      <Tr>
        <td>Total attendance?</td>
        <td><html:text property="workshopAttendanceStr" size="9" /></td> 
        <td><html:text property="workshopAttendanceStr2" size="9" /></td>
        <td><html:text property="workshopAttendanceStr3" size="9" /></td>
        <td><c:out value="${StatBean.workshopAttendance + StatBean.workshopAttendance2 + StatBean.workshopAttendance3}"/></td>
      </tr>
      <tr>
        <td height="20"/>
      </tr>
      <tr>
        <td colspan="2"><html:submit value="Save"/></td>
      </tr>
    </table>
  </html:form>
  
  
  </c:otherwise>
  </c:choose>
  
  </body>
</html>