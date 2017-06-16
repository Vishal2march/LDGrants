package gov.nysed.oce.ldgrants.grants.help.dao;

public abstract class GenericDao<T, PK> implements GenericDaoInt<T, PK> {

	public String sequence = null;
	
	public Long getNextId(String sequence) {
		Long nextval = null;
		String sql = "select "+ sequence + ".nextval from dual";
		try {
			nextval = (long) jt.queryForObject(sql, Integer.class);

		} catch (Exception ex) {
			System.err.println("error getNextId() " + ex.toString());
		} 
		finally 
		{}
		return nextval;
	}
	
	public String getSequence() {
		return sequence;
	}

	public void setSequence(String sequence) {
		this.sequence = sequence;
	}	
}
