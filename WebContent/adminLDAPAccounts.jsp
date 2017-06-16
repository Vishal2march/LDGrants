<%--
 * @author  Stefanie Husak
 * @version 2.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the Application        :  adminLDAPAccounts.jsp
 * Creation/Modification History  :
 *
 *     SH       3/1/07     Created
 *
 * Description
 * This page instructs the admin on how to create or update ldap account
 * attributes.  It also has a link to the OFT DA site.
--%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>User Accounts</title>
  </head>
  <body>  
 
  <h5>NYSDS User Accounts for Login</h5>
    
  <table width="90%" align="center" summary="for layout only">
    <tr>
      <th>Create/Update User's NYSDS user account and Reset Password</th>
    </tr>    
    <tr>
      <td>
        Use OFT's Delegated Admin website to create or update NYSDS (NYS Directory Services) user account and add entitlements
        so that the user may access the appropriate LDGrants pages.
        The Delegated Admin website is also used to reset passwords.
      </td>
    </tr>
    <tr>
      <td>       
      <%-- production url--%>
      <a href="http://ws04.nyenet.state.ny.us/" target="_blank" >
        OFT's NYSDS Delegated Admin System</a>  (Link opens in new window)</td>
    </tr>
    <tr>
      <td height="20"/>
    </tr>
    <tr>
      <td>      
        Before <b>creating</b>  an account you must have the following information:<br/>
        <ul>
          <li>User ID:</li>
            <ul>
              <li>For internal users:  GroupWise username</li>
              <li>For external users:  FirstName.LastName</li>
            </ul>
          <li>Address, City, State, Zip Code Information</li>
          <li>User's SEDREF Institution ID</li>
          <li>The grant programs the user will be allowed to access (ie. C/P Discretionary, 
          Family Literacy, LGRMIF, etc).</li>
        </ul>
      </td>
    </tr>
    
     <tr>
      <td>
        Before <b>updating </b> an account you must have the following information:<br/>
        <ul>
          <li>User ID:</li>
            <ul>
              <li>For internal users:  GroupWise username</li>
              <li>For external users:  FirstName.LastName</li>
            </ul>
          <li>User's SEDREF Institution ID</li>
          <li>The grant programs the user will be allowed to access.</li>
        </ul>
      </td>
    </tr>    
    <tr>
      <td height="30"><hr/></td>
    </tr>
    <tr >
      <td >
        <table align="center" border="1" summary="for layout only">
          <tr>
            <th colspan="2">SEDREF Institution ID's for Big 11</th>
          </tr>
          <tr>
            <th>Institution</th>
            <th>SEDREF ID</th>
          </tr>
          <tr>
            <td>Columbia University</td>
            <td>800000060098</td>
          </tr>
          <tr>
            <td>Cornell University</td>
            <td>800000059892</td>
          </tr>
          <tr>
            <td>New York State Library</td>
            <td>800000060476</td>
          </tr>
          <tr>
            <td>New York University</td>
            <td>800000060095</td>
          </tr>
          <tr>
            <td>University of Rochester</td>
            <td>800000060104</td>
          </tr>
          <tr>
            <td>Syracuse University</td>
            <td>800000060102</td>
          </tr>
          <tr>
            <td>NY Public Library</td>
            <td>800000060121</td>
          </tr>
          <tr>
            <td>SUNY Albany</td>
            <td>800000060094</td>
          </tr>
          <tr>
            <td>SUNY Binghamton</td>
            <td>800000060103</td>
          </tr>
          <tr>
            <td>SUNY Buffalo</td>
            <td>800000060101</td>
          </tr>
          <tr>
            <td>SUNY Stony Brook</td>
            <td>800000060097</td>
          </tr>
        </table>
      </td>
    </tr>
  </table>
  <br/>
  
  </body>
</html>
