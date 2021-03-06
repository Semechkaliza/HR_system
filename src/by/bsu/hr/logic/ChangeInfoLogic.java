package by.bsu.hr.logic;

import by.bsu.hr.dao.DAOException;
import by.bsu.hr.dao.UserDAO;

/**
 * Logic to change info command
 */
public class ChangeInfoLogic {
    /**
     * @param name
     * @param sname
     * @param phone
     * @param email
     * @param id
     * @throws LogicException
     */
    public static void updateInfo(String name, String sname, String phone, String email, int id) throws LogicException {
        try {
            UserDAO.updateInfo(name, sname, phone, email, id);
        } catch (DAOException e) {
            throw new LogicException("Error update info", e);
        }
    }
}
