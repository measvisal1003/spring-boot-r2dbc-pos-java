package backend.Controller;

import backend.Dto.EmployeeDto;
import backend.Entities.Employee;
import backend.Service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/employee")
@CrossOrigin("*")
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping("/all")
    private Flux<EmployeeDto> findAll() {
        return employeeService.findAll();
    }

    @PostMapping("/create")
    private Mono<Employee> create(@RequestBody Employee employee) {
        return employeeService.create(employee);
    }

    @PutMapping("/update")
    private Mono<EmployeeDto> update(@RequestBody EmployeeDto employeeDto) {
        return employeeService.update(employeeDto);
    }


}
