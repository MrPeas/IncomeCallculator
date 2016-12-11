package income.DAO;

import income.DBConnection.EmProvider;
import income.model.JobsEntity;
import income.model.UsersEntity;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * Created by Janusz on 09.12.2016.
 */
public class DAOJobsImpl implements DAOJobs {
    private EntityManager em=null;


    @Override
    public List<JobsEntity> findAll() {
        em=createEm();
        List<JobsEntity> jobs=em.createNamedQuery("JobsEntity.findAll",JobsEntity.class).getResultList();
        closeEm();
        return jobs;
    }

    @Override
    public boolean add(JobsEntity entity) {
        try{
            em=createEm();
            em.getTransaction().begin();
            em.persist(entity);
            em.getTransaction().commit();
            return true;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(JobsEntity entity) {
        try{
            em=createEm();
            em.getTransaction().begin();
            em.merge(entity);
            em.getTransaction().commit();
            closeEm();
            return true;
        }catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean remove(JobsEntity entity) {
        try{
            em=createEm();
            JobsEntity job = em.find(JobsEntity.class,entity.getId());
            em.getTransaction().begin();
            em.remove(job);
            em.getTransaction().commit();
            closeEm();
            return true;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<JobsEntity> findByIdUser(UsersEntity entity) {
        return em.createQuery("SELECT j from JobsEntity j "
                , JobsEntity.class).getResultList();
    }

    private void closeEm(){
        if(em!=null){
            em.close();
            em=null;
        }
    }

    private EntityManager createEm(){
        if(em==null){
            return EmProvider.getInstance().getEmf().createEntityManager();
        }else{
            return em;
        }
    }
}
