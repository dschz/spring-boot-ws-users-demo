package com.dsc.frm.springboot.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * si lib mapping modelmapper on doit avoir les mêmes noms de champs lexicographiques entre la classe PO et la classe DTO<br>
 * si lib mapping maststruct les noms de champs peuvent être différents, le mapping de nom est déclaré dans la classe de mapping
 * @author DSchneider
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserDto {
    private Long id;

    //not null and not ""
    @NotEmpty(message = "dto first name cannot be empty")
    private String firstName;

    @NotEmpty
    private String lastName;

    @NotEmpty
    @Email
    private String email;       //si lib mapping modelmapper, nom champ identique src/tgt (mapstruct le supporte bien sur)
    //private String emailAdr;    //si lib mapping mapstruct, nom champ différent src/tgt supporté par mapping dans interface de mapping
}
