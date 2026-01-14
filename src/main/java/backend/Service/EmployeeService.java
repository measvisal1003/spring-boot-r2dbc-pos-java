package backend.Service;

import backend.Dto.EmployeeDto;
import backend.Entities.Employee;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public interface EmployeeService {

    Flux<EmployeeDto> findAll();
    Mono<Employee> findById(Long id);
    Mono<Employee> create(Employee employee);
    Mono<EmployeeDto> update(EmployeeDto employeeDto);
}
