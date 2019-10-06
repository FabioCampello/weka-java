package br.com.weka.java;

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
	
	static DataSource ds;
	static Instances ins;
	static NaiveBayes nb;
	
	public static void main(String[] args) {
		try {
			ds = new DataSource("src/br/com/weka/java/vendas.arff");
			ins = recuperaBaseDeDados(ds);
			
			ins.setClassIndex(3);
			
			aplicandoNaiveBayes(ins);
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	static Instances recuperaBaseDeDados(DataSource data) throws Exception {
		Instances instance = data.getDataSet();
		return instance;
	}
	
	static void aplicandoNaiveBayes(Instances i) throws Exception {
		nb = new NaiveBayes();
		nb.buildClassifier(i);
		setNewInstance(i);
	}
	
	static void setNewInstance(Instances i) throws Exception {
		Instance novo = new DenseInstance(4);
		novo.setDataset(i);
		novo.setValue(0, "M");
		novo.setValue(1, "20-39");
		novo.setValue(2, "Nao");
		
		double probabilidade[] = nb.distributionForInstance(novo);
		System.out.println("Sim: " + probabilidade[1]);
		System.out.println("Não: " + probabilidade[0]);
	}

}
