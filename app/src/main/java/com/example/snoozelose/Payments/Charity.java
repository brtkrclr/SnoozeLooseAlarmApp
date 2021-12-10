package com.example.snoozelose.Payments;

import com.example.snoozelose.R;

/**
 * Represents a charity that the user can donate to.
 */
public class Charity {

    public static final Charity[] dummyCharities = new Charity[] {
            new Charity("Animal group", "We care very much about animals. So, if you do as well, consider donating your money to us!"),
            new Charity("1 dollar = 1 banana", "We feed hungry monkeys (which, we ensure you, there are a lot of) bananas."),
            new Charity("Student debt", "Help the creators of this app pay off their student debt."),
            new Charity("Idk 1", "Donations please."),
            new Charity("Idk 2", "Yeah, I just want money.")
    };

    private final String name;
    private final String description;

    public Charity(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

}
