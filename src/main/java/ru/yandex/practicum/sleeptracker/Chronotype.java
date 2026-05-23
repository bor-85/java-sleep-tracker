package ru.yandex.practicum.sleeptracker;

//хронотипы сова, жаворонок, голубь
public enum Chronotype {
    OWL("Сова"),
    LARK("Жаворонок"),
    PIGEON("Голубь");

    private final String ruName;

    Chronotype(String ruName) {
        this.ruName = ruName;
    }

    public String getRuName() {
        return ruName;
    }
}
