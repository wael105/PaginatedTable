package com.example.paginatedtable.models;

import lombok.Getter;

@Getter
public enum Major {
    COMPUTER_SCIENCE(0),
    MATHEMATICS(1),
    PHYSICS(2),
    CHEMISTRY(3),
    BIOLOGY(4),
    ENGLISH(5),
    HISTORY(6),
    PSYCHOLOGY(7),
    SOCIOLOGY(8),
    ECONOMICS(9),
    BUSINESS(10),
    ACCOUNTING(11),
    MARKETING(12),
    FINANCE(13),
    MANAGEMENT(14),
    POLITICAL_SCIENCE(15),
    PHILOSOPHY(16),
    ART(17),
    MUSIC(18),
    THEATER(19),
    FILM(20),
    DANCE(21),
    ENGINEERING(22),
    ARCHITECTURE(23),
    MEDICINE(24),
    NURSING(25),
    DENTISTRY(26),
    PHARMACY(27),
    LAW(28),
    EDUCATION(29),
    KINESIOLOGY(30),
    NUTRITION(31),
    CRIMINAL_JUSTICE(32),
    CULINARY_ARTS(33),
    OTHER(34);

    private final int value;

    Major(int value) {
        this.value = value;
    }

    // this is O(n) but the number of majors is small, so it's fine
    public static String getStringValueOfMajor(int value) {
        for (Major major : Major.values()) {
            if (major.getValue() == value) {
                String string = major.name().replace("_", " ");
                return string.charAt(0) + string.substring(1).toLowerCase();
            }
        }
        return null;
    }
}