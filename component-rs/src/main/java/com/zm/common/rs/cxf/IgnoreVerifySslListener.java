package com.zm.common.rs.cxf;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.cxf.configuration.jsse.TLSClientParameters;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.endpoint.ClientLifeCycleListener;
import org.apache.cxf.transport.http.HTTPConduit;

/**
 * 忽略SSL Https证书检查监听器。 <br>
 * 
 */
public class IgnoreVerifySslListener implements ClientLifeCycleListener {
	@Override
	public void clientCreated(Client client) {
		if (client.getConduit() instanceof HTTPConduit) {
			HTTPConduit conduit = (HTTPConduit) client.getConduit();

			TLSClientParameters params = conduit.getTlsClientParameters();

			if (params == null) {
				params = new TLSClientParameters();
				conduit.setTlsClientParameters(params);
			}

			params.setTrustManagers(new TrustManager[] { new TrustAllManager() });

			params.setDisableCNCheck(true);
		}
	}

	@Override
	public void clientDestroyed(Client client) {
		// Do Nothing
	}

	private class TrustAllManager implements X509TrustManager {

		@Override
		public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
			// Do Nothing
		}

		@Override
		public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
			// Do Nothing
		}

		@Override
		public X509Certificate[] getAcceptedIssuers() {
			return null;
		}

	}

}
