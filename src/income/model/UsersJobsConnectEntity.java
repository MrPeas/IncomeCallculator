package income.model;

import javax.persistence.*;

/**
 * Created by Janusz on 01.11.2016.
 */
@Entity
@Table(name = "USERS_JOBS_CONNECT", schema = "PUBLIC", catalog = "INCOME")
@IdClass(UsersJobsConnectEntityPK.class)
public class UsersJobsConnectEntity {
    private long idJob;
    private long idUser;
    private JobsEntity jobsByIdJob;
    private UsersEntity usersByIdUser;

    @Id
    @Column(name = "ID_JOB", nullable = false)
    public long getIdJob() {
        return idJob;
    }

    public void setIdJob(long idJob) {
        this.idJob = idJob;
    }

    @Id
    @Column(name = "ID_USER", nullable = false)
    public long getIdUser() {
        return idUser;
    }

    public void setIdUser(long idUser) {
        this.idUser = idUser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UsersJobsConnectEntity that = (UsersJobsConnectEntity) o;

        if (idJob != that.idJob) return false;
        if (idUser != that.idUser) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (idJob ^ (idJob >>> 32));
        result = 31 * result + (int) (idUser ^ (idUser >>> 32));
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "ID_JOB", referencedColumnName = "ID", nullable = false)
    public JobsEntity getJobsByIdJob() {
        return jobsByIdJob;
    }

    public void setJobsByIdJob(JobsEntity jobsByIdJob) {
        this.jobsByIdJob = jobsByIdJob;
    }

    @ManyToOne
    @JoinColumn(name = "ID_USER", referencedColumnName = "ID", nullable = false)
    public UsersEntity getUsersByIdUser() {
        return usersByIdUser;
    }

    public void setUsersByIdUser(UsersEntity usersByIdUser) {
        this.usersByIdUser = usersByIdUser;
    }
}
