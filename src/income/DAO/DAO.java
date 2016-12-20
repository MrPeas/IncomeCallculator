package income.DAO;

import java.util.List;

/**
 * E is entity class
 */
public interface DAO <E> {
    List<E> findAll();
    boolean add(E entity);
    boolean update(E entity);
    boolean remove(Long id);
}
