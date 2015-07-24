package rs.ac.bg.etf.pp1;

/**
 * Created with IntelliJ IDEA.
 * User: Aleksandar Grkajac ga040202d@student.etf.rs, aleksa888@gmail.com
 * Date: 5/1/14
 * Time: 11:49 PM
 * To change this template use File | Settings | File Templates.
 */

public enum ScopeNode {
	GLOBAL(1),

	CLASS(2),

	ABSTRACT_CLASS(3);

	private int scope;

	private ScopeNode(int s) {

		scope = s;
	}

	public int getScope() {

		return scope;
	}
}
