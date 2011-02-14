/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sujay.licensegeneration;

import de.schlichtherle.license.CipherParam;

/**
 *
 * @author sujay
 */
public class CipherParamImpl implements CipherParam{
    private String keyPwd;
    
    public CipherParamImpl(final String keyPwd){
        this.keyPwd = keyPwd;
    }

    public String getKeyPwd() {
        return this.keyPwd;
    }

}
