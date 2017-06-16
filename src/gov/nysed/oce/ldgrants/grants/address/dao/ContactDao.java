package gov.nysed.oce.ldgrants.grants.address.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.BeanPropertyRowMapper;

import gov.nysed.oce.ldgrants.grants.address.domain.Contact;
import gov.nysed.oce.ldgrants.grants.address.domain.ContactType;
import gov.nysed.oce.ldgrants.grants.address.domain.ProjectManager;
import gov.nysed.oce.ldgrants.user.domain.User;

public class ContactDao extends GenericDao<Contact, Long> {
	
	
	
	@Override
	public Contact select(Long id) {
				
		
		return null;
	}

	
	
	@Override
	public Long insert(Contact c, User user) {
		
		return null;
	}
	
	
	
	
	
	
	

	@Override
	public Long update(Contact c, User user) {

		
		
		return null;
	}

	
	
	
	
	
	@Override
	public boolean delete(Long id) {
		
		 String sql = "DELETE FROM CONTACTS WHERE id=?";		
			try {
				
				int rows = jt.update(sql, new Object[] { id });
				
				return (rows == 1);
	      
			}
			catch (Exception e) 
			{
				System.out.println("ContactDao.delete()" + e.getMessage());
			}
		return false;
	}
	
	
	
	
	
	

	public List<Contact> searchByProjectManagerId(Long id){
		
		List<Contact> cList = new ArrayList<Contact>();
		String sql = "  SELECT ID, "+
					 "      CONTACT_VALUE, "+
					 "      IS_ACTIVE, "+
					 "      PRM_ID, "+
					 "      CONTACT_TYPE_CODE "+
					 " FROM CONTACTS "+
					 " WHERE PRM_ID = ? " ;
		
		try{		
			List queryList = jt.queryForList(sql,new Object[] {id});
			
			for (int i = 0; i < queryList.size(); i++) {

				Map queryMap = (Map) queryList.get(i);
									
					Contact contact = new Contact();
					contact.setId(((BigDecimal) queryMap.get("ID")).longValue());
					contact.setContactValue((String) queryMap.get("contact_Value"));
					contact.setIsActive((Boolean) queryMap.get("is_active"));
					contact.setPrmId(((BigDecimal) queryMap.get("prm_ID")).longValue());
					contact.setContactTypeCode(((BigDecimal) queryMap.get("contact_type_code")).longValue());
					
					//get contact type  enum
					if(contact.getContactTypeCode()!=null)					
						contact.setContactType(ContactType.searchByContactTypeId(contact.getContactTypeCode().intValue()));
						
				
				cList.add(contact);				
			}				
		}catch(Exception e){
			System.err.println("error ContactDao.searchByProjectManagerId() " + e.toString());
		}	
		return cList;	
	}
	
	
	
	
	
public List<Contact> searchByGrantStaffId(Long id){
		
		List<Contact> cList = new ArrayList<Contact>();
		String sql = "  SELECT ID, "+
					 "      CONTACT_VALUE, "+
					 "      IS_ACTIVE, "+
					 "      GS_ID, "+
					 "      CONTACT_TYPE_CODE "+
					 " FROM CONTACTS "+
					 " WHERE GS_ID = ? " ;
		
		try{		
			List queryList = jt.queryForList(sql,new Object[] {id});
			
			for (int i = 0; i < queryList.size(); i++) {

				Map queryMap = (Map) queryList.get(i);
									
					Contact contact = new Contact();
					contact.setId(((BigDecimal) queryMap.get("ID")).longValue());
					contact.setContactValue((String) queryMap.get("contact_Value"));
					contact.setIsActive((Boolean) queryMap.get("is_active"));
					contact.setPrmId(((BigDecimal) queryMap.get("GS_ID")).longValue());
					contact.setContactTypeCode(((BigDecimal) queryMap.get("contact_type_code")).longValue());
					
					//get contact type  enum
					if(contact.getContactTypeCode()!=null)					
						contact.setContactType(ContactType.searchByContactTypeId(contact.getContactTypeCode().intValue()));
					
				
				cList.add(contact);				
			}				
		}catch(Exception e){
			System.err.println("error ContactDao.searchByGrantStaffId() " + e.toString());
		}	
		return cList;	
	}





@Override
public List<Contact> selectAll() {
	// TODO Auto-generated method stub
	return null;
}

}
