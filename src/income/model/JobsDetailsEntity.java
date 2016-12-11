package income.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;

/**
 * Created by Janusz on 01.11.2016.
 */
@Entity
@Table(name = "JOBS_DETAILS", schema = "PUBLIC", catalog = "INCOME")
public class JobsDetailsEntity {
    private long id;
    private Date wokrDate;
    private BigDecimal income;
    private Double hours;
    private long idJob;
    private JobsEntity jobsByIdJob;

    @Id
    @Column(name = "ID", nullable = false)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "WOKR_DATE", nullable = false)
    public Date getWokrDate() {
        return wokrDate;
    }

    public void setWokrDate(Date wokrDate) {
        this.wokrDate = wokrDate;
    }

    @Basic
    @Column(name = "INCOME", nullable = false, precision = 2)
    public BigDecimal getIncome() {
        return income;
    }

    public void setIncome(BigDecimal income) {
        this.income = income;
    }

    @Basic
    @Column(name = "HOURS", nullable = true, precision = 0)
    public Double getHours() {
        return hours;
    }

    public void setHours(Double hours) {
        this.hours = hours;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        JobsDetailsEntity that = (JobsDetailsEntity) o;

        if (id != that.id) return false;
        if (wokrDate != null ? !wokrDate.equals(that.wokrDate) : that.wokrDate != null) return false;
        if (income != null ? !income.equals(that.income) : that.income != null) return false;
        if (hours != null ? !hours.equals(that.hours) : that.hours != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (wokrDate != null ? wokrDate.hashCode() : 0);
        result = 31 * result + (income != null ? income.hashCode() : 0);
        result = 31 * result + (hours != null ? hours.hashCode() : 0);
        return result;
    }

    @Basic
    @Column(name = "ID_JOB", nullable = false)
    public long getIdJob() {
        return idJob;
    }

    public void setIdJob(long idJob) {
        this.idJob = idJob;
    }

    @ManyToOne
    @PrimaryKeyJoinColumn(name = "ID_JOB", referencedColumnName = "ID")
    @JoinColumn(name = "ID_JOB", referencedColumnName = "ID", nullable = false)
    public JobsEntity getJobsByIdJob() {
        return jobsByIdJob;
    }

    public void setJobsByIdJob(JobsEntity jobsByIdJob) {
        this.jobsByIdJob = jobsByIdJob;
    }
}
