package com.gerenciadortarefas.controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.gerenciadortarefas.model.Task;
import com.gerenciadortarefas.service.TaskService;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class HomeController implements Initializable {
    @FXML
    private StackPane rootPane;
    @FXML
    private VBox taskContainer;
    @FXML
    private Button addTaskButton;
    private TaskService service;

    public HomeController() {
        service = new TaskService();
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        // TODO Auto-generated method stub
        setTasks();// chama o metodo pra ser executado
    }

    private void setTasks() {
        service.initObservable().addListener((javafx.collections.ListChangeListener<Task>) change -> {
            while (change.next()) {
                if (change.wasAdded()) {
                    for (Task task : change.getAddedSubList()) {
                        renderTask(task);
                    }
                }
            }
        });

        for (Task task : service.getTasks()) {
            renderTask(task);
        }
    }

    private void renderTask(Task task) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/gerenciadortarefas/view/taskComponent.fxml"));
            Node taskNode = loader.load();
            TaskComponentController controller = loader.getController();
            controller.setService(service);
            controller.setTaskData(task);
            taskContainer.getChildren().add(taskNode);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleOpenTaskModal(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(
                    getClass().getResource("/com/gerenciadortarefas/view/taskComponentModal.fxml"));
            Parent root = fxmlLoader.load();

            TaskComponentModalController controller = fxmlLoader.getController();
            controller.setService(service);

            Stage stage = new Stage();
            stage.setTitle("Criar Nova Tarefa");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
