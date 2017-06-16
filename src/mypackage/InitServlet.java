package mypackage;
/**
 * 
 * E. Reed 4/30/2014 
 * 
 * CVE-2014-0114 - Attack through class loader exploit found on 4/29/2014
 * 
 * This class effectivly looks for and logs any attack. 
 * 
 * SH: added to ldgrants on 5/2/13; see also SafeBeanUtils.java and web.xml
 */

import java.io.IOException;

import javax.servlet.ServletConfig;

import javax.servlet.ServletException;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.struts.action.ActionServlet;

public class InitServlet extends ActionServlet{
    public InitServlet() {
        super();
    }
    
  public void init(ServletConfig config) throws ServletException
    {
    BeanUtilsBean.setInstance(new SafeBeanUtils());


  }

  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {

    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {

    }

    @Override
    public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException
    {
        throw new UnsupportedOperationException("this Servlet initialize use only");
    }
}
