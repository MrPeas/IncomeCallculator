package income.model;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by Janusz on 01.11.2016.
 */
public class UsersJobsConnectEntityPK implements Serializable {
    private long idJob;
    private long idUser;

    @Column(name = "ID_JOB", nullable = false)
    @Id
    public long getIdJob() {
        return idJob;
    }

    public void setIdJob(long idJob) {
        this.idJob = idJob;
    }

    @Column(name = "ID_USER", nullable = false)
    @Id
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

        UsersJobsConnectEntityPK that = (UsersJobsConnectEntityPK) o;

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
}
