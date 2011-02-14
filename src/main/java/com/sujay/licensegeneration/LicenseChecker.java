/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sujay.licensegeneration;

import de.schlichtherle.license.CipherParam;
import de.schlichtherle.license.KeyStoreParam;
import de.schlichtherle.license.LicenseContent;
import de.schlichtherle.license.LicenseManager;
import de.schlichtherle.license.LicenseParam;
import gnu.getopt.Getopt;
import gnu.getopt.LongOpt;
import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author sujay
 */
public class LicenseChecker {
    final static String SUBJECT = "License Generation";
    private static Map<String, String> options = new HashMap<String, String>(0);

    private void verifyLicense() {
        String keyStore = options.get("keystore");
        String alias = options.get("alias");
        String storepass = options.get("storepass");
        String keypass = options.get("keypass");
        boolean install = options.get("install") == null ? false : Boolean.parseBoolean(options.get("install"));
        try {
            KeyStoreParam keyStoreParam = new KeyStoreParamImpl(keyStore, alias, storepass, null);
            CipherParam cipherParam = new CipherParamImpl(keypass);
            LicenseParam licenseParam = new LicenseParamImpl(SUBJECT, keyStoreParam, cipherParam);
            LicenseManager lm = new LicenseManager(licenseParam);            
            
            if (install) {
                URL url = getClass().getClassLoader().getResource("license.lic");
                String file = url.getFile();
                System.out.println(file);
                lm.install(new File(file));
            }
            LicenseContent lc = lm.verify();
            System.out.println("LicenseChecker:: License valid till " + lc.getNotAfter());
        } catch (Exception e) {
            throw new RuntimeException("License error", e);
        }
    }

    public static void main(String[] args) {
        parseOptions("license-check", args);
        if (options.isEmpty()) {
            printHelp();
            System.exit(0);
        }
        if (options.size() < 4) {
            System.out.println("Printing Help:" + options.size());
            printHelp();
            System.exit(0);
        }
        LicenseChecker checker = new LicenseChecker();
        checker.verifyLicense();

    }

    static void parseOptions(String cmd, String[] args) {
        int c;
        String arg;
        LongOpt[] longOpts = new LongOpt[6];
        longOpts[0] = new LongOpt("alias", LongOpt.REQUIRED_ARGUMENT, null, 'a');
        longOpts[1] = new LongOpt("storepass", LongOpt.REQUIRED_ARGUMENT, null, 's');
        longOpts[2] = new LongOpt("keypass", LongOpt.REQUIRED_ARGUMENT, null, 'k');
        longOpts[3] = new LongOpt("keystore", LongOpt.REQUIRED_ARGUMENT, null, 'l');
        longOpts[4] = new LongOpt("install", LongOpt.OPTIONAL_ARGUMENT, null, 'i');
        longOpts[5] = new LongOpt("help", LongOpt.NO_ARGUMENT, null, 'h');


        Getopt g = new Getopt("LicenseChecker", args, "a:s:k:l:i:h", longOpts);
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

                case 'l':
                    arg = g.getOptarg();
                    options.put("keystore", arg);
                    break;

                case 'i':
                    arg = g.getOptarg();
                    options.put("install", arg);
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

    static void printHelp() {
        System.out.println("USEAGE:java com.sujay.LicenseChecker [--alias=<alias>] [--storepass=<store password>] [--keypass=<keypassword>] [--keystore=<key store file location>] [--install <true/false>] [--help]");
    }
}
