package com.ubs.opsit.interviews.impl;

import com.ubs.opsit.interviews.TimeConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BerlinClockConverter implements TimeConverter{
	
	private static final java.util.logging.Logger LOG = LoggerFactory.getLogger(BerlinClockConverter.class);

	private String[][] berlinClock = {{"O"},
					{"O", "O", "O", "O"},
					{"O", "O", "O", "O"},
					{"O", "O", "O", "O", "O", "O", "O", "O", "O", "O", "O"},
					{"O", "O", "O", "O"}};
	@Override
	public String convertTime(String aTime) {
		String berlinString = null;
		if(aTime != null)
		{
			String[] digiTime = aTime.split(":");
			try
			{
				if(digiTime.length > 3)
				{
					throw new Exception("");
				}
				for(int i=0; i< digiTime.length; i++)
				{
					int time = Integer.parseInt(digiTime[i]);
					switch(i)
					{
					case 0: Integer hrsMulFour = 0;
						if(time >=5)
						{
							Integer hrsMulFive = time/5;
							buildBerlinClockPartially(1, hrsMulFive*5, 5);
							hrsMulFour = time%5;
						}
						if(hrsMulFour > 0)
						{
							buildBerlinClockPartially(2, hrsMulFour, 1);
						}
						break;	
					case 1: Integer minMulFour = 0;
						if(time >=5)
						{
							Integer minMulFive = time/5;
							buildBerlinClockPartially(3, minMulFive*5, 5);
							minMulFour = time%5;
						}
						if(minMulFour > 0)
						{
							buildBerlinClockPartially(4, minMulFour, 1);
						}
						break;	
					case 2: 	
						if(time%2 == 0) 
						{
							berlinClock[0][0] = "Y";
						}
						break;
					}
				}
				berlinString = convertClockIntoString();
			}
			catch(NumberFormatException e)
			{
				LOG.error("Time cannot be alphanumeric: "+aTime);
			} catch (Exception e) {
				LOG.error("Invalid time pattern: "+aTime);
			}
		}
		LOG.info("Berlin Clock output for time "+aTime+" is: "+berlinString);
		return berlinString;
	}
	private String convertClockIntoString() {
		StringBuilder clock = new StringBuilder();
		for(int i =0 ; i < berlinClock.length; i++)
		{
			for(int j = 0; j< berlinClock[i].length; j++)
			{
				clock.append(berlinClock[i][j]);
			}
			if(i != berlinClock.length - 1)
			{
				clock.append("\n");
			}
		}
		return clock.toString();
	}
	private void buildBerlinClockPartially(int index, Integer hrsMulFive, int dividor) {
		String[] partialClock = berlinClock[index];
		for(int i=0; i< partialClock.length && hrsMulFive >= dividor; i++, hrsMulFive = hrsMulFive - dividor)
		{
			if((index == 3 && (i != 2 && i != 5 && i != 8)) || (index == 4))
			{
				partialClock[i] = "Y";
			}
			else
			{
				partialClock[i] = "R";
			}
		}
	}
}
