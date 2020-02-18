package pl.lukbed.ecantor.application;

import com.google.common.collect.ImmutableSet;

import java.util.Set;

//validation api from vavr.io could be better solution
//mutable builder is also needed
public class ActionResponse {
    private final Set<String> errors;

    public static ActionResponse ofSet(Set<String> errors) {
        return new ActionResponse(errors);
    }

    public static ActionResponse ofMessage(String message) {
        return new ActionResponse(message);
    }

    private ActionResponse(Set<String> errors) {
        this.errors = ImmutableSet.copyOf(errors);
    }

    private ActionResponse(String message) {
        errors = Set.of(message);
    }

    public boolean isSuccessful() {
        return errors.size() == 0;
    }

    public Set<String> getErrors() {
        return ImmutableSet.copyOf(errors);
    }
}
