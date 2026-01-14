package backend.Dto;

public record OrderRequest(
        String code, //product code
        int quantity
) {
}
