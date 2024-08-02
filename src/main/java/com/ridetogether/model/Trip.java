package com.ridetogether.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "Trip")
public class Trip {

    @Id
    private Integer id;
    private String pickUp;
    private String drop;
    private List<String> wayPoints;
    private Date date;
    private int seatsAvailable;
    private BigDecimal price;
}
