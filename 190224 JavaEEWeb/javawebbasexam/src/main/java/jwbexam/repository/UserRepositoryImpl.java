package jwbexam.repository;

import jwbexam.domain.entities.User;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

public class UserRepositoryImpl implements UserRepository {
    private final EntityManager entityManager;

    @Inject
    public UserRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public User save(User entity) {
        this.entityManager.getTransaction().begin();
        try {
            this.entityManager.persist(entity);
            this.entityManager.getTransaction().commit();

            return entity;
        } catch (Exception e) {
            this.entityManager.getTransaction().rollback();

            return null;
        }
    }

	@Override
    public User update(User entity) {
        this.entityManager.getTransaction().begin();
        try {
            User updatedUser = this.entityManager.merge(entity);
            this.entityManager.getTransaction().commit();

            return updatedUser;
        }catch (Exception e) {
            this.entityManager.getTransaction().rollback();

            return null;
        }
    }
	
    @Override
    public List<User> findAll() {
        return this.entityManager
                .createQuery("SELECT u FROM users u", User.class)
                .getResultList();
    }

    @Override
    public User findById(String id) {
		try {
			return this.entityManager
                .createQuery("SELECT u FROM users u WHERE u.id = :id", User.class)
                .setParameter("id", id)
                .getSingleResult();				
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Long size() {
        return this.entityManager
                .createQuery("SELECT count(u) FROM users u", Long.class)
                .getSingleResult();
    }

    @Override
    public User findByUsername(String username) {
        try {
            return this.entityManager
                    .createQuery("SELECT u FROM users u WHERE u.username = :username", User.class)
                    .setParameter("username", username)
                    .getSingleResult();
        } catch (Exception e){
            return null;
        }
    }
}
