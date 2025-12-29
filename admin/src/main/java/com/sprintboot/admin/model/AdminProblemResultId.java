package com.sprintboot.admin.model;

import java.io.Serializable;
import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class AdminProblemResultId implements Serializable {

    private Long resultId;
    private Long problemId;
}
