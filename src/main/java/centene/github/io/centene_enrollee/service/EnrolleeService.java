package centene.github.io.centene_enrollee.service;

import centene.github.io.centene_enrollee.domain.Dependent;
import centene.github.io.centene_enrollee.model.DependentDTO;
import centene.github.io.centene_enrollee.repos.DependentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import centene.github.io.centene_enrollee.config.CustomNotFoundException;
import centene.github.io.centene_enrollee.domain.Enrollee;
import centene.github.io.centene_enrollee.model.EnrolleeDTO;
import centene.github.io.centene_enrollee.repos.EnrolleeRepository;


@Service
public class EnrolleeService {

    private final EnrolleeRepository enrolleeRepository;
    private final DependentRepository dependentRepository;

    @Autowired
    public EnrolleeService(final EnrolleeRepository enrolleeRepository,
                           final DependentRepository dependentRepository) {
        this.enrolleeRepository = enrolleeRepository;
        this.dependentRepository = dependentRepository;
    }


    public EnrolleeDTO get(final Long id) {
        return enrolleeRepository.findById(id)
                .map(this::mapToDTO)
                .orElseThrow(() -> new CustomNotFoundException("Enrollee by given ID is not found") );
    }

    //
    public EnrolleeDTO create(final EnrolleeDTO enrolleeDTO) {
        final Enrollee enrollee = mapToEntity(enrolleeDTO);
         return mapToDTO(enrolleeRepository.save(enrollee));
    }

    // updating name, telephone, date of birth, and active status of a enrollee
    // we can do better by removing valid checking on the front part and
    // updating what's truly updated instead of updating all the four fields for each request.
    public void update(final Long id, final EnrolleeDTO enrolleeDTO) {
        final Enrollee enrollee = enrolleeRepository.findById(id)
                .orElseThrow(CustomNotFoundException::new);

        enrollee.setIsActive(enrolleeDTO.getIsActive());
        enrollee.setName(enrolleeDTO.getName());
        enrollee.setTelephone(enrolleeDTO.getTelephone());
        enrollee.setBirthDate(enrolleeDTO.getBirthDate());

        enrolleeRepository.save(enrollee);
    }

    // delete enrollee if exists and otherwise throw a missing ID exception
    public void delete(final Long id) {
        if (enrolleeRepository.existsById(id)) {
            enrolleeRepository.deleteById(id);
        } else {
            throw new CustomNotFoundException("Enrollee ID not found to delete");
        }
    }

    private EnrolleeDTO mapToDTO(final Enrollee enrollee) {
        EnrolleeDTO enrolleeDTO = new EnrolleeDTO();
        enrolleeDTO.setId(enrollee.getId());
        enrolleeDTO.setName(enrollee.getName());
        enrolleeDTO.setTelephone(enrollee.getTelephone());
        enrolleeDTO.setBirthDate(enrollee.getBirthDate());
        enrolleeDTO.setIsActive(enrollee.getIsActive());
        enrolleeDTO.setDependents(convertDependentsDTO(enrollee));
        return enrolleeDTO;
    }

    private Set<DependentDTO> convertDependentsDTO(Enrollee enrollee) {
        return enrollee.getDependents()
                .stream()
                .map(this::mapToDependentDTO)
                .collect(Collectors.toUnmodifiableSet());
    }


    private DependentDTO mapToDependentDTO(final Dependent dependent) {
        DependentDTO dto = new DependentDTO();
        dto.setId(dependent.getId());
        dto.setName(dependent.getName());
        dto.setBirthDate(dependent.getBirthDate());
        return dto;
    }

    private Enrollee mapToEntity(final EnrolleeDTO enrolleeDTO) {
        final Enrollee enrollee = new Enrollee();
        enrollee.setId(enrolleeDTO.getId());
        enrollee.setName(enrolleeDTO.getName());
        enrollee.setTelephone(enrolleeDTO.getTelephone());
        enrollee.setBirthDate(enrolleeDTO.getBirthDate());
        enrollee.setIsActive(enrolleeDTO.getIsActive());
        enrollee.setDependents(convertDependentsEntity(enrolleeDTO, enrollee));
        return enrollee;
    }

    private Set<Dependent> convertDependentsEntity(final EnrolleeDTO enrolleeDTO, final Enrollee enrollee) {
       return enrolleeDTO.getDependents()
                .stream()
                .map((DependentDTO dependentDTO) -> mapToDependentEntity(dependentDTO, enrollee))
                .collect(Collectors.toSet());
    }

    private Dependent mapToDependentEntity(final DependentDTO dependentDTO, final Enrollee enrollee) {
        Dependent dependent = new Dependent();
        dependent.setBirthDate(dependentDTO.getBirthDate());
        dependent.setName(dependentDTO.getName());
        dependent.setEnrollee(enrollee);
        return dependent;
    }


    private Enrollee findEnrolleeById(Long id) {
        Optional<Enrollee> optionalEnrollee = enrolleeRepository.findById(id);
        if (optionalEnrollee.isEmpty()) {
            throw new CustomNotFoundException();
        }
        return optionalEnrollee.get();
    }




    public EnrolleeDTO addDependent(Long id, DependentDTO dependentDTO) {
        Enrollee enrollee = findEnrolleeById(id);
        Dependent dependent = mapToDependentEntity(dependentDTO, enrollee);
        enrollee.addDependent(dependent);
        enrollee = enrolleeRepository.save(enrollee);
        return mapToDTO(enrollee);
    }

    public void deleteDependentById(Long id, Long dId) {
        if (this.dependentRepository.existsById(dId)) {
            this.dependentRepository.deleteById(dId);
        } else {
            throw new CustomNotFoundException("Dependent by given ID is not found.");
        }
    }

    public void updateDependent(Long id, Long dId, DependentDTO dependentDTO) {
        if (this.dependentRepository.existsById(dId)) {
            Dependent dependent = new Dependent();
            dependent.setBirthDate(dependentDTO.getBirthDate());
            dependent.setName(dependentDTO.getName());
            dependent.setId(dependentDTO.getId());
            this.dependentRepository.save(dependent);
        } else {
            throw new CustomNotFoundException("Dependent by given ID is not found.");
        }
    }
}
