package income.DAO;

import income.DBConnection.EmProvider;
import income.model.JobDetailsEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.sql.Date;
import java.util.List;

/**
 * Created by Janusz on 12.12.2016.
 */
public class DAOJobDetailsImpl extends DAOAbstract<JobDetailsEntity> implements DAOJobDetails {
    private EntityManager em;
    private ObservableList<JobDetailsEntity> jobDetails;
    private List<JobDetailsEntity> jobsByMonth;
    private List<JobDetailsEntity> jobsByYear;

    //TODO
    @Override
    public List<JobDetailsEntity> findAll() {
        return null;
    }

    @Override
    public ObservableList<JobDetailsEntity> findByIdJob(long jobId) {
        em = EmProvider.getInstance().createEm();
        Query query = em.createNamedQuery("JobDetails.findByIdJob");
        query.setParameter("jobId", jobId);
        jobDetails = FXCollections.observableList(query.getResultList());
        em.close();
        return jobDetails;
    }

    @Override
    public List<JobDetailsEntity> findJobsDetailsByYear(long id, int year) {
        em = EmProvider.getInstance().createEm();
        Query query = em.createNamedQuery("JobDetails.findByYear&&idJob");
        query.setParameter("jobId", id);
        query.setParameter("year", year);
        this.jobsByYear = query.getResultList();
        em.close();
        return jobsByYear;
    }

    @Override
    public List<JobDetailsEntity> findJobsDetailsByMonth(long id, int month) {
        em = EmProvider.getInstance().createEm();
        Query query = em.createNamedQuery("JobDetails.findByMonth&&idJob");
        query.setParameter("jobId", id);
        query.setParameter("month", month);
        jobsByMonth = query.getResultList();
        em.close();
        return jobsByMonth;
    }

    @Override
    public boolean isExistJobDetail(long id, Date date) {
        try {
            em = EmProvider.getInstance().createEm();
            Query query = em.createNamedQuery("JobDetails.findByDate&&idJob");
            query.setParameter("jobId", id);
            query.setParameter("date", date);
            JobDetailsEntity jobDetails = (JobDetailsEntity) query.getSingleResult();
            em.close();
            return true;
        }catch (NoResultException e){
            em.close();
            return false;
        }
    }
    @Override
    public ObservableList<JobDetailsEntity> getJobDetails() {
        return jobDetails;
    }
    @Override
    public void removeJobsDetailFromList(JobDetailsEntity job){
        jobDetails.remove(job);
    }
    @Override
    public void updateListJobDetail(JobDetailsEntity job, int index){
        jobDetails.set(index,job);
    }
    @Override
    public void addJobDetailToList(JobDetailsEntity jobDetails) {
        this.jobDetails.add(jobDetails);
    }

}
