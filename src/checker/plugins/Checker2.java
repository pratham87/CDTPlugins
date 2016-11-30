// Uncontrolled Format string

package checker.plugins;

import org.eclipse.cdt.codan.core.cxx.model.AbstractIndexAstChecker;
import org.eclipse.cdt.core.dom.ast.ASTVisitor;
import org.eclipse.cdt.core.dom.ast.IASTPreprocessorIncludeStatement;
import org.eclipse.cdt.core.dom.ast.IASTFunctionCallExpression;
import org.eclipse.cdt.core.dom.ast.IASTExpression;
import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;
import org.eclipse.cdt.core.dom.ast.IASTInitializerClause;

public class Checker2 extends AbstractIndexAstChecker {
	String exp = "printf", exp1 = "snprintf", exp2 = "\"%d\"", exp3 = "\"%s\"",
			exp4 = "\"%c\"", exp5 = "\"%f\"", exp6 = "\"%ld\"",
			exp7 = "\"%lf\"", exp8 = "\"%o\"", exp9 = "\"%p\"",
			exp10 = "\"%u\"", exp11 = "\"%x\"";
	private static final String ER_ID = "examplechecker.problem2"; //$NON-NLS-1$

	public void processAst(IASTTranslationUnit ast) {
		// traverse the ast using the visitor pattern.
		ast.accept(new CheckCodeVisitor());
	}

	class CheckCodeVisitor extends ASTVisitor {
		CheckCodeVisitor() {
			shouldVisitExpressions = true;
		}

		public int visit(IASTExpression expression) {
			if (no_format_spec(expression)) {
				reportProblem(ER_ID, expression, expression.getRawSignature());
			}
			return PROCESS_CONTINUE;
		}

		private boolean no_format_spec(IASTExpression e) {

			if (e instanceof IASTPreprocessorIncludeStatement) {
				IASTPreprocessorIncludeStatement obj = (IASTPreprocessorIncludeStatement) e;
				if (obj.isActive()) {
					return true;
				}
			}

			if (e instanceof IASTFunctionCallExpression) {
				IASTFunctionCallExpression bin = (IASTFunctionCallExpression) e;
				if (bin.getFunctionNameExpression().toString().equals(exp)
						|| bin.getFunctionNameExpression().toString()
								.equals(exp1)) {

					IASTInitializerClause[] obj = ((IASTFunctionCallExpression) e)
							.getArguments();
					// if(obj.length==1){

					for (int i = 0; i < obj.length; i++) {

						if (!((obj[0].toString().equals(exp2))
								|| (obj[0].toString().equals(exp3))
								|| (obj[0].toString().equals(exp4))
								|| (obj[0].toString().equals(exp5))
								|| (obj[0].toString().equals(exp6))
								|| (obj[0].toString().equals(exp7))
								|| (obj[0].toString().equals(exp8))
								|| (obj[0].toString().equals(exp9))
								|| (obj[0].toString().equals(exp10))
								|| (obj[0].toString().equals(exp11))))

						{
							return true;
						}

						// if(obj[i] instanceof IASTPointerOperator)
						// {return true;}

					}
					// }

				}

			}
			return false;
		}
	}
}
