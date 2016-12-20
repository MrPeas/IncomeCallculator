package income.DAO;

import income.DBConnection.EmProvider;
import income.model.JobDetailsEntity;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by Janusz on 12.12.2016.
 */
public class DAOJobDetailsImpl extends DAOAbstract<JobDetailsEntity> implements DAOJobDetails {
    private EntityManager em;

    //TODO
    @Override
    public List<JobDetailsEntity> findAll() {
        return null;
    }

    @Override
    public List<JobDetailsEntity> findByIdJob(long jobId) {
        em = EmProvider.getInstance().createEm();
        Query query = em.createNamedQuery("JobDetails.findByIdJob");
        query.setParameter("jobId", jobId);
        List<JobDetailsEntity> jobDetails = query.getResultList();
        em.close();
        return jobDetails;
    }

    @Override
    public List<JobDetailsEntity> findJobsDetailsByYear(long id, int year) {
        em = EmProvider.getInstance().createEm();
        Query query = em.createNamedQuery("JobDetails.findByYear&&idJob");
        query.setParameter("jobId", id);
        query.setParameter("year", year);
        List<JobDetailsEntity> jobDetails = query.getResultList();
        em.close();
        return jobDetails;
    }

    @Override
    public List<JobDetailsEntity> findJobsDetailsByMonth(long id, int month) {
        em = EmProvider.getInstance().createEm();
        Query query = em.createNamedQuery("JobDetails.findByMonth&&idJob");
        query.setParameter("jobId", id);
        query.setParameter("month", month);
        List<JobDetailsEntity> jobDetails = query.getResultList();
        em.close();
        return jobDetails;
    }
}
