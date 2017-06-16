<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/pd4ml.tld" prefix="pd4ml" %>
<%@ page contentType="text/html;charset=ISO8859_1"%>
<pd4ml:transform screenWidth="600" encoding="default" pageFormat="LETTER" pageOrientation="portrait" enableImageSplit="false">
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title></title>
  </head>
  <body>
  
  <font size="1">
  <table align="center" summary="for layout only">
    <tr>
      <th colspan="2">Local Government Records Management Improvement Fund (LGRMIF) 
          <br/>Imaging Microfilming Form
      </th>
    </tr>
    <tr>
      <td>Project Number</td>
      <td>05<fmt:formatNumber minIntegerDigits="2" value="${thisGrant.fccode}" />
          -<fmt:formatNumber value="${thisGrant.fycode}" minIntegerDigits="2" />
          -<fmt:formatNumber value="${thisGrant.projseqnum}" pattern="####" minIntegerDigits="4" />
      </td>
    </tr>
    <tr>
      <td>Institution</td>
      <td><c:out value="${thisGrant.instName}" /> -<c:out value="${thisGrant.dorisname}" /></td>
    </tr>
  </table>
  </font>
  <br/><br/>
  
  <font size="1">
  <table summary="for layout only">
    <tr>
        <td colspan="2"><b>Imaging/Microfilming Form</b></td>
    </tr>
    <tr>
        <td colspan="2"><b>Activities</b></td>
    </tr>
    <tr>
        <td><b>Imaging</b><br/>
        Paper documents to digital image: <c:out value="${StatBean.paperToDigital}" /><br/>
        Microfilm images to digital image: <c:out value="${StatBean.microfilmToDigital}" /><br/>
        Digital documents to digital image: <c:out value="${StatBean.digitalToDigital}"/>
        </td>
        <td><b>Microfilming</b><br/>
        Paper documents to microfilm: <c:out value="${StatBean.paperToMicrofilm}" /><br/>
        Digital images to microfilm: <c:out value="${StatBean.digitalToMicrofilm}" />
        </td>
    </tr>
  
  
    <tr>
      <td colspan="2"><b>Records Description</b></td>
    </tr>
    <tr>
       <td>Name of records series: <c:out value="${StatBean.nameseries}" /></td>
       <td>Retention period (years): <c:out value="${StatBean.retentionperiod}"/></td>
    </tr>
    <tr>
        <td>Date range of records: <c:out value="${StatBean.daterange}"/></td>
        <td>Records schedule (name and item num): <c:out value="${StatBean.recordschedule}"/></td>
    </tr>
    <tr>
        <td>Total number of images: <c:out value="${StatBean.totalimages}"/></td>
        <td>Format of use copies Diazo or vesicular film: <c:out value="${StatBean.diazofilm}"/>&nbsp;&nbsp;
            Digital images: <c:out value="${StatBean.digitalimage}" />
        </td>
    </tr>
  
    <tr>
        <td colspan="2"><b>Characteristics</b></td>
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
  </table>
  </font>
  
  </body>
</html>
</pd4ml:transform>