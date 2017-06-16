<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<html  xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <link href="../css/a.css" rel="stylesheet" media="screen" type="text/css" />
    <title>Online Grant System Account Registration</title>
  </head>
  <body>
  
  
  <div id="headerholder">
    <div class="topnav" id="headerpg">
    <div class="logo"><a href="http://www.nysed.gov"><img src="../images/nysed_white.gif" alt="NYSED.gov" width="92" height="20" border="0" /></a></div>

    <div class="topnavlist">
    <a href="http://www.nysarchivestrust.org/aptindex.shtml">Archives Partnership Trust</a> | <a href="http://www.oce.nysed.gov/">Office of Cultural Education</a> | <a href="http://nysl.nysed.gov/uhtbin/cgisirsi/Fri+Sep+22+13:16:33+EDT+2000/0/0/57/49?user_id=apublic&amp;password=">Online Catalog </a> | <a href="http://www.archives.nysed.gov/a/about/about_search.shtml">Search</a> | <a href="http://www.archives.nysed.gov/aindex.shtml">Home</a>      
    </div>
    </div>
    </div>

    <div id="linetopholder">
    <div id="linetoppg"><img src="../images/bump.bmp" width="1" height="2" alt=" " /></div>
    </div>

    <div id="bannerholder">
    <div id="banner"><img src="../images/banner_fade.jpg" alt="New York State Archives: Where History Goes on Record" /></div>
  </div>
    
    
    
  <div id="contentholder">      
  <div class="content" id="content">

  <h3>Online Grant System Account Registration Form</h3>
  
  <p>In order to apply for the NYS Archives Local Government Records Management 
  Improvement Fund Grant Program through the Online Grant System,
      you <b>must</b> have a NYS Directory Service account.  If you
      do not have a NYS Directory Service account, then complete the form below to register
      for an account.</p>
      
  <p>If you already have a NYS Directory Service account, proceed to the Online Grant System.<br/>
    <a href="https://eservices.nysed.gov/ldgrants">Login to Online Grant System</a></p>
  
  
  
  <table width="90%" border="1" align="center" summary="for layout only">
    <tr>
      <td colspan="2"></td>
    </tr>
    <html:form method="POST" action="/ldgext/cnRegistration">
    <tr>
      <th colspan="2">Register for a NYS Directory Service account</th>
    </tr>
    <tr>
      <td colspan="2" class="error">Fields marked with an (*) are required.</td>
    </tr>
    <tr>  
      <td><font color="Red">*</font>First Name</td>
      <td><html:text property="fname" /></td>
    </tr>
    <tr>  
      <td><font color="Red">*</font>Last Name</td>
      <td><html:text property="lname" /></td>
    </tr>
    <tr>  
      <td><font color="Red">*</font>Title</td>
      <td><html:text property="title" /></td>
    </tr>
    <tr>
      <td colspan="2">An email will be sent to the Work Email listed below containing the account
      name/password for the Online Grant System.</td>
    </tr>
    <tr>  
      <td><font color="Red">*</font>Work Email</td>
      <td><html:text property="email" /></td>
    </tr>
    <tr>  
      <td><font color="Red">*</font>Work Phone</td>
      <td><html:text property="phone" /></td>
    </tr>    
    <tr>  
      <td><font color="Red">*</font>Institution Name</td>
      <td><html:text property="instName" /></td>
    </tr>
    <tr>  
      <td>Department or Unit Name</td>
      <td><html:text property="libName" /></td>
    </tr>
    <tr>  
      <td><font color="Red">*</font>Institution Address<br/>
      Physical address, not PO Box number</td>
      <td><html:text property="addr1" /></td>
    </tr>
    <tr>
      <td>Address Line 2</td>
      <td><html:text property="addr2" /></td>
    </tr>
    <tr>  
      <td><font color="Red">*</font>City</td>
      <td><html:text property="city" /></td>
    </tr>
    <tr>  
      <td><font color="Red">*</font>State</td>
      <td><html:text property="state" /></td>
    </tr>
    <tr>  
      <td><font color="Red">*</font>Zip Code</td>
      <td><html:text property="zipcd" /></td>
    </tr>
    <tr>
      <td colspan="2">Enter the county, school district, and Federal ID of the institution you represent. If
      unknown, enter N/A in the corresponding box. </td>
    </tr>
    <tr>  
      <td>County</td>
      <td><html:text property="county" /></td>
    </tr>
    <tr>  
      <td>School District</td>
      <td><html:text property="schdistrict" /></td>
    </tr>
    <tr>  
      <td>Federal ID Number</td>
      <td><html:text property="fedid" /></td>
    </tr>
    <tr>
      <td colspan="2">Please select “Submit” below, as only one user account will be established per institution<br/>
          <%--<html:checkbox property="readaccess" />Read<br/>
          <html:checkbox property="editaccess" />Edit<br/>--%>
          <html:checkbox property="submitaccess" />Submit</td>
    </tr>
    <tr>
      <td>New RMO?</td>
      <td>
            <html:radio property="newRmo" value="true"/>Yes &nbsp;&nbsp;&nbsp;  
            <html:radio property="newRmo" value="false"/>No</td>
    </tr>
    <tr>  
      <td colspan="2"><html:hidden property="grantprogram" value="lgrmif"/>
          <html:reset value="Reset" /><html:submit value="Submit" /></td>
    </tr>
    </html:form>
  </table>

  <p>For questions regarding the LGRMIF Grant Program please contact the 
  State Archives Grants Administration Unit at archgrants@nysed.gov</p>
   
   </div>
   </div>
    
  <div class="footer">
    <p align="center">Cultural Education Center, Albany, New York 12230. Phone: (518) 474-6926</p>
  </div>
  
  </body>
</html>
