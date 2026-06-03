package com.mlb.domain;

import java.util.List;

public class Standings {
    public final List<Division> divisions;

    public Standings(List<Division> divisions) {
        this.divisions = divisions;
    }

    public List<Division> getDivisions() {
        return divisions;
    }
}
