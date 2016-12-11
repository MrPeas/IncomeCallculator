package income.DAO;

import income.model.JobsEntity;
import income.model.UsersEntity;

import java.util.List;

/**
 * Created by Janusz on 09.12.2016.
 */
public interface DAOJobs extends DAO<JobsEntity> {
public List<JobsEntity> findByIdUser(UsersEntity entity);
}
