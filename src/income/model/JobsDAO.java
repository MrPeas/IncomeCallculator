package income.model;

import income.ConnectionManager;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Janusz on 01.11.2016.
 */


public class JobsDAO {

    private Connection con;

    public List<JobsEntity> getJobs() {
        con = ConnectionManager.getConnection();
        Statement stmnt = null;
        try {
            stmnt = con.createStatement();
            ResultSet rs = stmnt.executeQuery("select * from JOBS where JOBS.IDUSER=1");
            List<JobsEntity> personList = new ArrayList();
            while (rs.next()) {
                String firstName = rs.getString("name");
                long id = rs.getLong("id");
                BigDecimal defaultIncome = rs.getBigDecimal("DEAFULTINCOME");
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
                person.setDeafultincome(defaultIncome);
                person.setDescribe(describe);
                personList.add(person);
            }
            return personList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    //TODO add alert when add failed
    public boolean insertJob(JobsEntity job){
        String query="INSERT INTO JOBS(NAME, DESCRIBE, DEAFULT_INCOME, ID_USER) " +
                "VALUES ('"+job.getName()+"','"+job.getDescribe()+"',"+job.getDeafultincome()+","+job.getIdUser()+")";
        return updateJobQuery(query,job);
    }
    public boolean editJob(JobsEntity job){
       String query="UPDATE JOBS "+"SET " +
               "NAME='"+job.getName()+"', DESCRIBE='"+job.getDescribe()+"',DEAFULT_INCOME="+job.getDeafultincome()+" WHERE JOBS.ID="+job.getId();
        return updateJobQuery(query,job);
    }
    private boolean updateJobQuery(String query,JobsEntity job){
        con = ConnectionManager.getConnection();
        Statement stmnt;
        try{
            stmnt = con.createStatement();
            stmnt.executeUpdate(query);
        }catch (SQLException e){
            System.err.print( e.getSQLState());
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
