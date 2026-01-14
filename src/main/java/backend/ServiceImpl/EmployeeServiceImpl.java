package backend.ServiceImpl;

import backend.Dto.EmployeeDto;
import backend.Entities.Employee;
import backend.Mapper.EmployeeMapper;
import backend.Repository.EmployeeRepository;
import backend.Service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final R2dbcEntityTemplate r2dbcEntityTemplate;

    @Override
    public Flux<EmployeeDto> findAll() {
        return employeeRepository.findAll()
                .map(EmployeeMapper::toDto);
    }

    @Override
    public Mono<Employee> findById(Long id) {
        return employeeRepository.findById(id)
                .switchIfEmpty(Mono.error(new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Employee not found with id: " + id))
                );
    }

    @Override
    public Mono<Employee> create(Employee employee) {
        return employeeRepository.save(Employee.from(employee)
                .createdAt(LocalDateTime.now())
                .isActive(true)
                .build()
        );
    }

    @Override
    public Mono<EmployeeDto> update(EmployeeDto employeeDto) {
        return employeeRepository.findById(employeeDto.getId())
                .flatMap(existingEmployee -> {
                        EmployeeDto.update(existingEmployee, employeeDto)
                                .setUpdatedAt(LocalDateTime.now());
                    return employeeRepository.save(existingEmployee);
                })
                .map(EmployeeMapper::toDto);
    }
}
