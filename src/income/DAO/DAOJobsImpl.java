package income.DAO;

import income.DBConnection.EmProvider;
import income.model.JobsEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 * Created by Janusz on 09.12.2016.
 */
public class DAOJobsImpl extends DAOAbstract<JobsEntity> implements DAOJobs {
    private EntityManager em=null;
    private ObservableList<JobsEntity> allJobs;
    private ObservableList<JobsEntity> jobsByUserId;

    @Override
    public ObservableList<JobsEntity> findAll() {
        em=EmProvider.getInstance().createEm();
        Query query = em.createNamedQuery("JobsEntity.findAll",JobsEntity.class);
        allJobs =FXCollections.observableList(query.getResultList());
        EmProvider.getInstance().closeEm(em);
        return allJobs;
    }

    @Override
    public ObservableList<JobsEntity> findByIdUser(Long userId) {
        em=EmProvider.getInstance().createEm();
        Query query = em.createNamedQuery("JobsEntity.findByUserId",JobsEntity.class);
        query.setParameter("id",userId);
        jobsByUserId=FXCollections.observableList(query.getResultList());
        EmProvider.getInstance().closeEm(em);
        return jobsByUserId;
    }

    @Override
    public void addJobToList(JobsEntity job) {
        jobsByUserId.add(job);
    }

    @Override
    public void removeJobFromList(JobsEntity job) {
        jobsByUserId.remove(job);
    }

    @Override
    public int jobIndex(JobsEntity job) {
        return jobsByUserId.indexOf(job);
    }

    @Override
    public void updateJobInList(JobsEntity job, int index) {
        jobsByUserId.set(index,job);
    }
}
