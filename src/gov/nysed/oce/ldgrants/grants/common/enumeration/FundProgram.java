package gov.nysed.oce.ldgrants.grants.common.enumeration;

public enum FundProgram {

	ADULT_LITERACY(40), 
	FAMILY_LITERACY(42),
	CONSTRUCTION(86),
	LGRMIF(80),
	CP_DISCRETIONARY(5),
	CP_STATUTORY(6);

	
	
	private final long fundCode;
	
	//constructor for FundProgram
	FundProgram(long fundCode){
		this.fundCode = fundCode;
	}

	public long getFundCode() {
		return fundCode;
	}
	
	
	
	
	public static FundProgram searchByFundCode(int fundCode){
		FundProgram fundProgram=null;
		
		switch(fundCode){
			case 40:
				fundProgram = ADULT_LITERACY;
				break;
			case 42:
				fundProgram = FAMILY_LITERACY;
				break;
			case 86:
				fundProgram = CONSTRUCTION;
				break;
			case 80:
				fundProgram = LGRMIF;
				break;
			case 5:
				fundProgram = CP_DISCRETIONARY;
				break;
			case 6:
				fundProgram = CP_STATUTORY;
				break;
		}
		return fundProgram;
	}
	
	
}
