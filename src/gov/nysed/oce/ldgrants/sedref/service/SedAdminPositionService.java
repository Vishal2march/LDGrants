package gov.nysed.oce.ldgrants.sedref.service;

import java.util.ArrayList;
import java.util.List;

import gov.nysed.oce.ldgrants.sedref.dao.SedAdminPositionDao;
import gov.nysed.oce.ldgrants.sedref.dao.SedContactInfoDao;
import gov.nysed.oce.ldgrants.sedref.domain.SedAdminPosition;
import gov.nysed.oce.ldgrants.sedref.domain.SedAdminPositionType;
import gov.nysed.oce.ldgrants.shared.LDGrantSessionManager;

public class SedAdminPositionService extends LDGrantSessionManager{
	
	SedAdminPositionDao adminDao = new SedAdminPositionDao();
	SedContactInfoDao contactDao = new SedContactInfoDao();
	
	
	public SedAdminPosition selectByAdminPosId(long adminPosId){
		return adminDao.select(adminPosId);
	}
	
	
	
	public List<SedAdminPosition> selectByInstId(long instId){
		return adminDao.searchByInstId(instId);
	}
	
	
	
	
	
	
	
	public SedAdminPosition selectByAdminPosIdWithContacts(long adminPosId){
		
		SedAdminPosition sap =null;
		try{
			
			sap = adminDao.select(adminPosId);//query sed_admin_positions
			if(sap!=null)
				sap.setContactInfos( contactDao.searchByAdminPositionId(adminPosId));//query sed_contact_info
			
			
		}catch(Exception e){
			System.err.println("SedAdminPositionService.selectByAdminPosIdWithContacts "+ e.getMessage());
		}
		return sap;
	}
	
	
	
	
	
	public SedAdminPosition selectByInstIdAdminPositionType(long instId, SedAdminPositionType adminType){
		
		ArrayList<SedAdminPosition> positions =null;
		SedAdminPosition sap = null;
		try{
			
			positions = adminDao.searchByInstId(instId);//get ALL admin positions for this inst_id
			if(positions!=null){
			
				//filter for the selected position type
				for(SedAdminPosition s: positions){
					
					if(adminType == SedAdminPositionType.searchByAdminTypeId(s.getAdminPosId().intValue()))
						sap = s;//position type found					
				}
				
				if(sap!=null)
					sap.setContactInfos( contactDao.searchByAdminPositionId(sap.getAdminPosId()));//get contacts for admin
			}
			
		}catch(Exception e){
			System.err.println("SedAdminPositionService.selectByAdminPosIdWithContacts "+ e.getMessage());
		}
		return sap;
	}

}
