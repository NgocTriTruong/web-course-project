package vn.edu.hcmuaf.fit.animalfeed_webapp.services;

import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.ActionLogDao;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.ActionLog;

import java.util.List;

public class ActionLogService {
    ActionLogDao actionLogDao = new ActionLogDao();
    public void logAction(ActionLog actionLog){
        actionLogDao.logAction(actionLog);
    }

    public List<ActionLog> getAllActionLogs() {
        return actionLogDao.getAllActionLogs();
    }
}
