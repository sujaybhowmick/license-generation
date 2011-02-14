package com.sujay.licensegeneration;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.prefs.Preferences;

import de.schlichtherle.license.CipherParam;
import de.schlichtherle.license.KeyStoreParam;
import de.schlichtherle.license.LicenseParam;

/**
 * License utility.
 *
 * @author Manohar Viswanathan
 */
public class LicenseUtil {

         final static String SUBJECT = "License Generation for EagleIntellignece Application";
	 // license param
	 public final static LicenseParam licenceParam = new LicenseParam() {
	      public String getSubject() {
	    	  // This MUST be the same as subject of the LicenseContent
	          return SUBJECT;
	      }

	      public Preferences getPreferences() {
	          return Preferences.systemNodeForPackage(LicenseUtil.class);
	      }

	      public KeyStoreParam getKeyStoreParam() {
	          return new KeyStoreParam() {
	              public InputStream getStream() throws IOException {
	                  final String resourceName = "publicCerts.store";
	                  final InputStream in =Thread.currentThread().getContextClassLoader().getResourceAsStream(resourceName);
	                  if (in == null) {
	                      throw new FileNotFoundException(resourceName);
	                  }
	                  return in;
	              }

	              public String getAlias() {
	                  return "publiccert";
	              }

	              public String getStorePwd() {
	                  return "manohar1";
	              }

	              public String getKeyPwd() {
	                  // These parameters are not used to create any licenses.
	                  // Therefore there should never be a private key in the
	                  // keystore
	                  // entry. To enforce this policy, we return null here.
	                  return null; // causes failure if private key is found in
	                  // this entry
	              }
	          };
	      }

	      public CipherParam getCipherParam() {
	          return new CipherParam() {
	              public String getKeyPwd() {
	                  return "manohar1";
	              }
	          };
	      }
	  };

}
