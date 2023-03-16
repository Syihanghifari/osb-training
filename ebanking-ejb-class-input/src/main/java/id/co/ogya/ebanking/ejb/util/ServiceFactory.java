package id.co.ogya.ebanking.ejb.util;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;

public class ServiceFactory {

	public Object getService(String serviceJndiName) throws Exception {
		Object object = null;
		Hashtable<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put(Context.INITIAL_CONTEXT_FACTORY, "weblogic.jndi.WLInitialContextFactory");
		parameters.put(Context.PROVIDER_URL, "t3://localhost:7001");

		Context context = new InitialContext(parameters);
		object = context.lookup(serviceJndiName);
		return object;
	}
}
