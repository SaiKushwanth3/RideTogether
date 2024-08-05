package com.ridetogether.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Route {

    private String routeId;
    private String source;
    private String destination;
    private List<Stop> stops;
}
