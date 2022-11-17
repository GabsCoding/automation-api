package DTO.Request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ContactsRequest {
    private String name;

    @JsonProperty("last_name")
    private String lastName;

    private String email;

    private String age;

    private String phone;

    private String address;

    private String state;

    private String city;
}