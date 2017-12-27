package indi.liudalei.eidea.api.shiro;

import com.google.common.collect.HashMultimap;
import org.apache.shiro.crypto.hash.Sha256Hash;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by 刘大磊 on 2017/4/13 9:46.
 */
public class Safe {
    static Map<String, String> passwords = new HashMap<>();
    static HashMultimap<String, String> roles = HashMultimap.create();
    static{
        //encrypt("green");
        //encrypt("blue")
        passwords.put("pierre", "green");
        passwords.put("paul","blue" );
        roles.put("paul", "vip");
    }
    private static String encrypt(String password) {
        return new Sha256Hash(password).toString();
    }
    public static String getPassword(String username) {
        return passwords.get(username);
    }
    public static Set<String> getRoles(String username) {
        return roles.get(username);
    }
}
