package org.example.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import org.example.entity.ApplicationMessage;

@Getter
@Setter
public class ApplicationMessageList {

    private List<ApplicationMessage> applicationList;

    public ApplicationMessageList(){
        this.applicationList = new ArrayList<ApplicationMessage>();
    }

}
