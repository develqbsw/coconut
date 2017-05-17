package sk.qbsw.reporting.base.generating.mock.service;

import org.springframework.stereotype.Service;

@Service
public class CReportCreatorForMockService
{
	private int calls=0;
	public void call(){
		calls++;
	}
	public int getCalls ()
	{
		return calls;
	}

}
