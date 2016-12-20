package income.DAO;

import income.model.JobDetailsEntity;

import java.util.List;

/**
 * Created by Janusz on 12.12.2016.
 */
public interface DAOJobDetails extends DAO<JobDetailsEntity>{
    List<JobDetailsEntity> findByIdJob(long jobId);
    List<JobDetailsEntity>findJobsDetailsByYear(long id,int year);
    List<JobDetailsEntity> findJobsDetailsByMonth(long id,int month);

}
