package com.scipionyx.butterflyeffect.backend.stocks.main.jobs.bitcoin.coinbase.getHistoricalPrice;

import java.util.Calendar;

import org.apache.commons.lang.StringUtils;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 
 * 
 * 
 * @author Renato Mendes
 *
 */
@RestController()
public class GetHistoricalPriceController {

	@Autowired(required = true)
	private JobLauncher jobLauncher;

	@Autowired(required = true)
	@Qualifier(value = "jobGetHistoricalPrice_Coinbase_bitcoin")
	private Job job;

	/**
	 * 
	 * @param exchange
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = { RequestMethod.PUT }, path = "/bitcoin/coinbase/getHistoricalPrice")
	public String run(@RequestParam(name = "date", required = false) String date) throws Exception {
		//
		JobParametersBuilder jobParametersBuilder = new JobParametersBuilder();
		jobParametersBuilder.addLong("timestamp", Calendar.getInstance().getTimeInMillis());
		if (StringUtils.isNotEmpty(date)) {
			jobParametersBuilder.addString("date", date);
		}

		//
		JobParameters jobParameters = jobParametersBuilder.toJobParameters();
		JobExecution run = jobLauncher.run(job, jobParameters);

		return run.getStepExecutions().toString();
	}

}
