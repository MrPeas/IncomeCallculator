package income;


import income.DAO.DAOJobDetailsImpl;
import income.DAO.DAOJobsImpl;
import income.DAO.DAOUsersImpl;
import income.model.JobDetailsEntity;
import income.model.JobsEntity;
import income.model.UsersEntity;

import java.sql.SQLException;
import java.util.List;

public class Controller {
private Main main;
    private DAOJobsImpl jobs=new DAOJobsImpl();
    private DAOJobDetailsImpl jobsDetails=new DAOJobDetailsImpl();
    private DAOUsersImpl users=new DAOUsersImpl();

    public Controller(Main main){
        this.main=main;
    }

     public void LoadJobsDataFromBase(Long id) throws SQLException, ClassNotFoundException {
         main.addJobs(jobs.findByIdUser(id));
     }
     public List<JobDetailsEntity> loadJobsDetailFromBase(long id){
         return jobsDetails.findByIdJob(id);
     }

     public List<JobDetailsEntity> jobsDetailsByYear(long id, int year){
         return jobsDetails.findJobsDetailsByYear(id,year);
     }
    public List<JobDetailsEntity> jobsDetailsByMonth(long id, int month){
        return jobsDetails.findJobsDetailsByMonth(id,month);
    }

    public void insertJob(JobsEntity job){
        jobs.add(job);
    }
    public void editJob(JobsEntity job){
        jobs.update(job);
    }
    public void removeJob(Long id){jobs.remove(id);}
    public void insertJobDetail(JobDetailsEntity jobDetails){
        jobsDetails.add(jobDetails);
    }
    public void editJobDetails(JobDetailsEntity jobDetails){
        jobsDetails.update(jobDetails);
    }
    public void removeJobDetail(Long id){jobsDetails.remove(id);}
    public UsersEntity getUserByName(String username){
        return users.findByUsername(username);
    }
}
