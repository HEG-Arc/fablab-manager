package net.collaud.fablab.api.data;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.FetchType;
import javax.persistence.Table;
import lombok.Getter;
import org.hibernate.annotations.Where;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Type;

/**
 * This is the business class for a <tt>Training</tt>
 *
 * @author Fabien Vuilleumier
 */
@Entity
@Table(name = "t_training")
@Getter
@Setter
@ToString
@Where(clause = "active=1")
public class TrainingEO extends AbstractDataEO<Integer> implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "training_id", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "price", nullable = true)
    private Float price;

    @Column(name = "note", nullable = true)
    @Type(type = "text")
    private String note;

    @JoinColumn(name = "training_level_id", referencedColumnName = "training_level_id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private TrainingLevelEO trainingLevel;

    @JoinColumn(name = "machine_type_id", referencedColumnName = "machine_type_id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private MachineTypeEO machineType;

    @Column(name = "active", nullable = false, columnDefinition = "TINYINT(1) DEFAULT 1")
    private boolean active;

    public TrainingEO() {
        this(null);
    }

    public TrainingEO(Integer id) {
        this.active = true;
        this.id = id;
    }
}
