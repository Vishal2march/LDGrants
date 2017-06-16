<%--
 * @author  ? unknown
 * @version 2.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the Application        :  addDocument.jsp
 * Creation/Modification History  :
 *     
 *     SHusak       6/15/07     Modified
 *
 * Description
 * This page allows the applicant to upload a document to thier application. The document
 * info is saved as a blob in table uploads. The code was taken from online javazoom,
 * and from BITS, and modified. 
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page errorPage="ExceptionHandler.jsp" %>
<jsp:useBean id="upBean" scope="application" class="javazoom.upload.UploadBean" />
<jsp:setProperty name="upBean" property="overwrite" value="true" />
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>untitled</title>
    <script type="text/javascript">
      function hideText() { 
      if (document.getElementById('option').value=="other")
         { 
            document.getElementById('txt').disabled=false;
            document.getElementById('txt').style.visibility='visible'; 
         }
      else{
            document.getElementById('txt').disabled=true; 
            document.getElementById('txt').style.visibility='hidden';
          }
      } 
    </script>
  </head>
  <body>
   
  <h4>Add an Attachment</h4>
  
  <c:if test="${wrongDocType=='true'}">
    <p><font color="red"><b>***Note:  </b></font>Please do not attach MS Word 2007 or 2010 (.docx) files; they must
        be converted to MS Word 2003 (.doc) format.</p>
  </c:if>
   
  <table width="80%" align="center" summary="for layout only">
    <c:if test="${param.prog=='fl' || param.prog=='al'}">
      <tr>
        <td>Possible items to attach:
            <br/>Letters of support (optional for Family Literacy)
            <br/>Supporting data for purchased services,bids</td>
      </tr>
    </c:if>
    <tr>
      <th>Instructions</th>
    </tr>
    <tr>
      <td>Step 1 Make sure the document name is clear and understandable.  The document name will be saved along with the file.<br/>
      Step 2 Click the Browse button.  The File Dialog window will open, then navigate to the location where the document is stored.<br/>
      Step 3 Choose the document, click Open, and the path to the document will appear in the textbox. <br/>
      Step 4 Enter an optional description of the document (ie. Appendix 1).<br/>
      Step 5 Click the Upload button to save document to the database, or Cancel to select another document.</td>
    </tr>      
  </table>
  
  

  <form method="post" action="handleAttachment.do?i=addattach" name="upform" enctype="multipart/form-data">
  <table width="75%" align="center" summary="for layout only">
    <tr>
      <td><b>Select a Document to attach:</b> <br/>
        Documents include MS Word (.doc), Excel, PDF, .txt, .bmp, .jpg<br/>
        <%--Please do not attach MS Word 2007 or 2010 (.docx) files; they must
        be converted to MS Word 2003 (.doc) format.--%></td>
    </tr>
    <tr>
      <td>
        <input type="file" name="uploadfile" size="50">
      </td>
    </tr>
    <tr>
      <td>Short Description of File<br/>
       <c:choose >
       <c:when test="${param.prog=='cn' || param.prog=='cnreview' || param.prog=='conadmin'}"> 
          <select id="option" name="filedesc" onchange="hideText()">
               <option value="ERROR">Please Select Attachment Type</option>
               <option value="Assurances">Assurances</option>
               <option value="SEAF">Environmental Assessment (short or full)</option>
               <option value="SHPO">SHPO</option>
               <option value="OFP">OFP Approval</option>
               <option value="10 Year Lease">10 Year Lease Agreement</option>
               <option value="Proof of Available Funds" >Proof of Available Funds</option>
               <option value="Smart Growth">Smart Growth Form</option>
               <option value="Pre Project Photo">Building Photos (pre project)</option>
               <option value="Post Project Photo">Building Photos (post project)</option>
               <option value="Bid">Contract/Construction/Material Bid</option>
               <option value="Municipal Consent">Municipal Consent for Site/Building Acquisition Projects</option>
               <option value="MWBE Requirement">M/WBE (Minority and Women-Owned Business Enterprises) Requirement</option>
               <option value="Expense">Expense Receipt</option>
               <option value="other">Other</option>
          </select>
          <input type="TEXT" id="txt" name="filedesc" value="please specify" disabled=true style="visibility:hidden" />
          <noscript><font color='Red'>JavaScript Must be enabled if file type is "Other" to allow for description of file</font></noscript>
       </c:when>
       <c:otherwise>
            <input type="TEXT" name="filedesc" />
       </c:otherwise>    
       </c:choose>
      </td>
    </tr>
    
    <tr>
      <td>
        <input type="hidden" name="todo" value="upload">
        <input type="HIDDEN" name="prog" value='<c:out value="${param.prog}" />' />
        <c:choose >
        <c:when test="${appStatus.allowSubmitFinal=='false' || lduser.readaccess=='true'} ">        
          <input type="button" value="Upload" disabled="disabled">
        </c:when>
        <c:otherwise >
          <input type="submit" name="Submit" value="Upload">
        </c:otherwise>
        </c:choose>
        
        <input type="reset" name="Reset" value="Cancel">
      </td>
    </tr>
    <tr>
      <td height="30" />
    </tr>
    <tr>
      <td>
        <c:choose >
        <c:when test="${param.prog=='di'}">      
          <a href="diInitialForms.do?i=attachment&m=di">View List of Attachments</a>
        </c:when>
        <c:when test="${param.prog=='co'}">
          <a href="coApplicantForms.do?item=attachment&m=co">View List of Attachments</a>
        </c:when>
        <c:when test="${param.prog=='lg'}">
          <a href="lgApplicant.do?i=attachment&m=lg">View List of Attachments</a>
        </c:when>
        <c:when test="${param.prog=='partlg'}">
          <a href="lgParticipantForms.do?i=attachment">View List of Attachments</a>
        </c:when>
        <c:when test="${param.prog=='al'}">
          <a href="liInitialForms.do?item=attachment&p=al">View List of Attachments</a>
        </c:when>
        <c:when test="${param.prog=='fl'}">
          <a href="liInitialForms.do?item=attachment&p=fl">View List of Attachments</a>
        </c:when>
        <c:when test="${param.prog=='partdi'}">
          <a href="diParticipatingForms.do?item=attachment">View List of Attachments</a>
        </c:when>
        <c:when test="${param.prog=='cn'}">      
          <a href="constructionForms.do?i=attachment&m=cn">View List of Attachments</a>
        </c:when>
        <c:when test="${param.prog=='staid'}">      
          <a href="stateaidForms.do?i=attachment&m=staid">View List of Attachments</a>
        </c:when>
        </c:choose>
      </td>
    </tr>
  </table>  
</form>    
  
  </body>
</html>
