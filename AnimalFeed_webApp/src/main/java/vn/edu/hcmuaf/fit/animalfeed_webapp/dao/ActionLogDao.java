package vn.edu.hcmuaf.fit.animalfeed_webapp.dao;

import org.jdbi.v3.core.Jdbi;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.db.JdbiConnect;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.ActionLog;

import java.util.List;

public class ActionLogDao {
    private static Jdbi jdbi = JdbiConnect.getJdbi();

    // Method to insert a new action log
    public static void logAction(ActionLog actionLog) {
        jdbi.useHandle(handle -> {
            handle.createUpdate("INSERT INTO action_log (user_id, action_type, entity_type, " +
                            "entity_id, created_at, description, before_data, after_data) " +
                            "VALUES (:user_id, :action_type, :entity_type, :entity_id, " +
                            ":created_at, :description, :before_data, :after_data)")
                    .bindBean(actionLog)
                    .execute();
        });
    }

    // Method to get all action logs
    public List<ActionLog> getAllActionLogs() {
        return jdbi.withHandle(handle ->
                handle.createQuery("SELECT * FROM action_log")
                        .mapToBean(ActionLog.class)
                        .list()
        );
    }

    public static void main(String[] args) {
        ActionLogDao actionLogDao = new ActionLogDao();
        List<ActionLog> actionLogs = actionLogDao.getAllActionLogs();
        for (ActionLog actionLog : actionLogs) {
            System.out.println(actionLog);
        }
    }

}
