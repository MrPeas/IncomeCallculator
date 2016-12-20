package income.DBConnection;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Created by Janusz on 09.12.2016.
 */
public class EmProvider {
    private static EmProvider ourInstance = new EmProvider();
    private EntityManagerFactory emf = null;

    public static EmProvider getInstance() {
        return ourInstance;
    }

    private EmProvider() {
    }

    public EntityManagerFactory getEmf() {
        if(emf==null){
            emf=Persistence.createEntityManagerFactory("Eclipselink_JPA");
            return emf;
        }else {
            return emf;
        }
    }
    public EntityManager createEm(){
        return getEmf().createEntityManager();
    }

    public void closeEm(EntityManager em){
        if(em!=null){
            em.close();
        }
    }

}
