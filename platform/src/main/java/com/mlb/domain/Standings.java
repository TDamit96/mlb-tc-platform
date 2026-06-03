package com.mlb.domain;

import java.util.List;

/**
 * Represents the overall standings in the MLB, containing a list of Division objects.
 */
public class Standings {
    public final List<Division> divisions;

    public Standings(List<Division> divisions) {
        this.divisions = divisions;
    }

    public List<Division> getDivisions() {
        return divisions;
    }
}
