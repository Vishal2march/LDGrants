package mypackage;


import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.regex.Pattern;
import org.apache.commons.beanutils.BeanUtilsBean;


/**
 * 
 * E. Reed 4/30/2014 
 * 
 * CVE-2014-0114 - Attack through class loader exploit found on 4/29/2014
 * 
 * This class effectivly looks for and logs any attack. 
 * 
 * SH: added to ldgrants on 5/2/13; see also InitServlet.java and web.xml
 */

public class SafeBeanUtils extends BeanUtilsBean 
{
    static org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory.getLog("gov.nysed.oti.common.sec.databean.SafeBeanUtils.class");
    private static final Pattern CLASS_ACCESS_PATTERN = Pattern.compile(
        "(.*\\.|^|.*|\\[('|\"))class(\\.|('|\")]|\\[).*",
        Pattern.CASE_INSENSITIVE);
 
    @SuppressWarnings("rawtypes")
    @Override
    public void populate(Object bean, Map properties) throws IllegalAccessException, InvocationTargetException 
    {
        for (Object obj : properties.keySet()) {
            if (obj instanceof String) {
                String key = (String) obj;
                    if (CLASS_ACCESS_PATTERN.matcher(key).matches()) {
                        log.fatal("ATTACK DETECTED CVE-2014-0114 : " + key);
                        throw new IllegalArgumentException("Attack detected: " + key);
                    }
            }
        }
    super.populate(bean, properties);
    }
}


