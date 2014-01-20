package sps.console;

import sps.util.Parse;

public class DevParsedCommand {
    public int[] Arguments;
    public String Id;

    public DevParsedCommand(String input) {
        String[] values = input.split(" ");
        Id = values[0].trim();
        Arguments = new int[values.length - 1];
        for (int ii = 1; ii < values.length; ii++) {
            Arguments[ii - 1] = Parse.inte(values[ii]);
        }
    }
}