public enum CommandWord {
    GO, BACK, LOOK, GET, USE, INVENTORY, QUIT, HELP, UNKNOWN;

    public static CommandWord getCommandWord(String command) {
        for (CommandWord cw : values()) {
            if (cw.name().equalsIgnoreCase(command)) {
                return cw;
            }
        }
        return UNKNOWN;
    }
}

