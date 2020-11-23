package centene.github.io.centene_enrollee.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Setter
@Getter
@MappedSuperclass
public class PersonEntity extends BaseEntity {
    @Column(length = 100)
    @NotEmpty
    private String name;

    @Column
    @NotNull
    private LocalDate birthDate;
}
