package income.DAO;

import income.DBConnection.EmProvider;

import javax.persistence.EntityManager;
import java.lang.reflect.ParameterizedType;

/**
 * Created by Janusz on 12.12.2016.
 */
public abstract class DAOAbstract<E> implements DAO<E> {
    private EntityManager em;
    final Class<E> typeClass;

    @SuppressWarnings("unchecked")
    public DAOAbstract() {
        this.typeClass = (Class<E>)
                ((ParameterizedType) getClass()
                        .getGenericSuperclass())
                        .getActualTypeArguments()[0];
    }

    public boolean add(E entity) {
        try {
            em = EmProvider.getInstance().createEm();
            em.getTransaction().begin();
            em.persist(entity);
            em.getTransaction().commit();
            return true;

        }catch(Exception e){
            return false;
        }
    }

    public boolean update(E entity) {
        try {
            em = EmProvider.getInstance().createEm();
            em.getTransaction().begin();
            em.merge(entity);
            em.getTransaction().commit();
            EmProvider.getInstance().closeEm(em);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean remove(Long id) {
        try {
            em = EmProvider.getInstance().createEm();
            E job = em.find(typeClass, id);
            em.getTransaction().begin();
            em.remove(job);
            em.getTransaction().commit();
            EmProvider.getInstance().closeEm(em);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
