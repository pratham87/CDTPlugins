// Use of a One-Way Hash without a Salt

package checker.plugins;

import org.eclipse.cdt.codan.core.cxx.model.AbstractIndexAstChecker;
import org.eclipse.cdt.core.dom.ast.ASTNodeProperty;
import org.eclipse.cdt.core.dom.ast.IASTIdExpression;
import org.eclipse.cdt.core.dom.ast.ASTVisitor;
import org.eclipse.cdt.core.dom.ast.IASTBinaryExpression;
import org.eclipse.cdt.core.dom.ast.IASTConditionalExpression;
import org.eclipse.cdt.core.dom.ast.IASTExpression;
import org.eclipse.cdt.core.dom.ast.IASTIfStatement;
import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;
import org.eclipse.cdt.core.dom.ast.IASTUnaryExpression;
import org.eclipse.cdt.core.dom.ast.IASTFunctionCallExpression;

public class Checker4 extends AbstractIndexAstChecker {
    String exp = "password", exp1 = "equal";
    private static final String ER_ID = "examplechecker.problem4"; //$NON-NLS-1$

    public void processAst(IASTTranslationUnit ast) {
        // traverse the ast using the visitor pattern.
        ast.accept(new CheckCodeVisitor());
    }

    class CheckCodeVisitor extends ASTVisitor {
        CheckCodeVisitor() {
            shouldVisitExpressions = true;
        }

        public int visit(IASTExpression expression) {
            if (isequalExpression(expression) && isifloop(expression)) {
                reportProblem(ER_ID, expression, expression.getRawSignature());
            }
            return PROCESS_CONTINUE;
        }

        private boolean isequalExpression(IASTExpression e) {
            if (e instanceof IASTBinaryExpression) {
                IASTBinaryExpression binExpr = (IASTBinaryExpression) e;
                return binExpr.getOperator() == IASTBinaryExpression.op_equals;
            }
            return false;
        }

        private boolean isifloop(IASTExpression expression) {
            ASTNodeProperty prop = expression.getPropertyInParent();
            
            if (prop == IASTIfStatement.CONDITION) {

                return true;
            }
            IASTFunctionCallExpression ex1 = (IASTFunctionCallExpression) expression;
            if (ex1.getFunctionNameExpression().toString().equals(exp1)) {

                return true;
            }

            if (prop == IASTUnaryExpression.OPERAND) {
                IASTUnaryExpression expr = (IASTUnaryExpression) expression
                        .getParent();
                if (expr.getOperator() == IASTUnaryExpression.op_bracketedPrimary
                        && expr.getPropertyInParent() == IASTConditionalExpression.LOGICAL_CONDITION) {

                    if (expression instanceof IASTIdExpression) {
                        IASTIdExpression ex = (IASTIdExpression) expression;
                        if ((ex).toString().equals(exp)) {
                            return true;
                        }
                        return true;

                    }
                    return false;
                }
                return false;
            }
            return false;
        }

    }
}