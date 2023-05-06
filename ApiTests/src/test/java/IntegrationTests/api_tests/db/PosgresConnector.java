package IntegrationTests.api_tests.db;

import IntegrationTests.api_tests.DataGenerator.DataGenerator;
import org.sql2o.Sql2o;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PosgresConnector {
    private Sql2o SRMUsers;

    private Sql2o BRT;
    private Sql2o SRMUsers() throws SQLException {
        if (SRMUsers == null) {
            SRMUsers = new Sql2o("jdbc:postgresql://localhost:5432/SRM-Users", "postgres", "1234");
        }
        return SRMUsers;
    }

    private Sql2o BRT() throws SQLException {
        if (BRT == null) {
            BRT = new Sql2o("jdbc:postgresql://localhost:5432/BRT", "postgres", "1234");
        }
        return BRT;
    }

    public Users getSRMUsers() throws SQLException{
        Sql2o SRMUsersDB = SRMUsers();
        Users users;
        try (org.sql2o.Connection connection = SRMUsersDB.beginTransaction()){
            users = connection.createQuery("select * from public.user")
                    .executeAndFetch(Users.class).get(0);
        }
        return users;
    }

    public void clearSRMUsers() throws SQLException{
        Sql2o SRMUsersDB = SRMUsers();
        try (org.sql2o.Connection connection = SRMUsersDB.beginTransaction()){
            connection.createQuery("delete from public.user")
                    .executeUpdate();
            connection.commit();
        }
    }

    public void clearBRTUsers() throws SQLException{
        BRT();
        try (org.sql2o.Connection connection = BRT.beginTransaction()){
            connection.createQuery("delete from client").executeUpdate();
            connection.commit();
        }
    }

    public List<String> getTarifs() throws SQLException{
        BRT();
        List<String> tarifIDs = null;
        try (org.sql2o.Connection connection = BRT.beginTransaction()){
            connection.createQuery("select id from tariff").executeAndFetchTable()
                    .rows()
                    .stream()
                    .forEach(row -> tarifIDs.add(row.getString("id")));
            connection.commit();
        }

        return tarifIDs;
    }

    public String addBRTUserWitchRandomParams(int numberLength, int userId) throws SQLException {
        BRT();

        List<Tariff> tarifIDs = null;
        try (org.sql2o.Connection connection = BRT.beginTransaction()){
            tarifIDs  = connection.createQuery("select id from tariff").executeAndFetch(Tariff.class);
            connection.commit();
        }

        double randomBalance = (Math.random() * (1000 + 1000) - 1000);
        String phoneNumber = new DataGenerator().generatePhoneNumber(numberLength);
        try (org.sql2o.Connection connection = BRT.beginTransaction()) {
            connection.createQuery("insert into client values" +
                            "(:idParam,:balanceParam, :numberParam, :tariffIdParam)")
                    .addParameter("idParam", userId)
                    .addParameter("balanceParam", randomBalance)
                    .addParameter("numberParam", phoneNumber)
                    .addParameter("tariffIdParam", tarifIDs.get((int) (Math.random() * tarifIDs.size())).getId())
                    .executeUpdate();
            connection.commit();
        }
        return phoneNumber;
    }

    public void clearAllTables() throws SQLException {
        clearSRMUsers();
        clearBRTUsers();
    }



}
