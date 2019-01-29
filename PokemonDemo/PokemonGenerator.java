package PokemonDemo;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class PokemonGenerator {

	private InputPanel ip;
	@SuppressWarnings("unused")
	private GeneratePanel gp;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		@SuppressWarnings("unused")
		PokemonGenerator pg = new PokemonGenerator("Pokemon: Brown - Pokemon Generator");

	}
	
	public PokemonGenerator(String s){
		JFrame jf = new JFrame(s);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel jp = new JPanel();
		jf.setPreferredSize(new Dimension(1000,200));
		
		jp.setLayout(new GridLayout(1,1));
		
		ip = new InputPanel();
		jp.add(ip);
		
		gp = new GeneratePanel();
	//	jp.add(gp);
		
		jf.add(jp);
		jf.pack();
		jf.setVisible(true);
	}
	
	@SuppressWarnings("serial")
	public class InputPanel extends JPanel{
		public JTextField pNameField, pDexField, pmaxHPField, pLevelField, pExpField, pexpMultField, pBaseExpField, pStatField, pBeltField, pRateField, pATKField, pDEFField, pspAField, pspDefField, pSpeedField, pAccField, pEvaField;
		public JComboBox t1, t2;
		public JLabel fLoc, bLoc, iLoc;
		public JPanel _this;
		public InputPanel(){
			_this = this;
			JLabel pName = new JLabel("Name: ");
			pNameField = new JTextField(8);
			this.add(pName);
			this.add(pNameField);
			
			JLabel dex = new JLabel(" PokeDex Number: ");
			pDexField = new JTextField(3);
			this.add(dex);
			this.add(pDexField);
			
			JLabel labt1 = new JLabel(" Type 1: ");
			String[] s = {"LIGHTNING", "FLYING", "NORMAL", "GHOST", "DRAGON", "GRASS", "FIRE", "WATER", "ROCK", "GROUND", "BUG", "POISON", "ICE", "FIGHTING", "PSYCHIC", "NONE"};
			t1 = new JComboBox(s);
			this.add(labt1);
			this.add(t1);
			
			JLabel labt2 = new JLabel(" Type 2: ");
			String[] s2 = {"LIGHTNING", "FLYING", "NORMAL", "GHOST", "DRAGON", "GRASS", "FIRE", "WATER", "ROCK", "GROUND", "BUG", "POISON", "ICE", "FIGHTING", "PSYCHIC", "NONE"};
			t2 = new JComboBox(s2);
			this.add(labt2);
			this.add(t2);
			
			JLabel mh = new JLabel(" Max HP: ");
			pmaxHPField = new JTextField(2);
			this.add(mh);
			this.add(pmaxHPField);
			
			JLabel lev = new JLabel(" Level: ");
			pLevelField = new JTextField(2);
			this.add(lev);
			this.add(pLevelField);
			
			JLabel exp = new JLabel(" Exp: ");
			pExpField = new JTextField(3);
			this.add(exp);
			this.add(pExpField);
			
			JLabel mu = new JLabel(" Exp Mult: ");
			this.pexpMultField = new JTextField(3);
			this.add(mu);
			this.add(pexpMultField);
			
			JLabel baseExp = new JLabel(" Base Exp: ");
			this.pBaseExpField = new JTextField(3);
			this.add(baseExp);
			this.add(pBaseExpField);
			
			pStatField = new JTextField(2);
			this.pStatField.setText("0");
			
			pBeltField = new JTextField(2);
			this.pBeltField.setText("1");
			
			JLabel r = new JLabel(" Rate: ");
			this.pRateField = new JTextField(3);
			this.add(r);
			this.add(pRateField);
			
			JLabel a = new JLabel(" ATK: ");
			this.pATKField = new JTextField(3);
			this.add(a);
			this.add(pATKField);
			
			JLabel d = new JLabel(" DEF: ");
			this.pDEFField = new JTextField(3);
			this.add(d);
			this.add(pDEFField);
			
			JLabel spa = new JLabel(" SpATK: ");
			this.pspAField = new JTextField(3);
			this.add(spa);
			this.add(pspAField);
			
			JLabel spd = new JLabel(" SpDEF: ");
			this.pspDefField = new JTextField(3);
			this.add(spd);
			this.add(pspDefField);
			
			JLabel acc = new JLabel(" ACC: ");
			this.pAccField = new JTextField(3);
			this.add(acc);
			this.add(pAccField);
			
			JLabel ev = new JLabel(" EVA: ");
			this.pEvaField = new JTextField(3);
			this.add(ev);
			this.add(pEvaField);
			
			JLabel f = new JLabel(" Front: ");
			fLoc = new JLabel("");
			JButton fB = new JButton("Front Image");
			ButtonListener bl = new ButtonListener(fLoc);
			fB.addActionListener(bl);
			this.add(f);
			this.add(fLoc);
			this.add(fB);
			
			JLabel b = new JLabel(" Back: ");
			bLoc = new JLabel("");
			JButton bB = new JButton("Back Image");
			ButtonListener bl2 = new ButtonListener(bLoc);
			bB.addActionListener(bl2);
			this.add(b);
			this.add(bLoc);
			this.add(bB);
			
			JLabel i = new JLabel(" Icon: ");
			iLoc = new JLabel("");
			JButton iB = new JButton("Icon Image");
			ButtonListener bl3 = new ButtonListener(iLoc);
			iB.addActionListener(bl3);
			this.add(i);
			this.add(iLoc);
			this.add(iB);
			
			JButton gen = new JButton("GENERATE CODE");
			GenerateListener gl = new GenerateListener();
			gen.addActionListener(gl);
			this.add(gen);
		}
		
		private class ButtonListener implements ActionListener{
			private JLabel jl;
			public ButtonListener(JLabel j){
				super();
				
				jl = j;
			}
			public void actionPerformed(ActionEvent e){
				JFileChooser fileChoose = new JFileChooser();
				if(fileChoose.showOpenDialog(_this) == JFileChooser.APPROVE_OPTION){
					File f = fileChoose.getSelectedFile();
					String name = f.getPath();
					
					String s = name;
					s = s.substring(s.lastIndexOf("Pokemon"));
					s = s.replace('\\', '/');
					
					jl.setText(s);
				}
			}
		}
		
		private class GenerateListener implements ActionListener{
			public void actionPerformed(ActionEvent e){
				
				SysOut.print("=====POKEMON GENERATION BEGIN=====");
				SysOut.print();
				
				SysOut.print("public static class " + pNameField.getText() + " extends Pokemon{");
				SysOut.print("\tpublic " + pNameField.getText()+ "() throws IOException{");
				SysOut.print("\t\tsuper(" + pDexField.getText()+", \"" + pNameField.getText() + "\", Types." + t1.getSelectedItem() + ", Types." + t2.getSelectedItem() + ", " + pmaxHPField.getText() + ", " + pLevelField.getText() + ", " + pExpField.getText() +", " + pexpMultField.getText() + ", " + pBaseExpField.getText() + ", " + pStatField.getText() + ", " + pBeltField.getText() + ", " + pRateField.getText() + ", " + pATKField.getText() + ", " + pDEFField.getText() + ", " + pspAField.getText() + ", " +  pspDefField.getText() + ", " + pAccField.getText() + ", " + pEvaField.getText() + ", ImageIO.read(new File(\"" + fLoc.getText() + "\")), ImageIO.read(new File(\"" + bLoc.getText() + "\")), new ImageIcon\"" + iLoc.getText() + "\"));");
				SysOut.print("\t}");
				SysOut.print("}");
				
				SysOut.print();
				SysOut.print("=====POKEMON GENERATION END=====");
				
				
			}
		}
	}
	

	
	@SuppressWarnings("serial")
	public class GeneratePanel extends JPanel{
		public GeneratePanel(){
			
		}
	}

}
