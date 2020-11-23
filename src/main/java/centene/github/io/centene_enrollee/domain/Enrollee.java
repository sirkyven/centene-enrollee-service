package centene.github.io.centene_enrollee.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;


@Entity
@Setter
@Getter
public class Enrollee extends PersonEntity {

    @Column(length = 20)
    private String telephone;

    @Column
    @NotNull
    private Boolean isActive;

    @OneToMany(mappedBy = "enrollee",
            targetEntity = Dependent.class,
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL
    )
    @JsonManagedReference
    private Set<Dependent> dependents = new HashSet<>();


    public void setDependents( Set<Dependent> dependents) {
        if (dependents != null || !dependents.isEmpty()) {
            dependents.forEach(this::addDependent);
        }
    }

    public void addDependent(Dependent dependent) {
       // dependent.setEnrollee(this);
        this.dependents.add(dependent);
    }
}
