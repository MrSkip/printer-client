package com.kursova.kep.control.main;

import com.kursova.kep.control.base.BaseController;
import com.kursova.kep.custom.CustomProperties;
import com.kursova.kep.custom.stage.StageUtil;
import com.kursova.kep.custom.table.TableColumnsGenerator;
import com.kursova.kep.entity.*;
import com.kursova.kep.message.Dialog;
import com.kursova.kep.rest.Client;
import javafx.fxml.Initializable;
import javafx.print.PrinterJob;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by Mr. Skip.
 */

public class MainController extends BaseController implements Initializable{
    public TableView<Object> table;

    private static Class currentClassObject;
    private static BaseEntity addedEntity;

    public Button
            butSpecification,
            butFirma,
            butPrinter,
            buttonDelete,
            buttonAdd,
            buttonPrint,
            buttonExport;

    public TextArea textArea;

    public Label labCurrentTable;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        currentClassObject = Firma.class;
        Client.setTextArea(textArea);

        List<Firma> list = Client.get("firma", Firma[].class).build();
        TableColumnsGenerator.setTableView(table).generateColumns(list, Firma.class);

        buttonsHandler();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    private void buttonsHandler() {
        buttonDelete.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
            if (event.getButton() == MouseButton.PRIMARY
                    && table.getSelectionModel().getSelectedItem() != null){
                BaseEntity entity = (BaseEntity) table.getSelectionModel().getSelectedItem();
                Client.delete(currentClassObject.getSimpleName().toLowerCase() + "/" + entity.getId(), currentClassObject);
                if (Client.status)
                    table.getItems().remove(table.getSelectionModel().getSelectedItem());
                Client.status = true;
            }
        });

        buttonAdd.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
            if (event.getButton() != MouseButton.PRIMARY)
                return;
            try {
                if (buttonAdd.getText().equals("Зберегти зміни")) {
                    Client.post(currentClassObject.getSimpleName().toLowerCase(), currentClassObject)
                            .setRequest(table.getItems().get(table.getItems().size() - 1))
                            .build();
                    addedEntity = null;

                    buttonAdd.setText("Додати");
                    buttonAdd.setPrefWidth(120);
                }
                else if (buttonAdd.getText().equals("Додати")) {
                    buttonAdd.setText("Зберегти зміни");
                    buttonAdd.setPrefWidth(200);

                    addedEntity = (BaseEntity) Class.forName(currentClassObject.getPackage().getName() + "." + currentClassObject.getSimpleName())
                            .newInstance();
                    addedEntity.setId((long)table.getItems().size() + 1);
                    table.getItems().add(addedEntity);
                    table.getSelectionModel().select((long) table.getItems().size() - 1);
                }
            } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
                e.printStackTrace();
            }
        });

        buttonPrint.setOnMouseClicked(event -> {
            if (event.getButton() != MouseButton.PRIMARY)
                return;
            if (table.getItems() == null || table.getItems().isEmpty())
                return;
            boolean bl = Dialog.showConfirmDialog("", "Друк поточної таблиці \""
                    + labCurrentTable.getText() + "\"", "Бажаєте роздрукувати?");
            if (!bl) return;

            PrinterJob printerJob = PrinterJob.createPrinterJob();
            if (printerJob == null){
                Dialog.showErrorDialog("Не встановлено жодного принтера");
                return;
            }
            if (printerJob.showPrintDialog(getStage())
                    && printerJob.printPage(table)) {
                printerJob.endJob();
            }
        });

        buttonExport.setOnMouseClicked(event -> {
            if (event.getButton() != MouseButton.PRIMARY)
                return;

            if (table.getColumns() == null || table.getItems() == null
                    || table.getColumns().isEmpty() || table.getItems().isEmpty())
                Dialog.showErrorDialog("Немає даних, будь ласка, введіть дані");

            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Збереження файлу");
            fileChooser.setSelectedExtensionFilter(new FileChooser.ExtensionFilter("TEXT", "*.txt"));
            File file = fileChooser.showSaveDialog(getStage());

            if (file == null || !file.getPath().endsWith(".txt"))
                return;

            try {
                Writer out = new BufferedWriter(new OutputStreamWriter(
                        new FileOutputStream(file), "UTF8"));
                for (Object o : table.getItems()) {
                    out.write(o.toString() + "\n");
                }
                out.close();
                Dialog.showInformationDialog("Експорт у файл вдало завершено");
            } catch (IOException e) {
                Dialog.showErrorDialog("Неправильно вказаний шлях до файлу, або ж його розширення");
            }
        });


        butSpecification.addEventFilter(MouseEvent.MOUSE_CLICKED, this::buttonObjectHandler);
        butFirma.addEventFilter(MouseEvent.MOUSE_CLICKED, this::buttonObjectHandler);
        butPrinter.addEventFilter(MouseEvent.MOUSE_CLICKED, this::buttonObjectHandler);
    }

    private void buttonObjectHandler(MouseEvent e){
        if (e.getButton() == MouseButton.PRIMARY){
            addedEntity = null;
            buttonAdd.setText("Додати");
            String objectName = ((Button) e.getSource()).getText();

            currentClassObject = CustomProperties.getClassFromHeaderButtons(objectName);
            labCurrentTable.setText(objectName);
            List list = null;
            try {
                list = Client.get(currentClassObject.getSimpleName().toLowerCase(),
                        Class.forName("[L" + currentClassObject.getPackage().getName() + "." + currentClassObject.getSimpleName() + ";")).build();
            } catch (ClassNotFoundException e1) {
                e1.printStackTrace();
            }
            TableColumnsGenerator.setTableView(table).generateColumns(list, currentClassObject);
        }
    }

}
