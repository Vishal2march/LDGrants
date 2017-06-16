package gov.nysed.oce.ldgrants.shared.dao.datacon;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.*; 

import org.springframework.jdbc.datasource.*; 

import org.springframework.jdbc.object.*; 

import org.springframework.jdbc.support.*; 

import javax.sql.*; 

public class dataconimpl implements datacon 

{ 
	
   private DataSource dataSource; 
   
   
   public void setDataSource(DataSource ds) 

   { 

      dataSource = ds; 

   } 

   public DataSource dbcon() 

   { 

      return dataSource; 

   } 

} 
