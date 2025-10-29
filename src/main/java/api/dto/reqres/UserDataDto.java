package api.dto.reqres;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class UserDataDto {
    private Integer id;
    private String email;
    private String first_name;
    private String last_name;
    private String avatar;
}
