<%--
 * @author  shusak
 * @version 1.0
 * Development Environment        :  JDeveloper 10g
 * Name of the Application        :  cpAdminSelectMail.jsp
 *
 * Description
 * This page is for c/p admin to create a new email template for initial/final grant
 * award/denial emails for programs sa/co/di.
 * modified 3/20/13 to include cp reviewer emails (req. for participation, rev assignments)
--%>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>CP Admin Email Selection</title>
  </head>
  <body>
  
  <h5>C/P Program - Create email template</h5>
  
  <form method="POST" action="adminMassEmail.do?item=newtemplate" >
  <table width="95%" align="center" summary="for layout only">
    <tr>
      <td>Choose a Program: </td>
    </tr>
    <tr>
      <td><select name="fccode" >
           <option value="6">C/P Statutory</option>
           <option value="7">C/P Coordinated</option>
           <option value="5">C/P Discretionary</option>  
           <option value="20">State Aid: CJH & NYHS</option>  
          </select>
        </td>
    </tr>
    <tr>
        <td height="20"/>
    </tr>
    <tr>
        <td>Choose an email template: (you may then edit the email text, and 
            choose recipients)</td>
    </tr>
    <tr>
      <td><input type="RADIO" name="type" value="Initial" checked="checked" />Application Approved Email<br/>
          <input type="RADIO" name="type" value="Final" />Final Report Approved Email<br/>
          <input type="RADIO" name="type" value="Deny" />Application Denied Email<br/>
          <input type="RADIO" name="type" value="Prov" />Provisional Award Email (For C/P Discretionary) <font color="red">*New</font><br/>
          <input type="radio" name="type" value="OSC"/>Intent to Award Pending OSC Approval (For C/P Coordinated)<br/>
          <input type="radio" name="type" value="Custom"/>Custom Email Text<br/>
          --------------------------------------------------------------<br/>
          <input type="radio" name="type" value="participate"/>Request for Participation Email<br/>
          <input type="radio" name="type" value="partremind"/>Request for Participation Reminder Email<br/>
          <input type="radio" name="type" value="assignment"/>Reviewer Assignments Email<br/>
          --------------------------------------------------------------<br/>
          <input type="radio" name="type" value="staidInitial"/>Application Approved (CJH & NYHS Only)<br/>
          <input type="radio" name="type" value="staidFinal"/>Final Approved (CJH & NYHS Only)
          </td>
    </tr>
    <tr>
      <td><input type="HIDDEN" name="m" value="<c:out value='${param.p}' />" />
          <input type="SUBMIT" value="Create Email Template" /></td>
    </tr>
  </table>
  </form>
 
    
  </body>
</html>