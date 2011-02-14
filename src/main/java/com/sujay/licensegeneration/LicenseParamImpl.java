/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sujay.licensegeneration;

import de.schlichtherle.license.CipherParam;
import de.schlichtherle.license.KeyStoreParam;
import de.schlichtherle.license.LicenseParam;
import java.util.prefs.Preferences;

/**
 *
 * @author sujay
 */
public class LicenseParamImpl implements LicenseParam{

    private String subject;

    private KeyStoreParam keyStoreParam;

    private CipherParam cipherParam;

    public LicenseParamImpl(final String subject, final KeyStoreParam keyStoreParam, final CipherParam cipherParam){
        this.subject = subject;
        this.keyStoreParam = keyStoreParam;
        this.cipherParam = cipherParam;
    }

    public String getSubject() {
        return this.subject;
    }

    public Preferences getPreferences() {
         return Preferences.userNodeForPackage(GenerateLicense.class);
    }

    public KeyStoreParam getKeyStoreParam() {
        return this.keyStoreParam;
    }

    public CipherParam getCipherParam() {
        return this.cipherParam;
    }

}
