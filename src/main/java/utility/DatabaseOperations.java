package utility;

import java.sql.*;

public class DatabaseOperations {

    public static String getPassword(String email) {

        String password = "";

        try {

            Connection con = DatabaseUtil.getConnection();

            PreparedStatement ps =
                    con.prepareStatement(
                    "SELECT password FROM users WHERE email=?");

            ps.setString(1, email);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                password = rs.getString("password");

            }

        } catch (Exception e) {

            e.printStackTrace();

        }

        return password;
    }

}