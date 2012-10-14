package net.pascalbrandt.dsm;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;



public class HibernateSessionFactoryUtil {



  /** The single instance of hibernate SessionFactory */

  private static org.hibernate.SessionFactory sessionFactory;

  private static Session session;

	/**

	 * disable contructor to guaranty a single instance

	 */

	private HibernateSessionFactoryUtil() {

	}



	static{

    sessionFactory = new Configuration().configure().buildSessionFactory();

  }



	public static SessionFactory getInstance() {

		return sessionFactory;

	}



  /**

   * Opens a session and will not bind it to a session context

   * @return the session

   */

	public Session openSession() {

		return sessionFactory.openSession();

	}



	/**

   * Returns a session from the session context. If there is no session in the context it opens a session,

   * stores it in the context and returns it.

	 * This factory is intended to be used with a hibernate.cfg.xml

	 * including the following property &lt;property

	 * name="current_session_context_class"&gt;thread&lt;/property&gt; This would return

	 * the current open session or if this does not exist, will create a new

	 * session

	 * 

	 * @return the session

	 */

	public static Session getCurrentSession() {

		if(session == null)
			session = getInstance().openSession();
		return session;
	}



  /**

   * closes the session factory

   */

	public static void close(){

		if (sessionFactory != null)

			sessionFactory.close();

		sessionFactory = null;



	}

}