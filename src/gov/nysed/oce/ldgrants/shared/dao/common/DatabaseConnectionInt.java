package gov.nysed.oce.ldgrants.shared.dao.common;


import javax.sql.DataSource;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import gov.nysed.oce.ldgrants.shared.dao.datacon.datacon;

public interface DatabaseConnectionInt {

    //JDBC connection
    Resource res = new ClassPathResource("datacon.xml");
    BeanFactory factory = new XmlBeanFactory(res);
    datacon bean1 = (datacon)factory.getBean("datacon");
    DataSource ds = bean1.dbcon();
    JdbcTemplate jt = new JdbcTemplate(ds);

    DataSourceTransactionManager txManager = new DataSourceTransactionManager(ds);

}
