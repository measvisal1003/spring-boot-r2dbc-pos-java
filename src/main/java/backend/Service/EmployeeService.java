package backend.Service;

import backend.Dto.EmployeeDto;
import backend.Dto.EmployeeUser;
import backend.Entities.Employee;
import backend.Utils.PageResponse;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public interface EmployeeService {

    Flux<EmployeeDto> findAll();
    Mono<Employee> findById(Long id);
    Mono<Employee> create(Employee employee);
    Mono<Employee> update(Employee employee);
    Mono<Long> delete(Long id);

    Mono<PageResponse<EmployeeUser>> findPagination(Integer pageNumber, Integer pageSize);
}
