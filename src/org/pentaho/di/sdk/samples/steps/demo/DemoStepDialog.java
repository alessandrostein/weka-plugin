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

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.pentaho.di.core.Const;
import org.pentaho.di.i18n.BaseMessages;
import org.pentaho.di.trans.TransMeta;
import org.pentaho.di.ui.trans.step.BaseStepDialog;
import org.pentaho.di.trans.step.BaseStepMeta;
import org.pentaho.di.trans.step.StepDialogInterface;

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
 * This class is the implementation of StepDialogInterface.
 * Classes implementing this interface need to:
 * 
 * Esta classe é uma implementação da interface StepDialogInterface
 * Classes que implementam esta interface precisam de:
 * 
 * - build and open a SWT dialog displaying the step's settings (stored in the step's meta object)
 * 
 * - Construir e abrir um display de dialog para configuração do passao (armazenado no "ste's" do objeto meta)
 * 
 * - write back any changes the user makes to the step's meta object
 * 
 * - Escrever novamente algumas mudanças que o usuario faz para o "step's" do objeto meta.
 * 
 * - report whether the user changed any settings when confirming the dialog 
 * 
 * - Informar se o usuario alterou alguma configuração ao confirmar o dialogo.
 * 
 */
public class DemoStepDialog extends BaseStepDialog implements StepDialogInterface {

	/**
	 *	The PKG member is used when looking up internationalized strings.
	 *	The properties file with localized keys is expected to reside in 
	 *	{the package of the class specified}/messages/messages_{locale}.properties  
	 *
	 * O membro PKG é usando ao olhar as Strings internacionalizadas.
	 * As propriedades chaves do arquivo devem estar localizadas em 
	 * {O pacote da classe especificada}/messages/messages_{locale}.properties 
	 */
	private static Class<?> PKG = DemoStepMeta.class; // for i18n purposes > translate PT-BR > TrPara fins i18n

	// this is the object the stores the step's settings
	// the dialog reads the settings from it when opening
	// the dialog writes the settings to it when confirmed 
	
	// Este é um objeto onde ficam as configurações
	// O dialogo le as configurações quando aberto.	
	// O diagolo escreve as configurações quando confirmado.
	private DemoStepMeta meta;

	// text field holding the name of the field to add to the row stream
	// O campo de texto fixa o nome do campo para adicionar uma nova linha no fluxo.
	private Text wHelloFieldName;

	/**
	 * The constructor should simply invoke super() and save the incoming meta
	 * object to a local variable, so it can conveniently read and write settings
	 * from/to it.
	 * 
	 * O construtor deve envocar o método super() e salvar a entrada do objeto meta
	 * a variavel local, entao ele pode de forma conveniente ler e escrever configurações 
	 * para ele.
	 * 
	 * @param parent 	the SWT shell to open the dialog in
	 * 					O shell SWT para a abrir o dialogo.
	 * @param in		the meta object holding the step's settings
	 * 					O objeto meta  fixado para o "step's" de configurações
	 * @param transMeta	transformation description
	 * 					Descrição da transformação
	 * @param sname		the step name
	 * 					O nome do "step"
	 */
	public DemoStepDialog(Shell parent, Object in, TransMeta transMeta, String sname) {
		super(parent, (BaseStepMeta) in, transMeta, sname);
		meta = (DemoStepMeta) in;
	}

	/**
	 * This method is called by Spoon when the user opens the settings dialog of the step.
	 * 	
	 * 
	 * Este método é chamado pelo Spoon quando o usuario abre o dialogo de confirações para o "step"
	 * Ele deve abrir o dialogo e apenas retornar um dialogo que tenha sido fechado pelo usuario.
	 * 
	 * If the user confirms the dialog, the meta object (passed in the constructor) must
	 * be updated to reflect the new step settings. The changed flag of the meta object must 
	 * reflect whether the step configuration was changed by the dialog.
	 * 
	 * Se o usuario confirmar o dialogo, o objeto meta (passado pelo construtor) necessita ser
	 * atualizado para refletir um novo "step" de configurações. Ao alterar a bandeira do objeto meta é
	 * necessario se o "step" de configuração foi alterado no dialogo.
	 * 
	 * If the user cancels the dialog, the meta object must not be updated, and its changed flag
	 * must remain unaltered.
	 * 
	 * Se o usuario cancelar o dialogo, o objeto meta não é necessario ser atualizado, e as alterações da bandeira 
	 * deve permancer inalterada.
	 * 
	 * The open() method must return the name of the step after the user has confirmed the dialog,
	 * or null if the user cancelled the dialog.
	 * 
	 * O método open() retorna o nome do "step" logo após o usuario seja confirmado o dialog,
	 * ou vazio se o usuario cancelar o dialog.
	 */
	public String open() {

		// store some convenient SWT variables 
		// Armazena algumas variaves convenientes SWT.
		Shell parent = getParent();
		Display display = parent.getDisplay();

		// SWT code for preparing the dialog
		// Código SWT para preparar o diagolo.
		shell = new Shell(parent, SWT.DIALOG_TRIM | SWT.RESIZE | SWT.MIN | SWT.MAX);
		props.setLook(shell);
		setShellImage(shell, meta);
		
		// Save the value of the changed flag on the meta object. If the user cancels
		// the dialog, it will be restored to this saved value.
		// The "changed" variable is inherited from BaseStepDialog
		
		// Salvar o valor das alterações da bandeira do objeto meta. Se o usuario cancelar
		// o dialogo, ele irá ser restaurado com o valor salvo.
		// A variavel "changed" é herdado do BaseStepDialog
		
		changed = meta.hasChanged();
		
		// The ModifyListener used on all controls. It will update the meta object to 
		// indicate that changes are being made.
		
		// A classe ModifyListener é usado em todos os controles. Ele irá atualizar o objeto meta para 
		// indicar que alterações estão sendo feitas.
		
		ModifyListener lsMod = new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				meta.setChanged();
			}
		};
		
		// ------------------------------------------------------- //
		// SWT code for building the actual settings dialog        //
		//                                                         //
		// Código SWT para construir o dialogo de confirações      //
		// ------------------------------------------------------- //
		FormLayout formLayout = new FormLayout();
		formLayout.marginWidth = Const.FORM_MARGIN;
		formLayout.marginHeight = Const.FORM_MARGIN;

		shell.setLayout(formLayout);
		shell.setText(BaseMessages.getString(PKG, "Demo.Shell.Title")); 

		int middle = props.getMiddlePct();
		int margin = Const.MARGIN;

		// Stepname line
		// Linha Stepname
		wlStepname = new Label(shell, SWT.RIGHT);
		wlStepname.setText(BaseMessages.getString(PKG, "System.Label.StepName")); 
		props.setLook(wlStepname);
		fdlStepname = new FormData();
		fdlStepname.left = new FormAttachment(0, 0);
		fdlStepname.right = new FormAttachment(middle, -margin);
		fdlStepname.top = new FormAttachment(0, margin);
		wlStepname.setLayoutData(fdlStepname);
		
		wStepname = new Text(shell, SWT.SINGLE | SWT.LEFT | SWT.BORDER);
		wStepname.setText(stepname);
		props.setLook(wStepname);
		wStepname.addModifyListener(lsMod);
		fdStepname = new FormData();
		fdStepname.left = new FormAttachment(middle, 0);
		fdStepname.top = new FormAttachment(0, margin);
		fdStepname.right = new FormAttachment(100, 0);
		wStepname.setLayoutData(fdStepname);

		// output field value
		// Campo de valor de saída.
		Label wlValName = new Label(shell, SWT.RIGHT);
		wlValName.setText(BaseMessages.getString(PKG, "Demo.FieldName.Label")); 
		props.setLook(wlValName);
		FormData fdlValName = new FormData();
		fdlValName.left = new FormAttachment(0, 0);
		fdlValName.right = new FormAttachment(middle, -margin);
		fdlValName.top = new FormAttachment(wStepname, margin);
		wlValName.setLayoutData(fdlValName);

		wHelloFieldName = new Text(shell, SWT.SINGLE | SWT.LEFT | SWT.BORDER);
		props.setLook(wHelloFieldName);
		wHelloFieldName.addModifyListener(lsMod);
		FormData fdValName = new FormData();
		fdValName.left = new FormAttachment(middle, 0);
		fdValName.right = new FormAttachment(100, 0);
		fdValName.top = new FormAttachment(wStepname, margin);
		wHelloFieldName.setLayoutData(fdValName);
		      
		// OK and cancel buttons
		// Botões OK e Cancelar.
		wOK = new Button(shell, SWT.PUSH);
		wOK.setText(BaseMessages.getString(PKG, "System.Button.OK")); 
		wCancel = new Button(shell, SWT.PUSH);
		wCancel.setText(BaseMessages.getString(PKG, "System.Button.Cancel")); 

		BaseStepDialog.positionBottomButtons(shell, new Button[] { wOK, wCancel }, margin, wHelloFieldName);

		// Add listeners for cancel and OK
		// Adicionado váriavel que escuta o evento Cancelar e OK.
		lsCancel = new Listener() {
			public void handleEvent(Event e) {cancel();}
		};
		lsOK = new Listener() {
			public void handleEvent(Event e) {ok();}
		};

		wCancel.addListener(SWT.Selection, lsCancel);
		wOK.addListener(SWT.Selection, lsOK);

		// default listener (for hitting "enter")
		// Por padrão escuta (para bater "enter")
		lsDef = new SelectionAdapter() {
			public void widgetDefaultSelected(SelectionEvent e) {ok();}
		};
		wStepname.addSelectionListener(lsDef);
		wHelloFieldName.addSelectionListener(lsDef);

		// Detect X or ALT-F4 or something that kills this window and cancel the dialog properly
		// Captura X ou atalho ALT+F4 ou algo que fecha a janela e cancela o dialogo corretamente.
		shell.addShellListener(new ShellAdapter() {
			public void shellClosed(ShellEvent e) {cancel();}
		});
		
		// Set/Restore the dialog size based on last position on screen
		// The setSize() method is inherited from BaseStepDialog
		
		// Set/Restore o tamanho do dialog baseado na ultima posicao da tela.
		// O método setSize() é herdado da classe BaseStepDialog
		
		setSize();

		// populate the dialog with the values from the meta object
		// Preencher o dialogo com os valores do objeto meta
		populateDialog();
		
		// restore the changed flag to original value, as the modify listeners fire during dialog population 
		// Restaura as mudanças para o valor original, fica escutando as modificações durante o preenchimento do dialogo.
		meta.setChanged(changed);

		// open dialog and enter event loop 
		// Abre o dialogo e entra no evento de laço.
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}

		// at this point the dialog has closed, so either ok() or cancel() have been executed
		// The "stepname" variable is inherited from BaseStepDialog
		
		// Neste ponto o dialogo foi fechado, deste moto foram executados Ok ou Cancelar.
		// A variavel "stepname" herdado da classe BaseStepDialog
		
		return stepname;
	}
	
	/**
	 * This helper method puts the step configuration stored in the meta object
	 * and puts it into the dialog controls.
	 * 
	 * Este método auxilia a colocar o "step" de configuração armazenadas em um objeto meta e 
	 * coloca-las os controles de dialogo.
	 */
	private void populateDialog() {
		wStepname.selectAll();
		wHelloFieldName.setText(meta.getOutputField());	
	}

	/**
	 * Called when the user cancels the dialog.  
	 * 
	 * Este método é chamado quando o usuario cancelar o dialogo.
	 */
	private void cancel() {
		// The "stepname" variable will be the return value for the open() method. 
		// Setting to null to indicate that dialog was cancelled.
		
		// A váriavel "stepname" séroa o valor de retorno do método open().
		// Seta-lo para nulo indicam que o dialogo foi cancelado.
		stepname = null;
		// Restoring original "changed" flag on the met a object
		// Restaura o valor original "changed" no conhecido objeto meta;
		meta.setChanged(changed);
		// close the SWT dialog window
		// Fechar a janela de dialogo SWT
		dispose();
	}
	
	/**
	 * Called when the user confirms the dialog
	 * 
	 * Este método é chando quando o usuario confirmar o dialogo.
	 */
	private void ok() {
		// The "stepname" variable will be the return value for the open() method. 
		// Setting to step name from the dialog control
		
		// A váriavel "stepname" séroa o valor de retorno do método open().
		// Definido o nome do valor para o dialog "step"
		stepname = wStepname.getText(); 
		// Setting the  settings to the meta object
		// Define as configurações do objeto meta.
		meta.setOutputField(wHelloFieldName.getText());
		// close the SWT dialog window
		// Fecha a janela de dialogo SWT.
		dispose();
	}
}
