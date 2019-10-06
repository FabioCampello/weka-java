package br.com.weka.java;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import weka.classifiers.bayes.NaiveBayes;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

/**
 * @author fabio
 *
 */
public class ApplicationMain {

	JFrame frame = new JFrame();

	// DECLARAÇÃO DE VARIÁVEIS DE ENTRADA DA PESSOA
	static String nome = "";
	static String sexo = "";
	static int idade = 0;
	static String filhos = "";

	// DECLARAÇÃO DE VARIÁVEIS WEKA
	static DataSource ds;
	static Instances ins;
	static NaiveBayes nb;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		createGUI();
		try {
			ds = new DataSource("src/br/com/weka/java/vendas.arff");
			ins = recuperaBaseDeDados(ds);
			ins.setClassIndex(3);
			aplicandoNaiveBayes(ins);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	static Instances recuperaBaseDeDados(DataSource data) throws Exception {
		Instances instance = data.getDataSet();
		return instance;
	}

	// APLICANDO ALGORITMO DE CLASSIFICAÇÃO
	static void aplicandoNaiveBayes(Instances i) throws Exception {
		nb = new NaiveBayes();
		nb.buildClassifier(i);
		setNewInstance(i);
	}

	// MÉTODO QUE SETA O OBJETO COLETADO PARA O CLASSIFICAR
	static void setNewInstance(Instances i) throws Exception {
		Instance novo = new DenseInstance(4);
		
		try {
			novo.setDataset(i);
			
			novo.setValue(0, sexo);
			if(idade >= 40) {
				novo.setValue(1, ">=40");
			} else if(idade >= 20 && idade <= 39) {
				novo.setValue(1, "20-39");
			} else if(idade >= 18 && idade < 20) {
				novo.setValue(1, "18-19");
			}
			novo.setValue(2, filhos);

			double probabilidade[] = nb.distributionForInstance(novo);
			
			JOptionPane.showMessageDialog(null, "Perfil pessoa" + "\nNome: " + nome + "\nSexo: " + sexo + "\nIdade: " + idade + "\nTem filhos: " + filhos
					+ "\nProbabilidade de gastar mais: "
					+ "\nSIM: " + Double.toString(probabilidade[1]) + "\nNÃO: " + Double.toString(probabilidade[0]));
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
	}

	// MÉTODO RESPONSÁVEL PELA INTERFACE GRÁFICA
	static void createGUI() {
		nome = createGUIFromInput("nome", "Informe o nome da pessoa nome:", "Você deve informar um nome.");
		sexo = createGUIFromInput("sexo", "Selecione o sexo da pessoa", "Você deve informar o sexo.");
		idade = Integer.parseInt(createGUIFromInput("idade", "Informe a idade da pessoa", "Você deve informar a idade."));
		filhos = createGUIFromInput("verdadeiroOuFalso", "Tem filhos?", "Você deve selecionar: SIM ou NÃO");
	}

	// MÉTODO QUE CRIA OS INPUTS DE ENTRADA DE DADOS
	static String createGUIFromInput(String tipo, String input, String output) {
		String variavel = null;
		int idadeTemp = 0;
		while (variavel == null || variavel.equals("")) {
			if (tipo != null && tipo.equals("nome")) {
				variavel = JOptionPane.showInputDialog(input);
				if (variavel == null || variavel.equals("")) {
					JOptionPane.showMessageDialog(null, output);
				}
			} else if (tipo != null && tipo.equals("verdadeiroOuFalso")) {
				int isFilhos = JOptionPane.showConfirmDialog(null, input, "Selecione um opção...", JOptionPane.YES_NO_OPTION);
				variavel = recuperaOption(isFilhos, tipo, output);
			} else if(tipo != null && tipo.equals("sexo")) {
				Object[] options = { "Masculino", "Feminino" };
				int isSexo = JOptionPane.showOptionDialog(null, input, "Escolha", JOptionPane.DEFAULT_OPTION, JOptionPane.DEFAULT_OPTION, null, options, options[0]);
				variavel = recuperaOption(isSexo, tipo, output);
			} else if(tipo != null && tipo.equals("idade")) {
				while(idadeTemp < 18) {
					variavel = JOptionPane.showInputDialog(input);
					idadeTemp = Integer.parseInt(variavel);
					if(idadeTemp < 18) {
						JOptionPane.showMessageDialog(null, "A pessoa deve ser de maior.");
					}
				}
				if (variavel == null || variavel.equals("")) {
					JOptionPane.showMessageDialog(null, output);
				}
			}
		}
		return variavel;
	}
	
	// RECUPERA INPUT TIPO OPTION
	static String recuperaOption(int isTrue, String tipo, String output) {
		String variavelTemp = null;
		switch (isTrue) {
		case -1:
			JOptionPane.showMessageDialog(null, output);
			break;
		case 0:
			if(tipo != null && tipo.equals("verdadeiroOuFalso")) {
				variavelTemp = "Sim";
			} 
			else if(tipo != null && tipo.equals("sexo")) {
				variavelTemp = "M";
			}
			break;
		case 1:
			if(tipo != null && tipo.equals("verdadeiroOuFalso")) {
				variavelTemp = "Nao";
			} 
			else if(tipo != null && tipo.equals("sexo")) {
				variavelTemp = "F";
			}
			break;
		default:
			break;
		}
		return variavelTemp;
	}
}
