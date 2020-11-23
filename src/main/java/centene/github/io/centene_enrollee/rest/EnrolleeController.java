package centene.github.io.centene_enrollee.rest;

import centene.github.io.centene_enrollee.model.DependentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import centene.github.io.centene_enrollee.model.EnrolleeDTO;
import centene.github.io.centene_enrollee.service.EnrolleeService;

import javax.validation.Valid;
import org.springframework.http.HttpStatus;


@RestController
@RequestMapping(value = "/api/enrollees", produces = MediaType.APPLICATION_JSON_VALUE)
public class EnrolleeController {

    private final EnrolleeService enrolleeService;

    @Autowired
    public EnrolleeController(final EnrolleeService enrolleeService) {
        this.enrolleeService = enrolleeService;
    }


    @GetMapping("/{id}")
    public EnrolleeDTO getEnrollee(@PathVariable final Long id) {
        return enrolleeService.get(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EnrolleeDTO createEnrollees(@RequestBody @Valid final EnrolleeDTO enrolleeDTO) {
        return enrolleeService.create(enrolleeDTO);
    }

    @PutMapping("/{id}")
    public void updateEnrollees(@PathVariable final Long id,
                                @RequestBody @Valid final EnrolleeDTO enrolleeDTO) {
        enrolleeService.update(id, enrolleeDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEnrollees(@PathVariable final Long id) {
        enrolleeService.delete(id);
    }


    @PostMapping("/{id}/dependents")
    @ResponseStatus(HttpStatus.CREATED)
    public EnrolleeDTO addDependent(@PathVariable final Long id,
                             @RequestBody @Valid final DependentDTO dependentDTO) {
         return enrolleeService.addDependent(id, dependentDTO);
    }


    @PutMapping(value = {"/{id}/dependents/{dId}", "/dependents/{dId}"})
    @ResponseStatus(HttpStatus.CREATED)
    public void updateDependent(@PathVariable(value = "id", required = false)  final Long id,
                                @PathVariable(value = "dId") final Long dId,
                                @RequestBody @Valid final DependentDTO dependentDTO) {
        enrolleeService.updateDependent(id, dId, dependentDTO);
    }

    @DeleteMapping(value = {"/{id}/dependents/{dId}", "/dependents/{dId}"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDependent(@PathVariable(value = "id", required = false) final Long id,
                                @PathVariable("dId") final Long dId) {
        enrolleeService.deleteDependentById(id, dId);
    }

}
