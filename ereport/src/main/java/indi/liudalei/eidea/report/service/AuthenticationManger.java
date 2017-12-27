package indi.liudalei.eidea.report.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

/**
 * Created by admin on 2016/9/7.
 */
@Slf4j
@Repository
public class AuthenticationManger {

    public boolean verifyUser(String token) {
        return true;
    }

}
