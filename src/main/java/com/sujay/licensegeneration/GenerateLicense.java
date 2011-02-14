package com.sujay.licensegeneration;

import java.io.File;
import java.util.Calendar;
import java.util.Date;

import javax.security.auth.x500.X500Principal;

import de.schlichtherle.license.CipherParam;
import de.schlichtherle.license.KeyStoreParam;
import de.schlichtherle.license.LicenseContent;
import de.schlichtherle.license.LicenseManager;
import de.schlichtherle.license.LicenseParam;
import gnu.getopt.Getopt;
import gnu.getopt.LongOpt;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author sujay
 */
public class GenerateLicense {
    final static String SUBJECT = "License Generation";
    static Map<String, String> options = new HashMap<String, String>(0);
    public static void main(String[] args) throws Exception {
        parseOptions("license-gen", args);
        if(options.isEmpty()){
           printHelp();
           System.exit(0);
        }
        if(options.size() < 5){
           printHelp();
           System.exit(0);
        }

        String keyStore = options.get("keystore");
        String alias = options.get("alias");
        String storepass = options.get("storepass");
        String keypass = options.get("keypass");

        final KeyStoreParam privateKeyStoreParam = new KeyStoreParamImpl(keyStore, alias, storepass, keypass);

        final CipherParam cipherParam = new CipherParamImpl(keypass);

        LicenseParam licenseParam = new LicenseParamImpl(SUBJECT, privateKeyStoreParam, cipherParam);

        System.out.print("Creating new license ...");
        LicenseManager lm = new LicenseManager(licenseParam);
        String fileName = "license.lic";
        lm.store(createLicenseContent(licenseParam), new File(fileName));
        System.out.println("done");

    }

    /**
     * Create license content
     *
     * @param licenseParam
     * @return
     */
    static LicenseContent createLicenseContent(LicenseParam licenseParam) {
        LicenseContent result = new LicenseContent();
        X500Principal holder = new X500Principal("CN=Sujay Bhowmick");
        result.setHolder(holder);
        X500Principal issuer = new X500Principal("CN=sujay");
        result.setIssuer(issuer);
        result.setConsumerAmount(1);
        result.setConsumerType("User");
        result.setInfo("This Application demonstrates uses TrueLicense and Aspects for license management");
        Date now = new Date();
        result.setIssued(now);
        Calendar cal = Calendar.getInstance();
        cal.setTime(now);
        // set validity for days
        cal.add(Calendar.DATE, Integer.parseInt(options.get("validupto")));
        result.setNotAfter(cal.getTime());
        result.setSubject(licenseParam.getSubject());
        return result;
    }

    static void parseOptions(String cmd, String[] args) {
        int c;
        String arg;
        LongOpt[] longOpts = new LongOpt[6];
        longOpts[0] = new LongOpt("alias", LongOpt.REQUIRED_ARGUMENT, null, 'a');
        longOpts[1] = new LongOpt("storepass", LongOpt.REQUIRED_ARGUMENT, null, 's');
        longOpts[2] = new LongOpt("keypass", LongOpt.REQUIRED_ARGUMENT, null, 'k');
        longOpts[3] = new LongOpt("days", LongOpt.REQUIRED_ARGUMENT, null, 'd');
        longOpts[4] = new LongOpt("keystore", LongOpt.REQUIRED_ARGUMENT, null, 'l');
        longOpts[5] = new LongOpt("help", LongOpt.NO_ARGUMENT, null, 'h');
        Getopt g = new Getopt("LicenseGenerator", args, "a:s:k:d:l:h", longOpts);
        g.setOpterr(true);
        
        while ((c = g.getopt()) != -1) {
            switch (c) {              
                case 'a':
                    arg = g.getOptarg();
                    options.put("alias", arg);
                    break;
                //

                case 's':
                    arg = g.getOptarg();
                    options.put("storepass", arg);
                    break;
                //
                case 'k':
                    arg = g.getOptarg();
                    options.put("keypass", arg);
                    break;

               case 'd':
                    arg = g.getOptarg();
                    try{
                        Integer.parseInt(arg);
                    }catch(NumberFormatException e){
                        printHelp();
                       System.exit(1);
                    }
                    
                    options.put("validupto", arg);
                    break;

               case 'l':
                    arg = g.getOptarg();
                    options.put("keystore", arg);
                    break;

               case 'h':
                    printHelp();
                    System.exit(1);
                    break;
                //
                default:
                    printHelp();
                    System.exit(0);
                    break;
            }
        }
    }

    static void printHelp(){
        System.out.println("USEAGE:java com.sujay.licesnegeneration.GenerateLicesne [--alias=<alias>] [--storepass=<store password>] [--keypass=<keypassword>] [--days=<validity in days e.g. 2>] [--keystore=<key store file location>][--help]");
    }
}
