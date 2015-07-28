package rs.ac.bg.etf.pp1.domain;

/**
 * Created with IntelliJ IDEA.
 * User: Aleksandar Grkajac ga040202d@student.etf.rs, aleksa888@gmail.com
 * Date: 5/6/14
 * Time: 8:03 AM
 * To change this template use File | Settings | File Templates.
 */
public enum ConstNode {

	NUMBER(1),
	CHAR(2),
	BOOL(3),
	STRING(4);

	private int constant;

	private ConstNode(int constant) {

		this.constant = constant;
	}

	public int getConstant() {

		return this.constant;
	}
}
