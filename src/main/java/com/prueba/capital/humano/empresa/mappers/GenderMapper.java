package com.prueba.capital.humano.empresa.mappers;

import com.prueba.capital.humano.empresa.dto.GenderDto;
import com.prueba.capital.humano.empresa.entities.Gender;

public class GenderMapper {

    public static GenderDto toGenderDto(Gender gender) {
        if (gender == null) {
            return null;
        }
        GenderDto genderDto = new GenderDto();
        genderDto.setId(gender.getId());
        genderDto.setName(gender.getName());
        return genderDto;
    }

    public static Gender toGender(GenderDto genderDto) {
        if (genderDto == null) {
            return null;
        }
        Gender gender = new Gender();
        gender.setId(genderDto.getId());
        gender.setName(genderDto.getName());
        return gender;
    }
}
