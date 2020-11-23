package centene.github.io.centene_enrollee.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@MappedSuperclass
public class BaseEntity {
    @Id
    @Column(nullable = false, updatable = false)
//    @SequenceGenerator(name = "primary_sequence", sequenceName = "primary_sequence",
//            allocationSize = 1, initialValue = 10000)
//    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "primary_sequence")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

}
