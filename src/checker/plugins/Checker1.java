// Integer Overflow or Wraparound

package checker.plugins;

import org.eclipse.cdt.codan.core.cxx.model.AbstractIndexAstChecker;
import org.eclipse.cdt.core.dom.ast.ASTVisitor;
import org.eclipse.cdt.core.dom.ast.IASTBinaryExpression;
import org.eclipse.cdt.core.dom.ast.IASTExpression;
import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;
import org.eclipse.cdt.core.dom.ast.IBasicType;

public class Checker1 extends AbstractIndexAstChecker {
    
    private static final String ER_ID = "examplechecker.problem1"; //$NON-NLS-1$

    public void processAst(IASTTranslationUnit ast) {
            // traverse the ast using the visitor pattern.
            ast.accept(new CheckCodeVisitor());
    }

    class CheckCodeVisitor extends ASTVisitor {
            CheckCodeVisitor() {
                    shouldVisitExpressions = true;
            }

            public int visit(IASTExpression expression) {
                    if (isAssignmentExpression(expression) && islongshort(expression)) 
                        {
                            reportProblem(ER_ID, expression, expression.getRawSignature());
                    }
                    return PROCESS_CONTINUE;
            }

            // Checks the assignment in expression
            private boolean isAssignmentExpression(IASTExpression e) {
                            if (e instanceof IASTBinaryExpression) {
                                    IASTBinaryExpression binExpr = (IASTBinaryExpression) e;
                                    return binExpr.getOperator() == IASTBinaryExpression.op_assign;
                            }
                            return false;
                    }

            // Checks the data type of the operands
            private boolean islongshort(IASTExpression e) {
                    if (e instanceof IASTBinaryExpression) {
                            IASTBinaryExpression binExpr = (IASTBinaryExpression) e;
                           // if(binExpr.getOperator() == IASTBinaryExpression.op_assign) {}
                       
                                IBasicType op1type = (IBasicType) binExpr.getOperand1().getExpressionType();
                                IBasicType op2type = (IBasicType) binExpr.getOperand2().getExpressionType();
                                
                                if  (!(((IBasicType)op1type).isLong()) && ((((IBasicType)op2type).isShort()))||(((IBasicType)op1type).isShort()) && ((((IBasicType)op2type).isLong())))
                                {return true;}
                           
                            
                    }
                    return false;
            }
            
    }
}