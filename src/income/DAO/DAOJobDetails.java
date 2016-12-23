package income.DAO;

import income.model.JobDetailsEntity;
import javafx.collections.ObservableList;

import java.util.List;

/**
 * Created by Janusz on 12.12.2016.
 */
public interface DAOJobDetails extends DAO<JobDetailsEntity>{
    ObservableList<JobDetailsEntity> findByIdJob(long jobId);
    List<JobDetailsEntity>findJobsDetailsByYear(long id,int year);
    List<JobDetailsEntity> findJobsDetailsByMonth(long id,int month);
    void removeJobsDetail(JobDetailsEntity job);
    void updateListJobDetail(JobDetailsEntity job, int index);
    void addJobDetailToList(JobDetailsEntity jobDetails);
    ObservableList<JobDetailsEntity> getJobDetails();

}
