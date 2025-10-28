package api.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class AddBookDto {
    private String userId;
    private IsbnDto[] collectionOfIsbns;
}
