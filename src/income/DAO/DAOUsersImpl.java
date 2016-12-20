package income.DAO;

import income.DBConnection.EmProvider;
import income.model.UsersEntity;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by Janusz on 12.12.2016.
 */
public class DAOUsersImpl extends DAOAbstract<UsersEntity> implements DAOUsers {
    private EntityManager em;
    @Override
    public List<UsersEntity> findAll() {
        return null;
    }

    @Override
    public UsersEntity findByUsername(String username) {
        try {
            em = EmProvider.getInstance().createEm();
            Query query = em.createNamedQuery("UserEntity.findByUsername");
            query.setParameter("login", username);
            UsersEntity user = (UsersEntity) query.getSingleResult();
            em.close();
            return user;
        }catch (NoResultException e){
            em.close();
            return null;
        }

    }
}
