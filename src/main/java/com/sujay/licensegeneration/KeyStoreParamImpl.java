/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sujay.licensegeneration;

import de.schlichtherle.license.KeyStoreParam;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 *
 * @author sujay
 */
public class KeyStoreParamImpl implements KeyStoreParam {

    private String keyStore;
    private String alias;
    private String storePwd;
    private String keyPwd;

    public KeyStoreParamImpl(String keyStore, String alias, String storePwd, String keyPwd) {
        this.keyStore = keyStore;
        this.alias = alias;
        this.storePwd = storePwd;
        this.keyPwd = keyPwd;
    }

    public KeyStoreParamImpl(String alias, String storePwd, String keyPwd) {
        this.alias = alias;
        this.storePwd = storePwd;
        this.keyPwd = keyPwd;
    }

    public InputStream getStream() throws IOException {
        
        final String resourceName = this.keyStore == null ? "privateKeyStore" : this.keyStore;
        final InputStream in = getClass().getClassLoader().getResourceAsStream(resourceName);
        if (in == null) {
            throw new FileNotFoundException(resourceName);
        }
        return in;
    }

    public String getAlias() {
        return this.alias;
    }

    public String getStorePwd() {
        return this.storePwd;
    }

    public String getKeyPwd() {
        return this.keyPwd;
    }

    /**
     * @param alias the alias to set
     */
    public void setAlias(String alias) {
        this.alias = alias;
    }

    /**
     * @param storePwd the storePwd to set
     */
    public void setStorePwd(String storePwd) {
        this.storePwd = storePwd;
    }

    /**
     * @param keyPwd the keyPwd to set
     */
    public void setKeyPwd(String keyPwd) {
        this.keyPwd = keyPwd;
    }
}
