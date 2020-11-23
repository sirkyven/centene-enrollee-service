package centene.github.io.centene_enrollee.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;


@Entity
@Setter
@Getter
public class Dependent extends PersonEntity{

    @ManyToOne
    @JoinColumn(name = "enrollee_id", nullable = false, updatable = false)
    @JsonBackReference
    private Enrollee enrollee;
}
