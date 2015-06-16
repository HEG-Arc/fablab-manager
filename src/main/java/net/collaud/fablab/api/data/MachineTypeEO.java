package net.collaud.fablab.api.data;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.io.Serializable;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Where;

/**
 *
 * @author Gaetan Collaud <gaetancollaud@gmail.com>
 */
@Entity
@Table(name = "t_machine_type")
@Getter
@Setter
@ToString(exclude = {"priceList", "machineList"})
@Where(clause = "active=1")
public class MachineTypeEO extends AbstractDataEO<Integer> implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "machine_type_id", nullable = false)
    private Integer id;

    @Column(name = "technicalname", nullable = false)
    private String technicalname;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "restricted", nullable = false, columnDefinition = "TINYINT(1)")
    private boolean restricted;

    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "machineType", fetch = FetchType.LAZY)
    private List<MachineEO> machineList;

    @JsonManagedReference("machineType-price")
    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "machineTypeEO", fetch = FetchType.LAZY)
    private Set<PriceMachineEO> priceList;

    @Column(name = "active", nullable = false, columnDefinition = "TINYINT(1) DEFAULT 1")
    private boolean active;

    /**
     * Add a machinery to this type (bidirectionnal use).
     *
     * @param machine the machinery
     */
    public void addMachine(MachineEO machine) {
        this.getMachineList().add(machine);
        machine.setMachineType(this);
    }

    public MachineTypeEO() {
        this(null);
    }

    public MachineTypeEO(Integer machineTypeId) {
        this.active = true;
        this.id = machineTypeId;
    }
}
