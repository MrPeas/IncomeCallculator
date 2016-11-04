package income.model;

import income.ConnectionManager;

import javax.persistence.*;
import javax.persistence.spi.PersistenceProvider;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Janusz on 01.11.2016.
 */


public class JobsDAO {

    private Connection con;
    public List<JobsEntity> getJobs() throws SQLException {
        con=ConnectionManager.getConnection();
        int x=1;
        try (
                Statement stmnt = con.createStatement();
                ResultSet rs = stmnt.executeQuery("select * from JOBS where JOBS.ID_USER=1")
        ) {
            List<JobsEntity> personList = new ArrayList<JobsEntity>() {
            };
            while (rs.next()) {
                String firstName = rs.getString("name");
                long id = rs.getLong("id");
                BigDecimal defaultIncome = rs.getBigDecimal("DEAFULT_INCOME");
                if (defaultIncome == null) {
                    defaultIncome = new BigDecimal(0);
                }
                String describe = rs.getString("describe");
                if (describe == null) {
                    describe = "";
                }
                JobsEntity person = new JobsEntity();
                person.setId(id);
                person.setName(firstName);
                person.setDeafultIncome(defaultIncome);
                person.setDescribe(describe);
                personList.add(person);
            }
            return personList;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            con.close();
        }
        return null;
    }
}
