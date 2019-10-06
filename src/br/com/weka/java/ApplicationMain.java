package br.com.weka.java;

import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

/**
 * @author fabio
 *
 */
public class ApplicationMain {
	
	static DataSource ds;
	static Instances ins;
	
	public static void main(String[] args) {
		try {
			ds = new DataSource("src/br/com/weka/java/vendas.arff");
			ins = recuperaBaseDeDados(ds);
			System.out.println(ins.toString());
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	static Instances recuperaBaseDeDados(DataSource data) throws Exception {
		Instances instance = data.getDataSet();
		return instance;
	}

}
