import IntegrationTests.api_tests.db.Tariff;
import io.restassured.path.json.JsonPath;
import io.restassured.response.ResponseBody;
import org.sql2o.Sql2o;

import java.sql.*;
import java.util.List;
import java.util.Random;

import static io.restassured.RestAssured.given;

public class main {
    public static void main(String[] args) throws SQLException {
        //System.out.println(getUsers().getUsername());
//        JsonPath number;
//        number = given()
//                .get("http://localhost:8081/prepare_cdr")
//                .jsonPath();
//        System.out.println(number.getString("numbers[0].phoneNumber"));
//        clearSRMUsers();
//        clearBRTUsers();
//        insertSRMUsers();
//        System.out.println(generatePhoneNumber(12));
//        addBRTUserWitchRandomParams(12);
//        addBRTUserWitchRandomParams(12);
//        BRT();
//        try (org.sql2o.Connection con = BRT.beginTransaction()) {
//            con.createQuery("insert into client(id, balance, number, tariff_id) values (1, 2 ,3 ,4",false)
//                    .executeUpdate().getKey();
//            con.commit();
//        }





        }
    static private Sql2o SRMUsers;

    private static Sql2o BRT;
    static private Sql2o getSRMUsers() throws SQLException {
        if (SRMUsers == null) {
            SRMUsers = new Sql2o("jdbc:postgresql://localhost:5432/SRM-Users", "postgres", "1234");
        }
        return SRMUsers;

    }
    private static Sql2o BRT() throws SQLException {
        if (BRT == null) {
            BRT = new Sql2o("jdbc:postgresql://localhost:5432/BRT", "postgres", "1234");
        }
        return BRT;
    }

    static public Users getUsers() throws SQLException{
        getSRMUsers();
        Users users;
        try (org.sql2o.Connection connection = SRMUsers.beginTransaction()){
            users = connection.createQuery("select * from public.user")
                    .executeAndFetch(Users.class).get(0);
        }
        return users;
    }

    public static void clearSRMUsers() throws SQLException{
        getSRMUsers();
        try (org.sql2o.Connection connection = SRMUsers.beginTransaction()){
            System.out.println(connection.createQuery("delete from public.user").executeUpdate().getResult());
        }
    }
    public static void insertSRMUsers() throws SQLException{
        getSRMUsers();
        try (org.sql2o.Connection connection = SRMUsers.beginTransaction()){
            System.out.println(connection.createQuery(
                    "insert into public.user(username, password, role) " +
                            "values (:usernameParam, :passwordParam, :roleParam)")
                            .addParameter("usernameParam","13424")
                            .addParameter("passwordParam","13424")
                            .addParameter("roleParam","13424")
                    .executeUpdate().getResult());
            connection.commit();
        }
    }

    public static void clearBRTUsers() throws SQLException{
//        BRT();
        BRT = new Sql2o("jdbc:postgresql://localhost:5432/BRT", "postgres", "1234");
        try (org.sql2o.Connection connection = BRT.beginTransaction()){
            System.out.println(connection.createQuery("delete from client").executeUpdate());
            connection.commit();
        }
    }
    static public List<Tariff>  getTarifs() throws SQLException{
        BRT();
        List<Tariff> tarifIDs = null;
        try (org.sql2o.Connection connection = BRT.beginTransaction()){
            tarifIDs  = connection.createQuery("select id from tariff").executeAndFetch(Tariff.class);
            connection.commit();
        }
        System.out.println(tarifIDs.get((int) (Math.random() * tarifIDs.size())).getId());

        return tarifIDs;
    }

    private static String generatePhoneNumber(int numberLength) {

        long m = (long) Math.pow(10, numberLength - 1);
        return String.valueOf(new Random().nextLong(m,10 * m));
    }

    public static void addBRTUserWitchRandomParams(int numberLength, int userId) throws SQLException {
        BRT();

        List<Tariff> tarifIDs = null;
        try (org.sql2o.Connection connection = BRT.beginTransaction()){
            tarifIDs  = connection.createQuery("select id from tariff").executeAndFetch(Tariff.class);
            connection.commit();
        }

        double randomBalance = (Math.random() * (1000 + 1000) - 1000);

        try (org.sql2o.Connection connection = BRT.beginTransaction()) {
            connection.createQuery("insert into client values" +
                            "(:balanceParam, :numberParam, :tariffIdParam)")
                    .addParameter("idParam", userId)
                    .addParameter("balanceParam", randomBalance)
                    .addParameter("numberParam", generatePhoneNumber(numberLength))
                    .addParameter("tariffIdParam", tarifIDs.get((int) (Math.random() * tarifIDs.size())).getId())
                    .executeUpdate();
            connection.commit();
        }

    }

}
