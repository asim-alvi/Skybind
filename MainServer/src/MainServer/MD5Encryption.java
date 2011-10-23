/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MainServer;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author kevin
 */
public class MD5Encryption {

    private String MD5hash = null;

    public MD5Encryption(String s) {
        MessageDigest digest = null;
        try {
            digest = java.security.MessageDigest.getInstance("MD5");
            digest.update(s.getBytes());
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(MD5Encryption.class.getName()).log(Level.SEVERE, null, ex);
        }

        byte[] hash = digest.digest();
        String h = null;
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < hash.length; i++) {
            hexString.append(Integer.toHexString(0xFF & hash[i]));
        }
        MD5hash = hexString.toString();

    }

    public String getMD5() {
        if (MD5hash != null) {
            return MD5hash;
        }
    return "null";
    }
}
