<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>imageMicrofilm</title>
  </head>
  <body>
  <br/>
  <b>Imaging and Microfilming Project Information (LG-IM)</b>  
  (<a href="docs/lgrmif/im.htm" target="_blank">Instructions</a>)<br/>
  <P>Please complete and attach a copy of the 
  <a href="docs/lgrmif/imagingMicrofilmingForm.pdf" target="_blank">PDF</a> 
  LG-IM form to the application for each additional record series beyond the first one 
  identified in your project. 
  <a href="LgAddAttachment.do">Attach</a> the forms to 
  your online grant application.</P><br/>
  
  
  <c:choose >
  <c:when test="${lduser.readaccess=='true' || appStatus.vqimyn=='true' || appStatus.pendingReview=='true' }">
       
  <html:form action="/saveImageMicrofilm">     
  <table width="90%" summary="for layout only">
    <tr>
      <td width="40%"><b>Local Government:</b></td>
      <td><c:out value="${thisGrant.instName}" /></td>
    </tr>
    <tr>
      <td><b>Project Number:</b></td>
      <td>05<fmt:formatNumber value="${thisGrant.fccode}" />
            -<fmt:formatNumber value="${thisGrant.fycode}" minIntegerDigits="2" />
            -<fmt:formatNumber value="${thisGrant.projseqnum}" minIntegerDigits="4" pattern="####" /></td>
    </tr>
    <tr>
        <td colspan="2" height="30"><hr/><b>Activities</b> (check all that apply)</td>
    </tr>
    <tr>
        <td><b>Imaging</b><br/>
        <html:checkbox property="paperToDigital" disabled="true"/>Paper documents to digital image<br/>
        <html:checkbox property="microfilmToDigital" disabled="true"/>Microfilm images to digital image<br/>
        <html:checkbox property="digitalToDigital" disabled="true"/>Digital documents to digital image
        </td>
        <td><b>Microfilming</b><br/>
        <html:checkbox property="paperToMicrofilm" disabled="true"/>Paper documents to microfilm<br/>
        <html:checkbox property="digitalToMicrofilm" disabled="true"/>Digital images to microfilm
        </td>
    </tr>
    <tr>
      <td colspan="2" height="30"><hr/><b>Records Description</b></td>
    </tr>
    <tr>
       <td>Name of records series:<br/><c:out value="${StatBean.nameseries}"/></td>
       <td>Retention period (years):<br/><c:out value="${StatBean.retentionperiod}"/></td>
    </tr>
    <tr>
        <td>Date range of records:<br/><c:out value="${StatBean.daterange}"/></td>
        <td>Records schedule (name and item num):<br/><c:out value="${StatBean.recordschedule}"/></td>
    </tr>
    <tr>
        <td>Total number of images<br/><c:out value="${StatBean.totalimages}"/></td>
        <td>Format of use copies:<html:checkbox property="diazofilm" disabled="true"/>Diazo or vesicular film&nbsp;&nbsp;
            <html:checkbox property="digitalimage" disabled="true"/>Digital images
        </td>
    </tr>
        
    <tr>
        <td colspan="2" height="30"><hr/><b>Characteristics</b></td>
    </tr>
    
    <tr>
        <td>Electronic data:</td>
        <td><c:out value="${StatBean.electronicdata}"/></td>
    </tr>
    <tr>
        <td>Document size:</td>
        <td><c:out value="${StatBean.docsize}"/></td>
    </tr>
    <tr>
        <td>Paper type:</td>
        <td><c:out value="${StatBean.papertype}"/></td>
    </tr>
    <tr>
        <td>Paper condition:</td>
        <td><c:out value="${StatBean.papercondition}"/></td>
    </tr>
    <tr>
        <td>Imprint:</td>
        <td><c:out value="${StatBean.imprint}"/></td>
    </tr>
    <tr>
        <td>Paper color:</td>
        <td><c:out value="${StatBean.papercolor}"/></td>
    </tr>
    <tr>
        <td>Fasteners:</td>
        <td><c:out value="${StatBean.fasteners}"/></td>
    </tr>
    <tr>
        <td>Frequency of fasteners:</td>
        <td><c:out value="${StatBean.freqfasteners}"/></td>
    </tr>
   
    <tr>
       <td><input type="button" value="Save" disabled="disabled"/></td>
    </tr>
  </table>
  </html:form>
       

  </c:when>
  <c:otherwise>
  
  <html:form action="/saveImageMicrofilm">
  <table width="90%" summary="for layout only">
    <tr>
      <td width="40%"><b>Local Government:</b></td>
      <td><c:out value="${thisGrant.instName}" /></td>
    </tr>
    <tr>
      <td><b>Project Number:</b></td>
      <td>05<fmt:formatNumber value="${thisGrant.fccode}" />
            -<fmt:formatNumber value="${thisGrant.fycode}" minIntegerDigits="2" />
            -<fmt:formatNumber value="${thisGrant.projseqnum}" minIntegerDigits="4" pattern="####" /></td>
    </tr>
    <tr>
        <td colspan="2" height="30"><hr/><b>Activities</b> (check all that apply)</td>
    </tr>
    <tr>
        <td><b>Imaging</b><br/>
        <html:checkbox property="paperToDigital"/>Paper documents to digital image<br/>
        <html:checkbox property="microfilmToDigital"/>Microfilm images to digital image<br/>
        <html:checkbox property="digitalToDigital"/>Digital documents to digital image
        </td>
        <td><b>Microfilming</b><br/>
        <html:checkbox property="paperToMicrofilm"/>Paper documents to microfilm<br/>
        <html:checkbox property="digitalToMicrofilm"/>Digital images to microfilm
        </td>
    </tr>
    <tr>
      <td colspan="2" height="30"><hr/><b>Records Description</b></td>
    </tr>
    <tr>
       <td>Name of records series:<br/><html:text property="nameseries"/></td>
       <td>Retention period (years):<br/><html:text property="retentionperiod"/></td>
    </tr>
    <tr>
        <td>Date range of records:<br/><html:text property="daterange"/></td>
        <td>Records schedule (name and item num):<br/><html:text property="recordschedule"/></td>
    </tr>
    <tr>
        <td>Total number of images<br/><html:text property="totalimages"/></td>
        <td>Format of use copies:<html:checkbox property="diazofilm"/>Diazo or vesicular film&nbsp;&nbsp;
            <html:checkbox property="digitalimage"/>Digital images
        </td>
    </tr>
        
    <tr>
        <td colspan="2" height="30"><hr/><b>Characteristics</b></td>
    </tr>
    
    <tr>
        <td>Electronic data:</td>
        <td><html:text property="electronicdata"/></td>
    </tr>
    <tr>
        <td>Document size:</td>
        <td><html:text property="docsize"/></td>
    </tr>
    <tr>
        <td>Paper type:</td>
        <td><html:text property="papertype"/></td>
    </tr>
    <tr>
        <td>Paper condition:</td>
        <td><html:text property="papercondition"/></td>
    </tr>
    <tr>
        <td>Imprint:</td>
        <td><html:text property="imprint"/></td>
    </tr>
    <tr>
        <td>Paper color:</td>
        <td><html:text property="papercolor"/></td>
    </tr>
    <tr>
        <td>Fasteners:</td>
        <td><html:text property="fasteners"/></td>
    </tr>
    <tr>
        <td>Frequency of fasteners:</td>
        <td><html:text property="freqfasteners"/></td>
    </tr>
   
    <tr>
       <td><html:submit value="Save"/></td>
    </tr>
  </table>
  </html:form>
  
  </c:otherwise>
  </c:choose>
  
  </body>
</html>