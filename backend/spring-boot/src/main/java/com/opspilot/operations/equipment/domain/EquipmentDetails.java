package com.opspilot.operations.equipment.domain;

import java.util.Locale;
import java.util.regex.Pattern;

public record EquipmentDetails(String code, String name) {

    private static final Pattern CODE_PATTERN =
            Pattern.compile("^[A-Z0-9][A-Z0-9_-]{0,49}$");

    public EquipmentDetails {
        if (code == null) {
            throw new IllegalArgumentException("Equipment code is required");
        }

        code = code.strip().toUpperCase(Locale.ROOT);

        if (!CODE_PATTERN.matcher(code).matches()) {
            throw new IllegalArgumentException(
                    "Equipment code must contain 1–50 letters, digits, "
                            + "hyphens or underscores, starting with a letter or digit"
            );
        }

        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Equipment name is required");
        }

        name = name.strip();

        if (name.codePointCount(0, name.length()) > 200) {
            throw new IllegalArgumentException(
                    "Equipment name must not exceed 200 characters"
            );
        }
    }
}