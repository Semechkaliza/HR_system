package by.bsu.hr.dao;

import by.bsu.hr.connection.ConnectionPool;
import by.bsu.hr.connection.ConnectionPoolException;
import by.bsu.hr.entity.Approved;
import by.bsu.hr.entity.User;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static by.bsu.hr.connection.ConnectionPool.closeSt;
import static by.bsu.hr.connection.ConnectionPool.returnConnectionToPool;

/**
 * All queries to table users
 */
public class UserDAO {
    private static final String FIND_USER_QUERY = "SELECT * FROM users WHERE login LIKE ? AND password=md5(?) and ACTIVE=1;";
    private static final String CHECK_USER_QUERY = "SELECT * FROM users WHERE login LIKE ? and ACTIVE=1;";
    private static final String ADD_USER_QUERY = "insert into users(login,password,name,sname,phone,email) values(?,md5(?),?,?,?,?);";
    private static final String UPDATE_INFO_QUERY = "UPDATE users SET NAME=?,SNAME=?,PHONE=?,EMAIL=? WHERE ID=?";
    private static final String DELETE_USER_QUERY = "UPDATE users set ACTIVE=0 WHERE ID=? and ACTIVE=1";
    private static final String GET_APPROVED_QUERY = "select users_id,vacancy_id,name,sname,phone,email,vacancy,company " +
            "from winners join vacancy join users " +
            "on winners.VACANCY_ID=vacancy.ID and winners.USERS_ID=users.ID " +
            "where winners.ACTIVE=1;";
    private static final String HANDLE_APPROVED_QUERY = "UPDATE winners set active=0 where USERS_ID=? and vacancy_id=?";
    private static final String ADD_APPROVED_QUERY = "insert into winners (USERS_ID, VACANCY_ID) VALUE (?,?)";
    private static final String ADD_ADMIN_QUERY = "UPDATE users set ROLE='ADMIN' where LOGIN=? and ACTIVE=1;";
    private static final String DELETE_INTERVIEWS_QUERY = "update interview set ACTIVE=0 where users_id=(select id from users where LOGIN=? and active=1);";
    private static final String DELETE_PROPOSALS_QUERY = "update interested_users set ACTIVE=0 where users_id=(select id from users where LOGIN=? and active=1);";
    private static Logger logger = Logger.getLogger(UserDAO.class);

    public static User findUser(String login, String password) throws DAOException {
        Connection cn = null;
        ResultSet rs = null;
        PreparedStatement st = null;
        User res = new User();
        try {
            cn = ConnectionPool.getInstance().takeConnection();
            st = cn.prepareStatement(FIND_USER_QUERY);
            st.setString(1, login);
            st.setString(2, password);
            rs = st.executeQuery();
            if (rs.next()) {
                res.setUserId(rs.getInt("id"));
                res.setLogin(rs.getString("login"));
                res.setPassword(rs.getString("password"));
                res.setName(rs.getString("name"));
                res.setSname(rs.getString("sname"));
                res.setRole(rs.getString("role"));
                res.setPhone(rs.getString("phone"));
                res.setEmail(rs.getString("email"));
                res.setActive(rs.getBoolean("active"));
            }
        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException("Finding user error", e);
        } finally {
            closeSt(st);
            ConnectionPool.returnConnectionToPool(cn);
        }
        return res;
    }

    public static boolean checkUser(String login) throws DAOException {
        Connection cn = null;
        ResultSet rs = null;
        PreparedStatement st = null;
        boolean check = true;
        try {
            cn = ConnectionPool.getInstance().takeConnection();
            st = cn.prepareStatement(CHECK_USER_QUERY);
            st.setString(1, login);
            rs = st.executeQuery();
            if (rs.next()) {
                check = false;
            }
        } catch (ConnectionPoolException | SQLException e) {
            throw new DAOException("Error check user", e);
        } finally {
            closeSt(st);
            returnConnectionToPool(cn);
        }
        return check;
    }

    public static void addUser(String login, String password, String name, String sname, String phone, String email) throws DAOException {
        Connection cn = null;
        PreparedStatement st = null;
        try {
            cn = ConnectionPool.getInstance().takeConnection();
            st = cn.prepareStatement(ADD_USER_QUERY);
            st.setString(1, login);
            st.setString(2, password);
            st.setString(3, name);
            st.setString(4, sname);
            st.setString(5, phone);
            st.setString(6, email);
            st.executeUpdate();
        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException("Error registration user", e);
        } finally {
            closeSt(st);
            returnConnectionToPool(cn);
        }
    }

    public static void updateInfo(String name, String sname, String phone, String email, int id) throws DAOException {
        Connection cn = null;
        PreparedStatement st = null;
        try {
            cn = ConnectionPool.getInstance().takeConnection();
            st = cn.prepareStatement(UPDATE_INFO_QUERY);
            st.setString(1, name);
            st.setString(2, sname);
            st.setString(3, phone);
            st.setString(4, email);
            st.setInt(5, id);
            st.executeUpdate();
        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException("Missing cancel proposal", e);
        } finally {
            closeSt(st);
            ConnectionPool.returnConnectionToPool(cn);
        }
    }


    public static void deleteUser(int userId) throws DAOException {
        Connection cn = null;
        PreparedStatement st = null;
        try {
            cn = ConnectionPool.getInstance().takeConnection();
            st = cn.prepareStatement(DELETE_USER_QUERY);
            st.setInt(1, userId);
            st.executeUpdate();
        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException("Error delete user", e);
        } finally {
            closeSt(st);
            ConnectionPool.returnConnectionToPool(cn);
        }
    }

    public static List<Approved> findApproved() throws DAOException {
        List<Approved> resList = new ArrayList<>();
        Connection cn = null;
        ResultSet rs = null;
        PreparedStatement st = null;
        try {
            cn = ConnectionPool.getInstance().takeConnection();
            st = cn.prepareStatement(GET_APPROVED_QUERY);
            rs = st.executeQuery();
            if (rs.next()) {
                do {
                    Approved res = new Approved();
                    res.setuserId(rs.getInt("users_id"));
                    res.setvacancyId(rs.getInt("vacancy_id"));
                    res.setName(rs.getString("name"));
                    res.setSname(rs.getString("sname"));
                    res.setPhone(rs.getString("phone"));
                    res.setEmail(rs.getString("email"));
                    res.setVacancy(rs.getString("vacancy"));
                    res.setCompany(rs.getString("company"));
                    resList.add(res);
                } while (rs.next());
            }
        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException("Finding approved error", e);
        } finally {
            closeSt(st);
            ConnectionPool.returnConnectionToPool(cn);
        }
        return resList;
    }

    public static void handleApproved(int userId, int vacancyId) throws DAOException {
        Connection cn = null;
        PreparedStatement statement = null;
        try {
            cn = ConnectionPool.getInstance().takeConnection();
            statement = cn.prepareStatement(HANDLE_APPROVED_QUERY);
            statement.setInt(1, userId);
            statement.setInt(2, vacancyId);
            statement.executeUpdate();
        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException("Error handle approved", e);
        } finally {
            closeSt(statement);
            ConnectionPool.returnConnectionToPool(cn);
        }
    }

    public static void addApproved(int userId, int vacancyId) throws DAOException {
        Connection cn = null;
        PreparedStatement st = null;
        try {
            cn = ConnectionPool.getInstance().takeConnection();
            st = cn.prepareStatement(ADD_APPROVED_QUERY);
            st.setInt(2, vacancyId);
            st.setInt(1, userId);
            st.executeUpdate();
        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException("Error add approved", e);
        } finally {
            closeSt(st);
            ConnectionPool.returnConnectionToPool(cn);
        }
    }

    public static void addAdmin(String login) throws DAOException {
        Connection cn = null;
        PreparedStatement st1 = null;
        PreparedStatement st2 = null;
        PreparedStatement st3 = null;
        try {
            cn = ConnectionPool.getInstance().takeConnection();
            st1 = cn.prepareStatement(ADD_ADMIN_QUERY);
            st2 = cn.prepareStatement(DELETE_INTERVIEWS_QUERY);
            st3 = cn.prepareStatement(DELETE_PROPOSALS_QUERY);
            st1.setString(1, login);
            st2.setString(1, login);
            st3.setString(1, login);
            st1.executeUpdate();
            st2.executeUpdate();
            st3.executeUpdate();
        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException("Error add admin", e);
        } finally {
            closeSt(st1);
            closeSt(st2);
            closeSt(st3);
            ConnectionPool.returnConnectionToPool(cn);
        }
    }
}
