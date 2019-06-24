package skeleton.dao;

import org.springframework.stereotype.Repository;

import skeleton.hibernate.Users;

@Repository
public class UserDao extends BaseDao<Users> {

	public UserDao() {
		super(Users.class);
	}

	public Users getUser(String principal) {
		return sessionFactory.getCurrentSession().createQuery("From Users where user_id =: user", Users.class)
				.setParameter("user", principal).uniqueResult();
	}

}
