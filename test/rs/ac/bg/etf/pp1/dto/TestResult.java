package rs.ac.bg.etf.pp1.dto;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Class that holds informations about counting, syntax & semantic errors.
 *
 * User: Aleksandar Grkajac aleksa888@gmail.com, ga040202d@student.etf.rs Date: 7/18/14 Time: 5:23 PM
 *
 */
public class TestResult {

	private String name;

	private Counting counting;

	private List<GrammarError> syntaxErrorList;

	private List<GrammarError> semanticErrorList;

	public TestResult() {

		counting = new Counting();

		syntaxErrorList = new ArrayList<>();

		semanticErrorList = new ArrayList<>();
	}

	public String getName() {

		return name;
	}

	public void setName(String name) {

		this.name = name;
	}

	public Counting getCounting() {

		return counting;
	}

	public void setCounting(Counting counting) {

		this.counting = counting;
	}

	public List<GrammarError> getSyntaxErrorList() {

		return syntaxErrorList;
	}

	public void setSyntaxErrorList(List<GrammarError> syntaxErrorList) {

		this.syntaxErrorList = syntaxErrorList;
	}

	public List<GrammarError> getSemanticErrorList() {

		return semanticErrorList;
	}

	public void setSemanticErrorList(List<GrammarError> semanticErrorList) {

		this.semanticErrorList = semanticErrorList;
	}

	public void addSemanticError(GrammarError grammarError) {

		semanticErrorList.add(grammarError);
	}

	public void addSyntaxError(GrammarError grammarError) {

		syntaxErrorList.add(grammarError);
	}
}
