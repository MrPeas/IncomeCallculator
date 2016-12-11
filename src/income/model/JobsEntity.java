package income.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Collection;

/**
 * Created by Janusz on 01.11.2016.
 */
@Entity
@NamedQuery(name="JobsEntity.findAll",query = "SELECT e from JobsEntity e")
@Table(name = "JOBS", schema = "PUBLIC", catalog = "INCOME")
public class JobsEntity {
    private long id;
    private String name;
    private String describe;
    private BigDecimal deafultIncome;
    private long idUser;
    private Collection<JobsDetailsEntity> jobsDetailsesById;
    private BigDecimal deafultincome;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "NAME", nullable = false, length = 55)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "DESCRIBE", nullable = true)
    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        JobsEntity that = (JobsEntity) o;

        if (id != that.id) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (describe != null ? !describe.equals(that.describe) : that.describe != null) return false;
        if (deafultIncome != null ? !deafultIncome.equals(that.deafultIncome) : that.deafultIncome != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (describe != null ? describe.hashCode() : 0);
        result = 31 * result + (deafultIncome != null ? deafultIncome.hashCode() : 0);
        return result;
    }

    @Basic
    @Column(name = "IDUSER", nullable = false)
    public long getIdUser() {
        return idUser;
    }

    public void setIdUser(long idUser) {
        this.idUser = idUser;
    }

    @OneToMany(mappedBy = "jobsByIdJob")
    public Collection<JobsDetailsEntity> getJobsDetailsesById() {
        return jobsDetailsesById;
    }

    public void setJobsDetailsesById(Collection<JobsDetailsEntity> jobsDetailsesById) {
        this.jobsDetailsesById = jobsDetailsesById;
    }

    @Basic
    @Column(name = "DEAFULTINCOME", nullable = true, precision = 2)
    public BigDecimal getDeafultincome() {
        return deafultincome;
    }

    public void setDeafultincome(BigDecimal deafultincome) {
        this.deafultincome = deafultincome;
    }
}
