package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    private Connection bdConnection;
    private String SQL;
    private Statement getStatement;
    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
        SQL = "create table IF NOT EXISTS user(\n" +
                "id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,\n" +
                "    name varchar(30),\n" +
                "    lastName varchar(30),\n" +
                "    age TINYINT(2) unsigned\n" +
                ");";
        Savepoint savepoint = null;
        try {
            bdConnection = Util.getConnection();
            bdConnection.setAutoCommit(false);
            savepoint = bdConnection.setSavepoint("SavepointOne");
            getStatement = bdConnection.createStatement();
            getStatement.executeUpdate(SQL);
            bdConnection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                bdConnection.rollback(savepoint);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }finally {
            if (bdConnection != null) {
                try {
                    bdConnection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void dropUsersTable() {
        SQL = "DROP TABLE IF EXISTS user;";
        Savepoint savepoint = null;
        try {
            bdConnection = Util.getConnection();
            bdConnection.setAutoCommit(false);
            savepoint = bdConnection.setSavepoint("SavepointOne");
            getStatement = bdConnection.createStatement();
            getStatement.executeUpdate(SQL);
            bdConnection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                bdConnection.rollback(savepoint);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }finally {
            if (bdConnection != null) {
                try {
                    bdConnection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        Savepoint savepoint = null;
        try {
            bdConnection = Util.getConnection();
            bdConnection.setAutoCommit(false);
            savepoint = bdConnection.setSavepoint("SavepointOne");
            PreparedStatement preparedStatement =
                    bdConnection.prepareStatement("INSERT INTO user (name, lastName, age) VALUES(?, ?, ?)");
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
            bdConnection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                bdConnection.rollback(savepoint);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }finally {
            if (bdConnection != null) {
                try {
                    bdConnection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public void removeUserById(long id) {
        Savepoint savepoint = null;
        try {
            bdConnection = Util.getConnection();
            bdConnection.setAutoCommit(false);
            savepoint = bdConnection.setSavepoint("SavepointOne");
            PreparedStatement preparedStatement =
                    bdConnection.prepareStatement("DELETE FROM user WHERE id=?");
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            bdConnection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                bdConnection.rollback(savepoint);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }finally {
            if (bdConnection != null) {
                try {
                    bdConnection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        SQL = "SELECT * FROM user";
        ResultSet resultSet;
        Savepoint savepoint = null;
        try {
            bdConnection = Util.getConnection();
            bdConnection.setAutoCommit(false);
            savepoint = bdConnection.setSavepoint("SavepointOne");
            getStatement = bdConnection.createStatement();
            resultSet = getStatement.executeQuery(SQL);
            bdConnection.commit();
            while (true) {
                User user = new User();
                try {
                    assert resultSet != null;
                    if (!resultSet.next()) break;
                    user.setId(resultSet.getLong("id"));
                    user.setName(resultSet.getString("name"));
                    user.setLastName(resultSet.getString("lastName"));
                    user.setAge(resultSet.getByte("age"));
                    users.add(user);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                bdConnection.rollback(savepoint);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }finally {
            if (bdConnection != null) {
                try {
                    bdConnection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return users;
    }

    public void cleanUsersTable() {
        Savepoint savepoint = null;
        try {
            bdConnection = Util.getConnection();
            bdConnection.setAutoCommit(false);
            savepoint = bdConnection.setSavepoint("SavepointOne");
            PreparedStatement preparedStatement =
                    bdConnection.prepareStatement("DELETE FROM user");
            preparedStatement.executeUpdate();
            bdConnection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                bdConnection.rollback(savepoint);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }finally {
            if (bdConnection != null) {
                try {
                    bdConnection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
