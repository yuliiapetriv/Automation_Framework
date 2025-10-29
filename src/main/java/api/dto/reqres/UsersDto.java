package api.dto.reqres;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class UsersDto {
    private Integer page;
    private Integer per_page;
    private Integer total;
    private Integer total_pages;
    private UserDataDto[] data;
    private SupportDataDto support;
    private MetaDataDto _meta;
}
