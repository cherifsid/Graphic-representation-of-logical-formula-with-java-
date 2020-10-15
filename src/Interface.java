import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedHashSet;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import java.util.Stack;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.mariuszgromada.math.mxparser.Constant;
import org.mariuszgromada.math.mxparser.Expression;
public class Interface extends JFrame
{
	private JPanel contentPane;
	static public  Panel panel;
	private static  JTextField formule;
	public static String arbreFormat="";
	static Random ran = new Random();
	static int ch = ran.nextInt(4) + 1;
    static	int x = 500 ,  y = 20;
	public static JButton btnBitree ;
	public static JButton eva = new JButton("EVALUATE");
	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
		{
			@Override
			public void run()
			{
				try
				{
					Interface frame = new Interface();
					frame.setVisible(true);
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}
	public static  boolean isConnector(char caracter)
	{
	     if (caracter == '&' || caracter == '~'|| caracter == '>' || caracter == '|'|| caracter == '#')
	     {
	         return true;
	     }
	     return false;
	 }
	public static  Node construire (String  postfix)
	{
	     Stack<Node> pile = new Stack();
	     Node tree, tree1, tree2;
	     for (int i = 0; i<postfix.length(); i++)
	     {
	         if (!isConnector(postfix.charAt(i))) //cas de proposition
	         {
	             tree = new Node(postfix.charAt(i));
	             pile.push(tree);
	         }
	         else
	         {
	             if(postfix.charAt(i)!='~')//cas de connecteur binaire
	             {
	            	 tree = new Node(postfix.charAt(i));
		             tree.right = pile.pop();
		             tree.left = pile.pop();
	             }
	             else//cas de negation
	             {
	            	 tree = new Node(postfix.charAt(i));
	            	 tree.right = pile.pop();
	             }
	             pile.push(tree);
	         }
	     }
	     tree = pile.peek();
	     pile.pop();
	     return tree;
	 }
	public static  void affichage(Node tree)
	{
		 if(tree!=null)
		 {
			 arbreFormat = arbreFormat+tree.value;
			 System.out.print("(");
			 System.out.print(tree.value);
			 affichage(tree.left);
			 affichage(tree.right);
			 System.out.print(")");
		 }
	 }

    public static void drawnodes(Node tree, int x , int y)
    {
    	if(tree != null)
    	{
    		Label l = new Label(String.valueOf("       "+tree.value));
    		panel.add(l);

    		l.setBounds(x, y, 50, 50);
    		ch = ran.nextInt(4) + 0;
    		switch (ch)
    		{
    		case 0 :
    			l.setBackground(new Color (225, 95, 65));
    			break ;
    		case 1:
        		l.setBackground(new Color (255, 234, 167));
        		break;
    		case 2:
        		l.setBackground(new Color (163, 203, 56));
        		break;
    		case 3:
        		l.setBackground(new Color (11, 232, 129));
        		break;
    		case 4:
        		l.setBackground(new Color (248, 165, 194) );
        		break;
    		}
    		if(tree.right!=null)
    		{
    			if(tree.left==null)
    			{

    				drawnodes(tree.right,x,y+100);
    				panel.getGraphics().drawLine(x+20, y, x+20, y+100);
    			}
    			else
    			{
    				drawnodes(tree.right,x+70,y+100);
    				panel.getGraphics().drawLine(x, y, x+70, y+100);
    				drawnodes(tree.left,x-80,y+100);
    				panel.getGraphics().drawLine(x+10, y+50, x-40, y+100);
    			}
    		}
    	}
    }
	public Interface()
	{
		setTitle("LIATP");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(-2, 0, 1380, 720);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(192, 57, 43));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		panel = new Panel();
		panel.setBackground(new Color(236, 240, 241));
		panel.setBounds(212, 11, 1143, 685);
		contentPane.add(panel);
		panel.setLayout(null);
		formule = new JTextField("p>(q&m)|~((s&g)|k)");
		formule.setFont(new Font("Tahoma", Font.BOLD, 18));
		formule.setBackground(new Color(242, 243, 244));
		formule.setBounds(10, 23, 192, 39);
		contentPane.add(formule);
		formule.setColumns(10);
		JButton b1 = new JButton("~");
		b1.setBackground(new Color(210, 180, 222 ));
		b1.setFocusable(false);
		b1.setFocusTraversalKeysEnabled(false);
		b1.setFocusPainted(false);
		b1.setBorderPainted(false);
		b1.setFont(new Font("Tahoma", Font.PLAIN, 23));
		b1.setBounds(10, 82, 83, 39);
		contentPane.add(b1);
		b1.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				formule.setText(formule.getText()+"~");
			}
		});
		JButton b2 = new JButton("&");
		b2.setBackground(new Color(210, 180, 222 ));
		b2.setBorderPainted(false);
		b2.setFocusPainted(false);
		b2.setFocusTraversalKeysEnabled(false);
		b2.setFocusable(false);
		b2.setFont(new Font("Tahoma", Font.PLAIN, 23));
		b2.setBounds(119, 82, 83, 39);
		contentPane.add(b2);
	    b2.addActionListener(new ActionListener()
	    {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				formule.setText(formule.getText()+"&");
			}
		});
		JButton b3 = new JButton(">");
		b3.setBackground(new Color(210, 180, 222 ));
		b3.setBorderPainted(false);
		b3.setFocusPainted(false);
		b3.setFocusTraversalKeysEnabled(false);
		b3.setFocusable(false);
		b3.setFont(new Font("Tahoma", Font.PLAIN, 23));
		b3.setBounds(119, 132, 83, 39);
		contentPane.add(b3);
	    b3.addActionListener(new ActionListener()
	    {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				formule.setText(formule.getText()+">");
			}
		});
		JButton b4 = new JButton("|");
		b4.setBackground(new Color(210, 180, 222 ));
		b4.setFocusPainted(false);
		b4.setFocusTraversalKeysEnabled(false);
		b4.setFocusable(false);
		b4.setBorderPainted(false);
		b4.setFont(new Font("Tahoma", Font.PLAIN, 23));
		b4.setBounds(10, 132, 83, 39);
		contentPane.add(b4);
	    b4.addActionListener(new ActionListener()
	    {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				formule.setText(formule.getText()+"|");
			}
		});
	    JButton b5 = new JButton("#");
		b5.setBackground(new Color(210, 180, 222 ));
	    b5.setFocusPainted(false);
	    b5.setFocusTraversalKeysEnabled(false);
	    b5.setFocusable(false);
	    b5.setBorderPainted(false);
		b5.setFont(new Font("Tahoma", Font.PLAIN, 23));
		b5.setBounds(63, 194, 83, 39);
		contentPane.add(b5);
        b5.addActionListener(new ActionListener()
        {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				formule.setText(formule.getText()+"#");
			}
		});
		JButton Go = new JButton("POSTFIX");
		Go.setBackground(new Color(210, 180, 222 ));
		Go.setBorderPainted(false);
		Go.setFocusPainted(false);
		Go.setFocusTraversalKeysEnabled(false);
		Go.setFocusable(false);
		Go.setFont(new Font("Tahoma", Font.PLAIN, 18));
		Go.setBounds(48, 260, 117, 39);
		contentPane.add(Go);
		Go.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				panel.removeAll();
				int  x =  10 , y = 10 ;
				String ex = formule.getText();
				String result;
				result = Postfix.infixToPostfix(ex);
				String[] strArray = result.split("");
				Label[] labels = new Label[strArray.length];
				int i = 0, c  = 1;
				for(i = 0 ; i < strArray.length ;i++)
				{
				   labels[i] = new Label("     "+strArray[i]);
				   labels[i].setFont(new Font("Tahoma", Font.BOLD, 15));
				   if(x >= 1100 )
				   {
					   x = 10 ;
					   y = 80 ;
				   }
				   labels[i].setBounds(x, y, 50, 50);
				   if(c == 1)
				   {
					  labels[i].setBackground(new Color(171, 235, 198 ));
					   c = 2;
				   }
				   else if(c==2) {labels[i].setBackground(new Color(249, 231, 159));
				    c = 1 ;}
				   panel.add(labels[i]);
				   x = x+55;
				}
			}
		});
		JButton reset = new JButton("RESET");
		reset.setBackground(new Color(210, 180, 222));
		reset.setBorderPainted(false);
		reset.setFocusPainted(false);
		reset.setFocusTraversalKeysEnabled(false);
		reset.setFocusable(false);
		reset.setFont(new Font("Tahoma", Font.PLAIN, 18));
		reset.setBounds(48, 445, 117, 39);
		contentPane.add(reset);
		btnBitree = new JButton("BI-TREE");
		btnBitree.setBackground(new Color(210, 180, 222 ));
		btnBitree.setFocusPainted(false);
		btnBitree.setFocusTraversalKeysEnabled(false);
		btnBitree.setFocusable(false);
		btnBitree.setBorderPainted(false);
		btnBitree.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnBitree.setBounds(48, 318, 117, 39);
		contentPane.add(btnBitree);
		eva.setVisible(false);
		JButton evaluate = new JButton("EVAL EXP");
		evaluate.setFont(new Font("Tahoma", Font.PLAIN, 18));
		evaluate.setFocusable(false);
		evaluate.setFocusTraversalKeysEnabled(false);
		evaluate.setFocusPainted(false);
		evaluate.setBorderPainted(false);
		evaluate.setBackground(new Color(210, 180, 222));
		evaluate.setBounds(48, 378, 117, 39);
		contentPane.add(evaluate);
		evaluate.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				panel.removeAll();

				int  x =  10 , y = 10 ;
				String input = formule.getText();
				String extract = input.replaceAll("[^a-zA-Z]+", "");
				System.out.println(extract);
				String[] strArray = extract.split("");
				Label[] labels = new Label[strArray.length];
				int i = 0, c  = 1;
				for(i = 0 ; i < strArray.length ;i++)
				{
					labels[i] = new Label("       "+strArray[i]);
					labels[i].setFont(new Font("Tahoma", Font.BOLD, 15));
					   if(x >= 950)
					   {
						   x = 10 ;
						   y = 110 ;
					   }
					   labels[i].setBounds(x, y, 70, 70);
					   if(c == 1)
					   {
						  labels[i].setBackground(new Color(171, 235, 198 ));
						   c = 2;
					   }
					   else if(c==2) {labels[i].setBackground(new Color(249, 231, 159));
					    c = 1 ;}
					   panel.add(labels[i]);
					   x = x+200;
					}
				TextArea textArea = new TextArea();
				textArea.setBounds(10, 200, 1123, 460);
				panel.add(textArea);
				try
				{
					textArea.setFont(new Font("Tahoma", Font.BOLD, 22));
					textArea.setText(textArea.getText()+Main.postFixToTruthTable(Postfix.infixToPostfix(input))+"\n\n");
				} catch (Exception e11)
				{
					e11.printStackTrace();
				}
				String z = new String("");

				StringBuilder sb = new StringBuilder();
				Set<Character> linkedHashSet = new LinkedHashSet<>();
				for (int i1 = 0; i1 < extract.length(); i1++){
				    linkedHashSet.add(extract.charAt(i1));
				}
				for (Character c1 : linkedHashSet){
				    sb.append(c1);
				}
				for (int j = 0 ; j <sb.length();j++)
				{
					System.out.println("donner la valuer de "+sb.charAt(j)+": ");
					Scanner sc = new Scanner(System.in);
					char s = sc.next().charAt(0);
					for(int k  = 0 ; k<input.length();k++)
					{
						if(input.charAt(k) == sb.charAt(j))
						{
					     z = input.replace(input.charAt(k),s);
						}
					}
					input = z ;
					System.out.println(input);
				}
				Constant T = new Constant("T = 1");
				Constant F = new Constant("F = 0");
				Expression e1 = new Expression (input, T, F);
				System.out.println(e1.getExpressionString() + " = " + e1.calculate());
			}
		});
		btnBitree.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				  panel.removeAll();
	              String ex = formule.getText();
				  String result;
				  result = Postfix.infixToPostfix(ex);
				  System.out.println(result);
				  Node Tree= construire(result);
				  affichage(Tree);
				  System.out.println("\n");
				  drawnodes(Tree,x,y);
			}
		});
		reset.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
             formule.setText(formule.getText());
             panel.removeAll();
			}
		});
	}
}