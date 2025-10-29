package api.dto.reqres;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class SupportDataDto {
    private String url;
    private String text;
}
