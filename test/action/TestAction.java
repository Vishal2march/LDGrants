package gov.nysed.oce.ldgrants.test.action;

import com.opensymphony.xwork2.ActionSupport;

public abstract class TestAction extends ActionSupport {

	public String test() {
		boolean result = true;

		if (insert() == true)
			System.out.println("insert test passed");
		else
			result = false;

		if (select() == true)
			System.out.println("select test passed");
		else
			result = false;

		if (update() == true)
			System.out.println("update test passed");
		else
			result = false;

		if (search() == true)
			System.out.println("search test passed");
		else
			result = false;

		if (delete() == true)
			System.out.println("delete test passed");
		else
			result = false;

		if (result == true)
			System.out.println("OVERALL: All test passed, hurray :)");
		else
			System.out.println("OVERALL: At least one test FAILED :(");

		return SUCCESS;
	};

	protected abstract boolean search();

	protected abstract boolean select();

	protected abstract boolean insert();

	protected abstract boolean update();

	protected abstract boolean delete();

}
