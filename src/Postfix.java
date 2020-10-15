import java.util.Stack;

import org.logicng.formulas.Formula;
import org.logicng.formulas.FormulaFactory;
import org.logicng.io.parsers.PropositionalParser;
class Postfix
{
	static int Prec(char ch)
	{
		switch (ch)
		{
		case '~':
			return 3;
		case '&':
		case '|':
			return 2;
		case '>':
		case '#':
			return 1;
		}
		return -1;
	}
	public static String infixToPostfix(String exp)
	{
		String result = new String("");
		Stack<Character> stack = new Stack<>();
		for (int i = 0; i < exp.length(); ++i)
		{
			char c = exp.charAt(i);
			if (Character.isLetter(c))
				result += c;
			else if (c == '(')
				stack.push(c);
			else if (c == ')')
			{
				while (!stack.isEmpty() && stack.peek() != '(')
					result += stack.pop();
				if (!stack.isEmpty() && stack.peek() != '(')
					return "Invalid Expression";
				else
					stack.pop();
			} else
			{
				while (!stack.isEmpty() && Prec(c) <= Prec(stack.peek()))
				{
					if (stack.peek() == '(')
						return "Invalid Expression";
					result += stack.pop();
				}
				stack.push(c);
			}
		}
		while (!stack.isEmpty())
		{
			if (stack.peek() == '(')
				return "Invalid Expression";
			result += stack.pop();
		}
		return result;
	}
	public static void main(String[] args) throws Exception
	{
		String exp = "(A =>B) | S";
		final FormulaFactory f = new FormulaFactory();
		final PropositionalParser p = new PropositionalParser(f);
		final Formula formula = p.parse(exp);
		final Formula cnf = formula.cnf();
		System.out.println(exp);
		System.out.println("FORME NORMAL CONJONCTIVE");
		System.out.println(cnf);
	}
}
