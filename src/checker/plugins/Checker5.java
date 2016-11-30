// Use of broken/risky cryptographic algorithm 

package checker.plugins;

import org.eclipse.cdt.codan.core.cxx.model.AbstractIndexAstChecker;
import org.eclipse.cdt.core.dom.ast.ASTVisitor;
import org.eclipse.cdt.core.dom.ast.IASTExpression;
import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;
import org.eclipse.cdt.core.dom.ast.IASTFunctionCallExpression;
import org.eclipse.cdt.core.dom.ast.IASTIdExpression;

public class Checker5 extends AbstractIndexAstChecker {

	private static final String ER_ID = "examplechecker.problem5"; //$NON-NLS-1$
	String exp = "EVP_des_ecb", exp1 = "EVP_CIPHER_CTX", expr1 = "des",
			expr2 = "DES";

	public void processAst(IASTTranslationUnit ast) {
		// traverse the ast using the visitor pattern.
		ast.accept(new CheckCodeVisitor());
	}

	class CheckCodeVisitor extends ASTVisitor {
		CheckCodeVisitor() {
			shouldVisitExpressions = true;
		}

		public int visit(IASTExpression expression) {
			if (is_des(expression)) {
				reportProblem(ER_ID, expression, expression.getRawSignature());
			}
			return PROCESS_CONTINUE;
		}

		private boolean is_des(IASTExpression e) {

			if (e instanceof IASTFunctionCallExpression) {
				IASTFunctionCallExpression ex = (IASTFunctionCallExpression) e;

				if (ex.getFunctionNameExpression().toString().equals(exp)) {
					if (!(ex.getArguments() == null)) {
						return true;
					}

					else {
						return false;
					}
				}

				if (e instanceof IASTIdExpression) {
					IASTIdExpression ex1 = (IASTIdExpression) e;

					if ((ex1).toString().equals(expr1)
							|| (ex1).toString().equals(expr2)) {
						return true;
					}
				}

			}
			return false;
		}
	}

}