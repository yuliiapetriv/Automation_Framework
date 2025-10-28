package api.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class BooksDto {
    private String userId;
    private String username;
    private BookDto[] books;
}
