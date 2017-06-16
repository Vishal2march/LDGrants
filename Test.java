package gov.nysed.oce.ldgrants;

import gov.nysed.oce.ldgrants.grants.common.service.NarrativeService;

public class Test {

	public static void main(String[] args) {

		NarrativeService narrativeService = new NarrativeService();

		narrativeService.deleteNarrative(1L, 1L);

	}

}
