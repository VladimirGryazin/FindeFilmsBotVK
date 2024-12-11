package ru.gryazin.my_application.model;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;
@Getter
@Setter
@NoArgsConstructor
public class Docs {
    public String description;
    public String name;

    public Rating rating;

    public Poster poster;

    public String type;
}
