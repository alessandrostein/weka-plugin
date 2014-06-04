/*******************************************************************************
 *
 * Pentaho Data Integration
 *
 * Copyright (C) 2002-2012 by Pentaho : http://www.pentaho.com
 *
 *******************************************************************************
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 ******************************************************************************/

package org.pentaho.di.sdk.samples.steps.demo;

import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.core.row.RowDataUtil;
import org.pentaho.di.core.row.RowMetaInterface;
import org.pentaho.di.trans.Trans;
import org.pentaho.di.trans.TransMeta;
import org.pentaho.di.trans.step.BaseStep;
import org.pentaho.di.trans.step.StepDataInterface;
import org.pentaho.di.trans.step.StepInterface;
import org.pentaho.di.trans.step.StepMeta;
import org.pentaho.di.trans.step.StepMetaInterface;

/**
 * This class is part of the demo step plug-in implementation.
 * It demonstrates the basics of developing a plug-in step for PDI. 
 * 
 * Esta classe é parte de uma implementação de um plug-in demo step.
 * É demonstrado o básico do desenvolvimento de um plug-in step para o PDI.
 * 
 * The demo step adds a new string field to the row stream and sets its
 * value to "Hello World!". The user may select the name of the new field.
 * 
 * O demonstrativo "step" adiciona um novo campo do tipo "String" para uma 
 * nova linha e define o valor "Ola Mundo". O usuario pode selecionar o nome 
 * deste novo campo.
 *   
 * This class is the implementation of StepInterface.
 * 
 * Esta classe é uma implementação da interface "StepInterface"
 * 
 * Classes implementing this interface need to:
 * 
 * Classes que implementam esta interface precisam de:
 * 
 * - initialize the step
 * 
 * - inicializar o "step"
 * 
 * - execute the row processing logic
 * 
 * - executar e processar a linha lógica.
 * 
 * - dispose of the step 
 * 
 * - Dispoẽ do "step"
 * 
 * Please do not create any local fields in a StepInterface class. Store any
 * information related to the processing logic in the supplied step data interface
 * instead.  
 * 
 * Por favor não crie nenhum campo local para a interface "StepInterface". 
 * Não armazene nenhuma informação relacionada para o processo lógico na
 * interface como alternativa
 */

public class DemoStep extends BaseStep implements StepInterface {

	/**
	 * The constructor should simply pass on its arguments to the parent class.
	 * 
	 * O construtor deve simplesmente passar seus argumentos para a classe pai. 
	 * 
	 * @param s 				step description
	 * 							Descrição do "step"
	 * @param stepDataInterface	step data class
	 * 							Interface de acesso a dados
	 * @param c					step copy
	 * 							Copia do "step"
	 * @param t					transformation description
	 * 							Descrição da transformação
	 * @param dis				transformation executing
	 * 							Executando a transformação
	 */
	public DemoStep(StepMeta s, StepDataInterface stepDataInterface, int c, TransMeta t, Trans dis) {
		super(s, stepDataInterface, c, t, dis);
	}
	
	/**
	 * This method is called by PDI during transformation startup. 
	 * 
	 * Este método é chamado pelo PDI durante a inicialização da transformação.
	 * 
	 * It should initialize required for step execution. 
	 * 
	 * A inicialização é obrigatória para a execucao do "step"
	 * 
	 * The meta and data implementations passed in can safely be cast
	 * to the step's respective implementations. 
	 * 
	 * As implementações meta e dados podem passar ser convertido por segurança 
	 * para "step's" respectivamente implementados
	 * 
	 * It is mandatory that super.init() is called to ensure correct behavior.
	 * 
	 * É obrigario que o método "super.init()" seje chamado para garantir o comportamento
	 * correto.
	 * 
	 * Typical tasks executed here are establishing the connection to a database,
	 * as wall as obtaining resources, like file handles.
	 * 
	 * Tarefas tipicas executadas aqui são estabelecidas conexões com a base de dados,
	 * como obter recursos, manipular arquivos. 
	 * 
	 * @param smi 	step meta interface implementation, containing the step settings
	 * 				Implementação da interface "meta" no "step", contendo um "step" de configurações.
	 * @param sdi	step data interface implementation, used to store runtime information
	 * 				Implementação da interface "data" no "step", usando para armazenar informações.
	 * 
	 * @return true if initialization completed successfully, false if there was an error preventing the step from working. 
	 * 				Se a inicialização for completada com uscesso retorna "true", caso contrario houve um erro que o impeça de trabalhar.
	 *  
	 */
	public boolean init(StepMetaInterface smi, StepDataInterface sdi) {
		// Casting to step-specific implementation classes is safe
		// Converçao de mplementações de "step" especificos é seguro.
		DemoStepMeta meta = (DemoStepMeta) smi;
		DemoStepData data = (DemoStepData) sdi;

		return super.init(meta, data);
	}	

	/**
	 * Once the transformation starts executing, the processRow() method is called repeatedly
	 * by PDI for as long as it returns true. To indicate that a step has finished processing rows
	 * this method must call setOutputDone() and return false;
	 * 
	 * Com a transformação começando a executar, o método processRow() é chamado repetidamente 
	 * pelo PDI contando até o retorno ser verdade. É indicado que para "step" finalize o processo de linhas
	 * deste metodo é necessario chamar o método setOutputDone() e retorne "false".
	 * 
	 * Steps which process incoming rows typically call getRow() to read a single row from the
	 * input stream, change or add row content, call putRow() to pass the changed row on 
	 * and return true. If getRow() returns null, no more rows are expected to come in, 
	 * and the processRow() implementation calls setOutputDone() and returns false to
	 * indicate that it is done too.
	 * 
	 * "Steps" que processam entradas de linhas geralmente é chamado o método getRow() para ler uma única linha para a
	 * o fluxo de entrada, alterar ou adicionar linhas no contexto, chamar o putRow() para passar as mudanças em uma linha
	 * e retornar "true". Se o método getRow() retornar vazio, não são esperados mais linhas para entrar.
	 * 
	 * Steps which generate rows typically construct a new row Object[] using a call to
	 * RowDataUtil.allocateRowData(numberOfFields), add row content, and call putRow() to
	 * pass the new row on. Above process may happen in a loop to generate multiple rows,
	 * at the end of which processRow() would call setOutputDone() and return false;
	 * 
	 * "Steps" que geram linhas tipicamente controem um novo objeto linha usando a chamada para RowDataUtil.allocateRowData(numberOfFields),
	 * adiciona uma nova linha de conteúdo, e chamado putRow() para passar uma nova linha. O processo acima atráves de um loop pode gerar muitas linhas,
	 * no final do processRow() que chamaria setOutputDone() e retorne false.
	 * 
	 * @param smi the step meta interface containing the step settings
	 * 
	 * 			  O "StepMetaInterface" contém o "step" com as configurações.
	 * 
	 * @param sdi the step data interface that should be used to store
	 * 
	 * 			  O "StepDataInterface que deve ser usado para armazenar dados.
	 * 
	 * @return true to indicate that the function should be called again, false if the step is done
	 *          
	 *          Para o retorno ser verdade é indicado que a função deve ser chamado novamente, se o retorno ser falso o "step" foi feito.
	 *     
	 */
	public boolean processRow(StepMetaInterface smi, StepDataInterface sdi) throws KettleException {

		// safely cast the step settings (meta) and runtime info (data) to specific implementations 
		// Converção segura de "step" de configurações (meta) e informações em tempo de execução (data) 
		// para implementações especificas.
		DemoStepMeta meta = (DemoStepMeta) smi;
		DemoStepData data = (DemoStepData) sdi;
		
		
		// get incoming row, getRow() potentially blocks waiting for more rows, returns null if no more rows expected
		// Pega a linha de entrada, o método getRow() potencialmente espera blocos para mais linhas, retorna nulo se não ter mais linhas esperadas.
		Object[] r = getRow(); 
		
		// if no more rows are expected, indicate step is finished and processRow() should not be called again
		// Se não houver mais linhas experadas, "step" indicado é finalizado e o método "processRow() não deve ser chamado novamente.
		if (r == null){
			setOutputDone();
			return false;
		}

		// the "first" flag is inherited from the base step implementation
		// A primeira bandeira é herdado para uma implementação básica do "step"
		// it is used to guard some processing tasks, like figuring out field indexes
		// Não é usado para guardar tarefas de processamento, e descobrir índices do campo.
		// in the row structure that only need to be done once
		// Na estrutura da linha que apenas precisa ser feito uma vez.
		if (first) {
			first = false;
			// clone the input row structure and place it in our data object
			// Clona a estrutura da linha de entrada e coloca em nosso objeto de dados.
			data.outputRowMeta = (RowMetaInterface) getInputRowMeta().clone();
			// use meta.getFields() to change it, so it reflects the output row structure 
			// Usar meta.getFields() para mudanças, por isto reflete na estrutura da linha de saída.
			meta.getFields(data.outputRowMeta, getStepname(), null, null, this);
		}

		// safely add the string "Hello World!" at the end of the output row
		// the row array will be resized if necessary 
		
		// Seguramente a String adicionada "Ola Mundo" no final da linha de saída.
		// A matriz da linha será redimensionada se necessario
		
		Object[] outputRow = RowDataUtil.addValueData(r, data.outputRowMeta.size() - 1, "Hello World!");

		// put the row to the output row stream
		// Mostra a linha para o fluxo da linha de sáida.
		putRow(data.outputRowMeta, outputRow); 

		// log progress if it is time to to so
		// Processo de histórico de tempos em tempos.
		if (checkFeedback(getLinesRead())) {
			// Some basic logging
			// Alguns historicos básicos. 
			logBasic("Linenr " + getLinesRead()); 
		}

		// indicate that processRow() should be called again
		// Indica que o método processRow() deve ser chamado novamente
		return true;
	}

	/**
	 * This method is called by PDI once the step is done processing. 
	 * 
	 * Este métodp é chamado pelo PDI quando o processo do "step" é feito.
	 * 
	 * The dispose() method is the counterpart to init() and should release any resources
	 * acquired for step execution like file handles or database connections.
	 * 
	 * O método dispose() é uma contrapartida do método init() e deve liberar alguns recursos
	 * adiquiridos para a executação do "step", como os identificadores do arquivo ou conexão do banco de dados.
	 * 
	 * The meta and data implementations passed in can safely be cast
	 * to the step's respective implementations. 
	 * 
	 * O StepMetaInterface e StepDataInterface implementados podem passar por uma conversão de segurança 
	 * conforme as respectivas implementações.
	 * 
	 * It is mandatory that super.dispose() is called to ensure correct behavior.
	 * 
	 * É obrigatório que o método super.dispose() seje chamado para o funcionamento correto.
	 * 
	 * @param smi 	step meta interface implementation, containing the step settings
	 * 				Implementação StepMetaInterface, contém o "step" de configurações.
	 * @param sdi	step data interface implementation, used to store runtime information
	 * 				Implementação do StepDataInterface, usado para armazenar informaçoes em tempo de execução.
	 */
	public void dispose(StepMetaInterface smi, StepDataInterface sdi) {

		// Casting to step-specific implementation classes is safe
		// Converçao de implementações de "step" especificos é seguro.
		DemoStepMeta meta = (DemoStepMeta) smi;
		DemoStepData data = (DemoStepData) sdi;
		
		super.dispose(meta, data);
	}

}
