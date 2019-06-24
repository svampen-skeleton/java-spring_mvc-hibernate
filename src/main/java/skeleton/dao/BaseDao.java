package skeleton.dao;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.List;
import java.util.function.BiConsumer;

import org.hibernate.ObjectNotFoundException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import skeleton.hibernate.Persistable;

public abstract class BaseDao<T extends Persistable> {
	private final Class<T> clazz;
	@Autowired
	protected SessionFactory sessionFactory;
	private final int BUFFERSIZE = 20;
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	protected SimpleDateFormat dbformat = new SimpleDateFormat("yyyyMMdd");

	public BaseDao(Class<T> clazz) {
		this.clazz = clazz;
	}

	public T save(T t) {
		return t.isNew() ? persist(t) : merge(t);
	}

	public void save(Collection<T> t) {
		batchOperation(t, (item, session) -> {
			if (item.isNew())
				session.save(item);
			else
				session.merge(item);
		});
	}

	public void delete(Collection<T> t) {
		batchOperation(t, (item, session) -> {
			session.delete(item);
		});
	}

	public List<T> findAll() {
		return (List<T>) sessionFactory.getCurrentSession().createQuery("from " + clazz.getSimpleName(), clazz).list();
	}

	public T get(Long id) {
		return (T) sessionFactory.getCurrentSession().get(clazz, id);
	}

	private void batchOperation(Collection<T> collection, BiConsumer<T, Session> operation) {
		int i = 0;
		Session session = sessionFactory.getCurrentSession();
		for (T item : collection) {
			i++;
			operation.accept(item, session);
			if (i % BUFFERSIZE == 0) {
				session.flush();
				session.clear();
			}

		}
		if (i % BUFFERSIZE > 0) {
			session.flush();
			session.clear();
		}
	}

	public void delete(T t) {
		Session session = sessionFactory.getCurrentSession();
		if (session.contains(t)) {
			try {
				session.delete(t);
				session.flush();
			} catch (ObjectNotFoundException e) {
				logger.warn("can't find {} with id {} to delete, ignoring ...", this.clazz.getSimpleName(), t.getId());
			}
		} else if (t == null || t.getId() == null || get(t.getId()) == null) {
			Object idToLog = t == null ? "null" : t.getId();
			logger.warn("can't find object to delete , type {} : id {}", this.getClass().getSimpleName(), idToLog);
		} else {
			session.delete(get(t.getId()));
			session.flush();
		}
	}

	public void delete(Long id) {
		delete(get(id));
	}

	private T persist(T t) {
		sessionFactory.getCurrentSession().save(t);
		sessionFactory.getCurrentSession().flush();
		return t;
	}

	public T merge(T t) {
		sessionFactory.getCurrentSession().merge(t);
		sessionFactory.getCurrentSession().flush();
		return t;
	}

	public T detach(T t) {
		sessionFactory.getCurrentSession().detach(t);
		sessionFactory.getCurrentSession().flush();
		return t;
	}
}
