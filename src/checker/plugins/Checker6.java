// Improper Restriction of Excessive Authentication Attempts

package checker.plugins;

import org.eclipse.cdt.codan.core.cxx.model.AbstractIndexAstChecker;
import org.eclipse.cdt.core.dom.ast.ASTNodeProperty;
import org.eclipse.cdt.core.dom.ast.ASTVisitor;
import org.eclipse.cdt.core.dom.ast.IASTBinaryExpression;
import org.eclipse.cdt.core.dom.ast.IASTExpression;
import org.eclipse.cdt.core.dom.ast.IASTEqualsInitializer;
import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;
import org.eclipse.cdt.core.dom.ast.IASTWhileStatement;
import org.eclipse.cdt.core.dom.ast.IASTIfStatement;

public class Checker6 extends AbstractIndexAstChecker {

	private static final String ER_ID = "examplechecker.problem6"; //$NON-NLS-1$
	String exp = "openSocketConnection", exp1 = "socket";

	public void processAst(IASTTranslationUnit ast) {
		// traverse the ast using the visitor pattern.
		ast.accept(new CheckCodeVisitor());
	}

	class CheckCodeVisitor extends ASTVisitor {
		CheckCodeVisitor() {
			shouldVisitExpressions = true;
		}

		public int visit(IASTExpression expression) {
			if (auth_attempts(expression)) {
				reportProblem(ER_ID, expression, expression.getRawSignature());
			}
			return PROCESS_CONTINUE;
		}

		// public String getArgument() { return this.getArgument(); }

		private boolean auth_attempts(IASTExpression e) {

			if (e instanceof IASTBinaryExpression) {

				ASTNodeProperty obj = e.getPropertyInParent();

				if (obj == IASTEqualsInitializer.INITIALIZER) {
					return true;
				}

				if (obj == IASTWhileStatement.CONDITIONEXPRESSION
						|| obj == IASTIfStatement.CONDITION) {

					IASTBinaryExpression bin = (IASTBinaryExpression) e;

					if (bin.getOperator() == bin.op_equals
							&& !(bin.getOperator() == bin.op_logicalAnd)) {
						return true;
					}

				}

			}

			return false;
		}

	}
}
