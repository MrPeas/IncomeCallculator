package income.DAO;

import income.model.JobsEntity;
import javafx.collections.ObservableList;

/**
 * Created by Janusz on 09.12.2016.
 */
public interface DAOJobs extends DAO<JobsEntity> {
    ObservableList<JobsEntity> findByIdUser(Long userId);
    void addJobToList(JobsEntity job);
    void removeJobFromList(JobsEntity job);
    int jobIndex(JobsEntity job);
    void updateJobInList(JobsEntity job,int index);
}
