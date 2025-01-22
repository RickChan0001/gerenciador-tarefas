package com.gerenciadortarefas.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.gerenciadortarefas.model.Task;
import com.gerenciadortarefas.service.TaskService;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class TaskComponentModalController implements Initializable {

    @FXML
    private TextArea description;

    @FXML
    private DatePicker executedAt;

    @FXML
    private DatePicker finishedAt;

    @FXML
    private Button saveButton;

    @FXML
    private TextField title;

    private TaskService service;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        // TODO Auto-generated method stub
    }

    @FXML
    public void createTask(ActionEvent event) {
        Task task = new Task(
                title.getText(),
                description.getText(),
                executedAt.getValue(),
                finishedAt.getValue());

        service.addTask(task);
        Stage stage = (Stage) saveButton.getScene().getWindow();
        stage.close(); // fecha a aba quando clicar em salvar
    }

    public void setService(TaskService service) {
        this.service = service;
    }

}
