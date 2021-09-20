package com.example.testcrudgs3.view;

import com.example.testcrudgs3.Funcionario;
import com.example.testcrudgs3.FuncionarioEditor;
import com.example.testcrudgs3.FuncionarioRepository;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.util.StringUtils;

@Route
public class MainView extends VerticalLayout {
	
	

	private final FuncionarioRepository repo;

	private final FuncionarioEditor editor;

	final Grid<Funcionario> grid;

	final TextField filter;

	private final Button addNewBtn;

	public MainView(FuncionarioRepository repo, FuncionarioEditor editor) {
		this.repo = repo;
		this.editor = editor;
		this.grid = new Grid<>(Funcionario.class);
		this.filter = new TextField();
		
		this.addNewBtn = new Button("Novo Funcionario", VaadinIcon.PLUS.create());


		HorizontalLayout actions = new HorizontalLayout(filter, addNewBtn);
		add(actions, grid, editor);

		grid.setHeight("300px");
		grid.setColumns("id", "nome", "cpf", "telefone", "endereco", "email");
		grid.getColumnByKey("id").setWidth("50px").setFlexGrow(0);

		filter.setPlaceholder("Filtrar por CPF");


		filter.setValueChangeMode(ValueChangeMode.EAGER);
		filter.addValueChangeListener(e -> listFuncionarios(e.getValue()));
		

		
		grid.asSingleSelect().addValueChangeListener(e -> {
			editor.editFuncionario(e.getValue());
		});


		addNewBtn.addClickListener(e -> editor.editFuncionario(new Funcionario("", "", "", "", "")));


		editor.setChangeHandler(() -> {
			editor.setVisible(false);
			listFuncionarios(filter.getValue());
		});


		listFuncionarios(null);
	}
	


	void listFuncionarios(String filterText) {
		if (StringUtils.isEmpty(filterText)) {
			grid.setItems(repo.findAll());
		}
		else {
			grid.setItems(repo.findBycpfStartsWithIgnoreCase(filterText));
		}
	}


}
