package DTO.Response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AttributesResponse {
    private String name;

    @JsonProperty("last-name")
    private String lastName;

    private String email;

    private String age;

    private String phone;

    private String address;

    private String state;

    private String city;
}
