package income;


import income.model.JobsDAO;
import income.model.JobsDetailsDao;
import income.model.JobsDetailsEntity;
import income.model.JobsEntity;

import java.sql.SQLException;
import java.util.List;

public class Controller {
private Main main;
    private JobsDAO jobs;
    private JobsDetailsDao jobsDetails=new JobsDetailsDao();
    public Controller(Main main){
        this.main=main;
    }

     public void LoadJobsDataFromBase() throws SQLException, ClassNotFoundException {
         this.jobs=new JobsDAO();
         main.addJobs(jobs.getJobs());
     }
     public List<JobsDetailsEntity> loadJobsDetailFromBase(JobsEntity job){
         return jobsDetails.findJobsDetail(job);
     }

     public List<JobsDetailsEntity> jobsDetailsByYear(JobsEntity job,int year){
         return jobsDetails.findJobsDetailsByYear(job,year);
     }
    public List<JobsDetailsEntity> jobsDetailsByMonth(JobsEntity job,int month){
        return jobsDetails.findJobsDetailsByMonth(job,month);
    }

}
