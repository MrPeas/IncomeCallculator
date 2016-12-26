package income.DAO;

import java.util.List;

/**
 * E is entity class
 */
public interface DAO <E> {
    List<E> findAll();
    void add(E entity);
    void update(E entity);
    void remove(Long id);
}
