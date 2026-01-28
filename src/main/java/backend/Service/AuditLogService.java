package backend.Service;

import backend.Entities.AuditLog;
import backend.Utils.PageResponse;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public interface AuditLogService {

    Mono<PageResponse<AuditLog>> findPagination(Integer pageNumber, Integer pageSize);
}
