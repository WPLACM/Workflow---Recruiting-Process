package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.example.entity.Client_Company;
import org.example.repository.Client_Company_Repository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Job_Opening_Information_Request {
    //Data Transfer Object
    private Client_Company client_company;
}
