// Use of potentially dangerous function

package checker.plugins;

import org.eclipse.cdt.codan.core.cxx.model.AbstractIndexAstChecker;
import org.eclipse.cdt.core.dom.ast.ASTVisitor;
import org.eclipse.cdt.core.dom.ast.IASTFunctionCallExpression;
import org.eclipse.cdt.core.dom.ast.IASTExpression;
import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;

public class Checker3 extends AbstractIndexAstChecker {
	String exp = "strcpy", exp1 = "strcat", exp2 = "strlcpy",
			exp3 = "sprintfW", exp4 = "strncpy", exp5 = "strcpyA",
			exp6 = "strcpyW", exp7 = "wcscpy", exp8 = "lstrcpy",
			exp9 = "lstrcpyA", exp10 = "lstrcpyW", exp11 = "StrCpyN",
			exp12 = "StrCpyNA", exp13 = "StrCpyNW", exp14 = "strcatA",
			exp15 = "strcatW", exp16 = "lstrcat", exp17 = "lstrcatA",
			exp18 = "lstrcatW", exp19 = "StrCatBuff", exp20 = "StrCatBuffA",
			exp21 = "StrCatBuffW", exp22 = "lstrncat", exp23 = "lstrncatA",
			exp24 = "lstrncatW";
	private static final String ER_ID = "examplechecker.problem3"; //$NON-NLS-1$

	public void processAst(IASTTranslationUnit ast) {
		// traverse the ast using the visitor pattern.
		ast.accept(new CheckCodeVisitor());
	}

	class CheckCodeVisitor extends ASTVisitor {
		CheckCodeVisitor() {
			shouldVisitExpressions = true;
		}

		public int visit(IASTExpression expression) {
			if (is_dang_func(expression)) {
				reportProblem(ER_ID, expression, expression.getRawSignature());
			}
			return PROCESS_CONTINUE;
		}

		private boolean is_dang_func(IASTExpression e) {

			if (e instanceof IASTFunctionCallExpression) {
				IASTFunctionCallExpression bin = (IASTFunctionCallExpression) e;
				if (bin.getFunctionNameExpression().toString().equals(exp)
						|| bin.getFunctionNameExpression().toString()
								.equals(exp1)
						|| bin.getFunctionNameExpression().toString()
								.equals(exp2)
						|| bin.getFunctionNameExpression().toString()
								.equals(exp3)
						|| bin.getFunctionNameExpression().toString()
								.equals(exp4)
						|| bin.getFunctionNameExpression().toString()
								.equals(exp5)
						|| bin.getFunctionNameExpression().toString()
								.equals(exp6)
						|| bin.getFunctionNameExpression().toString()
								.equals(exp7)
						|| bin.getFunctionNameExpression().toString()
								.equals(exp8)
						|| bin.getFunctionNameExpression().toString()
								.equals(exp9)
						|| bin.getFunctionNameExpression().toString()
								.equals(exp10)
						|| bin.getFunctionNameExpression().toString()
								.equals(exp11)
						|| bin.getFunctionNameExpression().toString()
								.equals(exp12)
						|| bin.getFunctionNameExpression().toString()
								.equals(exp13)
						|| bin.getFunctionNameExpression().toString()
								.equals(exp14)
						|| bin.getFunctionNameExpression().toString()
								.equals(exp15)
						|| bin.getFunctionNameExpression().toString()
								.equals(exp16)
						|| bin.getFunctionNameExpression().toString()
								.equals(exp17)
						|| bin.getFunctionNameExpression().toString()
								.equals(exp18)
						|| bin.getFunctionNameExpression().toString()
								.equals(exp19)
						|| bin.getFunctionNameExpression().toString()
								.equals(exp20)
						|| bin.getFunctionNameExpression().toString()
								.equals(exp21)
						|| bin.getFunctionNameExpression().toString()
								.equals(exp22)
						|| bin.getFunctionNameExpression().toString()
								.equals(exp23)
						|| bin.getFunctionNameExpression().toString()
								.equals(exp24)) {

					return true;
				}
			}
			return false;
		}

	}
}