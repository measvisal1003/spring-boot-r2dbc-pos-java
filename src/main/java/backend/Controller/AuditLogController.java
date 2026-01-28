package backend.Controller;

import backend.Entities.AuditLog;
import backend.Service.AuditLogService;
import backend.Utils.PageResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/auth/auditlog")
@AllArgsConstructor
@CrossOrigin("*")
public class AuditLogController {

    private final AuditLogService auditLogService;

    @GetMapping
    public Mono<PageResponse<AuditLog>> findPagination(@RequestParam Integer pageNumber, Integer pageSize) {
        return auditLogService.findPagination(pageNumber, pageSize);
    }
}
