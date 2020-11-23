package centene.github.io.centene_enrollee.model;

import centene.github.io.centene_enrollee.domain.Dependent;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Getter
@Setter
public class EnrolleeDTO extends PersonDTO{
    @Size(max = 20)
    private String telephone;

    @NotNull
    private Boolean isActive;

    private Set<DependentDTO> dependents = new HashSet<>();
}
