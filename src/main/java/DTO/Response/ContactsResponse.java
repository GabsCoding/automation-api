package DTO.Response;

import DTO.Response.DataResponse;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ContactsResponse {
    private DataResponse data;
}