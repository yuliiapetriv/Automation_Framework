package api.dto.reqres;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class MetaDataDto {
    private String powered_by;
    private String upgrade_url;
    private String docs_url;
    private String template_gallery;
    private String message;
    private String[] features;
    private String upgrade_cta;
}
