 /******************************************************************************
  * @author  : ? Unknown
  * @Version : 2.0
  *
  * Development Environment        :  JDeveloper 10g
  * Name of the file               :  SSOprocessorBean.java
  * Creation/Modification History  :
  *
  * SH   3/1/07      Modified SL's original code after I had the LDAP 
  * attributes changed (meeting with Jim Nortrup OFT).
  *
  * Description
  * This class will interact with the NYSDS LDAP authentication.  It will retrieve the http header
  * values when a user tries to log on.  The header values contain info about the username,
  * institution, and the type of access the user has (read, edit, submit). The code was
  * obtained by SLowe from WHamm.  
  * 
  * $Id: SSOprocessorBean.java 4689 2009-06-11 14:30:57Z shusak $
  *****************************************************************************/
 package gov.nysed.osa.datacollection.beans;
 import javax.servlet.http.*;
 import java.util.LinkedHashMap;
 import java.util.Enumeration;

 public class SSOprocessorBean{

 private LinkedHashMap appAttributes;

 public  SSOprocessorBean(HttpServletRequest request)
 {
   appAttributes = new LinkedHashMap();
   Enumeration enumHdrs =  request.getHeaderNames();
   
   System.out.println("Inside SSOprocessorBean");

   while (enumHdrs.hasMoreElements()){
         String hdrName = (String)enumHdrs.nextElement();
         System.out.println(hdrName + " : "+ request.getHeader(hdrName));
          System.out.println(request.getMethod());
          
         
         //NOTE: Jim Nortrup 7/31/07 changed http header INSTCODE to INSTID
         if (hdrName.equalsIgnoreCase("SM_USER")) {
             appAttributes.put(hdrName,request.getHeader(hdrName));
         } else if (hdrName.equalsIgnoreCase("INSTID")) {
             appAttributes.put(hdrName,request.getHeader(hdrName));
         } else if (hdrName.equalsIgnoreCase("PRGSA")) {
             appAttributes.put(hdrName,request.getHeader(hdrName));
         } else if (hdrName.equalsIgnoreCase("PRGCO")) {
             appAttributes.put(hdrName,request.getHeader(hdrName));
         } else if (hdrName.equalsIgnoreCase("PRGDI")) {
             appAttributes.put(hdrName,request.getHeader(hdrName));
         } else if (hdrName.equalsIgnoreCase("ADMIN1")) {
             appAttributes.put(hdrName,request.getHeader(hdrName));
         } else if (hdrName.equalsIgnoreCase("ADMIN2")) {
             appAttributes.put(hdrName,request.getHeader(hdrName));
         }else if (hdrName.equalsIgnoreCase("REVIEW")) {
             appAttributes.put(hdrName,request.getHeader(hdrName));
         }else if (hdrName.equalsIgnoreCase("PRGFL")) {
             appAttributes.put(hdrName,request.getHeader(hdrName));
         }else if (hdrName.equalsIgnoreCase("PRGAL")) {
             appAttributes.put(hdrName,request.getHeader(hdrName));
         }else if (hdrName.equalsIgnoreCase("SAADMIN")) {
             appAttributes.put(hdrName,request.getHeader(hdrName));
         }else if (hdrName.equalsIgnoreCase("SAREVIEW")) {
             appAttributes.put(hdrName,request.getHeader(hdrName));
         }else if (hdrName.equalsIgnoreCase("PRGSUBMIT")) {
             appAttributes.put(hdrName,request.getHeader(hdrName));
         }else if (hdrName.equalsIgnoreCase("PRGREAD")) {
             appAttributes.put(hdrName,request.getHeader(hdrName));
         }else if (hdrName.equalsIgnoreCase("PRGEDIT")) {
             appAttributes.put(hdrName,request.getHeader(hdrName));            
         }else if (hdrName.equalsIgnoreCase("PRGCN")) {        ////construction
           appAttributes.put(hdrName,request.getHeader(hdrName));
       }     
         
   }
 }
 public String getAttribute(String attrName)
 {
 if (appAttributes.containsKey(attrName)) {
      return ((String) appAttributes.get(attrName));
 } else  {
      return null;
 }
 }

 public String getAppAttributesString (String appStringName, String AttributeName)
 {
 //Still Wip as clarifications have been just recvd from OFT
 String appString;

 if (appAttributes.containsKey(appStringName)) {
     appString = (String) appAttributes.get(appStringName);    
     return "x";
 } else {
     return null;  
 }
 }

 private String getToken(String appString, String tokenName)
 //Still Wip as clarifications have been just recvd from OFT
 {
   String tokens[];
   int i;
   
   tokens = appString.split(";");
   for ( i = 0; i < tokens.length; i++) 
   {
    if (tokens[i].equalsIgnoreCase(tokenName)) 
    {
       
    }
    
   }
   return "x";
   
   
 }

 }
