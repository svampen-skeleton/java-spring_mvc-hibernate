package skeleton.hibernate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.util.ClassUtils;

import javax.persistence.*;
import java.text.SimpleDateFormat;

@MappedSuperclass
public abstract class Persistable {
    protected Long id;

    protected SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");

	/**
     * @return true if object don't have an id
     */
    @Transient
    @JsonIgnore
    public boolean isNew() {
        return getId() == null;
    }

    /**
     *
     * @return the primary key for object
     */

    @Id
    @Column(name = "ID", unique = true, nullable = false, precision = 12, scale = 0, columnDefinition="NUMBER")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ")
    @SequenceGenerator(sequenceName = "SEQ", allocationSize = 1, name = "SEQ")
    public Long getId(){
        return this.id;
    }
    public void setId(Long id){
        this.id = id;
    }

    @Override
	public boolean equals(Object obj) {
        if(null == obj) {
            return false;
        } else if(this == obj) {
            return true;
        } else if(!this.getClass().equals(ClassUtils.getUserClass(obj))) {
            return false;
        } else {
            Persistable that = (Persistable)obj;
            return  null != this.getId() && this.getId().equals(that.getId());
        }
    }

    @Override
	public int hashCode() {
        byte hashCode = 17;
        return hashCode + (null == this.getId()?0: this.getId().hashCode() * 31);
    }

}
