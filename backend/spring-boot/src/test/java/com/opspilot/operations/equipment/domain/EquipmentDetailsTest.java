package com.opspilot.operations.equipment.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class EquipmentDetailsTest {

    @Test
    void normalizesCodeAndTrimsName() {
        EquipmentDetails details = new EquipmentDetails(
                "  a-105  ",
                "  Compressor A-105  "
        );

        assertThat(details.code()).isEqualTo("A-105");
        assertThat(details.name()).isEqualTo("Compressor A-105");
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"   ", "A 105", "-A105", "A/105"})
    void rejectsInvalidCodes(String code) {
        assertThatThrownBy(() ->
                new EquipmentDetails(code, "Compressor"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"   ", "\t"})
    void rejectsMissingNames(String name) {
        assertThatThrownBy(() ->
                new EquipmentDetails("A-105", name))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Equipment name is required");
    }

    @Test
    void acceptsMaximumLengths() {
        EquipmentDetails details = new EquipmentDetails(
                "A".repeat(50),
                "N".repeat(200)
        );

        assertThat(details.code()).hasSize(50);
        assertThat(details.name()).hasSize(200);
    }

    @Test
    void rejectsCodeLongerThanFiftyCharacters() {
        assertThatThrownBy(() ->
                new EquipmentDetails("A".repeat(51), "Compressor"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void rejectsNameLongerThanTwoHundredCharacters() {
        assertThatThrownBy(() ->
                new EquipmentDetails("A-105", "N".repeat(201)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Equipment name must not exceed 200 characters");
    }
}