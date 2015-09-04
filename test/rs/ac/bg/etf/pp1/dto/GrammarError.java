package rs.ac.bg.etf.pp1.dto;

/**
 * Created with IntelliJ IDEA.
 * User: Aleksandar Grkajac aleksa888@gmail.com, ga040202d@student.etf.rs
 * Date: 7/18/14
 * Time: 5:35 PM
 * To change this template use File | Settings | File Templates.
 */
public class GrammarError {

    private String description;
    private Integer line;

    public GrammarError() {}

    public GrammarError(String description, Integer line) {

        this.description = description;
        this.line = line;
    }

    public Integer getLine() {

        return line;
    }

    public void setLine(Integer line) {

        this.line = line;
    }

    public String getDescription() {

        return description;
    }

    public void setDescription(String description) {

        this.description = description;
    }

    public boolean isEqual(GrammarError grammarError) {

        boolean result = false;

        if(this.getDescription().equals(grammarError.getDescription()) && this.getLine().equals(grammarError.getLine())) {

            result = true;
        }

        return result;
    }
}
