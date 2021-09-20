package com.example.testcrudgs3;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;


@SpringComponent
@UIScope
public class FuncionarioEditor extends VerticalLayout implements KeyNotifier {

	private final FuncionarioRepository repository;


	private Funcionario funcionario;


	TextField nome = new TextField("Nome");
	TextField cpf = new TextField("CPF");
	TextField telefone = new TextField("Telefones");
	TextField endereco = new TextField("Endereço");
	TextField email = new TextField("E-mail");
	




	Button save = new Button("Salvar", VaadinIcon.CHECK.create());
	Button delete = new Button("Deletar", VaadinIcon.TRASH.create());
	HorizontalLayout actions = new HorizontalLayout(save, delete);

	Binder<Funcionario> binder = new Binder<>(Funcionario.class);
	private ChangeHandler changeHandler;

	@Autowired
	public FuncionarioEditor(FuncionarioRepository repository) {
		this.repository = repository;

		add(nome, cpf, telefone, endereco, email, actions);


		binder.bindInstanceFields(this);


		setSpacing(true);

		save.getElement().getThemeList().add("primary");
		delete.getElement().getThemeList().add("error");

		addKeyPressListener(Key.ENTER, e -> save());


		save.addClickListener(e -> save());
		delete.addClickListener(e -> delete());
		setVisible(false);
	}

	void delete() {
		repository.delete(funcionario);
		changeHandler.onChange();
	}

	void save() {
		repository.save(funcionario);
		changeHandler.onChange();
	}

	public interface ChangeHandler {
		void onChange();
	}

	public final void editFuncionario(Funcionario f) {
		if (f == null) {
			setVisible(false);
			return;
		}
		final boolean persisted = f.getId() != null;
		if (persisted) {

			funcionario = repository.findById(f.getId()).get();
		}
		else {
			funcionario = f;
		}



		binder.setBean(funcionario);

		setVisible(true);


		nome.focus();
	}

	public void setChangeHandler(ChangeHandler h) {

		changeHandler = h;
	}

}
