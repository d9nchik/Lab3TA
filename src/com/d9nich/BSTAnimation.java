package com.d9nich;

import com.d9nich.AVL.AVLTree;
import com.d9nich.AVL.BST;
import com.d9nich.AVL.BTView;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.*;

public class BSTAnimation extends Application {
    private BST<Field> tree = new AVLTree<>(); // Create a tree

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {


        final File file = new File("output.dat");
        if (file.exists()) {
            try (ObjectInputStream inputStream = new ObjectInputStream(new BufferedInputStream(new FileInputStream(file)))) {
                tree = (AVLTree<Field>) (inputStream.readObject());
            } catch (FileNotFoundException ex) {
                System.out.println("File not found");
            } catch (IOException ex) {
                System.out.println("Problem with stream");
            } catch (ClassNotFoundException ex) {
                System.out.println("Class not found");
            }
        }

        BorderPane pane = new BorderPane();
        BTView view = new BTView(tree); // Create a View
        pane.setCenter(view);

        TextField tfKey = new TextField();
        tfKey.setPrefColumnCount(3);
        TextField tfValue = new TextField();
        tfValue.setPrefColumnCount(5);
        tfKey.setAlignment(Pos.BASELINE_RIGHT);
        Button btInsert = new Button("Insert");
        Button btDelete = new Button("Delete");
        Button btChange = new Button("Change");
        Button btGet = new Button("Get");
        HBox hBox = new HBox(5);
        hBox.getChildren().addAll(new Label("Enter a key: "), tfKey
                , new Label("Enter a value: "), tfValue, btInsert, btDelete, btChange, btGet);
        hBox.setAlignment(Pos.CENTER);
        pane.setBottom(hBox);

        btInsert.setOnAction(e -> {
            int key = Integer.parseInt(tfKey.getText());
            String value = tfValue.getText();
            Field field = new Field(key, value);
            if (tree.search(field)) { // key is in the tree already
                view.displayTree();
                view.setStatus(key + " is already in the tree");
            } else {
                tree.insert(field); // Insert a new key
                view.displayTree();
                view.setStatus(key + " is inserted in the tree");
            }
        });

        btDelete.setOnAction(e -> {
            int key = Integer.parseInt(tfKey.getText());
            String value = tfValue.getText();
            Field field = new Field(key, value);
            if (!tree.search(field)) { // key is not in the tree
                view.displayTree();
                view.setStatus(key + " is not in the tree");
            } else {
                tree.delete(field); // Delete a key
                view.displayTree();
                view.setStatus(key + " is deleted from the tree");
            }
        });

        // Create a scene and place the pane in the stage
        Scene scene = new Scene(pane, 500, 250);
        primaryStage.setTitle("BSTAnimation"); // Set the stage title
        primaryStage.setScene(scene); // Place the scene in the stage
        primaryStage.show(); // Display the stage
        view.displayTree();

        primaryStage.setOnCloseRequest(e -> saveData(file));

        btChange.setOnAction(e -> {
            int key = Integer.parseInt(tfKey.getText());
            String value = tfValue.getText();
            Field myField = tree.searchAndReturn(new Field(key, value));
            if (myField != null) {
                myField.setValue(value);
                view.displayTree();
                view.setStatus(key + " value changed to " + value);
            } else {
                view.displayTree();
                view.setStatus(key + " is not in the tree");
            }
        });

        btGet.setOnAction(e -> {
            int key = Integer.parseInt(tfKey.getText());
            String value = tfValue.getText();
            Field myField = tree.searchAndReturn(new Field(key, value));
            if (myField != null) {
                tfValue.setText(myField.getValue());
                view.displayTree();
                view.setStatus(key + " value is " + myField.getValue());
            } else {
                view.displayTree();
                view.setStatus(key + " is not in the tree");
            }
        });

        Timeline animation = new Timeline(new KeyFrame(Duration.minutes(5), e -> saveData(file)));
        animation.setCycleCount(Animation.INDEFINITE);
        animation.play();

    }

    private void saveData(File file) {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new BufferedOutputStream(
                new FileOutputStream(file)))) {
            outputStream.writeObject(tree);
        } catch (FileNotFoundException ex) {
            System.out.println("File not found!");
        } catch (IOException ex) {
            System.out.println("Stream problem");
        }
    }
}
