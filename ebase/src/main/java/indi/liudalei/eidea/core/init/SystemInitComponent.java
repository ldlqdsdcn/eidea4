package indi.liudalei.eidea.core.init;

import indi.liudalei.eidea.core.dao.util.ChangelogHelper;
import indi.liudalei.eidea.core.entity.bo.TableBo;
import indi.liudalei.eidea.core.service.TableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by 刘大磊 on 2016/12/23 8:49.
 */
@Component("systemInitComponent")
public class SystemInitComponent implements ApplicationListener<ContextRefreshedEvent> {
    @Autowired
    private TableService tableService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        List<TableBo> tableBoList = tableService.getTableBoListForOutLog();
        ChangelogHelper.init(tableBoList);
    }
}
