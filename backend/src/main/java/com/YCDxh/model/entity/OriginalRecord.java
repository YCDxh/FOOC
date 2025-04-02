package com.YCDxh.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;


/**
 * @author YCDxhg
 */
@Getter
@Setter
@EqualsAndHashCode
@Data
@Entity
public class OriginalRecord {

    @Id
    private String id;
    private String name;
    private String department;
    private LocalDateTime checkTime;

}
