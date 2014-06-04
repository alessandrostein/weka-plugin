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

import org.pentaho.di.core.row.RowMetaInterface;
import org.pentaho.di.trans.step.BaseStepData;
import org.pentaho.di.trans.step.StepDataInterface;

import weka.core.Instance;

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
 * This class is the implementation of StepDataInterface.
 * 
 * Esta classe é uma implementação da StepDataInterface.
 *   
 * Implementing classes inherit from BaseStepData, which implements the entire
 * interface completely. 
 * 
 * Implementando classes herdadas do BaseStepData, com implemetações completas de interface.
 * 
 * In addition classes implementing this interface usually keep track of
 * per-thread resources during step execution. Typical examples are:
 * result sets, temporary data, caching indexes, etc.
 * 
 * Nas adições de implemenção desta interface geralmente mantem o controle de recursos por thread durante a 
 * execução do "step". Exemplos típocos são:
 * Conjunto de resultados, dados temporarios, indices de cache, etc.
 *   
 * The implementation for the demo step stores the output row structure in 
 * the data class. 
 * 
 * A implementação da demonstração "step" armazena uma estrutura de linha de saída na classe de dados.
 *   
 */

public class DemoStepData extends BaseStepData implements StepDataInterface {

	public RowMetaInterface outputRowMeta;
	
    public DemoStepData()
	{
		super();
	}
    
    
    
    /**
     * Helper method that constructs an Instance to input to the Weka model based
     * on incoming Kettle fields and pre-constructed attribute-to-field mapping
     * data.
     * 
     * @param inputMeta a <code>RowMetaInterface</code> value
     * @param inputRow an <code>Object</code> value
     * @param mappingIndexes an <code>int</code> value
     * @param model a <code>WekaScoringModel</code> value
     * @return an <code>Instance</code> value
     *
    private Instance constructInstance(RowMetaInterface inputMeta,
        Object[] inputRow, int[] mappingIndexes, WekaScoringModel model,
        boolean freshVector) {

      Instances header = model.getHeader();

      // Re-use this array (unless told otherwise) to avoid an object creation
      if (m_vals == null || freshVector) {
        m_vals = new double[header.numAttributes()];
      }

      for (int i = 0; i < header.numAttributes(); i++) {

        if (mappingIndexes[i] >= 0) {
          try {
            Object inputVal = inputRow[mappingIndexes[i]];

            Attribute temp = header.attribute(i);
            ValueMetaInterface tempField = inputMeta
                .getValueMeta(mappingIndexes[i]);
            int fieldType = tempField.getType();

            // Check for missing value (null or empty string)
            if (tempField.isNull(inputVal)) {
              m_vals[i] = Utils.missingValue();
              continue;
            }

            switch (temp.type()) {
            case Attribute.NUMERIC: {
              if (fieldType == ValueMetaInterface.TYPE_BOOLEAN) {
                Boolean b = tempField.getBoolean(inputVal);
                if (b.booleanValue()) {
                  m_vals[i] = 1.0;
                } else {
                  m_vals[i] = 0.0;
                }
              } else if (fieldType == ValueMetaInterface.TYPE_INTEGER) {
                Long t = tempField.getInteger(inputVal);
                m_vals[i] = t.longValue();
              } else {
                Double n = tempField.getNumber(inputVal);
                m_vals[i] = n.doubleValue();
              }
            }
              break;
            case Attribute.NOMINAL: {
              String s = tempField.getString(inputVal);
              // now need to look for this value in the attribute
              // in order to get the correct index
              int index = temp.indexOfValue(s);
              if (index < 0) {
                // set to missing value
                m_vals[i] = Utils.missingValue();
              } else {
                m_vals[i] = index;
              }
            }
              break;
            case Attribute.STRING: {
              String s = tempField.getString(inputVal);
              // Set the attribute in the header to contain just this string value
              temp.setStringValue(s);
              m_vals[i] = 0.0;
              break;
            }
            default:
              m_vals[i] = Utils.missingValue();
            }
          } catch (Exception e) {
            m_vals[i] = Utils.missingValue();
          }
        } else {
          // set to missing value
          m_vals[i] = Utils.missingValue();
        }
      }

      Instance newInst = new DenseInstance(1.0, m_vals);
      newInst.setDataset(header);
      return newInst;
    }*/
}
	
