package org.example.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ActivityResponseDTO {

    private int acti_id;
    private String acti_category;
    private String acti_url;
    private String acti_participant;
    private char acti_isProject;
    private String acti_period;


}
