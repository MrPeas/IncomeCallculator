package income.model;

import income.ConnectionManager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Janusz on 02.11.2016.
 */
public class JobsDetailsDao {

    private Connection con;
    private JobsEntity jobsEntity;

    public List<JobsDetailsEntity> findJobsDetail(JobsEntity job) {
        this.jobsEntity = job;
        List<JobsDetailsEntity> jobsDetails;
        con = ConnectionManager.getConnection();
        try {
            Statement stmnt = con.createStatement();
            ResultSet rs = stmnt.executeQuery("SELECT * FROM JOBS_DETAILS WHERE JOBS_DETAILS.ID_JOB=" + jobsEntity.getId());
            jobsDetails = getResult(rs);
            stmnt.close();
            con.close();
            return jobsDetails;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<JobsDetailsEntity> findJobsDetailsByYear(JobsEntity job, int year) {
        this.jobsEntity = job;
        List<JobsDetailsEntity> jobsDetails;
        con = ConnectionManager.getConnection();
        try {
            Statement stmnt = con.createStatement();
            ResultSet rs = stmnt.executeQuery("SELECT * FROM JOBS_DETAILS WHERE " +
                    "JOBS_DETAILS.ID_JOB=" + jobsEntity.getId() + " AND YEAR(JOBS_DETAILS.WOKR_DATE)=" + year);
            jobsDetails = getResult(rs);
            stmnt.close();
            con.close();
            return jobsDetails;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
        }
        return null;
    }

    public List<JobsDetailsEntity> findJobsDetailsByMonth(JobsEntity job, int month) {
        this.jobsEntity = job;
        List<JobsDetailsEntity> jobsDetails;
        con = ConnectionManager.getConnection();
        try {
            Statement stmnt = con.createStatement();
            ResultSet rs = stmnt.executeQuery("SELECT * FROM JOBS_DETAILS WHERE " +
                    "JOBS_DETAILS.ID_JOB=" + jobsEntity.getId() + " AND MONTH(JOBS_DETAILS.WOKR_DATE)=" + month);
            jobsDetails = getResult(rs);
            stmnt.close();
            con.close();
            return jobsDetails;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
        }
        return null;
    }

    private List<JobsDetailsEntity> getResult(ResultSet rs) throws SQLException {
        List<JobsDetailsEntity> jobsDetails = new ArrayList<>();
        while (rs.next()) {
            JobsDetailsEntity temp = new JobsDetailsEntity();
            temp.setId(rs.getLong("id"));
            temp.setHours(rs.getDouble("hours"));
            temp.setIdJob(rs.getLong("id_job"));
            temp.setIncome(rs.getBigDecimal("Income"));
            temp.setWokrDate(rs.getDate("Wokr_Date"));
            jobsDetails.add(temp);
        }
        return jobsDetails;

    }
}
