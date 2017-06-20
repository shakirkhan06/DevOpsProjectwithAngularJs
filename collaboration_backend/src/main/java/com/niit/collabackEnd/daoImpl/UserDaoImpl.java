package com.niit.collabackEnd.daoImpl;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import com.niit.collabackEnd.dao.UserDao;
import com.niit.collabackEnd.model.User;
	


@EnableTransactionManagement
	@Repository
	public class UserDaoImpl implements UserDao{
		
		
		private static final Logger log=LoggerFactory.getLogger(UserDaoImpl.class);
		@Autowired(required=true)
		private SessionFactory sessionFactory;
		
		public UserDaoImpl(SessionFactory sessionFactory){
			try{
				this.sessionFactory =sessionFactory;
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		@Transactional
	//this method is used to return list of users from the database
		public List<User> list() {	
			@SuppressWarnings("unchecked")
			List<User> list = (List<User>) 
			          sessionFactory.getCurrentSession()
					.createCriteria(User.class)
					.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();

			return list;	
		}
		@Transactional
	//this method is used to bring a user detail by sending userid to the database
		public User getuser(String userid) {
			log.debug("CALLING METHOD GET USER BY USERID");
			String hql = "from UserDetails where userid= "+" '" +userid+ "'";
			Query query =sessionFactory.getCurrentSession().createQuery(hql);
			@SuppressWarnings("unchecked")
			List<User> list = query.list();
			if(list == null || list.isEmpty())
			{
				return null;
			}
			else
			{
				log.debug("RETURNING USER LIST BY USER ID");
				return list.get(0);
			}
			
		}
		
		
		//this method will save user details
		@Transactional
		public boolean save(User userdetails)
		{
		try {
			// Session session = sessionFactory.getCurrentSession();
		
			sessionFactory.getCurrentSession().save(userdetails);
			return true;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
		}

		
		@Transactional
	//this method is used to update userdetails
		public boolean update(User userdetails) {
				try
				{
				sessionFactory.getCurrentSession().update(userdetails);
				}catch (Exception e)
				{
					e.printStackTrace();
					return false;
				}
				return true;	
				}
		
	//this method is used to delete a single user by using userid
		@Transactional
		public boolean delete(String id)
		{
		try {
			
			
				User CategoryToDelete = new User();
				CategoryToDelete.setUserid(id);
				sessionFactory.getCurrentSession().delete(CategoryToDelete);
			
			return true;
		}
		catch(Exception e)
		{
			
			e.printStackTrace();
			return false;
		}
		}

	//this method will return a user based on the userid and password
		
		@Transactional
		public User authenticate(String username, String password) {
			log.debug("CALLING METHOD USER AUTHENTICATE");
	    String hql = "from UserDetails  where username= '" + username +"' and " + " password = '" + password+"'";
			
			Query query=  sessionFactory.getCurrentSession().createQuery(hql);
	       log.debug("RETURNING METHOD USER AUTHENTICATE");
			return  (User)query.uniqueResult();
				}
		//this method is used to set a user as online
		@Transactional
		public void setOnLine(String username)
		{
			String hql ="update UserDetails SET isonline='Y' where username= "+" '" +username+ "'";
			Query query =sessionFactory.getCurrentSession().createQuery(hql);
			query.executeUpdate();
		}

		  // this method is used to set user as offline
		@Transactional
		public void setOffLine(String username)
		{
			String hql ="update Userdetails SET isonline='N' where username= "+" '" +username+ "'";
			Query query =sessionFactory.getCurrentSession().createQuery(hql);
			query.executeUpdate();
			
		}


	}
