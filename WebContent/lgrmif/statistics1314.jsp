<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%--
 * @author  Stefanie Husak
 * @version 1.0
 * Development Environment        :  JDeveloper 11g
 * Name of the Application        :  statistics1314.jsp
 * SH  8/19/13   Created
 *
 * New LGRMIF statistics report starting in fy 2013-14, per FC
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>statistics1314</title>
  </head>
  <body>
  
  
   <br/>
  <a href="http://www.archives.nysed.gov/common/archives/files/grants_lgrmif_egrant_user_manual.pdf" target="_blank">Final statistics help</a>
  <br/>
  
  <br/>
  <table width="95%" align="center" class="boxtype" summary="for layout only">
    <tr>
      <th colspan="2">LGRMIF - Final Statistical Report</th>
    </tr>
    <tr>
      <td><b>Project Number</b></td>
      <td>05<fmt:formatNumber minIntegerDigits="2" value="${thisGrant.fccode}" />
          -<fmt:formatNumber value="${thisGrant.fycode}" minIntegerDigits="2" />
          -<fmt:formatNumber  minIntegerDigits="4" pattern="####" value="${thisGrant.projseqnum}" /></td>
    </tr> 
    <tr>
      <td><b>Institution:</b></td>
      <td><c:out value="${thisGrant.instName}" /></td>
    </tr>
  </table><br/>
  
  
  <c:choose >
  <c:when test="${lduser.readaccess=='true' || appStatus.pendingFinalRev=='true'}" > 
      
     
  <table width="95%" align="center" class="boxtype" summary="for layout only">
    <tr>
      <td colspan="2">Provide quantitative data for your project where applicable, using 
      whole numbers only.  Text, commas, spaces, blanks, etc. are not allowed by the system, 
      EXCEPT in the field "Other".  If the project has quantifiable data but is not listed,
      please provide a brief explanation in the comment field below, where entering text 
      is acceptable. </td>
    </tr>
    <tr>
      <td>Cubic Feet of Records Inventoried</td>
      <td><c:out value="${StatBean.inventory}" /></td>
    </tr>
    <tr>
      <td height="20" />
    </tr>
    <tr>
      <td>Cubic Feet of Records Destroyed</td>
      <td><c:out value="${StatBean.destroy}" /></td>
    </tr>
    <tr>
      <td height="20" />
    </tr>
    <tr>
      <td>Number of Images Digitized</td>
      <td><c:out value="${StatBean.scan}" /></td>
    </tr>
    <tr>
      <td height="20" />
    </tr>
    <tr>
      <td>Number of Images Microfilmed</td>
      <td><c:out value="${StatBean.microfilm}" /></td>
    </tr>
    <tr>
      <td height="20" />
    </tr>
    <tr>
      <td>Cubic Feet of Historical Records Arranged & Described</td>
      <td><c:out value="${StatBean.recordsarranged}" /></td>
    </tr>
    <tr>
      <td height="20" />
    </tr>
    <tr>
      <td>Cubic Feet of Inactive Records Moved to Records Storage</td>
      <td><c:out value="${StatBean.inactiverecords}" /></td>
    </tr>
    <tr>
      <td height="20" />
    </tr>
    <tr>
      <td>Cubic Feet of Records Conserved</td>
      <td><c:out value="${StatBean.recordsconserved}" /></td>
    </tr>
    <tr>
      <td height="20" />
    </tr>
    <tr>
      <td>Number of Documents Made Available Online</td>
      <td><c:out value="${StatBean.online}" /></td>
    </tr>
    <tr>
      <td height="20" />
    </tr>
    <tr>
      <td>Number of Series Descriptions Produced</td>
      <td><c:out value="${StatBean.seriesdescrip}" /></td>
    </tr>
    <tr>
      <td height="20" />
    </tr>
    <tr>
      <td>Shared Services Grants Only: Total Startup Cost Avoidance</td>
      <td><c:out value="${StatBean.startupcost}" /></td>
    </tr>
    <tr>
      <td height="20" />
    </tr>
    <tr>
      <td>Shared Services Grants Only:  Anticipated Annual Cost Savings</td>
      <td><c:out value="${StatBean.costsaving}" /></td>
    </tr>
    <tr>
      <td height="20" />
    </tr>    
    <tr>
      <td>Other (Describe below)</td>
      <td><c:out value="${StatBean.other}" /></td>
    </tr>
    <tr>
      <td colspan="2"><c:out value="${StatBean.otherExplained}" /></td>
    </tr>
  </table>
        
  </c:when>
  <c:otherwise >
  
    <html:errors/>
    <html:form action="/saveStatisticsLgNew">
    <table width="95%" align="center" class="boxtype" summary="for layout only">
      <tr>
        <td colspan="2">Provide quantitative data for your project where applicable, using 
      whole numbers only.  Text, commas, spaces, blanks, etc. are not allowed by the system, 
      EXCEPT in the field "Other".  If the project has quantifiable data but is not listed,
      please provide a brief explanation in the comment field below, where entering text 
      is acceptable. </td>
      </tr>
      <tr>
        <td>Cubic Feet of Records Inventoried</td>
        <td><html:text property="inventoryStr" maxlength="9" /></td>
      </tr>
      <tr>
        <td height="20" />
      </tr>
      <tr>
        <td>Cubic Feet of Records Destroyed</td>
        <td><html:text property="destroyStr" maxlength="9" /></td>
      </tr>
      <tr>
        <td height="20" />
      </tr>
      <tr>
        <td>Number of Images Digitized</td>
        <td><html:text property="scanStr" maxlength="9" /></td>
      </tr>
      <tr>
        <td height="20" />
      </tr>
      <tr>
        <td>Number of Images Microfilmed</td>
        <td><html:text property="microfilmStr" maxlength="9" /></td>
      </tr>
      <tr>
        <td height="20" />
      </tr>
      <tr>
        <td>Cubic Feet of Historical Records Arranged & Described</td>
        <td><html:text property="recordsarrangedStr" maxlength="9" /></td>
      </tr>
      <tr>
        <td height="20" />
      </tr>
      <tr>
        <td>Cubic Feet of Inactive Records Moved to Records Storage</td>
        <td><html:text property="inactiverecordsStr" maxlength="9" /></td>
      </tr>
      <tr>
        <td height="20" />
      </tr>
      <tr>
        <td>Cubic Feet of Records Conserved</td>
        <td><html:text property="recordsconservedStr" maxlength="9" /></td>
      </tr>
      <tr>
        <td height="20" />
      </tr>
      <tr>
        <td>Number of Documents Made Available Online</td>
        <td><html:text property="onlineStr" maxlength="9" /></td>
      </tr>
      <tr>
        <td height="20" />
      </tr>
      <tr>
        <td>Number of Series Descriptions Produced</td>
        <td><html:text property="seriesdescripStr" maxlength="9" /></td>
      </tr>
      <tr>
        <td height="20" />
      </tr>
      <tr>
        <td>Shared Services Grants Only: Total Startup Cost Avoidance</td>
        <td><html:text property="startupcostStr" maxlength="9" /></td>
      </tr>
      <tr>
        <td height="20" />
      </tr>
      <tr>
        <td>Shared Services Grants Only:  Anticipated Annual Cost Savings</td>
        <td><html:text property="costsavingStr" maxlength="9" /></td>
      </tr>
      <tr>
        <td height="20" />
      </tr>
      <tr>
        <td>Other (Describe below)</td>
        <td><html:text property="otherStr" maxlength="9" /></td>
      </tr>
      <tr>
        <td colspan="2"><html:text property="otherExplained" maxlength="100" size="60" /></td>
      </tr>
      <tr>
        <td height="20" />
      </tr>
      <tr>
        <td colspan="2" align="center">
        <html:hidden property="module" value="lg" /><html:submit value="Save" /></td>
      </tr>
    </table>
  </html:form>
  
  
  </c:otherwise>
  </c:choose>
  
  
  
  
  </body>
</html>