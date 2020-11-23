package centene.github.io.centene_enrollee.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter
@Setter
public abstract class PersonDTO {
    private Long id;

    @Size(max = 100)
    @NotEmpty
    private String name;

    @NotNull
    private LocalDate birthDate;
}
