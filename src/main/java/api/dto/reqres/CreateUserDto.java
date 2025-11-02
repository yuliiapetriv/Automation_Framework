package api.dto.reqres;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class CreateUserDto {
    private String name;
    private String job;
}
