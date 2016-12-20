package income.DAO;

import income.DBConnection.EmProvider;
import income.model.JobsEntity;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by Janusz on 09.12.2016.
 */
public class DAOJobsImpl extends DAOAbstract<JobsEntity> implements DAOJobs {
    private EntityManager em=null;

    @Override
    public List<JobsEntity> findAll() {
        em=EmProvider.getInstance().createEm();
        Query query = em.createNamedQuery("JobsEntity.findAll",JobsEntity.class);
        List<JobsEntity> jobs=query.getResultList();
        EmProvider.getInstance().closeEm(em);
        return jobs;
    }

    @Override
    public List<JobsEntity> findByIdUser(Long userId) {
        em=EmProvider.getInstance().createEm();
        Query query = em.createNamedQuery("JobsEntity.findByUserId",JobsEntity.class);
        query.setParameter("id",userId);
        List<JobsEntity> jobs=query.getResultList();
        EmProvider.getInstance().closeEm(em);
        return jobs;
    }

}
